package com.example.shliapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shliapp.Activities.ChooseStoreActivity;
import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Adapter.ProductItemAdapter;
import com.example.shliapp.Adapter.ShopListAdapter;
import com.example.shliapp.Models.ItemRespones;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.GetShoppingList;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Activities.GeneralUtills.getSharedPreferences;

public class ShoppingFragment extends Fragment implements View.OnClickListener {

    View view;

    @BindView(R.id.cvFindFood)
    CardView cvFindFood;
    @BindView(R.id.tvFindFood)
    TextView tvFindFood;

    @BindView(R.id.tvGateWay)
    TextView tvGateWay;

    @BindView(R.id.rvShoppingList)
    RecyclerView rvShoppingList;
    ApiInterface services;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_shopping, container, false);
        initListeners();
        initUI();
        return view;
    }


    private void initListeners() {
        ButterKnife.bind(this, view);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI(){
        cvFindFood.setOnClickListener(this);

        rvShoppingList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvShoppingList.setHasFixedSize(true);



        tvFindFood.setText(GeneralUtills.getSharedPreferences(getContext()).getString("itemTitle" , ""));
        getItem();

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cvFindFood:
                Intent intent = new Intent(getContext(), ChooseStoreActivity.class);
                startActivity(intent);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getItem() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);
        String strUserID= getSharedPreferences(getContext()).getString("userId" , "");


        Call<GetShoppingList> call = services.getShoppingList(strUserID);

        call.enqueue(new Callback<GetShoppingList>() {
            @Override
            public void onResponse(Call<GetShoppingList> call, Response<GetShoppingList> response) {
                if (response.isSuccessful()) {
                    GetShoppingList jobDataModels = response.body();
                    ShopListAdapter adapter = new ShopListAdapter(getActivity(), jobDataModels.getData());
                    rvShoppingList.setAdapter(adapter);


                }

            }

            @Override
            public void onFailure(Call<GetShoppingList> call, Throwable t) {

            }
        });

    }

}
