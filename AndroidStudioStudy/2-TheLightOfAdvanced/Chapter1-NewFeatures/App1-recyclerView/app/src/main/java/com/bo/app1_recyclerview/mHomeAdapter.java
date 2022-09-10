package com.bo.app1_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class mHomeAdapter extends RecyclerView.Adapter<mHomeAdapter.ViewHolder> {

    private List<String> mHomeList;
    private Context context;
    private OnItemClickListener itemClickListener;

    public mHomeAdapter(List<String> mHomeList, Context context) {
        this.mHomeList = mHomeList;
        this.context = context;
    }

    public void removeData(int pos) {
        mHomeList.remove(pos);
        notifyItemRemoved(pos);
    }

    /**
     * 创建一个ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflateView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull mHomeAdapter.ViewHolder holder, int position) {
        holder.tv.setText(mHomeList.get(position));

        ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
        params.height= (int)(100+Math.random()*300);
        holder.tv.setLayoutParams(params);

        //默认点击事件
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                String str = mHomeList.get(adapterPosition);
                Toast.makeText(context,String.format("%s",str),Toast.LENGTH_SHORT).show();
            }
        });*/

        //自定义监听
        if(this.itemClickListener!=null){
            //点击
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();//获取到点击的item的position
                    itemClickListener.onItemClick(v,adapterPosition);
                }
            });
            //长按
            holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    itemClickListener.onItemLongClick(v,adapterPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHomeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
        }
    }

    /**
     * 监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }


    //设置监听器
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
}
