package com.example.sodas.service

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sodas.models.Soda
import org.json.JSONObject

class SodaServiceImpl : ISodaService {
    override fun getAll(context: Context, completionHandler: (response: ArrayList<Soda>?) -> Unit) {
        val path = SodaSingleton.getInstance(context).baseUrl + "/api/sodas"
        val arrayRequest = JsonArrayRequest(Request.Method.GET, path, null,
                { response ->
                    var sodas: ArrayList<Soda> = ArrayList()
                    for (i in 0 until response.length()) {
                        val soda = response.getJSONObject(i)
                        val id = soda.getInt("id")
                        val productName = soda.getString("productName")
                        val productQuantity = soda.getInt("productQuantity")
                        val productFormat = soda.getString( "productFormat")
                        val productPack = soda.getInt("productPack")
                        sodas.add(Soda(id, productName, productQuantity, productFormat, productPack))
                    }
                    completionHandler(sodas)
                },
                { error ->
                    completionHandler(ArrayList<Soda>())
                })
        SodaSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getById(context: Context, sodaId: Int, completionHandler: (response: Soda?) -> Unit) {
        val path = SodaSingleton.getInstance(context).baseUrl + "/api/sodas/" + sodaId.toString()
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
                { response ->
                    if(response == null) completionHandler(null)
                    val id = response.getInt("id")
                    val productName = response.getString("productName")
                    val productQuantity = response.getInt("productQuantity")
                    val productFormat = response.getString( "productFormat")
                    val productPack = response.getInt("productPack")

                    val soda = Soda(id,productName,productQuantity,productFormat,productPack)
                    completionHandler(soda)
                },
                { error ->
                    completionHandler(null)
                })
        SodaSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }
    private var valor:Int=0;
    fun setValor(valor:Int){
        this.valor=valor;
    }

    fun validateSodaForImage(name: String): Int{
        if(name.toLowerCase().trim()=="cocacola" ||name.toLowerCase().trim()=="coca-cola"||name.toLowerCase().trim()=="coca cola"){
            setValor(1);
        }
        if(name.toLowerCase().trim()=="sprite" ||name.toLowerCase().trim()=="esprite"||name.toLowerCase().trim()=="sprait"){
            setValor(2);
        }
        if(name.toLowerCase().trim()=="fanta" ||name.toLowerCase().trim()=="fanta naranja"||name.toLowerCase().trim()=="fanta limon"){
            setValor(3);
        }
        if(name.toLowerCase().trim()=="aquarius" ||name.toLowerCase().trim()=="acuarius"){
            setValor(4);
        }
        if(name.toLowerCase().trim()=="powerade" ||name.toLowerCase().trim()=="power"){
            setValor(5);
        }
        if(name.toLowerCase().trim()=="sweps" ||name.toLowerCase().trim()=="swepps"||name.toLowerCase().trim()=="schweppes" || name.toLowerCase().trim()=="schweps" || name.toLowerCase().trim()=="swchepps" ||name.toLowerCase().trim()=="schwepps"){
            setValor(6);
        }
        if(name.toLowerCase().trim()=="appletiser" ||name.toLowerCase().trim()=="apletiser"||name.toLowerCase().trim()=="appletaiser" ){
            setValor(7);
        }
        if(name.toLowerCase().trim()=="nestea" ||name.toLowerCase().trim()=="nesti"){
            setValor(8);
        }

        return this.valor;
    }
    override fun deleteById(context: Context, sodaId: Int, completionHandler: () -> Unit) {
        val path = SodaSingleton.getInstance(context).baseUrl + "/api/sodas/" +sodaId
        val objectRequest = JsonObjectRequest(Request.Method.DELETE, path, null,
                { response ->
                    Log.v("Hola caracola", "se borró")
                    completionHandler()
                },
                { error ->
                    Log.v("Hola caracola", "dió error")
                    completionHandler()
                })
        SodaSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun updateSoda(context: Context, soda: Soda, completionHandler: () -> Unit) {
        val path = SodaSingleton.getInstance(context).baseUrl + "/api/sodas/" + soda.id
        val sodaJson: JSONObject = JSONObject()
        sodaJson.put("id", soda.id.toString())
        sodaJson.put("productName", soda.productName)
        sodaJson.put("productQuantity", soda.productQuantity.toString())
        sodaJson.put("productFormat", soda.productFormat)
        sodaJson.put("productPack", soda.productPack.toString())
        val objectRequest = JsonObjectRequest(Request.Method.PUT, path, sodaJson,
                { response ->
                    completionHandler()
                },
                { error ->
                    completionHandler()
                })
        SodaSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun createSoda(context: Context, soda: Soda, completionHandler: () -> Unit) {
        val path = SodaSingleton.getInstance(context).baseUrl + "/api/sodas"
        val sodaJson: JSONObject = JSONObject()
        sodaJson.put("id", soda.id.toString())
        sodaJson.put("productName", soda.productName)
        sodaJson.put("productQuantity", soda.productQuantity.toString())
        sodaJson.put("productFormat", soda.productFormat)
        sodaJson.put("productPack", soda.productPack.toString())

        val objectRequest = JsonObjectRequest(Request.Method.POST, path, sodaJson,
                { response -> completionHandler() },
                { error -> completionHandler() })
        SodaSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }
}