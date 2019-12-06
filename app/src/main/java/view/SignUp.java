package view;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;
import java.util.regex.Pattern;

import javax.crypto.spec.SecretKeySpec;

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



    //methods to check characters in password
    private static final Pattern[] inputRegexes = new Pattern[4];

    static {
        inputRegexes[0] = Pattern.compile(".*[A-Z].*");
        inputRegexes[1] = Pattern.compile(".*[a-z].*");
        inputRegexes[2] = Pattern.compile(".*\\d.*");
        inputRegexes[3] = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
    }

    private static boolean isMatchingRegex(String input) {
        boolean inputMatches = true;
        for (Pattern inputRegex : inputRegexes) {
            if (!inputRegex.matcher(input).matches()) {
                inputMatches = false;
            }
        }
        return inputMatches;
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


        int username_value=Mobile_no.length();
        int pass_value=Password.length();

if(FirstName.isEmpty() |  LastName.isEmpty())
{
    Toast.makeText(SignUp.this,"Names required",Toast.LENGTH_LONG).show();
    progressbar.setVisibility(View.INVISIBLE);
    signup.setVisibility(View.VISIBLE);

}
else if(Email.isEmpty())
{
    email.setError("email required");
    progressbar.setVisibility(View.INVISIBLE);
    signup.setVisibility(View.VISIBLE);
}

        else if(TextUtils.isEmpty(Mobile_no)  ){
            mobile_no.setError("Phone number  required ");
    progressbar.setVisibility(View.INVISIBLE);
    signup.setVisibility(View.VISIBLE);

        }
        else if ( username_value!=9)
        {
            Toast.makeText(SignUp.this,"not a valid phone number",Toast.LENGTH_LONG).show();
            progressbar.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.VISIBLE);

        }
else if(TextUtils.isEmpty(Password)){
    password.setError("Password required ");
    progressbar.setVisibility(View.INVISIBLE);
    signup.setVisibility(View.VISIBLE);

}
else  if ( pass_value<6)
{
    Toast.makeText(SignUp.this,"password must be at least 6 characters",Toast.LENGTH_LONG).show();
    progressbar.setVisibility(View.INVISIBLE);
    signup.setVisibility(View.VISIBLE);

}


else if (!isMatchingRegex(Password))
{
    Toast.makeText(SignUp.this,"password must include alphabets,special characters,numbers",Toast.LENGTH_LONG).show();
    progressbar.setVisibility(View.INVISIBLE);
    signup.setVisibility(View.VISIBLE);

}
        else if (!Password.equals(Confirmpassword))
        {

            confirmpassword.setError("Psswords must match");
            progressbar.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.VISIBLE);
        }
        else
        {
            register(FirstName,LastName,Email,Mobile_no,Password);


        }







    }
    private  void  register(final String FirstName, final String LastName, final String Email, final String Mobile_no, final String Password )
    {

      //  String url="http://192.168.43.121/canary_camera/register.php";

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

                    params.put("password",encrypt.encrypt(Password));

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
