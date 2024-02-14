package com.erayyilmaz.randevumm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erayyilmaz.randevumm.DateBerberActivity
import com.erayyilmaz.randevumm.databinding.RecyclerRowBinding
import com.erayyilmaz.randevumm.model.berber
import com.squareup.picasso.Picasso

class BerberRecyclerAdapter(val berberList: ArrayList<berber>) : RecyclerView.Adapter<BerberRecyclerAdapter.PostHolder>() {
    var clickedItemID = ""

    class PostHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return berberList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val currentBerber = berberList[position]

        holder.binding.recyclerBerberName.text= currentBerber.name
        holder.binding.recyclerBerberAddress.text = currentBerber.address
        holder.binding.recyclerBerberPhone.text = currentBerber.phone
        Picasso.get().load(currentBerber.imageurl).into(holder.binding.recyclerBerberImage)

        holder.binding.recyclerBerberImage.setOnClickListener {
            clickedItemID = currentBerber.docId
            // Logcat'e tıklanan berberin adını yazdır
            println("Clicked Berber Name: $clickedItemID")
            val intent = Intent(holder.binding.root.context,DateBerberActivity::class.java)
            intent.putExtra("berberID",clickedItemID)
            holder.binding.root.context.startActivity(intent)

        }
    }
}