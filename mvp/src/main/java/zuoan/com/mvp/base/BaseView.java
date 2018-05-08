package zuoan.com.mvp.base;

/**
 * Created by 15225 on 2018/2/7.
 */

public interface BaseView<T> {
    void showLoading();

    void dissmissLoading();

    void resultSuccess(T data);

    void resultFailed();
}
