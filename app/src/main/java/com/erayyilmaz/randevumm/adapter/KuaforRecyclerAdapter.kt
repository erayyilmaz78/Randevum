package com.erayyilmaz.randevumm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erayyilmaz.randevumm.DateKuaforActivity
import com.erayyilmaz.randevumm.databinding.RecyclerRowKuaforBinding
import com.erayyilmaz.randevumm.model.kuafor
import com.squareup.picasso.Picasso

class KuaforRecyclerAdapter(val kuaforList: ArrayList<kuafor>) : RecyclerView.Adapter<KuaforRecyclerAdapter.PostHolder>(){
    var clickedItemID = ""

    class PostHolder(val binding: RecyclerRowKuaforBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowKuaforBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return kuaforList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val currentKuafor = kuaforList[position]

        holder.binding.recyclerKuaforName.text= currentKuafor.name
        holder.binding.recyclerKuaforAddress.text = currentKuafor.address
        holder.binding.recyclerKuaforPhone.text = currentKuafor.phone
        Picasso.get().load(currentKuafor.imageurl).into(holder.binding.recyclerKuaforImage)

        holder.binding.recyclerKuaforImage.setOnClickListener {
            clickedItemID = currentKuafor.docId
            // Logcat'e tıklanan berberin adını yazdır
            println("Clicked Kuafor ID: $clickedItemID")
            val intent = Intent(holder.binding.root.context, DateKuaforActivity::class.java)
            intent.putExtra("kuaforID",clickedItemID)
            holder.binding.root.context.startActivity(intent)

        }
    }

}