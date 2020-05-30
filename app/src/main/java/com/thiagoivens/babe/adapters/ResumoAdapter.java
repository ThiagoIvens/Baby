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

public class ResumoAdapter extends RecyclerView.Adapter<ResumoAdapter.ViewHolderResumo> {
    private List<Rotina> resumoDados;

    public ResumoAdapter(List<Rotina> resumoDados){
        this.resumoDados = resumoDados;
    }

    @NonNull
    @Override
    public ViewHolderResumo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_rotina, parent, false);
        ResumoAdapter.ViewHolderResumo viewHolderResumo = new ResumoAdapter.ViewHolderResumo(view);
        return viewHolderResumo;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderResumo holder, int position) {
        if((resumoDados != null)&&(resumoDados.size() > 0)) {
            Rotina rotina = resumoDados.get(position);
            holder.tipo.setText(rotina.tipo);
            holder.dia.setText(rotina.dia);
            holder.imageView.setImageResource(R.drawable.bebedormindo);

            Rotina rotina1 = resumoDados.get(position+1);
            int resumo = (24 - ( Integer.valueOf(rotina.hora) - Integer.valueOf(rotina1.hora) ) );
            holder.conta.setText("Dormiu por "+ resumo +" horas!");
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderResumo extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView dia;
        public TextView conta; // mostra a hora que o bebe passou dormindo
        public TextView tipo;


        public ViewHolderResumo(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgview_id);
            dia     =  itemView.findViewById(R.id.resumo_dia_id);
            conta    =  itemView.findViewById(R.id.resumo_hora_id);
            tipo    =  itemView.findViewById(R.id.resumo_tipo_id);
        }
    }
}
