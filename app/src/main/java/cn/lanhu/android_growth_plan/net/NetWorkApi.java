package cn.lanhu.android_growth_plan.net;

import android.annotation.SuppressLint;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yx on 2016/7/15.
 * 网络请求构建
 */
public class NetWorkApi {

    private static volatile NetWorkApi instance;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    public static NetWorkApi getInstance() {
        if (instance == null) {
            synchronized (NetWorkApi.class) {
                if (instance == null) {
                    instance = new NetWorkApi();
                }
            }
        }
        return instance;
    }

    /**
     * 构建okhttp
     */
    public OkHttpClient gradleOkHttp(Context context) {
        if (mOkHttpClient == null) {
            CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(context));
            OkHttpClient.Builder okHttpBuider = new OkHttpClient.Builder()
                    .addInterceptor(new NetLoggerInterceptor(true))
                    .connectTimeout(30000, TimeUnit.MILLISECONDS)
                    .readTimeout(30000, TimeUnit.MILLISECONDS)
                    //其他配置
                    .cookieJar(cookieJar);//持久化session

            try {
                // OKHttp信任所有证书
                 TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                okHttpBuider.sslSocketFactory(sslSocketFactory);
                okHttpBuider.hostnameVerifier((hostname, session) -> true);

                mOkHttpClient = okHttpBuider.build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            OkHttpUtils.initClient(mOkHttpClient);
        }
        return mOkHttpClient;
    }
    /**
     * 构建Retrofit
     *
     * @return
     */
    public Retrofit gradleRetrofit(Context context) {
        if (mRetrofit == null) {
            OkHttpClient okHttpClient = gradleOkHttp(context);
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(HttpUrlApi.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }


}

