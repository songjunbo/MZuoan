package zuoan.com.mzuoan.http;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zuoan.com.mzuoan.http.api.Api;

/**
 * Created by 15225 on 2018/2/3.
 */

public class Apis {

    private static Api api;
    private static final int DEFAULT_CONNECT_TIMEOUT = 5;
    private static final int DEFAULT_READ_TIMEOUT = 10;
    private static final int DEFAULT_WRITE_TIMEOUT = 10;


    /**
     * 不带生命周期绑定的 获取单例模式的api
     *
     * @return
     */
    public static Api getApi() {
        if (api == null) {
            synchronized (Api.class) {
                if (api == null) {
                    api = getaApi(Api.url, Api.class);
                }
            }
        }
        return api;
    }


    /**
     * 生成api实例
     *
     * @param url   base url
     * @param clazz class
     * @param <T>
     * @return 实例
     */
    private static <T> T getaApi(String url, Class<T> clazz) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 超时设置
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true)
                // 支持HTTPS
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)); //明文Http与比较新的Https
        // cookie管理
//                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance())));

        /**
         * 添加公共参数
         */
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("paltform","android")
//                .addHeaderParams("userToken","1234343434dfdfd3434")
//                .addHeaderParams("userId","123445")
//                .build();
//        builder.addInterceptor(commonInterceptor);


        return new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(clazz);
    }


    public static <T> Observable.Transformer<RespWrapper<T>, T> getTransformer() {
        return new Observable.Transformer<RespWrapper<T>, T>() {
            @Override
            public Observable<T> call(Observable<RespWrapper<T>> respWrapperObservable) {
                return respWrapperObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<RespWrapper<T>, T>() {
                            @Override
                            public T call(RespWrapper<T> allServicesRespWrapper) {
                                if (allServicesRespWrapper.status != 200) {
//                                    throw new RuntimeException(allServicesRespWrapper.msg);
                                    throw new ApiException(allServicesRespWrapper.status, allServicesRespWrapper.msg);
                                }
                                return allServicesRespWrapper.data;
                            }
                        });
            }
        };
    }


}
