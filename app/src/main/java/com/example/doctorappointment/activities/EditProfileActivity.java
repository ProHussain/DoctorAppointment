package com.example.doctorappointment.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.doctorappointment.R;
import com.example.doctorappointment.databinding.ActivityEditProfileBinding;
import com.example.doctorappointment.models.UserModel;
import com.example.doctorappointment.utils.Constants;
import com.example.doctorappointment.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity implements IPickResult {
    ActivityEditProfileBinding binding;
    private boolean isImagePick;
    private Bitmap bitmap;
    private int PermissionReqCode = 1102;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SetUI();

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveProfile();
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.gender));
        binding.spnGender.setAdapter(adapter);
        binding.spnGender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                binding.spnGender.showDropDown();
                return true;
            }
        });

        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionReqCode);
                } else {
                    PickProfile();
                }

            }
        });

    }

    private void PickProfile() {
        PickImageDialog.build(new PickSetup()).show(this);
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            bitmap = r.getBitmap();
            binding.profileImage.setImageBitmap(bitmap);
            isImagePick = true;
        } else {
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionReqCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PickProfile();
            } else {
                Utils.ShowToast(EditProfileActivity.this, "Permission Denied, ");
            }
        }
    }

    private void DeleteProfile() {
//        UploadImage(name, dob, location, gender);
    }

    private void SetUI() {
        binding.titleName.setText(Constants.user.getName());
        binding.edName.setText(Constants.user.getName());
        binding.edBirthday.setText(Constants.user.getDob());
        binding.edLocation.setText(Constants.user.getLocation());
        binding.spnGender.setText(Constants.user.getGender());
        isImagePick = false;
        if (Constants.user.getProfileImage() != null) {
            Glide.with(this).load(Constants.user.getProfileImage()).placeholder(R.drawable.user).into(binding.profileImage);
        }

    }

    private void SaveProfile() {
        Utils.ShowProgressDialog(EditProfileActivity.this, "Saving...");
        String name = binding.edName.getText().toString().trim();
        String dob = binding.edBirthday.getText().toString().trim();
        String location = binding.edLocation.getText().toString().trim();
        String gender = binding.spnGender.getText().toString().trim();

        if (isImagePick) {
            UserModel model = new UserModel(Constants.user.getId(), name, dob, gender, Constants.user.getEmail(), Constants.user.getPhone(), location, Constants.user.getProfileImage());
            UploadImage(model);
        } else {
            UserModel model = new UserModel(Constants.user.getId(), name, dob, gender, Constants.user.getEmail(), Constants.user.getPhone(), location, Constants.user.getProfileImage());
            WriteDB(model);
        }

    }

    private void WriteDB(UserModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(Constants.userDataBase).child(Constants.userAccountsDataBase);
        reference.child(Constants.user.getId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utils.DismissProgressDialog();
                    Utils.ShowToast(EditProfileActivity.this, "Saved");
                    Constants.user = model;
                } else {
                    Utils.DismissProgressDialog();
                    Utils.ShowToast(EditProfileActivity.this, "Error : " + task.getException().getMessage());
                }
            }
        });
    }

    private void UploadImage(UserModel model) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child(Constants.userDataBase).child(Constants.userAccountsDataBase)
                .child(Constants.user.getId() + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask =  reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Utils.ShowToast(EditProfileActivity.this,"Error : "+exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        model.setProfileImage(uri.toString());
                        WriteDB(model);
                    }
                });
            }
        });
    }
}