package com.example.webcam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    public static String mIP = "";
    private int mPort = 8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (shouldAskPermissions()) {
            askPermissions();
        }

        try {
            mIP = getLocalIpAddress();
        } catch (UnknownHostException e) {
            Log.d("Tuan", "Loi o doan wifi");
            e.printStackTrace();
        }

        TextView ipAdress = (TextView) findViewById(R.id.ipp);
        ipAdress.setText(mIP);
        ipAdress.setTextSize(20);

        findViewById(R.id.startBtn)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(this, AfterConnectView.class);
                    intent.putExtra("ip adress", mIP);
                    startActivity(intent);
                });

    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.MANAGE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    private String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiManager != null;
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        return ip;
    }

}