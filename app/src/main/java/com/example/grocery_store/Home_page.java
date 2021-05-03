package com.example.grocery_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.api.Api;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static com.example.grocery_store.R.string.close;
import static com.example.grocery_store.R.string.open;

import java.util.ArrayList;
import java.util.List;

public class Home_page extends AppCompatActivity {

    Toolbar mtool;
    DrawerLayout mdrawer;
    ActionBarDrawerToggle mtoggle;
    ViewFlipper viewFlipper;
    DatabaseReference mdata;
    NavigationView navigationView;
    RecyclerView mrecycle;
    categories_adapter myadapter;
    ArrayList<categories> list_cat;
    FirebaseUser current;
    FirebaseAuth mauth;
   // ShimmerFrameLayout sh1,sh2;
    Button cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



       // sh1=(ShimmerFrameLayout)findViewById(R.id.shimmer1);
        //sh2=(ShimmerFrameLayout)findViewById(R.id.shimmer2);

        current= FirebaseAuth.getInstance().getCurrentUser();

        mtool = (Toolbar) findViewById(R.id.app_tool);
        navigationView = (NavigationView) findViewById(R.id.navi);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Grocery Basket");

        cart=(Button)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_page.this, cart.class));
            }
        });

        mdrawer = (DrawerLayout) findViewById(R.id.drawer);
        mtoggle = new ActionBarDrawerToggle(this,mdrawer,mtool,open,close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mdrawer.setDrawerListener(mtoggle);
        mtoggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.name1:
                        startActivity(new Intent(Home_page.this, profile.class));
                        return true;
                    case R.id.name3:
                        Toast.makeText(Home_page.this, "Not yet added", Toast.LENGTH_SHORT).show();
                        return  true;
                    case R.id.name4:
                        mauth.signOut();
                        Toast.makeText(Home_page.this, "Logout", Toast.LENGTH_SHORT).show();
                        sendtologinpage();//Log.i("message","name4");// Toast.makeText(Home_page.this, "Not yet added", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }

            }
        });
        navigationView.bringToFront();

        int images[] = {R.drawable.g1, R.drawable.g2, R.drawable.g4};

        viewFlipper = (ViewFlipper) findViewById(R.id.fliper);


        for (int image : images) {
            fliperimages(image);
        }
        mauth=FirebaseAuth.getInstance();
        mdata = FirebaseDatabase.getInstance().getReference("categories");
        mrecycle = (RecyclerView) findViewById(R.id.recycle);
        mrecycle.setLayoutManager(new GridLayoutManager(this, 2));
        list_cat = new ArrayList<categories>();
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_cat.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    categories m = data.getValue(categories.class);
                    list_cat.add(m);
                }
                myadapter = new categories_adapter(Home_page.this, list_cat);
                mrecycle.setAdapter(myadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fliperimages(int image){
        ImageView imageview=new ImageView(this);
        imageview.setBackgroundResource(image);

        viewFlipper.addView(imageview);
        viewFlipper.setFlipInterval(2500);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this,R.anim.slide_out_left);
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(current==null){
            sendtologinpage();
        }


    }


    private void sendtologinpage() {

        startActivity(new Intent(Home_page.this,Login_page.class));
    }



   /* @Override
    protected void onResume() {
        super.onResume();
        sh1.startShimmer();

        sh2.startShimmer();
    }

    @Override
    protected void onPause() {
        sh1.stopShimmer();
        sh2.stopShimmer();
        super.onPause();
    }*/
}
