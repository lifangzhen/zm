var _url = "http://121.40.216.161:28080";
var datasval = {};
var isin = false;

function Minit(){
	getMenu();
	if(isin){
		return;
	}
	isin = true;
	
	initevent();
	
}
function setvaldate(id) {
	var item = datasval[id];
	$(".js_menu_name").val(item.name);
	if(item.type == "text") {
		$(".frm_radio_label").eq(0).addClass("selected").siblings().removeClass("selected");
		$(".tab_content").eq(0).show().siblings().hide();
		$(".tab_appmsg").addClass("selected");
		$(".tab_img").removeClass("selected");
		$(".menu_content").hide();
		$(".menu_content_container").show();
		$("#urlText").val("");
		$(".appmsgSendedItem").hide();
		$(".create_access").show();
		$(".inner textarea").val(item.content);

	} else if(item.type == "image") {
		$(".frm_radio_label").eq(0).addClass("selected").siblings().removeClass("selected");
		$(".tab_content").eq(1).show().siblings().hide();
		$(".menu_content").hide();
		$(".menu_content_container").show();
		$(".tab_appmsg").removeClass("selected");
		$(".tab_img").addClass("selected");
		if(item.content != "") {
			$(".appmsgSendedItem img").attr("src", item.content);
			$(".appmsgSendedItem").show();
			$(".create_access").hide();
		} else {
			$(".appmsgSendedItem").hide();
			$(".create_access").show();
		}
		$("#src").val(item.content);
		$(".inner textarea").val("");
		$("#urlText").val("");
	} else if(item.type == "view") {
		$(".frm_radio_label").eq(1).addClass("selected").siblings().removeClass("selected");
		$(".menu_content").show();
		$(".menu_content_container").hide();
		$("#urlText").val(item.content);
		$(".inner textarea").val("");
		$(".appmsgSendedItem").hide();
		$(".create_access").show();
	}
}
function readFile(obj){   
    var file = obj.files[0];      
    //判断类型是不是图片  
    if(!/image\/\w+/.test(file.type)){     
            alert("请确保文件为图像类型");   
            return false;   
    }   
    var reader = new FileReader(); 
    var types = file.type;
    reader.readAsDataURL(file);   
    reader.onload = function(e){   
    	// alert(this.result); //就是base64  
    	$.ajax({
			type: "POST",
			data: {
				data : $.trim(this.result),
				fileext: types.split("/")[1]
			},
			url:  "/h5/uploadPic/json",
			dataType: "json",
			success: function(data) {
				console.log(data);
				$(".appmsgSendedItem").show();
				$(".appmsgSendedItem img").attr("src",data.result.imgurl);
				$(".create_access").hide();
				$("#src").val(data.result.imgurl);
			}
		});
       
    }   
}   
  
/*function ajaxFileUpload() {
	$.ajaxFileUpload({
		url: '/h5/uploadPic/json', //用于文件上传的服务器端请求地址 
		secureuri: false, //是否需要安全协议，一般设置为false 
		fileElementId: "file", //文件上传域的ID 
		success: function(data, status) //服务器成功响应处理函数 
		{
			console.log(data);
		},
		error: function(data, status, e) //服务器响应失败处理函数 
		{
			alert(e);
		}
	})
	return false;
}*/

function submitform(){
	var type = $("#type").val();
	var id = $("#id").val();
	var father = $("#father").val();
	var level = $("#level").val();
	var number = "";
	var content = " ";
	var url = "";
	var caozuo = $("#caozuo").val();
	if(type == "text") {
		content = $(".inner textarea").val();
	} else if(type == "view") {
		content = $("#urlText").val();
	} else if(type == "image") {
		content = $("#src").val();
	}
	if(level == "1") {
		number = $(".pre_menu_item").length;
	} else {
		number = $(".pre_menu_item.selected").find(".current").length;
	}
	var data = {
		father: father,
		level: level,
		number: number,
		name: $(".js_menu_name").val(),
		type: type,
		content: content
	};
	if(caozuo == "update") {
		data.id = id;
		url = "/h5/updateMenu/json";
	} else {
		url = "/h5/addMenu/json";
	}

	$.ajax({
		type: "POST",
		data: data,
		url:  url,
		dataType: "json",
		success: function(data) {
			//location.reload(); 
			$(".pre_menu_item").remove();
			$(".menu_form_area").hide();
			getMenu();
		}
	});
}

function initdata(){
	$(".frm_radio_label").eq(0).addClass("selected").siblings().removeClass("selected");
	$(".tab_content").eq(0).show().siblings().hide();
	$(".tab_appmsg").addClass("selected");
	$(".tab_img").removeClass("selected");
	$(".menu_content").hide();
	$(".menu_content_container").show();
	$("#urlText").val("");
	$(".appmsgSendedItem").hide();
	$(".create_access").show();
	$(".inner textarea").val("");
}


function getMenu(){
	$.ajax({
		type: "POST",
		url:  '/h5/getMenu/json ',
		dataType: "json",
		success: function(data) {
			var item = eval(data.result.menujson);
			console.log(item);
			if(item && item.length > 0) {
				$(".no_menu").removeClass("no_menu");
				var htm = '';
				var sizen = 2;
				if(item.length > 1) {
					sizen = 3;
					if(item.length == 3) {
						$(".js_addMenuBox.grid_item").hide();
					}
				}
				for(var i = 0; i < item.length; i++) {
					datasval[item[i].id] = item[i];
					var menuson = '';
					var menubtn = '<li class="js_addMenuBox">' +
						'<a href="javascript:void(0)" class="jsSubView js_addL2Btn">' +
						'<span class="sub_pre_menu_inner js_sub_pre_menu_inner">' +
						'<i class="icon14_menu_add"></i>' +
						'</span>' +
						'</a>' +
						'</li>';
					if(item[i].sub_button_list) {
						for(var j = 0; j < item[i].sub_button_list.length; j++) {
							var itemson = item[i].sub_button_list[j];
							datasval[itemson.id] = itemson;
							menuson = menuson + '<li class="jslevel2 current" data-id="' + itemson.id + '" data-fid="' + itemson.father + '">' +
								'<a href="javascript:void(0)" class="jsSubView">' +
								'<span class="js_l2Title">' + itemson.name + '</span>' +
								'</a>' +
								'</li>';
						}
						if(item[i].sub_button_list.length == 5) {
							menubtn = '';
						}
					}

					htm = htm + '<li class="pre_menu_item grid_item  size1of' + sizen + '" data-id="' + item[i].id + '">' +
						'<a href="javascript:void(0);" class="pre_menu_link">' +
						'<i class="icon20_common sort_gray"></i>' +
						'<span class="js_l1Title">' + item[i].name + '</span>' +
						'</a>' +
						'<div class="sub_pre_menu_box">' +
						'<ul class="sub_pre_menu_list">' +
						menuson +
						menubtn +
						'</ul>' +
						'<i class="arrow arrow_out"></i>' +
						'<i class="arrow arrow_in"></i>' +
						'</div>' +
						'</li>';

				}
				$(".js_addMenuBox.grid_item").before(htm);
			}
			console.log(datasval);
		}
	});
}

function initevent(){
	//一级菜单添加
	$(document).on("click",".js_addMenuBox .js_addL1Btn",function() {
		var classname = "";
		var obj = $(this);
		if($(".pre_menu_item").length == 0) {
			classname = "size1of2";
		} else {
			classname = "size1of3";
			$(".size1of2").removeClass("size1of2").addClass("size1of3");
		}
		$(".no_menu").removeClass("no_menu");
		$(".pre_menu_item").removeClass("selected");
		var htm = '<li class="pre_menu_item selected grid_item ' + classname + '">' +
			'<a href="javascript:void(0);" class="pre_menu_link">' +
			'<i class="icon20_common sort_gray"></i>' +
			'<span class="js_l1Title">菜单名称</span>' +
			'</a>' +
			'<div class="sub_pre_menu_box">' +
			'<ul class="sub_pre_menu_list">' +
			'<li class="js_addMenuBox">' +
			'<a href="javascript:void(0)" class="jsSubView js_addL2Btn">' +
			'<span class="sub_pre_menu_inner js_sub_pre_menu_inner">' +
			'<i class="icon14_menu_add"></i>' +
			'</span>' +
			'</a>' +
			'</li>' +
			'</ul>' +
			'<i class="arrow arrow_out"></i>' +
			'<i class="arrow arrow_in"></i>' +
			'</div>' +
			'</li>';
		if($(".pre_menu_item").length == 2) {
			$(".js_addMenuBox.grid_item").hide();
		}
		$(this).parent().before(htm);
		$("#father").val("");

		initdata();
		$("#level").val("1");
		$("#caozuo").val("add");
		$(".js_menu_name").val("菜单名称");
		$(".menu_form_area").show();
		$.ajax({
			type: "POST",
			data: {
				father: "",
				number: $(".pre_menu_item").length,
				level: "1",
				name: "菜单名称",
				type: "text",
				content: ""
			},
			url:  '/h5/addMenu/json',
			dataType: "json",
			success: function(datas) {
				obj.parent().siblings(".selected").data("id", datas.result.id);
				$("#id").val(datas.result.id);
				$("#father").val("");
				$("#level").val("1");
				$("#type").val("text");
				$("#src").val("");
				$("#caozuo").val("update");
			}
		});
		
	});

	//一级菜单点击
	$(document).on("click", ".pre_menu_item.grid_item .pre_menu_link", function() {
		var id = $(this).parent().data("id")
		$(".pre_menu_item.grid_item").removeClass("selected");
		$(this).parent().addClass("selected");
		$("#id").val(id);
		$("#father").val("");
		$(this).siblings(".sub_pre_menu_box").find(".selected").removeClass("selected");
		$("#level").val("1");
		$("#caozuo").val("update");
		$(".menu_form_area").show();
		
		if(datasval[id]){
			$("#type").val(datasval[id].type);
			setvaldate(id);
		}else{
			$(".js_menu_name").val("菜单名称");
			initdata();
		}
		
	});

	//二级菜单添加
	$(document).on("click", ".js_addMenuBox .js_addL2Btn", function() {
		var item = $(this).parents(".sub_pre_menu_list");
		var obj = $(this);
		$(this).parent().siblings().removeClass("selected");
		var htm = '<li class="jslevel2 selected current">' +
			'<a href="javascript:void(0)" class="jsSubView">' +
			'<span class="js_l2Title">子菜单名称</span>' +
			'</a>' +
			'</li>';
		if(item.find(".jslevel2").length == 4) {
			item.find(".js_addMenuBox").hide();
		}
		$(this).parent().before(htm);
		$("#father").val($(this).parents(".pre_menu_item").data("fid"));
		$("#level").val("2");
		$("#caozuo").val("add");
		$(".js_menu_name").val("子菜单名称");
		initdata();
		$.ajax({
			type: "POST",
			data: {
				father: $(this).parents(".pre_menu_item").data("id"),
				number: $(".pre_menu_item.selected").find(".current").length,
				level: "2",
				name: "子菜单名称",
				type: "text",
				content: ""
			},
			url:  '/h5/addMenu/json',
			dataType: "json",
			success: function(datas) {
				obj.parents(".sub_pre_menu_list").find(".selected").data("id", datas.result.id);
				obj.parents(".sub_pre_menu_list").find(".selected").data("fid", obj.parents(".pre_menu_item").data("id"));
				$("#id").val(datas.result.id);
				$("#father").val(obj.parents(".pre_menu_item").data("id"));
				$("#level").val("2");
				$("#type").val("text");
				$("#src").val("");
				$("#caozuo").val("update");
			}
		});
	});
	//二级菜单点击
	$(document).on("click", ".jslevel2", function() {
		$(this).addClass("selected").siblings().removeClass("selected");
		$("#id").val($(this).data("id"));
		$("#father").val($(this).data("fid"));
		$("#level").val("2");
		$("#caozuo").val("update");
		$(".menu_form_area").show();
		console.log(datasval[$(this).data("id")]);
		if(datasval[$(this).data("id")]){
			$("#type").val(datasval[$(this).data("id")].type);
			setvaldate($(this).data("id"));
		}else{
			$(".js_menu_name").val("子菜单名称");
			initdata();
		}
	});

	//文字图片切换
	$(document).on("click",".tab_nav_li",function() {
		var index = $(this).index();
		$(this).addClass("selected").siblings().removeClass("selected");
		$(".tab_content").eq(index).show().siblings().hide();
		$("#type").val($(this).data("type"));
	});

	//消息跳转链接切换
	$(document).on("click",".frm_radio_label",function() {
		$(this).addClass("selected").siblings().removeClass("selected");
		var type = $(this).data("type");
		if(type == "text") {
			$(".menu_content_container").show();
			$(".menu_content").hide();
			$("#type").val($(".frm_radio_label.selected").data("type"));
		} else {
			$(".menu_content_container").hide();
			$(".menu_content").show();
			$("#type").val("view");
		}
	});

	//删除
	$(document).on("click", "#jsDelBt", function() {
		$.ajax({
			type: "POST",
			data: {
				id: $("#id").val(),
				level: $("#level").val()
			},
			url:  "/h5/deleteMenu/json",
			dataType: "json",
			success: function(data) {
				if($(".pre_menu_list .selected").length>1){
					$(".jslevel2.selected").remove();
				}else{
					$(".js_addMenuBox.grid_item").show();
					$(".pre_menu_list .selected").remove();
				}
				$(".menu_form_area").hide();
				$(".pre_menu_list .selected").removeClass("selected");
			}
		});
	});
	//上传图片
	/*$("#file").change(function() {
		ajaxFileUpload();
	});*/
	//保存
	$(document).on("click","#submitbtn",function() {
		submitform();
	});
	
	//图片删除
	$(document).on("click","#delBtn",function(){
		$(".appmsgSendedItem").hide();
		$(".create_access").show();
		$("#src").val();
	});
	
	$("#pubBt").click(function(){
		$.ajax({
			type: "POST",
			data: {},
			url:  '/mlm/publish/json',
			dataType: "json",
			success: function(data) {
				if(data.statusCode=='200'){
					alert("发布成功");
				}else{
					alert("发布失败:"+data.message);
				}
			}
		});
	});
}
