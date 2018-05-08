package zuoan.com.mvp.base;

import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;



import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者: lzh on 16/9/23
 * 邮箱: hy04150829@gmail.com
 * 个人网站: http://leizh.online/
 */
public abstract class BasePresenter<V> {

    //定义view的软引用避免内存泄漏
    private Reference<V> mViewRef;


    //定义activity的软引用
    private Reference<BaseActivity> mActivity;

    private CompositeSubscription mCompositeSubscription;


    /**
     * 与View关联
     * @param view
     */
    public void attachView(V view, BaseActivity activity){
        if(view != null) {
            mViewRef = new WeakReference<V>(view);
        }
        if(activity != null){
            mActivity = new WeakReference<BaseActivity>(activity);
        }
    }

    protected V getView(){
        return mViewRef.get();
    }

    protected BaseActivity getActivity() { return mActivity.get();}

    public boolean isViewAttached(){
        return mViewRef != null&&mViewRef.get()!=null;
    }

    /**
     * 回收引用对象
     */
    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
            onUnsubscribe();
        }
        if(mActivity != null){
            mActivity.get();
            mActivity = null;
        }
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
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




}
