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
		<title>预订单</title>
		<script src="/sail/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="/sail/js/iscroll.js"></script>
		<script src="/sail/js/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="/sail/js/order.js" type="text/javascript" charset="utf-8"></script>
		<script src="/sail/js/mydate.js" type="text/javascript" charset="utf-8"></script>
		<link rel="stylesheet" type="text/css" href="/sail/css/style.css" />
	</head>

	<body style="background: #f0f0f0;">
		<div class="xiadan-box">
			<div class="xiadan-dz">
				<span class="xiadan-name"><i></i><b></b></span>
				<span class="xiadan-tel">
					<i></i><input type="text" readonly="readonly" value="" />
				</span>
				<div class="xiadan-dizhi ">
					<i></i><input type="text" readonly="readonly" value="" />
					<a href="javascript:void(0)" class="editbtn"></a>
				</div>
			</div>
		</div>
		<div class="xiadan-box">
			<ul class="xiadan-zhif" id="type">
				<li tval = "1" data-type="waimai">
					外卖
					<i></i>
				</li>
				<li tval = "2" data-type="shitang">
					自取<span class="tishi-hong">(自取再打95折)</span>
					<i></i>
				</li>
			</ul>
		</div>
<!-- 		<div class="xiadan-box"> -->
<!-- 			<ul class="xiadan-zhif" ttype="checkbox" id="paytype"> -->
<!-- 				<li class="active" tval="0"> -->
<!-- 					预存款支付 -->
<!-- 					<i></i> -->
<!-- 				</li> -->
<!-- 				<li tval="1"> -->
<!-- 					微信支付 -->
<!-- 					<i></i> -->
<!-- 				</li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
		<div class="xiadan-box">
			<div class="songd-time clearfix">
				<label style="line-height: 18px;margin-top: 5px;">送达时间<br><span style="margin: 0px;" class="tishi-hong">(预订明后日，再打9折)</span></label>
				<div class="songda-t">
					<span id="songdatime">立即送达</span>
					<i></i>
				</div>
			</div>
			<div class="songd-beizhu">
				<p>备注</p>
				<textarea class="songd-bztextarea" id="beizhu" placeholder="输入备注"></textarea>
			</div>
		</div>
		<div class="xiadan-box">
			<div class="dingda-title">订单详情</div>
			<ul class="xiadan-ul">
				<!--<li>
					<div class="dingda-d">
						<label>x1</label>
						<span>¥39.9</span>
					</div>
					<p>太阳后裔公仔太阳后裔公仔一份太阳后裔公仔一份一份</p>
				</li>
				<li>
					<div class="dingda-d">
						<label>x1</label>
						<span>¥39.9</span>
					</div>
					<p>太阳后裔公仔太阳后裔公仔一份太阳后裔公仔一份一份</p>
				</li>
				<li>
					<div class="dingda-d">
						<label>x1</label>
						<span>¥39.9</span>
					</div>
					<p>太阳后裔公仔太阳后裔公仔一份太阳后裔公仔一份一份</p>
				</li>-->
			</ul>
			<div class="dingda-jiag">
				<span >总计：¥<b id="zongji">0</b></span>
				<span >优惠：¥<b id="youhui">0</b></span>
				<p >实付：¥<b class="shifu">0</b></p>
			</div>
		</div>
		<div class="xiadan-box">
			<div class="xiadan-foot">
				待支付：¥<span class="shifu" id="zhifujine">0</span>
				<a id="submit">提交订单</a>
			</div>
		</div>
		<div id="datePlugin"></div>

		<!--送达时间-->
		<div class="scroll-select-container " tabindex="-1">
			<div class="scroll-select-close"></div>
			<div class="scroll-select-content">
				<div class="scroll-select-header">
					<span>选择送达时间</span>
					<div class="scroll-select-confirm btn--yellow">确定</div>
				</div>
				<div class="flex-row">
					<div class="scroll-select flex-area-select" id="daywrapper" style="height: 200px;">
						<div class="scroll-wrapper" style="transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1); transition-duration: 0ms; transform: translate(0px, 0px) translateZ(0px);">
							<div class="placeholder-container">
								<a data-option="false"></a>
								<a data-option="false"></a>
							</div>
							<div class="active-select">
								<div class="optionsData-wrapper clearfix">
									
								</div>
							</div>
							<div class="placeholder-container">
								<a data-option="false"></a>
								<a data-option="false"></a>
							</div>
						</div>
					</div>
					<div class="scroll-select flex-table-select"  id="Hourwrapper" style="height: 200px;">
						<div class="scroll-wrapper" style="transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1); transition-duration: 0ms; transform: translate(0px, 0px) translateZ(0px);">
							<div class="placeholder-container">
								<a data-option="false"></a>
								<a data-option="false"></a>
							</div>
							<div class="active-select">
								<div class="optionsData-wrapper clearfix">
									
								</div>
							</div>
							<div class="placeholder-container">
								<a data-option="false"></a>
								<a data-option="false"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--送达时间 end-->
		
	</body>

</html>