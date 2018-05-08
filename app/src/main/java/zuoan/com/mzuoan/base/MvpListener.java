package zuoan.com.mzuoan.base;

/**
 * Created by 15225 on 2018/2/7.
 */

public interface MvpListener<T> {

    void onSuccess(T data);

    void onError();
}
