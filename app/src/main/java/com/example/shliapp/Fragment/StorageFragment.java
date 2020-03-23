package com.example.shliapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shliapp.Activities.AddStorageActivity;
import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Activities.LoginActivity;
import com.example.shliapp.Activities.UnderSinkActivity;
import com.example.shliapp.Adapter.StorageAdapter;


import com.example.shliapp.Models.StorageModelss.DatumStorage;
import com.example.shliapp.Models.GetStorageModel;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class StorageFragment extends Fragment implements View.OnClickListener {

    View view;
    @BindView(R.id.ivPlusIconStorage)
    ImageView ivPlusIcon;
    @BindView(R.id.rvStorage)
    RecyclerView rvShoppingList;

    private ArrayList<DatumStorage> itemLists;
    ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_storage, container, false);
   initListeners();
        initUI();
        return view;
    }

    private void initListeners() {
        ButterKnife.bind(this, view);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI(){
        progressDialog  = new ProgressDialog(getContext());
        ivPlusIcon.setOnClickListener(this);
        rvShoppingList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvShoppingList.setHasFixedSize(true);
        itemLists = new ArrayList<DatumStorage>() ;

        getStorage();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getStorage() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);


        Call<GetStorageModel> call = services.getStorage(GeneralUtills.getSharedPreferences(getContext()).getString("userId" , ""));

        call.enqueue(new Callback<GetStorageModel>() {
            @Override
            public void onResponse(Call<GetStorageModel> call, Response<GetStorageModel> response) {
                if (response.isSuccessful()) {
                    itemLists.addAll(response.body().getData());

                    StorageAdapter adapter = new StorageAdapter(getContext(), itemLists);
                    rvShoppingList.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<GetStorageModel> call, Throwable t) {

            }
        });

    }



//    private ArrayList<StorageModel> ProductName(){
//
//        ArrayList<StorageModel> list = new ArrayList<>();
//
//        for(int i = 0; i < 4; i++){
//            StorageModel fruitModel = new StorageModel();
//            fruitModel.setStStorageName(myStoreNameListC[i]);
//            fruitModel.setStStorageItems(myKGListC[i]);
//            fruitModel.setImageStorage(myimageC[i]);
//            list.add(fruitModel);
//        }
//
//        return list;
//    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivPlusIconStorage:
                Intent intent = new Intent(getContext(), AddStorageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
