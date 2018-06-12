var picker = "";
var huiyuan = 100;
var isziqu = 100;
var isdaytime = 100;

$(function(){
	$(".xiadan-dizhi").addClass("edit");
	$(".xiadan-tel").addClass("edit");
	$(".xiadan-dizhi input").removeAttr("readonly");
	$(".xiadan-tel input").removeAttr("readonly");
	
	huiyuan = user.result.rate;
	$(".xiadan-name b").text(user.result.trueName)
	$(".xiadan-tel input").val(user.result.phone)
	$(".xiadan-dizhi input").val(user.result.addr)
	$(".xiadan-zhif li").click(function() {
		if($(this).parent().attr("ttype")=="checkbox"){
			if($(this).hasClass("active")){
				$(this).removeClass("active");
			}else{
				$(this).addClass("active");
			}
		}else{
			$(this).addClass("active").siblings().removeClass("active");
			if($(this).data("type")=="shitang"){
				isziqu = 95;
			}else{
				isziqu = 100;
			}
			comPrice();
		}
	});
	var openid = localStorage.getItem("openid");
	
	$("#submit").click(function(){
		var addr = $(".xiadan-dizhi input").val();
		var takeType = $("#type li.active").attr("tval");
		var payType = "";
		var phone = $(".xiadan-tel input").val();
		if(phone==""){
			alert("手机不能为空");
			return false;
		}
		if($("#type li.active").length==0){
            alert("请选择取餐类型")
            return;
		}
		if(takeType=="1"&&addr==""){
			alert("地址不能为空");
			return false;
		}
//		if($("#paytype li.active").length==2){
//			payType = 2;
//		}else if($("#paytype li.active").length==0){
//			alert("请选择支付方式");
//			return;
//		}else{
//			payType = $("#paytype li.active").attr("tval");
//		}
		
		var sendTime = $("#songdatime").attr("mytime");
		var mark = $("#beizhu").val();
		var orderjson = {};
		var total = $("#zhifujine").text();
		var list = [];
		var dalis =  sessionStorage.getItem("datalist");
		dalis = eval(dalis);
		for(var i=0;i<dalis.length;i++){
			var item = dalis[i];
			var listitem = {};
			listitem.id = item.stype;
			listitem.num = item.num;
			listitem.categoryId = item.ttype;
			list.push(listitem);
		}
		orderjson.total = total;
		orderjson.list = list;
		var objval = {
	        	openid: openid,
	        	takeType: takeType,
	        	payType: payType,
	        	phone:phone,
	        	addr:addr,
	        	sendTime: sendTime,
	        	mark: mark,
	        	orderjson:JSON.stringify(orderjson)
	       };
		load();
		$.ajax({
	        type: "POST",
	        data: objval,
	        url:  '/h5/saveorder/json',
	       	dataType:"json", 
	        success: function(data) {
	        	hideload()
	        	if(data.statusCode==200){
	        		sessionStorage.clear("datalist");
	        		sessionStorage.setItem("objval",objval);
	        		var orderId = data.result.orderId;
	        		var stateObj = { order: "myOrder" }; 
	        		history.replaceState(stateObj, "", "/h5/myorderUi");
	        		window.location.href = "/h5/paydetailUi?orderId="+orderId;
	        	}else{
	        		if(data.message.indexOf("预存已不足") != -1){
	        			if(confirm(data.message)){
	        				window.location.href = "/h5/myaccountUi";
	        			}
	        		}else{
	        			alert(data.message);
	        		}
	        	}
	        }
	    });
	});
	
	$(".editbtn").on("click",function(){
		if($(this).parents(".xiadan-dizhi").hasClass("edit")){
			if($(".xiadan-dizhi input").val() == ""){
				alert("收货地址不能为空");
				return false;
			}
			if($(".xiadan-tel input").val() == ""){
				alert("电话不能为空");
				return false;
			}
			$.ajax({
		        type: "POST",
		        data: {
		        	openid: openid,
		        	trueName : user.result.trueName,
		        	addr : $(".xiadan-dizhi input").val(),
		        	phone : $(".xiadan-tel input").val()
		        },
		        url: '/h5/saveAddr/json',
		       	dataType:"json", 
		        success: function(data) {
		        	$(".xiadan-dizhi").removeClass("edit");
		        	$(".xiadan-dizhi input").attr("readonly","readonly");
		        	$(".xiadan-tel").removeClass("edit");
		        	$(".xiadan-tel input").attr("readonly","readonly");
		        }
		    });
		}else{
			$(this).parents(".xiadan-dizhi").addClass("edit");
			$(".xiadan-tel").addClass("edit");
			$(".xiadan-dizhi input").removeAttr("readonly");
			$(".xiadan-tel input").removeAttr("readonly");
			$(".xiadan-tel input").focus();
		}
	});
	
	initlist();
});
function initlist(){
	var dalis =  sessionStorage.getItem("datalist");
	if(dalis){
		dalis = eval(dalis);
		var htm  = '';
		var zongjg = 0;
		for(var i=0;i<dalis.length;i++){
			var item = dalis[i]
			zongjg = zongjg + parseFloat(item.price)*parseInt(item.num);
			htm = htm + '<li>'+
							'<div class="dingda-d">'+
								'<label>x'+item.num+'</label>'+
								'<span>¥'+item.price+'</span>'+
							'</div>'+
							'<p>'+item.title+'</p>'+
						'</li>'
		}
		
		$(".xiadan-ul").append(htm);
	}
	picker = zongjg;
	comPrice();
}

function comPrice(){
	var jiage = parseFloat(picker)*parseInt(huiyuan)*parseInt(isziqu)*parseInt(isdaytime)/100/100/100;
	jiage = jiage.toFixed(2);
	var youhui = (parseFloat(picker)*100 - parseFloat(jiage)*100)/100;
	youhui = youhui.toFixed(2);
	$("#zongji").text(picker.toFixed(2));
	$(".shifu").text(jiage);
	$("#youhui").text(youhui);
}

