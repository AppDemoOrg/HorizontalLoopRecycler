package com.abt.basic.arch.mvvm;

import android.databinding.BaseObservable;

import java.lang.ref.WeakReference;

/**
 * @描述：     @Dialog VM基类
 * @作者：     @黄卫旗
 * @创建时间： @2017-04-25
 */
public class DialogModel extends BaseObservable {

    private WeakReference<com.abt.basic.arch.mvvm.IDialogView> mIDialogView;

    public DialogModel() {

    }

    /**
     * 绑定视图
     * @param dialogView
     */
    void bindView(com.abt.basic.arch.mvvm.IDialogView dialogView){
        this.mIDialogView = new WeakReference<com.abt.basic.arch.mvvm.IDialogView>(dialogView);
    }

    /**
     * 解除绑定
     */
    void unbindView(){
        this.mIDialogView.clear();
    }

    /**
     * 关闭对话框
     */
    public final void dismiss(){
        if(null != mIDialogView){
            final com.abt.basic.arch.mvvm.IDialogView dialog = mIDialogView.get();
            if(null != dialog){
                dialog.hideDialog();
            }
        }
    }
}
