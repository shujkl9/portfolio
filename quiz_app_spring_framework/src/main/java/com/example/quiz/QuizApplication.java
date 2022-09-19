package com.example.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// @Autowiredは記述するだけで他のクラスを呼び出すことができる。いちいちnewを書いてクラスを呼び出す必要がない
// @Autowiredを書くことで、他クラスとの繋がりを宣言する記述が大幅に減る
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quiz.entity.Quiz;
//import com.example.quiz.repository.QuizRepository;
import com.example.quiz.service.QuizService;

@SpringBootApplication
public class QuizApplication {
	/** 起動メソッド */
	public static void main(String[] args) {
		// getBean()は、コンテナに対して「このクラスのインスタンスくれー」と要求（getBean）すること
		SpringApplication.run(QuizApplication.class, args);
		
		//【重要】動作確認ができたので、上記のように修正
		//SpringApplication.run(QuizApplication.class, args).getBean(QuizApplication.class).execute();	
	}

//【重要】動作確認が終了したため、以下コードをコメントアウトする。
//	/** 注入 */
//	@Autowired
//	QuizService service;
//	//QuizRepository repository;
//	
//	/** 実行メソッド */
//	private void execute() {
//		// 登録処理
//		//setup();
//		
//		// 全件取得
//		//showList();
//		
//		// 1件取得
//		//showOne();
//		
//		// 更新処理
//		//updateQuiz();
//		
//		// 削除処理
//		//deleteQuiz();
//		
//		// クイズを実行する
//		doQuiz();
//	}
//	
//	/** === クイズを5件登録します == */
//	private void setup() {
//		System.out.println("---登録開始処理---");
//		// エンティティ生成
//		//Quiz quiz1 = new Quiz(null, "「Springはフレームワークですか？」", true, "登録太郎");
//		Quiz quiz1 = new Quiz(null, "「JAVA」はオブジェクト指向言語である。", true, "登録太郎");
//		Quiz quiz2 = new Quiz(null, "「Spring Data」はデータアクセスに対する機能を提供する。", true, "登録太郎");
//		Quiz quiz3 = new Quiz(null, "プログラムが沢山配置されているサーバーのことを「ライブラリ」という。", false, "登録太郎");
//		Quiz quiz4 = new Quiz(null, "「@Component」はインスタンス生成アノテーションである", true, "登録太郎");
//		Quiz quiz5 = new Quiz(null, "「Spring MVC」が実装している「デザインパターン」で全てのリクエストを一つ"
//				+ "のコントローラで受け取るパターンは「シングルコントローラパターン」である。", false, "登録太郎");
//		
//		// リストにエンティティを格納
//		List<Quiz> quizList = new ArrayList<>();
//		// 第一引数に格納先、第二引数は可変長引数なので、エンティティを記述
//		Collections.addAll(quizList, quiz1, quiz2, quiz3, quiz4, quiz5);
//		
//		// 登録実行
//		for (Quiz quiz: quizList) {
//			// 登録実行
//			service.insertQuiz(quiz);
//		}
//		System.out.println("---登録処理完了---");
//		
//		// 登録実行
//		// save(S entity): Saves a given entity.
//		// saveメソッドは@Idアノテーションが付与されたフィールドが「null」の場合「Insert文」を実行し、
//		// 「null」でない場合UPDATE文を実行する
//		// quiz1 = repository.save(quiz1);
//		// 登録確認
//		// System.out.println("登録データは、" + quiz1 + "です。");
//		// エンティティ生成
//		// Quiz quiz2 = new Quiz(null, "「Spring MVCはバッチ処理を提供しますか？」", false, "登録太郎");
//		// 登録実行
//		// quiz2 = repository.save(quiz2);
//		// 登録確認
//		// System.out.println("登録データは" + quiz2 + "です。");
//	
//	}
//	
//	/** === 全件取得 === */
//	private void showList() {
//		System.out.println("---全件取得開始---");
//		
//		// リポジトリを使用して全件取得を実施、結果を取得
//		//System.out.println(repository.findAll());
//		
//		//Implementing this interface allows an object to be the target of the "for-each loop" statement. 
//		//Iterable<Quiz> quizzes = repository.findAll();
//		Iterable<Quiz> quizzes = service.selectAll();
//			for (Quiz quiz : quizzes) {
//			System.out.println(quiz);
//		}
//		System.out.println("--全件取得完了--");
//	}
//	
//	/** === 1件取得 === */
//	private void showOne() {
//		System.out.println("---1件取得---");
//		// リポジトリを使用して1件取得を実施、結果を取得（戻り値はオプショナル）
//		// Optionalはnullかもしれない値を取り扱うためのクラス、値をラップして使用する
//		// ランダムにIdを指定する
//		//Optional<Quiz> quizOpt = repository.findById(Integer.valueOf((int)(Math.random()  * repository.count())));
//		Optional<Quiz> quizOpt = service.selectOneById(1);
//		// 値存在チェック
//		if (quizOpt.isPresent()) {
//			System.out.println(quizOpt.get());
//		}
//		else {
//			System.out.println("該当する問題が存在しません。");
//		}
//		System.out.println("--- 1件取得完了 ---");
//	}
//	
//	/** === 更新処理 === */
//	private void updateQuiz() {
//		System.out.println("--更新処理開始--");
//		// 更新したいエンティティを生成する
//		Quiz quiz1 = new Quiz(1, "「Springはフレームワークですか？」", true, "変更タロウ");
//		// 更新実行
//		//quiz1 = repository.save(quiz1);
//		service.updateQuiz(quiz1);
//		// 更新確認
//		//System.out.println("更新したデータは" + quiz1 + "です。");
//		System.out.println("---更新処理完了---");
//	}
//	
//	/** === 削除処理 === */
//	private void deleteQuiz() {
//		System.out.println("--削除処理開始--");
//		//削除実行
//		//repository.deleteById(Integer.valueOf((int)(Math.random()  * repository.count())));
//		service.deleteQuizById(2);
//		System.out.println("--削除完了--");
//	}
//	
//	/** === ランダムでクイズを取得して、クイズの正解/不正解を判定する === */
//	private void doQuiz() {
//		System.out.println("--- クイズ1件取得開始 ---");
//		
//		// リポジトリを使用して1件取得を実施、結果を取得（戻り値はOptional）
//		Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
//		
//		// 値存在チェック
//		if(quizOpt.isPresent()) {
//			System.out.println(quizOpt.get());
//		} else {
//			System.out.println("該当する問題が存在しません。");
//		}
//		
//		System.out.println("--- クイズ1件取得完了 ---");
//		
//		// 解答を実施
//		Boolean myAnswer = false;
//		Integer id = quizOpt.get().getId();
//		if(service.checkQuiz(id, myAnswer)) {
//			System.out.println("正解です！");
//		} else {
//			System.out.println("不正解です・・・");
//		}
//	}

}
