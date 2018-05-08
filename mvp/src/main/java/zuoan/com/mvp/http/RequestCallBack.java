package zuoan.com.mvp.http;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 作者: lzh on 16/9/26
 * 邮箱: hy04150829@gmail.com
 * 个人网站: http://leizh.online/
 */
public abstract class RequestCallBack<M> extends Subscriber<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(int code, String msg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "网络拥堵，请稍后再试";
            }
            onFailure(code, msg);
        } else {
            onFailure(0, e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(M model) {
        onSuccess(model);
    }

    @Override
    public void onCompleted() {
        onFinish();
    }
}
