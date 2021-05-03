package com.example.grocery_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView phone,address1,email,user;
    FirebaseAuth mauth;
    DatabaseReference data;
    String currentuser;
    Toolbar mtool;
    Button cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mtool = (Toolbar) findViewById(R.id.app_tool);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Grocery Basket");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intialize();
        mauth=FirebaseAuth.getInstance();
        currentuser=mauth.getCurrentUser().getUid();
        data= FirebaseDatabase.getInstance().getReference("Users");

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, cart.class));
            }
        });

        data.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phone.setText(dataSnapshot.child("Phone_number").getValue().toString());
                email.setText(dataSnapshot.child("Email").getValue().toString());
                address1.setText(dataSnapshot.child("address").getValue().toString());
                user.setText(dataSnapshot.child("Name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id== android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Intialize() {

        phone=(TextView)findViewById(R.id.phone_number);
        email=(TextView)findViewById(R.id.email_id);
        address1=(TextView)findViewById(R.id.address);
        user=(TextView)findViewById(R.id.username);
        cart=(Button)findViewById(R.id.cart);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
