package com.example.laundryapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.lifecycle.Observer;

import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.laundryapp.MainActivity;
import com.example.laundryapp.R;
import com.example.laundryapp.databinding.FragmentHomeBinding;
import com.example.laundryapp.fragments.adapter.ImageSliderAdapter;
import com.example.laundryapp.fragments.adapter.PlansAdapter;
import com.example.laundryapp.fragments.adapter.ServiceAdapter;
import com.example.laundryapp.fragments.pojo.Plans;
import com.example.laundryapp.fragments.pojo.Services;
import com.example.laundryapp.fragments.viewmodel.CategoryViewModel;
import com.example.laundryapp.fragments.viewmodel.PlansViewModel;
import com.example.laundryapp.fragments.viewmodel.ServiceViewModel;
import com.example.laundryapp.user.DetailsActivity;
import com.example.laundryapp.user.ServiceDetails;
import com.example.laundryapp.user.pojo.ServiceResponse;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.GetAddressIntentService;
import com.example.laundryapp.utilities.GridSpacingItemDecoration;
import com.example.laundryapp.utilities.NetworkUtilities;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    public FragmentHomeBinding homeBinding;
    public CategoryViewModel categoryViewModel;
    public PlansViewModel plansViewModel;
    public ServiceViewModel serviceViewModel;
    public PlansAdapter plansAdapter;
    public ServiceAdapter serviceAdapter;
    public double latitude;
    public double longitude;
    private BaseActivity baseActivity;
    public Location currentLocation;
    public Context context;
    public String user_id;

    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationAddressResultReceiver addressResultReceiver;
    private LocationCallback locationCallback;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        serviceViewModel = ViewModelProviders.of((FragmentActivity) this.getActivity()).get(ServiceViewModel.class);
        plansViewModel = ViewModelProviders.of((FragmentActivity) this.getActivity()).get(PlansViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeBinding.layoutBase.toolbar.setTitle("Home");
        homeBinding.layoutBase.textTitle.setText("Home");

        //baseActivity = (BaseActivity) this.getActivity();

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        user_id = sharedpreferences.getString(Constants.USER_ID, null);


        ///homeBinding.recyclerService.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        homeBinding.recyclerService.setLayoutManager(new GridLayoutManager(getActivity(),2));
        homeBinding.recyclerService.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));


        homeBinding.recyclerPlans.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        homeBinding.recyclerPlans.setHasFixedSize(true);


        setValuesToFields();

        runAnimationAgain();

//        if(ContextCompat.checkSelfPermission(context.getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//          ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
//        }else {
//           getCurrentLocation();
//        }
        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

//        locationCallback=new LocationCallback(){
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                currentLocation = locationResult.getLocations().get(0);
//                getAddress();
//            }
//
//
//        };

        startLocationUpdates();

        getServices();

        getPlans();

        return homeBinding.getRoot();
    }


        private int dpToPx(int dp) {
            Resources r = getResources();
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
        }


    private void getCurrentLocation() {

    }

    //get Plans
    private void getPlans() {
        plansViewModel.getPlans().observe((LifecycleOwner) this.getActivity(), new Observer<List<Plans>>() {
            @Override
            public void onChanged(List<Plans> banners) {
             plansAdapter=new PlansAdapter(getActivity(),banners);
             homeBinding.recyclerPlans.setAdapter(plansAdapter);
            }
        });
    }

    //Get services
    private void getServices() {
     if (NetworkUtilities.getNetworkInstance(getActivity()).isConnectedToInternet()) {
         serviceViewModel.getServices().observe(getActivity(), serviceResponse -> {
             if (serviceResponse != null && serviceResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)) {
                 serviceAdapter = new ServiceAdapter(getActivity(), serviceResponse.getServices());
                 homeBinding.recyclerService.setAdapter(serviceAdapter);
             }
         });
     }
     else {
         Toast.makeText(getActivity(),"No Internet connection",Toast.LENGTH_LONG).show();
     }
    }


    //set Banner
    private void setValuesToFields() {
        //banner image
        List<String> bannerList = new ArrayList<>();
        bannerList.add("1");
        bannerList.add("2");
        bannerList.add("3");
        homeBinding.rlBanner.setVisibility(View.VISIBLE);
        homeBinding.vpImage.setAdapter(new ImageSliderAdapter(getActivity(), bannerList));

        homeBinding.cpImage.setViewPager(homeBinding.vpImage);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        homeBinding.cpImage.setRadius(5 * density);

        homeBinding.vpImage.startAutoScroll();
        homeBinding.vpImage.setInterval(5000);
        homeBinding.vpImage.setCycle(true);
        homeBinding.vpImage.setStopScrollWhenTouch(true);

        // Pager listener over indicator
        homeBinding.cpImage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        }
    }



    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                ///getAddress();
            }
            if (resultCode == 1) {
                Toast.makeText(getActivity(), "Address not found, ", Toast.LENGTH_SHORT).show();
            }
            String currentAdd = resultData.getString("address_result");
            //showResults(currentAdd);
        }
    }




    private void runAnimationAgain() {


        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.gridlayout_animation_from_bottom);

        homeBinding.recyclerService.setLayoutAnimation(controller);
//        itemAdapter.notifyDataSetChanged();
        homeBinding.recyclerService.scheduleLayoutAnimation();

    }

    public void fetchService(){

    }


}