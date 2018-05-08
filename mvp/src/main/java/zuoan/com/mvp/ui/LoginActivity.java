package zuoan.com.mvp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zuoan.com.mvp.R;
import zuoan.com.mvp.base.BaseActivity;

import zuoan.com.mvp.base.BaseView;
import zuoan.com.mvp.bean.User;
import zuoan.com.mvp.ui.presenter.LoginPresenter;


public class LoginActivity extends BaseActivity<BaseView, LoginPresenter> implements BaseView<User> {


    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dissmissLoading() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void resultSuccess(User data) {
        tv.setText(data.getName());
    }


    @Override
    public void resultFailed() {
        tv.setText("加载失败");
    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        mPresenter.login("song", "123456", "huawei");
    }
}
