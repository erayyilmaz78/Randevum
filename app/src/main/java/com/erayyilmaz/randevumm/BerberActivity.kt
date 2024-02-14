package com.erayyilmaz.randevumm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.erayyilmaz.randevumm.adapter.BerberRecyclerAdapter
import com.erayyilmaz.randevumm.databinding.ActivityBerberBinding
import com.erayyilmaz.randevumm.model.berber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.erayyilmaz.randevumm.databinding.RecyclerRowBinding

class BerberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBerberBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var berberArrayList: ArrayList<berber>
    private lateinit var berberAdapter: BerberRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        berberArrayList = ArrayList()
        binding.recyclerViewBerber.layoutManager = LinearLayoutManager(this)
        berberAdapter = BerberRecyclerAdapter(berberArrayList)
        binding.recyclerViewBerber.adapter = berberAdapter
        getData()
    }

    private fun getData() {
        db.collection("berber").addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }

            value?.let {
                if (!it.isEmpty) {
                    val documents = it.documents
                    for (document in documents) {
                        val docId = document.id
                        val name = document.getString("Name") ?: ""
                        val address = document.getString("Address") ?: ""

                        // 'Phone' alanını doğrudan 'get' metodu ile alın ve uygun türe dönüştür
                        val phone = document.get("Phone")?.toString() ?: ""

                        val image = document.getString("download url") ?: ""
                        val berber = berber(docId,name, address, phone, image)
                        berberArrayList.add(berber)
                    }
                    // Verileri aldıktan sonra RecyclerView'ı güncelle
                    berberAdapter.notifyDataSetChanged()
                } else {
                    // Veri yoksa kullanıcıya bilgi ver
                    Toast.makeText(this, "Veri bulunamadı", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
