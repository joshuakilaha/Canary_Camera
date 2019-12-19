package view

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class balance_viewmodel: ViewModel()
{


    fun getbalance(context: Context) :String {

        val pp: Int = 22000
        val context = context
        val queue = Volley.newRequestQueue(context)
        val url = "https://project-daudi.000webhostapp.com/canary_camera/check_balance.php"
         var resss:String="02"

        val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {

            Log.i("res", it.toString())
            if (it == "failure") {


                 var res = "0"
                resss=res
            } else {

                 var res = it.toString()
                resss=res

            }

        }, Response.ErrorListener {
            Log.i("res", it.toString())

        })
        {}
        queue.add(stringRequest)


        return resss

       // return pp

    }


}