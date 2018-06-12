$(function(){

	$(".comment textarea").bind('input propertychange', function() {
	    if($(this).val().length > 0){
	    	$(".subcomment").removeClass("disabled");
	    }else{
	    	$(".subcomment").addClass("disabled");
	    }
	});

	$(".subcomment").click(function(){
		var text = $(".comment textarea").val();
		var imgs = [];
		$(".preview").each(function(){
			imgs.push($(this).attr("src"));
		})
		var data = {
			commentText : text,
			imgs : imgs
		}
		console.log(data);
		//提交留言数据
		$.ajax({
		    type: "POST",
		    url: "url",
		    data: data,
		    dataType: "json",
		    success: function(data){
		    	location.href = "/mark.html";
		    },
		    error:function(){
		    	alert("加载失败,请重新加载")
		    }

	     });
	})

	clickImg = function(obj){
	   $(obj).parent().find('.upload_input').click();
	}
	deleteImg = function(obj){
	    $(obj).parent().find('input').val('');
	    $(obj).parent().find('img.preview').attr("src","");
	    //IE9以下
	    $(obj).parent().find('img.preview').css("filter","");
	    $(obj).hide();
	    $(obj).parent().find('.addImg').show();
	}
	 
})

function change(file) {
  //预览
  var pic = $(file).parent().find(".preview");
  //添加按钮
  var addImg = $(file).parent().find(".addImg");
  //删除按钮
  var deleteImg = $(file).parent().find(".delete");

  var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();

   if(ext!='png'&&ext!='jpg'&&ext!='jpeg'){
      if (ext != '') {
        alert("图片的格式必须为png或者jpg或者jpeg格式！"); 
      } 
       return;
   }
   //判断IE版本
   var isIE = navigator.userAgent.match(/MSIE/)!= null,
       isIE6 = navigator.userAgent.match(/MSIE 6.0/)!= null;
       isIE10 = navigator.userAgent.match(/MSIE 10.0/)!= null;
   if(isIE && !isIE10) {
      file.select();
      var reallocalpath = document.selection.createRange().text;
       if (isIE6) {
          pic.attr("src",reallocalpath);
       }else{
          pic.css("filter","progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + reallocalpath + "\")");
          pic.attr('src','data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==');             
       }
       addImg.hide();
       deleteImg.show();
   }else {
      html5Reader(file,pic,addImg,deleteImg);
   }
}

function html5Reader(file,pic,addImg,deleteImg){
	    var file = file.files[0];
	    var reader = new FileReader();
	    reader.readAsDataURL(file);
	    reader.onload = function(e){
	         pic.attr("src",this.result);
	    }
	    addImg.hide();
	    deleteImg.show();
	}