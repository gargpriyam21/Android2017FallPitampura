package com.codingblocks.permissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.textView)).setText(readFile());
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int perm = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                switch (perm) {
                    case PackageManager.PERMISSION_GRANTED:
                        writeFile("Our custom string");
                        break;
                    case PackageManager.PERMISSION_DENIED:
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                324
                        );
                        break;
                }
            }
        });
    }

    void writeFile(String data) {
        File f = new File(Environment.getExternalStorageDirectory(), "file.txt");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(data.getBytes());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    String readFile () {
        String data = "";
        File f = new File(Environment.getExternalStorageDirectory(), "file.txt");
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(f)
                    )
            );
            String buf = "";
            StringBuilder sb = new StringBuilder();
            while (buf != null) {
                buf = br.readLine();
                sb.append(buf).append("\n");
            }
            data = sb.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return data;
    }
}
