package com.abt.recycler.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.abt.recycler.R;
import java.util.List;

/**
 * @描述：     @LiveAdapter
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-31
 */
public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {

    private RecyclerView parentRecycler;
    private List<Live> data;

    public LiveAdapter(List<Live> data) {
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_platform, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Live forecast = data.get(position%data.size());
        holder.imageView.setImageResource(forecast.getPlatformIcon());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.platform_image);
            itemView.findViewById(R.id.platform_image).setOnClickListener(this);
        }

        public void showText() {
            int parentHeight = ((View) imageView.getParent()).getHeight();
            float scale = (parentHeight) / (float) imageView.getHeight();
            imageView.setPivotX(imageView.getWidth() * 0.5f);
            imageView.setPivotY(0);
            imageView.animate().scaleX(scale)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setAlpha(1.0f);
                        }
                    })
                    .scaleY(scale).setDuration(10)
                    .start();
        }

        public void hideText() {
            imageView.setAlpha(0.5f);
            imageView.animate().scaleX(1f).scaleY(1f)
                    .setDuration(10)
                    .start();
        }

        @Override
        public void onClick(View v) {
            parentRecycler.smoothScrollToPosition(getAdapterPosition());
        }
    }

}
