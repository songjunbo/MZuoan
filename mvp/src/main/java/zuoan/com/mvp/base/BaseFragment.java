package zuoan.com.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 *
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends RxFragment {

    protected T mPresenter;
    //时间紧迫Activity请求网络,后期优化放入presenter中
    private CompositeSubscription mCompositeSubscription;
    private View rootView;
    private String pageName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageName = getClass().getSimpleName();
        //允许为空，不是所有都要实现MVP模式
        if(createPresenter()!=null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this,(BaseActivity) getActivity());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(createViewLayoutId(),container,false);
        initView(rootView);
        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onUnsubscribe();
        if(mPresenter!=null) {
            mPresenter.detachView();
        }
        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
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

    protected abstract T createPresenter();

    protected abstract int createViewLayoutId();

    protected abstract void initView(View rootView);

}

