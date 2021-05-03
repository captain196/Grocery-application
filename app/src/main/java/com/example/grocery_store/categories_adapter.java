package com.example.grocery_store;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import java.util.List;

public class categories_adapter extends RecyclerView.Adapter<categories_adapter.Myholder> {

    private Context mcontext;
    private ArrayList<categories> mdata;
    View view;


    public categories_adapter(Context mcontext, ArrayList<categories> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater minflater= LayoutInflater.from(mcontext);
        view=minflater.inflate(R.layout.cardview_items,parent,false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, final int position) {
        holder.setdetails(mcontext,mdata.get(position).getName(),mdata.get(position).getImage());
       holder.view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               Intent myintent=new Intent(mcontext,categorie_items.class);
               myintent.putExtra("head",mdata.get(position).getName());
               mcontext.startActivity(myintent);

           }
       });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder{

        ImageView images;
        TextView text;
        View view;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            view =itemView;

        }

        public void setdetails(Context context,String title,String image){

            this.images=(ImageView)itemView.findViewById(R.id.card_image);
            this.text=(TextView)itemView.findViewById(R.id.card_name);

            text.setText(title);
            Picasso.get().load(image).into(images);

        }

    }

}
