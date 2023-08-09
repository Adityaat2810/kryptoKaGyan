package com.example.kryptokagyan.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kryptokagyan.fragments.top_gain_loss_fragment

class TopLossGainPagerAdapter(fragment: Fragment):
FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
      val fragment = top_gain_loss_fragment()
        val bundle = Bundle()
        bundle.putInt("position",position)
        fragment.arguments=bundle
        return  fragment

    }
}