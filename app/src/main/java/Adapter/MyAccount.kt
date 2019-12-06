package Adapter

import DataClass.MyAccount_details
import android.content.Context
import android.content.Intent
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.myaccount_custom.view.*
import kotlinx.android.synthetic.main.myaccount_custom.view.*
import view.Unauthorized_list
//import kotlinx.android.synthetic.main.video_row.view.*
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast.makeText as makeText1


class MyAccount (val user_list:ArrayList<MyAccount_details>,var c: Context) :RecyclerView.Adapter<MyAccount.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
val view=LayoutInflater.from(parent.context).inflate(R.layout.myaccount_custom,parent,false)


    return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
return  user_list.size   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


       // val picasso = Picasso.get()
val user_data: MyAccount_details=user_list[position]


        val imag: ImageView= holder?.itemview.imageV

    holder?.itemview.name.text=user_data.name

        holder?.itemview.date.text=user_data.date

       // imag.setImageURI(user_data.image)

val local_host: String="http://192.168.43.121/canary_camera/canary_camera/"
        val online_url: String="https://project-daudi.000webhostapp.com/canary_camera/canary_camera/"


        Picasso.with(c) // give it the context
                .load(online_url+user_data.image)
                .into(imag)







    }

    class  ViewHolder(val itemview: View): RecyclerView.ViewHolder(itemview)
    {
    }




    }









