package com.erayyilmaz.randevumm

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.erayyilmaz.randevumm.databinding.ActivityDateKuaforBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DateKuaforActivity() : AppCompatActivity()  {
    private lateinit var binding: ActivityDateKuaforBinding
    private lateinit var db: FirebaseFirestore
    var name = ""
    var time9 = true
    var time10 = true
    var time11 = true
    var time12 = true
    var time13 = true
    var time14 = true
    var time15 = true
    var time16 = true
    var time17 = true

    override fun onCreate(savedInstanceState: Bundle?) {
        name = intent.getStringExtra("kuaforID").toString()
        super.onCreate(savedInstanceState)
        binding = ActivityDateKuaforBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent = intent
        db = Firebase.firestore
        getData()
        controlDate()
        println("ID: $name")
    }

    private fun getData() {
        val name = intent.getStringExtra("kuaforID")
        db.collection("kuafor").document("$name").collection("date").get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        time9 = document.getBoolean("09:00") ?: false
                        time10 = document.getBoolean("10:00") ?: false
                        time11 = document.getBoolean("11:00") ?: false
                        time12 = document.getBoolean("12:00") ?: false
                        time13 = document.getBoolean("13:00") ?: false
                        time14 = document.getBoolean("14:00") ?: false
                        time15 = document.getBoolean("15:00") ?: false
                        time16 = document.getBoolean("16:00") ?: false
                        time17 = document.getBoolean("17:00") ?: false
                    }
                    // Veri geldikten sonra kontrolü başlat
                    controlDate()
                } else {
                    // Veri yoksa kullanıcıya bilgi ver
                    Toast.makeText(this, "Veri bulunamadı", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    fun controlDate() {
        getData()
        updateButtonColor(binding.hour9button, time9)
        updateButtonColor(binding.hour10button, time10)
        updateButtonColor(binding.hour11button, time11)
        updateButtonColor(binding.hour12button, time12)
        updateButtonColor(binding.hour13button, time13)
        updateButtonColor(binding.hour14button, time14)
        updateButtonColor(binding.hour15button, time15)
        updateButtonColor(binding.hour16button, time16)
        updateButtonColor(binding.hour17button, time17)
    }

    fun updateButtonColor(button: View, timeValue: Boolean) {
        if (timeValue) {
            button.setBackgroundColor(Color.parseColor("#4CAF50"))
        } else {
            button.setBackgroundColor(Color.parseColor("#636060"))
        }
    }

    fun updateDatabase(hour: String, value: Boolean) {
        val name = intent.getStringExtra("kuaforID")
        val data = hashMapOf(
            hour to value
        )

        db.collection("kuafor").document(name!!).collection("date").document("datetime").update(hour, value)
            .addOnSuccessListener {
                // Başarıyla güncellendi
                Toast.makeText(this, "Saat güncellendi", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Hata durumunda kullanıcıya bilgi ver
                Toast.makeText(
                    this,
                    "Saat güncellenirken hata oluştu: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    fun getDate(view: View, hour: String, timeValue: Boolean) {
        if (!timeValue) {
            Toast.makeText(this, "Bu saate randevu oluşturamazsınız!", Toast.LENGTH_LONG).show()
        } else {
            val alert = AlertDialog.Builder(this@DateKuaforActivity)
            alert.setMessage("Randevu oluşturmak istediğinize emin misiniz?")
            alert.setPositiveButton("Evet") { _, _ ->
                // Saat seçildiğinde, timeValue değerini false yap
                // Veritabanını güncelle
                updateDatabase(hour, false)
                // UI'yı güncelle
                updateButtonColor(view, false)
            }
            alert.setNegativeButton("Hayır") { _, _ ->
                // Hayır'a tıklandığında yapılacak işlemler
                // İsterseniz buraya başka bir işlem de ekleyebilirsiniz.
            }
            alert.show()
        }
    }

    fun getDate9(view: View) {
        getDate(binding.hour9button, "09:00", time9)
    }

    fun getDate10(view: View) {
        getDate(binding.hour10button, "10:00", time10)
    }

    fun getDate11(view: View) {
        getDate(binding.hour11button, "11:00", time11)
    }

    fun getDate12(view: View) {
        getDate(binding.hour12button, "12:00", time12)
    }

    fun getDate13(view: View) {
        getDate(binding.hour13button, "13:00", time13)
    }

    fun getDate14(view: View) {
        getDate(binding.hour14button, "14:00", time14)
    }

    fun getDate15(view: View) {
        getDate(binding.hour15button, "15:00", time15)
    }

    fun getDate16(view: View) {
        getDate(binding.hour16button, "16:00", time16)
    }

    fun getDate17(view: View) {
        getDate(binding.hour17button, "17:00", time17)
    }
}