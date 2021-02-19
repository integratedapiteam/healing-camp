package kr.co.hksoft.healingcamp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kr.co.hksoft.healingcamp.R
import timber.log.Timber
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var id: String? = ""
    var email: String? = ""
    var nickname: String? = ""
    var phone: String? = ""
    var gender: String? = "male"

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Timber.plant(Timber.DebugTree())

        val rdgGender = findViewById<RadioGroup>(R.id.rdgGender)
        rdgGender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rdbMale) {
                gender = "male"
            } else if (checkedId == R.id.rdbFemale) {
                gender = "female"
            }
        }

        val edtNickname = findViewById<EditText>(R.id.edtNickname)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)

        id = intent.getStringExtra("id")
        email = intent.getStringExtra("email")
        nickname = intent.getStringExtra("nickname")
        phone = intent.getStringExtra("phone")

        Timber.d("id: %s", id)
        Timber.d("email: %s", email)
        Timber.d("nickname: %s", nickname)
        Timber.d("phone: %s", phone)

        edtEmail.setText(email)
        edtNickname.setText(nickname)
        edtPhone.setText(phone)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            email = edtEmail.text.toString()
            nickname = edtNickname.text.toString()
            phone = edtPhone.text.toString()

            if (email == "" || email == null || nickname == "" || nickname == null || phone == "" || phone == null) {
                Toast.makeText(this, "빈 칸을 모두 채워주세요.", Toast.LENGTH_LONG).show()
            } else {
                val user = hashMapOf(
                        "id" to id,
                        "email" to email,
                        "nickname" to nickname,
                        "phone" to phone,
                        "gender" to gender,
                        "createdAt" to Date()
                )

                db.collection("users")
                        .add(user)
                        .addOnSuccessListener {
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            Timber.e(it.toString())
                            Toast.makeText(this@RegisterActivity, it.toString(), Toast.LENGTH_LONG)
                        }
            }
        }
    }
}