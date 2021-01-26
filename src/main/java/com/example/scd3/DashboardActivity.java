package com.example.scd3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scd3.Request.LocationRequest;
import com.example.scd3.Response.LocationResponse;
import com.example.scd3.Response.LoginResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    String user, password;
    TextView username;
    TextView latitudeTextView, longitudeTextView, countryTextView, localityTextView, addressTextView;
    Button btnGetLocation;
    Button btnGetLocationAuto;
    Button btnStop;
    FusedLocationProviderClient fusedLocationProviderClient;
    CountDownTimer countDownTimer;
    TextView textView;

    public int counter =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textView= (TextView) findViewById(R.id.textView);
        username = findViewById(R.id.welcomeMsg);
        latitudeTextView = findViewById(R.id.latitude);
        longitudeTextView = findViewById(R.id.longitude);
        countryTextView = findViewById(R.id.country);
        localityTextView = findViewById(R.id.locality);
        addressTextView = findViewById(R.id.address);



        btnGetLocation = findViewById(R.id.btnSendLocation);
        btnGetLocationAuto = findViewById(R.id.btnAutomaticallySendLocation);
        btnStop = findViewById(R.id.btnStop);



        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String passedUsername = intent.getStringExtra("data");
            username.setText("Welcome " + passedUsername);

            user = intent.getStringExtra("userText");
            password = intent.getStringExtra("passwordText");
        }



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });



        btnGetLocationAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    countDownTimer = new CountDownTimer(10000, 1000){
                        public void onTick(long millisUntilFinished){
                            textView.setText(String.valueOf(counter));
                            counter++;
                        }
                        public  void onFinish(){
                            getLocation();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }.start();

                } else {
                    ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });



        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
            }
        });
    }

    private void getLocation() {

        LocationRequest locationRequest = new LocationRequest();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(DashboardActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        Toast.makeText(DashboardActivity.this, "LOCATION", Toast.LENGTH_LONG).show();


                        latitudeTextView.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude()));
                        longitudeTextView.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude()));
                        countryTextView.setText(Html.fromHtml("<font color='#6200EE'><b>Country :</b><br></font>" + addresses.get(0).getCountryName()));
                        localityTextView.setText(Html.fromHtml("<font color='#6200EE'><b>Locality :</b><br></font>" + addresses.get(0).getLocality()));
                        addressTextView.setText(Html.fromHtml("<font color='#6200EE'><b>Address :</b><br></font>" + addresses.get(0).getAddressLine(0)));


                        String encoding = "Basic " + Base64.getEncoder().encodeToString((user + ":" + password).getBytes());


                        locationRequest.setLatitude(String.valueOf(addresses.get(0).getLatitude()));
                        locationRequest.setLongitude(String.valueOf(addresses.get(0).getLongitude()));


                        Call<LocationResponse> locationResponseCall = ApiClient.getUserService().addLocation(locationRequest, encoding);
                        locationResponseCall.enqueue(new Callback<LocationResponse>() {
                            @Override
                            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(DashboardActivity.this, "Location send with success", Toast.LENGTH_LONG).show();
                                    LocationResponse locationResponse = response.body();

                                } else {
                                    Toast.makeText(DashboardActivity.this, "Sending Location Failed", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<LocationResponse> call, Throwable t) {
                                Toast.makeText(DashboardActivity.this, "Throwable" + t.getLocalizedMessage() + t.getMessage(), Toast.LENGTH_LONG);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}