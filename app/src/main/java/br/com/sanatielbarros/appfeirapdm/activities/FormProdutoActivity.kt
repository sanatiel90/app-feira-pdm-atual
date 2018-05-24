package br.com.sanatielbarros.appfeirapdm.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.sanatielbarros.appfeirapdm.R
import br.com.sanatielbarros.appfeirapdm.domain.Produto
import br.com.sanatielbarros.appfeirapdm.extensions.setupToolbar

class FormProdutoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_produto)
        val argument = intent.getParcelableExtra<Produto>("produto")
        var title = "Novo Produto"
        if(argument != null) title = argument.nome

        setupToolbar(R.id.toolbar,title,true)
    }
}
