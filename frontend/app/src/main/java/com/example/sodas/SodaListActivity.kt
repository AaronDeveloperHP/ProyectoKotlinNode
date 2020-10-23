package com.example.sodas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sodas.models.Soda
import com.example.sodas.service.SodaServiceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SodaListActivity : AppCompatActivity() {
    private lateinit var sodas: ArrayList<Soda>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SodaAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soda_list)


        sodas = ArrayList<Soda>()

        viewManager = LinearLayoutManager(this)

        viewAdapter = SodaAdapter(sodas, this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBicycles)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager

        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter

        getAllBicycles()

        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            val intent = Intent(this, SodaDetailActivity::class.java)
            intent.putExtra("state", "Adding")
            startActivity(intent)
        }
    }

    private fun getAllBicycles() {

        val sodaServiceImpl = SodaServiceImpl()
        sodaServiceImpl.getAll(this) { response ->
            run {
                if (response != null) {
                    viewAdapter.sodaList = response
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }

}

