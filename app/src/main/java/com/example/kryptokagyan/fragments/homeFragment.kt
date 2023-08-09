package com.example.kryptokagyan.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup

import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.kryptokagyan.adapters.TopLossGainPagerAdapter
import com.example.kryptokagyan.adapters.TopMarketAdapter
import com.example.kryptokagyan.api.apiInterface
import com.example.kryptokagyan.api.apiUtilities
import com.example.kryptokagyan.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class homeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ?{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        getTopCurrencyList()
        setTabLayout()
        return binding.root
    }



    private fun setTabLayout() {
        val adapter = TopLossGainPagerAdapter(this)

        binding.contentViewPager.adapter = adapter

      binding.contentViewPager.registerOnPageChangeCallback(
          object:ViewPager2.OnPageChangeCallback(){
              override fun onPageSelected(position: Int) {
                  super.onPageSelected(position)
                  if(position ==0){
                      binding.topGainIndicator.visibility=VISIBLE
                      binding.topLoseIndicator.visibility=GONE

                  }else{
                      binding.topGainIndicator.visibility= GONE
                      binding.topLoseIndicator.visibility= VISIBLE
                  }
              }
          }
      )

        TabLayoutMediator(binding.tabLayout,
            binding.contentViewPager) { tab, position ->
            val title = if (position == 0) {
                "Top Gainers"
            } else {
                "Top Losers"
            }
            tab.text = title
        }.attach()



    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = apiUtilities.getInstance()
                .create(apiInterface::class.java)
                .getMarketData()

            withContext(Dispatchers.Main) {
                binding.topCurrencyRecyclerView.adapter =TopMarketAdapter(requireContext(),
                res.body()!!.data.cryptoCurrencyList)

            }
            Log.d("aditya","get top currency list ${res.body()!!.data.cryptoCurrencyList}")
        }
    }
}




