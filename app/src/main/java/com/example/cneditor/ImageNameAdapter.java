package com.example.cneditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageNameAdapter extends RecyclerView.Adapter<ImageNameAdapter.ImageNameHolder> {

    List<String> list;
    Context mcontext;

    public ImageNameAdapter(List<String> list , Context mcontext)
    {
        this.list = list;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ImageNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v =    LayoutInflater.from(parent.getContext()).inflate(R.layout.image_name_view,parent,false);
        ImageNameHolder imageNameHolder = new ImageNameHolder(v);
        return imageNameHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageNameHolder holder, int position) {

        holder.imagename.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ImageNameHolder extends RecyclerView.ViewHolder
    {
        TextView imagename;
        public ImageNameHolder(@NonNull View itemView) {
            super(itemView);
            imagename = itemView.findViewById(R.id.image_name);
        }
    }
}
