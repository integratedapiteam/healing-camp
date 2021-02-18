package kr.co.hksoft.healingcamp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kr.co.hksoft.healingcamp.R
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SignInActivity : AppCompatActivity() {

    lateinit var mOAuthLoginInstance: OAuthLogin
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        Timber.plant(Timber.DebugTree())

        mContext = this

        val keyHash = Utility.getKeyHash(mContext)
        Timber.v(keyHash)
    }

    override fun onResume() {
        super.onResume()

        val btnKakao = findViewById<Button>(R.id.btnKakao)
        val btnNaver = findViewById<Button>(R.id.btnNaver)

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                //Login Fail
                Timber.e(error.localizedMessage)
            }
            else if (token != null) {
                //Login Success
                val intent: Intent = Intent(this@SignInActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        btnNaver.setOnClickListener {
            mOAuthLoginInstance = OAuthLogin.getInstance()
            mOAuthLoginInstance.init(mContext,
                    resources.getString(R.string.naver_client_id),
                    resources.getString(R.string.naver_client_secret),
                    resources.getString(R.string.naver_client_name))
            mOAuthLoginInstance.startOauthLoginActivity(this, mOAuthLoginHandler)
        }

        btnKakao.setOnClickListener {
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@SignInActivity)) {
                    loginWithKakaoTalk(this@SignInActivity, callback = callback)
                } else {
                    loginWithKakaoAccount(this@SignInActivity, callback = callback)
                }
            }
        }
    }

    val mOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
        @SuppressLint("HandlerLeak")
        override fun run(success: Boolean) {
            when (success) {
                true -> {
                    val accessToken: String = mOAuthLoginInstance.getAccessToken(mContext)
                    val refreshToken: String = mOAuthLoginInstance.getRefreshToken(mContext)
                    Timber.d("accessToken: %s", accessToken)
                    Timber.d("refreshToken: %s", refreshToken)
                    val intent: Intent = Intent(mContext, MainActivity::class.java)
                    startActivity(intent)
                }
                false -> {
                    val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).code
                    Timber.e(errorCode)
                    val intent: Intent = Intent(mContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    public fun kakaoSignIn() {

    }
}