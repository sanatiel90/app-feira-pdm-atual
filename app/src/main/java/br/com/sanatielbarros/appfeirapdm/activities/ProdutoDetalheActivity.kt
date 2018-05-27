package br.com.sanatielbarros.appfeirapdm.activities

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.Menu
import android.view.MenuItem
import br.com.sanatielbarros.appfeirapdm.R
import br.com.sanatielbarros.appfeirapdm.domain.FavoritosService
import br.com.sanatielbarros.appfeirapdm.domain.Produto
import br.com.sanatielbarros.appfeirapdm.domain.ProdutoService
import br.com.sanatielbarros.appfeirapdm.extensions.setupToolbar
import br.com.sanatielbarros.appfeirapdm.extensions.toast
import br.com.sanatielbarros.appfeirapdm.utils.AndroidUtils.formatMoney
import kotlinx.android.synthetic.main.activity_produto_detalhe.*
import kotlinx.android.synthetic.main.activity_produto_detalhe_contents.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class ProdutoDetalheActivity : BaseActivity() {

    /*
     * recuperando o produto enviado pelo ProdutosFragment através de intent.getParcelableExtra<ClasseDoObj>("nome_parametro")
     *
     * a notacao by lazy faz com que o obj seja carregado na memoria apenas quando necessario
     */
    val produto by lazy { intent.getParcelableExtra<Produto>("produto") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_detalhe)
        updateFabColorFavorite(produto)
        fabFavorite.setOnClickListener{
            onClickFavoritar(produto)
        }
        setupToolbar(R.id.toolbar,produto.nome,true)
        initViews()
    }

    private fun initViews(){
        txtPreco.text = "Preço: "+formatMoney(produto.preco)
        txtLocal.text = "Local: ${produto.local}"
        txtData.text = "Data: ${produto.created_at}"
        txtCategoria.text = "Categoria: ${produto.categoria}"
    }

    //criando menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_detalhe, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_editar -> {
                startActivity<FormProdutoActivity>("produto" to produto)
            }
            R.id.menu_deletar -> {
                //alert lib anko
                alert("Confirma a exclusão desse produto?", "Feira") {
                    positiveButton("Sim"){
                        taskExcluir()
                    }
                    negativeButton("Não"){

                    }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onClickFavoritar(produto: Produto){
        doAsync {
            val favoritado = FavoritosService.favoritar(produto)
            uiThread {
                 if(favoritado){
                    updateFabColorFavorite(produto)
                    toast("Produto adicionado aos favoritos")
                }else{
                    updateFabColorFavorite(produto)
                    toast("Produto removido dos favoritos")
                 }
            }
        }
    }//clickfavoritar

    //atualiza cor do fab de acordo se produto esta favoritado ou nao
    fun updateFabColorFavorite(produto: Produto){
        doAsync {
            val favoritado = FavoritosService.isFavorite(produto)
            uiThread {
                if(favoritado){
                    fabFavorite.backgroundTintList = getColorStateList(R.color.colorIsFavoriteBack)
                    fabFavorite.imageTintList = getColorStateList(R.color.colorIsFavoriteIcon)
                }else{
                    fabFavorite.backgroundTintList = getColorStateList(R.color.colorIsNotFavoriteBack)
                    fabFavorite.imageTintList = getColorStateList(R.color.colorIsNotFavoriteIcon)
                }

            }
        }
    }


    fun taskExcluir(){
        doAsync {
            val response = ProdutoService.delete(produto)
            uiThread {
                //toast(response.msg)
                toast("Produto deletado")
                finish()
            }
        }
    }


}
