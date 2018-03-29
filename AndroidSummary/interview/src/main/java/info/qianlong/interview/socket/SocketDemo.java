package info.qianlong.interview.socket;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by junzhao on 2017/12/16.
 */

public class SocketDemo {

    //客户端的实现
    public void tcpSendMessage(String msg) {
        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            socket = new Socket("192.168.199.121", 8888);
            outputStream = socket.getOutputStream(); //socket获取输出流不需要关闭
            outputStream.write(msg.getBytes());
            socket.shutdownOutput(); //关闭输出流
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            final StringBuffer stringBuffer = new StringBuffer();
            while ((len = inputStream.read(bytes)) != -1) {
                stringBuffer.append(new String(bytes, 0, len, Charset.forName("gbk")));
            }
            //todo 在子线程中更新ui
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //创建服务端
    public void receiveMessage() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(8888);
            while (true) {
                socket = serverSocket.accept();
                Log.d("receiveMessage---", socket.getInetAddress().getHostName());
                Log.d("receiveMessage---", socket.getInetAddress().getHostAddress());
                InputStream inputStream = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = bis.read(b)) != -1) {
                    Log.d("receiveMessage---", new String(b,0,len,"utf-8"));
                }
                socket.shutdownInput();
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("ok,我已经收到了".getBytes());
                bis.close();
                socket.close();
                socket = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
