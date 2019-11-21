package view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_my_account.*

class MyAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
    button.setOnClickListener {

load_recyler()

    }

    }
fun load_recyler()
{
    val namess: String=intent.getStringExtra("name")

    intent.putExtra("name",namess)

    val intent= Intent(this, Unauthorized_list::class.java)
    startActivity(intent)
}



}
