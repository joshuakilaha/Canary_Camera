package view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.example.myapplication.R

class Welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

Card_click(View(this))



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
