package com.westerosatwar.got;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.westerosatwar.got.Model.Battle;
import com.westerosatwar.got.Model.King;
import com.westerosatwar.got.Network.NetworkClient;
import com.westerosatwar.got.Network.NetworkStatus;
import com.westerosatwar.got.Network.ResponseCallback;
import com.westerosatwar.got.database.Constants;
import com.westerosatwar.got.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements Constants, SwipeRefreshLayout.OnRefreshListener {

    ActivityMainBinding activityMainBinding;
    List<Battle> battleLists = new ArrayList<>();

    KingsAdapter adapter;
    private ArrayList<King> kings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        activityMainBinding.recyclerView.setLayoutManager(manager);

        adapter = new KingsAdapter(this);
        activityMainBinding.recyclerView.setAdapter(adapter);

        setListeners();

        if (dbHelper.getKings() == null || dbHelper.getKings().size() == 0) {
            getBattles();
        } else {
            setAdapter();
        }
    }

    private void setListeners() {
        activityMainBinding.searchEditText.addTextChangedListener(searchEditTextWatcher);
        activityMainBinding.searchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMainBinding.searchEditText.setText("");
                activityMainBinding.searchCancel.setVisibility(View.GONE);
            }
        });

        activityMainBinding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialogue();
            }
        });
        activityMainBinding.refreshlayout.setOnRefreshListener(this);
    }

    private void setAdapter() {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               kings = dbHelper.getKings();
               if (kings != null) {
                   adapter.setKings(kings);
                   activityMainBinding.totalKings.setText(getString(R.string.total_kings_text,
                           String.valueOf(kings.size())));
               }
           }
       });
    }

    private void getBattles() {
        if (!NetworkStatus.isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.no_network), Toast.LENGTH_LONG).show();

        } else {
            showProgressDialog();
            new NetworkClient(this).getData(new ResponseCallback() {

                @Override
                public void onResponse(List<Battle> battleList) {
                    hideProgressDialog();

                    battleLists= battleList;

                    insertKings();
                }
            });
        }
    }

    public void insertKings() {
        if (battleLists != null) {
            ArrayList<String> kingNames = new ArrayList<>();
            for (Battle battle : battleLists) {
                if (!kingNames.contains(battle.getAttackerKing())) {
                    kingNames.add(battle.getAttackerKing());
                }

                if (!kingNames.contains(battle.getDefenderKing())) {
                    kingNames.add(battle.getDefenderKing());
                }
            }

            getKingsInfo(kingNames);
        }
    }

    private void getKingsInfo(ArrayList<String> kingNames) {
        if (kingNames != null && battleLists != null) {
            ArrayList<King> kings = new ArrayList<>();

            for (String name : kingNames) {
                int won = 0;
                int lost = 0;
                int ambush = 0;
                int siege = 0;
                int pitchedBattle = 0;
                int attacker = 0;
                int defender = 0;
                for (Battle battle : battleLists) {

                    if (name.equals(battle.getAttackerKing()) || name.equals(battle.getDefenderKing())) {
                        if (name.equals(battle.getDefenderKing())) {
                            attacker++;
                            if (battle.getAttackerOutcome().equals(LOSS)) {
                                won++;
                            } else {
                                lost++;
                            }
                        } else {
                            defender++;
                            if (battle.getAttackerOutcome().equals(WIN)) {
                                won++;
                            } else {
                                lost++;
                            }
                        }

                        if (battle.getBattleType().equals(AMBUSH)) {
                            ambush++;
                        } else if (battle.getBattleType().equals(SIEGE)) {
                            siege++;
                        } else {
                            pitchedBattle++;
                        }

                    }

                }

                King king = new King();
                king.setName(name);
                king.setBattlesWon(won);
                king.setBattlesLost(lost);
                king.setStrength(attacker > defender
                        ? getString(R.string.attack) : getString(R.string.defend));
                if (ambush > siege) {
                    king.setBattleType(ambush > pitchedBattle ? AMBUSH : PITCHED_BATTLE);
                } else {
                    king.setBattleType(siege > pitchedBattle ? SIEGE : PITCHED_BATTLE);
                }
                kings.add(king);
            }

            calculateRatings(kings);
        }
    }

    private void calculateRatings(ArrayList<King> kings) {
        if (kings != null && battleLists != null) {
            for (Battle battle : battleLists) {
                int k = 32;
                double n = 400.0;
                King attackedKing = getKingFromList(battle.getAttackerKing(), kings);
                King defenderKing = getKingFromList(battle.getDefenderKing(), kings);

                double r1 = Math.pow(10, attackedKing.getRating()/n);
                double r2 = Math.pow(10, defenderKing.getRating()/n);

                double e1 = r1 / (r1 + r2);
                double e2 = r2 / (r1 + r2);

                double s1 = battle.getAttackerOutcome().equals(WIN) ? 1 : 0;
                double s2 = battle.getAttackerOutcome().equals(LOSS) ? 1 : 0;

                int attackerRate = (int) (attackedKing.getRating() + (k * (s1 - e1)));
                int defenderRate = (int) (defenderKing.getRating() + (k * (s2 - e2)));

                attackedKing.setHigh_rating(attackedKing.getHigh_rating() > attackerRate
                        ? attackedKing.getHigh_rating() : attackerRate);

                defenderKing.setHigh_rating(defenderKing.getHigh_rating() > defenderRate
                        ? defenderKing.getHigh_rating() : defenderRate);

                attackedKing.setRating(attackerRate);
                defenderKing.setRating(defenderRate);
            }
        }

        insertKingsIntoDb(kings);
    }

    private void insertKingsIntoDb(ArrayList<King> kings) {
        if (kings != null) {
            for (King king : kings) {
                dbHelper.insertKing(king);
            }
        }

        setAdapter();
    }

    private King getKingFromList(String name, ArrayList<King> kings) {
        King selectedKing = new King();
        for (King king : kings) {
            if (king.getName().equals(name)) {
                return king;
            }
        }
        return selectedKing;
    }


    @Override
    public void onRefresh() {
        dbHelper.deleteKingsTable();
        activityMainBinding.refreshlayout.setRefreshing(false);
        getBattles();
    }

    TextWatcher searchEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length() > 0){
                activityMainBinding.searchCancel.setVisibility(View.VISIBLE);
            }else{
                activityMainBinding.searchCancel.setVisibility(View.GONE);
            }
            searchKings(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void showFilterDialogue(){
        final Dialog dialog = new Dialog(MainActivity.this,R.style.CustomDialogAnimTheme);
        dialog.setContentView(R.layout.sort_dialogue);
        final TextView attack = (TextView) dialog.findViewById(R.id.attack);
        TextView defend = (TextView) dialog.findViewById(R.id.defend);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                activityMainBinding.searchEditText.setText(getString(R.string.attack));
            }
        });
        defend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                activityMainBinding.searchEditText.setText(getString(R.string.defend));
            }
        });
        dialog.show();
    }

    public void searchKings(String text) {
        ArrayList<King> searchedKings = new ArrayList<>();
        if (kings != null) {
            for (int i = 0; i < kings.size(); i++) {
                King king = kings.get(i);
                if (king.getName().toLowerCase().contains(text.toLowerCase())
                        || king.getStrength().toLowerCase().contains(text.toLowerCase())) {
                    searchedKings.add(king);
                }
            }
            activityMainBinding.totalKings.setText(getString(R.string.total_kings_text,
                    String.valueOf(searchedKings.size())));

            adapter.setKings(searchedKings);

            if (searchedKings.size() == 0) {
                Toast.makeText(this, "no events found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "no events found", Toast.LENGTH_SHORT).show();
        }

    }

}
