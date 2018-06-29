<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US" data-scale="true">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="renderer" content="webkit|ie-comp|ie-stand" />

  <!-- <meta http-equiv="x-dns-prefetch-control" content="on" /> -->
  <!-- <link rel="dns-prefetch" href="//xlsdg.github.io" /> -->

  <meta name="format-detection" content="telephone=no,email=no" />

  <!-- <link type="image/x-icon" rel="icon" href="/favicon.ico" />
  <link type="image/x-icon" rel="shortcut icon" href="/favicon.ico" />
  <link type="image/x-icon" rel="bookmark" href="/favicon.ico" /> -->
  <title>留下</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />

  <meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
  <meta name="apple-touch-fullscreen" content="yes">
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta http-equiv="Pragma" content="no-cache" />
  <meta http-equiv="Expires" content="0" />
  <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
  <script src="//as.alipayobjects.com/g/component/fastclick/1.0.6/fastclick.js"></script>
  <script>
      if ('addEventListener' in document) {
          document.addEventListener('DOMContentLoaded', function() {
              FastClick.attach(document.body);
          }, false);
      }
      if(!window.Promise) {
          document.writeln('<script src="//as.alipayobjects.com/g/component/es6-promise/3.2.2/es6-promise.min.js"'+'>'+'<'+'/'+'script>');
      }
  </script>

  <link href="/dva-antd-mobile-starter/index.b1ee95f2.css" rel="stylesheet"></head>
<body>
<input id="uid" value="${uid}" style="display:none;"/>
<input id="storeId" value="${storeId}" style="display:none;"/>
<input id="tableId" value="${tableId}" style="display:none;"/>
<div id="root"></div>
<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script>
    $(function(){
        var linkUrl = "http://zm.herison.com.cn/h5/redirect?storeId="+$("#storeId").val()+"&tableId="+$("#tableId").val()+"&uid="+$("#uid").val();
        $.ajax({
            type: 'POST',
            data: {
                pageUrl: location.href.split('#')[0]
            },
            url:  '/h5/config',
            dataType:"json",
            success: function(data) {
                if(data.errorCode==0){
                    wx.config({
                        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: data.data.appId, // 必填，公众号的唯一标识
                        timestamp: data.data.timestamp, // 必填，生成签名的时间戳
                        nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
                        signature: data.data.signature,// 必填，签名，见附录1
                        jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                    });

                    wx.ready(function(){
                        wx.onMenuShareTimeline({
                            title: '我在[么么哒]的桌上刻了留言，你们造吗？', // 分享标题
                            link: linkUrl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一>致
                            imgUrl: 'http://pic.herison.com.cn/1.png', // 分享图标
                            success: function () {
                                alert();
                            }
                        });
                        wx.onMenuShareAppMessage({
                            title: '我在[么么哒]的桌上刻了留言，你们造吗？', // 分享标题
                            link: "http://zm.herison.com.cn/h5/redirect?storeId="+$("#storeId").val()+"&tableId="+$("#tableId").val()+"&uid="+$("#uid").val(), // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一>致
                            imgUrl: 'http://pic.herison.com.cn/1.png', // 分享图标
                            success: function () {
                                // 用户点击了分享后执行的回调函数
                            }
                        });
                    });
                    wx.error(function(res){
                        alert("wx.error");
                    });
                }else{
                    alert(data.message);
                }
            },
            error:function(data){
                alert("erro:"+data.statusCode);
            }
        });
    });

</script>
<!-- <script>
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    WeixinJSBridge.call('hideToolbar');
    WeixinJSBridge.call('hideOptionMenu');
});
</script>  -->
<script type="text/javascript" src="/dva-antd-mobile-starter/common.e3a2f22d12b9e99620cb.js"></script><script type="text/javascript" src="/dva-antd-mobile-starter/index.ca701e75.js"></script></body>
</html>










