package com.example.shliapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shliapp.activities.ChooseStoreActivity;
import com.example.shliapp.activities.GeneralUtills;
import com.example.shliapp.adapter.ShoppingAdapter;
import com.example.shliapp.dataModels.getUserSelctedItem.UserSelctedResponse;
import com.example.shliapp.dataModels.getUserSelctedItem.UserSelectedDataModel;
import com.example.shliapp.dataModels.locationModels.LocationNearStoreResponse;
import com.example.shliapp.dataModels.locationModels.Store;
import com.example.shliapp.network.ApiClienTh;
import com.example.shliapp.network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;
import com.example.shliapp.utils.CheckLocation;

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
    @BindView(R.id.rvShoppingList)
    RecyclerView rvShoppingList;
    ApiInterface services;
    ProgressDialog progressDialog;

    @BindView(R.id.cvItems)
    CardView cvItems;
    @BindView(R.id.tvItemNotAvailable)
    TextView tvItemNotAvailable;

    List<UserSelectedDataModel> userSectedDataModels = new ArrayList<>();

    public static ImageView ivAds;
    public static CardView cvAds;

    private int storeID;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        ivAds=view.findViewById(R.id.ivAds);
       cvAds=view.findViewById(R.id.cvAds);

        cvFindFood.setOnClickListener(this);

        rvShoppingList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvShoppingList.setHasFixedSize(true);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();

        if (CheckLocation.isLocationEnabled(getActivity())) {
            if (AppRepository.mLat(getActivity()) != null) {
                if (AppRepository.mLng(getActivity()) != null) {
                    addLocation();
                }
            }
        } else {
            Toast.makeText(getActivity(), "Turn on your location", Toast.LENGTH_SHORT).show();

        }
        getItem();
//        ApiCallShoppingList();


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addLocation() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<LocationNearStoreResponse> addLocation = services.AddLocation(AppRepository.mUserID(getActivity()), AppRepository.mLat(getActivity()), AppRepository.mLng(getActivity()));
        addLocation.enqueue(new Callback<LocationNearStoreResponse>() {
            @Override
            public void onResponse(Call<LocationNearStoreResponse> call, Response<LocationNearStoreResponse> response) {

                if (response.isSuccessful()) {

                    ArrayList<Store> storeArrayList = new ArrayList<>();
                    storeArrayList.addAll(response.body().getStores());
                    if (storeArrayList.size() == 0) {
                        Toast.makeText(getActivity(), "No store available in this area", Toast.LENGTH_SHORT).show();
                    } else {
                        tvFindFood.setText(response.body().getStores().get(0).getStoreName());
                        storeID = response.body().getStores().get(0).getStoreID();
                        if (GeneralUtills.getSharedPreferences(getActivity()).getString("itemTitle", "").equals("")) {
                            tvFindFood.setText(response.body().getStores().get(0).getStoreName());
                        } else {
                            tvFindFood.setText(GeneralUtills.getSharedPreferences(getActivity()).getString("itemTitle", ""));
                        }

                    }

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LocationNearStoreResponse> call, Throwable t) {
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

        progressDialog.show();
        userSectedDataModels.clear();
        services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<UserSelctedResponse> UserSelctedResponseCall = services.getUserSelectedItems(AppRepository.mUserID(getActivity()), GeneralUtills.getSharedPreferences(getActivity()).getString("store_id", "13"));
        UserSelctedResponseCall.enqueue(new Callback<UserSelctedResponse>() {
            @Override
            public void onResponse(Call<UserSelctedResponse> call, Response<UserSelctedResponse> response) {

                Log.d("zma response", String.valueOf(response.body().getData()));


                if (response.isSuccessful()) {
                    userSectedDataModels.addAll(response.body().getData());
                    ShoppingAdapter adapter = new ShoppingAdapter(getActivity(), userSectedDataModels);
                    rvShoppingList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();


                    if (userSectedDataModels.size()==0) {
                        cvItems.setVisibility(View.GONE);
                        tvItemNotAvailable.setVisibility(View.VISIBLE);
                    }else {
                        cvItems.setVisibility(View.VISIBLE);
                        tvItemNotAvailable.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<UserSelctedResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }

}


//        services = ApiClienTh.getApiClient().create(ApiInterface.class);
//        Call<GetShoppingListResponse> call = services.getShoppingList();
//
//        call.enqueue(new Callback<GetShoppingListResponse>() {
//            @Override
//            public void onResponse(Call<GetShoppingListResponse> call, Response<GetShoppingListResponse> response) {
//                if (response.isSuccessful()) {
//                    ArrayList<ListItem> arrayList = new ArrayList<>();
//                    for (int j = 0; j < response.body().getData().size(); j++) {
//                        Header header = new Header();
//                        header.setHeader(response.body().getData().get(j).getRowName());
//                        header.setId(response.body().getData().get(j).getId());
//                        arrayList.add(header);
//
//
//                    }
//
//
////                    datumList.addAll(response.body().getData());
////
////                    for (int i = 0; i < response.body().getData().size(); i++) {
////                        itemList.addAll(response.body().getData().get(i).getItems());
////                    }
//                    ShoppingListAdapter adapter = new ShoppingListAdapter(getActivity(), arrayList);
//                    rvShoppingList.setAdapter(adapter);
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetShoppingListResponse> call, Throwable t) {
//                progressDialog.dismiss();
//            }
//        });

//    }
//
//    private void ApiCallShoppingList() {
//        Call<ShoppingRackResponse> rackResponseCall = BaseNetworking.services.rack(Integer.parseInt(AppRepository.mUserID(getActivity())), 1);
//        rackResponseCall.enqueue(new Callback<ShoppingRackResponse>() {
//            @Override
//            public void onResponse(Call<ShoppingRackResponse> call, Response<ShoppingRackResponse> response) {
//                if (response.isSuccessful()) {
//                    ArrayList<ListItem> arrayList = new ArrayList<>();
//                    for (int j = 0; j < response.body().getData().size(); j++) {
//                        Header header = new Header();
//                        header.setHeader(response.body().getData().get(j).getRowNumber());
//                        header.setId(response.body().getData().get(j).getId());
//                        arrayList.add(header);
//                        for (int i = 0; i < response.body().getData().get(j).getItems().size(); i++) {
//                            ContentItem item = new ContentItem();
//                            item.setQuatity(response.body().getData().get(j).getItems().get(i).getQuantity());
//                            item.setName(response.body().getData().get(j).getItems().get(i).getItemTitle());
//                            arrayList.add(item);
//                        }
//
//                    }
//                    ShoppingListAdapter adapter = new ShoppingListAdapter(getActivity(), arrayList);
//                    rvShoppingList.setAdapter(adapter);
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ShoppingRackResponse> call, Throwable t) {
//
//            }
//        });
