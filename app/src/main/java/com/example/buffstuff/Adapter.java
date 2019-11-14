package com.example.buffstuff;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Adapter to create card for items and add them to recyclerView in buy page
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Item> ItemList;
    Context context;

    public Adapter(List<Item>ItemList)
    {
        this.ItemList = ItemList;
    }

    @Override
    //When new view is made, make a new view holder
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    //When this adapter is bound to a view, set the holder title and price
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Item thing = ItemList.get(position);

        holder.title.setText(thing.getName());
        holder.price.setText((thing.getPrice()).toString());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String print = "" + thing.getID();
                Intent intent = new Intent(view.getContext(), DisplayItemActivity.class);
                intent.putExtra("ID", thing.getID());
                Log.d("working", "There");
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView price;
        CardView cv;
        //Create new itemView for each item in passed item list
        public ViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.text_view_name);
            price = (TextView)itemView.findViewById(R.id.text_view_price);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }
}