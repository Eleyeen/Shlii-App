package com.example.shliapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shliapp.Activities.ChooseStoreActivity;
import com.example.shliapp.Adapter.ShoppingListAdapter;
import com.example.shliapp.Models.LocationModels.LocationNearStoreModels;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.ContentItem;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.Datum;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.GetShoppingListResponse;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.Header;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.Item;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.ListItem;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingFragment extends Fragment implements View.OnClickListener {

    View view;

    @BindView(R.id.cvFindFood)
    CardView cvFindFood;
    @BindView(R.id.tvFindFood)
    TextView tvFindFood;
//
//    @BindView(R.id.tvGateWay)
//    TextView tvGateWay;

    @BindView(R.id.rvShoppingList)
    RecyclerView rvShoppingList;
    ApiInterface services;
    ProgressDialog progressDialog;
    List<Item> itemList = new ArrayList<>();
    List<Datum> datumList = new ArrayList<>();
    List<List<ListItem>> arraylist;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shopping, container, false);
        initListeners();
        initUI();
        return view;
    }


    private void initListeners() {
        ButterKnife.bind(this, view);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        cvFindFood.setOnClickListener(this);

        rvShoppingList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvShoppingList.setHasFixedSize(true);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();

        addLocation();
        getItem();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addLocation() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<LocationNearStoreModels> addLocation = services.AddLocation(AppRepository.mUserID(getActivity()), AppRepository.mLat(getActivity()), AppRepository.mLng(getActivity()));
        addLocation.enqueue(new Callback<LocationNearStoreModels>() {
            @Override
            public void onResponse(Call<LocationNearStoreModels> call, Response<LocationNearStoreModels> response) {

                if (response.isSuccessful()) {
                    tvFindFood.setText(response.body().getStores().get(0).getStoreName());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LocationNearStoreModels> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvFindFood:
                Intent intent = new Intent(getContext(), ChooseStoreActivity.class);
                startActivity(intent);
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getItem() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<GetShoppingListResponse> call = services.getShoppingList();

        call.enqueue(new Callback<GetShoppingListResponse>() {
            @Override
            public void onResponse(Call<GetShoppingListResponse> call, Response<GetShoppingListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<ListItem> arrayList = new ArrayList<>();
                    for(int j = 0; j < response.body().getData().size(); j++) {
                        Header header = new Header();
                        header.setHeader(response.body().getData().get(j).getRowName());
                        arrayList.add(header);
                        for (int i = 0; i < response.body().getData().get(j).getItems().size(); i++) {
                            ContentItem item = new ContentItem();
                            item.setRollnumber(response.body().getData().get(j).getItems().get(i).getItemNumber());
                            item.setName(response.body().getData().get(j).getItems().get(i).getItemTitle());
                            arrayList.add(item);
                        }
                    }



//                    datumList.addAll(response.body().getData());
//
//                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        itemList.addAll(response.body().getData().get(i).getItems());
//                    }
                    ShoppingListAdapter adapter = new ShoppingListAdapter(getActivity(), arrayList);
                    rvShoppingList.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetShoppingListResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

}
