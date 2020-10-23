package com.example.sodas

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sodas.models.Soda
import com.squareup.picasso.Picasso
import com.example.sodas.service.SodaServiceImpl

class SodaAdapter(var sodaList: ArrayList<Soda>, val context: Context) : RecyclerView.Adapter<SodaAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.soda_list_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(sodaList[position], context)
    }

    override fun getItemCount(): Int {
        return sodaList.size;
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sodaServiceImpl = SodaServiceImpl()
        fun bindView(b: Soda, context: Context){
            val url = "http://192.168.1.49:8080/img/soda-"
            val txt_format: TextView = itemView.findViewById(R.id.textViewQuantity)
            val txt_name: TextView = itemView.findViewById(R.id.textViewName)

            val img: ImageView = itemView.findViewById(R.id.imageViewBicycle)

            txt_name.text = b.productName
            txt_format.text = b.productQuantity.toString()

            val imageUrl = url +sodaServiceImpl.validateSodaForImage(b.productName) + ".png"
            Picasso.with(context).load(imageUrl).into(img);

            itemView.setOnClickListener {
                val intent = Intent(context, SodaDetailActivity::class.java)
                intent.putExtra("sodaId", b.id)
                intent.putExtra("state", "Showing")
                Log.v("hola caracola antes", b.id.toString())
                context.startActivity(intent)
            }
        }
    }
}