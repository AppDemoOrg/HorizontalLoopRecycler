package com.abt.basic.arch.mvvm;

/**
 * @描述：     @V与VM关联接口
 * @作者：     @黄卫旗
 * @创建时间： @2017-04-25
 */
public interface IView<VM extends com.abt.basic.arch.mvvm.IViewModel> {
    void setViewModel(VM viewModel);
}
