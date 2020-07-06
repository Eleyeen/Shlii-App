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
import com.example.shliapp.interfaces.SinkItemDetector;
import com.example.shliapp.utils.AppRepository;

import java.util.ArrayList;
import java.util.HashMap;
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
    private SinkItemDetector sinkItemDetector;
    int i = 0;
    private HashMap<String, Integer> hashMap = new HashMap<>();
    private String lastItemName;
    private ArrayList<String> itemTitle = new ArrayList<>();
    private ArrayList<String> itemQuantity = new ArrayList<>();
    private List<String> itemPosition = new ArrayList<>();

    public UnderSinkAdapterItem(Context context, List<DatumUnderSink> modelListP, SinkItemDetector mSinkItemDetector) {
        this.context = context;
        this.listItemsP = modelListP;
        this.modelListP = modelListP;
        this.sinkItemDetector = mSinkItemDetector;
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

        viewBinderHelper.bind(myViewHolder.swipeRevealLayout, item.getItemId());

        myViewHolder.tvDelete.setOnClickListener(v -> DeleteItem(item.getId()));
        myViewHolder.ivPlus.setOnClickListener(v -> {
            if (!itemTitle.contains(item.getMitemTitle())) {
                _counter = 0;
                _counter++;
                itemPosition.add(item.getId());
                itemTitle.add(item.getMitemTitle());
                itemQuantity.add(String.valueOf(_counter));

            } else {


                _counter = Integer.parseInt(myViewHolder.tvValue.getText().toString());
                _counter++;
                for (int i = 0; i<itemTitle.size(); i++){
                    if (itemTitle.get(i).equals(item.getMitemTitle())){
                        itemQuantity.set(i, String.valueOf(_counter));
                    }
                }
            }

            sinkItemDetector.detectArrays(itemTitle, itemQuantity);
            _stringVal = Integer.toString(_counter);
            myViewHolder.tvValue.setText(_stringVal);
//
        });

        myViewHolder.ivMinus.setOnClickListener(v -> {
            if (!itemTitle.contains(item.getMitemTitle())) {
                _counter = 0;
                if (_counter>0) {
                    _counter--;
                }
                itemPosition.add(item.getId());
                itemTitle.add(item.getMitemTitle());
                itemQuantity.add(String.valueOf(_counter));

            } else {
                _counter = Integer.parseInt(myViewHolder.tvValue.getText().toString());
                if (_counter>0) {
                    _counter--;
                }
                for (int i = 0; i<itemTitle.size(); i++){
                    if (itemTitle.get(i).equals(item.getMitemTitle())){
                        itemQuantity.set(i, String.valueOf(_counter));
                    }
                }
            }

            sinkItemDetector.detectArrays(itemTitle, itemQuantity);
            Log.d("zma arrays", itemTitle.toString()+ itemQuantity.toString());
            _stringVal = Integer.toString(_counter);
            myViewHolder.tvValue.setText(_stringVal);

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



}
