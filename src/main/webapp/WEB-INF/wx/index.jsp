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
	<title>留下餐厅首页</title>
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
<div class="wifi">
	<div><img src="/assets/wifi.png"></div>
	<div class="slogan">wifi: 123456789</div>
</div>
<input id="openid" name="openid" value="${openid}">
<div id="comsdiv">
	<div class="comtype">
		<span class="type" type="hot">最热</span> <span class="type active" type="new">最新</span>
	</div>
	<div class="comitem">
		<div class="comhead">
			<div class="avart">
				<img src="/assets/avart.jpg">
			</div>
			<div class="nickname">
				小红
			</div>
		</div>
		<div class="combody">
			<div class="comcontent">网红餐厅--6号</div>
			<div class="comimg">
				<img src="/assets/comimg.jpg">
				<img src="/assets/comimg.jpg">
				<img src="/assets/comimg.jpg">
			</div>
		</div>
		<div class="comfoot">
			<div class="footbut share">
				<img src="/assets/share.png" style="width:16px;height:16px;"> <span>转发</span>
			</div>
			<div class="footbut message">
				<img src="/assets/message.png"> <span>65432</span>
			</div>
			<div class="footbut fav">
				<img src="/assets/fav.png" style="vertical-align: bottom;"> <span>93</span>
			</div>
			<div class="footbut complain">
				<img src="/assets/complain.png" style="width:16px;height:16px;"> <span>投诉</span>
			</div>
		</div>
	</div>

	<div class="comitem">
		<div class="comhead">
			<div class="avart">
				<img src="/assets/avart.jpg">
			</div>
			<div class="nickname">
				小红
			</div>
		</div>
		<div class="combody">
			<div class="comcontent">网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号网红餐厅--6号</div>
			<div class="comimg">
				<img src="/assets/comimg.jpg">
				<img src="/assets/comimg.jpg">
				<img src="/assets/comimg.jpg">
			</div>
		</div>
		<div class="comfoot">
			<div class="footbut share">
				<img src="/assets/share.png" style="width:16px;height:16px;"> <span>转发</span>
			</div>
			<div class="footbut message">
				<img src="/assets/message.png"> <span>65432</span>
			</div>
			<div class="footbut fav">
				<img src="/assets/fav.png" style="vertical-align: bottom;"> <span>93</span>
			</div>
			<div class="footbut complain">
				<img src="/assets/complain_active.png" style="width:16px;height:16px;"> <span>投诉</span>
			</div>
		</div>
	</div>

	<div class="comitem">
		<div class="comhead">
			<div class="avart">
				<img src="/assets/avart.jpg">
			</div>
			<div class="nickname">
				小红
			</div>
		</div>
		<div class="combody">
			<div class="comcontent">网红餐厅--6号</div>
			<div class="comimg">
				<img src="/assets/comimg.jpg">
				<img src="/assets/comimg.jpg">
				<img src="/assets/comimg.jpg">
			</div>
		</div>
		<div class="comfoot">
			<div class="footbut share">
				<img src="/assets/share.png" style="width:16px;height:16px;"> <span>转发</span>
			</div>
			<div class="footbut message">
				<img src="/assets/message.png"> <span>65432</span>
			</div>
			<div class="footbut fav">
				<img src="/assets/fav_active.png" style="vertical-align: bottom;"> <span>93</span>
			</div>
			<div class="footbut">
				<img src="/assets/complain.png" style="width:16px;height:16px;"> <span>投诉</span>
			</div>
		</div>
	</div>

</div>
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
<script>
    $(function(){
        $.ajax({
            type: "POST",
            url:  '/h5/config',
            dataType:"json",
            success: function(data) {
                if(data.statusCode==200){
                    if(data.result.payType==0){
                        document.location.href ="#";
                    }else{
                        var m = data.result;
                        var jc = m.jsconfig;
                        var signature = m.signature;
                        var appid = m.appid;
                        var timeStamp = m.timeStamp;
                        var nonceStr = m.nonce_str;
                        var packages =  "prepay_id="+m.prepay_id;
                        var signType = "MD5";
                        var paySign = m.paySign;
                        wx.config({
                            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId: jc.appId, // 必填，公众号的唯一标识
                            timestamp: jc.timestamp, // 必填，生成签名的时间戳
                            nonceStr: jc.nonceStr, // 必填，生成签名的随机串
                            signature: jc.signature,// 必填，签名，见附录1
                            jsApiList: ["onMenuShareAppMessage"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });

                        wx.ready(function(){
                            wx.onMenuShareTimeline({
                                title: 'type', // 分享标题
                                link: 'zm.herison.com.cn', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                                imgUrl: 'pic.herison.com.cn/1.png', // 分享图标
                                success: function () {
                                    // 用户点击了分享后执行的回调函数
                                }
                            });
                        });
                        wx.error(function(res){
                            alert("wx.error");
                        });
                    }
                }else{
                    alert(data.message);
                    hideload();
                }
            },
			error:function(data){
                alert("erro:"+data.statusCode);
			}
        });
    });

</script>


<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/jquery.endless-scroll-1.3.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/unslider.min.js"></script>
<script src="/js/shopMark.js"></script>
</body>
</html>