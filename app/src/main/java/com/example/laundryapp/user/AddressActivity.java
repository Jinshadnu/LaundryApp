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
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public String building,street,zone_no;


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

//                    try{
//                        SmsManager smgr = SmsManager.getDefault();
//                        smgr.sendTextMessage("8606356595",null,addressBinding.editTextaddress.getText().toString(),null,null);
//                        Toast.makeText(this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
//                    }
//                    catch (Exception e){
//                        Toast.makeText(this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
//                    }
            zone=addressBinding.spinnerZone.getSelectedItem().toString().trim();
            if (validatefields()){
                addOrder();
            }
        });

        getAddress();



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

    public void addOrder(){
        orderViewModel.addOrder(user_id,building_address,street_number,zone).observe(this,comonResponse -> {
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

    public void getAddress(){
        addressViewModel.getAddress(user_id).observe(this,addressResponse -> {
            if (addressResponse != null && addressResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                addressAdapter=new AddressAdapter(this,addressResponse.getAddress());
                addressBinding.recyclerAddress.setAdapter(addressAdapter);
                addressAdapter.setActionListener(this);
            }
            if(addressAdapter.getItemCount() == 0){
                addressBinding.recyclerAddress.setVisibility(View.GONE);
                //addressBinding.button3.setVisibility(View.GONE);
            }
        });
    }




    @Override
    public void onActionPerformed(String building_number, String street_address, String zone) {
     this.building=building_number;
     this.street=street_address;
     this.zone_no=zone;

     adOrder();
    }

    public void adOrder(){
        orderViewModel.addOrder(user_id,building,street,zone_no).observe(this,comonResponse -> {
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


}

