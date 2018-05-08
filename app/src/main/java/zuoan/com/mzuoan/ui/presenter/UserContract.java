package zuoan.com.mzuoan.ui.presenter;

import java.util.List;

import zuoan.com.mzuoan.bean.User;

/**
 * Created by 15225 on 2018/2/7.
 */

public interface UserContract {

    interface Presenter{
        void login(String phone, String pwd, String type);
    }

    interface View{
        void showLoading();

        void hideLoading();

        void upDateUi(User user);

        void showOnFailure();
    }
}
