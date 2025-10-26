# Questionnaire - 整体師用問診票アプリ

これは、紙媒体で管理されていた問診票をDX化するために制作したAndroidアプリです。顧客情報と問診内容をデジタルで管理し、Google Driveへのバックアップ・復元機能を備えています。


![アプリのメイン画面](https://github.com/user-attachments/assets/1a413eca-8144-4892-8411-db7d05eea408)

## 目次
- [プロジェクトの背景](#プロジェクトの背景)
- [主な機能](#主な機能)
- [使用技術](#使用技術)
- [セットアップ方法](#セットアップ方法)
- [アプリの使い方](#アプリの使い方)
- [こだわった点](#こだわった点)
- [ER図・画面遷移図](#er図画面遷移図)
- [いただいたFBと今後の展望](#いただいたfbと今後の展望)
- [詳細資料(説明書)](#詳細資料)

## プロジェクトの背景
私の父は個人で整体師を営んでおり、紙媒体の問診票が100枚以上に達していました 。この業務をDX化し、管理を効率化するために本アプリを開発しました。

### Androidアプリである理由
開発にあたり、初期費用とランニングコストを最小限に抑えることを重視しました。Androidタブレットは1万円程度から購入可能であり、個人開発でコストを最も抑えられる選択肢だと考え、Androidでの開発を選択しました。

## 主な機能
- **新規顧客登録機能**: 新しいお客様の個人情報を登録します。
- **問診票（カルテ）追記機能**: 既存のお客様の問診内容を記録します。
- **顧客情報・問診内容の確認機能**: 登録された情報を一覧で確認・検索できます。
- **Google Driveへのバックアップ・復元機能**: 端末の故障や買い替えに備え、全データをGoogle Driveに安全に保存・復元できます。

## 使用技術
- **言語**: Kotlin
- **UI**: Android View System (XML)
- **アーキテクチャ**: Activity-Based (各画面をActivityとして実装)
- **非同期処理**: Kotlin Coroutines
- **API通信**:
  - Google Drive API (OAuth 2.0)
  - Google Auth Library for Android
- **JSONパース**: Gson
- **データベース**: SQLite (SQLiteOpenHelper)

- ## セットアップ方法
1. このリポジトリをクローンします。
2. Android Studioでプロジェクトを開きます。
3. **Google Drive APIのセットアップ**
   Google Driveへのバックアップ機能を利用するには、以下の手順でOAuth 2.0クライアントIDを設定する必要があります。
   - Google Cloud Platformでプロジェクトを作成し、**Google Drive APIを有効化**します。
   - **OAuth 2.0 クライアント IDを作成**します。
     - **パッケージ名**: `build.gradle.kts`に記載されている`namespace`を指定します。
     - **SHA-1 証明書フィンガープリント**: Android Studioのターミナルで `./gradlew signingReport` を実行して取得したキーを設定します。
   - 作成したプロジェクトの「OAuth同意画面」で、ご自身のGoogleアカウントを**テストユーザーとして追加**します。
4. **インターネット権限の確認**
  `AndroidManifest.xml`に`<uses-permission android:name="android.permission.INTERNET" />`が含まれていることを確認します。
5. Android Studioでビルドして実行します。

## アプリの使い方

<details>
<summary>1. メイン画面</summary>

![メイン画面](https://github.com/user-attachments/assets/1a413eca-8144-4892-8411-db7d05eea408)

- **新規登録**: 新しいお客様の情報を入力する画面（新規登録画面）へ遷移します。
- **情報追記**: 既存のお客様を選択し、問診票を記入する画面（既存顧客選択画面）へ遷移します。
- **情報を見る**: 登録されているお客様の情報を確認する画面（顧客情報確認用選択画面）へ遷移します。
- **GoogleDriveに保存/取得ボタン**: 全データをGoogle Driveへバックアップ、または復元します。

</details>

<details>
<summary>2. 新規登録画面</summary>

![新規登録画面](https://github.com/user-attachments/assets/1c5a0c6c-a101-4812-843b-337b267a1a97)

お客様の個人情報を入力します。

- **クリア**: 入力項目をすべてリセットします。
- **確認画面へ**: 入力内容を確認する画面（登録内容確認画面）へ遷移します。

</details>

<details>
<summary>3. 登録内容確認画面</summary>

![登録内容確認画面](https://github.com/user-attachments/assets/93917a2b-3bb4-478c-a947-ef9ff3597378)

新規登録、または情報更新時に入力した内容の最終確認を行います。

- **登録/更新**: 入力内容をデータベースに保存します。保存後、問診票を続けて入力するか、トップ画面に戻るかを選択するダイアログが表示されます。
- **キャンセル**: 入力内容を破棄し、前の画面に戻ります。

</details>

<details>
<summary>3.5. 登録完了フラグメント</summary>

![登録完了フラグメント](https://github.com/user-attachments/assets/30047351-34dc-4f92-b761-2fe4b772d363)

- **トップ画面へ戻る**
保存し、トップに戻る場合はこのボタンを押下(メイン画面に遷移)

- **カルテへ**
保存した情報を持って、カルテ画面にそのまま移動(カルテ画面に遷移)

</details>

<details>
<summary>4. 既存顧客選択画面 (情報追記)</summary>

![既存顧客選択画面](https://github.com/user-attachments/assets/a7165937-27f1-4162-aa7f-383879587bb8)

メイン画面の「情報追記」から遷移します。問診票を記入したいお客様を一覧から選択します。フリガナやIDで検索することも可能です。

</details>

<details>
<summary>5. カルテ画面 (問診票入力)</summary>

![カルテ画面](https://github.com/user-attachments/assets/8e90db63-42b9-49b8-94dc-f0b668c68635)

お客様の症状などをヒアリングしながら入力します。すべての必須項目を入力すると「次の画面へ」ボタンが押せるようになります。

</details>

<details>
<summary>6. カルテ画面2 (免責事項)</summary>

![カルテ画面2](https://github.com/user-attachments/assets/52180b30-9687-4c13-9457-a82646d143ea)

免責事項を確認後、同意のチェックボックスにチェックを入れると「確認画面へ」ボタンが押せるようになります。

</details>

<details>
<summary>7. 顧客情報確認用選択画面 (情報を見る)</summary>

![顧客情報確認用選択画面](https://github.com/user-attachments/assets/44f64aa6-17cc-4259-b25a-7912970d7cc1)

メイン画面の「情報を見る」から遷移します。登録されているお客様の一覧が表示されます。確認したいお客様を選択すると、登録情報確認画面へ遷移します。

</details>

<details>
<summary>8. 登録情報確認画面</summary>

![登録情報確認画面](https://github.com/user-attachments/assets/2117cb16-3917-4788-b985-90a96b7259fc)

選択したお客様の登録情報が表示されます。

- **再登録**: 情報を修正するための画面（登録内容修正画面）へ遷移します。
- **戻る**: 前の選択画面に戻ります。
- **問診票**: このお客様の過去の問診票一覧画面へ遷移します。

</details>

<details>
<summary>9. 登録内容修正画面</summary>

![登録内容修正画面](https://github.com/user-attachments/assets/dda9827d-167a-40d0-8407-186c33821911)  
既存の顧客情報が入力された状態で表示されます。内容を修正し、「確認画面へ」ボタンを押すと、更新内容確認画面へ遷移します。

</details>


<details>
<summary>10. 更新内容確認画面</summary>

![更新内容確認画面](https://github.com/user-attachments/assets/e171e2dd-6c65-4853-991b-cf50e1178514)  
修正した登録内容が表示されます。修正内容が正されていることを確認し、「更新する」ボタンを押すと、内容が更新されメイン画面へ遷移します。

</details>

<details>
<summary>11. カルテ一覧画面</summary>

![カルテ一覧画面](https://github.com/user-attachments/assets/d7e92ffe-99de-4d63-a6c3-c02e673d748f)  
特定のお客様の、過去の問診票が日付順に一覧表示されます。確認したい問診票をタップすると、その詳細画面へ遷移します。

</details>

<details>
<summary>12. カルテ内容確認画面</summary>

![カルテ内容確認画面](https://github.com/user-attachments/assets/5d3e0cad-4c6e-4d59-8d7f-ff95fe90ccd4) 
選択した問診票に前回登録した内容を確認できます。

- **問診票を修正する**: 内容を修正するための画面(カルテ再入力画面)へ遷移します。

</details>

<details>
<summary>13. カルテ再入力画面</summary>

![カルテ再入力画面](https://github.com/user-attachments/assets/63e6e0a8-a60a-418f-b99e-c896bc71edfe)  
選択した問診票の詳細について書いてあります。前回入力された項目が入力された状態で表示されます。

- **次の画面へ**: 内容を修正するための画面（カルテ2再入力画面）へ遷移します。

</details>

<details>
<summary>14. カルテ2再入力画面</summary>

![カルテ2再入力画面](https://github.com/user-attachments/assets/d13a23a1-de80-4b40-914d-d257508dcb7e)  
前画面での修正内容を確認し、同意にチェックを入れると、問診内容が更新されます。

</details>

<details>
<summary>15. Google Drive 保存・取得</summary>

![保存・取得ボタン後](https://github.com/user-attachments/assets/9c1b1abf-cf3d-483c-bd36-c0006e941dc1)

「GoogleDriveに保存」または「GoogleDriveから取得」ボタンを押すと、Googleアカウントの選択画面が表示されます。アカウントを選択して認証を行うことで、データのバックアップ・復元が実行されます。

</details>


## こだわった点
このプロジェクトでは、**いかにお金をかけずにDX化を実現するか**にこだわりました。
- **低コストなハードウェア**: 安価なAndroidタブレットで運用できるよう設計しました。
- **サーバーレスなデータ管理**: Google Drive APIを利用することで、有料の外部データベースやサーバーを契約せずにデータバックアップを実現しました。
- **直感的なUI**: 父がPC操作に不慣れなため、直感的に使えるボタン名やレイアウトを意識しました。
- **継続的な開発**: 夏休みの1ヶ月間、毎日開発を続けることで、継続的な開発スキルを証明しました。

## ER図・画面遷移図
### ER図
![ER図](https://github.com/user-attachments/assets/d8e22406-de18-49ef-aa1c-afcb0cb7643c)

### 画面遷移図
Mermaidを使った画面遷移図
```mermaid
graph TD;
    subgraph "新規登録フロー"
        MainActivity["メイン画面"] -- "新規登録" --> NewCustomer["新規登録画面"];
        NewCustomer -- "確認画面へ" --> NewCustomerConfirm["新規登録/確認画面"];
        NewCustomerConfirm -- "登録" --> CompleteDialog["登録完了ダイアログ"];
        CompleteDialog -- "メイン画面へ" --> MainActivity;
    end

    subgraph "問診票フロー"
        MainActivity -- "情報追記" --> LoginCustomer["既存顧客選択画面"];
        LoginCustomer -- "顧客を選択" --> Questionnaire["カルテ画面1"];
        CompleteDialog -- "カルテへ" --> Questionnaire;
        Questionnaire -- "次の画面へ" --> Questionnaire2["カルテ画面2"];
        Questionnaire2 -- "DBへ保存" --> MainActivity;
    end

    subgraph "情報確認・修正フロー"
        MainActivity -- "情報を見る" --> InfomationCheckLogin["顧客情報確認用 選択画面"];
        InfomationCheckLogin -- "顧客を選択" --> CustomerInfoConfirm["登録情報確認画面"];
        CustomerInfoConfirm -- "再登録" --> ReEnterCustomer["登録内容修正画面"];
        ReEnterCustomer -- "確認画面へ" --> CustomerConfirm["更新確認画面"];
        CustomerConfirm -- "DBへ更新" --> MainActivity;
        CustomerInfoConfirm -- "問診票" --> QuestionConfirmLogin["問診票一覧画面"];
        QuestionConfirmLogin -- "問診票を選択" --> QuestionnaireConfirm["問診票確認画面"];
        QuestionnaireConfirm -- "問診票を修正する" --> ReQuestionnaire["カルテ再入力画面1"];
        ReQuestionnaire -- "次の画面へ" --> ReQuestionnaire2["カルテ再入力画面2"];
        ReQuestionnaire2 -- "DBへ更新" --> MainActivity;
    end
```

## いただいたFBと今後の展望
実務でご活躍されているAndroidエンジニアの方から頂いたフィードバックを元に、以下の項目を修正・改善していきます  
- [x] **READMEの作成** :ReadMEをしっかり作らないと説明書だけでは作成過程などをしっかり見てもらえない可能性がある。  
- [x] **ファイル構造の見やすさ** :ファイル構造が画面ごとにまとまっていないため、この作品を見た人が理解しずらい、見るときに見づらいなどの可能性がある。  
- [ ] **Jetpack Composeへの対応** :授業で習ったやり方でこのアプリ開発を行っていたが、授業で習った内容が古い内容であり、現在の主流となっているUI手法として、Jetpack Composeが主流であるため、今風に変える必要がある。  (先に親に提供するため親が使用し始めて安定してきたタイミングで変更しようと考えています。)  
- [ ] **今後の学習課題**: 今回はAndroid View Systemで開発しましたが、現在の主流であるJetpack ComposeでのUI構築スキルや、最新の技術、主流を習得するため、簡単なアプリ作成などを通して学習を進めます。  現在練習中のGitHubリポジトリ(https://github.com/PANAKA-4696/PracticeToDoApp)


## 製作者
田中 潮音 (Shion Tanaka)

---

## 詳細資料

このプロジェクトに関するより詳細な情報（画面ごとの詳しい使い方、API設定の背景など）は、以下の説明書にまとめてあります。
情報の追記頻度が遅めのためverが違うことがありますご了承ください。

[➡️ Questionnaire説明書はこちら](https://docs.google.com/presentation/d/1xqYt9AdNYIFBNOu1SfC3jbIsnh-Z6TQV/edit?usp=drive_link&ouid=100060518070943536625&rtpof=true&sd=true)

Jetpack Comporseを使うためのToDoアプリを作成しました。今後このToDoアプリでの開発を活かしてこのアプリをアップデートしていこうと考えておりますので見ていただけると良いと思います。  
[➡️ Jetpack Comporseを使ったToDoアプリはこちら](https://github.com/PANAKA-4696/PracticeToDoApp)
