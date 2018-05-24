package br.com.sanatielbarros.appfeirapdm.utils

import okhttp3.MediaType
import okhttp3.OkHttpClient
import android.util.Log
import okhttp3.Request
import java.io.IOException

/**
 * Created by Sanatiel on 06/05/2018.
 */
//classe que usa lib okhttp para fazer requisicoes
object HttpHelper {

    private val TAG = "http"
    private val LOG_ON = true
    //define o tipo de arquivo e o encode
    val JSON = MediaType.parse("application/json; charset=utf-8")
    //instancia de okhttp
    val client = OkHttpClient()

    //GET
    fun get(url: String): String{
        log("HttpHelper.get: $url")
        //cria uma requisicao GET
        val request = Request.Builder().url(url).get().build()
        //faz a req GET e retorna o json de resposta
        return getJson(request)
    }


    fun delete(url: String): String{
        log("HttpHelper.delete: $url")

        val request = Request.Builder().url(url).delete().build()
        return getJson(request)
    }


    //faz a requisicao e lê a resposta do servidor em formato json
    fun getJson(request: Request?): String{
        //faz a req e retorna a resposta
        val response = client.newCall(request).execute()
        //pega o body da resposta
        val responseBody = response.body()
        if(responseBody != null){
            //json com o corpo da resposta
            val json = responseBody.string()
            log("  << : $json")
            return json
        }
        throw IOException("Erro ao fazer requisição")

    }


    private fun log(s: String){
        if(LOG_ON){
            Log.d(TAG,s)
        }
    }


}