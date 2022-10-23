package com.example.quiz.repository;

import org.springframework.data.jdbc.repository.query.Query;
// Interface for generic CRUD operations on a repository for a specific type.
import org.springframework.data.repository.CrudRepository;

import com.example.quiz.entity.Quiz;

/** Quizテーブル： RepositoryImpl */
// ImplはImplementの略
// RepositoryImplは、Repositoryインタフェースで定義された操作の実装を行う役割を担う
// RepositoryImplの実装はRepositoryインタフェースによって隠蔽されるため、ドメイン層のコンポーネントでは、
// どのようにデータアクセスされているか意識しなくてすむ。
// <Quiz, Integer>は保存対象のオブジェクトの型と保存対象のオブジェクトの主キーの型を指定する
// リポジトリとは簡単にいうとデータベースへのデータ操作を行うクラス→必ずインターフェースを定義して実装する
public interface QuizRepository extends CrudRepository<Quiz, Integer> {
	@Query("SELECT id FROM quiz ORDER BY RANDOM() limit 1")
	Integer getRandomId();
}
