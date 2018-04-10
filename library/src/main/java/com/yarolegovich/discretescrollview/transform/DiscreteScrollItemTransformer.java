package com.yarolegovich.discretescrollview.transform;

import android.view.View;

/**
 * @描述：      @DiscreteScrollItemTransformer
 * @作者：      @黄卫旗
 * @创建时间：  @2018-03-31
 */
public interface DiscreteScrollItemTransformer {
    void transformItem(View item, float position);
}
