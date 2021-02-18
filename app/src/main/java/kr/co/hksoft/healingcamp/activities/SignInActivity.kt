package kr.co.hksoft.healingcamp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.Utility
import com.kakao.util.helper.Utility.getPackageInfo
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kr.co.hksoft.healingcamp.R
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SignInActivity : AppCompatActivity() {

    private var callback: SessionCallback = SessionCallback()
    lateinit var mOAuthLoginInstance: OAuthLogin
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        Timber.plant(Timber.DebugTree())

        mContext = this

        Timber.d("HashKey: " + getHashKey(mContext))
    }

    override fun onResume() {
        super.onResume()

        val btnKakao = findViewById<Button>(R.id.btnKakao)
        val btnNaver = findViewById<Button>(R.id.btnNaver)

        btnNaver.setOnClickListener {
            mOAuthLoginInstance = OAuthLogin.getInstance()
            mOAuthLoginInstance.init(mContext,
                    resources.getString(R.string.naver_client_id),
                    resources.getString(R.string.naver_client_secret),
                    resources.getString(R.string.naver_client_name))
            mOAuthLoginInstance.startOauthLoginActivity(this, mOAuthLoginHandler)
        }

        btnKakao.setOnClickListener {
            Session.getCurrentSession().addCallback(callback)
            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this)
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
                }
                false -> {
                    val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).code
                    Timber.e(errorCode)
                }
            }
        }
    }

    fun getHashKey(context: Context): String? {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                val packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNING_CERTIFICATES)
                val signatures = packageInfo.signingInfo.apkContentsSigners
                val md = MessageDigest.getInstance("SHA")
                for (signature in signatures) {
                    md.update(signature.toByteArray())
                    return String(Base64.encode(md.digest(), Base64.NO_WRAP))
                }
            } else {
                val packageInfo =
                        Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

                for (signature in packageInfo!!.signatures) {
                    try {
                        val md = MessageDigest.getInstance("SHA")
                        md.update(signature.toByteArray())
                        return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                    } catch (e: NoSuchAlgorithmException) {
                        // ERROR LOG
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }

    private class SessionCallback : ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {
            Timber.e("Session Call back :: onSessionOpenFailed: ${exception?.message}")
        }

        override fun onSessionOpened() {
            UserManagement.getInstance().me(object : MeV2ResponseCallback() {

                override fun onFailure(errorResult: ErrorResult?) {
                    Timber.d("Session Call back :: on failed ${errorResult?.errorMessage}")
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    Timber.e("Session Call back :: onSessionClosed ${errorResult?.errorMessage}")

                }

                override fun onSuccess(result: MeV2Response?) {
                    checkNotNull(result) { "session response null" }
                    // register or login
                }

            })
        }
    }
}