package com.example.kryptokagyan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.
        findFragmentById(R.id.fragmentContainerView)

        val navController = navHostFragment!!.findNavController()
        val popupMenu= PopupMenu(this,null)
        val bottomBar:SmoothBottomBar = findViewById(R.id.bottomBar)
        popupMenu.inflate(R.menu.bottom_menu)
        bottomBar.setupWithNavController(
            popupMenu.menu,navController
        )

    }
}