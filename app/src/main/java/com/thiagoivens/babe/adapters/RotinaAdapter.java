package com.thiagoivens.babe.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thiagoivens.babe.R;
import com.thiagoivens.babe.dominio.entidades.Rotina;

import java.util.List;

public class RotinaAdapter extends RecyclerView.Adapter<RotinaAdapter.ViewHolderRotina> {
    private List<Rotina> rotinaDados;

    public RotinaAdapter(List<Rotina> rotinaDados){
        this.rotinaDados = rotinaDados;
    }

    @NonNull
    @Override
    public RotinaAdapter.ViewHolderRotina onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_rotina, parent, false);
        ViewHolderRotina viewHolderRotina = new ViewHolderRotina(view);
        return viewHolderRotina;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRotina holder, int position) {
        if((rotinaDados != null)&&(rotinaDados.size() > 0)) {
            Rotina rotina = rotinaDados.get(position);
            holder.tipo.setText(rotina.tipo);
            holder.hora.setText(rotina.hora);
            holder.dia.setText(rotina.dia);
            if (rotina.tipo.contentEquals("Dormiu"))
                holder.imageView.setImageResource(R.drawable.bebedormindo);
            if (rotina.tipo.contentEquals("Acordou"))
                holder.imageView.setImageResource(R.drawable.acordando);
            if (rotina.tipo.contentEquals("Trocou"))
                holder.imageView.setImageResource(R.drawable.trocando);
            if (rotina.tipo.contentEquals("Mamou"))
                holder.imageView.setImageResource(R.drawable.mamando);
        }
    }

    @Override
    public int getItemCount() {
        return rotinaDados.size();
    }

    public class ViewHolderRotina extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView tipo;
        public TextView hora;
        public TextView dia;

        public ViewHolderRotina(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgview_id);
            tipo = itemView.findViewById(R.id.tv_tipo_id);
            hora = itemView.findViewById(R.id.tv_time_id);
            dia = itemView.findViewById(R.id.tv_dia_id);
        }
    }
}