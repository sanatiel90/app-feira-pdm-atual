package br.com.sanatielbarros.appfeirapdm.domain

import android.widget.Toast
import br.com.sanatielbarros.appfeirapdm.domain.dao.DatabaseManager

/**
 * Created by Sanatiel on 18/05/2018.
 */
object FavoritosService {

    //retorna todos os prod
    fun getProdutos(): List<Produto>{
        val daoProduto = DatabaseManager.getProdutoDAO()
        val produtos = daoProduto.findAll()
        return produtos
    }


    //verifica se produto esta favoritado (esta favoritado quando esta salvo no banco)
    fun isFavorite(produto: Produto): Boolean{
        val daoProduto = DatabaseManager.getProdutoDAO()
        val favoritado = daoProduto.find(produto.id) != null
        return favoritado
    }


    fun favoritar(produto: Produto): Boolean{
        val daoProduto = DatabaseManager.getProdutoDAO()
        //verifica se o carro esta favoritado ou nao
        val favorito = isFavorite(produto)
        //se estiver, desfavorita (apaga do banco)
        if(favorito){
            daoProduto.delete(produto)
            return false
        }
        //se nao estiver, favorita (salva no banco)
        daoProduto.store(produto)
        return true
    }



}