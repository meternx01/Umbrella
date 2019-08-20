package com.example.umbrella.view;

import android.os.Bundle;

import com.example.umbrella.R;
import com.example.umbrella.model.ApiInterface;
import com.example.umbrella.model.Current;
import com.example.umbrella.model.Forecast;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.graphics.Color;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity {

    public static final String API_KEY = "e72a767b2833bb17873b82db36b58634";
    TextView tv_temp_now, tv_location_name;
    CollapsingToolbarLayout toolbar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_location_name = findViewById(R.id.tv_location_name);
        tv_temp_now = findViewById(R.id.tv_temp_now);
        toolbar_layout = findViewById(R.id.toolbar_layout);

        initRetrofit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getCurrentConditions("99501,us", API_KEY,"imperial").enqueue(callback);
        apiInterface.getForecast("99501,us",API_KEY,"imperial","12").enqueue(forcastCallback);
    }

    Callback<Forecast> forcastCallback = new Callback<Forecast>() {
        @Override
        public void onResponse(Call<Forecast> call, Response<Forecast> response) {
            if (response.isSuccessful()){
                //recyclerview stuff
                tv_location_name.setText(response.body().city.name);
            }
        }

        @Override
        public void onFailure(Call<Forecast> call, Throwable t) {

        }
    };
    Callback<Current> callback = new Callback<Current>() {
        @Override
        public void onResponse(Call<Current> call, Response<Current> response) {
            if (response.isSuccessful()){
                int temperature = (int)Math.round(response.body().main.temp);
                tv_temp_now.setText(Integer.toString(temperature));
                tv_location_name.setText(response.body().name);
                if (temperature > 60){
                    toolbar_layout.setBackgroundColor(Color.parseColor("#FF9800"));
                }
                else{
                    toolbar_layout.setBackgroundColor(Color.parseColor("#00b0ff"));
                }

            }
        }

        @Override
        public void onFailure(Call<Current> call, Throwable t) {
            Toast.makeText(ScrollingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
