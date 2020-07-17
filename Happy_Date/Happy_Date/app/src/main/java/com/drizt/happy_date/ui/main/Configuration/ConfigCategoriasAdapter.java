package com.drizt.happy_date.ui.main.Configuration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drizt.happy_date.Clases.Categorie;
import com.drizt.happy_date.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ConfigCategoriasAdapter extends RecyclerView.Adapter<ConfigCategoriasAdapter.ViewHolderConfig> implements View.OnClickListener{

    ArrayList<Categorie> list_categorias;
    View.OnClickListener listener;

    public ConfigCategoriasAdapter(ArrayList<Categorie> list_categorias) {
        this.list_categorias = list_categorias;
    }

    @Override
    public ViewHolderConfig onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.config_categorias_adapter, parent, false);
        itemview.setOnClickListener(this);
        return  new ViewHolderConfig(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolderConfig holder, int position) {
        holder.fillData(list_categorias.get(position));
    }

    @Override
    public int getItemCount() {
        return list_categorias.size();
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

            name = itemView.findViewById(R.id.config_categoria_adapter_name);
        }

        public void fillData(Categorie categorie) {
            name.setText(categorie.getName());
        }
    }
}
