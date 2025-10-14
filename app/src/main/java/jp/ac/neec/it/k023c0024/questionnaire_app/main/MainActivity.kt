package jp.ac.neec.it.k023c0024.questionnaire_app.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import kotlinx.coroutines.withContext
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.http.FileContent
import com.google.api.services.drive.model.File
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper
import jp.ac.neec.it.k023c0024.questionnaire_app.InfomationCheckLogin
import jp.ac.neec.it.k023c0024.questionnaire_app.LoginCustomer
import jp.ac.neec.it.k023c0024.questionnaire_app.customer.NewCustomer
import jp.ac.neec.it.k023c0024.questionnaire_app.R

class MainActivity : AppCompatActivity() {
    private val _helper = DatabaseHelper(this@MainActivity)

    private lateinit var googleSignInClient: GoogleSignInClient

    //どのボタンが押されたかを記憶するための変数
    private var postSignInAction: ((GoogleSignInAccount) -> Unit)? = null

    // サインイン画面からの結果を受け取るためのランチャー
    private val signInResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.result
                // 記憶しておいたアクション（バックアップ or 復元）を実行する
                postSignInAction?.invoke(account)
            } catch (e: Exception) {
                Log.w("SIGN_IN_ERROR", "サインインに失敗しました", e)
            }
        } else {
            Log.w("SIGN_IN_CANCEL", "サインインがキャンセルされました")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt_new = findViewById<Button>(R.id.btNew)
        val bt_member = findViewById<Button>(R.id.btMember)
        val bt_check = findViewById<Button>(R.id.btCheck)
        val bt_read_google_drive = findViewById<Button>(R.id.btReadGoogleDrive)
        val bt_write_google_drive = findViewById<Button>(R.id.btWriteGoogleDrive)

        bt_new.setOnClickListener(ClickNewCustomerListener())
        bt_member.setOnClickListener(ClickLoginCustomerListener())
        bt_check.setOnClickListener(ClickCheckCustomerListener())
        bt_read_google_drive.setOnClickListener(ClickReadGoogleDriveListener())
        bt_write_google_drive.setOnClickListener(ClickWriteGoogleDriveListener())

        // Googleサインインのオプションを設定
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(DriveScopes.DRIVE_APPDATA))//DRIVE_APPDATA：「アプリのプライベートなデータフォルダ（appDataFolder）への全アクセス」を許可する権限
            .build()

        // サインインクライアントを作成
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private inner class ClickNewCustomerListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val NewCustomer = Intent(this@MainActivity, NewCustomer::class.java)
            startActivity(NewCustomer)
        }
    }

    private inner class ClickLoginCustomerListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val ClickLoginCustomerListener = Intent(this@MainActivity, LoginCustomer::class.java)
            startActivity(ClickLoginCustomerListener)
        }
    }

    private inner class ClickCheckCustomerListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val ClickCheckCustomerListener = Intent(this@MainActivity, InfomationCheckLogin::class.java)
            startActivity(ClickCheckCustomerListener)
        }
    }

    // Readボタンの処理
    private inner class ClickReadGoogleDriveListener : View.OnClickListener {
        override fun onClick(v: View?) {
            // ログイン後のアクションとして「復元処理」を設定
            postSignInAction = { account -> handleSignInSuccessForRestore(account) }

            val account = GoogleSignIn.getLastSignedInAccount(this@MainActivity)
            if (account == null) {
                signIn() // 未ログインならサインイン開始
            } else {
                // ログイン済みなら直接アクションを実行
                postSignInAction?.invoke(account)
            }
        }
    }

    // Writeボタンの処理
    private inner class ClickWriteGoogleDriveListener : View.OnClickListener {
        override fun onClick(v: View?) {
            // ログイン後のアクションとして「バックアップ処理」を設定
            postSignInAction = { account -> handleSignInSuccessForBackup(account) }

            val account = GoogleSignIn.getLastSignedInAccount(this@MainActivity)
            if (account == null) {
                signIn() // 未ログインならサインイン開始
            } else {
                // ログイン済みなら直接アクションを実行
                postSignInAction?.invoke(account)
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInResultLauncher.launch(signInIntent)
    }
    //バックアップのためのサインイン成功後処理
    private fun handleSignInSuccessForBackup(account: GoogleSignInAccount) {
        Log.d("MY_APP_TRACE", "3. Driveサービスを作成します。")
        // Drive APIを操作するための認証情報を作成
        val credential = GoogleAccountCredential.usingOAuth2(
            this,
            setOf(DriveScopes.DRIVE_APPDATA)//正しい権限に修正。
        )
        credential.selectedAccount = account.account

        // Driveサービスクライアントを構築
        val driveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory(),
            credential
        )
            .setApplicationName("Questionnaire_App") // 自分のアプリ名を設定
            .build()

        // バックアップ処理を呼び出す
        backupDatabaseToDrive(driveService)
    }

    //データベースの内容をGoogle Driveにバックアップする
    private fun backupDatabaseToDrive(driveService: Drive) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("MY_APP_TRACE", "4. バックアップ処理を開始します（非同期）。")
            try {
                // DatabaseHelperからDB内容を文字列として取得
                val dbDataAsString = _helper.exportToString()
                if (dbDataAsString.isBlank()) {
                    //データベースの存在を確認。
                    withContext(Dispatchers.Main) { Toast.makeText(this@MainActivity, "バックアップするデータがありません", Toast.LENGTH_SHORT).show() }
                    Log.d("BACKUP_INFO", "バックアップするデータがありません。")
                    return@launch
                }

                // 取得したデータを一時ファイルに書き込む
                val tempFile = java.io.File(cacheDir, "db_backup.json")
                tempFile.writeText(dbDataAsString)


                //より信頼度の高いものにするための変更
//                // Driveにアップロードするファイルのメタデータ（情報）を作成
//                val fileMetadata = File().apply {
//                    name = "QuestionnaireBackup.json" // Drive上でのファイル名
//                    parents = listOf("appDataFolder")
//                }
//
//                // アップロードするファイル本体のコンテンツを作成
//                val mediaContent = FileContent("application/json", tempFile)

                // 1. 既存のバックアップファイルを検索
                val searchResult = driveService.files().list()
                    .setSpaces("appDataFolder")
                    .setQ("name = 'QuestionnaireBackup.json'")
                    .setFields("files(id, name)")
                    .execute()

                val existingFile = searchResult.files.firstOrNull()

                //より信頼度の高いものにするための変更
//                if (existingFile == null) {
//                    // 2a. ファイルが存在しない場合：新規作成
//                    Log.d("MY_APP_TRACE", "5a. ファイルが見つからないため、新規作成します。")
//                    val fileMetadata = File().apply {
//                        name = "QuestionnaireBackup.json"
//                        parents = listOf("appDataFolder")
//                    }
//                    driveService.files().create(fileMetadata, mediaContent).execute()
//                } else {
//                    // 2b. ファイルが存在する場合：更新
//                    Log.d("MY_APP_TRACE", "5b. 既存ファイル(${existingFile.id})を更新します。")
//                    driveService.files().update(existingFile.id, null, mediaContent).execute()
//                }

                //ファイルが見つかった際削除
                searchResult.files.firstOrNull()?.id?.let { fileId ->
                    Log.d("BACKUP_INFO", "古いファイル($fileId)を削除します。")
                    driveService.files().delete(fileId).execute()
                }

                //新しいファイルを作成。(ある場合は新しいファイルとして作成。)
                val fileMetadata = File().apply {
                    name = "QuestionnaireBackup.json"
                    parents = listOf("appDataFolder")
                }
                val mediaContent = FileContent("application/json", tempFile)
                Log.d("BACKUP_INFO", "新しいファイルを作成します。")
                driveService.files().create(fileMetadata, mediaContent).execute()


                withContext(Dispatchers.Main) {
                    Log.d("BACKUP_SUCCESS", "データベースのバックアップに成功しました。")
                    // 成功をToast表示
                    Toast.makeText(this@MainActivity, "バックアップに成功しました", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("BACKUP_ERROR", "バックアップに失敗しました。", e)
                    // バックアップ失敗時に失敗した旨を表示
                    Toast.makeText(this@MainActivity, "バックアップ失敗: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //復元のためのサインイン成功後処理
    private fun handleSignInSuccessForRestore(account: GoogleSignInAccount) {
        val credential = GoogleAccountCredential.usingOAuth2(
            this,
            setOf(DriveScopes.DRIVE_APPDATA)//正しい権限に修正
        )
        credential.selectedAccount = account.account

        val driveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory(),
            credential
        )
            .setApplicationName("Questionnaire_App")
            .build()

        restoreDatabaseFromDrive(driveService)
    }

    //Google Driveからデータベースの内容を復元する
    private fun restoreDatabaseFromDrive(driveService: Drive) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // バックアップファイルを検索
                val response = driveService.files().list()
                    .setSpaces("appDataFolder")
                    .setQ("name = 'QuestionnaireBackup.json'")
                    .setFields("files(id, name)")
                    .execute()

                val backupFile = response.files.firstOrNull()
                if (backupFile == null) {
                    withContext(Dispatchers.Main) {
                        Log.d("RESTORE_INFO", "バックアップファイルが見つかりませんでした。")
                    }
                    return@launch
                }

                // ファイルの内容を読み込む
                val inputStream = driveService.files().get(backupFile.id).executeMediaAsInputStream()
                val fileContent = inputStream.bufferedReader().use { it.readText() }

                // 読み込んだ内容でDBを上書き
                _helper.importFromString(fileContent)

                withContext(Dispatchers.Main) {
                    Log.d("RESTORE_SUCCESS", "データベースの取得に成功しました。")
                    Toast.makeText(this@MainActivity, "取得に成功しました", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("RESTORE_ERROR", "取得失敗しました。", e)
                    //バックアップ取得失敗時に失敗した旨を表示
                    Toast.makeText(this@MainActivity, "取得失敗: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
