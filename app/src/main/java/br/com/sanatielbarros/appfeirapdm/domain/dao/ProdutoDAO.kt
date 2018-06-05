package br.com.sanatielbarros.appfeirapdm.domain.dao

import android.arch.persistence.room.*
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

    @Query("UPDATE products SET nome = :arg0, local = :arg1, categoria = :arg2, preco = :arg3 WHERE id = :arg4")
    fun update(nome: String, local: String, categoria: String, preco: Float, id: Long)

}