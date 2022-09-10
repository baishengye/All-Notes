package com.bo.app2_matrialdesignui.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bo.app2_matrialdesignui.R;
import com.bo.app2_matrialdesignui.databinding.ListItemCardMainBinding;

import org.jetbrains.annotations.NotNull;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ListItemCardMainBinding listItemCardMainBinding = ListItemCardMainBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(listItemCardMainBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), holder.tv.getText(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;
        public View mView;
        public ImageView iv;

        public ViewHolder(ListItemCardMainBinding listItemCardMainBinding) {
            super(listItemCardMainBinding.getRoot());
            mView=listItemCardMainBinding.getRoot();
            iv=listItemCardMainBinding.iv;
            tv = listItemCardMainBinding.tv;
        }
    }
}
