package com.westerosatwar.got;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.westerosatwar.got.Model.King;

/**
 * Created by Arjun.
 */

public class KingDetailsAdapter extends RecyclerView.Adapter<KingDetailsAdapter.ViewHolder> {

    private final King king;
    String[] types;

    public KingDetailsAdapter(Context context, King king) {
        types = context.getResources().getStringArray(R.array.king_details);
        this.king = king;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_king_details, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.type.setText(types[position]);
        if (position == 0) {
            holder.value.setText(String.valueOf(king.getHigh_rating()));

        } else if (position == 1) {
            holder.value.setText(String.valueOf(king.getBattlesWon()));

        } else if (position == 2) {
            holder.value.setText(String.valueOf(king.getBattlesLost()));

        } else if (position == 3) {
            holder.value.setText(king.getStrength());

        } else {
            holder.value.setText(king.getBattleType());
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView type;
        TextView value;

        public ViewHolder(View itemView) {
            super(itemView);

            type = (TextView) itemView.findViewById(R.id.type);
            value = (TextView) itemView.findViewById(R.id.value);
        }
    }
}
