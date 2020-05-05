package com.example.shliapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.GetShoppingListNew.Item;
import com.example.shliapp.R;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {
    final int VIEW_TYPE_ROW_NUMBER = 0;
    final int VIEW_TYPE_ROW_ITEMS = 1;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    View view;
    private Context context;
    private List<Item> rowItemList;
    private Context mContext;
    String previousPosition;

    public ShoppingListAdapter(Context context, List<Item> rowItemList) {
        this.context = context;
        this.rowItemList = rowItemList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoppinglistcardview, parent, false);
        return new ShoppingListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Item item = rowItemList.get(position);
        myViewHolder.tvItemName.setText(item.getItemTitle());
        myViewHolder.tvQuantity.setText(item.getItemNumber());
        if (position > 0) {
            previousPosition = rowItemList.get(position - 1).getRowNumber();
            Log.d("zma previous", previousPosition);
            if (previousPosition.equals(item.getItemTitle())) {
                myViewHolder.tvRowNumber.setVisibility(View.GONE);
            } else {
                myViewHolder.tvRowNumber.setVisibility(View.GONE);

                myViewHolder.tvRowNumber.setText(item.getRowNumber());
            }
        }




    }

    @Override
    public int getItemCount() {
        return rowItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuantity, tvItemName, tvRowNumber;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRowNumber = itemView.findViewById(R.id.tvshopingListRoomName);
            tvItemName = itemView.findViewById(R.id.shopingListItemName);
            tvQuantity = itemView.findViewById(R.id.shopingListItemValue);
        }
    }
}
