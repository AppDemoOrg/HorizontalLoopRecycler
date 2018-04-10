package com.abt.basic.arch.mvvm;

/**
 * @描述：     @ViewModel业务接口
 * @作者：     @黄卫旗
 * @创建时间： @2017-04-25
 */
public interface IViewModel<N extends INavigator> {

    void initialize();
    void setNavigator(N navigator);
}
