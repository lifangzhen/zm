var _url = "http://wx.herison.com.cn";
var user = "";
$(function(){
	var openid = localStorage.getItem("openid");
	$.ajax({
        type: "POST",
        async: false,
        data: {
        	openid : openid
        },
        url:  '/h5/core/json',
       	dataType:"json", 
        success: function(data) {
        	console.log(data);
        	user = data;
        }
    });
    
});

function load(){
	var htm = '<div class="loding"></div>';
	$("body").append(htm);
}

function hideload(){
	$(".loding").remove();
}
