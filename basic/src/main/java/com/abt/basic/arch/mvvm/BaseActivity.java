package com.abt.basic.arch.mvvm;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.abt.basic.R;
import com.abt.basic.permission.PermissionHolder;

/**
 * @描述：     @MVVM Activity基类
 * @作者：     @黄卫旗
 * @创建时间： @2017-04-25
 */
public abstract class BaseActivity<VM extends BaseObservable & com.abt.basic.arch.mvvm.IViewModel, V extends com.abt.basic.arch.mvvm.BaseFragment<VM>>
        extends AppCompatActivity {
    public String TAG = "";

    protected static final String FRAGMENT_TAG_VIEW_MODEL = "FRAGMENT_TAG_VIEW_MODEL";
    public static final int REQUEST_PERMISSIONS            = 1;

    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_common);
        //getWindow().setBackgroundDrawable(null);
        TAG     = getClass().getCanonicalName();
        if(enableRequestPermissions()){
            requestPermissions();
        }else{
            V fragment  = findOrCreateViewFragment();
            mViewModel = findOrCreateViewModel();
            fragment.setViewModel(mViewModel);
        }
    }

    @SuppressWarnings("unchecked")
    @NonNull
    private V findOrCreateViewFragment() {
        V fragment = (V) getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (fragment == null) {
            fragment = createFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_content, fragment)
                    .commitAllowingStateLoss();
        }
        return fragment;
    }

    protected boolean enableRequestPermissions() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    private VM findOrCreateViewModel() {
        com.abt.basic.arch.mvvm.ViewModelHolder<VM> retainedViewModel = (com.abt.basic.arch.mvvm.ViewModelHolder<VM>) getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_TAG_VIEW_MODEL);
        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            // model已经保存，直接返回
            return retainedViewModel.getViewModel();
        } else {
            // 还没有ViewModel，创建
            VM viewModel = createViewModel();
            if (this instanceof com.abt.basic.arch.mvvm.INavigator) {
                viewModel.setNavigator((com.abt.basic.arch.mvvm.INavigator) this);
            }
            // 通过FragmentManager绑定Activity生命周期
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(com.abt.basic.arch.mvvm.ViewModelHolder.createContainer(viewModel), FRAGMENT_TAG_VIEW_MODEL)
                    .commit();
            return viewModel;
        }
    }

    /**
     * 创建Fragment
     */
    @NonNull
    protected abstract V createFragment();

    /**
     * 创建ViewModel
     */
    @NonNull
    protected abstract VM createViewModel();

    /**
     * 申请权限
     */
    private final void requestPermissions() {
        PermissionHolder.requestPermissions(this,
                REQUEST_PERMISSIONS,
                getPermissions(),
                new PermissionHolder.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                V fragment  = findOrCreateViewFragment();
                mViewModel = findOrCreateViewModel();
                fragment.setViewModel(mViewModel);
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                showGotoSettingPermission();
            }
        });
    }

    protected void showGotoSettingPermission(){

    }

    protected String[] getPermissions(){
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionHolder.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
