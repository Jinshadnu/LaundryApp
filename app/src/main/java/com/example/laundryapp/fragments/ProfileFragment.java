package com.example.laundryapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.FragmentProfileBinding;
import com.example.laundryapp.login.LoginActivity;
import com.example.laundryapp.user.AddAddressActivity;
import com.example.laundryapp.user.AddressActivity;
import com.example.laundryapp.user.ChangePassword;
import com.example.laundryapp.user.HelpsActivity;
import com.example.laundryapp.user.HistoryActivity;
import com.example.laundryapp.user.TermsandConditions;
import com.example.laundryapp.utilities.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
public FragmentProfileBinding profileBinding;
public String username,phone,email;
public View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        SharedPreferences sharedpreferences = getContext().getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        username=sharedpreferences.getString(Constants.USER_NAME,null);
        phone=sharedpreferences.getString(Constants.PHONE,null);
        email=sharedpreferences.getString(Constants.EMAIL,null);

        profileBinding.textViewName.setText(username);
        profileBinding.textViewPhone.setText(phone);
        profileBinding.textViewEmail.setText(email);



        profileBinding.textViewChangePassword.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ChangePassword.class));
        });

        profileBinding.textViewAddress.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddAddressActivity.class));
        });

        profileBinding.textViewHistory.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), HistoryActivity.class));
        });

        profileBinding.textViewLogout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        profileBinding.textViewTerms.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), TermsandConditions.class));
        });

        profileBinding.textViewHelp.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), HelpsActivity.class));
        });

        profileBinding.textViewEdit.setOnClickListener(view1 -> {
            withEditText(view1);
        });





        return profileBinding.getRoot();
    }

    public void withEditText(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Profile");
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.layout_edit_profile, null);
        final EditText editText_phone=view.findViewById(R.id.edittext_phone);
        final EditText editText_email=view.findViewById(R.id.editext_email);
        builder.setView(view);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // Toast.makeText(cogetApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                // Toast.makeText(cogetApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}