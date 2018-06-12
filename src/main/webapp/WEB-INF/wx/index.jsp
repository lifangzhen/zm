<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
	<link rel="stylesheet" href="/css/bootstrap.min.css" >
	<link rel="stylesheet" href="/css/shopMark.css" >
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<title></title>
</head>
<body>
<div class="banner" id="b06">
	<ul>
		<li><img class="sliderimg img-responsive" src="/assets/01.jpg" alt="" width="100%" ></li>
		<li><img class="sliderimg img-responsive" src="/assets/02.jpg" alt="" width="100%" ></li>
		<li><img class="sliderimg img-responsive" src="/assets/03.jpg" alt="" width="100%" ></li>
		<li><img class="sliderimg img-responsive" src="/assets/04.jpg" alt="" width="100%" ></li>
		<li><img class="sliderimg img-responsive" src="/assets/05.jpg" alt="" width="100%" ></li>
	</ul>
</div>
<
<input id="openid" name="openid" value="${openid}">

<input id="msgId" name="msgId" value="${msgId}">

<div class="toshopmark">
	<a href="/mark.html">
		<span><img src="/assets/back.png"></span>
	</a>
</div>
<!--     <div class="markbut">
      <span><img src="./assets/add.png"></span>
    </div> -->
<div class="bottom">
	<div class="row">
		<div class="col-xs-4 col-sm-4">
			<a href="/mark.html" style="display:block;text-decoration:none;width:100%;color:#7d7d7d;">
				<div class="buticon">
					<img src="/assets/message.png">
				</div>
				<div>
					留言
				</div>
			</a>

		</div>
		<div class="col-xs-4 col-sm-4">
			<a href="/friends.html" style="display:block;text-decoration:none;color:#7d7d7d;width:100%;">
				<div class="buticon">
					<img src="/assets/friends.png">
				</div>
				<div>
					好友
				</div>
			</a>
		</div>
		<div class="col-xs-4 col-sm-4">
			<a href="/my.html" style="display:block;text-decoration:none;color:#7d7d7d;width:100%;">
				<div class="buticon">
					<img src="/assets/my.png">
				</div>
				<div>
					我的
				</div>
			</a>
		</div>
	</div>

</div>
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/jquery.endless-scroll-1.3.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/unslider.min.js"></script>
<script src="/js/shopMark.js"></script>
<script>
    $(function(){
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
						debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
						appId: data.data.appId, // 必填，公众号的唯一标识
						timestamp: data.data.timestamp, // 必填，生成签名的时间戳
						nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
						signature: data.data.signature,// 必填，签名，见附录1
						jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					});

					wx.ready(function(){
                        wx.onMenuShareTimeline({
                            title: '我在[么么哒]的桌上刻了留言，你们造吗？', // 分享标题
                            link: 'http://zm.herison.com.cn/h5/redirect?msgId='+$("#msgId").val(), // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一>致
                            imgUrl: 'http://pic.herison.com.cn/1.png', // 分享图标
                            success: function () {
                                alert();
                            }
                        });
                        wx.onMenuShareAppMessage({
                            title: '我在[么么哒]的桌上刻了留言，你们造吗？', // 分享标题
                            link: 'http://zm.herison.com.cn/h5/redirect?msgId='+$("#msgId").val(), // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一>致
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

</body>
</html>