package com.example.grocery_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class confirm_address extends AppCompatActivity {

    Toolbar mtool;
    RadioGroup r1;
    EditText new_address;
    RadioButton radiobutton,r2;
    Button proceed;
    FirebaseAuth mauth;
    DatabaseReference mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_address);

        mtool = (Toolbar) findViewById(R.id.app_tool);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Confirm address");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intialize();

        mauth=FirebaseAuth.getInstance();
        String currentuser=mauth.getCurrentUser().getUid();
        mdata= FirebaseDatabase.getInstance().getReference("Users").child(currentuser).child("address");

        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                r2.setText(dataSnapshot.getValue().toString());
                r2.setPaintFlags(r2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r2=r1.getCheckedRadioButtonId();

                radiobutton=findViewById(r2);
                if(radiobutton.equals("address1")) {
                    Toast.makeText(confirm_address.this, radiobutton.getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(confirm_address.this, new_address.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void Intialize() {

        r1=(RadioGroup)findViewById(R.id.radio);
        r2=(RadioButton)findViewById(R.id.address1);
        proceed=(Button)findViewById(R.id.proceed);
        new_address=(EditText)findViewById(R.id.address2);

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

    public void checkbutton(View v){

        int r2=r1.getCheckedRadioButtonId();

        radiobutton=findViewById(r2);

    }
}
