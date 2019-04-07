package com.manu.retrofit_minitwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSingUp;
    TextView tvToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        // ocultar el Toolbar
        getSupportActionBar().hide();

        btnSingUp = findViewById(R.id.buttonSingUp);
        btnSingUp.setOnClickListener(this);

        tvToLogin = findViewById(R.id.textViewGoLogin);
        tvToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.buttonSingUp:
                break;
            case R.id.textViewGoLogin:
                goToLogin();
                break;
        }
    }

    private void goToLogin() {
        Intent i = new Intent(SingUpActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
