package com.erayyilmaz.randevumm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.erayyilmaz.randevumm.adapter.KuaforRecyclerAdapter
import com.erayyilmaz.randevumm.databinding.ActivityKuaforBinding
import com.erayyilmaz.randevumm.model.kuafor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class KuaforActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKuaforBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var kuaforArrayList: ArrayList<kuafor>
    private lateinit var KuaforAdapter: KuaforRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuaforBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        kuaforArrayList = ArrayList<kuafor>()
        binding.recyclerViewKuafor.layoutManager = LinearLayoutManager(this)
        KuaforAdapter = KuaforRecyclerAdapter(kuaforArrayList)
        binding.recyclerViewKuafor.adapter = KuaforAdapter
        getData()
    }


    private fun getData() {
        db.collection("kuafor").addSnapshotListener { value, error ->
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
                        val kuafor = kuafor(docId,name, address, phone, image)
                        kuaforArrayList.add(kuafor)
                    }
                    // Verileri aldıktan sonra RecyclerView'ı güncelle
                    KuaforAdapter.notifyDataSetChanged()
                } else {
                    // Veri yoksa kullanıcıya bilgi ver
                    Toast.makeText(this, "Veri bulunamadı", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}