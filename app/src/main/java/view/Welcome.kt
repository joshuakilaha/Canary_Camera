package view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Mpesa_Deposit

import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.myaccount_custom.*
import java.util.*


class Welcome : AppCompatActivity() {
//    val cc: Context=this.applicationContext

    private lateinit var model: balance_viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

model= ViewModelProviders.of(this).get(balance_viewmodel::class.java)
        //val bal: String="kshs. "+model.bala.toString()
val bal=model.getbalance(this).toString()
        balances.text=bal

        Card_click(View(this))

val user__name: String?=intent.getStringExtra("name")
        val email: String?=intent.getStringExtra("email")
user_name.text=user__name
user_email.text=email
    }



    fun volley () {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.google.com"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    // textView.text = "Response is: ${response.substring(0, 500)}"
                },
                Response.ErrorListener { "That didn't work!" })



// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun Card_click(view: View) {

        when(view.id) {
            R.id.transactions ->{
                Toast.makeText(this,"clicked on transaction",Toast.LENGTH_SHORT).show()
                val intent=Intent(this,Transactions::class.java)
                startActivity(intent)
            }

            R.id.loans->{
                Toast.makeText(this,"clicked on loans",Toast.LENGTH_SHORT).show()
            }
            R.id.withdraw->{
                Toast.makeText(this,"clicked on withdraw",Toast.LENGTH_SHORT).show()
            }
            R.id.balance->{
                Toast.makeText(this,"clicked on balance",Toast.LENGTH_SHORT).show()
            }
            R.id.my_account->{
                Toast.makeText(this,"clicked on my account",Toast.LENGTH_SHORT).show()
                val namess=intent.getStringExtra("name")

                val intent=Intent(this, MyAccount::class.java)
                intent.putExtra("name",namess)
                startActivity(intent)
            }

            R.id.mpesa->{
                val intent=Intent(this, Mpesa_Deposit::class.java)
                startActivity(intent)
               // Toast.makeText(this,"clicked on mpesa",Toast.LENGTH_SHORT).show()
            }
        }
    }


}
