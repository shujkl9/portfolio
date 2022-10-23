package com.example.quiz.entity;

// org.springframework.data.annotation is the core annotations being used by Spring Data.
// Demarcates an identifier. (識別子を区切る。)
import org.springframework.data.annotation.Id;

// 〇〇ArgsConstructorは、コンストラクタを自動生成してくれるアノテーション
// @AllArgsConstructorは、全フィールドに対する初期化値を引数に取るコンストラクタを生成する
// @NoArgsConstructorは、デフォルトコンストラクタを自動生成する
import lombok.AllArgsConstructor;
// クラスに@Dataアノテーションを付与すると、対象クラス内のインスタンス変数に対してgetter/setterでアクセスすることが可能になる
// @Dataアノテーションで、@Getter/@Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructorを付与したのと同じ状態になる
import lombok.Data;
import lombok.NoArgsConstructor;

/** quizテーブル用： Entity */
// Domain Objectはサービス処理を実行する上で必要な資源、ここではEntityと同義語。
// Entityはデータベースの「テーブルの１行」に対応するクラス。
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
	/** 識別ID */
	// @Idはプライマリーキーとなるプロパティかフィールドを指定する
	@Id
	private Integer id;
	/** クイズの内容 */
	private String question;
	/** クイズの解答 */
	private Boolean answer;
	/** 作成者 */
	private String author;
}
