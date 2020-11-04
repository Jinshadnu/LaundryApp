package com.example.laundryapp.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityRegisterBinding;
import com.example.laundryapp.login.LoginActivity;
import com.example.laundryapp.register.viewmodel.RegisterViewModel;
import com.example.laundryapp.user.HomeActivity;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;

import static android.text.TextUtils.isEmpty;
import static com.example.laundryapp.utilities.NetworkUtilities.getNetworkInstance;
import static java.util.Objects.requireNonNull;

public class RegisterActivity extends BaseActivity {
public ActivityRegisterBinding registerBinding;
   public boolean isPasswordShown = false;
   public ProgressDialog progressDialog;
   public String userName,password,phone,email,confirm_password;
   public RegisterViewModel registerViewModel;
   public int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        registerBinding= DataBindingUtil.setContentView(this,R.layout.activity_register);

        registerViewModel= ViewModelProviders.of(this).get(RegisterViewModel.class);

        registerBinding.buttonRegister.setOnClickListener(v -> {
            if (validateFields()){
              userRegistration();
            }
        });

        registerBinding.textViewSignIn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });




    }

    private boolean validateFields() {
        userName = requireNonNull(registerBinding.edittextName.getText()).toString().trim();
        phone = requireNonNull(registerBinding.edittextPhone.getText()).toString().trim();
        email = requireNonNull(registerBinding.editextEmail.getText()).toString().trim();
        password = requireNonNull(registerBinding.editextPassword.getText()).toString().trim();
        confirm_password=requireNonNull(registerBinding.edittextConfirm.getText()).toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




        if (isEmpty(userName)) {
            registerBinding.edittextName.setError("Please enter your name");
            return false;
        }

        if (isEmpty(phone)) {
            registerBinding.edittextPhone.setError("Please enter phone number");
            return false;
        }

        if (isEmpty(email)) {
            registerBinding.editextEmail.setError("Please enter email");
            return false;
        }

        if (isEmpty(password)) {
            registerBinding.editextPassword.setError("Please enter password");
            return false;
        }

        if (password.length() < 6){
            registerBinding.editextPassword.setError("Password Must be atleast characters");
            return false;
        }

        if (isEmpty(confirm_password)) {
            registerBinding.edittextConfirm.setError("Please enter password");
            return false;
        }

        if(!password.equals(confirm_password)){
            registerBinding.edittextConfirm.setError("Password not matching");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length() < 5){
            registerBinding.editextEmail.setError("Invalid email address");
            return false;
        }

        if(phone.length() < 8){
            registerBinding.edittextPhone.setError("Invalid phone number");
            return false;
        }

        return true;
    }

    public void userRegistration(){
        if (getNetworkInstance(this).isConnectedToInternet()){
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Please wait");
            progressDialog.show();

            registerViewModel.registerUser(userName,phone,email,password).observe(this,registerResponse -> {
                progressDialog.dismiss();
                if (registerResponse != null && registerResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    SharedPreferences sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedpreferences.edit();
                    editor.putBoolean(Constants.IsUserLogIn, true);
                    editor.putString(Constants.USER_ID,registerResponse.getUser().get(position).getUser_id());
                    editor.putString(Constants.USER_NAME,registerResponse.getUser().get(position).getUsername());
                    editor.putString(Constants.PHONE,registerResponse.getUser().get(position).getPhone());
                    editor.putString(Constants.EMAIL,registerResponse.getUser().get(position).getEmail());
                    editor.commit();
                  startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                  finish();
                }
                else {
                    //showSnackBar(this,commonResponse.getStatus());
                }
            });

        }
        else {
            showErrorSnackBar(this, getString(R.string.no_internet_message));
        }
    }
}