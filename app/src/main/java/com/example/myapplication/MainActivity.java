package com.example.myapplication;

import Config.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;


public class MainActivity extends HiddenCameraActivity {

   // static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQ_CODE_CAMERA_PERMISSION = 1253;

    ImageView Image;
    Button Login;
    TextView Counter;

    EditText Username, Password;
    private  int attempts = 3;


    Boolean Enter = false;

    private CameraConfig mCameraConfig;

        //http://canarytokens.com/traffic/jf83bwt57jglpjru2e6kd2l8y/post.jsp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);

        Counter = findViewById(R.id.counter);

        Login = findViewById(R.id.capture);
        Image = findViewById(R.id.image);

/*
        //canary
        OkHttpClient client = new OkHttpClient();
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
                    //final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            takePicture();
                        }
                    });
                }
            }
        });
        */

    Counter.setText("Attempts remaining: 3");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                LogIn();
}

        });


        //camera
        //Image.setVisibility(View.INVISIBLE);

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

    /////login

    @SuppressLint("SetTextI18n")
    public void LogIn() {
        final String name = Username.getText().toString();
        final String pass = Password.getText().toString();

        if (name.equals("rob") && pass.equals("r1234")){
            Toast.makeText(this, "access granted", Toast.LENGTH_SHORT).show();
            Intent welcome = new Intent(MainActivity.this, Welcome.class);
            startActivity(welcome);
        }
        else {
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
            attempts --;
            Counter.setText("Attempts remaining: "+String.valueOf(attempts));
            if (attempts == 0){
                //Login.setEnabled(false);
                Toast.makeText(this, " Canary", Toast.LENGTH_SHORT).show();
            }
        }

    }



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

        //Displaying the image to the image view
        ((ImageView) Image).setImageBitmap(bitmap);

    }

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


    //canary

    public void cannary(){
        OkHttpClient client = new OkHttpClient();
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
                    //final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            takePicture();
                        }
                    });
                }
            }
        });
    }
}




