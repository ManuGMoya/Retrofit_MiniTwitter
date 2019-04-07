package com.manu.retrofit_minitwitter.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manu.retrofit_minitwitter.R;
import com.manu.retrofit_minitwitter.retrofit.MiniTwitterClient;
import com.manu.retrofit_minitwitter.retrofit.MiniTwittterService;
import com.manu.retrofit_minitwitter.retrofit.request.RequestLogin;
import com.manu.retrofit_minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    TextView goSingUp;

    // Variables para cada elemento del Login (email y contraseña)
    EditText edEmail,edPassword;

    MiniTwitterClient miniTwitterClient;
    MiniTwittterService miniTwittterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ocultar el Toolbar
        getSupportActionBar().hide();

        // Iniciamos nuestras variables de retrofot
        retrofitInit();

        // Agrupamos la recuperacion de las vistas
        findViews();
        // Agrupamos los eevntos
        events();
    }



    private void findViews() {
        goSingUp = findViewById(R.id.textViewGoSingUp);
        btnLogin = findViewById(R.id.buttonLogin);
        edEmail = findViewById(R.id.editTextEmail);
        edPassword = findViewById(R.id.editTextPassword);
    }

    private void events() {
        goSingUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwittterService = miniTwitterClient.getMiniTwittterService();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.buttonLogin:
                goToLogin();
                break;
            case R.id.textViewGoSingUp:
                goToSingUp();
                break;
        }
    }

    private void goToLogin() {
        // Rescatamos los valores introducidos
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        // Validamos
        if(email.isEmpty()){
            edEmail.setError("El email es requerido");
        }else if(password.isEmpty()){
            edPassword.setError("La contraseña es requerida");
        }else{
            //Podemos hacer el login - objeto de la peticion
            RequestLogin requestLogin = new RequestLogin(email,password);

            // Creamos el objeto de respuesta, haciendo uso del objeto miniTwittterService
            // llamando al metodo del servcicio de login y pasandole la peticion.
            // Esto sería la llamada
            Call<ResponseAuth> call = miniTwittterService.doLogin(requestLogin);

            // Sobre la llamada, hacemos la peticion asincrona (metodo enqueue) para lo que necesitamos
            // una clase anonima Callback de tipo ResponseAuth para gestionar la respuesta del servidor
            // Se nos generan 2 metodos para gestioanr esa comunicacion
            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    // Cuando la comunicacion ha ido bien - return code >= 200 && code < 300;
                    if(response.isSuccessful()){
                        // Informamos de que el login ha sido correcto, navegamos a la siguiente actividad y destruimos esta
                        Toast.makeText(MainActivity.this, "Sesion iniciada correctamente", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(i);
                        // destruimos este activity para que no se pueda volver
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Algo fue mal, revise sus datos de acceso", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    // en caso de fallo de comunicacion
                    Toast.makeText(MainActivity.this, "Problemas de conexion, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void goToSingUp() {
        Intent i = new Intent(MainActivity.this, SingUpActivity.class);
        startActivity(i);
        finish();
    }
}
