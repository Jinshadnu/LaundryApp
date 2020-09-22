package com.example.laundryapp.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.FragmentRequestBinding;
import com.example.laundryapp.fragments.adapter.OrderAdapter;
import com.example.laundryapp.fragments.adapter.PlansAdapter;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.pojo.Plans;
import com.example.laundryapp.fragments.repository.OrderRepository;
import com.example.laundryapp.fragments.viewmodel.OrderViewModel;
import com.example.laundryapp.fragments.viewmodel.PlansViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFragment extends Fragment {

    public FragmentRequestBinding requestBinding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public OrderViewModel orderViewModel;
    public OrderAdapter orderAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
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

        orderViewModel = ViewModelProviders.of((FragmentActivity) this.getActivity()).get(OrderViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_request, container, false);

        requestBinding.layoutBase.toolbar.setTitle("My Orders");

        requestBinding.recyclerOrders.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        requestBinding.recyclerOrders.setHasFixedSize(true);

        fetchOrders();

        return requestBinding.getRoot();
    }

    private void fetchOrders() {
        orderViewModel.fetchOrders().observe((LifecycleOwner) this.getActivity(), new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                orderAdapter=new OrderAdapter(getActivity(),orders);
                requestBinding.recyclerOrders.setAdapter(orderAdapter);
            }
        });
    }
}