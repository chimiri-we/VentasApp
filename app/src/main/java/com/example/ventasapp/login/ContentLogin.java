package com.example.ventasapp.login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ventasapp.R;


public class ContentLogin extends AppCompatActivity {


    private Fragment fragmentR, fragmentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        Fragment fragmentL = new LoginActivity();
        fragmentR = new RegistroFragment();
        fragmentLocation = new RegistroPerfilFragment();
       getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, fragmentL).commit();

    }
    public void onClick (View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId())
        {
            case R.id.registrarme: transaction.replace(R.id.contenedorFragment, fragmentR);
               transaction.addToBackStack(null);
                break;
            case R.id.recuperar_password: transaction.replace(R.id.contenedorFragment, fragmentLocation);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();

    }
}
