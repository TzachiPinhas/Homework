package com.example.homework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.Interfaces.ScoreCallback;
import com.example.homework.Models.Score;
import com.example.homework.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private Context context;
    private ArrayList<Score> scores;
    private ScoreCallback scoreCallBack;

    public ScoreAdapter(Context context, ArrayList<Score> scores) {
        this.context = context;
        this.scores = scores;
    }

    public ScoreAdapter setScoreCallBack(ScoreCallback scoreCallBack) {
        this.scoreCallBack = scoreCallBack;
        return this;
    }

    @NonNull
    @Override
    public ScoreAdapter.ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horisontal_score_item, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ScoreViewHolder holder, int position) {
        Score score = getItem(position);
        holder.score_LBL_name.setText(score.getName());
        holder.score_LBL_score.setText(String.valueOf(score.getScore()));
        

    }

    @Override
    public int getItemCount() {
        if (scores == null)
            return 0;
        else if (scores.size() > 10)
            return 10;
        return scores.size();
    }

    private Score getItem(int position) {
        return scores.get(position);
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView score_LBL_name;
        private MaterialTextView score_LBL_score;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            score_LBL_name = itemView.findViewById(R.id.score_LBL_name);
            score_LBL_score = itemView.findViewById(R.id.score_LBL_score);
            score_LBL_name.setOnClickListener(v -> {
                        if (scoreCallBack != null) {
                            scoreCallBack.scoreClicked(getItem(getAdapterPosition()), getAdapterPosition());
                        }
                    }
            );
        }
    }
}
