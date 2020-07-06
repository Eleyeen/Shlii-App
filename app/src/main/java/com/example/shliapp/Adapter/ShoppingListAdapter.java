package com.example.shliapp.Adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.example.shliapp.Models.ShppingListModel.GetShopingList.ContentItem;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.Header;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.ListItem;
import com.example.shliapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class ShoppingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    View view;
    private Context context;
    List<ListItem> list;
    private Context mContext;
    String previousPosition;

    public ShoppingListAdapter(Context context, List<ListItem> headerItems) {
        this.context = context;
        this.list = headerItems;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_number_layout, parent, false);
            return new ShoppingListAdapter.VHHeader(view);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shoppinglistcardview, parent, false);
            return new ShoppingListAdapter.VHItem(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return list.get(position) instanceof Header;
    }

    private String getItem(int position) {
        return list.get(position - 1).getName();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            VHItem vhItem = (VHItem) holder;
            ContentItem currentItem = (ContentItem) list.get(position);
            vhItem.tvItemName.setText(currentItem.getName());
            ((VHItem) holder).tvQuantity.setText(currentItem.getQuantity());
        } else if (holder instanceof VHHeader) {
            Header currentItem = (Header) list.get(position);
            VHHeader vhHeader = (VHHeader) holder;
            vhHeader.tvRowNumber.setText(currentItem.getHeader());
            viewBinderHelper.bind(vhHeader.swipeRevealLayout, String.valueOf(currentItem.getId()));
            vhHeader.tvDelete.setOnClickListener(v -> {
                DeleteItem(String.valueOf(currentItem.getId()));
                list.remove(position);

                notifyDataSetChanged();

            });


        }

    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VHItem extends RecyclerView.ViewHolder {
        private TextView tvQuantity, tvItemName;

        public VHItem(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.shopingListItemName);
            tvQuantity = itemView.findViewById(R.id.shopingListItemValue);
        }
    }

    public class VHHeader extends RecyclerView.ViewHolder {
        TextView tvRowNumber;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView tvDelete;


        public VHHeader(@NonNull View itemView) {
            super(itemView);
            tvRowNumber = itemView.findViewById(R.id.tvshopingListRoomName);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_layout_1);
            tvDelete = itemView.findViewById(R.id.tvDelete);

        }
    }

    private void DeleteItem(String id) {
        Call<DeleteShopList> call = services.deleteShopingList(id);
        call.enqueue(new Callback<DeleteShopList>() {
            @Override
            public void onResponse(Call<DeleteShopList> call, Response<DeleteShopList> response) {
                if (response.body().getStatus()) {
                    notifyDataSetChanged();
//                    Intent intent = new Intent(context, UnderSinkActivity.class);
//                    context.startActivity(intent);

                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DeleteShopList> call, Throwable t) {
            }
        });
    }

}

