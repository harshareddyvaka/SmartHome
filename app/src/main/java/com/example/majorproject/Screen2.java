package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Screen2 extends AppCompatActivity {

    EditText Email, password;
    Button signIn;
    TextView dontyouhaveacc;

    String emailpatttern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        Email = findViewById(R.id.sign_in_username);
        password = findViewById(R.id.sign_in_password);

        signIn = findViewById(R.id.button);

        dontyouhaveacc = findViewById(R.id.text_sign_up);

        pro = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        dontyouhaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 = new Intent(getApplicationContext(), Screen3.class);
                startActivity(act2);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInCode();

            }
        });


    }

    private void logInCode() {

        String emaill = Email.getText().toString();
        String passwordd = password.getText().toString();

        if (!emaill.matches(emailpatttern)) {
            Toast.makeText(getApplicationContext(), "Email Patter does'nt Match", Toast.LENGTH_SHORT).show();
        } else if (passwordd.isEmpty()) {
            Toast.makeText(getApplicationContext(), "password Empty!", Toast.LENGTH_SHORT).show();
        } else if (passwordd.isEmpty() || passwordd.length() < 5) {
            Toast.makeText(getApplicationContext(), "Must contain atleast 5 characters", Toast.LENGTH_SHORT).show();
        } else {
            pro.setMessage("Authenticating...");
            pro.setTitle("Login");
            pro.setCanceledOnTouchOutside(false);
            pro.show();

            mAuth.signInWithEmailAndPassword(emaill,passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pro.dismiss();
                        toNextActivity();
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        pro.dismiss();
                        Toast.makeText(getApplicationContext()," " + task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void toNextActivity() {
        Intent act2 = new Intent(getApplicationContext(), Screen4.class);
        act2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(act2);
    }

}