package br.com.sanatielbarros.appfeirapdm.utils

import okhttp3.MediaType
import okhttp3.OkHttpClient
import android.util.Log
import okhttp3.Request
import okhttp3.RequestBody
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

    fun store(url: String, json: String): String{
        log("HttpHelper.post: $url")
        //transformando o json com dados do produto num obj RequestBody a ser enviado na requisicao
        val body = RequestBody.create(JSON, json)
        //criando a requsicao  post
        val request = Request.Builder().url(url).post(body).build()
        //fazendo a requisicao e retornando json de resposta
        return getJson(request)
    }

    fun update(url: String, json: String): String{
        log("HttpHelper.put: $url")
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder().url(url).addHeader("Accept","application/json")
                      .addHeader("Content-Type","application/x-www-form-urlencoded").put(body).build()
        return getJsonUpdate(request)
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

    fun getJsonUpdate(request: Request?): String{
        //faz a req e retorna a resposta
        val response = client.newCall(request).execute()
        //pega o body da resposta
        val responseBody = response.body()
        if(responseBody != null){
            log("HttpHelper.put: $responseBody")
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