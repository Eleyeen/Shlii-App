package com.example.shliapp.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.Activities.AddGroceryActivity;
import com.example.shliapp.Activities.UnderSinkActivity;
import com.example.shliapp.Models.AddGrocery;
import com.example.shliapp.Models.DatumUnderSink;
import com.example.shliapp.Models.DeleteModel;
import com.example.shliapp.Models.ShppingListModel.AddShopingList.AddShopingListModel;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class UnderSinkAdapterItem extends RecyclerView.Adapter<UnderSinkAdapterItem.MyviewHolder>  {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private String  strAddItem ,strUserID,strQuality;

    private Context context;
    private List<DatumUnderSink> modelListP ;
    private List<DatumUnderSink> listItemsP;
    private  Context mContext;

    public UnderSinkAdapterItem(Context context, List<DatumUnderSink> modelListP) {
        this.context = context;
        this.listItemsP = modelListP;
        this.modelListP = modelListP;
    }



    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }


    @NonNull
    @Override
    public UnderSinkAdapterItem.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview, parent , false) ;
        return new UnderSinkAdapterItem.MyviewHolder(view);    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull UnderSinkAdapterItem.MyviewHolder myViewHolder, int position) {

        final DatumUnderSink item =  modelListP.get(position);

        myViewHolder.tvItemName.setText(item.getMitemTitle());
        myViewHolder.tvQuantity.setText(item.getQuantity());
        String stritemName = item.getMitemTitle();

        viewBinderHelper.bind(myViewHolder.swipeRevealLayout, item.getItemId());

        myViewHolder.tvDelete.setOnClickListener(v -> DeleteItem(item.getId()));
        myViewHolder.tvAddShopList.setOnClickListener(v -> AddShopList(stritemName));

    }


    @Override
    public int getItemCount() {
        return modelListP.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeRevealLayout;
        private TextView tvQuantity;
        private TextView tvItemName;
        private TextView tvStrtId;
        private TextView tvDelete;
        private TextView tvAddShopList;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tvStrtId = (TextView) itemView.findViewById(R.id.startId);
            tvItemName =itemView.findViewById(R.id.tvItems);
            tvQuantity=itemView.findViewById(R.id.tvQuantity);
            swipeRevealLayout=itemView.findViewById(R.id.swipe_layout_1);
            tvDelete=itemView.findViewById(R.id.tvDelete);
            tvAddShopList=itemView.findViewById(R.id.tvaddShop);

        }
    }
    private void DeleteItem(String id ){
        Call<DeleteModel> call = services.deleteItem(id);
        call.enqueue(new Callback<DeleteModel>() {
            @Override
            public void onResponse(Call<DeleteModel> call, Response<DeleteModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Item Delete", Toast.LENGTH_SHORT).show();
                    Intent  intent = new Intent(context, UnderSinkActivity.class);
                    context.startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<DeleteModel> call, Throwable t) {
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private  void  AddShopList(String strItem){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custoom_alertdialog);
         ImageView ivPlus = dialog.findViewById(R.id.ivPlusShopList);
        ImageView ivMinus = dialog.findViewById(R.id.ivMinusShopList);
        TextView tvItemName = dialog.findViewById(R.id.tvItemName);
        tvItemName.setText(strItem);
        Button btnAddShopList = dialog.findViewById(R.id.btnShopList);

        btnAddShopList.setOnClickListener(v -> {
            Intent intent = new Intent(context,UnderSinkActivity.class);
            context.startActivity(intent);
        });

        dialog.show();




//        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
//        Call<AddShopingListModel> addGrocery = services.AddShopListPost(
//                strAddItem,strUserID, strQuality);
//        addGrocery.enqueue(new Callback<AddShopingListModel>() {
//            @Override
//            public void onResponse(Call<AddShopingListModel> call, Response<AddShopingListModel> response) {
//
//                if (response.body() == null) {
//                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
//
//                } else if (response.body().getStatus()) {
//                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                    context.startActivity(new Intent(context, UnderSinkActivity.class));
//
//                } else {
//                    Toast.makeText(context, "something went wrong please try again with valid email", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddShopingListModel> call, Throwable t) {
//                Log.d("response", "error " + t.getMessage());
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });

    }
}
