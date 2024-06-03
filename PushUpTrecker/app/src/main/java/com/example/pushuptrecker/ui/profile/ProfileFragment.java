package com.example.pushuptrecker.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pushuptrecker.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ProfileFragment extends Fragment {
    //Codes of actions
    private static final int CAMERA_REQUEST = 11;
    private static final int STORAGE_REQUEST = 21;
    private static final int GALLERY_RESULT = 10;
    private static final int CROP_RESULT = 20;
    //Permissions for actions
    String cameraPermission;
    String[] storagePermissions;
    //Flags
    boolean isProfileEdit;
    //Views
    private ImageButton editImageIBtn;
    private ImageButton editProfileIBtn;
    private ImageView profileIV;
    private Uri imageURI;
    private String picturePath;
    private EditText nameEdt;
    private EditText ageEdt;
    private EditText sexEdt;
    private EditText locationEdt;
    private ListView linksLV;
    //Root view
    private View root;

    //Binding with ViewModel
    private FragmentProfileBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = init(inflater, container);

        cameraPermission = Manifest.permission.CAMERA;
        storagePermissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        editImageIBtn.setOnClickListener(v -> {
            Intent gallery_Intent = new Intent(getContext(), SelectImageActivity.class);
            startActivityForResult(gallery_Intent, GALLERY_RESULT);
        });

        editProfileIBtn.setOnClickListener(v -> {
            setEditableProfile();
        });
        return root;
    }

    private View init(@NotNull LayoutInflater inflater, ViewGroup container) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        isProfileEdit = false;
        profileIV = binding.profileImage;
        nameEdt = binding.profileName;
        ageEdt = binding.profileAge;
        sexEdt = binding.profileSex;
        locationEdt = binding.profileLocation;

        editImageIBtn = binding.editProfileImageBtn;
        editProfileIBtn = binding.editProfileBtn;

        return root;
    }

    private boolean checkCameraPermission(){
        return ContextCompat.checkSelfPermission(this.getContext(), cameraPermission) ==
                (PackageManager.PERMISSION_GRANTED);
    }

    private boolean checkStoragePermission(){
        return ContextCompat.checkSelfPermission(this.getContext(), storagePermissions[0]) ==
                (PackageManager.PERMISSION_GRANTED) &&
                ContextCompat.checkSelfPermission(this.getContext(), storagePermissions[1]) ==
                        (PackageManager.PERMISSION_GRANTED);
    }

    private void requestCameraPermission(){
        requestPermissions(new String[]{cameraPermission}, CAMERA_REQUEST);
    }

    private void requestStoragePermission(){
        requestPermissions(storagePermissions, STORAGE_REQUEST);
    }

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);
            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_RESULT);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setEditableProfile(){
        if(isProfileEdit) {
            nameEdt.setEnabled(true);
            ageEdt.setEnabled(true);
            sexEdt.setEnabled(true);
            locationEdt.setEnabled(true);
        }else{
            nameEdt.setEnabled(false);
            ageEdt.setEnabled(false);
            sexEdt.setEnabled(false);
            locationEdt.setEnabled(false);
        }
        isProfileEdit = !isProfileEdit;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //If pick image from gallery
            case (GALLERY_RESULT): {
                if(resultCode == Activity.RESULT_OK){
                    picturePath = data.getStringExtra("picturePath");
                    //perform Crop on the Image Selected from Gallery
                    performCrop(picturePath);
                }
            }
            break;
            //If crop a picked image
            case (CROP_RESULT): {
                if(resultCode == Activity.RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable("data");
                    // Set The Bitmap Data To ImageView
                    profileIV.setImageBitmap(selectedBitmap);
                    profileIV.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = checkCameraPermission();
                    if (!cameraAccepted) {
                        Toast.makeText(this.getContext(), "Please Enable Camera Permissions", Toast.LENGTH_LONG).show();
                        requestCameraPermission();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = checkStoragePermission();
                    if (!storageAccepted) {
                        Toast.makeText(this.getContext(), "Please Enable Storage Permissions", Toast.LENGTH_LONG).show();
                        requestStoragePermission();
                    }
                }
            }
            break;
        }
    }
}