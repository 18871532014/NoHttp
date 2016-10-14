package com.example.wang.nohttp;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //请求队列
    private RequestQueue queue ;

    //设置区分那个一个链接的what（类似hanlder中的what）
    private static final int INT_WHAT =1;

    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //初始化得到http链接
        NoHttp.init(getApplication());

        //创建请求对类，默认的是三个请求 可以传入数量改变请求数量：Nohttp.newRequestQueue(1);
        queue = NoHttp.newRequestQueue();

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //创建请求对象
                Request<String> request=NoHttp.createStringRequest("http://www.baidu.com", RequestMethod.GET);

              /*   添加请求参数（Post或者需要参数请求的时候）
                request.add("userName", "yolanda");
                request.add("userPass", 1);
                request.add("userAge", 1.25);*/


               /*上传文件
               request.add("userHead", new FileBinary(new File(path)));
               */

                /*
                *   添加请求头
                    request.addHeader("Author", "nohttp_sample");
                * */




                //开始http访问链接,
               /*  传入三个参数
                  1.进程自定义编号   what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
                  2.对应的请求
                  3.请求的监听事件  回调对象，接受请求结果
                */
                queue.add(INT_WHAT,request,onResponseListener);
            }
        });

    }


    /*
    * 回调对象，接受请求结果
    * */
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            //请求开始

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            //请求成功
            if (what == INT_WHAT){
                Log.i("information", "返回的结果：" + response.get());
                Log.i("infromation","所用到的时间："+response.getNetworkMillis());
            }

            /*
            *
            *    if (what == NOHTTP_WHAT_TEST) {// 判断what是否是刚才指定的请求
                // 请求成功
                String result = response.get();// 响应结果
                // 响应头
                Headers headers = response.getHeaders();
                headers.getResponseCode();// 响应码
                response.getNetworkMillis();// 请求花费的时间
            }

            * */
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            //请求失败
            if (what == INT_WHAT){
                Log.i("information","返回的结果：结果错误");
            }
        }

        @Override
        public void onFinish(int what) {
            //请求关闭
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll();//活动销毁的时候停止所有的请求
        queue.stop();   //退出App时停止队列
    }
}
