package com.example.laundryapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.laundryapp.user.viewModel.AddCartViewModel;
import com.example.laundryapp.user.viewModel.UsersViewModel;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.NetworkUtilities;

import static android.text.TextUtils.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
public FragmentProfileBinding profileBinding;
public String username,phone,email;
public View view;
public UsersViewModel usersViewModel;
public String user_id;
public int position;
public String userphone,useremail;
    public EditText editText_phone,editText_email;

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
        usersViewModel= ViewModelProviders.of((FragmentActivity) this.getActivity()).get(UsersViewModel.class);
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
        user_id=sharedpreferences.getString(Constants.USER_ID,user_id);





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



       getUsers();

        return profileBinding.getRoot();
    }

    public boolean validate(String phone,String email){
        if (isEmpty(phone)) {
            return false;
        }

        if (isEmpty(email)) {
            editText_email.setError("Please enter email");

            return false;
        }
        if(phone.length() < 8){
            editText_phone.setError("Invalid phone number");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length() < 5){
            //editText_email.setError("Invalid email address");
            return false;
        }
        return true;
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_edit_profile);
        dialog.setTitle("Edit Profile");
        editText_phone=dialog.findViewById(R.id.editText_phone);
        editText_email=dialog.findViewById(R.id.editTextEmail);
        final Button button=dialog.findViewById(R.id.button_save);

        button.setOnClickListener(view1  -> {
            userphone=editText_phone.getText().toString();
            useremail=editText_email.getText().toString();

            if (validate(userphone,useremail)){
                editProfile(user_id,userphone,useremail);
                dialog.dismiss();
            }
        });


    }



    public void withEditText(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Profile");
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.layout_edit_profile, null);
        editText_phone=view.findViewById(R.id.editText_phone);
        editText_email=view.findViewById(R.id.editTextEmail);
        builder.setView(view);
        editText_email.setText(profileBinding.textViewEmail.getText().toString());
        editText_phone.setText(profileBinding.textViewPhone.getText().toString());
        final Button button=view.findViewById(R.id.button_save);


        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // Toast.makeText(cogetApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();

                userphone=editText_phone.getText().toString();
                useremail=editText_email.getText().toString();

                if (validate(userphone,useremail)){
                    editProfile(user_id,userphone,useremail);
                    dialogInterface.dismiss();
                }
                else {
                    Toast.makeText(getActivity(), "Updation Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });




        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                // Toast.makeText(cogetApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void getUsers(){
        if (NetworkUtilities.getNetworkInstance(getActivity()).isConnectedToInternet()){
            usersViewModel.getUsers(user_id).observe(getActivity(),userResponse -> {
              if (userResponse != null && userResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                  profileBinding.textViewName.setText(userResponse.getUser().get(position).getUsername());
                  profileBinding.textViewPhone.setText(userResponse.getUser().get(position).getPhone());
                  profileBinding.textViewEmail.setText(userResponse.getUser().get(position).getEmail());
              }
            });
        }
    }

    public void editProfile(String user_id,String userphone,String useremail){
        if (NetworkUtilities.getNetworkInstance(getActivity()).isConnectedToInternet()){
            usersViewModel.editProfile(user_id,userphone,useremail).observe(getActivity(),comonResponse -> {
                if (comonResponse != null && comonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    Toast.makeText(getActivity(),comonResponse.getMessage(),Toast.LENGTH_LONG).show();
                    getUsers();
                }
                else {
                    Toast.makeText(getActivity(),comonResponse.getMessage(),Toast.LENGTH_LONG).show();
                }

            });
        }
    }

}