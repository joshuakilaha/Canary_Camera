package view;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;

public class SignUp extends AppCompatActivity {

    TextView Login;
    EditText FirstName, LastName, Email, Password, Confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirstName = findViewById(R.id.firstName);
        LastName = findViewById(R.id.lastName);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.passWord);
        Confirmpassword = findViewById(R.id.confirmPassword);


        Login = findViewById(R.id.Login_tv);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(SignUp.this, view.LoginScreen.class);
                startActivity(toLogin);
            }
        });

    }
}
