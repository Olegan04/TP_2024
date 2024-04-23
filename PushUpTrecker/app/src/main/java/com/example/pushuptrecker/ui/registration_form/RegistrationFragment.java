package com.example.pushuptrecker.ui.registration_form;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.pushuptrecker.MainActivity;
import com.example.pushuptrecker.R;
import com.example.pushuptrecker.databinding.FragmentRegistrationFormBinding;

public class RegistrationFragment extends Fragment {
    private EditText userNameEdit, passwordEdit;
    private Button submitBtn;

    private FragmentRegistrationFormBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegistrationViewModel registrationViewModel =
                new ViewModelProvider(this).get(RegistrationViewModel.class);

        binding = FragmentRegistrationFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        submitBtn = root.findViewById(R.id.idBtnSubmit);
        userNameEdit = root.findViewById(R.id.idEditUserName);
        passwordEdit = root.findViewById(R.id.idEditPassword);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(root.getContext(), "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }

                // calling a method to register a user.
                registerUser(userName, password);
            }
        });
        return root;
    }

    public void registerUser(String name, String password){

        Log.i("Registry",String.format("New User:\n\tName: %s\n Password: %s", name, password));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
