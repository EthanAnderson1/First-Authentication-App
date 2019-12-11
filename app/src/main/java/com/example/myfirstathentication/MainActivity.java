package com.example.myfirstathentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    EditText email_Input, password_Input;
    Button signUpbtn;
    TextView signUpToLoginlbl;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email_Input = findViewById(R.id.signUpEmailtxt);
        password_Input = findViewById(R.id.signUpPasswordtxt);
        signUpToLoginlbl = findViewById(R.id.signUpToLoginlbl);
        signUpbtn = findViewById(R.id.signUpbtn);
        signUpbtn.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_Input.getText().toString();
                String password = password_Input.getText().toString();
                if (email.isEmpty()) {
                    email_Input.setError("Please enter an email address");
                    email_Input.requestFocus();
                } else if (password.isEmpty()) {
                    password_Input.setError("Please enter a password");
                    password_Input.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Sign Up Unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signUpToLoginlbl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
