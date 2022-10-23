package com.example.quiz.service;

// Optional: A container object which may or may not contain a non-null value. 
// If a value is present, isPresent() will return true and get() will return the value.
import java.util.Optional;

// クラスの呼び出しや結びつけのための記述を減らしてくれる
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// トランザクションとは複数の処理を１つにまとめたもの、成功か失敗のどちらかしかない
// 処理の途中で失敗した場合は、トランザクション実行前に戻る、これをロールバックという	
// 処理が全て成功した場合は、処理が確定する、これをコミットと呼ぶ
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizRepository;

// ビジネスロジック・アルゴリズム等が書かれた処理を提供する。
// 付与することでSpirngのコンポーネントとして認識され、ApplicationContextに登録される。
// @Transactionalは、クラスに付与することで、自動でコミット・ロールバックをしてくれる
// トランザクション境界が必要なのは、更新処理を含むサービス処理のみだが、仕様上はクラスへ付与する
@Service
@Transactional
public class QuizServiceImpl implements QuizService {
	/** Repository: 注入 */
	// QuizRepositoryを注入
	@Autowired
	QuizRepository repository;
	
	@Override
	public Iterable<Quiz> selectAll() {
		return repository.findAll();
	}
	
	@Override
	public Optional<Quiz> selectOneById(Integer id) {
		return repository.findById(id);
	}
	
	@Override
	public Optional<Quiz> selectOneRandomQuiz() {
		// ランダムでidの値を取得する
		Integer randId = repository.getRandomId();
		// 問題がない場合
		if (randId == null) {
			// 空のOptionalインスタンスを返す
			return Optional.empty();
		}
		return repository.findById(randId);
	}
	
	@Override
	public Boolean checkQuiz(Integer id, Boolean myAnswer) {
		// クイズの正解/不正解用を判定する変数
		Boolean check = false;
		// 対象のクイズを取得
		Optional<Quiz> optQuiz = repository.findById(id);
		// 値存在チェック
		if (optQuiz.isPresent()) {
			Quiz quiz = optQuiz.get();
			// クイズの解答チェック
			if(quiz.getAnswer().equals(myAnswer)) {
				check = true;
			}
		}
		return check;
	}
	
	@Override
	public void insertQuiz(Quiz quiz) {
		repository.save(quiz);
	}
	
	@Override
	public void updateQuiz(Quiz quiz) {
		repository.save(quiz);
	}
	
	@Override
	public void deleteQuizById(Integer id) {
		repository.deleteById(id);
	}

}
