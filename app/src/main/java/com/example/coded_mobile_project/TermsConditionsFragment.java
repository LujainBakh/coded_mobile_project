package com.example.coded_mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TermsConditionsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_conditions, container, false);

        // Reference TextView and load styled text
        TextView termsTextView = view.findViewById(R.id.tvTermsConditions);
        String termsText = getString(R.string.terms_and_conditions);
        termsTextView.setText(Html.fromHtml(termsText, Html.FROM_HTML_MODE_LEGACY));

        // Handle "Agree" button click
        Button agreeButton = view.findViewById(R.id.btnAgree);
        agreeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }
}
