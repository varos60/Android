package com.drizt.happy_date.ui.main.Play;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.drizt.happy_date.Clases.Categorie;
import com.drizt.happy_date.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class PlaySelectCategoriasAdapter extends RecyclerView.Adapter<PlaySelectCategoriasAdapter.ViewHolderPlay>{

    ArrayList<Categorie> list_categorias;
    private onCheckChange onCheckChange;
    private SparseBooleanArray mSelectedItemsIds;
    int [] positions;

    public interface onCheckChange{
        void onCheckedChange(boolean check);
    }

    public PlaySelectCategoriasAdapter(ArrayList<Categorie> list_categorias, onCheckChange onCheckChange) {
        this.list_categorias = list_categorias;
        this.onCheckChange = onCheckChange;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public ViewHolderPlay onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_categorias_adapter, parent, false);
        return  new ViewHolderPlay(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolderPlay holder, int position) {
        Categorie categorie = list_categorias.get(position);
        holder.checkbox.setText(categorie.getName());
        holder.checkbox.setOnCheckedChangeListener(null);
        //holder.checkbox.setChecked(is_Checked);
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                }
                onCheckChange.onCheckedChange(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_categorias.size();
    }

    public class ViewHolderPlay extends RecyclerView.ViewHolder {

        CheckBox checkbox;

        public ViewHolderPlay(View itemView) {
            super(itemView);

            checkbox = itemView.findViewById(R.id.categoria_checkbox);
        }
    }
}
