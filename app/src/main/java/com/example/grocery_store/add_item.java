package com.example.grocery_store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class add_item extends AppCompatActivity {

    Toolbar mtool;
    ImageView item;
    TextView name,price,item_amount;
    Button addition,substraction,add;
    DatabaseReference mdata;
    FirebaseAuth mauth;
    String currentdate,currenttime;
    Button cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mtool = (Toolbar) findViewById(R.id.app_tool);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Grocery Basket");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String item_name=getIntent().getExtras().get("name").toString();
        final String item_price=getIntent().getExtras().get("price").toString();
        final String item_image=getIntent().getExtras().get("image").toString();

        Intialize();
       mauth=FirebaseAuth.getInstance();
        final String currentuser=mauth.getCurrentUser().getUid();


        mdata= FirebaseDatabase.getInstance().getReference();

        Picasso.get().load(item_image).into(item);
        name.setText(item_name);
        price.setText(item_price);

        cart=(Button)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(add_item.this, cart.class));
            }
        });

        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item_amount.getText().equals("10")){
                    Toast.makeText(add_item.this, "No more", Toast.LENGTH_SHORT).show();
                }
                else{
                    int t=Integer.parseInt(item_amount.getText().toString());
                    t+=1;
                    item_amount.setText(String.valueOf(t));
                }
            }
        });

        substraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item_amount.getText().equals("0")){
                }
                else{
                    int t=Integer.parseInt(item_amount.getText().toString());
                    t-=1;
                    item_amount.setText(String.valueOf(t));
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(item_amount.getText().toString())>0) {
                    mdata.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if (dataSnapshot.child("user_orders").child(currentuser).child("Cart").child(item_name).exists()) {
                                try {
                                    mdata.child("user_orders").child(currentuser).child("Cart").child(item_name).child("amount").setValue(item_amount.getText().toString());
                                    mdata.child("user_orders").child(currentuser).child("Cart").child(item_name).child("charge").setValue(String.valueOf(Integer.parseInt(item_price.replaceAll("[$ ]", "")) * Integer.parseInt(item_amount.getText().toString())));
                                   // Toast.makeText(add_item.this, "Added to cart", Toast.LENGTH_SHORT).show();

                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }else{
                                try {
                                    mdata.child("user_orders").child(currentuser).child("Cart").child(item_name).child("name").setValue(item_name);
                                    mdata.child("user_orders").child(currentuser).child("Cart").child(item_name).child("image").setValue(item_image);
                                    mdata.child("user_orders").child(currentuser).child("Cart").child(item_name).child("price").setValue(item_price);
                                    mdata.child("user_orders").child(currentuser).child("Cart").child(item_name).child("amount").setValue(item_amount.getText().toString());
                                    mdata.child("user_orders").child(currentuser).child("Cart").child(item_name).child("charge").setValue(String.valueOf(Integer.parseInt(item_price.replaceAll("[$ ]", "")) * Integer.parseInt(item_amount.getText().toString())));
                                    //Toast.makeText(add_item.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(add_item.this, "Added to cart", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(add_item.this, "Please select some item", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void Intialize() {

        item=(ImageView)findViewById(R.id.item_image);
        name=(TextView)findViewById(R.id.header);
        price=(TextView)findViewById(R.id.price_header);
        addition=(Button)findViewById(R.id.add_button);
        substraction=(Button)findViewById(R.id.sub_button);
        add=(Button)findViewById(R.id.add_to_cart);
        item_amount=(TextView)findViewById(R.id.amount);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id== android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
