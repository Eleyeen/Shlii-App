package com.example.shliapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.ContentItem;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.Datum;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.Header;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.Item;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.ListItem;
import com.example.shliapp.R;

import java.util.List;

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
            ((VHItem) holder).tvQuantity.setText(currentItem.getRollnumber());
        } else if (holder instanceof VHHeader) {
            Header  currentItem = (Header) list.get(position);
            VHHeader vhHeader = (VHHeader) holder;
            vhHeader.tvRowNumber.setText(currentItem.getHeader());
        }
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

        public VHHeader(@NonNull View itemView) {
            super(itemView);
            tvRowNumber = itemView.findViewById(R.id.tvshopingListRoomName);

        }
    }
}

