package kr.co.hksoft.healingcamp.retrofit

import kr.co.hksoft.healingcamp.httpResponse.NaverResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NaverAPI {
    @GET("v1/nid/me")
    fun getUserInfo(
            @Header("X-Naver-Client-Id") clientId: String,
            @Header("X-Naver-Client-Secret") clientSecret: String,
            @Header("Authorization") accessToken: String
    ): Call<NaverResponse>
}