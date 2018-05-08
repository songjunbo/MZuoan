package zuoan.com.mzuoan;

import android.app.Application;

/**
 * Created by 15225 on 2018/2/5.
 */

public class MyApplication extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Application getInstance(){
        if (application == null) {
            synchronized (MyApplication.class) {
                if (application == null) {
                    application = new MyApplication();
                }
            }
        }
        return application;
    }

}
