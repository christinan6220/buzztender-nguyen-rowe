package com.BuzzTenderRoweNguyen.BuzzTender;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CompletedGameRecyclerAdapter extends FirestoreRecyclerAdapter<CompletedGame, CompletedGameRecyclerAdapter.CompletedGameViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final OnItemClickListener listener;

    CompletedGameRecyclerAdapter(FirestoreRecyclerOptions<CompletedGame> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }


    CompletedGameRecyclerAdapter(FirestoreRecyclerOptions<CompletedGame> options) {
        super(options);
        this.listener = null;
    }

    class CompletedGameViewHolder extends RecyclerView.ViewHolder {
        final CardView view;
        final TextView gameName;
        final TextView nickname;
        final TextView userBAC;
        final TextView userResult;

        CompletedGameViewHolder(CardView v) {
            super(v);
            view = v;
            gameName = v.findViewById(R.id.cg_game_name);
            nickname = v.findViewById(R.id.cg_user_nickname);
            userBAC = v.findViewById(R.id.cg_user_avg_bac);
            userResult = v.findViewById(R.id.cg_user_result);
        }

    }

    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(CompletedGameViewHolder holder, int position, final CompletedGame cg){
        holder.gameName.setText(String.valueOf(cg.getGame()));
        holder.nickname.setText(String.valueOf(cg.getNickname()));
        holder.userBAC.setText(String.valueOf(cg.getBac()));
        holder.userResult.setText(String.valueOf(cg.getResult()));
        if (listener != null) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.getAbsoluteAdapterPosition());
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CompletedGameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completed_game_card,parent,false);
        return new CompletedGameViewHolder(v);
    }

}
