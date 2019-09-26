package com.wd.health.home.fanye;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * author: 张荣生
 * date: 2019/8/28 16:42
 * update: $date$
 */
public class FrescoLoadUtil {
    private static FrescoLoadUtil inst;
    private ExecutorService executeBackgroundTask = Executors.newSingleThreadExecutor();

    public static FrescoLoadUtil getInstance() {
        if (inst == null) {
            inst = new FrescoLoadUtil();
        }
        return inst;
    }

    private FrescoBitmapCallback callback;

    //加载直接返回Bitmap
    public final void loadImageBitmap(String url, FrescoBitmapCallback<Bitmap> callback) {
        this.callback = callback;
        if (TextUtils.isEmpty(url)) {
            return;
        }

        new MyTask().execute(url);
        /*try {
        } catch (Exception e) {
            //oom风险.
            e.printStackTrace();
            callback.onFailure(Uri.parse(url), e);
        }*/
    }

    class MyTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else {
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                callback.onSuccess(bitmap);
            } else {
                callback.onFailure("请求失败");
            }

        }
    }

    /**
     * @param callable Callable
     * @param <T>      T
     * @return Future
     */
    /*private <T> Future<T> handlerBackgroundTask(Callable<T> callable) {
        return executeBackgroundTask.submit(callable);
    }*/

    /**
     * 回调UI线程中去
     *
     * @param result   result
     * @param uri      uri
     * @param callback FrescoBitmapCallback
     * @param <T>      T
     */
   /* private <T> void postResult(final T result, final Uri uri, final FrescoBitmapCallback<T> callback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(uri, result);
            }
        });
    }*/

    public interface FrescoBitmapCallback<T> {

        void onSuccess(T result);

        void onFailure(String throwable);

    }
}



