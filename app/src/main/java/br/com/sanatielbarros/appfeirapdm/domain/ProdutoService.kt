package br.com.sanatielbarros.appfeirapdm.domain

import android.util.Log
import br.com.sanatielbarros.appfeirapdm.domain.dao.DatabaseManager
import br.com.sanatielbarros.appfeirapdm.extensions.fromJson
import br.com.sanatielbarros.appfeirapdm.extensions.toJson
import br.com.sanatielbarros.appfeirapdm.utils.HttpHelper

/**
 * Created by Sanatiel on 06/05/2018.
 */
//class que vai fazer requisicao à API
object ProdutoService {
    private val TAG = "feira"

    //endpoint base da API a ser acessada
    private val BASE_URL = "https://web-service-pdm.herokuapp.com/api/v1"

    fun getProdutos(categoria: CategoriaProduto): List<Produto>{
        Log.d(TAG, "CATEGORIA PARAM: "+categoria.name)

        val url: String

        if(categoria.name.equals("todas")){
            //pegando todos os produtos ordenados por data de cadastro descendente (últimos produtos cadastrados)
            url = "$BASE_URL/products?order=created_at,desc"

        }else{
            //se for informado uma categoria, pegar so os produtos da categoria
            url = "$BASE_URL/products?where[categoria]=${categoria.name}"
        }

            Log.d(TAG, url)

            //json com o resultado da requisicao (a classe HttpHelper faz as requisicoes usando a lib okhttp)
            val json = HttpHelper.get(url)
            //transforma o json num List<Produto>
            val produtos = fromJson<List<Produto>>(json)
            Log.d(TAG,"${produtos.size} produtos encontrados")
            return produtos
    }


    fun delete(produto: Produto): Response{
        //endpoint de deletar
        val url = "$BASE_URL/products/${produto.id}"
        //faz requisicao delete e recebe o json de resposta
        val json = HttpHelper.delete(url)
        //tranforma o json em um obj do tipo Response, q contem os dados da resposta (msg, status, etc) obtidas do servidor
        val response = fromJson<Response>(json)
        Log.d(TAG,"STATUS: "+response.status)

        //se id da resposta nao estiver nulo, deu certo deletar, entao apaga tbm do bd interno sqlite
        if(response.id != null){
            val daoProduto = DatabaseManager.getProdutoDAO()
            daoProduto.delete(produto)
        }
        return response
    }

    fun save(produto: Produto): Response{
        val url = "$BASE_URL/products";
        //informa o endpoint para salvar e o produto a ser salvo, em forma de json; recebe o json de resposta vinda do WS
        val json = HttpHelper.store(url, produto.toJson())
        //usando met fromJson q foi criado para converter o json de resposta em obj(nesse caso um obj da classe Response)
        val response = fromJson<Response>(json)
        return response
    }

    fun update(produto: Produto): Response{
        val url = "$BASE_URL/products/${produto.id}"
        val json = HttpHelper.update(url, produto.toJson())
        val response = fromJson<Response>(json)

        //se atualizou, modificar tbm no sqlite
        if(response.id != null){
            val daoProduto = DatabaseManager.getProdutoDAO()
            daoProduto.update(produto.nome,produto.local,produto.categoria,produto.preco, produto.id)
        }
        return response
    }


}