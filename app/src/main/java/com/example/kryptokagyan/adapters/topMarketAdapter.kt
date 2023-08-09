package com.example.kryptokagyan.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.bumptech.glide.Glide
import com.example.kryptokagyan.R
import com.example.kryptokagyan.fragments.homeFragmentDirections
import com.example.kryptokagyan.fragments.marketFragmentDirections
import com.example.kryptokagyan.fragments.top_currency
import com.example.kryptokagyan.fragments.watchListFragmentDirections
import com.example.kryptokagyan.models.CryptoCurrency

class TopMarketAdapter(var context:Context,
val list:List<CryptoCurrency>): RecyclerView
.Adapter<TopMarketAdapter.TopMarketViewHolder>() {


    inner class TopMarketViewHolder(view: View):
        RecyclerView.ViewHolder(view){
        val topCurrencyNameTextView: TextView = itemView.findViewById(R.id.topCurrencyNameTextView)
        val topCurrencyImageView:ImageView=itemView.findViewById(R.id.topCurrencyImageView)
        val topCurrencyChangeTextView:TextView=itemView.
                findViewById(R.id.topCurrencyChangeTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        return TopMarketViewHolder(LayoutInflater.from(context).inflate(
            R.layout.top_currency_layout,
        parent,false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item =list[position]

       holder.topCurrencyNameTextView.text=item.name
      Glide.with(context).load(
          "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
      ).thumbnail(Glide.with(context).load(R.drawable.spinner))
          .into(holder.topCurrencyImageView)

        if(item.quotes[0].percentChange24h>0){
            holder.topCurrencyChangeTextView.setTextColor(context
                .resources.getColor(R.color.green))
            holder.topCurrencyChangeTextView.text="+${item.quotes[0].percentChange24h}%"
        }else{
            holder.topCurrencyChangeTextView.setTextColor(context
                .resources.getColor(R.color.red))
            holder.topCurrencyChangeTextView.text="-${item.quotes[0].percentChange24h}%"

        }




    }

}