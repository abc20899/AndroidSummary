package info.qianlong.interview.okhttp;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by junzhao on 2017/12/10.
 */

public class OkHttpTest {

    private OkHttpClient okHttpClient;

    public OkHttpTest() {
        //创建全局的 OkHttpClient对象  使用builder设计模式
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .cache(new Cache(new File("file"), 24 * 24 * 1024))
                .build();
    }

    //同步请求
    public void syncRequest() {
        //创建请求对象  建造者模式创建
        Request request = new Request.Builder().url("http://www.junechiu.cn").get().build();
        //返回一个Call对象  链接request和response的桥梁
        Call call = okHttpClient.newCall(request); //RealCall.newRealCall(this, request, false);
        //发起请求 同步方式
        try {
            Response response = call.execute(); //会阻塞当前线程 直到有响应
            System.out.println("syncRequest: " + response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //异步请求
    public void asynRequest() {
        //创建请求对象  建造者模式创建
        Request request = new Request.Builder().url("http://www.junechiu.cn").get().build();
        //返回一个Call对象  链接request和response的桥梁
        Call call = okHttpClient.newCall(request); //RealCall.newRealCall(this, request, false);
        //发起请求 异步方式  开启一个新工作线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("asynRequest: " + response.body().toString());
            }
        });
    }

    public void websocketDemo() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("www.websocket.com")
                .build();


        client.newWebSocket(request, listener);

        //关闭线程池
        client.dispatcher().executorService().shutdown();
    }

    private final WebSocketListener listener = new WebSocketListener() {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
        }
    };

    public void downFileokhttp() {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        //记录已经下载的文件长度
        long downloadLength = 0;
        String downUrl = "http://xxx.xxx/111.txt";
        String fileName = downUrl.substring(downUrl.lastIndexOf("/"));
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File file = new File(dir + fileName);
        if (file.exists()) {
            downloadLength = file.length();
        }
//        long contentLength = getContentLehgth(downUrl);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes=" + downloadLength + "-")
                .url(downUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadLength);//跳过已经下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    total += len;
                    saveFile.write(b, 0, len);
//                    int progress = (int) ((total + downloadLength) * 100 / contentLength);
                }
                response.body().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (saveFile != null) {
                    saveFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
