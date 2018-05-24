package br.com.sanatielbarros.appfeirapdm.domain.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.sanatielbarros.appfeirapdm.domain.Produto

/**
 * Created by Sanatiel on 17/05/2018.
 */
//classe abstrata q vai gerenciar o banco de dados Room; ela define uma lista com todas as entidades que precisam
//ser persistidas (classes model mapeadas com @Entity), o nome e a versao do bd
@Database(entities = arrayOf(Produto::class), version = 1)
abstract class ProdutoDatabase : RoomDatabase(){
    abstract fun produtoDAO(): ProdutoDAO
}