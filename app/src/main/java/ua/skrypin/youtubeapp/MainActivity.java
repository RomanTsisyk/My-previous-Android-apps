package ua.skrypin.youtubeapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String[] YOUTUBE_PLAYLISTS = {
            "PLNiaxBmKsUotj3wWVanVIi8hGlkxquRzf",
            "PLNiaxBmKsUot9g0gkYW1hkHLeZRSx8_IU",
            "PLNiaxBmKsUovcbg0ooXI9JkLAIIfDl9NI",
            "PLNiaxBmKsUovDtnoT3d2V21sgMslHskAF",
            "PLNiaxBmKsUosNJ9F67gy-bZ_FSEkpCixM",
            "PLNiaxBmKsUoud3K1EFWcALRIO0DypEayv",
            "PLNiaxBmKsUosBBz-9kZ9vyxWweyioUzSg"
    };

    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_activity);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (!isConnected()) {
            Toast.makeText(MainActivity.this, "No Internet Connection Detected", Toast.LENGTH_LONG).show();
        }

        if (ApiKey.YOUTUBE_API_KEY.startsWith("YOUR_API_KEY")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage("Edit ApiKey.java and replace \"YOUR_API_KEY\" with your Applications Browser API Key")
                    .setTitle("Missing API Key")
                    .setNeutralButton("Ok, I got it!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (savedInstanceState == null) {
            mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                    .setApplicationName(getResources().getString(R.string.app_name))
                    .build();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
                    .commit();
        }
    }


    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_recyclerview) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
                    .commit();

        } else if (id == R.id.shop) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pg/SKRYPIN.UA/shop/"));
            startActivity(myIntent);

            // help
        } else if (id == R.id.privat_bank) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://privatbank.ua/sendmoney?payment=65c5561aec5baf10047fa447fc7e69c10346fe32"));
            startActivity(myIntent);

        } else if (id == R.id.paypal) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/donate/?token=AMNc-9TpJ3Z5J-cW9dWcyrWgUcx23diw7JcLEI3sn7hDvjjAX-X3DOReFhAb3flbbFLU90&country.x=CZ&locale.x=CZ"));
            startActivity(myIntent);

        } else if (id == R.id.map) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/skrypin.ua/@50.440795,30.499385,15z/data=!4m5!3m4!1s0x0:0xd77e91002d54ff12!8m2!3d50.4407945!4d30.4993854?hl=en-US"));
            startActivity(myIntent);
        } else if (id == R.id.facebook) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SKRYPIN.UA/"));
            startActivity(myIntent);
        } else if (id == R.id.instagram) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/skrypinua/"));
            startActivity(myIntent);
        } else if (id == R.id.telegram) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/SkrypinUA"));
            startActivity(myIntent);
        } else if (id == R.id.twitter) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/SkrypinUa"));
            startActivity(myIntent);
        } else if (id == R.id.email) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","abc@gmail.com", null));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
