package com.example.ventasapp.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventasapp.R;
import com.example.ventasapp.databinding.ActividadPrincipalBinding;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.entidades.Usuarios;
import com.example.ventasapp.entidades.VolleySingleton;
import com.example.ventasapp.ui.ActividadPrincipal;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{


    ProgressDialog progreso;
    private EditText user, pass;
    private Button mSubmit, mRegister;

    private ProgressDialog pDialog;
    JsonObjectRequest jsonObjectRequest;
    // Clase JSONParser
    JSONParser jsonParser = new JSONParser();
    private static final String TABLE_USUARIO = "Usuario";


 public static final String URL = "https://servicioparanegocio.es/ventasApp/login.php";
    private LoginViewModel loginViewModel;
    private EditText usernameEditText, passwordEditText;
    public static final String Password = "Password";
    public static final String Usuario = "Usuario";

    // La respuesta del JSON es
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        // setup input fields
        usernameEditText = v.findViewById(R.id.correo);
        passwordEditText = v.findViewById(R.id.password);
        final Button mSubmit = v.findViewById(R.id.login);
        final ProgressBar loadingProgressBar = v.findViewById(R.id.loading);


        // setup buttons


        // register listeners
       mSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               new AttemptLogin().execute();
           }
       });

        return v;
    }



    @SuppressLint("StaticFieldLeak")
    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            String Usuario = usernameEditText.getText().toString();
            String Password = passwordEditText.getText().toString();
            try {
                // Building Parameters
                List params = new ArrayList();
                params.add(new BasicNameValuePair("Usuario", Usuario));
                params.add(new BasicNameValuePair("Password", Password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(URL, "POST",
                        params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    // save user data
                    obtenerDatosUsuario(Usuario);


                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(getContext(), file_url, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void obtenerDatosUsuario(String usuario) {

        String url="https://servicioparanegocio.es/ventasApp/ConsultarUsuarioNombre.php?usuario="+usuario;


        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {


        //    Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        Usuarios user = new Usuarios();

        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            assert json != null;
            jsonObject=json.getJSONObject(0);
            user.setId_usuario(jsonObject.optInt("id_producto"));
            user.setNombre(jsonObject.optString("Nombre"));
            user.setTelefono(jsonObject.optString("Telefono"));
            user.setUser(jsonObject.optString("Usuario"));
            user.setCorreo(jsonObject.optString("Correo"));
            user.setColonia(jsonObject.optString("Colonia"));
            user.setCalle(jsonObject.optString("Calle"));
            user.setDireccion(jsonObject.optString("Direccion_ciudad"));
            user.setPassword(jsonObject.optString("Password"));

            ContentValues values = new ContentValues();
            values.put("id_usuario", user.getId_usuario());
            values.put("nombre", user.getNombre());
            values.put("telefono", user.getTelefono());
            values.put("direccion", user.getDireccion());
            values.put("user", user.getUser());
            values.put("password", user.getPassword());
            values.put("uri_imagen", user.getUrlImagen());
            values.put("correo", user.getCorreo());
            values.put("calle", user.getCorreo());
            values.put("direccion", user.getCorreo());

            BaseDatos bdLocal = new BaseDatos(requireContext().getApplicationContext());
            SQLiteDatabase db = bdLocal.getReadableDatabase();
            db.insert(TABLE_USUARIO, null, values);

            Intent i = new Intent(getContext(), ActividadPrincipal.class);
            startActivity(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}



/*

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



       loginButton.setOnClickListener(v1 -> loginCliente());
        return v;
    }
    private void loginCliente() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando..");
        progressDialog.show();
      //  Intent intent = new Intent(getContext(), ActividadPrincipal.class);
       // startActivity(intent);

        final String password = passwordEditText.getText().toString().trim();
        final String username =  usernameEditText.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {


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
*/