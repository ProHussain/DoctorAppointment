package com.example.doctorappointment.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.doctorappointment.R;
import com.example.doctorappointment.databinding.ActivityLoginBinding;
import com.example.doctorappointment.models.UserModel;
import com.example.doctorappointment.utils.Constants;
import com.example.doctorappointment.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        binding.txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPassword();
            }
        });

    }

    private void ForgotPassword() {

    }

    private void LoginUser() {
        String mail = binding.txtEditEmail.getText().toString().trim();
        String password = binding.txtEditPass.getText().toString().trim();

        if (mail.isEmpty()) {
            binding.txtEditEmail.setError("required filed is missing");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            binding.txtEditEmail.setError("required filed is invalid");
            return;
        }

        if (password.isEmpty()) {
            binding.txtEditPass.setError("required filed is missing");
            return;
        }
        Utils.ShowProgressDialog(LoginActivity.this,"Login...");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference().child(Constants.userDataBase).child(Constants.userAccountsDataBase);
                    reference.child(Objects.requireNonNull(auth.getUid())).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Utils.DismissProgressDialog();
                            UserModel model = snapshot.getValue(UserModel.class);
                            assert model != null;
                            Constants.user = model;

                            Utils.ShowToast(LoginActivity.this,"Login successfully");
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finishAffinity();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Utils.DismissProgressDialog();
                            Utils.ShowToast(LoginActivity.this,error.getMessage());
                        }
                    });
                } else {
                    Utils.DismissProgressDialog();
                    Utils.ShowToast(LoginActivity.this, "Error : "+Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }
}