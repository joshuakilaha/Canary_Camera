package view

import Adapter.MyAccount
import DataClass.MyAccount_details
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_unauthorized_list.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class Unauthorized_list : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unauthorized_list)

        recycler_view.layoutManager= LinearLayoutManager(parent)



        val users_list=ArrayList<MyAccount_details>()


        loaddata(users_list);
    }

    private fun loaddata( usersList: ArrayList<MyAccount_details>) {

        val progressbar: ProgressDialog = ProgressDialog(this)
        progressbar.setMessage("Loading..........")
        progressbar.setCancelable(false)
        progressbar.show()




        val queue = Volley.newRequestQueue(this)
        val url = "http://project-daudi.000webhostapp.com/canary_camera/check_anuthorized.php"
        //val url = "http://192.168.43.121/canary_camera/check_anuthorized.php"

//        val namess: String=intent.getStringExtra("name")

    //    intent.putExtra("name",namess)
        // val url = "http://192.168.43.121/recycler/check_tiko.php"

        // Request a string response from the provided URL.
        val stringRequest=object :StringRequest(Request.Method.POST, url, Response.Listener { unauthorized_data ->
Log.i("erraa",unauthorized_data)




                try {

                    val json_object = JSONObject(unauthorized_data)
                    val response: String = json_object.getString("unauthorized_data")



                    if (response.equals("no_data")) {
                        Toast.makeText(applicationContext, "No data", Toast.LENGTH_SHORT).show()
                    }
                    else if (response.equals("session has expired.Login again"))
                    {
Toast.makeText(applicationContext,"Session expired,login again",Toast.LENGTH_SHORT).show()
                        val intent= Intent(this,LoginScreen::class.java)
                        startActivity(intent)
                    }else {
                        val data_array = json_object.getJSONArray("unauthorized_data")
                        for (i in 0..data_array.length() - 1) {

                            val tickets_object = data_array.getJSONObject(i)
                            val tickets_dataaa = MyAccount_details(
                                    tickets_object.getString("name"),
                                    tickets_object.getString("date"),

                                    tickets_object.getString("image_url")

                            )

                            usersList.add(tickets_dataaa)
                        }

                        progressbar.dismiss()
                        val adap = MyAccount(usersList,applicationContext)
                        recycler_view.adapter = adap
                    }


                    }
                    catch(e: JSONException)
                    {

                        progressbar.dismiss()

                        Toast.makeText(this, "no data received" + e, Toast.LENGTH_LONG).show()
                        Log.i("catch_e", e.toString())


                    }



                }, Response.ErrorListener
        {


            progressbar.dismiss()

            Toast.makeText(this,"eeror"+it, Toast.LENGTH_LONG).show()
            Log.d("hhht",it.toString())

        })
        {
            val MyPreferences = "mypref"
            val sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
            // String session_ide= sharedPreferences.getString("sessions_ids","");

            val editor: SharedPreferences.Editor = sharedPreferences.edit()

         val session_id: String?=sharedPreferences.getString("sessions_ids","")
            val phone_number:String?=sharedPreferences.getString("phone_number","")


            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()


                if (session_id != null) {
                    params.put("session_id",session_id)
                }

                if (phone_number != null) {
                    params.put("name",phone_number)
                };


                // return super.getParams();
                return params
            }
        }




        //psendind params with volley request
// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}
