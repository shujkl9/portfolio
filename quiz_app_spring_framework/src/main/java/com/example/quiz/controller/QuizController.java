package com.example.quiz.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//Indicates that an annotated class is a "Controller" (e.g. a web controller).
//This annotation serves as a specialization of @Component, allowing for implementation classes to be autodetected through 
//classpath scanning. It is typically used in combination with annotated handler methods based on the RequestMapping annotation.
import org.springframework.stereotype.Controller;
//Interface that defines a holder for model attributes.
//Primarily designed for adding attributes to the model.
//Allows for accessing the overall model as a java.util.Map.
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//@RequestMappingはコントローラに付与して、リクエストURLに対してどのメソッド処理を実行するか定義する。
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quiz.entity.Quiz;
import com.example.quiz.form.QuizForm;
import com.example.quiz.service.QuizService;

/** Quizコントローラ */
//@RequestMappingはコントローラに付与して、リクエストURLに対してどのメソッド処理を実行するか定義する。
// リクエストを受け取るメソッドは、@RequestMappingアノテーションを付与する。
// リクエストで送られるURLに対応するメソッドのことをリクエストハンドラメソッドと呼ぶ
@Controller
@RequestMapping("/quiz")
public class QuizController {
	/** DI対象 */
	@Autowired
	QuizService service;
	
	/** form-backing beanの初期化 */
	// HTMLのformタグに関連づけるFormクラスインスタンスのことをform-backing beanと呼ぶ→@ModelAttributeで結びつけることができる
	//メソッドの戻り値を、Thymeleafで使用する変数へ対応付ける。マッピングされたメソッドの前に実行される。
	//「Thymeleaf（タイムリーフ）」とはJavaテンプレートエンジンです。 MVCモデルでいう「V（ビュー）」にあたる部分。
	// @ModelAttributeアノテーションが付与されたメソッドは、リクエストハンドラメソッド実行前に呼ばれ、リクエストスコープでModelに格納される
	@ModelAttribute
	public QuizForm setUpForm() {
		QuizForm form = new QuizForm();
		//ラジオボタンのデフォルト値設定
		form.setAnswer(true);
		return form;
	}
	
	/** Quizの一覧を表示する */
	// Modelインターフェースとは、処理したデータをビューで表示させたい場合、データを引き渡す役割を持っている
	// ModelはSpring MVCにより管理され、手動または自動でオブジェクトを格納し管理する機能を提供する
	// Modelを使用したい場合、リクエストハンドラメソッドの引数にModel型を渡す→Spring MVCが自動的にModel型のインスタンスを設定する
	// GETリクエストを処理する@RequestMappngの簡略アノテーション
	@GetMapping
	public String showList(QuizForm quizForm, Model model) {
		// 新規登録設定
		quizForm.setNewQuiz(true);
		
		// 掲示板の一覧を取得する
		Iterable<Quiz> list = service.selectAll();
		
		// 表示用
		// addAttribute: Add the supplied attribute under the supplied name
		// 指定した名前に対して指定した値を設定する、ニックネームをつけるイメージ: addAttribute(String name, Object value)
		// name: ニックネーム、value: 値、格納したいオブジェクト
		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		// Spring Bootのデフォルト設定では、src/main/resources/templatesフォルダ配下のリクエストハンドラメソッドの戻り値.htmlファイルが参照される
		return "crud";
	}
	
	/** Quizデータを１件挿入 */
	// @Validatedアノテーションで単項目チェックアノテーションを設定しているFormクラスに付与することでバリデーションを実行
	// 実行結果はBindingResultインターフェースに保持されるBindingResultインターフェースのhasErrorsメソッドの戻り値でエラー有無を確認
	// true→エラーあり、false→エラーなし
	// エラー有の場合は、一覧表示処理を呼び出す。RedirectAttributesを引数に設定する
	@PostMapping("/insert")
	public String insert(@Validated QuizForm quizForm, BindingResult bindingResult, 
			Model model, RedirectAttributes redirectAttributes) {
		// FormからEntityへの詰め替え
		Quiz quiz = new Quiz();
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());
		//入力チェック
		if (!bindingResult.hasErrors()) {
			service.insertQuiz(quiz);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました");			
			return "redirect:/quiz";
		}
		else {
			//エラーがある場合は、一覧表示処理を呼び出します
			return showList(quizForm, model);
		}
	}
	
	/** Quizデータを１件取得し、フォーム内に表示する */
	@GetMapping("/{id}")
	public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
		//Quizを取得しOptionalでラップ
		Optional<Quiz> quizOpt = service.selectOneById(id);
		//QuizFormへの詰め直し→mapメソッドは、Optionalのメソッドで、値があるときだけ値を何かに返す
		Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
		// QuizFormがnullでなければ中身を取り出す
		if(quizFormOpt.isPresent()) {
			quizForm = quizFormOpt.get();
		}
		//更新用のModelを作成する
		makeUpdateModel(quizForm, model);
		return "crud";
	}

	/** 更新用のModelを作成する */
	private void makeUpdateModel(QuizForm quizForm, Model model) {
		model.addAttribute("id", quizForm.getId());
		quizForm.setNewQuiz(false);
		model.addAttribute("quizForm", quizForm);
		model.addAttribute("title", "更新用フォーム");
	}
	
	/** idをKeyにしてデータを更新する */
	//本updateメソッドは、登録機能のinsertメソッドとやっていることは変わらない、
	@PostMapping("/update")
	public String update(@Validated QuizForm quizForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		//QuizFormからQuizに詰め直す
		Quiz quiz = makeQuiz(quizForm);
		//入力チェック
		if (!result.hasErrors()) {
			//更新処理、フラッシュスコープの使用、リダイレクト（個々の編集ページ）
			service.updateQuiz(quiz);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました");
			//更新画面を表示する
			return "redirect:/quiz/" + quiz.getId();
		}
		else {
			//更新用のModelを作成する
			makeUpdateModel(quizForm, model);
			return "crud";
		}
	}
	
	// ---------【以下はFormとDomainObjectの詰め直し】 ----------
	/** QuizFormからQuizに詰め直して戻り値とし返します */
	private Quiz makeQuiz(QuizForm quizForm) {
		Quiz quiz = new Quiz();
		quiz.setId(quizForm.getId());
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());
		return quiz;
	}
	
	/** QuizからQuizFormに詰め直して戻り値とし返します */
	private QuizForm makeQuizForm(Quiz quiz) {
		QuizForm form = new QuizForm();
		form.setId(quiz.getId());
		form.setQuestion(quiz.getQuestion());
		form.setAnswer(quiz.getAnswer());
		form.setAuthor(quiz.getAuthor());
		form.setNewQuiz(false);
		return form;
	}
	
	/** idをKeyにしてデータを削除する */
	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id, Model model, RedirectAttributes redirectAttributes) {
		//タスクを1件削除してリダイレクト
		service.deleteQuizById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました");
		return "redirect:/quiz";
	}
	
	// ------------------【以下はクイズで遊ぶ機能】---------------------
	/** Quizデータをランダムで１件取得し、画面に表示する */
	@GetMapping("/play")
	public String showQuiz(QuizForm quizForm, Model model) {
		//Quizを取得（Optionalでラップ）
		Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
		//値が入っているか判定する
		if(quizOpt.isPresent()) {
			//QuizFormへの詰め直し
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();
		}
		else {
			model.addAttribute("msg", "問題がありません・・・");
			return "play";
		}
		
		//表示用「Model」への格納
		model.addAttribute("quizForm", quizForm);
		return "play";
	}
	
	/** クイズの正解・不正解を判定する　*/
	@PostMapping("/check")
	public String checkQuiz(QuizForm quizForm, @RequestParam Boolean answer, Model model) {
		if(service.checkQuiz(quizForm.getId(), answer)) {
			model.addAttribute("msg", "正解です！");
		}
		else {
			model.addAttribute("msg","残念、不正解です・・・" );
		}
		return "answer";
	}
	
}
