package com.example.shliapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Activities.StartBottomActivity;
import com.example.shliapp.Models.LocationModels.Store;
import com.example.shliapp.R;

import java.util.List;

public class ChosseStoreAdapter extends RecyclerView.Adapter<ChosseStoreAdapter.MyviewHolder> {


    private Context context;
    private List<Store> modelListP;
    private List<Store> listItemsP;
    private Context mContext;

    public ChosseStoreAdapter(Context context, List<Store> modelListP) {
        this.context = context;
        this.listItemsP = modelListP;
        this.modelListP = modelListP;
    }


    @NonNull
    @Override
    public ChosseStoreAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chossestore_cardview, parent, false);
        return new ChosseStoreAdapter.MyviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChosseStoreAdapter.MyviewHolder myViewHolder, int position) {

        final Store item = modelListP.get(position);

        myViewHolder.tvStoreName.setText(item.getStoreName());

        myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtills.putStringValueInEditor(context, "itemTitle", item.getStoreName());
                GeneralUtills.putStringValueInEditor(context, "store_id", String.valueOf(item.getStoreID()));
                GeneralUtills.putBooleanValueInEditor(context, "change_store", true);
                Intent intent = new Intent(context, StartBottomActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return modelListP.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private TextView tvStoreName;
        private TextView tvLocation;
        private RelativeLayout relativeLayout;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
//            tvLocation = itemView.findViewById(R.id.tvGateWay);
            tvStoreName = itemView.findViewById(R.id.tvFindFood);
            relativeLayout = itemView.findViewById(R.id.choseStore);
        }
    }

}
