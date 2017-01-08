package com.westerosatwar.got;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.westerosatwar.got.Model.King;
import com.westerosatwar.got.database.Constants;
import com.westerosatwar.got.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Arjun.
 */

public class KingsAdapter extends RecyclerView.Adapter<KingsAdapter.ViewHolder>{

    ListItemBinding mBinding;

    Context context;

    int[] images = new int[]{R.drawable.random_user_1, R.drawable.random_user_2,
            R.drawable.random_user_3, R.drawable.random_user_4, R.drawable.random_user_5};

    private ArrayList<King> kings = new ArrayList<>();

    public KingsAdapter(Context context) {
        this.context = context;
    }

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
        final King king = kings.get(position);
        king.setImage(images[new Random().nextInt(5)]);

        holder.binding.setVariable(BR.king, king);
        holder.binding.executePendingBindings();

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, KingDetailsActivity.class);
                intent.putExtra(Constants.KING, king);
                context.startActivity(intent);
            }
        });
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
