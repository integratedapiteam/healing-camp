package kr.co.hksoft.healingcamp.httpResponse

data class NaverResponse (
    var resultCode: String = "",
    var message: String = "",
    var response: Response
)

data class Response (
    var id: String = "",
    var nickname: String = "",
    var name: String = "",
    var email: String = "",
    var gender: String = "",
    var age: String = "",
    var birthday: String = "",
    var profile_image: String = ""
)