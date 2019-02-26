package com.example.magicpin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgrammingAdapter extends RecyclerView.Adapter<ProgrammingAdapter.ProgrammingViewHolder> {
    private String data[];
    String urls[]={"https://player.vimeo.com/external/286837767.m3u8?s=42570e8c4a91b98cdec7e7bfdf0eccf54e700b69"
    ,"https://player.vimeo.com/external/286837810.m3u8?s=610b4fee49a71c2dbf22c01752372ff1c6459b9e"
    ,"https://player.vimeo.com/external/286837723.m3u8?s=3df60d3c1c6c7a11df4047af99c5e05cc2e7ae96"
    ,"https://player.vimeo.com/external/286837649.m3u8?s=9e486e9b932be72a8875afc6eaae21bab124a35a"
    ,"https://player.vimeo.com/external/286837529.m3u8?s=20f83af6ea8fbfc8ce0c2001f32bf037f8b0f65f"
    ,"https://player.vimeo.com/external/286837402.m3u8?s=7e01c398e2f01c29ecbd46e5e2dd53e0d6c1905d"};
    Context mContext;
    public ProgrammingAdapter(Context mContext,String data[]){
        this.data=data;
        this.mContext=mContext;
    }
    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.list_item_layout,viewGroup,false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder programmingViewHolder, int i) {
        final String title=data[i];
        String uri=null;
        if(title.equals("SampleVideo1")){
            uri=urls[0];
        }
        else if(title.equals("SampleVideo2")){
            uri=urls[1];
        }
        else if(title.equals("SampleVideo3")){
            uri=urls[2];
        }
        else if(title.equals("SampleVideo4")){
            uri=urls[3];
        }
        else if(title.equals("SampleVideo5")){
            uri=urls[4];
        }
        else if(title.equals("SampleVideo6")){
            uri=urls[5];
        }
        programmingViewHolder.textView.setText(title);
        final String finalUri = uri;
        programmingViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,GalleryActivity.class);
                intent.putExtra("video_url", finalUri);
                intent.putExtra("list",urls);

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        LinearLayout parentLayout;
        public ProgrammingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageIcon);
            textView=(TextView)itemView.findViewById(R.id.txtTitle);
            parentLayout=(LinearLayout)itemView.findViewById(R.id.parentLayout);
        }
    }

}
