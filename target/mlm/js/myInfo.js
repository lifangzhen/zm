$(function(){
	$(".bottom .row > div").css("width","33.3%");
	$("input").focus(function(){
		$(".bottom").hide();
	})
	$("input").blur(function(){
		$(".bottom").show();
	})

	var mobileSelect1 = new MobileSelect({
	    trigger: '#sex',
	    title: '选择性别',
	    wheels: [
	                {data: ["男","女"]}
	            ],
	    position:[0],
	    transitionEnd:function(indexArr, data){
	    },
	    callback:function(indexArr, data){
	        console.log(data);
	    }
	});

	$("button").click(function(){//保存个人数据
		if(!$("input").eq(0).val()) alert("请填写姓名");
		var data = {
			name:$("input").eq(0).val(),
			sex:$("#sex").text(),
			location:$("input").eq(1).val(),
			phone: $("input").eq(2).val()
		};

		$.ajax({
		    type: "GET",
		    url: "url",
		    data: data,
		    dataType: "json",
		    success: function(data){
		    	window.location.href = "./my.html";
		    },
		    error:function(){
		    	alert("保存失败,请检查网络");
		    	window.location.href = "./my.html";
		    }

	    });
	})
})