package com.example.shliapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Models.GetStoresModels.Datum;
import com.example.shliapp.R;

import java.util.List;

public class ChosseStoreAdapter extends RecyclerView.Adapter<ChosseStoreAdapter.MyviewHolder>  {


    private Context context;
    private List<com.example.shliapp.Models.GetStoresModels.Datum> modelListP ;
    private List<com.example.shliapp.Models.GetStoresModels.Datum>  listItemsP;
    private  Context mContext;

    public ChosseStoreAdapter(Context context, List<com.example.shliapp.Models.GetStoresModels.Datum> modelListP) {
        this.context = context;
        this.listItemsP = modelListP;
        this.modelListP = modelListP;
    }


    @NonNull
    @Override
    public ChosseStoreAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chossestore_cardview, parent , false) ;
        return new ChosseStoreAdapter.MyviewHolder(view);    }


    @Override
    public void onBindViewHolder(@NonNull ChosseStoreAdapter.MyviewHolder myViewHolder, int position) {

        final Datum item = modelListP.get(position);

        myViewHolder.tvStoreName.setText(item.getStoreName());
        GeneralUtills.putStringValueInEditor(context, "itemTitle", item.getStoreName());

    }


    @Override
    public int getItemCount() {
        return modelListP.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private TextView tvStoreName;
        private TextView tvLocation;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tvGateWay);
            tvStoreName =itemView.findViewById(R.id.tvFindFood);

        }
    }

}