package com.example.bandify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bandify.R;
import com.example.bandify.models.Band;

import java.util.List;

public class BandsAdapter extends RecyclerView.Adapter<BandsAdapter.ViewHolder> {
    private List<Band> bands;
    private Context context;
    private Fragment fragment;

    public BandsAdapter(List<Band> bands, Context context, Fragment fragment) {
        this.bands = bands;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_band, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Band band = bands.get(position);
        holder.bind(band);
    }

    @Override
    public int getItemCount() {
        return bands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Band band) {

        }
    }
}
