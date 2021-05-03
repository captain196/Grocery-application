package com.example.grocery_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class cart extends AppCompatActivity {

    Button payment;
    Toolbar mtool;
    RecyclerView mrecycle;
    cart_adapter cartAdapter;
    ArrayList<cart_data> cart_list;
    DatabaseReference mdata,mdata2,mdata3,mdata4;
    FirebaseAuth mauth;
    cart_adapter myadapter;
    final int UPI_PAYMENT = 0;
    String currentdate,currenttime;
    int t=0;
    TextView Total;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mtool = (Toolbar) findViewById(R.id.app_tool);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Grocery Basket");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Calendar calcfordata=Calendar.getInstance();
        SimpleDateFormat currentdate1=new SimpleDateFormat("dd MMM, yyyy");
        currentdate=currentdate1.format(calcfordata.getTime());

        Calendar calcfortime=Calendar.getInstance();
        SimpleDateFormat currenttime1=new SimpleDateFormat("hh:mm a");
        currenttime=currenttime1.format(calcfortime.getTime());


        mauth=FirebaseAuth.getInstance();
        String currentuser=mauth.getCurrentUser().getUid();
        mdata= FirebaseDatabase.getInstance().getReference("user_orders").child(currentuser).child("Cart");
        mdata4=FirebaseDatabase.getInstance().getReference("Users").child(currentuser);
        mdata2=FirebaseDatabase.getInstance().getReference("Order_demo").child(currentuser).child(currentdate+" "+currenttime);
        mdata3= FirebaseDatabase.getInstance().getReference("user_orders").child(currentuser).child("Cart");
        mrecycle = (RecyclerView) findViewById(R.id.recycle);
        mrecycle.setLayoutManager(new LinearLayoutManager(this));
        cart_list=new ArrayList<cart_data>();
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cart_list.clear();
                t=0;
                try {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        //mdata2.child(data.getKey()).child("amount").setValue(data.child(data.getKey()).child("amount").getValue());
                        cart_data m = data.getValue(cart_data.class);
                        cart_list.add(m);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                myadapter = new cart_adapter(cart.this, cart_list);
                mrecycle.setAdapter(myadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Total=(TextView)findViewById(R.id.total);


        mdata3.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    //mdata2.child("Apple").child("amount").setValue("20");
                    try {
                        t += Integer.parseInt(data.child("charge").getValue().toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
                Total.setText("₹ "+String.valueOf(t));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        payment=(Button)findViewById(R.id.proceed);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(cart.this,confirm_address.class));
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
