package com.abt.recycler.Live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abt.basic.arch.mvvm.BaseFragment;
import com.abt.basic.log.LogHelper;
import com.abt.recycler.Decoration.SpaceItemDecoration;
import com.abt.recycler.GlobalConstant;
import com.abt.recycler.R;
import com.abt.recycler.adapter.PlatformAdapter;
import com.abt.recycler.databinding.FragmentLiveBinding;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @描述：     @直播fragment
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-05
 */
public class LiveFragment extends BaseFragment<LiveViewModel> {

    private static final String TAG = "LiveFragment";

    private FragmentLiveBinding mLiveSettingBinding;
    private ArrayList<Integer> mList;
    private int mCurrentPosition = GlobalConstant.INIT_POSITION;

    /**
     * 返回实例
     */
    public static LiveFragment newInstance() {
        return new LiveFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLiveSettingBinding = FragmentLiveBinding.inflate(
                inflater, container, false);
        initialize();
        mLiveSettingBinding.setModel(mViewModel);
        mViewModel.initialize();
        return mLiveSettingBinding.getRoot();
    }

    private final void initialize() {
        mList = new ArrayList<>(Arrays.asList(R.drawable.platform_pi,
                R.drawable.platform_weibo, R.drawable.platform_facebook,
                R.drawable.platform_youtube, R.drawable.platform_rtmp));
        //需要设置RecyclerView的LayoutManager，不然视图无法显示
        //第二个参数表示水平布局，第三个参数表示是否反转, 视图从下往上滑
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.supportsPredictiveItemAnimations();
        mLiveSettingBinding.recyclerView.setLayoutManager(linearLayoutManager);//设置布局管理器
        int itemSpace = getResources().getDimensionPixelSize(R.dimen.recyclerView_item_space);
        mLiveSettingBinding.recyclerView.addItemDecoration(new SpaceItemDecoration(itemSpace));
        //初始化适配器
        PlatformAdapter recyclerViewAdapter = new PlatformAdapter(mList);
        //绑定适配器
        mLiveSettingBinding.setAdapter(recyclerViewAdapter);
        mLiveSettingBinding.recyclerView.scrollToPosition(10000);
        //item的点击事件
        recyclerViewAdapter.setOnItemClickListener(new PlatformAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mLiveSettingBinding.recyclerView.smoothToCenter(position);
            }
        });

        mLiveSettingBinding.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int childCount = mLiveSettingBinding.recyclerView.getChildCount();
                int width = mLiveSettingBinding.recyclerView.getChildAt(0).getWidth();
                int padding = (mLiveSettingBinding.recyclerView.getWidth() - width) / 2;

                for (int index = 0; index < childCount; index++) {
                    View view = recyclerView.getChildAt(index);

                   //往左 从 padding 到 recyclerView.getWidth()-padding 的过程中，由小到大
                    float rate = 0;
                    if (view.getLeft() <= padding) {
                        if (view.getLeft() >= padding - view.getWidth()) {
                            rate = (padding - view.getLeft()) * 1f / view.getWidth();
                        } else {
                            rate = 1;
                        }
                        LogHelper.d(TAG, "往左 = " + (1.0f + rate * ration));
                        view.setScaleX(1.0f + rate * ration);
                        view.setScaleY(1.0f + rate * ration);
                        view.setAlpha(0.6f);
                    } else {
                        //往右 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                        if (view.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - view.getLeft()) * 1f / view.getWidth();
                        }
                        LogHelper.d(TAG, "往右 = " + (1.0f + rate * ration));
                        view.setScaleX(1.0f + rate * ration);
                        view.setScaleY(1.0f + rate * ration);
                        view.setAlpha(1.0f);
                    }

                    if (index == 2) {
                        view.setScaleX(1.0f + ration);
                        view.setScaleY(1.0f + ration);
                        view.setAlpha(1.0f);
                    } else {
                        view.setScaleX(1.0f);
                        view.setScaleY(1.0f);
                        view.setAlpha(0.6f);
                    }
                }
            }
        });

        mLiveSettingBinding.recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                Log.d(TAG, "velocityX = "+velocityX);
                Log.d(TAG, "velocityY = "+velocityY);
                return false;
            }
        });

        mLiveSettingBinding.recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mLiveSettingBinding.recyclerView.getChildCount() < 3) {
                    if (mLiveSettingBinding.recyclerView.getChildAt(1) != null) {
                        View v1 = mLiveSettingBinding.recyclerView.getChildAt(1);
                        v1.setScaleY(0.9f+ration);
                    }
                } else {
                    if (mLiveSettingBinding.recyclerView.getChildAt(0) != null) {
                        View v0 = mLiveSettingBinding.recyclerView.getChildAt(0);
                        v0.setScaleY(0.9f+ration);
                    }
                    if (mLiveSettingBinding.recyclerView.getChildAt(2) != null) {
                        View v2 = mLiveSettingBinding.recyclerView.getChildAt(2);
                        v2.setScaleY(0.9f+ration);
                    }
                }

            }
        });
    }

    private float ration = 0.625f; // 50/80

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLiveSettingBinding.recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCurrentPosition = 10000;
                mLiveSettingBinding.recyclerView.smoothToCenter(mCurrentPosition);
            }
        }, 200);
    }

}
