package com.example.laundryapp.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityAddressBinding;
import com.example.laundryapp.databinding.ActivityOrderBinding;
import com.example.laundryapp.user.adapter.AddressAdapter;
import com.example.laundryapp.user.viewModel.AddressViewModel;
import com.example.laundryapp.user.viewModel.OrderViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.GPSTracker;
import com.example.laundryapp.utilities.Utilities;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;
import static java.util.Objects.requireNonNull;

public class AddressActivity extends BaseActivity implements AddressAdapter.setOnAddressListener {
public ActivityAddressBinding addressBinding;
    private NotificationManager mNotificationManager;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS=0;
    public OrderViewModel orderViewModel;
    public AddressViewModel addressViewModel;
    public String building_address,street_number,zone;
    public AddressAdapter addressAdapter;
    public String building,street,zone_no,live_location,order_address,lati,longi;
    int PLACE_PICKER_REQUEST = 1463;
    double latitude, longitude;
    Location mLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    private Geocoder geocoder;


    public String user_id;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addressBinding= DataBindingUtil.setContentView(this,R.layout.activity_address);

        addressBinding.layoutBase.textTitle.setText("Address Details");



        SharedPreferences sharedPreferences=getSharedPreferences(Constants.MyPREFERENCES,MODE_PRIVATE);
        user_id=sharedPreferences.getString(Constants.USER_ID,null);



        addressBinding.recyclerAddress.setLayoutManager(new LinearLayoutManager(this));
        addressBinding.recyclerAddress.setHasFixedSize(true);






        String[] ZONES = new String[]{
                "Select your zone",
                "71",
                "74",
                "75",
                "76"
        };;

        final List<String> plantsList = new ArrayList<>(Arrays.asList(ZONES));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {

                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        addressBinding.spinnerZone.setAdapter(spinnerArrayAdapter);


        addressBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        addressBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        orderViewModel= ViewModelProviders.of(this).get(OrderViewModel.class);


        addressViewModel= ViewModelProviders.of(this).get(AddressViewModel.class);





        addressBinding.buttonsubmit.setOnClickListener(v -> {


            zone=addressBinding.spinnerZone.getSelectedItem().toString().trim();
            if (validatefields()){
                addOrder();
            }



        });

        getAddress();




    }


    @Override
    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(AddressActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

             latitude = gps.getLatitude();
             longitude = gps.getLongitude();



            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
//                    + latitude + "\nLong: " + longitude + "https://www.google.com/maps/search/?api=1&query= " + String.valueOf(latitude) + "," +String.valueOf(longitude)  , Toast.LENGTH_LONG).show();

            getTheAddress(latitude,longitude);
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
         //   gps.showSettingsAlert();
            askUserToOpenGPS();
        }
    }

    private void startActivityForResult(int place_picker_request) {

    }

    // ask location permission for user if location is off
    public void askUserToOpenGPS() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        mAlertDialog.setTitle("Location not available, Open GPS?")
                .setMessage("Activate GPS to use use location services?")
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    SmsManager smsManager = SmsManager.getDefault();
//
//                    smsManager.sendTextMessage(addressBinding.editTextaddress.getText().toString(), null, addressBinding.editTextaddress.getText().toString(), null, null);
//                    Toast.makeText(getApplicationContext(), "SMS sent.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//    }

    // get Address
private void getTheAddress(double latitude, double longitude) {
    List<Address> addresses;
    geocoder = new Geocoder(this, Locale.getDefault());

    try {
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        order_address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        Log.d("neel", order_address);
        addressBinding.editTextLocation.setText(order_address);
    } catch (IOException e) {
        e.printStackTrace();
    }


}
    public boolean validatefields(){
        building_address= requireNonNull(addressBinding.editTextBuildingNumber.getText().toString().trim());
        street_number= requireNonNull(addressBinding.editTextStreetNumber.getText().toString().trim());



        if (isEmpty(building_address)){
            addressBinding.editTextBuildingNumber.setError("please enter your building address");
            return false;
        }

        if (isEmpty(street_number)){
            addressBinding.editTextStreetNumber.setError("please enter street number");
            return false;
        }

        if (addressBinding.spinnerZone.getSelectedItem().equals("Select your zone")){
            showSnackBar(this,"Please select your zone");
            return false;
        }



        return true;
    }


    private void addNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "notify_001");
        Intent ii = new Intent(this, HistoryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
     //   bigText.bigText(verseurl);
        bigText.setBigContentTitle("Order Status");
        bigText.setSummaryText("Order Status");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Order placed successfully");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }

    //add order
    public void addOrder(){
        orderViewModel.addOrder(user_id,building_address,street_number,zone,String.valueOf(latitude),String.valueOf(longitude),order_address).observe(this,comonResponse -> {
            if (comonResponse != null && comonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this, "notify_001");
                Intent ii = new Intent(this, HistoryActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                //   bigText.bigText(verseurl);
                bigText.setBigContentTitle("Order Status");
                bigText.setSummaryText("Order Status");

                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                mBuilder.setContentTitle("Your Title");
                mBuilder.setContentText(comonResponse.getMessage());
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                mBuilder.setStyle(bigText);

                mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    mBuilder.setChannelId(channelId);
                }

                mNotificationManager.notify(0, mBuilder.build());
                startActivity(new Intent(AddressActivity.this,SuccessActivity.class));
            }
            else {
                showSnackBar(this,comonResponse.getMessage());
            }
        });
    }

    public void getAddress(){
        addressViewModel.getAddress(user_id).observe(this,addressResponse -> {
            if (addressResponse != null && addressResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                addressAdapter=new AddressAdapter(this,addressResponse.getAddress());
                addressBinding.recyclerAddress.setAdapter(addressAdapter);
                addressAdapter.setActionListener(this);
            }
//            if(addressAdapter.getItemCount() == 0){
//                addressBinding.recyclerAddress.setVisibility(View.GONE);
//                //addressBinding.button3.setVisibility(View.GONE);
//            }
        });
    }




    @Override
    public void onActionPerformed(String building_number, String street_address, String zone) {
     this.building=building_number;
     this.street=street_address;
     this.zone_no=zone;

     adOrder();
    }

    //add order if user already add their address
    public void adOrder(){
        orderViewModel.addOrder(user_id,building,street,zone_no,String.valueOf(latitude),String.valueOf(longitude),order_address).observe(this,comonResponse -> {
            if (comonResponse != null && comonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this, "notify_001");
                Intent ii = new Intent(this, HistoryActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                //   bigText.bigText(verseurl);
                bigText.setBigContentTitle("Order Status");
                bigText.setSummaryText("Order Status");

                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                mBuilder.setContentTitle("Your Title");
                mBuilder.setContentText(comonResponse.getMessage());
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                mBuilder.setStyle(bigText);

                mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    mBuilder.setChannelId(channelId);
                }

                mNotificationManager.notify(0, mBuilder.build());
                startActivity(new Intent(AddressActivity.this,SuccessActivity.class));
            }
        });
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                String lat = data.getStringExtra("lat");
//                String lon = data.getStringExtra("lon");
//                latitude = lat;
//                longitude = lon;
//                String address = Utilities.getCompleteAddressString(this,Double.parseDouble(latitude),Double.parseDouble(longitude));
//                addressBinding.editTextLocation.setText(latitude + ", " + longitude);
//               // input_address.setText(address);
////                 place.getName();
////                 place.getAddress();
//            }
//            else {
//                Log.e("ShippingAddress","Error in data fetching");
//                // Toast.makeText(getActivity(),   "Error in data fetching", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//    }

//    private void getMyLocation() {
//        //LatLng latLng = new LatLng(Double.parseDouble(getLatitude()), Double.parseDouble(getLongitude()));
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
//        //googleMap.animateCamera(cameraUpdate);
//    }


    @SuppressLint("LongLogTag")
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                addressBinding.editTextLocation.setText(strAdd);
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }




}

