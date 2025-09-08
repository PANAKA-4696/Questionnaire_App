package jp.ac.neec.it.k023c0024.questionnaire_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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

class MainActivity : AppCompatActivity() {
    val _helper = DatabaseHelper(this@MainActivity)

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
            .requestScopes(Scope(DriveScopes.DRIVE_FILE))
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
            setOf(DriveScopes.DRIVE_FILE)
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
                    Log.d("BACKUP_INFO", "バックアップするデータがありません。")
                    return@launch
                }

                // 取得したデータを一時ファイルに書き込む
                val tempFile = java.io.File(cacheDir, "db_backup.json")
                tempFile.writeText(dbDataAsString)

                // Driveにアップロードするファイルのメタデータ（情報）を作成
                val fileMetadata = File().apply {
                    name = "QuestionnaireBackup.json" // Drive上でのファイル名
                    parents = listOf("appDataFolder")
                }

                // アップロードするファイル本体のコンテンツを作成
                val mediaContent = FileContent("application/json", tempFile)

                Log.d("MY_APP_TRACE", "5. Drive APIを呼び出してファイルをアップロードします。")
                // Drive APIを使ってファイルを新規作成（アップロード）
                driveService.files().create(fileMetadata, mediaContent).execute()

                withContext(Dispatchers.Main) {
                    Log.d("BACKUP_SUCCESS", "データベースのバックアップに成功しました。")
                    // ここでToastを表示するなど
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("BACKUP_ERROR", "バックアップに失敗しました。", e)
                    // ここでToastを表示するなど
                }
            }
        }
    }

    //復元のためのサインイン成功後処理
    private fun handleSignInSuccessForRestore(account: GoogleSignInAccount) {
        val credential = GoogleAccountCredential.usingOAuth2(
            this,
            setOf(DriveScopes.DRIVE_FILE)
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
                    Log.d("RESTORE_SUCCESS", "データベースの復元に成功しました。")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("RESTORE_ERROR", "復元に失敗しました。", e)
                }
            }
        }
    }
}
