package com.example.runtimepermission;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    public static  final int PERMISSION_REQUEST_CODE=222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndroidVersion();
    }
    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            checkMyPermission();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkMyPermission() {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                + ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.SEND_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA )
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)) {

                Snackbar.make(MainActivity.this.findViewById(android.R.id.content),
                        "please give permission to access data",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("ENABLE", new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(View view) {

                                requestPermissions(new String[]{ Manifest.permission.SEND_SMS,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
                            }
                        }).show();
            }
            else {
                requestPermissions(new String[]{ Manifest.permission.SEND_SMS,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
            }
        }
        else {
            Intent i=new Intent(MainActivity.this,MainActivity2.class);
            startActivity(i);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case PERMISSION_REQUEST_CODE:
                if (grantResults.length >0)
                {
                    boolean msgpermission=grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean camerapermission=grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean locationpermission=grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (msgpermission && camerapermission && locationpermission)
                    {
                        Intent i=new Intent(MainActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                    else {

                        Snackbar.make(MainActivity.this.findViewById(android.R.id.content),
                                "please give permission to access data",
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction("ENABLE", new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onClick(View view) {

                                        requestPermissions(new String[]{ Manifest.permission.SEND_SMS,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);


                                    }
                                }).show();
                    }

                }
                break;
        }



    }
}