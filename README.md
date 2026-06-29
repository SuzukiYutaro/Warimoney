# Warimoney - 割り勘記録清算アプリ

## プロジェクト概要

**Warimoney**は、グループで発生した支出を記録し、効率的に清算できるWebアプリケーションです。複数人での旅行や食事、イベント開催時に、誰がいくら払ったか、誰がいくら返すべきかを自動計算し、公平に清算できます。ユーザー登録・認証機能を備え、プロジェクト単位で支出管理を行う設計になっています。

**主な特徴：**
- ユーザー登録・ログイン認証
- プロジェクト作成・管理（グループ単位での管理）
- メンバー追加・管理
- 支出の記録と複数メンバーへの割り当て
- 自動清算計算（誰が誰にいくら返すべきかの算出）
- 清算状況の確認

---

## 技術スタック

- **言語：** Java 25
- **フレームワーク：** Spring Boot 4.0.6
- **テンプレートエンジン：** Thymeleaf（HTML生成）
- **データベース：** MySQL
- **認証・セキュリティ：** Spring Security
- **ORM：** Spring Data JPA
- **ビルドツール：** Maven
- **その他：** Lombok（ボイラープレートコード削減）

---

## プロジェクト構成

```
src/main/java/com/example/warimoney/
├── WarimoneyApplication.java       # アプリケーション起動クラス
├── controller/                      # HTTPリクエスト処理
│   ├── UserController.java          # ユーザー登録・ログイン
│   ├── ProjectController.java       # プロジェクト管理
│   ├── MemberController.java        # メンバー管理
│   ├── ExpenseController.java       # 支出記録・一覧
│   └── SettlementController.java    # 清算情報表示
├── service/                         # ビジネスロジック層
│   ├── UserService.java             # ユーザー管理ロジック
│   ├── ProjectService.java          # プロジェクト管理ロジック
│   ├── MemberService.java           # メンバー管理ロジック
│   ├── ExpenseService.java          # 支出記録・計算ロジック
│   ├── SettlementService.java       # 清算額計算ロジック（複雑な計算エンジン）
│   └── AppUserDetailsService.java   # Spring Security連携
├── domain/                          # エンティティ・ドメインモデル
│   ├── User.java                    # ユーザーエンティティ
│   ├── Project.java                 # プロジェクトエンティティ
│   ├── Member.java                  # プロジェクト内のメンバー
│   ├── Expense.java                 # 支出記録エンティティ
│   └── ExpenseParticipant.java      # 支出の割り当て情報
├── repository/                      # データベースアクセス層
│   └── [JPA Repository インターフェース]
├── dto/                             # データ転送オブジェクト
│   └── [リクエスト・レスポンス用DTO]
├── exception/                       # カスタム例外
│   └── [アプリケーション例外クラス]
└── common/                          # 共通ユーティリティ
    └── [共通処理]

src/main/resources/
├── application.properties           # Spring Boot設定（DB接続、ポート等）
├── templates/                       # Thymeleafテンプレート（HTML）
└── static/                          # 静的リソース（CSS、JavaScript、画像）
```

**アーキテクチャの流れ：**
ユーザーからのHTTPリクエスト → Controller（リクエスト処理） → Service（ビジネスロジック実行） → Repository（DB操作） → Domain Model（データモデル）という標準的なレイヤード・アーキテクチャを採用しています。特にSettlementServiceでは複雑な清算額計算ロジックを実装し、複数メンバー間の債権債務関係を最適化して計算します。

---

## 起動方法

### 必要な環境
- **Java 25** がインストールされていること
- **MySQL** がインストール・起動されていること
- **Maven** がインストールされていること（またはプロジェクトに同梱のmvnwを使用）

### セットアップ手順

1. **リポジトリをクローン**
   ```bash
   git clone https://github.com/SuzukiYutaro/Warimoney.git
   cd Warimoney
   ```

2. **データベースの作成**
   MySQLにログインして、アプリケーション用のデータベースを作成します：
   ```sql
   CREATE DATABASE warimoney CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **application.propertiesの設定**
   `src/main/resources/application.properties` を編集して、MySQLの接続情報を設定します：
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/warimoney
   spring.datasource.username=root
   spring.datasource.password=[あなたのMySQLパスワード]
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```

4. **アプリケーション起動**
   
   **Mavenを使用する場合：**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   または
   ```bash
   mvn spring-boot:run
   ```
   
   **Windowsの場合：**
   ```bash
   mvnw.cmd spring-boot:run
   ```
   
   **JARファイルにビルドして実行する場合：**
   ```bash
   ./mvnw clean package
   java -jar target/warimoney-0.0.1-SNAPSHOT.jar
   ```

5. **ブラウザでアクセス**
   アプリケーションが起動したら、以下のURLでアクセスします：
   ```
   http://localhost:8080
   ```

### 起動確認
- コンソールに `Started WarimoneyApplication in X.XXX seconds` というメッセージが表示されれば起動成功です
- ブラウザでログイン画面またはホーム画面が表示されます

---

## 主な機能説明

### 1. ユーザー管理（UserController / UserService）
- ユーザー登録：新規アカウント作成
- ログイン：Spring Securityによるセッション管理
- プロフィール編集

### 2. プロジェクト管理（ProjectController / ProjectService）
- 新規プロジェクト作成（グループ作成）
- プロジェクト情報の表示・編集
- メンバーの追加・削除

### 3. メンバー管理（MemberController / MemberService）
- プロジェクトへのメンバー招待
- メンバー情報の管理
- プロジェクト内での役割設定

### 4. 支出記録（ExpenseController / ExpenseService）
- 支出の記録：「誰が、いくら支払ったか」を入力
- 支出の割り当て：その支出を「誰と割るか」を設定
- 支出一覧の表示・編集・削除

### 5. 清算計算（SettlementController / SettlementService）
- 自動清算計算：複数メンバー間の債権債務を最適化
- 清算結果の表示：「AさんはBさんに〇〇円返す」という形式
- 清算状況の確認・追跡

---

## データベース設計概要

- **users テーブル**：ユーザー情報（ログインID、パスワード等）
- **projects テーブル**：プロジェクト・グループ情報
- **members テーブル**：プロジェクト内のメンバー管理
- **expenses テーブル**：支出記録（支払者、金額、日付等）
- **expense_participants テーブル**：支出の割り当て（支出を誰と割るか）

---

## 使用技術の特徴

- **Spring Boot 4.0.6**：最新のSpring Bootフレームワーク、自動設定により開発効率UP
- **Spring Security**：ユーザー認証・認可、セッション管理
- **Spring Data JPA**：オブジェクトとデータベースの自動マッピング
- **Thymeleaf**：サーバーサイドテンプレートエンジン、HTMLレンダリング
- **Lombok**：アノテーションでgetterやequalsメソッドを自動生成
- **MySQL**：信頼性の高いリレーショナルデータベース

---

## 今後の拡張案

- 支出のカテゴリ分類機能
- 複数通貨対応
- REST API化
- 支出履歴のレポート・分析機能
- LINE Notify連携による通知機能
- モバイルアプリ化

---

## ポートフォリオにおける学習ポイント

このプロジェクトから学べること：

1. **Spring Bootフル・スタック開発**：Web MVCアーキテクチャの実装
2. **認証・認可実装**：Spring Securityを使用したセキュアなアプリケーション開発
3. **データベース設計**：複数テーブル間のリレーション設計と正規化
4. **レイヤード・アーキテクチャ**：Controller → Service → Repository パターンの実装
5. **複雑なビジネスロジック実装**：清算計算エンジンの設計（SettlementService）
6. **JPA/Hibernateの活用**：ORM技術の実践的な使用

---

**作成者：** SuzukiYutaro  
**ライセンス：** 未設定  
**更新日：** 2026年6月
