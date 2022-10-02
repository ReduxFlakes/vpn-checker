package org.tiago.vpnchecker;

import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private boolean vpn = false;
    private final Intent goSource = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            setTheme(R.style.ThemeDark);
        } else {
            setTheme(R.style.ThemeLight);
        }
        setContentView(R.layout.activity_main);
        initializeLogic();

        TextView textViewResult = findViewById(R.id.textViewResult);
        Button buttonCheck = findViewById(R.id.buttonCheck);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
                textViewResult.setVisibility(View.VISIBLE);
                textViewResult.setText(R.string.result_true);
            }
            else {
                textViewResult.setVisibility(View.VISIBLE);
                textViewResult.setText(R.string.result_false);
            }
        });

    }

    private void initializeLogic() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, R.string.menu_about);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int _id = item.getItemId();
        if (_id == 0) {
            AlertDialog.Builder dgAbout = new AlertDialog.Builder(MainActivity.this);
            goSource.setAction(Intent.ACTION_VIEW);
            goSource.setData(Uri.parse("https://github.com/ReduxFlakes/vpn-checker"));
            dgAbout.setTitle(R.string.menu_about);
            dgAbout.setMessage(R.string.about_message);
            dgAbout.setPositiveButton(R.string.diag_gosource_btn, (_dialog, _which) -> startActivity(goSource));
            dgAbout.setNegativeButton(R.string.diag_negative_btn, (_dialog, _which) -> {
            });
            dgAbout.setCancelable(true);
            dgAbout.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

}

