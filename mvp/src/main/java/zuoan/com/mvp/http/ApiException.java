package zuoan.com.mvp.http;

import android.util.Log;

/**
 * Created by 15225 on 2018/2/5.
 */

public class ApiException extends RuntimeException {


    private int errorCode;
    private String msg;

    public ApiException(String detailMessage) {
        super(detailMessage);
    }



    public ApiException(int resultCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = resultCode;
        this.msg = detailMessage;
        Log.d("emsg", "===>" + detailMessage);
    }

    public int getErrorCode() {
        return errorCode;
    }



}
