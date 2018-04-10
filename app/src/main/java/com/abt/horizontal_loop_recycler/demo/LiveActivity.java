package com.abt.horizontal_loop_recycler.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.abt.horizontal_loop_recycler.R;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

/**
 * @描述：     @LiveActivity
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-31
 */
public class LiveActivity extends AppCompatActivity implements
        DiscreteScrollView.ScrollStateChangeListener<LiveAdapter.ViewHolder>,
        DiscreteScrollView.OnItemChangedListener<LiveAdapter.ViewHolder> {

    private List<Live> platforms;
    private DiscreteScrollView platformPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        platforms = LivePlatform.get().getPlatforms();
        platformPicker = (DiscreteScrollView) findViewById(R.id.recycler_view);
        platformPicker.setSlideOnFling(true);
        platformPicker.setAdapter(new LiveAdapter(platforms));
        platformPicker.addOnItemChangedListener(this);
        platformPicker.addScrollStateChangeListener(this);
        platformPicker.scrollToPosition(Integer.MAX_VALUE/2-1);
        platformPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        platformPicker.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.615f).build());
    }

    @Override
    public void onCurrentItemChanged(@Nullable LiveAdapter.ViewHolder holder, int position) {
        if (holder != null) {
            holder.showText();
        }
    }

    @Override
    public void onScrollStart(@NonNull LiveAdapter.ViewHolder holder, int position) {
        holder.hideText();
    }

    @Override
    public void onScroll(
            float position,
            int currentIndex, int newIndex,
            @Nullable LiveAdapter.ViewHolder currentHolder,
            @Nullable LiveAdapter.ViewHolder newHolder) {
        Live current = platforms.get(currentIndex%platforms.size());
        if (newIndex >= 0 && newIndex < (platformPicker.getAdapter().getItemCount()%platforms.size())) {
            Live next = platforms.get(newIndex%platforms.size());
        }
    }

    @Override
    public void onScrollEnd(@NonNull LiveAdapter.ViewHolder holder, int position) {

    }
}
