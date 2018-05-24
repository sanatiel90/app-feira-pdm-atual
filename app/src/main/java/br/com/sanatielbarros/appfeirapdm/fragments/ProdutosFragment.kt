package br.com.sanatielbarros.appfeirapdm.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sanatielbarros.appfeirapdm.R
import br.com.sanatielbarros.appfeirapdm.activities.ProdutoDetalheActivity
import br.com.sanatielbarros.appfeirapdm.adapter.ProdutoAdapter
import br.com.sanatielbarros.appfeirapdm.domain.CategoriaProduto
import br.com.sanatielbarros.appfeirapdm.domain.Produto
import br.com.sanatielbarros.appfeirapdm.domain.ProdutoService
import br.com.sanatielbarros.appfeirapdm.extensions.toast
import br.com.sanatielbarros.appfeirapdm.utils.AndroidUtils
import kotlinx.android.synthetic.main.fragment_produtos.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

//import org.jetbrains.anko.


open class ProdutosFragment : Fragment() {

    //var privada da Enum CategoriaProduto, vai receber umas das categorias listadas la; a categoria todas é pra indicar mostrar todos os produtos
    //independente da categoria
    private var categoria: CategoriaProduto = CategoriaProduto.todas

    //array q vai conter os produtos, buscados num web service
    protected var produtos = listOf<Produto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //le parametro enviado pelo Bundle
        if(arguments != null){
            categoria = arguments.getSerializable("categoria") as CategoriaProduto
        }

    }

    //cria a view do fragment
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //infla o layout da fragment de produtos e retorna
        val view = inflater?.inflate(R.layout.fragment_produtos, container, false)
        return view
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //views
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }


    override fun onResume() {
        super.onResume()
        taskProdutos()
    }

    //metodo que faz a requisicao ao web service e atualiza a lista de carros na tela
    open fun taskProdutos(){
        //verificar se existe conexao com a net
        if(!AndroidUtils.isNetworkAvaliable(context)){
            toast("Não há conexão com a internet")
        }
        doAsync {
            //recupera a lista de produtos do Web Service
            produtos = ProdutoService.getProdutos(categoria)

            uiThread{
                //atualiza o Recycler View (listagem de produtos) na tela usando o adapter de ProdutoAdapter
                //(um adapter informa a quantidade de elementos da lista e cria uma view para cada linha, ou seja, cada produto)
                //esse adapter recebe a lista de produtos e uma funcao lambda para ser o evento de clique no produto da lista
                recyclerView.adapter = ProdutoAdapter(produtos) {onClickProduto(it)}
            }

        }
    }

    //evento de clique no produto da lista
   open fun onClickProduto(produto: Produto){
        activity.startActivity<ProdutoDetalheActivity>("produto" to produto)
    }




}
