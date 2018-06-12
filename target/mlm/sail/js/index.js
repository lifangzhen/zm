$(function(){
	var openid = $("#openid").val();
	var rate = $("#rate").val();
	if(openid!=null&&openid!=""){
		localStorage.setItem("openid", openid);
	}else{
		openid = localStorage.getItem("openid");
	}
	$.ajax({
        type: "POST",
        data: {
        	openid : openid
        },
        url:  '/h5/index/json',
       	dataType:"json", 
        success: function(data) {
        	var head = "<img src='"+data.result.headImg+"'/>";
        	var zk = (parseFloat(data.result.rate)/10).toFixed(1)+"折";
        	if(data.result.rate=="100") zk = "无优惠";
        	var ratehtm = "<span style='color:red'>全场"+zk+"</span>";
        	$(".userimg").html(head);
        	$(".username").html(data.result.grade);
        	$(".usertext").html(ratehtm);
        	var navhtm = "";
        	for(var i = 0;i<data.result.categoryList.length;i++){
        		var item = data.result.categoryList[i];
        		navhtm = navhtm + '<li><a ttype="'+item.id+'" href="javascript:void(0)">'+item.name+'</a></li>';
        		rate = data.result.rate;
        		var listlih = '';
        		for(var j = 0;j<item.list.length;j++){
        			var litems = item.list[j];
        			listlih = listlih +'<li ttype="'+item.id+'" stype="'+litems.id+'">'+
											'<div class="fenlei-list-img">'+
												'<img src="'+litems.pic+'"  />'+
											'</div>'+
											'<div class="fenlei-list-txt">'+
												'<p>'+litems.name+'</p>'+
												'<div class="fenlei-jg">￥<span>'+litems.price+'</span></div>'+
												'<span class="fenl-tianjia"></span>'+
											'</div>'+
											'<div class="wm-cz wm-hide" data-num="'+litems.stock+'" tipe="0">'+
												'<span class="jian"></span>'+
												'<span class="num">0</span> '+
												'<span class="jia"></span>'+
											'</div>'+
										'</li>';
        		}
        		var listhtm = "";
        		listhtm = listhtm+'<div class="itype" mytype="'+item.id+'">'+
											'<div class="i-title">'+item.name+'</div>'+
											'<ul class="fenlei-list">'+listlih+'</ul>'+
										'</div>';
				$(".fenlei-right").append(listhtm);
				
        	}
        	$("#fenlnav").append(navhtm);
        	$("#fenlnav li").first().addClass("active");
        	
        	initlist(rate);
        }
    });
	$(".fenlei-right").scroll(function(){
		var scrltop  = $(this).scrollTop();
		$(".itype").each(function(index,el){
			var ttop = $(el).offset().top-60+scrltop;
			if(scrltop >= ttop){
				$('.fenlei-left li').eq(index).addClass("active").siblings().removeClass("active");
			}
		});
	});
	$(document).on("click",".fenlei-left ul li",function(e){
		var index = $(this).index();
		$(this).addClass("active").siblings().removeClass("active");
		$(".fenlei-right").scrollTop($(".itype").eq(index).offset().top-55+$(".fenlei-right").scrollTop());
		e.stopPropagation(); 
		return false;
	});
	
	$(document).on("click",".fenlei-list .jia",function(){
		$(this).parent().removeClass("wm-hide");
		var num = parseInt($(this).siblings(".num").text())+1;
		$(this).siblings(".num").text(num);
		
		var type = $(this).parents(".itype").attr("mytype");
		var stype = $(this).parents("li").attr("stype");
		var obj = $("#orderlist li[ttype='"+type+"'][stype='"+stype+"']");
		//价格
		var jg = parseFloat($(this).parents("li").find(".fenlei-jg span").text());
		//名称
		var name = $(this).parents("li").find(".fenlei-list-txt p").text();
		
		if(obj.length>0){
			obj.find(".num").text(num);
		}else{
			var htm = '<li ttype="'+type+'" stype="'+stype+'">'+
						'<div class="wm-cz">'+
							'<span class="jian"></span> '+
							'<span class="num">'+num+'</span> '+
							'<span class="jia"></span>'+
						'</div>'+
						'<div class="shop-jg">¥<span>'+jg+'</span></div>'+
						'<p class="shop-title">'+name+'</p>'+
					'</li>';
			
			$("#orderlist").append(htm);
		}
		
		//购物车数量
		if($(".gwc .gwc-num").length>0){
			var gwcnum = parseInt($(".gwc .gwc-num").text())+1;
			$(".gwc .gwc-num").text(gwcnum);
		}else{
			var gwchtm = '<span class="gwc-num">1</span>';
			$(".gwc").append(gwchtm);
		}
		//价格
		var zongjg = (jg+parseFloat($(".gwc-jiag span").text())).toFixed(2);
		$(".qisongtis").hide();
		$(".gwc-jiag span").text(zongjg);
		$(".gwc-jiag i").text(" * "+rate+"%");
		$(".gwc-jiag").show();
		
		if(zongjg>= parseInt($(".gwc-tishi").attr("mmin"))){
			$(".gwc-tishi").hide();
			$(".gwc-btn").show();
		}
		
		//左侧数字
		var zcnum = $(".fenlei-left li a[ttype="+type+"] span");
		if(zcnum.length>0){
			zcnum.text(parseInt(zcnum.text())+1);
		}else{
			var zchtm = '<span class="flspan">1</span>';
			$(".fenlei-left li a[ttype="+type+"]").append(zchtm);
		}
		listcookie();
		if($(this).parent().data("num") == '0' && $(this).parent().attr("tipe") == '0'){
			alert("今日已售罄，可预订明日");
			$(this).parent().attr("tipe","1");
		}
	});
	
	$(document).on("click",".fenlei-list .jian",function(){
		var num = parseInt($(this).siblings(".num").text())-1;
		var type = $(this).parents(".itype").attr("mytype");
		var stype = $(this).parents("li").attr("stype");
		var obj = $("#orderlist li[ttype='"+type+"'][stype='"+stype+"']");
		var jg = parseFloat($(this).parents("li").find(".fenlei-jg span").text());
		obj.find(".num").text(num);
		
		if(num>0){
			$(this).siblings(".num").text(num);
		}else{
			$(this).siblings(".num").text(num);
			obj.remove();
			$(this).parent().addClass("wm-hide");
		}
	
		//购物车数量
		if(parseInt($(".gwc .gwc-num").text())-1>0){
			var gwcnum = parseInt($(".gwc .gwc-num").text())-1;
			$(".gwc .gwc-num").text(gwcnum);
		}else{
			$(".gwc .gwc-num").remove();
		}
		//价格
		var zongjg = (parseFloat($(".gwc-jiag span").text())-jg).toFixed(2);
		$(".gwc-jiag span").text(zongjg);
		$(".gwc-jiag i").text(" * "+rate+"%");
		if(zongjg == 0){
			$(".qisongtis").show();
			$(".gwc-jiag").hide();
			$(".i-foot").removeClass("gwclisshow");
			$(".foot-fix").hide();
		}
		if(zongjg< parseInt($(".gwc-tishi").attr("mmin"))){
			$(".gwc-tishi").show();
			$(".gwc-btn").hide();
		}
		
		//左侧数字
		var zcnum = parseInt($(".fenlei-left li a[ttype="+type+"] span").text());
		if(zcnum-1>0){
			$(".fenlei-left li a[ttype="+type+"] span").text(zcnum-1);
		}else{
			$(".fenlei-left li a[ttype="+type+"] span").remove();
		}
		listcookie();
	});
	
	$(document).on("click",".gwc",function(){
		if(!$(".i-foot").hasClass("gwclisshow") && $(this).find("span").length>0){
			$(".i-foot").addClass("gwclisshow");
			$(".foot-fix").show();
		}else{
			$(".i-foot").removeClass("gwclisshow");
			$(".foot-fix").hide();
		}
	});
	$(document).on("click",".foot-fix",function(){
		$(".i-foot").removeClass("gwclisshow");
		$(".foot-fix").hide();
	});
	
	//清空
	$(document).on("click",".cleargwc",function(){
		$(".flspan").remove();
		$(".gwc-num").remove();
		$(".gwc-btn").hide();
		$(".gwc-tishi").show();
		$(".gwc-jiag").hide();
		$(".gwc-jiag span").text(0);
		$(".qisongtis").show();
		$("#orderlist").html("");
		$(".gwclisshow").removeClass("gwclisshow");
		$(".foot-fix").hide();
		$(".num").text(0);
		$(".wm-cz").addClass("wm-hide");
		listcookie();
	});
	
	$(document).on("click","#orderlist .jia",function(){
		var num = parseInt($(this).siblings(".num").text())+1;
		$(this).siblings(".num").text(num);
		
		var type = $(this).parents("li").attr("ttype");
		var stype = $(this).parents("li").attr("stype");
		var obj = $(".fenlei-list li[ttype='"+type+"'][stype='"+stype+"']");
		//价格
		var jg = parseFloat($(this).parents("li").find(".shop-jg span").text());
		//名称
		var name = $(this).parents("li").find(".shop-title").text();
		
		obj.find(".wm-cz .num").text(num);
		
		//购物车数量
		var gwcnum = parseInt($(".gwc .gwc-num").text())+1;
		$(".gwc .gwc-num").text(gwcnum);
		
		
		//价格
		var zongjg = (jg+parseFloat($(".gwc-jiag span").text())).toFixed(2);
		$(".qisongtis").hide();
		$(".gwc-jiag span").text(zongjg);
		$(".gwc-jiag i").text(" * "+rate+"%");
		$(".gwc-jiag").show();
		
		if(zongjg>= parseInt($(".gwc-tishi").attr("mmin"))){
			$(".gwc-tishi").hide();
			$(".gwc-btn").show();
		}
		
		//左侧数字
		var zcnum = $(".fenlei-left li a[ttype="+type+"] span");
		zcnum.text(parseInt(zcnum.text())+1);
		listcookie();
	});
	
	
	$(document).on("click","#orderlist .jian",function(){
		var num = parseInt($(this).siblings(".num").text())-1;
		var type = $(this).parents("li").attr("ttype");
		var stype = $(this).parents("li").attr("stype");
		var obj = $(".fenlei-list li[ttype='"+type+"'][stype='"+stype+"']");
		var jg = parseFloat($(this).parents("li").find(".shop-jg span").text());
		obj.find(".wm-cz .num").text(num);
		
		if(num>0){
			$(this).siblings(".num").text(num);
		}else{
			$(this).parents("li").remove();
			$(this).siblings(".num").text(num);
			obj.find(".wm-cz").addClass("wm-hide");
		}
	
		//购物车数量
		if(parseInt($(".gwc .gwc-num").text())-1>0){
			var gwcnum = parseInt($(".gwc .gwc-num").text())-1;
			$(".gwc .gwc-num").text(gwcnum);
		}else{
			$(".gwc .gwc-num").remove();
		}
		//价格
		var zongjg = (parseFloat($(".gwc-jiag span").text())-jg).toFixed(2);
		$(".gwc-jiag span").text(zongjg);
		$(".gwc-jiag i").text(" * "+rate+"%");
		if(zongjg == 0){
			$(".qisongtis").show();
			$(".gwc-jiag").hide();
			$(".i-foot").removeClass("gwclisshow");
			$(".foot-fix").hide();
		}
		if(zongjg< parseInt($(".gwc-tishi").attr("mmin"))){
			$(".gwc-tishi").show();
			$(".gwc-btn").hide();
		}
		
		//左侧数字
		var zcnum = parseInt($(".fenlei-left li a[ttype="+type+"] span").text());
		if(zcnum-1>0){
			$(".fenlei-left li a[ttype="+type+"] span").text(zcnum-1);
		}else{
			$(".fenlei-left li a[ttype="+type+"] span").remove();
		}
		
		listcookie();
	});
	
	$(".gwc-btn").click(function(){
		window.location.href = "/h5/h5order";
	});
})

function listcookie(){
	var datalist = [];
	$("#orderlist li").each(function(){
		var thisdata = {};
		thisdata.ttype = $(this).attr("ttype");
		thisdata.stype = $(this).attr("stype");
		thisdata.title = $(this).find(".shop-title").text();
		thisdata.price = $(this).find(".shop-jg span").text();
		thisdata.num = $(this).find(".num").text();
		datalist.push(thisdata);
	});
	//document.cookie = "datalist="+ datalist;
	
	sessionStorage.setItem("datalist",  JSON.stringify(datalist) );
}

function initlist(rate){
	var dalis =  sessionStorage.getItem("datalist");
	if(dalis){
		dalis = eval(dalis);
		console.log(dalis);
		var htm  = '';
		var gwcnum = 0;
		var zongjg = 0;
		for(var i=0;i<dalis.length;i++){
			var item = dalis[i];
			var zcnum = $(".fenlei-left li a[ttype="+item.ttype+"] span");
			gwcnum = gwcnum + parseInt(item.num);
			zongjg = zongjg + parseFloat(item.price)*parseInt(item.num);
			if(zcnum.length>0){
				zcnum.text(parseInt(zcnum.text())+parseInt(item.num));
			}else{
				var zchtm = '<span class="flspan">'+item.num+'</span>';
				$(".fenlei-left li a[ttype="+item.ttype+"]").append(zchtm);
			}
			
			var listitem = $(".fenlei-list li[stype="+item.stype+"]");
			listitem.find(".wm-cz").removeClass("wm-hide");
			listitem.find(".num").text(item.num);
			
			 htm = htm+'<li ttype="'+item.ttype+'" stype="'+item.stype+'">'+
						'<div class="wm-cz">'+
							'<span class="jian"></span> '+
							'<span class="num">'+item.num+'</span> '+
							'<span class="jia"></span>'+
						'</div>'+
						'<div class="shop-jg">¥<span>'+item.price+'</span></div>'+
						'<p class="shop-title">'+item.title+'</p>'+
					'</li>';
			
			
		}
		
		if(gwcnum!=0){
			var gwchtm = '<span class="gwc-num">'+gwcnum+'</span>';
			$(".gwc").append(gwchtm);
			zongjg = zongjg.toFixed(2);
			$(".qisongtis").hide();
			$(".gwc-jiag span").text(zongjg);
			$(".gwc-jiag i").text(" * "+rate+"%");
			$(".gwc-jiag").show();
			if(zongjg>0){
				$(".gwc-tishi").hide();
				$(".gwc-btn").show();
			}
		}
		$("#orderlist").append(htm);
	}

}
