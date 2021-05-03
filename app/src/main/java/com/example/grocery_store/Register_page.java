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

public class Register_page extends AppCompatActivity {

    EditText full_name,phone_number,email_id,address_flat_no,address_apartment,password;
    TextView already_login;
    Button otp_send;
    DatabaseReference data;
    ProgressDialog loadinbar,p;
    FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        Intialize();

        mauth=FirebaseAuth.getInstance();
        data= FirebaseDatabase.getInstance().getReference();

        already_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtologinactivity();
            }
        });

        otp_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(full_name.getText().toString())) {
                    Toast.makeText(Register_page.this, "Please Enter Full name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone_number.getText().toString())) {
                    Toast.makeText(Register_page.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email_id.getText().toString())) {
                    Toast.makeText(Register_page.this, "Please Enter Email id", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address_flat_no.getText().toString())) {
                    Toast.makeText(Register_page.this, "Please Enter Flat no.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address_apartment.getText().toString())) {
                    Toast.makeText(Register_page.this, "Please Enter apartment", Toast.LENGTH_SHORT).show();
                } else {
                    p.setTitle("Creating account");
                    p.setMessage("Please Wait we creating a user......");
                    p.setCanceledOnTouchOutside(true);
                    p.show();

                    String email1=email_id.getText().toString();
                    String password1=password.getText().toString();

                    mauth.createUserWithEmailAndPassword(email1,password1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        sendtomainactivity();
                                        String currentuser=mauth.getCurrentUser().getUid();
                                        data.child("Users").child(currentuser).child("Name").setValue(full_name.getText().toString());
                                        data.child("Users").child(currentuser).child("Phone_number").setValue(phone_number.getText().toString());
                                        data.child("Users").child(currentuser).child("Email").setValue(email_id.getText().toString());
                                        data.child("Users").child(currentuser).child("Password").setValue(password.getText().toString());
                                        data.child("Users").child(currentuser).child("address").setValue(address_flat_no.getText().toString()+" "+address_apartment.getText().toString());
                                        Toast.makeText(Register_page.this, "User Registered", Toast.LENGTH_SHORT).show();
                                        p.dismiss();

                                    }
                                    else{
                                        Toast.makeText(Register_page.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        p.dismiss();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void sendtomainactivity() {
        Intent mainintent=new Intent(Register_page.this,Home_page.class);
        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        finish();
    }

    private void Intialize() {

        full_name=(EditText)findViewById(R.id.name);
        phone_number=(EditText)findViewById(R.id.phone);
        email_id=(EditText)findViewById(R.id.email);
        address_flat_no=(EditText)findViewById(R.id.flat_number);
        address_apartment=(EditText)findViewById(R.id.apartment_name);
        otp_send=(Button)findViewById(R.id.otp_button);
        already_login=(TextView) findViewById(R.id.already_signup);
        password=(EditText)findViewById(R.id.password);
        p=new ProgressDialog(this);
        loadinbar=new ProgressDialog(this);
    }

    private void sendtologinactivity() {
        startActivity(new Intent(Register_page.this,Login_page.class));
    }
}
