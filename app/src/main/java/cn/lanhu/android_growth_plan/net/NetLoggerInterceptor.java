package cn.lanhu.android_growth_plan.net;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;

/**
 * Created by yx on 17/6/2.
 * okhttp网络请求拦截器
 */
public class NetLoggerInterceptor implements Interceptor {

    private boolean showResponse;

    public NetLoggerInterceptor(boolean showResponse) {
        this.showResponse = showResponse;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .build();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();

            Logger.wtf("========response'log=======" +
                    "\nurl : " + clone.request().url() +
                    "\ncode : " + clone.code() +
                    "\nprotocol : " + clone.protocol() +
                    "\nmessage : " + (clone.message().isEmpty() ? "" : clone.message())
            );
            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Logger.wtf("responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            Logger.wtf("responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            Logger.wtf("responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
//            Logger.wtf("========response'log=======end");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            Logger.wtf("========request'log=======" +
                    "\nmethod : " + request.method() +
                    "\nurl : " + url +
                    "\nheaders : " + (headers != null && headers.size() > 0 ? headers.toString() : ""));
            if (showResponse) {
                RequestBody requestBody = request.body();
                if (requestBody != null) {
                    MediaType mediaType = requestBody.contentType();
                    if (mediaType != null) {
                        Logger.wtf("requestBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            Logger.wtf("requestBody's content : " + bodyToString(request));
                        } else {
                            Logger.wtf("requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

//            Logger.wtf("========request'log=======end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
