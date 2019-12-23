package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_mpesa__deposit.*
import org.json.JSONObject
import view.MyAccount
import java.util.*


class Mpesa_Deposit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mpesa__deposit)



        check_transaction_status()
        mpesa.setOnClickListener {
            val ss=phonenumber_value.text
val count=ss.count()
if (phonenumber_value.text.isEmpty())
{
    phonenumber_value.setError("Phone number required")
}
            else if (count!=9)
{
    phonenumber_value.setError("wrong number format")

}
            else
{            deposit_from_mpesa()
}
        }


    }




    fun deposit_from_mpesa()
    {
        val progressbar: ProgressDialog = ProgressDialog(this)
        progressbar.setMessage("Loading..........")
        progressbar.setCancelable(false)
        progressbar.show()

        val queue = Volley.newRequestQueue(this)
        val url = "http://project-daudi.000webhostapp.com/mpesa_daraja/lipa_online.php"

        val stringrequest=object :StringRequest(Request.Method.POST,url,Response.Listener { responsess->


            Log.i("responses",responsess.toString())


            val server_response=JSONObject(responsess);
            val response_server: String = server_response.getString("server_response")

            when(response_server) {
                "!post_phone_number" -> {

                    Toast.makeText(this,"phonenumber needed",Toast.LENGTH_SHORT).show()
                    progressbar.dismiss()
                    val intent= Intent(this, Mpesa_Deposit::class.java)
                    startActivity(intent)

                }
                "invalid phonenumber,try again" -> {
                    Toast.makeText(this,"transaction in progress",Toast.LENGTH_SHORT).show()
                    progressbar.dismiss()
                    val intent= Intent(this, Mpesa_Deposit::class.java)
                    startActivity(intent)

                }
                "timeout" -> {

                    Toast.makeText(this,"transaction timeout",Toast.LENGTH_SHORT).show()
                    progressbar.dismiss()
                    val intent= Intent(this, Mpesa_Deposit::class.java)
                    startActivity(intent)

                }
                "successful" -> {

                    Toast.makeText(this,"transaction successful",Toast.LENGTH_SHORT).show()
                    progressbar.dismiss()
                    val intent= Intent(this, Mpesa_Deposit::class.java)
                    startActivity(intent)

                }
                "cancelled" -> {

                    Toast.makeText(this,"transaction cancelled",Toast.LENGTH_SHORT).show()
                    progressbar.dismiss()
                    val intent= Intent(this, Mpesa_Deposit::class.java)
                    startActivity(intent)

                }
                "limited" -> {
                    Toast.makeText(this,"transaction limited",Toast.LENGTH_SHORT).show()
                    progressbar.dismiss()
                    val intent= Intent(this, Mpesa_Deposit::class.java)
                    startActivity(intent)

                }


                else -> {

                    Toast.makeText(this,"transaction timed out",Toast.LENGTH_SHORT).show()
                    progressbar.dismiss()

                }
            }




        },Response.ErrorListener {
Log.i("ErrorListener",it.toString())

            progressbar.dismiss()


        })


        {

            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()


                val phone_number="254"+phonenumber_value.text
                val amount=deposit_amounts.text
                params.put("phone_number", phone_number.toString())
                params.put("amount", amount.toString())



                return params
            }
        }

        queue.add(stringrequest)
        stringrequest.setRetryPolicy(DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))

    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.phonenumber_value ->
                    if (checked) {
                        Toast.makeText(this,"Already is checked",Toast.LENGTH_SHORT).show()
                    }
                else
                    {phonenumber_value.setText(713836954.toString())
                    }
                R.id.deposit_amount ->
                    if (checked) {
Toast.makeText(this,"Already is checked",Toast.LENGTH_SHORT).show()                    }
            }

        }
    }

fun check_transaction_status()
{

    radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)

when(radio.text) {

    mine.text->{
        //Toast.makeText(applicationContext," On checked change : ${radio.text}",                        Toast.LENGTH_SHORT).show()
phonenumber_value.setText(713836954.toString())

    }
    other.text->{



    }
}

            })


}

    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)

    }



}
