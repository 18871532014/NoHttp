# NoHttp
<b>NoHttp框架简单实用</b>

Nohttp框架学习
开发环境：AndroidStudio
自己理解：
                    创建一个网络连接池quequ，将需要进行的链接都丢到这个池中间去
项目代码上传

<b>第一步：添加依赖文件</b>
    在build.gradle 文件长添加注解文件 compile 'com.yolanda.nohttp:nohttp:+'

<b>第二步：穿件Nohttp链接池</b>
      ```  //请求队列
    private RequestQueue queue ;

    初始化队列,默认的是三个请求 可以传入数量改变请求数量：Nohttp.newRequestQueue(1);
    queue = Nohttp.newRequestQueue();
    
    开始创建请求
    Request<String> request = NoHttp.createStringRequest
    ("http://www.baidu.com",what);
       在这里需要传入两个值 
   
    1.需要访问的页面链接
    2.链接的标识码

    /*   添加请求参数（Post或者需要参数请求的时候）
      request.add("userName", "yolanda");
      request.add("userPass", 1);
      request.add("userAge", 1.25);*/


     /*上传文件
         request.add("userHead", new FileBinary(new File(path)));
     */

      /*
       添加请求头
          request.addHeader("Author", "nohttp_sample");
      */
      ```

开始http访问链接,
/*  传入三个参数
   1.进程自定义编号   what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样
   2.对应的请求
   3.请求的监听事件  回调对象，接受请求结果
 */
 queue.add(INT_WHAT,request,onResponseListener);

  创建监听器
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
