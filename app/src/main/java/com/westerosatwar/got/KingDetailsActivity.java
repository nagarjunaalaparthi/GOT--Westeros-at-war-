package com.westerosatwar.got;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.westerosatwar.got.Model.King;
import com.westerosatwar.got.custom.VerticleSpacingItemDecoration;
import com.westerosatwar.got.database.Constants;
import com.westerosatwar.got.databinding.KingDetailsBinding;

/**
 * Created by Arjun.
 */

public class KingDetailsActivity extends BaseActivity {

    KingDetailsBinding detailsBinding;
    private King king;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsBinding = DataBindingUtil.setContentView(this, R.layout.king_details);

        king = (King) getIntent().getExtras().getSerializable(Constants.KING);

        setSupportActionBar(detailsBinding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setDataToViews();
        setAdapter();
    }

    private void setDataToViews() {
        detailsBinding.collapsingToolbar.setTitle(king.getName());
        detailsBinding.movieImageBanner.setImageResource(king.getImage());
    }

    private void setAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        detailsBinding.recyclerView.setLayoutManager(manager);
        VerticleSpacingItemDecoration decoration = new VerticleSpacingItemDecoration(8);
        detailsBinding.recyclerView.addItemDecoration(decoration);
        KingDetailsAdapter adapter = new KingDetailsAdapter(this, king);
        detailsBinding.recyclerView.setAdapter(adapter);
    }
}
