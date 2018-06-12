<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="no">
		<meta name="apple-mobile-web-app-status-bar-style" content="white">
		<meta name="format-detection" content="telephone=no">
		<meta name="apple-touch-fullscreen" content="yes">
		<title>我的账户</title>
		<link rel="stylesheet" type="text/css" href="/sail/css/style.css" />
		<script src="/sail/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
		<script src="/sail/js/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="/sail/js/jweixin-1.0.0.js" type="text/javascript" charset="utf-8"></script>
		<script src="/sail/js/myAccount.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body style="background: #f0f0f0;">
		<div class="head">
			<ul>
				<li>
					<a href="/h5/h5index"><span>点菜</span></a>
				</li>
				<li >
					<a href="/h5/myorderUi"><span>订单</span></a>
				</li>
				<li class="active" style="width: 45%;">
					<a href="#">
						<span>充值</span>
						<div class="user">
							<div class="userimg"><img src="" /></div>
							<div class="username"></div>
							<div class="clear usertext"></div>
						</div>
					</a>
				</li>
			</ul>
		</div>
		<div class="mychongz">
			<div class="dingda-title">预存充值</div>
			<div class="xiadan-box">
				<ul class="xiadan-zhif">
					<li class="active">
						<span></span>
						<b></b>
						<i></i>
					</li>
					<li>
						<span></span>
						<i></i>
					</li>
					<li>
						<span></span>
						<i></i>
					</li>
				</ul>
			</div>
			<div class="mybtnbox">
				<a href="javascript:void(0)" id="chongzhi">充值</a>
			</div>
		</div>
		<div class="mychongz">
			<div class="dingda-title">收货信息</div>
			<div class="xiadan-box">
				<ul class="my-form">
					<li>
						<label>真实姓名<span class="red">*</span></label>
						<div class="my-formright">
							<input type="text" placeholder="请输入姓名" id="trueName"  class="xiadan-input"/>
						</div>
					</li>
					<li>
						<label>联系电话<span class="red">*</span></label>
						<div class="my-formright">
							<input type="text" placeholder="请输入联系电话"  id="phone"  class="xiadan-input"/>
						</div>
					</li>
					<li>
						<label>收货地址<span class="red">*</span></label>
						<div class="my-formright">
							<input type="text" placeholder="请输入收货地址"  id="addr" class="xiadan-input"/>
						</div>
					</li>
				</ul>
			</div>
			<div class="mybtnbox">
				<a href="javascript:void(0)" id="saveaddr">保存</a>
			</div>
		</div>
		<script type="text/javascript">
			$(".xiadan-zhif li").click(function(){
				$(this).addClass("active").siblings().removeClass("active");
			});
		</script>
	</body>

</html>