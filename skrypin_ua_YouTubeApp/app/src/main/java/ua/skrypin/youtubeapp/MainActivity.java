package ua.skrypin.youtubeapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

    public static String FACEBOOK_URL = "https://www.facebook.com/SKRYPIN.UA";
    public static String FACEBOOK_PAGE_ID = "SKRYPIN.UA";


    private static final String[] YOUTUBE_PLAYLISTS = {
            "PLNiaxBmKsUot9g0gkYW1hkHLeZRSx8_IU",  "PLNiaxBmKsUov0-teiEPCsaZP2C3RUtaza",
            "PLNiaxBmKsUotj3wWVanVIi8hGlkxquRzf", "PLNiaxBmKsUotwX8C6oFcfMDJvhwkE1zMV",
            "PLNiaxBmKsUosBBz-9kZ9vyxWweyioUzSg", "PLNiaxBmKsUoulwDp2xgr-ux8f_kVTSXgj",
            "PLNiaxBmKsUosRLt2dM9YrApd6sgmfoPhs", "PLNiaxBmKsUovMg2Mg0Unik7uhh6SUA6lg0",
            "PLNiaxBmKsUovDtnoT3d2V21sgMslHskAF", "PLNiaxBmKsUovcbg0ooXI9JkLAIIfDl9NI",
            "PLNiaxBmKsUotdhvepEvsvgt-KrdHko82A", "PLNiaxBmKsUousK4iqVhhf3nQ6HMMTnzwE"
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

        if (savedInstanceState == null) {
            mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                    .setApplicationName(getResources().getString(R.string.app_name))
                    .build();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
                    .commit();
        }
    }


    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

        } else if (id == R.id.map) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/skrypin.ua/@50.440795,30.499385,15z/data=!4m5!3m4!1s0x0:0xd77e91002d54ff12!8m2!3d50.4407945!4d30.4993854?hl=en-US"));
            startActivity(myIntent);
        } else if (id == R.id.facebook) {
            newFacebookIntent();
        } else if (id == R.id.instagram) {
            newInstagramProfileIntent();
        } else if (id == R.id.telegram) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/SkrypinUA"));
            startActivity(myIntent);
        } else if (id == R.id.twitter) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/SkrypinUa"));
            startActivity(myIntent);
        } else if (id == R.id.email) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "hello@skrypin.ua", null));
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


    public void newInstagramProfileIntent() {
        Uri uri = Uri.parse("http://instagram.com/_u/skrypinua");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.instagram.android");
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/skrypinua")));
        }
    }


    public void newFacebookIntent() {
        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + FACEBOOK_URL);
        Intent fbintent = new Intent(Intent.ACTION_VIEW, uri);
        fbintent.setPackage("com.facebook.katana");
        try {
            startActivity(fbintent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(FACEBOOK_URL)));
        }
    }
}
