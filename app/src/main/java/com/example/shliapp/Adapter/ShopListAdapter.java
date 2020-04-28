package com.example.shliapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Activities.LoginActivity;
import com.example.shliapp.Activities.StartBottomActivity;
import com.example.shliapp.Models.DeleteModel;
import com.example.shliapp.Models.DeleteShoppingList.DeleteShopList;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.Datum;
import com.example.shliapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.MyviewHolder>{
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    View view;
    private Context context;
    private List<Datum> modelListP ;
    private List<Datum>  listItemsP;
    private  Context mContext;
    public ShopListAdapter(Context context, List<Datum> modelListP) {
        this.context = context;
        this.listItemsP = modelListP;
        this.modelListP = modelListP;
    }


    @NonNull
    @Override
    public ShopListAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_cardview, parent , false) ;


        return new ShopListAdapter.MyviewHolder(view);    }


    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.MyviewHolder myViewHolder, int position) {

        final Datum item = modelListP.get(position);

        myViewHolder.tvItemName.setText(item.getItemTitle());
        myViewHolder.tvQuantity.setText(item.getQuantity());
        viewBinderHelper.bind(myViewHolder.swipeRevealLayout, item.getId());
        myViewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteItem(item.getId());
            }
        });

    }


    @Override
    public int getItemCount() {
        return modelListP.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuantity;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView tvItemName;
        private TextView tvDelete;




        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            swipeRevealLayout=itemView.findViewById(R.id.swipe_layout_1);
            tvItemName =itemView.findViewById(R.id.tvItems);
            tvQuantity=itemView.findViewById(R.id.tvQuantity);
            tvDelete=itemView.findViewById(R.id.tvDelete);

        }
    }

    private void DeleteItem(String id ){

        Call<DeleteShopList> call = services.deleteShopingList(id);
        call.enqueue(new Callback<DeleteShopList>() {
            @Override
            public void onResponse(Call<DeleteShopList> call, Response<DeleteShopList> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Item Delete", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<DeleteShopList> call, Throwable t) {

            }
        });
    }

}




