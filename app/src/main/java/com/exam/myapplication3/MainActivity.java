package com.exam.myapplication3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioFormat;
import android.os.Bundle;
import android.os.Environment;
import org.tensorflow.lite.Interpreter;
import android.media.AudioRecord;
import android.os.FileUtils;
import android.util.Log;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.Manifest;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.tensorflow.lite.support.label.Category;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;




import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.exam.myapplication3.ml.*;
import com.exam.myapplication3.ml.Modelo2;


import android.widget.EditText;
import android.widget.TextView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import androidx.activity.EdgeToEdge;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

     Button startBtn,stopBtn ,START;
      Button PREDECT , select;


     private TextView  textwiev,textview22,textview3;

     Intent myfileintent;

     MediaPlayer mediaPlayer;
    private Uri contentUri;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        wavClass wavObj = new wavClass(Environment.getExternalStorageDirectory().getPath());
        startBtn = findViewById(R.id.button);
        stopBtn = findViewById(R.id.button2);
        PREDECT= findViewById(R.id.button8) ;
        START=findViewById(R.id.button3);
        textwiev=findViewById(R.id.textview1);
        textview22=findViewById(R.id.textview2);
        textview3=findViewById(R.id.textview);
        select=findViewById(R.id.button4);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               myfileintent=new Intent(Intent.ACTION_GET_CONTENT);
               myfileintent.setType("*/*");
               startActivityForResult(myfileintent,10);
               Toast.makeText(MainActivity.this,"selceccionar audio",Toast.LENGTH_SHORT).show();
            }
        });
        startBtn.setOnClickListener(v -> {
            if(checkWritePermission()) {
                wavObj.startRecording();
            }
            if(!checkWritePermission()){
                requestWritePermission();
            }

            Toast.makeText(MainActivity.this,"empieza grabacion",Toast.LENGTH_SHORT).show();
        });
        stopBtn.setOnClickListener(v -> {
            wavObj.stopRecording();
            Toast.makeText(MainActivity.this,"detener grbacion",Toast.LENGTH_SHORT).show();
                }
        );

        START.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"final_record.wav");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this,"reproduccion",Toast.LENGTH_SHORT).show();  ///sdcard/Download/2104.08396.pdf
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });




        //StartRecording=findViewById(R.id.button);
       // Stoprecording=findViewById(R.id.button2);
        //Startplaying   = findViewById(R.id.button3) ;
        //Stoplaying =findViewById(R.id.button3) ;



        //if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        //    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        //}







       //Startplaying.setOnClickListener(new View.OnClickListener() {  /storage/self/primary/Download/file.wav
        //   @Override
          // public void onClick(View v) {
            //   mediaPlayer=new MediaPlayer();
              // try {
               //    mediaPlayer.setDataSource(audiosave);
                 //  mediaPlayer.prepare();
                   //mediaPlayer.start();
                   //Toast.makeText(MainActivity.this,"reproduccion",Toast.LENGTH_SHORT).show();
               //}catch (IOException e){
                   //e.printStackTrace();
               //}
           //}
       //});
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }



        //if (isMicrophonePresent()) {
          //  getMicrophonePermission();
       // }




        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject Mymodule = py.getModule("myhelper");
        PyObject myFnCallVale = Mymodule.get("predecir");

        String greetingg =  "/sdcard/final_record.wav" ;      //"/sdcard/Download/example4.wav";
        System.out.println("el resultado  es :" + myFnCallVale.call(greetingg.toString()));


       // PyObject Mymodules= py.getModule("myhelper");
      // PyObject myFnCallVales= Mymodules.get("predecir");
       //String greetings=  "/sdcard/final_record.wav" ;
       //System.out.println("archivo creado con exyito:" + myFnCallVales.call(greetings.toString()));



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                   Uri imagefile = data.getData();

                    String path = this.getRealPathFromURI(imagefile);//data.getData().getPath();
                    String[] split = path.split("/");
                    String name = split[split.length - 1];
                    textview3.setText(name);

                    PREDECT.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {


                            String[] split = path.split("/");
                            Python py = Python.getInstance();
                            PyObject Mymodule = py.getModule("myhelper");
                            PyObject myFnCallVale = Mymodule.get("wav_to_mfcc");
                            PyObject Mymodule2 = py.getModule("myhelper");
                            PyObject myFnCallVale2 = Mymodule2.get("predecir");
                            int a=split.length;



                            if (a == 5){

                            String greeting= "/sdcard/" + name;

                            String greeting2=  "/sdcard/" + name;

                            Toast.makeText(MainActivity.this,"predecir",Toast.LENGTH_SHORT).show();
                            textview22.setText(myFnCallVale.call(greeting.toString()).toString());
                            textwiev.setText(myFnCallVale2.call(greeting2.toString()).toString());

                        }
                          else{


                                String greeting= "/sdcard/Download/" + name;

                                String greeting2=  "/sdcard/Download/" + name;

                                Toast.makeText(MainActivity.this,"predecir",Toast.LENGTH_SHORT).show();
                                textview22.setText(myFnCallVale.call(greeting.toString()).toString());
                                textwiev.setText(myFnCallVale2.call(greeting2.toString()).toString());


                            }




                        }
                    });






                }
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private boolean checkWritePermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED ;
    }
    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.MODIFY_AUDIO_SETTINGS,WRITE_EXTERNAL_STORAGE},1);
    }



}