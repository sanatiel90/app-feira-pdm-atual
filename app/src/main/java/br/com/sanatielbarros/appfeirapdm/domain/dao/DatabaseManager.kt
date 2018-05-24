package br.com.sanatielbarros.appfeirapdm.domain.dao

import android.arch.persistence.room.Room
import br.com.sanatielbarros.appfeirapdm.FeiraApplication

/**
 * Created by Sanatiel on 17/05/2018.
 */
//class singleton q gerencia a classe ProdutoDatabase
object DatabaseManager {
    private var dbInstance: ProdutoDatabase
    init {
        val appContext = FeiraApplication.getInstance().applicationContext
        //config o room, criando instancia para banco dados
        dbInstance = Room.databaseBuilder(
                appContext,     //contexto
                ProdutoDatabase::class.java,  //@Database
                "feira.sqlite"      //nome do bd
        ).build()
    }

    //retorna instancia de ProdutoDAO de forma global
    fun getProdutoDAO(): ProdutoDAO{
        return dbInstance.produtoDAO()
    }

}