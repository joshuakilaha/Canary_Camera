package View;

import Controler.CameraConfiguration.*;
import Model.CameraConfig;
import Model.CameraError;
import Model.HiddenCameraActivity;
import Model.HiddenCameraUtils;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.myapplication.R;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends HiddenCameraActivity {

    private static final int REQ_CODE_CAMERA_PERMISSION = 1253;

    ImageView Image;
    Button Login;
    TextView Counter;
    EditText Username, Password;
    private  int attempts = 3;
    private CameraConfig mCameraConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Counter = findViewById(R.id.counter);
        Login = findViewById(R.id.capture);
        Image = findViewById(R.id.image);





        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn();

            }
        });

       Image.setVisibility(View.GONE);

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



                                             /////Login//////////
    private void LogIn() {

        final String name = Username.getText().toString();
        final String pass = Password.getText().toString();
        attempts --;


        // checks whether the credentials are the right ones,i true hen the user is taken to another activity
        if (name.equals("rob") && pass.equals("r1234")){
            Toast.makeText(this, "access granted", Toast.LENGTH_SHORT).show();
            Intent welcome = new Intent(MainActivity.this, Welcome.class);
            startActivity(welcome);
        }
else
        {

            if (  attempts<3 && attempts!=0) {


            //bothe the username and password are false

            Toast.makeText(this, "Wrong credentials.Try again", Toast.LENGTH_SHORT).show();


        }

        else
        {

            //both the username and password are false

            takePicture();
           // cannary();
            Toast.makeText(this, "Wrong credentials.Try again", Toast.LENGTH_SHORT).show();


            //   uploadimage();

            Toast.makeText(this, "Authentication Failed!!", Toast.LENGTH_LONG).show();
            //finish();

        }

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

                uploadimage(bitmap);


       ((ImageView) Image).setImageBitmap(bitmap);

    }





                                                ////////Camera Error/////////
    @Override
    public void onCameraError(@CameraError.CameraErrorCodes int errorCode) {
        switch (errorCode) {
            case CameraError.ERROR_CAMERA_OPEN_FAILED:
                //Camera open failed. Probably because another application
                //is using the camera
                Toast.makeText(this, R.string.error_cannot_open, Toast.LENGTH_LONG).show();
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

                    MainActivity.this.runOnUiThread(new Runnable() {
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

    public  void uploadimage(final Bitmap bitmap)

    {

        String upload_image_url="https://project-daudi.000webhostapp.com/android_login_register/upload_image.php";
        final StringRequest stringrequest = new StringRequest(com.android.volley.Request.Method.POST, upload_image_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override


                    public void onResponse(String response) {
                        try {
                              Log.e("RESPONSE", response);
                            JSONObject json = new JSONObject(response);
                            Toast.makeText(getBaseContext(),
                                    "The image is uploaded", Toast.LENGTH_LONG)
                                    .show();
                        } catch (JSONException e) {
                            Log.d("JSON Exception", e.toString());
                            Toast.makeText(getBaseContext(),
                                    "Error while loading data!",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                Log.e("enda", error.toString());


                Toast.makeText(MainActivity.this, "nnnnnn"+error, Toast.LENGTH_LONG).show();

            }
        })
        {


            protected Map<String, String> getParams() throws AuthFailureError {





                Map<String, String> params = new HashMap<>();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                byte[] imageBytes = baos.toByteArray();
                String imagesd= Base64.encodeToString(imageBytes, Base64.DEFAULT);


                EditText username=findViewById(R.id.username);
                final String name = username.getText().toString();

           //     TextView tt=findViewById(R.id.textView);
            //tt.setText(imagesd);

                params.put("name", name);

                params.put("image",imagesd );


                // return super.getParams();
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringrequest);


    }



}




