package com.example.grocery_store;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class item_categorie_adapter extends RecyclerView.Adapter<item_categorie_adapter.myholder> {


    private Context mcontext;
    private ArrayList<items_categorie> mdata;
    View view;

    public item_categorie_adapter(Context mcontext, ArrayList<items_categorie> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public item_categorie_adapter.myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater minflater= LayoutInflater.from(mcontext);
        view=minflater.inflate(R.layout.item_cardview,parent,false);
        return new item_categorie_adapter.myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull item_categorie_adapter.myholder holder, final int position) {
        holder.setdetail(mcontext,mdata.get(position).getItem_name(),mdata.get(position).getItem_price(),mdata.get(position).getItem_image());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(mcontext,add_item.class);
                myintent.putExtra("name",mdata.get(position).getItem_name());
                myintent.putExtra("price",mdata.get(position).getItem_price());
                myintent.putExtra("image",mdata.get(position).getItem_image());
                mcontext.startActivity(myintent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }
    public static class myholder extends RecyclerView.ViewHolder {

        ImageView images;
        TextView text1, text2;
        View view;

        public myholder(@NonNull View itemView) {
            super(itemView);

            view = itemView;

        }

        public void setdetail(Context context, String name, String prices, String image) {

            this.images = (ImageView) itemView.findViewById(R.id.image1);
            this.text1 = (TextView) itemView.findViewById(R.id.name);
            this.text2 = (TextView) itemView.findViewById(R.id.price);


            text1.setText(name);
            text2.setText(prices);
            Picasso.get().load(image).into(images);

        }

    }
}
