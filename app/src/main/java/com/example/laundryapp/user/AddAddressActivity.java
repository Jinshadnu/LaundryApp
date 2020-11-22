package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityAddAddressBinding;
import com.example.laundryapp.fragments.viewmodel.ServiceViewModel;
import com.example.laundryapp.user.adapter.AddAddressAdapter;
import com.example.laundryapp.user.adapter.AddressAdapter;
import com.example.laundryapp.user.viewModel.AddressViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.NetworkUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.text.TextUtils.isEmpty;
import static java.util.Objects.requireNonNull;

public class AddAddressActivity extends BaseActivity {
public ActivityAddAddressBinding addAddressBinding;
public AddressViewModel addressViewModel;
public String building_address,street_number,zone,user_id;
public AddAddressAdapter addressAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_address);
        addAddressBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_address);

       addAddressBinding.layoutBase.textTitle.setText("Address Details");

        addAddressBinding.recyclerAddress.setLayoutManager(new LinearLayoutManager(this));
        addAddressBinding.recyclerAddress.setHasFixedSize(true);

        SharedPreferences sharedPreferences=getSharedPreferences(Constants.MyPREFERENCES,MODE_PRIVATE);
        user_id=sharedPreferences.getString(Constants.USER_ID,null);

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
        addAddressBinding.spinnerZone.setAdapter(spinnerArrayAdapter);


        addressViewModel= ViewModelProviders.of(this).get(AddressViewModel.class);



        addAddressBinding.buttonsubmit.setOnClickListener(v -> {
            zone=addAddressBinding.spinnerZone.getSelectedItem().toString();

            if (validatefields()){
               addAddress();
                addAddressBinding.editTextBuildingAddress.setText(" ");
                addAddressBinding.editTextStreetnumber.setText(" ");

            }
        });



         getAddress();

        addAddressBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        addAddressBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


    }


    public boolean validatefields(){
        building_address= requireNonNull(addAddressBinding.editTextBuildingAddress.getText().toString().trim());
        street_number= requireNonNull(addAddressBinding.editTextStreetnumber.getText().toString().trim());
        //zone=requireNonNull(addAddressBinding.spinnerZone.getSelectedItem().toString().trim());


        if (isEmpty(building_address)){
            addAddressBinding.editTextBuildingAddress.setError("please enter your building address");
            return false;
        }

        if (isEmpty(street_number)){
            addAddressBinding.editTextStreetnumber.setError("please enter street number");
            return false;
        }

        if (addAddressBinding.spinnerZone.getSelectedItem().equals("Select your zone")){
            showSnackBar(this,"Please select your zone");
            return false;
        }



        return true;
    }

    public void addAddress(){
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            addressViewModel.addAddress(user_id,building_address,street_number,zone).observe(this,comonResponse  -> {
                if (comonResponse != null && comonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    openSuccessDialog(comonResponse.getMessage());
                    getAddress();
                }

            });
        }
        else {
            showErrorSnackBar(this,"No Internet Connection");
        }

    }

    public void getAddress(){
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            addressViewModel.getAddress(user_id).observe(this,addressResponse -> {
                if (addressResponse != null && addressResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    addressAdapter=new AddAddressAdapter(this,addressResponse.getAddress());
                    addAddressBinding.recyclerAddress.setAdapter(addressAdapter);
                    addAddressBinding.editTextStreetnumber.setEnabled(false);
                    addAddressBinding.editTextBuildingAddress.setEnabled(false);

                }


            });
        }
        else {
            showErrorSnackBar(this,"No Internet Connection");
        }

    }


}