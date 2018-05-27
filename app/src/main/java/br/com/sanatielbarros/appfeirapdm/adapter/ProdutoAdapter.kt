package br.com.sanatielbarros.appfeirapdm.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sanatielbarros.appfeirapdm.R
import br.com.sanatielbarros.appfeirapdm.domain.Produto
import br.com.sanatielbarros.appfeirapdm.utils.AndroidUtils.formatMoney
import kotlinx.android.synthetic.main.adapter_produto.view.*

/**
 * Created by Sanatiel on 05/05/2018.
 */

/*
* classe que vai inflar o layout de adapter de produtos (uma linha da lista) e fazer a ligacao(bind) com cada produto
* construtor recebe uma List de Produtos e uma funcao onClick q vai ser a função a ser chamada quando o produto da lista for clicado
* retorna um adapter do tipo ProdutoAdapter, q é uma linha a ser adicionada num RecyclerView
* */
class ProdutoAdapter(val produtos: List<Produto>, val onClick: (Produto) -> Unit): RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    //retorna quant de produtos na lista
    override fun getItemCount() = this.produtos.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProdutoViewHolder {
        //infla a view do layout
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_produto, parent, false)
        //retorna o viewholder q contem todas as views
        val holder = ProdutoViewHolder(view)
        return holder
    }


    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        //recupera o obj Produto
        val produto = produtos[position]
        //declara a var view com base no item do holder (item da lista), para facilitar o acesso dos atributos
        val view = holder.itemView
        with(view){
            //atualiza dados do produto
            txtNomeProduto.text = produto.nome
            txtPreco.text =  "Preço: "+formatMoney(produto.preco)         //produto.preco.toString()
            txtLocal.text = "Local: "+produto.local
            //add evento de click na linha
            setOnClickListener { onClick(produto) }
        }
    }



    class ProdutoViewHolder(view: View): RecyclerView.ViewHolder(view){

    }


}