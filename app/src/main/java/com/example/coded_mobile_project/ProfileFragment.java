package com.example.coded_mobile_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView nameTextView, emailTextView;
    private Button editProfileButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI components
        nameTextView = view.findViewById(R.id.profile_name);
        emailTextView = view.findViewById(R.id.profile_email);
        editProfileButton = view.findViewById(R.id.edit_profile);

        // Set initial values
        nameTextView.setText("John Doe");
        emailTextView.setText("johndoe@example.com");

        // Set button click listener
        editProfileButton.setOnClickListener(v -> {
            // Handle edit profile action
            Toast.makeText(getActivity(), "Edit Profile clicked!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
