package com.vlg.testick.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vlg.testick.R;
import com.vlg.testick.model.Variant;

import java.util.List;

public class AdapterVariant extends RecyclerView.Adapter<AdapterVariant.VariantsViewHolder> {

    private List<Variant> variants;

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VariantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VariantsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VariantsViewHolder holder, int position) {
        holder.bind(variants.get(position));
    }

    @Override
    public int getItemCount() {
        return variants.size();
    }

    public static class VariantsViewHolder extends RecyclerView.ViewHolder {

        private final Button item;

        public VariantsViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
        }

        public void bind(Variant variant) {
            String text = variant.toString();
            item.setText(text);
        }
    }

}
