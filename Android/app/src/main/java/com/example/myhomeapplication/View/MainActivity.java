package com.example.myhomeapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myhomeapplication.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;
    private NavigationView navigationView;
    private TextView temperatureCardValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        temperatureCardValue = findViewById(R.id.temperatureCardValue);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        /*setSupportActionBar(toolbar);*/

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        //Allows navigation to destinations when clicking menu items in the navigation drawer
        NavigationUI.setupWithNavController(navigationView, navController);


        //Retrofit (won't work until we have the base URL)
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")// <-- insert api url
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Measurement>> call = jsonPlaceHolderApi.getMeasurements();
        call.enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                if (!response.isSuccessful()){
                    temperatureCardValue.setText(response.code());
                    return;
                }
                List<Measurement> measurements = response.body();
                for (Measurement measurement : measurements){
                    String content = "";
                    content+="Value: "+measurement.getValue() + "\n\n";
                    temperatureCardValue.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {
                temperatureCardValue.setText("404");
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

}