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
import com.example.shliapp.Models.DatumUnderSink;
import com.example.shliapp.R;

import java.util.List;

public class UnderSinkAdapterItem extends RecyclerView.Adapter<UnderSinkAdapterItem.MyviewHolder>  {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private Context context;
    private List<DatumUnderSink> modelListP ;
    private List<DatumUnderSink> listItemsP;
    private  Context mContext;

    public UnderSinkAdapterItem(Context context, List<DatumUnderSink> modelListP) {
        this.context = context;
        this.listItemsP = modelListP;
        this.modelListP = modelListP;
    }



    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }


    @NonNull
    @Override
    public UnderSinkAdapterItem.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview, parent , false) ;
        return new UnderSinkAdapterItem.MyviewHolder(view);    }


    @Override
    public void onBindViewHolder(@NonNull UnderSinkAdapterItem.MyviewHolder myViewHolder, int position) {

        final DatumUnderSink item =  modelListP.get(position);

        myViewHolder.tvItemName.setText(item.getItemId());
        myViewHolder.tvQuantity.setText(item.getQuantity());
        viewBinderHelper.bind(myViewHolder.swipeRevealLayout, item.getItemId());

        myViewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "test delete layoutAdapter", Toast.LENGTH_SHORT).show();

                
            }
        });

    }


    @Override
    public int getItemCount() {
        return modelListP.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeRevealLayout;
        private TextView tvQuantity;
        private TextView tvItemName;
        private TextView tvStrtId;
        private TextView tvDelete;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tvStrtId = (TextView) itemView.findViewById(R.id.startId);
            tvItemName =itemView.findViewById(R.id.tvItems);
            tvQuantity=itemView.findViewById(R.id.tvQuantity);
            swipeRevealLayout=itemView.findViewById(R.id.swipe_layout_1);
            tvDelete=itemView.findViewById(R.id.tvDelete);

        }
    }




}
