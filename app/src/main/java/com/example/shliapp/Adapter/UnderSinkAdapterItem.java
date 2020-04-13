package com.example.shliapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Activities.StartBottomActivity;
import com.example.shliapp.Activities.UnderSinkActivity;
import com.example.shliapp.Models.DatumUnderSink;
import com.example.shliapp.Models.DeleteModel;
import com.example.shliapp.Models.ShppingListModel.AddShopingList.AddShoppingListResponse;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class UnderSinkAdapterItem extends RecyclerView.Adapter<UnderSinkAdapterItem.MyviewHolder> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private static int _counter = 0;
    private String _stringVal;

    private Context context;
    private List<DatumUnderSink> modelListP;
    private List<DatumUnderSink> listItemsP;
    private Context mContext;
    int i = 0;

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
                .inflate(R.layout.item_cardview, parent, false);
        return new UnderSinkAdapterItem.MyviewHolder(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull UnderSinkAdapterItem.MyviewHolder myViewHolder, int position) {

        final DatumUnderSink item = modelListP.get(position);

        myViewHolder.tvItemName.setText(item.getMitemTitle());
//        myViewHolder.tvQuantity.setText(item.getQuantity());
        String stritemName = item.getMitemTitle();

        viewBinderHelper.bind(myViewHolder.swipeRevealLayout, item.getItemId());

        myViewHolder.tvDelete.setOnClickListener(v -> DeleteItem(item.getId()));


        myViewHolder.ivPlus.setOnClickListener(v -> {
            _counter++;
            _stringVal = Integer.toString(_counter);
            myViewHolder.tvValue.setText(_stringVal);
            new CountDownTimer(5000, 500) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {

                    AddShopList(item.getMitemTitle());
                }
            }.start();

        });

        myViewHolder.ivMinus.setOnClickListener(v -> {
            _counter--;
            _stringVal = Integer.toString(_counter);
            myViewHolder.tvValue.setText(_stringVal);
            new CountDownTimer(5000, 500) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    AddShopList(item.getMitemTitle());

                }
            }.start();


        });


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
        private ImageView ivMinus;
        private ImageView ivPlus;
        private TextView tvValue;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tvStrtId = itemView.findViewById(R.id.startId);
            tvItemName = itemView.findViewById(R.id.tvItems);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_layout_1);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            ivMinus = itemView.findViewById(R.id.ivMinusCard);
            ivPlus = itemView.findViewById(R.id.ivPlusCard);
            tvValue = itemView.findViewById(R.id.tvValueCard);


//            tvAddShopList=itemView.findViewById(R.id.tvaddShop);
        }
    }

    private void DeleteItem(String id) {
        Call<DeleteModel> call = services.deleteItem(id);
        call.enqueue(new Callback<DeleteModel>() {
            @Override
            public void onResponse(Call<DeleteModel> call, Response<DeleteModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Item Delete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, UnderSinkActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<DeleteModel> call, Throwable t) {
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void AddShopList(String strItem) {

//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.custoom_alertdialog);
//        ImageView ivPlus = dialog.findViewById(R.id.ivPlusShopList);
//        ImageView ivMinus = dialog.findViewById(R.id.ivMinusShopList);
//        TextView tvItemName = dialog.findViewById(R.id.tvItemName);
//        TextView tvValue =dialog.findViewById(R.id.tvValue);
//
//        tvItemName.setText(strItem);
//        Button btnAddShopList = dialog.findViewById(R.id.btnShopList);
//
        String strUserID = GeneralUtills.getSharedPreferences(context).getString("userId", "");


        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<AddShoppingListResponse> addItem = services.AddShopListPost(
                strUserID, strItem, _stringVal);
        addItem.enqueue(new Callback<AddShoppingListResponse>() {
            @Override
            public void onResponse(Call<AddShoppingListResponse> call, Response<AddShoppingListResponse> response) {
                if (response.body() == null) {
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();

                } else if (response.body().getStatus()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    i = i + 1;
                    if (i == _counter) {
                        context.startActivity(new Intent(context, StartBottomActivity.class));
                        _counter = 0;

                    }
                    Log.d("zma counter", String.valueOf(_counter));

                } else {
                    Toast.makeText(context, "something went wrong please try again with valid email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddShoppingListResponse> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClick(View v) {

    }


}
