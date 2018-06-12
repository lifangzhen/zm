$(function(){
	$(".zhifu-btn").click(function(){
		load();
		$(".zhifu-btn").attr('disabled','disabled');
		var openid = localStorage.getItem("openid");
		$.ajax({
	        type: "POST",
	        data: {
	        	openid: openid,
	        	orderId:$("#orderId").val(),
	        	type:"payDetail"
	        },
	        url:  '/h5/payconfig/json',
	       	dataType:"json", 
	        success: function(data) {
	        	if(data.statusCode==200){
	        		if(data.result.payType==0){
	        			document.location.href ="/h5/myorderUi";
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
				    		debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    		appId: jc.appId, // 必填，公众号的唯一标识
				    		timestamp: jc.timestamp, // 必填，生成签名的时间戳
				    		nonceStr: jc.nonceStr, // 必填，生成签名的随机串
				    		signature: jc.signature,// 必填，签名，见附录1
				    		jsApiList: ["chooseWXPay"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
						});
			        	
			        	wx.ready(function(){
							wx.chooseWXPay({						
				    			timestamp: timeStamp+"", // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
				   				nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
				    			package: packages, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
				    			signType: signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
				    			paySign: paySign, // 支付签名    					
				    			success: function (res) {
				    				document.location.href ="/h5/myorderUi";
				    			},
								error:function(res){
									if(res.errMsg == "chooseWXPay:fail"){
										document.location.href ="/h5/myorderUi";
									}														 		 
								},
								cancel:function(res){
									if(res.errMsg == "chooseWXPay:cancel"){
										document.location.href ="/h5/myorderUi";
									}														 		 
								}	
						});
					});
					wx.error(function(res){
						document.location.href ="/h5/myorderUi";
					});
	        		}
	        	}
	        }
	    });
	});
	
});

function load(){
	var htm = '<div class="loding"></div>';
	$("body").append(htm);
}

function hideload(){
	$(".loding").remove();
}
