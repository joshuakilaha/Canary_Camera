package view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.myapplication.R
import java.util.HashMap

class Welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

Card_click(View(this))



    }



    fun volley ()
    {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.google.com"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                   // textView.text = "Response is: ${response.substring(0, 500)}"
                },
                Response.ErrorListener {  "That didn't work!" })



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
            }

            R.id.mpesa->{
                Toast.makeText(this,"clicked on mpesa",Toast.LENGTH_SHORT).show()
            }
        }
    }


}
