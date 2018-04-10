package com.abt.basic.arch.mvvm;

import android.support.v4.app.Fragment;

/**
 * @描述：     @MVVM BaseFragment基类
 * @作者：     @黄卫旗
 * @创建时间： @2017-04-25
 */
public class BaseFragment<VM extends com.abt.basic.arch.mvvm.IViewModel> extends Fragment implements com.abt.basic.arch.mvvm.IView<VM> {

    protected VM mViewModel;

    @Override
    public void setViewModel(VM viewModel) {
        this.mViewModel = viewModel;
    }
}
