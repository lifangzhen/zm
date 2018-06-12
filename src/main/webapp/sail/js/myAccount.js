$(function(){
	$("#trueName").val(user.result.trueName);
	$("#addr").val(user.result.addr);
	$("#phone").val(user.result.phone);
	var head = "<img src='"+user.result.headImg+"'/>"
	$(".userimg").html(head);
	$(".username").html(user.result.trueName);
	$(".usertext").html("￥:"+user.result.prepay+"元");
	
	var htm = "";
	for(var i=0;i<user.result.ruleList.length;i++){
		var item = user.result.ruleList[i]
		htm = htm + '<li data-id="'+item.id+'">'+
						'<span>¥ '+item.prepay+'</span>'+
						'<b>'+item.name+'(永享'+(parseFloat(item.rate)/10).toFixed(1)+'折)'+'</b>'+
						'<i></i>'+
					'</li>';
	}
	$(".xiadan-zhif").html(htm);
	$(document).on("click",".xiadan-zhif li",function(){
		$(this).addClass("active").siblings().removeClass("active");
	});
	$("#chongzhi").click(function(){
		load();
		if($(".xiadan-zhif .active").length == 0){
			alert("请选择充值类型");
			hideload();
		}
		var openid = localStorage.getItem("openid");
		var gradeId = $(".xiadan-zhif .active").data("id");
//		$.ajax({
//	        type: "POST",
//	        async: false,
//	        data: {
//	        	openid: openid,
//	        	gradeId : gradeId
//	        },
//	        url:  '/h5/charge/json',
//	       	dataType:"json", 
//	        success: function(data) {
//	        	if(data.statusCode=="200"){
//	        		alert("充值成功");
//	        		$(".usertext").html("￥:"+data.result.prepay+"元");
//	        	}
//	        }
//	    });
		
		$.ajax({
	        type: "POST",
	        data: {
	        	openid: openid,
	        	gradeId:gradeId
	        },
	        url:  '/h5/prepayconfig/json',
	       	dataType:"json", 
	        success: function(data) {
	        	if(data.statusCode==200){
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
			    				$(".usertext").html("￥:"+data.result.prepay+"元");
			    				hideload();
			    			},
							error:function(res){
								if(res.errMsg == "chooseWXPay:fail"){
									hideload();
									alert("充值失败");
								}														 		 
							},
							cancel:function(res){
								if(res.errMsg == "chooseWXPay:cancel"){
									hideload();
									alert("充值失败");
								}														 		 
							}	
					});
				});
				wx.error(function(res){
					hideload();
					alert("充值失败");
				});
	        	}
	        }
	    });
		
		
	});
	
	$("#trueName").val(user.result.trueName);
	$("#addr").val(user.result.addr);
	$("#phone").val(user.result.phone);
	
	$("#saveaddr").click(function(){
		var trueName = $("#trueName").val();
		var addr = $("#addr").val();
		var phone = $("#phone").val();
		if(trueName == ""){
			alert("请填写真实姓名");
			return;
		}
		if(addr == ""){
			alert("请填写地址");
			return;
		}
		if(phone == ""){
			alert("请填写电话");
			return;
		}
		var openid = localStorage.getItem("openid");
		$.ajax({
	        type: "POST",
	        async: false,
	        data: {
	        	openid: openid,
	        	trueName : trueName,
	        	addr : addr,
	        	phone : phone
	        },
	        url:  '/h5/saveAddr/json',
	       	dataType:"json", 
	        success: function(data) {
	        	alert(data.message);
	        }
	    });
	});
});
