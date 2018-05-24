package br.com.sanatielbarros.appfeirapdm.adapter

import android.support.v4.app.FragmentManager
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import br.com.sanatielbarros.appfeirapdm.fragments.FavoritosFragment
import br.com.sanatielbarros.appfeirapdm.fragments.ProdutosFragment


/**
 * Created by Sanatiel on 16/05/2018.
 */
//classe que sera adapter para alimentar viewpager
class TabsAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }


    fun getTabTitle(position: Int) = when(position){
        0 -> "Novidades"
        else -> "Favoritos"
    }

    //retorna titulo da pagina(nesse caso, titulo da tab)
    override fun getPageTitle(position: Int): CharSequence {
        val titulo = getTabTitle(position)
        return titulo //context.getString(titulo.string)
    }

    //retorna o fragment da tab de acordo com o indice position
    override fun getItem(position: Int): Fragment {
        //caso for a tab 1, mostrar frag de favoritos, caso contrario mostrar frag prdutos
        if(position == 1){
            return FavoritosFragment()
        }

        val frag: Fragment = ProdutosFragment()
        return frag
    }


}
