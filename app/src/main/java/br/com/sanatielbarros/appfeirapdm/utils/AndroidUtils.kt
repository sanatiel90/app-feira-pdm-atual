package br.com.sanatielbarros.appfeirapdm.utils

import android.content.Context
import android.icu.text.NumberFormat
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.util.*

/**
 * Created by Sanatiel on 07/05/2018.
 */
//met para verificar se existe conexao com a net
object AndroidUtils{

    fun isNetworkAvaliable(context: Context): Boolean{
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networks = connectivity.allNetworks
        return networks
                .map { connectivity.getNetworkInfo(it) }
                .any {it.state == NetworkInfo.State.CONNECTED}

        /*for(n in networks){
            val info = connectivity.getNetworkInfo(n)
            if(info.state == NetworkInfo.State.CONNECTED){
                return true
            }
        }
        return false*/
    }


    fun formatMoney(valor: Float): String{
        val local = Locale("pt","BR")
        val formato = java.text.NumberFormat.getCurrencyInstance(local)
        val valorFormatado = formato.format(valor)
        return valorFormatado
    }


}