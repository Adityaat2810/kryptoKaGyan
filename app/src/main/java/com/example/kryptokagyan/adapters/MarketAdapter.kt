package com.example.kryptokagyan.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kryptokagyan.R
import com.example.kryptokagyan.databinding.CurrencyItemLayoutBinding
import com.example.kryptokagyan.fragments.homeFragmentDirections
import com.example.kryptokagyan.fragments.marketFragmentDirections
import com.example.kryptokagyan.fragments.watchListFragmentDirections
import com.example.kryptokagyan.models.CryptoCurrency

class MarketAdapter(var context: Context, var list: List<CryptoCurrency>, var type: String) :RecyclerView.
Adapter<MarketAdapter.MarketViewHolder> (){

    inner class MarketViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding = CurrencyItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MarketViewHolder {
        return MarketViewHolder(LayoutInflater.from(context).
        inflate(R.layout.
        currency_item_layout,parent,false))
    }

    fun upDateList(dataItem:List<CryptoCurrency>){
        list = dataItem
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
       return list.size
    }
    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]
        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol

        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${item.id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyImageView)

        Glide.with(context)
            .load("https://s2.coinmarketcap.com/generated/sparkline/web/7d/usd/${item.id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyChartImageView)

        if (item.quotes.isNotEmpty()) {
            holder.binding.currencyPriceTextView.text = item.quotes[0].price.toString()

            val percentChange24h = item.quotes[0].percentChange24h
            if (percentChange24h > 0) {
                holder.binding.currencyChangeTextView.setTextColor(
                    ContextCompat.getColor(context, R.color.green)
                )
                holder.binding.currencyChangeTextView.text = "+$percentChange24h%"
            } else {
                holder.binding.currencyChangeTextView.setTextColor(
                    ContextCompat.getColor(context, R.color.red)
                )
                holder.binding.currencyChangeTextView.text = "-$percentChange24h%"
            }
            holder.itemView.setOnClickListener {

                if(type =="home") {
                    findNavController(it).navigate(
                        homeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
                    )
                }else if(type=="market"){
                    findNavController(it).navigate(
                        marketFragmentDirections.actionMarketFragmentToDetailsFragment(item)
                    )
                }else{

                    findNavController(it).navigate(
                        watchListFragmentDirections.actionWatchListFragmentToDetailsFragment(item)
                    )

                }
            }


        }


    }





}