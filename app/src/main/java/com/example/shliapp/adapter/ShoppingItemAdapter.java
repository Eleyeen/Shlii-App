package com.example.shliapp.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.shliapp.R;
import com.example.shliapp.dataModels.allAds.AllAdsDataModel;
import com.example.shliapp.dataModels.allAds.AllAdsResponse;
import com.example.shliapp.dataModels.deleteNewShoppingList.DeleteNewShoppingResponse;
import com.example.shliapp.dataModels.getUserSelctedItem.SelectedItem;
import com.example.shliapp.fragment.ShoppingFragment;
import com.example.shliapp.network.BaseNetworking;
import com.example.shliapp.utils.AppRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.network.BaseNetworking.services;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.MyViewHolder> {
    private Context context;
    private List<SelectedItem> modelList;
    private List<SelectedItem> selectedItemList;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    ArrayList<AllAdsDataModel> allAdsDataModels = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.N)
    public ShoppingItemAdapter(Context context, List<SelectedItem> modelList) {
        this.context = context;
//        this.listItems = modelList;
        this.modelList = modelList;
        getAllAds();
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
        SelectedItem item = modelList.get(position);
        myViewHolder.tvDownItemName.setText(item.getItemTitle());
        myViewHolder.tvRowQuantity.setText(item.getQuantity());
        viewBinderHelper.bind(myViewHolder.swipeRevealLayout, item.getItemTitle());

        myViewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < allAdsDataModels.size(); i++) {

                    Log.d("zma", allAdsDataModels.get(i).getCategory() + "   " + item.getCategoryId());
                    if (item.getCategoryId().equals(allAdsDataModels.get(i).getCategory())) {

                        ShoppingFragment.cvAds.setVisibility(View.VISIBLE);
                        Glide.with(context).load(allAdsDataModels.get(i).getImage()).into(ShoppingFragment.ivAds);
//                        ShoppingFragment.tvAdsName.setText(allAdsDataModels.get(i).getAdsName());
//                        ShoppingFragment.tvCategoryName.setText(allAdsDataModels.get(i).getCategoryName());

                    }
                }

                deleteItem(item.getId(), position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView tvDownItemName, tvRowQuantity;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView tvDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDownItemName = itemView.findViewById(R.id.tvDownItemName);
            tvRowQuantity = itemView.findViewById(R.id.tvRowQuantity);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_layout_1);
            tvDelete = itemView.findViewById(R.id.tvDelete);


        }
    }


    private void deleteItem(String id, int indexPosition) {
        Call<DeleteNewShoppingResponse> call = services.deleteNewShoppingItem(AppRepository.mUserID(context), id);
        call.enqueue(new Callback<DeleteNewShoppingResponse>() {
            @Override
            public void onResponse(Call<DeleteNewShoppingResponse> call, Response<DeleteNewShoppingResponse> response) {

                Log.d("zma", String.valueOf(response));

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Item Delete", Toast.LENGTH_SHORT).show();
                    modelList.remove(indexPosition);
                    notifyDataSetChanged();
//                    Intent intent = new Intent(context, UnderSinkActivity.class);
//                    context.startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<DeleteNewShoppingResponse> call, Throwable t) {

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllAds() {
        allAdsDataModels.clear();
        Call<AllAdsResponse> allAdsResponseCall = BaseNetworking.ApiInterface().getAllAds();
        allAdsResponseCall.enqueue(new Callback<AllAdsResponse>() {
            @Override
            public void onResponse(Call<AllAdsResponse> call, Response<AllAdsResponse> response) {
                if (response.isSuccessful()) {
                    allAdsDataModels.addAll(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<AllAdsResponse> call, Throwable t) {

            }
        });

    }


}
