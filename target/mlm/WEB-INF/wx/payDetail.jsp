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
		<title>订单支付页</title>
		<link rel="stylesheet" type="text/css" href="/sail/css/style.css" />
		<script src="/sail/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
		<script src="/sail/js/jweixin-1.0.0.js" type="text/javascript" charset="utf-8"></script>
		<script src="/sail/js/payDetail.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body style="background: #f0f0f0;">
		<input type="hidden" value="${orderId }" id="orderId">
		<div class="zhifu-div">
			你需要支付<span>${total }</span>
		</div>
		<div class="zhifu-box">
			<table class="zhifu-tab" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="zhifu-td">预存支付</td>
					<td>
						<p>预存支付：${prepay }</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="zhifu-box" style="margin-top: 0px;">
			<table class="zhifu-tab" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="zhifu-td">微信支付</td>
					<td>
						<p>支付金额：${wxpay }</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="zhifu-btn">确认支付</div>
	</body>

</html>