package br.com.sanatielbarros.appfeirapdm.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Sanatiel on 05/05/2018.
 */
@Entity(tableName = "products")
class Produto() : Parcelable   {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var nome: String = ""
    var categoria: String = ""
    var preco: String = ""
    var created_at: String = ""
    var local : String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        nome = parcel.readString()
        categoria = parcel.readString()
        preco = parcel.readString()
        created_at = parcel.readString()
        local = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nome)
        parcel.writeString(categoria)
        parcel.writeString(preco)
        parcel.writeString(created_at)
        parcel.writeString(local)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Produto> {
        override fun createFromParcel(parcel: Parcel): Produto {
            return Produto(parcel)
        }

        override fun newArray(size: Int): Array<Produto?> {
            return arrayOfNulls(size)
        }
    }


}