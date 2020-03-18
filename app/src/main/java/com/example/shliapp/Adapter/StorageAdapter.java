package com.example.shliapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shliapp.Activities.UnderSinkActivity;


import com.example.shliapp.Models.StorageModelss.DatumStorage;
import com.example.shliapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {
    private Context context;
    private List<DatumStorage> modelList ;
    private List<DatumStorage>  listItems;


    public StorageAdapter(Context context, List<DatumStorage> modelList) {
        this.context = context;
        this.listItems = modelList;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storage_cardview , parent , false) ;
        return new StorageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        DatumStorage item = modelList.get(position);

        myViewHolder.tvStorageName.setText(item.getStorageName());
        myViewHolder.tvStorageItem.setText(item.getTagLine());

        //        Glide.with(context).load(item.getImageStorage()).into(myViewHolder.civStorage);

        myViewHolder.cvStorage.setOnClickListener(v -> {
            Intent  intent = new Intent(context, UnderSinkActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civStorage;
        private CardView cvStorage;
        private TextView tvStorageItem;
        private  TextView tvStorageName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            civStorage = itemView.findViewById(R.id.civCardView);
            tvStorageName = itemView.findViewById(R.id.tvCardViewName);
            tvStorageItem = itemView.findViewById(R.id.tvStorageItemCardView);
            cvStorage=itemView.findViewById(R.id.cvStorage);
        }
    }
}
