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
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kr.co.hksoft.healingcamp.R
import kr.co.hksoft.healingcamp.httpResponse.NaverResponse
import kr.co.hksoft.healingcamp.retrofit.NaverAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Timber.e("사용자 정보 요청 실패 ${error}")
                    }
                    else if (user != null) {
                        Timber.e("사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        val intent: Intent = Intent(this@SignInActivity, RegisterActivity::class.java)
                        intent.putExtra("id", user.id)
                        intent.putExtra("email", user.kakaoAccount?.email)
                        intent.putExtra("nickname", user.kakaoAccount?.profile?.nickname)
                        intent.putExtra("profile", user.kakaoAccount?.profile?.thumbnailImageUrl)
                        startActivity(intent)
                    }
                }
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
                    val retrofit = Retrofit.Builder()
                                   .baseUrl("https://openapi.naver.com/")
                                   .addConverterFactory(GsonConverterFactory.create())
                                   .build()

                    val api = retrofit.create(NaverAPI::class.java)
                    val getInfoNaver = api.getUserInfo(resources.getString(R.string.naver_client_id),
                                                       resources.getString(R.string.naver_client_secret),
                                                       accessToken)

                    getInfoNaver.enqueue(object: retrofit2.Callback<NaverResponse> {
                        override fun onResponse(call: Call<NaverResponse>, response: Response<NaverResponse>) {
                            TODO("Not yet implemented")
                            Timber.d("성공: ${response.raw()}")
                            val intent: Intent = Intent(mContext, RegisterActivity::class.java)
                            startActivity(intent)
                        }

                        override fun onFailure(call: Call<NaverResponse>, t: Throwable) {
                            Timber.e("실패: $t")
                        }

                    })
                }
                false -> {
                    val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).code
                    Timber.e(errorCode)
                }
            }
        }
    }

    public fun kakaoSignIn() {

    }
}