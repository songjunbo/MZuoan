package zuoan.com.mvp.ui.presenter;

import android.content.Context;



import zuoan.com.mvp.base.BasePresenter;
import zuoan.com.mvp.base.BaseView;
import zuoan.com.mvp.bean.User;
import zuoan.com.mvp.http.Apis;
import zuoan.com.mvp.http.RequestCallBack;
import zuoan.com.mvp.http.RespWrapper;


/**
 * Created by 15225 on 2018/2/7.
 */

public class LoginPresenter extends BasePresenter<BaseView> {

    private Context mContext;

    public LoginPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void login(final String phone, final String pwd, final String type) {

        if (getView() != null) {
            getView().showLoading();

            getActivity().addSubscription(Apis.getApi().login(phone, pwd, type), new RequestCallBack<RespWrapper<User>>() {

                @Override
                public void onSuccess(RespWrapper<User> model) {
                    getView().resultSuccess(model.data);
                }

                @Override
                public void onFailure(int code, String msg) {
                    getView().resultFailed();
                }

                @Override
                public void onFinish() {
                    getView().dissmissLoading();
                }
            });
        }
    }
}
