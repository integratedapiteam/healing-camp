package kr.co.hksoft.healingcamp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.hksoft.healingcamp.R
import kr.co.hksoft.healingcamp.adapter.CommunityAdapter
import kr.co.hksoft.healingcamp.adapter.RoomAdapter
import kr.co.hksoft.healingcamp.models.Communities
import kr.co.hksoft.healingcamp.models.Rooms
import java.util.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val roomList = arrayListOf<Rooms>(
                Rooms("마음의 힐링캠프", Date(), "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg", "힐링캠프에서 놀다 가유"),
                Rooms("마음의 힐링캠프", Date(), "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg", "힐링캠프에서 놀다 가유"),
                Rooms("마음의 힐링캠프", Date(), "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg", "힐링캠프에서 놀다 가유"),
                Rooms("마음의 힐링캠프", Date(), "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg", "힐링캠프에서 놀다 가유")
        )

        val communityList = arrayListOf<Communities>(
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg"),
                Communities("마음의 힐링캠프", "https://i.pinimg.com/originals/e2/0e/11/e20e116c0d645047f2570263611e1970.jpg")
        )

        val rvMainRooms = view.findViewById<RecyclerView>(R.id.rvMainRooms)
        rvMainRooms.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvMainRooms.adapter = RoomAdapter(roomList)

        val rvCommunities = view.findViewById<RecyclerView>(R.id.rvCommunities)
        rvCommunities.layoutManager = GridLayoutManager(activity, 2)
        rvCommunities.adapter = CommunityAdapter(communityList)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        return view
    }
}