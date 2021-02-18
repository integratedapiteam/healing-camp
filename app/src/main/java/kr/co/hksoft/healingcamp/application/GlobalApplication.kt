package kr.co.hksoft.healingcamp.application

import android.app.Application
import com.kakao.auth.KakaoSDK
import kr.co.hksoft.healingcamp.util.KakaoSDKAdapter

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        KakaoSDK.init(KakaoSDKAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance)
        return instance!!
    }

    companion object {
        var instance: GlobalApplication? = null
    }
}