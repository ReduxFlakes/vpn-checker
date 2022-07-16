package com.redux.vpnchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean vpn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewResult = findViewById(R.id.textViewResult);
        Button buttonCheck = findViewById(R.id.buttonCheck);

        buttonCheck.setOnClickListener(v -> {
            try {
                android.net.ConnectivityManager manager = (android.net.ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                Network activeNetwork = manager.getActiveNetwork();
                NetworkCapabilities cap = manager.getNetworkCapabilities(activeNetwork);

                vpn = cap.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
            } catch (Exception e){
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            if (vpn) {
                textViewResult.setText(R.string.result_true);
            }
            else {
                textViewResult.setText(R.string.result_false);
            }
        });
    }
}