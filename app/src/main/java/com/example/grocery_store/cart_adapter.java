package com.example.grocery_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class cart_adapter extends RecyclerView.Adapter<cart_adapter.cartViewholder>{

    Context mcontext;
    ArrayList<cart_data> cartlist;
    DatabaseReference mdata;
    FirebaseAuth mauth;


    public cart_adapter(Context mcontext, ArrayList<cart_data> cartlist) {
        this.mcontext = mcontext;
        this.cartlist = cartlist;
    }



    @NonNull
    @Override
    public cartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(mcontext).inflate(R.layout.cart_cardview,parent,false);
        return new cartViewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final cartViewholder holder, final int position) {

        mauth=FirebaseAuth.getInstance();
        String currentuser=mauth.getCurrentUser().getUid();
        holder.setdetails(mcontext,cartlist.get(position).getImage(),cartlist.get(position).getName(),cartlist.get(position).getPrice(),cartlist.get(position).getAmount(),"$ "+String.valueOf(Integer.parseInt(cartlist.get(position).getAmount())*Integer.parseInt(cartlist.get(position).getPrice().replaceAll("[₹ ]",""))));
        mdata= FirebaseDatabase.getInstance().getReference("user_orders").child(currentuser).child("Cart");
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.amount.getText().toString().equals("10")){
                    Toast.makeText(mcontext, "No more", Toast.LENGTH_SHORT).show();
                }
                else{
                    int t=Integer.parseInt(holder.amount.getText().toString());
                    t+=1;
                    holder.amount.setText(String.valueOf(t));
                    //mdata.child(cartlist.get(position).getName()).child("amount").setValue(String.valueOf(t));
                    holder.charge.setText("₹"+String.valueOf(t*Integer.parseInt(cartlist.get(position).getPrice().replaceAll("[₹ ]",""))));
                }
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.amount.getText().toString().equals("1")){
                }
                else{
                    int t=Integer.parseInt(holder.amount.getText().toString());
                    t-=1;
                    holder.amount.setText(String.valueOf(t));
                    //mdata.child(cartlist.get(position).getName()).child("amount").setValue(String.valueOf(t));
                    holder.charge.setText("$ "+String.valueOf(t*Integer.parseInt(cartlist.get(position).getPrice().replaceAll("[₹ ]",""))));
                }
            }
        });
        //mauth=FirebaseAuth.getInstance();
        //String currentuser=mauth.getCurrentUser().getUid();
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdata.child(cartlist.get(position).getName()).removeValue();
                cartlist.clear();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    class cartViewholder extends RecyclerView.ViewHolder {

        ImageView item_img;
        TextView item,price,amount,charge;
        View view;
        Button add,sub,cancel;


        public cartViewholder(@NonNull View itemView) {
            super(itemView);

            view=itemView;



        }

        public void setdetails(Context context,String image,String title,String prices,String amounts,String charges){

            item_img=(ImageView)itemView.findViewById(R.id.item_image);
            item=(TextView)itemView.findViewById(R.id.item_name);
            price=(TextView)itemView.findViewById(R.id.item_price);
            amount=(TextView)itemView.findViewById(R.id.amount);
            charge=(TextView)itemView.findViewById(R.id.item_charge);
            add=(Button)itemView.findViewById(R.id.add_button);
            sub=(Button)itemView.findViewById(R.id.sub_button);
            cancel=(Button)itemView.findViewById(R.id.cancel);

            item.setText(title);
            Picasso.get().load(image).into(item_img);
            price.setText(prices);
            amount.setText(amounts);
            charge.setText(charges);

        }
    }
}
