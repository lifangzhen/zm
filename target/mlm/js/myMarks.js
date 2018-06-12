var fakeData = [{
	avart:"./assets/avart.jpg",
	nickName:"确认过眼神",
	content:"遇上对的人",
	imgs:["./assets/comimg.jpg","./assets/comimg.jpg","./assets/comimg.jpg"],
	fav: 456,//点赞数量
	message:7654// 留言数量
}];

var page = 1;//加载留言页码

$(function(){

    $(".bottom .row > div").css("width","33.3%");
    
	$(document).endlessScroll({//滚动加载数据
	    fireOnce: false,
	    fireDelay: false,
	    loader: "<div class=>加载中...<div>",
	    callback: function(p){
	        addMarks(fakeData);//测试加载
	        // loadData();//获取数据;
	    }
	});

	$(document).on("click",".fav",function(){//点赞
		var src = $(this).find("img").attr("src");
		var fav = src.indexOf("active") > -1;
		var num = +$(this).find("span").html();
		if(fav){
			$(this).find("img").attr("src","./assets/fav.png");
			$(this).find("span").html(--num);
		}else{
			$(this).find("img").attr("src","./assets/fav_active.png");
			$(this).find("span").html(++num);
		};

		// $.ajax({//点赞 执行的ajax
		// 	type: "GET",
		//     url: "url",
		//     data: {comId: page,type:""},
		//     dataType: "json",
		//     success: function(data){

		//     },
		//     error:function(){
		//     	alert("加载失败,请重新加载")
		//     }
		// });

	});
	$(document).on("click",".complain",function(){//点击投诉
		var src = $(this).find("img").attr("src");
		var complain = src.indexOf("active") > -1;
		if(complain){
			$(this).find("img").attr("src","./assets/complain.png");
		}else{
			$(this).find("img").attr("src","./assets/complain_active.png");
			alert("投诉成功!")
		};
	})
})

function loadData(){//加载留言数据
	var type = $(".type.active").attr("type");//获取评论类型 最新 或者最热

	$.ajax({
	    type: "GET",
	    url: "url",
	    data: {page: page,size: 10,type:type},
	    dataType: "json",
	    success: function(data){
	    	page++
	        
	        addMarks(data);
	    },
	    error:function(){
	    	alert("加载失败,请重新加载")
	    }

     });
}

function addMarks(data){//添加留言数据到页面
	if(!Array.isArray(data)) return;
	var html = "";
	data.forEach(function(item,i){
		var imgs = "";
		if(!Array.isArray(item.imgs)) return;
		item.imgs.forEach(function(img,j){
			imgs += '<img src='+img+'>';
		});
		html += '<div class="comitem"> '+
			        '<div class="comhead"> '+
			          '<div class="avart"> '+
			            '<img src="'+ item.avart +'"> '+
			          '</div> '+
			          '<div class="nickname">'+ item.nickName +'</div> '+
			        '</div> '+
			        '<div class="combody"> '+
			          '<div class="comcontent"></div> '+
			          '<div class="comimg"> '+
			            imgs+
			          '</div> '+
			        '</div> '+
			        '<div class="comfoot"> '+
			          '<div class="footbut share"> '+
			            '<img src="./assets/share.png" style="width:16px;height:16px;"> <span>转发</span> '+
			          '</div> '+
			          '<div class="footbut message"> '+
			            '<img src="./assets/message.png"> <span>'+ item.message+'</span> '+
			          '</div> '+
			          '<div class="footbut fav"> '+
			            '<img src="./assets/fav.png" style="vertical-align: bottom;"> <span>'+ item.fav +'</span> '+
			          '</div> '+
			          '<div class="footbut complain"> '+
			            '<img src="./assets/complain.png" style="width:16px;height:16px;"> <span>投诉</span> '+
			          '</div> '+
			        '</div> '+
		       '</div>';
	})
	$("#comsdiv").append(html);
}

