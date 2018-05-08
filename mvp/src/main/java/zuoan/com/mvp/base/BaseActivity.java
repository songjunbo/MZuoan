package zuoan.com.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends RxActivity {

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

        mBaseContext = getApplicationContext();
        mActivity = this;

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        if(mPresenter!=null) {
            mPresenter.detachView();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(subscriber));
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }



    /**
     * initPresenter
     * @return
     */
    protected abstract T createPresenter();

}
