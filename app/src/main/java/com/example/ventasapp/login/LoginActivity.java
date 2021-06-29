package com.example.ventasapp.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventasapp.R;
import com.example.ventasapp.databinding.ActividadPrincipalBinding;
import com.example.ventasapp.ui.ActividadPrincipal;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText usernameEditText, passwordEditText;
    //public static final String KEY_PASSWORD = "password";
   // public static final String KEY_EMAIL = "email";
    public static String URL = "https://servicioparanegocio.es/bdApp/loginApp.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

       usernameEditText = v.findViewById(R.id.username);
       passwordEditText = v.findViewById(R.id.password);
        final Button loginButton = v.findViewById(R.id.login);
        final ProgressBar loadingProgressBar = v.findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe((LifecycleOwner) getContext(), new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                ;

                //Complete and destroy login activity once successful
             //   finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v1, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v12 -> {
            //loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
            Intent intent = new Intent(getActivity(), ActividadPrincipal.class);
            startActivity(intent);
        });



     //   loginButton.setOnClickListener(v1 -> loginCliente( URL="https://servicioparanegocio.es/bdApp/validar_usuario.php"));
        return v;
    }
    private void loginCliente(String s) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando..");
        progressDialog.show();
        Intent intent = new Intent(getContext(), ActividadPrincipal.class);
        startActivity(intent);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            if(!response.isEmpty()){
                Intent miIntent = new Intent(getContext(), ActividadPrincipalBinding.class);
                startActivity(miIntent);
            }else {
                Toast.makeText(getContext(), "uduario o contraseña son incorrectas", Toast.LENGTH_SHORT).show();

            }
        }, error -> Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("email", usernameEditText.getText().toString().trim());
                params.put("password", passwordEditText.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);

    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = "Bienvenido"+ model.getDisplayName();

        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}