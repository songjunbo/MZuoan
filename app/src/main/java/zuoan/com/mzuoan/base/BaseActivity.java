package zuoan.com.mzuoan.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.orhanobut.logger.Logger;


import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuoan.com.mzuoan.app.ActivityManager;

/**
 * 作者: lzh on 16/9/9
 * 邮箱: hy04150829@gmail.com
 * 个人网站(统一更改为): http://leizh.online/
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    public T mPresenter;


    public Activity mActivity;
    public Context mBaseContext;
    //时间紧迫Activity请求网络,后期优化放入presenter中
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //允许为空，不是所有都要实现MVP模式
        if(createPresenter()!=null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this,(BaseActivity) this);
        }

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            getWindow().setBackgroundDrawable(null);
        }
        mBaseContext = getApplicationContext();
        mActivity = this;
        ButterKnife.bind(this);
        ActivityManager.addActivity(this);
        initData();
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
        onUnsubscribe();
        if(mPresenter!=null) {
            mPresenter.detachView();
        }
    }

    /**
     * 在API16以前使用setBackgroundDrawable，在API16以后使用setBackground
     * API16<---->4.1
     * @param view
     * @param drawable
     */
    public void setBackgroundOfVersion(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //Android系统大于等于API16，使用setBackground
            view.setBackground(drawable);
        } else {
            //Android系统小于API16，使用setBackground
            view.setBackgroundDrawable(drawable);
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init data
     */
    protected abstract void initData();

    /**
     * initPresenter
     * @return
     */
    protected abstract T createPresenter();


}
