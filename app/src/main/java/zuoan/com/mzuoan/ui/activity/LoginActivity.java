package zuoan.com.mzuoan.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zuoan.com.mzuoan.R;
import zuoan.com.mzuoan.base.BaseActivity;

import zuoan.com.mzuoan.bean.User;
import zuoan.com.mzuoan.http.Apis;
import zuoan.com.mzuoan.http.RespWrapper;
import zuoan.com.mzuoan.http.RxCallback;
import zuoan.com.mzuoan.ui.presenter.UserContract;
import zuoan.com.mzuoan.ui.presenter.UserPresenter;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Log.d("aaa", "====");

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        Log.d("aaa", "-------------");
        Apis.getApi().login("song", "123456", "huawei")
                .compose(Apis.<User>getTransformer())
                .subscribe(new RxCallback<User>() {
                    @Override
                    public void onSuccess(User user) {

                        Log.d("aaa", "--->" + user.getName());
                    }

                    @Override
                    public void onFinished() {

                    }
                });

    }


}
