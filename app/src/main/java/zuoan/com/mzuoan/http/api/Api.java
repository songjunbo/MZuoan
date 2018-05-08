package zuoan.com.mzuoan.http.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import zuoan.com.mzuoan.bean.User;
import zuoan.com.mzuoan.http.BaseUrl;
import zuoan.com.mzuoan.http.RespWrapper;

/**
 * Created by 15225 on 2018/2/3.
 */

public interface Api {


    String url = BaseUrl.url + "/index.php/api/";

    /**
     * 登录
     *
     * @param username
     * @param userpwd
     * @param jixing
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<RespWrapper<User>> login(@Field("username") String username, @Field("userpwd") String userpwd, @Field("jixing") String jixing);


}
