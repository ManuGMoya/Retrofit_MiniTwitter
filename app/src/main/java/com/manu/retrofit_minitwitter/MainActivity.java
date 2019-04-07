package com.manu.retrofit_minitwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    TextView goSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ocultar el Toolbar
        getSupportActionBar().hide();

        btnLogin = findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(this);

        goSingUp = findViewById(R.id.textViewGoSingUp);
        goSingUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.buttonLogin:
                break;
            case R.id.textViewGoSingUp:
                goToSingUp();
                break;
        }
    }

    private void goToSingUp() {
        Intent i = new Intent(MainActivity.this,SingUpActivity.class);
        startActivity(i);
        finish();
    }
}
