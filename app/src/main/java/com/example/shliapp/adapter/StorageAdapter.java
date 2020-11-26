package com.example.shliapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.activities.GeneralUtills;
import com.example.shliapp.activities.UnderSinkActivity;


import com.example.shliapp.dataModels.deleteStorageModel.DeleteStorageResponse;
import com.example.shliapp.dataModels.storageModelss.DatumStorage;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.network.BaseNetworking.services;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {
    private Context context;
    private List<DatumStorage> modelList ;
    private List<DatumStorage>  listItems;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


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

        //        Glide.with(context).load(item.getImageStorage()).into(myViewHolder.civStorage);

        myViewHolder.cvStorage.setOnClickListener(v -> {
            GeneralUtills.putStringValueInEditor(context, "storageItem", item.getStorageName());
            AppRepository.mPutValue(context).putString("storageId", String.valueOf(item.getId())).commit();
            Intent  intent = new Intent(context, UnderSinkActivity.class);
            context.startActivity(intent);
        });
        viewBinderHelper.bind(myViewHolder.swipeRevealLayout, String.valueOf(item.getId()));

        myViewHolder.tvDelete.setOnClickListener(v -> {
            DeleteItem(String.valueOf(item.getId()));
            modelList.remove(position);

            notifyDataSetChanged();

        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView civStorage;
        private CardView cvStorage;
        private TextView tvStorageItem;
        private  TextView tvStorageName;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView tvDelete;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            civStorage = itemView.findViewById(R.id.civCardView);
            tvStorageName = itemView.findViewById(R.id.tvCardViewName);
            cvStorage=itemView.findViewById(R.id.cvStorage);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_layout_1);
            tvDelete = itemView.findViewById(R.id.tvDelete);

        }
    }
    private void DeleteItem(String id) {
        Call<DeleteStorageResponse> call = services.deleteStorage(id);
        call.enqueue(new Callback<DeleteStorageResponse>() {
            @Override
            public void onResponse(Call<DeleteStorageResponse> call, Response<DeleteStorageResponse> response) {
                if (response.body().getStatus()) {
                    notifyDataSetChanged();
//                    Intent intent = new Intent(context, UnderSinkActivity.class);
//                    context.startActivity(intent);

                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DeleteStorageResponse> call, Throwable t) {
            }
        });
    }

}
