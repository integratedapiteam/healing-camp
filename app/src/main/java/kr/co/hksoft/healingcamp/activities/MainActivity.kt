package kr.co.hksoft.healingcamp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.co.hksoft.healingcamp.R
import kr.co.hksoft.healingcamp.adapter.RoomAdapter
import kr.co.hksoft.healingcamp.fragments.HomeFragment
import kr.co.hksoft.healingcamp.models.Rooms
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener =  BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.nav_home -> {
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment())

        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.divFragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}