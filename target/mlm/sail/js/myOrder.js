$(function(){
	var head = "<img src='"+user.result.headImg+"'/>"
	$(".userimg").html(head);
	$(".username").html(user.result.trueName);
	$(".usertext").html("￥:"+user.result.prepay+"元");
	var openid = localStorage.getItem("openid");
	$.ajax({
        type: "POST",
        async: false,
        data: {
        	openid: openid,
        	page : 1,
        },
        url:  '/h5/orderList/json',
       	dataType:"json", 
        success: function(data) {
        	if(data.result.list.length>0){
        		var htm = "";
        		for(var i = 0;i<data.result.list.length;i++){
        			var item = data.result.list[i];
        			var qrhtm = '';
        			var listhtm = '';
        			if(item.status == "1"){
        				qrhtm = '<a data-id="'+item.id+'" href="javascript:void(0)" class="mydingd-btn">确认支付</a>';
        			}
        			if(item.list.length>0){
        				for(var j =0;j<item.list.length;j++){
        					listhtm = listhtm + '<li>'+
													'<p>'+item.list[j].dishName+'</p>'+
													'<span>'+item.list[j].price+'  x  '+item.list[j].dishNum+'</span>'+
												'</li>';
        				}
        			}
        			htm = htm + '<div class="dingdan-lis">'+
									'<div class="dingdan-title">收取时间：'+item.sendTimeStr+'</div>'+
									'<ul class="dingdan-lisul">'+
										listhtm+
									'</ul>'+
									'<div class="xinxi">'+
										'<p>共'+item.dishNum+'件商品，实付¥'+item.pay+'</p>'+
										 qrhtm+
									'</div>'+
								'</div>';
        		}
        		$(".myborde").html(htm);
        	}
        }
    });
    $(document).on("click",".mydingd-btn",function(){
    	var orderid = $(this).data("id");
    	var self = $(this);
    	if(confirm("确定要付款吗？"))
		{
    		load();
		 	$.ajax({
		        type: "POST",
		        data: {
		        	openid: openid,
		        	orderId: orderid,
		        	type:"order"
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
					    				self.remove();
					    				hideload();
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
		        	}else{
		        		alert(data.message);
		        		hideload();
		        	}
		        }
		});
		}
    });
});
