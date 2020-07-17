package com.drizt.happy_date.ui.main.Configuration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drizt.happy_date.Clases.Challenge;
import com.drizt.happy_date.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ConfigRetosAdapter extends RecyclerView.Adapter<ConfigRetosAdapter.ViewHolderConfig> implements View.OnClickListener{

    ArrayList<Challenge> list_challenges;
    View.OnClickListener listener;

    public ConfigRetosAdapter(ArrayList<Challenge> list_challenges) {
        this.list_challenges = list_challenges;
    }

    @Override
    public ViewHolderConfig onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.config_retos_adapter, parent, false);
        itemview.setOnClickListener(this);
        return  new ViewHolderConfig(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolderConfig holder, int position) {
        holder.fillData(list_challenges.get(position));
    }

    @Override
    public int getItemCount() {
        return list_challenges.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderConfig extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolderConfig(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.config_reto_adapter_name);
        }

        public void fillData(Challenge challenge) {
            name.setText(challenge.getName());
        }
    }
}
