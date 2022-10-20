package com.example.qrscan;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.security.Policy;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity2 extends AppCompatActivity {
    CodeScannerView codeScannerView;
    CodeScanner codeScanner;

//   TextView textView;


    //    Camera cam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        setContentView ( R.layout.activity_main2 );
//        textView = (TextView) findViewById ( R.id.textView );
        codeScannerView = findViewById ( R.id.Scanner_View );
        codeScanner = new CodeScanner ( this, codeScannerView );

        Dexter.withContext ( getApplicationContext () )
                .withPermission ( Manifest.permission.CAMERA )
                .withListener ( new PermissionListener ( ) {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        codeScanner.startPreview ();

                        codeScanner.setFormats ( codeScanner.ALL_FORMATS );
                        codeScanner.setAutoFocusEnabled ( true );
                        codeScanner.setTouchFocusEnabled ( false );




                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest ();
                    }
                } ).check ();
//codeScanner=new CodeScanner ( this,codeScannerView );
//codeScanner.setAutoFocusEnabled ( true);
//codeScanner.setFormats ( CodeScanner.ALL_FORMATS );
//codeScanner.setScanMode ( ScanMode.CONTINUOUS );
        codeScanner.setDecodeCallback ( new DecodeCallback ( ) {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread ( new Runnable ( ) {
                    @Override
                    public void run() {
                        MainActivity.textView.setText ( result.getText () );
                        onBackPressed ();
//                        String data = result.getText ( );
//                        textView.setText ( data );
                    }
                } );
            }
        } );
        codeScannerView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview ( );
            }
        } );
        onPause ();
        onResume ();

    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

}
