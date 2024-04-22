package com.example.pushuptrecker.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pushuptrecker.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private ImageButton editImageIBtn;
    private ImageButton editProfileIBtn;
    private ImageView profileIV;
    private EditText nameEdt;
    private EditText ageEdt;
    private EditText sexEdt;
    private EditText locationEdt;
    private ListView linksLV;


    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editImageIBtn = binding.imageProfileEdit;
        editImageIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
            }
        });
        editProfileIBtn = binding.profileEdit;
        editProfileIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEdt.getLayoutParams().
                ageEdt;
                sexEdt;
                locationEdt;
                linksLV;
            }
        });

        profileIV = binding.imageProfile;

        nameEdt = binding.profileName;
        ageEdt = binding.profileAge;
        sexEdt = binding.profileSex;
        locationEdt = binding.profileLocation;
        linksLV = binding.listProfileLinks;
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    profileIV.setImageURI(selectedImageUri);
                }
            }
        }
    }
}