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
import com.manu.retrofit_minitwitter.retrofit.request.RequestSingup;
import com.manu.retrofit_minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSingUp;
    TextView tvToLogin;

    EditText edUserName, edMail, edPassword;

    MiniTwitterClient miniTwitterClient;
    MiniTwittterService miniTwittterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        // ocultar el Toolbar
        getSupportActionBar().hide();

        retrofitInit();

        findViews();

        events();

    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwittterService = miniTwitterClient.getMiniTwittterService();
    }

    private void findViews() {
        btnSingUp = findViewById(R.id.buttonSingUp);
        tvToLogin = findViewById(R.id.textViewGoLogin);
        edUserName = findViewById(R.id.editTextUsername);
        edMail = findViewById(R.id.editTextEmail);
        edPassword = findViewById(R.id.editTextPassword);
    }

    private void events() {
        btnSingUp.setOnClickListener(this);
        tvToLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.buttonSingUp:
                gotoSingUp();
                break;
            case R.id.textViewGoLogin:
                goToLogin();
                break;
        }
    }

    private void gotoSingUp() {
        String userName = edUserName.getText().toString();
        String eMail = edMail.getText().toString();
        String password = edPassword.getText().toString();

        if(userName.isEmpty()){
            edUserName.setError("Debe introducir el nombre de usuario");
        }else if(eMail.isEmpty()){
            edMail.setError("Debe insertar el correo");
        }else if(password.isEmpty() || password.length() < 4){
            edPassword.setError("Debe insertar una contraseña de al menos 4 caracteres");
        }else{
            String code = "UDEMYANDROID";
            RequestSingup requestSingup = new RequestSingup(userName,eMail,password,code);
            Call<ResponseAuth> call= miniTwittterService.doSingup(requestSingup);
            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if(response.isSuccessful()){
                        Intent i = new Intent(SingUpActivity.this, DashboardActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(SingUpActivity.this, "Ha ocurrido algun problema, revise los datos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SingUpActivity.this, "error en conexion. Intentelo de nuevo", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void goToLogin() {
        Intent i = new Intent(SingUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
