package com.example.laundryapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityHomeBinding;
import com.example.laundryapp.fragments.CartFragment;
import com.example.laundryapp.fragments.HelpFragment;
import com.example.laundryapp.fragments.HomeFragment;
import com.example.laundryapp.fragments.ProfileFragment;
import com.example.laundryapp.fragments.RequestFragment;
import com.example.laundryapp.fragments.SettingsFragment;
import com.example.laundryapp.utilities.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
public ActivityHomeBinding homeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeBinding= DataBindingUtil.setContentView(this,R.layout.activity_home);


        openFragment(new HomeFragment(), Constants.HOME_FRAGMENT_TAG);

        homeBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigationHome:
                openFragment(new HomeFragment(), Constants.HOME_FRAGMENT_TAG);
                break;
            case R.id.navigationRequest:
                openFragment(new CartFragment(), Constants.REQUEST_FRAGMENT_TAG);
                break;
            case R.id.navigationProfile:
                openFragment(new ProfileFragment(), Constants.PROFILE_FRAGMENT_TAG);
                break;
            case R.id.navigationSettings:
                openFragment(new SettingsFragment(), Constants.SETTINGS_FRAGMENT_TAG);
                break;
        }
        return true;
    }

    private void openFragment(Fragment fragment, String tag){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.layoutFragment,fragment,tag);
        transaction.addToBackStack(null);
        transaction.commit();

    }



}