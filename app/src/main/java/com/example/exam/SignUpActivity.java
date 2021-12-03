package com.example.exam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity{
    Button btnSignUp, btnAccount;
    EditText edEmail, edPassword, edFirstName, edSecName, edRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edEmail = findViewById(R.id.etEmail);
        edFirstName = findViewById(R.id.etFirstName);
        edSecName = findViewById(R.id.etSecName);
        edPassword = findViewById(R.id.etPassword);
        edRePassword = findViewById(R.id.etRePassword);
        btnSignUp = findViewById(R.id.btnAccount);
        btnAccount = findViewById(R.id.btnSignUp);

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmail.getText().toString())||
                    TextUtils.isEmpty(edPassword.getText().toString())||
                    TextUtils.isEmpty(edSecName.getText().toString())||
                    TextUtils.isEmpty(edFirstName.getText().toString())||
                    TextUtils.isEmpty(edRePassword.getText().toString()))
                {
                    ShowAlertDialogWindow("Есть пустые поля!");
                }
                else if (!edPassword.getText().toString().equals(edRePassword.getText().toString()))
                {
                    ShowAlertDialogWindow("Пароли не совпадают");
                }
                else {
                    registerUser();
                }

            }

        });
    }

    private boolean emailVaild(String email)
    {
        Pattern emailPattern = Pattern.compile("a-z.@[a-z]+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public void registerUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(edEmail.getText().toString());
        registerRequest.setFirstname(edFirstName.getText().toString());
        registerRequest.setLastname(edSecName.getText().toString());
        registerRequest.setPassword(edPassword.getText().toString());

        Call<RegisterResponse> registerResponseCall = ApiClient.getRegister().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>(){
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response){
                if(response.isSuccessful()){
                    String message = "Все ок";
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    String message = "Что-то пошло не так";
                    Toast.makeText(SignUpActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable throwable){
                String message = "Регистрация прошла успешно";
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });
    }

    public void ShowAlertDialogWindow(String s){
        AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).setMessage(s).setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
        }).create();
        alertDialog.show();
    }

}

