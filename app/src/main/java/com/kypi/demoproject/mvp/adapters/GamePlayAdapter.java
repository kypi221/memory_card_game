package com.kypi.demoproject.mvp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kypi.demoproject.R;
import com.kypi.demoproject.domain.entities.MemoryCard;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GamePlayAdapter extends RecyclerView.Adapter {

    private List<MemoryCard> listItems;
    private View.OnClickListener onClickListener;

    public GamePlayAdapter(List<MemoryCard> listItems) {
        this.listItems = listItems;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Kiểu item
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list_card, parent, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        itemView.setOnClickListener(onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            MemoryCard item = listItems.get(position);

        }
    }

    @Override
    public int getItemCount() {
        if(listItems == null){
            listItems = new ArrayList<>();
        }
        return listItems.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_icon_card)
        ImageView imgIconCard;

        @BindView(R.id.flipView)
        EasyFlipView layoutFlipView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
