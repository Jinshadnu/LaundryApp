package com.example.laundryapp.user.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.example.laundryapp.utilities.Constants.MyPREFERENCES;


public class SessionManager {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    private static final String IS_LOGIN = "isLogin";
    private static final String USER_ID = "userID";
    private static final String USER_NAME = "userName";
    private static final String EMAIL_ID = "emailID";
    private static final String PHONE_NO = "phoneNo";
    private static final String USER_TYPE = "userType";
    private static final String PINCODE_ID="pincode_id";

    @SuppressLint("StaticFieldLeak")
    private static SessionManager sessionManager;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

    /* ------------------------------------------------------------- *
     * Constructor
     * ------------------------------------------------------------- */

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context mCtx) {
        sharedPreference = mCtx.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    /* ------------------------------------------------------------- *
     * Public Methods
     * ------------------------------------------------------------- */

    public static SessionManager getSessionInstance(Context context) {
        if (sessionManager == null)
            sessionManager = new SessionManager(context);

        return sessionManager;
    }

    /**
     * This method is invoked to store user details.
     *
     * @param userID       ID of user.
     * @param userFullName full name of user.
     * @param emailID      Email Address of user.
     * @param phoneNo      Mobile Number of user.
     * @param userType     Type of user [Client/Customer]
     */
    public void storeUserCredentials(final String userID, final String userFullName, final String emailID,
                                     final String phoneNo, final String userType,final int pincode_id) {
        editor.putString(USER_ID, userID);
        editor.putString(USER_NAME, userFullName);
        editor.putString(EMAIL_ID, emailID);
        editor.putString(PHONE_NO, phoneNo);
        editor.putString(USER_TYPE, userType);
        editor.putInt(PINCODE_ID,pincode_id);
        editor.commit();
    }

    public void storeRegistrationData(final String userType,final int pincode_id){
        editor.putInt(PINCODE_ID,pincode_id);
        editor.putString(USER_TYPE,userType);
    }

    /**
     * This method is invoked to update user details.
     *
     * @param userFullName full name of user.
     * @param emailID      Email Address of user.
     * @param phoneNo      Mobile Number of user.
     */
    public void updateUserCredentials(final String userFullName, final String emailID,
                                      final String phoneNo) {
        storeUserCredentials(getUserID(), userFullName, emailID, phoneNo, getUserType(),getPincodeId());
    }



    /**
     * This method is invoked when user is successfully logged In and we will store its login value
     * as true.
     */
    public void userLoggedIn() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    /**
     * This method is called to check whether user is logged in or not.
     *
     * @return login status of user in boolean.
     */
    public boolean isUserLoggedIn() {
        return sharedPreference.getBoolean(IS_LOGIN, false);
    }

    /**
     * This method is invoked to return user's ID.
     *
     * @return ID of user.
     */
    public String getUserID() {
        return sharedPreference.getString(USER_ID, "");
    }

    public  int getPincodeId() {
        return sharedPreference.getInt(PINCODE_ID,0);
    }

    /**
     * This method is invoked to return user's name.
     *
     * @return Name of user.
     */
    public String getUserName() {
        return sharedPreference.getString(USER_NAME, "");
    }

    /**
     * This method is invoked to return user's email ID.
     *
     * @return Registered email ID of user.
     */
    public String getUserEmailID() {
        return sharedPreference.getString(EMAIL_ID, "");
    }

    /**
     * This method is invoked to return user's mobile number.
     *
     * @return Registered mobile number of user.
     */
    public String getUserPhoneNo() {
        return sharedPreference.getString(PHONE_NO, "");
    }

    /**
     * This method is invoked to return user's mobile number.
     *
     * @return Registered mobile number of user.
     */
    public String getUserType() {
        return sharedPreference.getString(USER_TYPE, "");
    }

    /**
     * This method is invoked to clear all user details that are stored in shared preference.
     */
    public void clearUserCredentials() {
        editor.clear();
        editor.commit();
    }
}