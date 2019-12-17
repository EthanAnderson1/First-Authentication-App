package com.example.myfirstathentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.SignInMethodQueryResult;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class HomeActivity extends AppCompatActivity {

    Button logoutbtn;
    Button deleteAccountbtn;
    FirebaseAuth mFirebaseAuth;
    Button submitChangeEmailbtn;
    EditText newEmailtxt;
    EditText userSearchtxt;
    TextView userFoundtxt;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Intent intToLogin = new Intent(HomeActivity.this,LoginActivity.class);
        logoutbtn = findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println("running");
                getInstance().signOut();
                startActivity(intToLogin);
            }
        });
        deleteAccountbtn = findViewById(R.id.deleteAccountbtn);
        deleteAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = getInstance().getCurrentUser();
                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(intToLogin);
                        }
                    }
                });
            }
        });
        submitChangeEmailbtn = findViewById(R.id.submitEmailbtn);
        submitChangeEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEmailtxt = findViewById(R.id.editEmailtxt);
                String newEmail = newEmailtxt.getText().toString();
                FirebaseUser currentUser = getInstance().getCurrentUser();
                currentUser.updateEmail(newEmail);
                Toast.makeText(HomeActivity.this, "Email Address Updated", Toast.LENGTH_SHORT).show();
            }
        });
        userSearchtxt = findViewById(R.id.searchUsertxt);
        userSearchtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("running");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("running");
            }

            @Override
            public void afterTextChanged(Editable s) {
                userFoundtxt = findViewById(R.id.userFoundtxt);
                System.out.println("running");
                Task<SignInMethodQueryResult> result = mFirebaseAuth.fetchSignInMethodsForEmail(userSearchtxt.getText().toString());
                if (result!=null){
                    userFoundtxt.setText("User Found!");
                }else {
                    userFoundtxt.setText("User Doesn't Exist");
                }
            }
        });
    }
}
