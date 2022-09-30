package com.example.doctorappointment.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.example.doctorappointment.activities.LoginActivity;
import com.example.doctorappointment.activities.SignUpActivity;

public class Utils {
    private static ProgressDialog dialog;
    public static void LoginDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Login Required")
                .setMessage("Login required to perform this action")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                })
                .setNegativeButton("SignUp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(context, SignUpActivity.class));
                    }
                })
                .show();
    }

    public static void ShowProgressDialog(Context context,String msg) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.show();
    }

    public static void DismissProgressDialog() {
        dialog.dismiss();
    }

    public static void ShowToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
