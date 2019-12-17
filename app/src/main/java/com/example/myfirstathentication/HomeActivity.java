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
    TextView currentUsertxt;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Intent intToLogin = new Intent(HomeActivity.this,LoginActivity.class);
        currentUsertxt = findViewById(R.id.currentUsertxt);
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
                currentUsertxt.setText(newEmail);
                Toast.makeText(HomeActivity.this, "Email Address Updated", Toast.LENGTH_SHORT).show();
            }
        });
        String email = mFirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        currentUsertxt.setText(email);
    }
}
