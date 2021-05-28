package com.example.myhomeapplication.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.myhomeapplication.Authorization.UserManager;
import com.example.myhomeapplication.Local_Persistence.DataRetrieverWorker;
import com.example.myhomeapplication.R;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences sharedPreferences;

    private NavController navController;
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);

        // Setting metric system as default
        if (sharedPreferences.getString("sh_system", "null").equalsIgnoreCase("null"))
            sharedPreferences.edit().putString("sh_system", "metric").apply();

        super.onCreate(savedInstanceState);

        UserManager userManager = UserManager.getInstance();

        if (userManager.getLiveUser().getValue() == null) {
            setContentView(R.layout.login_main);
        } else {
            setupMain();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setupMain() {
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        //Allows navigation to destinations when clicking menu items in the navigation drawer
        NavigationUI.setupWithNavController(navigationView, navController);

        //Periodic WorkManager Request
        PeriodicWorkRequest latestDataRequest =
                new PeriodicWorkRequest.Builder(DataRetrieverWorker.class, 5, TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(latestDataRequest);

        // Log out
        navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setOnMenuItemClickListener(l -> {
            UserManager.getInstance().logout();
            // Reloading the activity
            finish();
            startActivity(getIntent());
            return true;
        });

        // Setting toolbar label programmatically
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int desID = destination.getId();
                TextView label = findViewById(R.id.toolbarLabel);
                switch (desID) {
                    case R.id.homeFragment:
                        label.setText("My Home");
                        break;
                    case R.id.temperatureFragment:
                        label.setText("Temperature");
                        break;
                    case R.id.humidityFragment:
                        label.setText("Humidity");
                        break;
                    case R.id.CO2Fragment:
                        label.setText("CO2");
                        break;
                    case R.id.motionFragment:
                        label.setText("Sound");
                        break;
                    case R.id.deviceSettingsFragment:
                        label.setText("Device Settings");
                        break;
                    case R.id.applicationPreferencesFragment:
                        label.setText("App preferences");
                        break;
                    default:
                        Log.wtf("FRAGMENT_ID_NOT_RECOGNIZED", "onDestinationChangedListener");
                        break;
                }
            }
        });

    }

}