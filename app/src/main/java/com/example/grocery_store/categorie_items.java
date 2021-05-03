package com.example.grocery_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class categorie_items extends AppCompatActivity {

    Toolbar mtool;
    DatabaseReference mdata;
    RecyclerView mrecycle;
    item_categorie_adapter my1adapter;
    ArrayList<items_categorie> list_cat;
    String header;
    Button cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_items);

        mtool = (Toolbar) findViewById(R.id.app_tool);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Grocery");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cart=(Button)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categorie_items.this, cart.class));
            }
        });

        header=getIntent().getExtras().get("head").toString();
        Log.i("header",header);
        TextView head_text1=(TextView)findViewById(R.id.head_text);
        head_text1.setText(header);
        mdata = FirebaseDatabase.getInstance().getReference("categories").child(header).child("items");
        mrecycle = (RecyclerView) findViewById(R.id.m1recycle);
        mrecycle.setLayoutManager(new GridLayoutManager(this, 3));
        list_cat = new ArrayList<items_categorie>();
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        items_categorie m = data.getValue(items_categorie.class);
                        list_cat.add(m);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                my1adapter =new item_categorie_adapter(categorie_items.this,list_cat);
                mrecycle.setAdapter(my1adapter);
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
