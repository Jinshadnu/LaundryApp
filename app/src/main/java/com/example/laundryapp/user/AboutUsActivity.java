package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {
public ActivityAboutUsBinding aboutUsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about_us);

        aboutUsBinding= DataBindingUtil.setContentView(this,R.layout.activity_about_us);

        aboutUsBinding.layoutBase.textTitle.setText("Contact us");

        aboutUsBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        aboutUsBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        aboutUsBinding.imageViewPhone.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel: 77123465"));


            if (ActivityCompat.checkSelfPermission(AboutUsActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            startActivity(callIntent);
        });

        aboutUsBinding.textPhone.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel: 77123465"));

            if (ActivityCompat.checkSelfPermission(AboutUsActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        });








    }
}