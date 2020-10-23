package com.example.sodas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.sodas.models.Soda
import com.example.sodas.service.SodaServiceImpl
import com.example.sodas.service.SodaSingleton
import com.google.android.material.textfield.TextInputEditText
import com.example.sodas.SodaAdapter
import com.squareup.picasso.Picasso

class SodaDetailActivity : AppCompatActivity() {
    private lateinit var state: String
    private lateinit var textInputEditTextName: EditText
    private lateinit var textInputEditTextQuantity: EditText
    private lateinit var textInputEditTextFormat: EditText
    private lateinit var textInputEditTextPack: EditText
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soda_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        state = this.intent.getStringExtra("state").toString()

        val sodaId = this.intent.getIntExtra("sodaId", 1)

        textInputEditTextName = findViewById(R.id.TextInputEditTextName)
        textInputEditTextQuantity = findViewById(R.id.TextInputEditTextQuantity)
        textInputEditTextFormat = findViewById(R.id.TextInputEditTextFormat)
        textInputEditTextPack = findViewById(R.id.TextInputEditTextPack)

        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            deleteSoda(sodaId)
        }

        if(state == "Showing") getSoda(sodaId)

        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            when(state){
                "Showing" -> {
                    changeButtonsToEditing()
                }
                "Editing" -> {
                    val soda = Soda(sodaId, textInputEditTextName.text.toString(),Integer.parseInt(textInputEditTextQuantity.text.toString()) ,textInputEditTextFormat.text.toString() ,Integer.parseInt(textInputEditTextPack.text.toString() ))
                    updateSoda(soda)
                }
                "Adding" -> {
                    val soda = Soda(sodaId, textInputEditTextName.text.toString(), Integer.parseInt(textInputEditTextQuantity.text.toString()) , textInputEditTextFormat.text.toString() ,Integer.parseInt(textInputEditTextPack.text.toString()))
                    createSoda(soda)
                }
            }
        }

        if(state == "Adding") changeButtonsToAdding()
    }

    private fun updateSoda(soda: Soda) {
        val sodaServiceImpl = SodaServiceImpl()
        sodaServiceImpl.updateSoda(this, soda) { ->
            run {
                changeButtonsToShowing(soda.id)
                val intent = Intent(this, SodaListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createSoda(soda: Soda) {
        val sodaServiceImpl = SodaServiceImpl()
        sodaServiceImpl.createSoda(this, soda) { ->
            run {
                changeButtonsToShowing(soda.id)
                val intent = Intent(this, SodaListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeButtonsToAdding() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Add Soda")
        textInputEditTextName.isEnabled = true
        textInputEditTextQuantity.isEnabled = true
        textInputEditTextFormat.isEnabled = true
        textInputEditTextPack.isEnabled = true
        state = "Adding"
    }

    private fun changeButtonsToShowing(sodaId: Int){
        buttonDelete.visibility = View.VISIBLE
        buttonDelete.isEnabled = true
        buttonEdit.setText("Edit Soda")
        textInputEditTextName.isEnabled = false
        textInputEditTextQuantity.isEnabled = false
        textInputEditTextFormat.isEnabled = false
        textInputEditTextPack.isEnabled = false
        state = "Showing"
    }

    private fun changeButtonsToEditing() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Apply changes")
        textInputEditTextName.isEnabled = true
        textInputEditTextQuantity.isEnabled = true
        textInputEditTextFormat.isEnabled = true
        textInputEditTextPack.isEnabled = true
        state = "Editing"
    }

    private fun getSoda(sodaId: Int) {
        val sodaServiceImpl = SodaServiceImpl()
        sodaServiceImpl.getById(this, sodaId) { response ->
            run {

                val txtName: TextInputEditText = findViewById(R.id.TextInputEditTextName)
                val txtQuantity: TextInputEditText = findViewById(R.id.TextInputEditTextQuantity)
                val txtFormat: TextInputEditText = findViewById(R.id.TextInputEditTextFormat)
                val txtPack: TextInputEditText = findViewById(R.id.TextInputEditTextPack)
                val img: ImageView = findViewById(R.id.imageViewBicycleDetail)

                txtName.setText(response?.productName ?: "")
                txtQuantity.setText(response?.productQuantity.toString() ?: "")
                txtFormat.setText(response?.productFormat ?:"")
                txtPack.setText(response?.productPack.toString() ?: "")

                val url = SodaSingleton.getInstance(this).baseUrl + "/img/soda-"
                val imageUrl = url + (response?.productName?.let { sodaServiceImpl.validateSodaForImage(it) }
                        ?: "0" ) + ".png"
                Picasso.with(this).load(imageUrl).into(img);
            }
        }
    }

    private fun deleteSoda(sodaId: Int) {
        val sodaServiceImpl = SodaServiceImpl()
        sodaServiceImpl.deleteById(this, sodaId) { ->
            run {
                val intent = Intent(this, SodaListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}