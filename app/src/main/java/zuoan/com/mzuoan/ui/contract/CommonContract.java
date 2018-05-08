package zuoan.com.mzuoan.ui.contract;

import zuoan.com.mzuoan.base.BasePresenter;
import zuoan.com.mzuoan.base.BaseView;
import zuoan.com.mzuoan.base.MvpListener;
import zuoan.com.mzuoan.bean.User;

/**
 * Created by 15225 on 2018/2/7.
 */

public interface CommonContract {

    interface CommonView extends BaseView {
        void setUser(User user);
    }

}
