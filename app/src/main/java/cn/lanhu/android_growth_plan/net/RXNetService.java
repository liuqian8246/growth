package cn.lanhu.android_growth_plan.net;

import java.util.ArrayList;
import java.util.Map;

import cn.lanhu.android_growth_plan.entity.DaohangEntity;
import cn.lanhu.android_growth_plan.entity.DateEntity;
import cn.lanhu.android_growth_plan.entity.ListEntity;
import cn.lanhu.android_growth_plan.entity.LoginEntity;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by yx on 2016/7/14.
 * 网络请求总接口
 */
public interface RXNetService {

    String YZM_CODE = "sms/verify_code";
    String REGISTER_CODE = "user/register";
    String PROVINCE_CODE = "system/all";
    String USER_UPLOAD_INFO_CODE = "user/upload/info";
    String VERIFY_LOGIN_CODE = "user/verify/login";
    String PASSWORD_LOGIN_CODE = "user/pwd/login";
    String USER_VERIFY_CODE = "user/verify";
    String SIGN_CODE = "sign/2";
    String UPLOAD_HEAD = "user/upload/head/2";

    /**
     * 注册获取验证码
     */
    @POST(YZM_CODE)
    Observable<BaseBean<String>> getYzCode(@Body Map<String, String> params);

    /**
     * 注册方法
     */
    @POST(REGISTER_CODE)
    Observable<BaseBean<String>> registerAccount(@Body Map<String, String> params);

    @GET(PROVINCE_CODE)
    Observable<BaseBean<ListEntity>> getProvince();

    @PUT(USER_UPLOAD_INFO_CODE)
    Observable<BaseBean<LoginEntity>> uploadInfo(@Body Map<String,Map<String,String>> params);

    @POST(VERIFY_LOGIN_CODE)
    Observable<BaseBean<LoginEntity>> verifyLogin(@Body Map<String,String> params);

    @POST(PASSWORD_LOGIN_CODE)
    Observable<BaseBean<LoginEntity>> passwordLogin(@Body Map<String,String> params);

    @PUT(USER_VERIFY_CODE)
    Observable<BaseBean<String>> forgetPassword(@Body Map<String,String> params);

    @POST("sign/{userId}")
    Observable<BaseBean<String>> sign(@Path("userId") String userId,@Body Map<String,String> params);

    @GET("sign/{userId}")
    Observable<BaseBean<ArrayList<DateEntity>>> getSign(@Path("userId") String userId,@QueryMap Map<String,String> params);

    /**
     * 上传头像
     */
    @Multipart
    @PUT("user/upload/head/{userId}")
    Observable<BaseBean<String>> uploadHead(@Path("userId") String userId,@Part MultipartBody.Part part);


    @GET("banner/5")
    Observable<BaseBean<ArrayList<String>>> getBanner();

    @GET("navigation")
    Observable<BaseBean<ArrayList<DaohangEntity>>> navigation();
}
 