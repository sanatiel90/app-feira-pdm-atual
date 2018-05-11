package br.com.sanatielbarros.appfeirapdm.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import br.com.sanatielbarros.appfeirapdm.R
import br.com.sanatielbarros.appfeirapdm.domain.CategoriaProduto
import br.com.sanatielbarros.appfeirapdm.extensions.addFragment
import br.com.sanatielbarros.appfeirapdm.extensions.setupToolbar
import br.com.sanatielbarros.appfeirapdm.extensions.toast
import br.com.sanatielbarros.appfeirapdm.fragments.ProdutosFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(R.id.toolbar) //config a toolbar
        setupNavDrawer()
        fab.setOnClickListener{
            toast("Novo produto")
        }

        if (savedInstanceState == null){
            val ft = supportFragmentManager.beginTransaction()
            val fragProdutos = ProdutosFragment()
            ft.add(R.id.layoutFragment, fragProdutos, "ProdutosFragment")
            ft.commit()
            //addFragment(R.id.container, ProdutosFragment())
        }

    }

    //configura o navigation drawer
    fun setupNavDrawer(){
        //componentes drawer_layout e toolbar foram recuperados pela Kotlin Extensions
        val toogle = ActionBarDrawerToggle(
                this,drawer_layout,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

    }

    //trata evento de click no Navigation Drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item_home -> {
                toast("Você está na página inicial")
            }

            R.id.nav_item_frutas -> {
                startActivity<ProdutosActivity>("categoria" to CategoriaProduto.frutas)
            }

            R.id.nav_item_verduras -> {
                startActivity<ProdutosActivity>("categoria" to CategoriaProduto.verduras)
            }

            R.id.nav_item_graos -> {
                startActivity<ProdutosActivity>("categoria" to CategoriaProduto.graos)
            }

            R.id.nav_item_carnes -> {
                startActivity<ProdutosActivity>("categoria" to CategoriaProduto.carnes)
            }

            R.id.nav_item_outros -> {
                startActivity<ProdutosActivity>("categoria" to CategoriaProduto.outros)
            }

            R.id.nav_item_sobre -> {
                toast("Sobre")
            }
        }
        //fecha o menu depois de tratar o evento
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
