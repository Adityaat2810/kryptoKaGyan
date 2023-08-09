package com.example.kryptokagyan.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.kryptokagyan.R
import com.example.kryptokagyan.databinding.FragmentDetailsBinding
import com.example.kryptokagyan.models.CryptoCurrency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.NullPointerException


class DetailsFragment :Fragment(){

    lateinit var binding:FragmentDetailsBinding
    private val item:DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDetailsBinding.inflate(layoutInflater)
        val data:CryptoCurrency = item.data!!

        setUpDetails(data)

        loadChart(data)

        setButtonOnClickListener(data)

        addToWatchList(data)

        return binding.root
    }

    var watchList :ArrayList<String>?= null
    var watchListIsChecked = false

    private fun addToWatchList(data: CryptoCurrency){
        readData()
        watchListIsChecked=if (watchList!!.contains(data.symbol)){
           binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
            true
        }else{
            binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
            false
        }

        binding.addWatchlistButton.setOnClickListener{
            watchListIsChecked=
                if(!watchListIsChecked){
                    if(!watchList!!.contains(data.symbol)){
                        watchList!!.add(data.symbol)
                    }
                    storeData()
                    binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
                    true
                }else{
                    binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
                       watchList!!.remove(data.symbol)
                      storeData()
                    false
                }
        }

    }

    private fun storeData(){
        val sharedPrefrences=requireContext().
        getSharedPreferences("watchlist",Context.MODE_PRIVATE)
        val editor = sharedPrefrences.edit()
        val gson = Gson()
        val json = gson.toJson(watchList)
        editor.putString("watchlist",json)
        editor.apply()
    }

    private fun readData(){
        val sharedPrefrences = requireContext()
            .getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPrefrences.getString("watchlist",ArrayList<String>().toString())
        val type = object: TypeToken<ArrayList<String>>() {}.type
        watchList =gson.fromJson(json,type)

    }

    private fun setButtonOnClickListener(data: CryptoCurrency) {

        val oneMonth=binding.button
        val oneWeek=binding.button1
        val oneDay=binding.button2
        val fourHour=binding.button3
        val oneHour=binding.button4
        val fifteenMinutes=binding.button5

        val clickListener=View.OnClickListener {
            when(it.id){

                fifteenMinutes.id->loadChartData(it,"15",data, oneDay,oneMonth,oneWeek,fourHour,oneHour)
                oneHour.id->loadChartData(it,"1H",data, oneDay,oneMonth,oneWeek,fourHour,fifteenMinutes)
                fourHour.id->loadChartData(it,"4H",data, oneDay,oneMonth,oneWeek,fifteenMinutes,oneHour)
                oneDay.id->loadChartData(it,"D",data, fifteenMinutes,oneMonth,oneWeek,fourHour,oneHour)
                oneWeek.id->loadChartData(it,"W",data, oneDay,oneMonth,fifteenMinutes,fourHour,oneHour)
                oneMonth.id->loadChartData(it,"M",data, oneDay,fifteenMinutes,oneWeek,fourHour,oneHour)

            }
        }
        fifteenMinutes.setOnClickListener(clickListener)
        oneHour.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
        oneMonth.setOnClickListener(clickListener)


    }

    private fun loadChartData(
        it: View?,
        s: String,
        item: CryptoCurrency,
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton
    ) {

        diasbleBUtton(oneDay,oneMonth,oneWeek,fourHour,oneHour)
        it!!.setBackgroundResource(R.drawable.active_button)
        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingView_76d87&symbol=" + item.symbol
                .toString() + "USD&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"


        )

    }

    private fun diasbleBUtton(oneDay: AppCompatButton, oneMonth: AppCompatButton, oneWeek: AppCompatButton, fourHour: AppCompatButton, oneHour: AppCompatButton) {
        oneDay.background=null
        oneMonth.background=null
        oneWeek.background=null
        fourHour.background=null
        oneHour.background=null

    }

    private fun loadChart(data: CryptoCurrency) {

        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
        binding.detaillChartWebView.loadUrl(

            "https://s.tradingview.com/widgetembed/?frameElementId=tradingView_76d87&symbol=" + data.symbol
                .toString() + "USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )


    }

    private fun setUpDetails(data:CryptoCurrency) {
        binding.detailSymbolTextView.text=data.symbol

        Glide.with(requireContext())
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${data.id}.png")
            .thumbnail(Glide.with(requireContext()).load(R.drawable.spinner))
            .into(binding.detailImageView)


        binding.detailPriceTextView.text= data.quotes[0].price.toString()

            val percentChange24h = data.quotes[0].percentChange24h
            if (percentChange24h > 0) {
                binding.detailChangeTextView.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.green)
                )

                binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)

                binding.detailChangeTextView.text = "+$percentChange24h%"
            } else {
                binding.detailChangeTextView.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.red)
                )
                binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)

                binding.detailChangeTextView.text = "-$percentChange24h%"
            }


    }


}