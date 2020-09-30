package com.example.laundryapp.user;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityAddressBinding;
import com.example.laundryapp.databinding.ActivityOrderBinding;

import static java.util.Objects.requireNonNull;

public class AddressActivity extends AppCompatActivity {
public ActivityAddressBinding addressBinding;
    private NotificationManager mNotificationManager;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS=0;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addressBinding= DataBindingUtil.setContentView(this,R.layout.activity_address);

        addressBinding.buttonsubmit.setOnClickListener(v -> {

                    try{
                        SmsManager smgr = SmsManager.getDefault();
                        smgr.sendTextMessage("8606356595",null,addressBinding.editTextaddress.getText().toString(),null,null);
                        Toast.makeText(this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                    }


            addNotification();


            startActivity(new Intent(AddressActivity.this,SuccessActivity.class));
        });

        addressBinding.cardViewHome.setOnClickListener(v -> {
            addressBinding.cardViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorDarkstateBlue));
            addressBinding.cardViewOffice.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorwhite));
            addressBinding.textHome.setTextColor(getResources().getColor(R.color.colorwhite));
            addressBinding.textOffice.setTextColor(getResources().getColor(R.color.colorblack));
            addressBinding.imageViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorwhite));
            addressBinding.imageViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorDarkstateBlue));
        });

        addressBinding.cardViewOffice.setOnClickListener(v -> {
            addressBinding.cardViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorwhite));
            addressBinding.cardViewOffice.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorDarkstateBlue));
            addressBinding.textHome.setTextColor(getResources().getColor(R.color.colorblack));
            addressBinding.textOffice.setTextColor(getResources().getColor(R.color.colorwhite));
            addressBinding.imageViewOffice.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorwhite));
            addressBinding.imageViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorDarkstateBlue));
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(addressBinding.editTextaddress.getText().toString(), null, addressBinding.editTextaddress.getText().toString(), null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
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
}