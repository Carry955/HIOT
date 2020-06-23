package com.zkl.hiot.ui.main.equipment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zkl.hiot.R;
import com.zkl.hiot.entity.HolderDeviceEntity;
import com.zkl.hiot.http.HttpService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>
        implements View.OnClickListener {
    private final Context mContext;
    private final ArrayList<HolderDeviceEntity> mData;
    private OnItemClickListener onItemClickListener;

    public DeviceAdapter(Context context){
        this.mContext = context;
        this.mData = new ArrayList<HolderDeviceEntity>();
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_device_item, parent, false);
        view.setOnClickListener(this);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        holder.itemView.setTag(position);
        HolderDeviceEntity deviceEntity = mData.get(position);
        holder.tv_name.setText(deviceEntity.getTitle());
        holder.tv_desc.setText(deviceEntity.getDescription());
        Glide.with(mContext)
                .load(HttpService.IMAGE_BASE_URL+deviceEntity.getDeviceimg())
                .error(R.mipmap.little_image_error)
                .into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void resetDataAndNotifyDataSetChanged(List<HolderDeviceEntity> data){
        if (data != null && data.size() != 0){
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null){
            int pos = (int)v.getTag();
            HolderDeviceEntity deviceEntity = mData.get(pos);
            onItemClickListener.onItemClick(pos,deviceEntity);
        }
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image)
        ImageView iv_image;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_desc)
        TextView tv_desc;
        public DeviceViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(int position, HolderDeviceEntity item);
    }
}
