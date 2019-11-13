package view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;

public class StartUpScreen extends AppCompatActivity {

    Button SignUp,Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_screen);

        SignUp = findViewById(R.id.signUp_StartScreen);
        Login = findViewById(R.id.login_StartScreen);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignUp = new Intent(StartUpScreen.this, view.SignUp.class);
                startActivity(toSignUp);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(StartUpScreen.this, view.LoginScreen.class);
                startActivity(toLogin);
            }
        });
    }
}
