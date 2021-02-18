package kr.co.hksoft.healingcamp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.hksoft.healingcamp.R
import kr.co.hksoft.healingcamp.models.Rooms
import java.text.DateFormat
import java.text.SimpleDateFormat

class RoomViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val tvRoomCreatedAt = v.findViewById<TextView>(R.id.tvRoomCreatedAt)
    val imgRoomProfile = v.findViewById<ImageView>(R.id.imgRoomProfile)
    val tvRoomDescription = v.findViewById<TextView>(R.id.tvRoomDescription)
    val tvRoomName = v.findViewById<TextView>(R.id.tvRoomName)
}

class RoomAdapter(val RoomList:ArrayList<Rooms>): RecyclerView.Adapter<RoomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.room_item, parent, false)
        return RoomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return RoomList.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.tvRoomName.text = RoomList.get(position).roomName
        holder.tvRoomDescription.text = RoomList.get(position).roomDescription
        holder.tvRoomCreatedAt.text = SimpleDateFormat().format(RoomList.get(position).createdAt)

        Glide.with(holder.itemView)
                .load(RoomList.get(position).roomProfile)
                .circleCrop()
                .into(holder.imgRoomProfile)
    }

}