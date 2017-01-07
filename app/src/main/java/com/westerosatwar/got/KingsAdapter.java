package com.westerosatwar.got;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.westerosatwar.got.Model.King;
import com.westerosatwar.got.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Arjun on 1/7/2017.
 */

public class KingsAdapter extends RecyclerView.Adapter<KingsAdapter.ViewHolder>{

    ListItemBinding mBinding;

    int[] images = new int[]{R.drawable.random_user_1, R.drawable.random_user_2,
            R.drawable.random_user_3, R.drawable.random_user_4, R.drawable.random_user_5};

    private ArrayList<King> kings = new ArrayList<>();
    public void setKings(ArrayList<King> kings) {
        this.kings = kings;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_item, parent, false);

        return new ViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        King king = kings.get(position);
        king.setImage(images[new Random().nextInt(5)]);

        holder.binding.setVariable(BR.king, king);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return kings != null ? kings.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewDataBinding binding;

         ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
