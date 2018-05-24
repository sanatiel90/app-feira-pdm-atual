package br.com.sanatielbarros.appfeirapdm.domain.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.com.sanatielbarros.appfeirapdm.domain.Produto

/**
 * Created by Sanatiel on 17/05/2018.
 */
//interface DAO que define as operacoes CRUD que serão feitas no banco sqlite
@Dao
interface ProdutoDAO {

    @Query("select * from products")
    fun findAll(): List<Produto>

    @Query("select * from products where id = :arg0")
    fun find(id: Long): Produto?

    @Insert
    fun store(produto: Produto)

    @Delete
    fun delete(produto: Produto)

}