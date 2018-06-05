package br.com.sanatielbarros.appfeirapdm.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import br.com.sanatielbarros.appfeirapdm.R
import br.com.sanatielbarros.appfeirapdm.domain.Produto
import br.com.sanatielbarros.appfeirapdm.domain.ProdutoService
import br.com.sanatielbarros.appfeirapdm.extensions.isEmpty
import br.com.sanatielbarros.appfeirapdm.extensions.setupToolbar
import br.com.sanatielbarros.appfeirapdm.extensions.string
import br.com.sanatielbarros.appfeirapdm.extensions.toast
import br.com.sanatielbarros.appfeirapdm.utils.MoneyTextWatcher
import kotlinx.android.synthetic.main.activity_form_produto.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.NumberFormat
import java.util.*

class FormProdutoActivity : AppCompatActivity() {

    val categorias = arrayOf("Frutas","Verduras","Carnes","Grãos","Outros")
    val produto by lazy { intent.getParcelableExtra<Produto>("produto") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_produto)
        var title = "Novo Produto"
    //    val produto = intent.getParcelableExtra<Produto>("produto")
        if(produto != null) title = produto.nome
        setupToolbar(R.id.toolbar,title,true)
        initViews(produto)
    }


    fun initViews(produto : Produto?){
        spinnerInputCategoria.adapter = categoriaAdapter()
        if(produto != null){
            inputNome.setText(produto.nome)
            inputLocal.setText(produto.local)
            inputPreco.setText(produto.preco.toString())
            spinnerInputCategoria.setSelection(categorias.indexOf(produto.categoria))

        }
     }


    fun categoriaAdapter(): ArrayAdapter<String>{
        val catAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categorias)
        return catAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_cadastro, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_salvar ->{
                validaForm()
                if(produto == null) taskSalvar() else taskAtualizar()

            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun taskSalvar(){
        doAsync {
            //operador Elvis ?: vai receber o Produto a ser editado ou vai ser instanciado novo produto caso nao tenha sido passado produto como param
            val prod = produto?: Produto()

            //copiando os valores do form para o obj que vai ser salvo
            prod.nome = inputNome.string
            prod.local = inputLocal.string
            prod.preco = inputPreco.string.toFloat()
            prod.categoria = spinnerInputCategoria.selectedItem.toString()
            val response = ProdutoService.save(prod)

            uiThread {
                toast("Produto salvo com sucesso!")
                finish()
            }
        }
    }

    fun taskAtualizar(){
        doAsync {
            val prod = produto
            prod.nome = inputNome.string
            prod.local = inputLocal.string
            prod.preco = inputPreco.string.toFloat()
            prod.categoria = spinnerInputCategoria.selectedItem.toString()

            val response = ProdutoService.update(prod)
            uiThread {
                toast("Produto atualizado com sucesso!")
                finish()
            }
        }

    }


    fun validaForm(){
        if(inputNome.isEmpty()){
            inputNome.error = getString(R.string.msg_erro_form_nome)
        }
        if (inputLocal.isEmpty()){
            inputLocal.error = getString(R.string.msg_erro_form_local)
        }
        if (inputPreco.text.isEmpty()){
            inputPreco.error = getString(R.string.msg_erro_form_preco)
        }
    }




}







