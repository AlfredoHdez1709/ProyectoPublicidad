package com.ahrsoft.movil.proyectofood.Activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahrsoft.movil.proyectofood.R;

/**
 * Created by alfre on 07/05/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] titles = {"Chapter One",

            "Chapter Two",

            "Chapter Three",

            "Chapter Four",

            "Chapter Five",

            "Chapter Six",

            "Chapter Seven",

            "Chapter Eight"};



    private String[] details = {"Item one details",

            "Item two details", "Item three details",

            "Item four details", "Item file details",

            "Item six details", "Item seven details",

            "Item eight details"};



    private int[] images = { R.drawable.add_btn,

            R.drawable.add_btn,

            R.drawable.add_btn,

            R.drawable.add_btn,

            R.drawable.add_btn,

            R.drawable.add_btn,

            R.drawable.add_btn,

            R.drawable.add_btn };



    class ViewHolder extends RecyclerView.ViewHolder{



        public int currentItem;

        public ImageView itemImage;

        public TextView itemTitle;

        public TextView itemDetail;



        public ViewHolder(View itemView) {

            super(itemView);

            itemImage = (ImageView)itemView.findViewById(R.id.imgplatillo);

            itemTitle = (TextView)itemView.findViewById(R.id.txttitlePlatillo);

            itemDetail =

                    (TextView)itemView.findViewById(R.id.txtdescPlatillo);

        }

    }



    @Override

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())

                .inflate(R.layout.card_platillos, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }



    @Override

    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.itemTitle.setText(titles[i]);

        viewHolder.itemDetail.setText(details[i]);

        viewHolder.itemImage.setImageResource(images[i]);

    }



    @Override

    public int getItemCount() {

        return titles.length;

    }
}
