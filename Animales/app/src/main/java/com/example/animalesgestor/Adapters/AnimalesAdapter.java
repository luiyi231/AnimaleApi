package com.example.animalesgestor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalesgestor.R;
import com.example.animalesgestor.models.Animal;
import com.example.animalesgestor.models.Ave;
import com.example.animalesgestor.models.AveRapaz;
import com.example.animalesgestor.models.Mamifero;

import java.util.List;

public class AnimalesAdapter extends RecyclerView.Adapter<AnimalesAdapter.AnimalesViewHolder> {

    private List<Animal> animalesList;
    private Context context;
    private OnAnimalesClickListener listener;

    public interface OnAnimalesClickListener {
        void onAnimalClick(Animal animal, int position);
        void onEditClick(Animal animal, int position);
        void onDeleteClick(Animal animal, int position);
    }

    public AnimalesAdapter(List<Animal> animalesList, Context context, OnAnimalesClickListener listener) {
        this.animalesList = animalesList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnimalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        return new AnimalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalesViewHolder holder, int position) {
        Animal animal = animalesList.get(position);

        // Configurar imagen según tipo de animal
        int iconResId;
        String tipoAnimal;

        if (animal instanceof AveRapaz) {
            iconResId = R.drawable.ave_rapaz; // Asegúrate de tener estos drawables
            tipoAnimal = "Ave Rapaz";
        } else if (animal instanceof Ave) {
            iconResId = R.drawable.pajaro;
            tipoAnimal = "Ave";
        } else if (animal instanceof Mamifero) {
            iconResId = R.drawable.mamiferos;
            tipoAnimal = "Mamífero";
        } else {
            iconResId = R.drawable.ni_idea;
            tipoAnimal = "Animal";
        }

        holder.ivAnimal.setImageResource(iconResId);
        holder.ivAnimal.setContentDescription("Ícono de " + tipoAnimal);

        // Configurar textos comunes a todos los animales
        holder.tvAnimalId.setText("#" + (position + 1)); // O usa un ID real si lo tienes
        holder.tvAnimalNombre.setText(animal.especie);
        holder.tvHabitad.setText("Hábitat: " + animal.habitad);
        holder.tvTipoAnimal.setText(tipoAnimal);
        holder.tvEsperanzaVida.setText("Esperanza vida: " + animal.getEsperanzaVida());
        holder.tvPesoPromedio.setText(animal.pesoPromedio + " KG");

        // Configurar color del chip según tipo
        int backgroundResId;
        if (animal instanceof AveRapaz) {
            backgroundResId = R.drawable.bg_type_ave_rapaz;
        } else if (animal instanceof Ave) {
            backgroundResId = R.drawable.bg_type_ave;
        } else if (animal instanceof Mamifero) {
            backgroundResId = R.drawable.bg_type_mamifero;
        } else {
            backgroundResId = R.drawable.bg_animal_type_default;
        }
        holder.tvTipoAnimal.setBackgroundResource(backgroundResId);

        // Configurar listeners para los botones
        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(animal, position);
            }
        });

        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(animal, position);
            }
        });

        // Listener para el item completo
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAnimalClick(animal, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalesList.size();
    }

    public void updateAnimalesList(List<Animal> newAnimalesList) {
        this.animalesList = newAnimalesList;
        notifyDataSetChanged();
    }

    public static class AnimalesViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAnimal;
        TextView tvAnimalId, tvAnimalNombre, tvHabitad, tvTipoAnimal, tvPesoPromedio, tvEsperanzaVida;
        ImageButton btnEditar, btnEliminar;

        public AnimalesViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAnimal = itemView.findViewById(R.id.ivAnimal);
            tvAnimalId = itemView.findViewById(R.id.tvAnimalId);
            tvAnimalNombre = itemView.findViewById(R.id.tvAnimalNombre);
            tvHabitad = itemView.findViewById(R.id.tvHabitad);
            tvEsperanzaVida = itemView.findViewById(R.id.tvEsperanzaVida);
            tvTipoAnimal = itemView.findViewById(R.id.tvTipoAnimal);
            tvPesoPromedio = itemView.findViewById(R.id.tvPesoPromedio);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}