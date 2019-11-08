package view

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Toast

import com.example.myapplication.R

class Transactions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        Toast.makeText(this,"Waiting for transactions",Toast.LENGTH_SHORT).show()



    }
}
