package kr.co.hksoft.healingcamp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.hksoft.healingcamp.R
import kr.co.hksoft.healingcamp.models.Communities

class CommunityViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val imgCommunityProfile = v.findViewById<ImageView>(R.id.imgCommunityProfile)
    val tvCommunityName = v.findViewById<TextView>(R.id.tvCommunityName)
}

class CommunityAdapter(val CommunityList:ArrayList<Communities>): RecyclerView.Adapter<CommunityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.community_item, parent, false)
        return CommunityViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return CommunityList.size
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.tvCommunityName.text = CommunityList.get(position).communityName

        Glide.with(holder.itemView)
                .load(CommunityList.get(position).communityProfile)
                .circleCrop()
                .into(holder.imgCommunityProfile)
    }
}