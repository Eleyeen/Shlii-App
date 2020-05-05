package com.example.shliapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.Models.DeleteShoppingList.DeleteShopList;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.GetShoppingListNew.Datum;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.GetShoppingListNew.Item;
import com.example.shliapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class ShopListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int VIEW_TYPE_ROW_NUMBER = 0;
    final int VIEW_TYPE_ROW_ITEMS = 1;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    View view;
    private Context context;
    private List<Item> rowItemList;
    private List<Datum> rowNumberList;
    private Context mContext;

    public ShopListAdapter(Context context, List<Item> rowItemList, List<Datum> rowNumberList) {
        this.context = context;
        this.rowItemList = rowItemList;
        this.rowNumberList = rowNumberList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoppinglistcardview, parent, false);
        View rowNumberView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_number_layout, parent, false);
        if (viewType == VIEW_TYPE_ROW_NUMBER) {
            return new RowNumberViewHolder(rowNumberView);
        }

        if (viewType == VIEW_TYPE_ROW_ITEMS) {
            return new MyviewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RowNumberViewHolder) {
            ((RowNumberViewHolder) holder).populate(rowNumberList.get(position));
        }

        if (holder instanceof MyviewHolder) {
            ((MyviewHolder) holder).populate(rowItemList.get(position - rowNumberList.size()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < rowNumberList.size()) {
            return VIEW_TYPE_ROW_NUMBER;
        }

        if (position - rowNumberList.size() < rowItemList.size()) {
            return VIEW_TYPE_ROW_ITEMS;
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return rowItemList.size() + rowNumberList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuantity, tvItemName;
        private SwipeRevealLayout swipeRevealLayout;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.shopingListItemName);
            tvQuantity = itemView.findViewById(R.id.shopingListItemValue);

        }

        public void populate(Item rowItems) {
            tvItemName.setText(rowItems.getItemTitle());
            tvQuantity.setText(rowItems.getItemNumber());
        }
    }

    public class RowNumberViewHolder extends RecyclerView.ViewHolder {

        TextView tvRowNumber;

        public RowNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRowNumber = itemView.findViewById(R.id.tvshopingListRoomName);
        }

        public void populate(Datum rowNumber) {
            tvRowNumber.setText(rowNumber.getRowNumber());
        }
    }

    private void DeleteItem(String id) {

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




