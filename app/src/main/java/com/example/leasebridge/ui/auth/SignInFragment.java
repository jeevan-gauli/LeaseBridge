package com.example.leasebridge.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.leasebridge.MainActivity;
import com.example.leasebridge.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment {

    private TextInputEditText etEmail, etPassword;
    private Button btnSignIn;
    private TextView tvSignup;
    private FirebaseAuth firebaseAuth;

    public SignInFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.activity_signin, container, false);

        // 🔗 Bind views
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        tvSignup = view.findViewById(R.id.tvSignup);

        // 🔥 Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // 🔐 Sign In
        btnSignIn.setOnClickListener(v -> signInUser());

        // 📝 Go to Sign Up Fragment
        tvSignup.setOnClickListener(v -> { startActivity(new Intent(getActivity(), SignUpFragment.class)); });

        return view;
    }

    private void signInUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // ✅ Validation
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        // 🔥 Firebase Login
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        Toast.makeText(
                                getActivity(),
                                "Login successful",
                                Toast.LENGTH_SHORT
                        ).show();

                        startActivity(new Intent(getActivity(), MainActivity.class));
                        requireActivity().finish();

                    } else {
                        Toast.makeText(
                                getActivity(),
                                "Login failed: " +
                                        (task.getException() != null
                                                ? task.getException().getMessage()
                                                : "Unknown error"),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}
