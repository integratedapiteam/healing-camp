package kr.co.hksoft.healingcamp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.hksoft.healingcamp.R
import kr.co.hksoft.healingcamp.adapter.RoomAdapter
import kr.co.hksoft.healingcamp.models.Rooms
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val roomList = arrayListOf<Rooms>(
                Rooms("마음의 힐링캠프", Date(), "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg", "힐링캠프에서 놀다 가유"),
                Rooms("마음의 힐링캠프", Date(), "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg", "힐링캠프에서 놀다 가유"),
                Rooms("마음의 힐링캠프", Date(), "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg", "힐링캠프에서 놀다 가유"),
                Rooms("마음의 힐링캠프", Date(), "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg", "힐링캠프에서 놀다 가유")
        )

        val rvMainRooms = findViewById<RecyclerView>(R.id.rvMainRooms)
        rvMainRooms.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvMainRooms.adapter = RoomAdapter(roomList)
    }
}