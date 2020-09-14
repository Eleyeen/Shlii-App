package com.example.shliapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.Models.getShoppingList.GetShoppingDataModel;
import com.example.shliapp.Models.getShoppingList.Item;
import com.example.shliapp.R;

import java.util.List;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.MyViewHolder> {
    private Context context;
    private List<Item> modelList;
    private List<Item> items;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public ShoppingItemAdapter(Context context, List<Item> modelList) {
        this.context = context;
//        this.listItems = modelList;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_row_dow_layout, parent, false);
        return new ShoppingItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Item item = modelList.get(position);
        myViewHolder.tvDownItemName.setText(item.getItemTitle());
        myViewHolder.tvRowQuantity.setText(item.getItemNumber());





    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        private TextView tvDownItemName,tvRowQuantity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDownItemName = itemView.findViewById(R.id.tvDownItemName);
            tvRowQuantity = itemView.findViewById(R.id.tvRowQuantity);

        }
    }


}