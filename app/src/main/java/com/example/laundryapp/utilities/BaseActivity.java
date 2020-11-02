package com.example.laundryapp.utilities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.laundryapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.graphics.Color.TRANSPARENT;
import static android.util.Base64.encodeToString;
import static android.view.Window.FEATURE_NO_TITLE;
import static android.widget.Toast.LENGTH_LONG;
import static com.google.android.material.snackbar.Snackbar.make;
import static java.text.DateFormat.DEFAULT;
import static java.util.Objects.requireNonNull;

public class BaseActivity extends AppCompatActivity {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    private Dialog dialog;

    /* ------------------------------------------------------------- *
     * Protected Methods
     * ------------------------------------------------------------- */

    /**
     * This method is invoked to hide the soft keyboard.
     *
     * @param activity the calling activity.
     */
    protected void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(INPUT_METHOD_SERVICE);

        /*Find the currently focused view, so we can grab the correct window token from it.*/
        View view = activity.getCurrentFocus();

        /*If no view currently has focus, create a new one, just so we can grab a window token
         from it*/
        if (view == null)
            view = new View(activity);

        requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * This method is invoked to display soft keyboard.
     */
    protected void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /* ------------------------------------------------------------- *
     * Public Methods
     * ------------------------------------------------------------- */

    /**
     * This method is invoked to display loading indicator.
     */
//    public void showLoadingIndicator() {
//        dialog = new Dialog(this);
//        dialog.requestWindowFeature(FEATURE_NO_TITLE);
//        requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
//        dialog.setContentView(R.layout.layout_loading_indicator);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.show();
//    }

    /**
     * This method is invoked to hide loading indicator.
     */
    public void hideLoadingIndicator() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog = null;

        }
    }

    /**
     * This method is invoked to display a snackBar in the screen.
     *
     * @param context layout in which we want to display snack bar.
     * @param message message that we want to display in snack bar.
     */
    public void showSnackBar(Activity context, String message) {
        @SuppressLint("WrongConstant") Snackbar snackbar = make(context.findViewById(android.R.id.content), message, LENGTH_LONG);
        View view = snackbar.getView();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(context.getResources().getColor(R.color.colorDarkstateBlue));
        snackbar.show();
    }

    public void showErrorSnackBar(Activity context, String message) {
        @SuppressLint("WrongConstant") Snackbar snackbar = make(context.findViewById(android.R.id.content), message, LENGTH_LONG);
        View view = snackbar.getView();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(context.getResources().getColor(R.color.colorError));
        snackbar.setDuration(3000);
        snackbar.show();
    }

    /**
     * This method is invoked to open a success dialog.
     */
    public void openSuccessDialog(String message) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(FEATURE_NO_TITLE);
        requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
        dialog.setContentView(R.layout.layout_success_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        TextView textMessage = dialog.findViewById(R.id.textMessage);
        Button buttonOk = dialog.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(v -> dialog.dismiss());
        textMessage.setText(message);


        dialog.show();
    }

//    public void openErrorDialog(String message) {
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(FEATURE_NO_TITLE);
//        requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
//        dialog.setContentView(R.layout.layout_success_dialog);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//
//        TextView textMessage = dialog.findViewById(R.id.textMessage);
//        Button buttonOk = dialog.findViewById(R.id.buttonOk);
//
//        buttonOk.setOnClickListener(v -> dialog.dismiss());
//        textMessage.setText(message);
//
//
//        dialog.show();
//    }




    /**
     * This method is invoked to convert a Bitmap Image into Base64 format.
     *
     * @param image image in bitmap format.
     * @return image in base64 format.
     */
    public String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        image.compress(PNG, 80, byteArray);
        byte[] b = byteArray.toByteArray();
        return encodeToString(b, DEFAULT);
    }
}