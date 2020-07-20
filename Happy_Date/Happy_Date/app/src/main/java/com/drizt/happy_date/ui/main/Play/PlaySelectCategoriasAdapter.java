package com.drizt.happy_date.ui.main.Play;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.drizt.happy_date.Clases.Categorie;
import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class PlaySelectCategoriasAdapter extends RecyclerView.Adapter<PlaySelectCategoriasAdapter.ViewHolderPlay>{

    ArrayList<Categorie> list_categorias;
    private onCheckChange onCheckChange;
    private SparseBooleanArray mSelectedItemsIds;
    ArrayList<Integer> positions;

    public interface onCheckChange{
        void onCheckedChange(boolean check);
    }

    public PlaySelectCategoriasAdapter(ArrayList<Categorie> list_categorias, onCheckChange onCheckChange, ArrayList<Integer> positions) {
        this.list_categorias = list_categorias;
        this.onCheckChange = onCheckChange;
        mSelectedItemsIds = new SparseBooleanArray();
        this.positions = positions;
    }

    @Override
    public ViewHolderPlay onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_categorias_adapter, parent, false);
        return  new ViewHolderPlay(itemview);
    }

    @Override
    public void onBindViewHolder(final ViewHolderPlay holder, final int position) {
        Categorie categorie = list_categorias.get(position);
        holder.checkbox.setText(categorie.getName());
        holder.checkbox.setOnCheckedChangeListener(null);
        //holder.checkbox.setChecked(is_Checked);
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (list_categorias.get(position).getChallenges().size()>0) {
                    if (isChecked) {
                        positions.add(position);
                    } else {
                        positions.remove(position);
                    }
                }else{
                    Toast.makeText(holder.itemView.getContext(), "Categoria Sin Retos", Toast.LENGTH_LONG).show();
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
