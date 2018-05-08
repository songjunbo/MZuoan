package zuoan.com.mzuoan.ui.presenter;

import android.util.Log;

import zuoan.com.mzuoan.base.MvpListener;
import zuoan.com.mzuoan.bean.User;
import zuoan.com.mzuoan.http.Apis;
import zuoan.com.mzuoan.http.RespWrapper;
import zuoan.com.mzuoan.http.RxCallback;
import zuoan.com.mzuoan.http.api.Api;
import zuoan.com.mzuoan.ui.contract.CommonContract;

/**
 * Created by 15225 on 2018/2/7.
 */

public class UserPresenter implements UserContract.Presenter {

    private UserContract.View view;

    public UserPresenter(UserContract.View view) {
        this.view = view;
    }

    @Override

    public void login(String phone, String pwd, String type) {
        view.showLoading();

        Apis.getApi().login(phone, pwd, type)
                .compose(Apis.<User>getTransformer())
                .subscribe(new RxCallback<User>() {
                    @Override
                    public void onSuccess(User user) {
                        Log.d("aaa", "--->" + user);
                        view.upDateUi(user);
                    }

                    @Override
                    public void onFinished() {
                        view.hideLoading();
                    }
                });
    }
}
