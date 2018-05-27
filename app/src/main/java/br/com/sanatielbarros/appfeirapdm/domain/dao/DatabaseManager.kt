package br.com.sanatielbarros.appfeirapdm.domain.dao

import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
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
        //VER NOTA ***
    }

    //retorna instancia de ProdutoDAO de forma global
    fun getProdutoDAO(): ProdutoDAO{
        return dbInstance.produtoDAO()
    }

}

/*
* **NOTA
* Se mudar alguma coisa no model Produto ou qualquer outro model, como adicionar um novo campo ou mudar um tipo de um campo existente
* vc vai precisar atualizar o schema da tabela sqlite para atualizar as modificacoes; vc pode criar uma migration (addMigration()) e adicionar ao
* Room.databaseBuilder(), antes do build(), para fazer as modificacoes e manter os dados q estavam salvos; ou pode adicioanr o metodo
* .fallbackToDestructiveMigration(), antes do build(), para ele forçar a atualizacao do banco, porém com esse metodo os dados que estavam salvos
* vao ser apagados. Em ambos os casos, seja adicionando uma migration ou seja usando o met .fallbackToDestructiveMigration(), depois que fizer
* uma modificacao na classe model, vc deve tbm, na classe ProdutosDatabase (ou qualquer classe com a annotation @Database) mudara  version do banco
* adicionando +1 a versao atual
* */