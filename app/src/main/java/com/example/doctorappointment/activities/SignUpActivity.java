package com.example.doctorappointment.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doctorappointment.R;
import com.example.doctorappointment.models.UserModel;
import com.example.doctorappointment.utils.Constants;
import com.example.doctorappointment.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private EditText edName, edMail, edPassword;
    private Button btnSignUp;
    private TextView txtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindViews();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateUser();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }

    private void CreateUser() {
        String name = edName.getText().toString().trim();
        String mail = edMail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if (name.isEmpty()) {
            edName.setError("required field is missing");
            return;
        }

        if (mail.isEmpty()) {
            edMail.setError("required filed is missing");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            edMail.setError("required filed is invalid");
            return;
        }

        if (password.isEmpty()) {
            edPassword.setError("required filed is missing");
            return;
        }
        ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage("Creating User...");
        dialog.show();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserModel model = new UserModel(mAuth.getUid(),name,mail);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference().child(Constants.userDataBase).child(Constants.userAccountsDataBase);
                            reference.child(Objects.requireNonNull(mAuth.getUid())).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        Constants.user = model;
                                        Utils.ShowToast(SignUpActivity.this,"Register successfully");
                                        startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                                        finishAffinity();
                                    } else {
                                        dialog.dismiss();
                                        Utils.ShowToast(SignUpActivity.this,"Something went wrong, Please try again later");
                                        Objects.requireNonNull(mAuth.getCurrentUser()).delete();
                                    }
                                }
                            });

                        } else {
                            dialog.dismiss();
                            Utils.ShowToast(SignUpActivity.this,"Something went wrong, Please try again later");
                        }
                    }
                });
    }

    private void bindViews() {
        edName = findViewById(R.id.txtEditName);
        edMail = findViewById(R.id.txtEditEmail);
        edPassword = findViewById(R.id.txtEditPass);
        btnSignUp = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);
    }
}