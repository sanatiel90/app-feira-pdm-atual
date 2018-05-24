package br.com.sanatielbarros.appfeirapdm.fragments

import br.com.sanatielbarros.appfeirapdm.activities.ProdutoDetalheActivity
import br.com.sanatielbarros.appfeirapdm.adapter.ProdutoAdapter
import br.com.sanatielbarros.appfeirapdm.domain.FavoritosService
import br.com.sanatielbarros.appfeirapdm.domain.Produto
import kotlinx.android.synthetic.main.fragment_produtos.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

/**
 * Created by Sanatiel on 17/05/2018.
 */
//fragment q vai mostrar a lista de produtos favoritados, ela estende do fragment padrao de produtos
class FavoritosFragment : ProdutosFragment() {

    override fun taskProdutos() {

        doAsync {
            val produtos = FavoritosService.getProdutos()
            uiThread {
                recyclerView.adapter = ProdutoAdapter(produtos) {onClickProduto(it)}
            }
        }


    }

    override fun onClickProduto(produto: Produto){
        activity.startActivity<ProdutoDetalheActivity>("produto" to produto)
    }

}