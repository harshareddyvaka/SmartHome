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

public class Screen3 extends AppCompatActivity {

    EditText  username,email,password,confirmPassword;
    Button signUp;
    TextView signIn;
    String emailpatttern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        username = findViewById(R.id.sign_up_username);
        email = findViewById(R.id.sign_up_mail);
        password = findViewById(R.id.sign_up_password);
        confirmPassword = findViewById(R.id.sign_up_re_password);
        signIn = findViewById(R.id.text_sign_up);
        signUp = findViewById(R.id.button);
        pro = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2  = new Intent(getApplicationContext(),Screen2.class);

                startActivity(act2);
            }
        });

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SignupCode();

                }
            });


    }
    private void SignupCode(){
        String userName = username.getText().toString();
        String emaill = email.getText().toString();
        String passwordd = password.getText().toString();
        String cpasswordd = confirmPassword.getText().toString();

        if(!emaill.matches(emailpatttern)){
            Toast.makeText(getApplicationContext(),"Email Patter does'nt Match",Toast.LENGTH_SHORT).show();
        }
        else if(passwordd.isEmpty()){
            Toast.makeText(getApplicationContext(),"password Empty!",Toast.LENGTH_SHORT).show();
        }
        else if(passwordd.isEmpty() || passwordd.length()<5){
            Toast.makeText(getApplicationContext(),"Must contain atleast 5 characters",Toast.LENGTH_SHORT).show();
        }
        else if(!passwordd.equals(cpasswordd)){
            Toast.makeText(getApplicationContext(),"passwords doesn't match",Toast.LENGTH_SHORT).show();
        }
        else{
            pro.setMessage("Registering....");
            pro.setTitle("Registration");
            pro.setCanceledOnTouchOutside(false);
            pro.show();

            mAuth.createUserWithEmailAndPassword(emaill,passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pro.dismiss();
                        toNextActivity();
                        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
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