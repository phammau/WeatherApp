package com.example.weatherapp;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText editSearch;
    Button btnSearch, btnChangeActivity;
    TextView textViewName, textViewCountry,textViewTemp, textViewHumidity, textViewCloud, textViewWind, textViewDays, textViewstatus;
    ImageView imgIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.layout1);
        Anhxa();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editSearch.getText().toString();
                GetCurrentWeatherData(city);
            }
        });
    }

    public void GetCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url= "https://api.openweathermap.org/data/2.5/weather?q="+data+"&appid=25c5420ee088fc708dfd4d67daa04270";
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            textViewName.setText("Tên thành phố:"+name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE YYYY-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format((date));

                            textViewDays.setText(Day);
                            JSONArray jsonArrayweather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayweather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            String iconUrl = "https://openweathermap.org/img/wn/"+ icon +"@4x.png";
                            Picasso.get().load(iconUrl).into(imgIcon);
                            textViewstatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double a = (Double.valueOf(nhietdo))-273.15;
                            String Nhietdo = String.valueOf(a.intValue());
                            textViewTemp.setText(Nhietdo+"°C");
                            textViewHumidity.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            textViewWind.setText(gio+"m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            textViewCloud.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            textViewCountry.setText("Tên quốc gia :"+country);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        
                    }
                });
        requestQueue.add(stringRequest);
    }
    private void Anhxa() {
        editSearch =findViewById(R.id.edittextSearch);
        btnSearch = findViewById(R.id.btnSearch);
        textViewName = findViewById(R.id.textviewName);
        textViewCloud = findViewById(R.id.textviewCloud);
        textViewCountry  = findViewById(R.id.textviewCountry);
        textViewstatus = findViewById(R.id.textviewStatus);
        textViewDays = findViewById(R.id.textviewDay);
        textViewTemp = findViewById(R.id.textviewTemp);
        textViewWind = findViewById(R.id.textviewWind);
        textViewHumidity = findViewById(R.id.textviewHumidity);
        imgIcon = findViewById(R.id.imgIcon);
    }
}