package view;

import Controler.*;
import Model.CameraConfig;
import Model.CameraError;
import Model.HiddenCameraActivity;
import Model.HiddenCameraUtils;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.*;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginScreen extends HiddenCameraActivity {

    private static final int REQ_CODE_CAMERA_PERMISSION = 1253;
    private static int timeOut = 5000;

    TextView SignUp;
    Button Login;
    EditText Username,Password;
    ImageView Image;

    private  int attempts = 3;
    private CameraConfig mCameraConfig;
public static final String  AES="AES";

    public static final View view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);


        Username = findViewById(R.id.Email);
        Password = findViewById(R.id.PassWord);
        Image = findViewById(R.id.image);
        SignUp = findViewById(R.id.signUp_tv);
        Login = findViewById(R.id.login_loginScreen);

        Image.setVisibility(View.GONE);




        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignUp = new Intent(LoginScreen.this, view.SignUp.class);
                startActivity(toSignUp);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LogIn(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


                            //Calling Camera Config class

        mCameraConfig = new CameraConfig()
                .getBuilder(this)
                .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
                .setCameraResolution(CameraResolution.HIGH_RESOLUTION)
                .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                .setImageRotation(CameraRotation.ROTATION_270)
                .setCameraFocus(CameraFocus.AUTO)
                .build();

        //Check for the camera permission for the runtime
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            //Start camera preview
            startCamera(mCameraConfig);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQ_CODE_CAMERA_PERMISSION);
        }


    }



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
                                            /////Login//////////

    private void LogIn(View view) throws Exception {




        final String email = Username.getText().toString();
        final String pass = Password.getText().toString();

       // Encrypt encrypt=new Encrypt();

       // final  String pass_value=encrypt.encrypt(email,pass);

int username_value=email.length();
        int pass_value=pass.length();


        if(TextUtils.isEmpty(email)  ){
            Username.setError("Username required ");

        }
       else if ( username_value!=9)
        {
            Toast.makeText(LoginScreen.this,"not a valid phone number",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(pass)){
            Password.setError("Password required ");

        }


        else if (!isMatchingRegex(pass))
        {
            Toast.makeText(LoginScreen.this,"password must include alphabets,special characters,numbers",Toast.LENGTH_LONG).show();

        }

        else {

         check_login(view);


        }


    }



                                 ///////////////////camera permissions/////////////


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE_CAMERA_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera(mCameraConfig);
            } else {
                Toast.makeText(this, R.string.error_camera_permission_denied, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onImageCapture(@NonNull File imageFile) {

        // Convert file to bitmap.
        // Do something.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        uploadimage(bitmap,view);


        ((ImageView) Image).setImageBitmap(bitmap);

    }





    ////////Camera Error/////////
    @Override
    public void onCameraError(@CameraError.CameraErrorCodes int errorCode) {
        switch (errorCode) {
            case CameraError.ERROR_CAMERA_OPEN_FAILED:
                //Camera open failed. Probably because another application
                //is using the camera
                // Toast.makeText(this, R.string.error_cannot_open, Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_IMAGE_WRITE_FAILED:
                //Image write failed. Please check if you have provided WRITE_EXTERNAL_STORAGE permission
                Toast.makeText(this, R.string.error_cannot_write, Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE:
                //camera permission is not available
                //Ask for the camera permission before initializing it.
                Toast.makeText(this, R.string.error_cannot_get_permission, Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION:
                //Display information dialog to the user with steps to grant "Draw over other app"
                //permission for the app.
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_FRONT_CAMERA:
                Toast.makeText(this, R.string.error_not_having_camera, Toast.LENGTH_LONG).show();
                break;
        }
    }





                                             ////////canary token/////////////////
    private void cannary(){
        OkHttpClient client = new OkHttpClient();
        // String url="http://canarytokens.com/static/about/images/87m4t8nep7qpmcnu6stnrot0a/submit.aspx";
        String url = "http://canarytokens.com/traffic/jf83bwt57jglpjru2e6kd2l8y/post.jsp";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    LoginScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //takePicture();
                        }
                    });
                }
            }
        });
    }



    /////////Image to server

    public  void uploadimage(final Bitmap bitmap,final View view)

    {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginScreen.this, "Please wait...","Processing...",true);


          String upload_image_url="https://project-daudi.000webhostapp.com/canary_camera/upload_image.php";

     //  String upload_image_url="http://192.168.43.121/canary_camera/upload_image.php";
       // String upload_image_url="https://localhost/canary_camera/upload_image.php";


        final StringRequest stringrequest = new StringRequest(com.android.volley.Request.Method.POST, upload_image_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override


                    public void onResponse(String response) {
                        try {
                          //  progressDialog.dismiss();
                            Log.i("RESPONSE", response);
                            JSONObject json = new JSONObject(response);
                            String str=json.getString("response");
                            if (str.equals("successful"))
                            {  //Toast.makeText(getBaseContext(), "The image is uploaded", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                finish();
                            }



                        } catch (JSONException e) {
                            Log.d("JSON Exception", e.toString());
                            Toast.makeText(getBaseContext(),
                                    "Error while loading data!",
                                    Toast.LENGTH_LONG).show();
                           progressDialog.dismiss();
                           // finish();

                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();
                    //This indicates that the reuest has either time out or there is no connection
                } else if (error instanceof AuthFailureError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();
                } else if (error instanceof ServerError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();
                } else if (error instanceof NetworkError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();                }
                else if (error instanceof ParseError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();                }


                Log.e("enda", error.toString());


              //  Toast.makeText(LoginScreen.this, "nnnnnn"+error, Toast.LENGTH_LONG).show();

            }
        })


        {
              protected Map<String, String> getParams() throws AuthFailureError {





                Map<String, String> params = new HashMap<>();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                byte[] imageBytes = baos.toByteArray();
                String image_string= Base64.encodeToString(imageBytes, Base64.DEFAULT);

                  final String name=   "+254"+Username.getText().toString();
                  Date currentTime = Calendar.getInstance().getTime();



                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                  String currentDateandTime = sdf.format(new Date());
                  SimpleDateFormat time = new SimpleDateFormat(" HH:mm:ss ");
                  SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                  SimpleDateFormat currenttime = new SimpleDateFormat("HH.mm.ss_dd.MM.yyyy");



                 String current_date_time=currenttime.format(new Date());
                  String current__time=time.format(new Date());
                  String current_date=date.format(new Date());


                  String image_url="https://project-daudi.000webhostapp.com/canary_camera/canary_camera/."+name+"."+current_date_time+".jpg";
                String image_name=name+current_date_time;






                  params.put("name",name);//the username that one will try to login with
                  params.put("url","http://192.168.43.121/canary_camera/canary_camera/");//url path to location of picture
                params.put("image_url", image_url);
                  params.put("date_now", current_date);
                  params.put("time_now", current__time);
                  params.put("date_time", current_date_time);

                  params.put("image_name",image_name);



                  params.put("image",image_string );


                // return super.getParams();
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(LoginScreen.this);
        requestQueue.add(stringrequest);


    }


    public void check_login(final View view)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginScreen.this, "Please wait...","Processing...",true);

     //String login_url="http://192.168.43.121/canary_camera/login3.php";

    String login_url="https://project-daudi.000webhostapp.com/canary_camera/login3.php";
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.POST, login_url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.i("Response", response.toString());


                try {

                    login_in_function(progressDialog,response);
                   // login_function(progressDialog,response);

                    }


                 catch (JSONException e) {
                    e.printStackTrace();
                 //   responses.equals(login_response);
                    Log.i("JSONEXCEPTION", e.toString());
                    progressDialog.dismiss();

                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("volleyError",error.toString());


                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    snackbar("No connection",view);
                       progressDialog.dismiss();
                    //This indicates that the reuest has either time out or there is no connection
                } else if (error instanceof AuthFailureError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();
                } else if (error instanceof ServerError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();
                } else if (error instanceof NetworkError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();                }
                else if (error instanceof ParseError) {
                    snackbar("No connection",view);
                    progressDialog.dismiss();                }


           }
        })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                final Encrypt encrypt=new Encrypt();

                String MyPreferences="mypref";
                SharedPreferences sharedPreferences=getSharedPreferences(MyPreferences, (Context.MODE_PRIVATE));
                // String session_id= sharedPreferences.getString("sessions_ids","");


                SharedPreferences.Editor editor=sharedPreferences.edit();
                String session_idss=sharedPreferences.getString("session_ids","");

                // editor.putString("sessions_ids",session_ids);
                // editor.putString("phone_numbers",phone_number_);
               // editor.commit();

               params.put("session_ids",session_idss);

                params.put("username","+254"+Username.getText().toString());
                try {
                    params.put("encrypted_password",encrypt.encrypt(Password.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return  params;
            }


        };

        final RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);




    }

public void  snackbar(String error,View view)
    {
Snackbar mysnackbar=Snackbar.make(view,error,Snackbar.LENGTH_LONG);
    mysnackbar.show();
    }





private  void login_in_function(final ProgressDialog progressDialog,String response) throws JSONException {
   //  String status_userdata=jsonObjectt.getString("user_data");

        JSONObject jsonObject_response = new JSONObject(response);

    String responses=jsonObject_response.getString("response");



if (responses.equals("wrong_pass"))
    {
        attempts--;
        if (attempts < 3 && attempts != 0) {


            //bothe the username and password are false

            Toast.makeText(getApplicationContext(), "Wrong credentials.Try again", Toast.LENGTH_SHORT).show();

            progressDialog.dismiss();
        } else {

            //both the username and password are false

            // Toast.makeText(this, "Wrong credentials.Try again", Toast.LENGTH_SHORT).show();

            // Toast.makeText(getApplicationContext(), "Authentication Failed!!", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

            takePicture();
            cannary();
        }


    }
    else if(responses.equals("!phone_number"))
    {
        Toast.makeText(this,"Wrong credentials.Enter the correct username",Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }
    else if (responses.equals("exists"))
{
    finish();
}
    else {
        //////{"user_data":{"user_data":[{"firstname":"dausi","lastname":"mumo","email":"devimumo@gmail.com"}]},"session_id":"b1850067893af7e877b9d00e2a7adaa8"}
        JSONObject jsonObject = new JSONObject(response);
        String responsed = jsonObject.getString("response");
        String session_id=jsonObject.getString("session_id");

        if (responsed.equals("successful"))   {


            Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_LONG).show();
            String MyPreferences="mypref";
            SharedPreferences sharedPreferences=getSharedPreferences(MyPreferences, (Context.MODE_PRIVATE));
           // String session_ide= sharedPreferences.getString("sessions_ids","");

            SharedPreferences.Editor editor=sharedPreferences.edit();
            // String phone_number_= phone_number.getText().toString().trim();
            final String email = Username.getText().toString();

             editor.remove("sessions_ids");
            editor.remove("phone_number");

        editor.putString("sessions_ids",session_id);
            editor.putString("phone_number",email);

            // editor.putString("phone_numbers",phone_number_);
            editor.commit();


get_data(response) ;
            progressDialog.dismiss();
        }
        ///////
    }





    }



private  void get_data(String response) throws JSONException
{
    JSONObject jsonObject = new JSONObject(response);

    String user_data=jsonObject.getString("user_data");



    JSONObject jjk=new JSONObject(user_data);
    JSONArray jsonArray=jjk.getJSONArray("user_data");

    for (int i = 0; i < jsonArray.length(); i++){

        JSONObject tickets_object = jsonArray.getJSONObject(i);
        String firstname=tickets_object.getString("firstname");
        String lastname=tickets_object.getString("lastname");
        String email_=tickets_object.getString("email");

        Intent welcome=new Intent(LoginScreen.this,Welcome.class);
String username=firstname+lastname;
welcome.putExtra("name",username);
        welcome.putExtra("email",email_);
        startActivity(welcome);



    }
}
}
