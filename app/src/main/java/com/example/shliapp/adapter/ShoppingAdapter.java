package com.example.shliapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.R;
import com.example.shliapp.dataModels.getUserSelctedItem.SelectedItem;
import com.example.shliapp.dataModels.getUserSelctedItem.UserSelectedDataModel;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.MyViewHolder> {
    private Context context;
    private List<UserSelectedDataModel> modelList;
    private List<SelectedItem> items;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public ShoppingAdapter(Context context, List<UserSelectedDataModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_row_layout, parent, false);
        return new ShoppingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        UserSelectedDataModel item = modelList.get(position);
        myViewHolder.tvItemName.setText(item.getRowNumber());
        myViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        myViewHolder.recyclerView.setHasFixedSize(true);

        ShoppingItemAdapter adapter = new ShoppingItemAdapter(context, item.getItems());
        myViewHolder.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();




    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private TextView tvItemName, tvRowName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvRowName = itemView.findViewById(R.id.tvRowName);
            recyclerView=itemView.findViewById(R.id.rvShopping);

        }
    }


}
