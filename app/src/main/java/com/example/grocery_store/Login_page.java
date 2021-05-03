package com.example.grocery_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_page extends AppCompatActivity {

    TextView forget_link,signup_link;
    EditText email_id,password;
    Button login_button;
    FirebaseAuth mauth;
    DatabaseReference data;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Intialize(); //Intializing Variables
        mauth=FirebaseAuth.getInstance();
        data= FirebaseDatabase.getInstance().getReference("Users");

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1=password.getText().toString();
                if(TextUtils.isEmpty(email_id.toString())){
                    Toast.makeText(Login_page.this, "Please enter email id", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password1)|| password1.length()<6){
                    Toast.makeText(Login_page.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
                else{
                    progress.setTitle("Signing in User");
                    progress.setMessage("verifying information please wait...");
                    progress.setCanceledOnTouchOutside(true);
                    progress.show();
                    String email1=email_id.getText().toString();
                    mauth.signInWithEmailAndPassword(email1,password1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        sendtomainactivity();   //User Login Confirmed
                                        progress.dismiss();
                                    }
                                    else{
                                        Toast.makeText(Login_page.this, "Error", Toast.LENGTH_SHORT).show();
                                        progress.dismiss();
                                    }
                                }
                            });
                }
            }
        });

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_page.this,Register_page.class));
            }
        });
    }

    private void sendtomainactivity() {

        Intent mainintent=new Intent(Login_page.this,Home_page.class);
        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        finish();
    }


    //Intializing variables
    private void Intialize() {
        forget_link=(TextView)findViewById(R.id.forget);
        signup_link=(TextView)findViewById(R.id.sign_up);
        email_id=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login_button=(Button)findViewById(R.id.login_button);
        progress=new ProgressDialog(this);
    }
}
