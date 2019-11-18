package view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

public class SignUp extends AppCompatActivity {

    TextView signup;
    public ProgressBar progressbar ;


    EditText firstName, lastName, email, password,mobile_no, confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

progressbar=findViewById(R.id.progress);
        signup = findViewById(R.id.signUp);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();

               // Intent toLogin = new Intent(SignUp.this, view.LoginScreen.class);
               // startActivity(toLogin);
            }
        });

    }


    public void  signup()
    {

        progressbar.setVisibility(View.VISIBLE);
signup.setVisibility(View.GONE);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        mobile_no=findViewById(R.id.mobileNumber);
        password=findViewById(R.id.passWord);
        confirmpassword=findViewById(R.id.confirmPassword);

        String FirstName =firstName.getText().toString();
        String LastName = lastName.getText().toString();
        String  Email = email.getText().toString();
        String Mobile_no=mobile_no.getText().toString();
        String Password = password.getText().toString();
        String Confirmpassword = confirmpassword.getText().toString();




        if (!FirstName.isEmpty() ||  !LastName.isEmpty() || !Email.isEmpty() || !Password.isEmpty()  || !Confirmpassword.isEmpty())
        {

            if (Password.equals(Confirmpassword))
            {
                register(FirstName,LastName,Email,Mobile_no,Password);

            }
            else
            {
                confirmpassword.setError("Psswords must match");
                progressbar.setVisibility(View.INVISIBLE);
                signup.setVisibility(View.VISIBLE);

            }
        }
//effected when edittext is empty
        else
        {


            firstName.setError("Username Required");

            lastName.setError("lastname Required");
            email.setError("Email Required");
            mobile_no.setError("Mobileno required");
            password.setError("Password Required");
            confirmpassword.setError("Confirmpassword Required");
            progressbar.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.VISIBLE);


        }}
    private  void  register(final String FirstName, final String LastName, final String Email, final String Mobile_no, final String Password )
    {

        //String url="https://192.168.43.121/canary_camera/register.php";

        String url="https://project-daudi.000webhostapp.com/canary_camera/register.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

Log.i("Responsed",response.toString());
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(response);
                    String responses=jsonObject.getString("response");


                    switch (responses)
                    {
                        case "successful":
                            Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                            startActivity(intent);

                            break;

                        case "mobile_no_exists":
                            Toast.makeText(getApplicationContext(),"Register with a different mobile  no",Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.INVISIBLE);
                            signup.setVisibility(View.VISIBLE);
                            break;

                        case "unsuccessful":
                            Toast.makeText(getApplicationContext(),"Registration unsuccessful",Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.INVISIBLE);
                            signup.setVisibility(View.VISIBLE);
                            break;

                        default:
                            Toast.makeText(getApplicationContext(),"Try again"+response,Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.INVISIBLE);
                            signup.setVisibility(View.VISIBLE);
                            break;




                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Volley_Error",error.toString());
                progressbar.setVisibility(View.INVISIBLE);
                signup.setVisibility(View.VISIBLE);

            }
        }) {



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                Encrypt encrypt=new Encrypt();
                params.put("firstname",FirstName);

                params.put("lastname",LastName);
                params.put("email",Email);
                params.put("mobile_no","+254"+Mobile_no);
                try {
                    params.put("password",encrypt.encrypt(Mobile_no,Password));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return  params;
            }



        }  ;



        final RequestQueue requestQueue= Volley.newRequestQueue(SignUp.this);
        requestQueue.add(stringRequest);








    }
}
