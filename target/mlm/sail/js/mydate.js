var HourScroll = "";
var DayScroll = "";
var hourht = '';
$(function(){
	var indexY ="";
	//$("#daywrapper .optionsData-wrapper")
	dathtml();
	
	HourScroll = new iScroll("Hourwrapper",{snap:"a",vScrollbar:false,
        onScrollEnd:function () {
              indexY = (this.y/40)*(-1)+1;
              $("#Hourwrapper .optionsData-wrapper a").eq(indexY-1).addClass("is-checked").siblings().removeClass("is-checked");
             
    	}
   });
	DayScroll = new iScroll("daywrapper",{snap:"a",vScrollbar:false,
        onScrollEnd:function () {
            indexY = (this.y/40)*(-1)+1;
            $("#daywrapper .optionsData-wrapper a").eq(indexY-1).addClass("is-checked").siblings().removeClass("is-checked");
            hourdate();
            HourScroll.refresh()
            HourScroll.scrollTo(0, 0);
    	}
   });
   
   $(".scroll-select-confirm").click(function(){
   		var dval = $("#daywrapper .is-checked").attr("label");
   		var dvaltime = $("#daywrapper .is-checked").data("id");
   		var mval = $("#Hourwrapper .is-checked").attr("label");
   		if(mval == "立即送达"){
   			$("#songdatime").text(mval);
   			var datea = new Date();
   			var h = datea.getHours();
   			var i = datea.getMinutes();
   			$("#songdatime").attr("mytime",dvaltime+" "+h+":"+i);
   			isdaytime = 100;
   		}else{
   			if(mval == "今日"){
   				isdaytime = 100;
   			}else{
   				isdaytime = 90;
   			}
   			$("#songdatime").text(dval+" "+mval);
   			$("#songdatime").attr("mytime",dvaltime+" "+mval);
   		}
   		comPrice();
   		console.log(dvaltime);
   		$(".scroll-select-container").removeClass("scroll-show");
   		
   });
   $("#songdatime").click(function(){
   		$(".scroll-select-container").addClass("scroll-show");
   });
   
   
	initdate();

   
})

function daydate(num){
	var datea = new Date();
	var dates = new Date(datea.getTime()+num*24*60*60*1000);
	var y = dates.getFullYear();
	var m  = dates.getMonth()+1;
	var d = dates.getDate();
	if(m<10){
		m = "0"+m;
	}
	if(d<10){
		d = "0"+d;
	}
	return y+"-"+m+"-"+d;
	
}

function dathtml(){
	var htm = "";
	for(var i=0;i<3;i++){
		if(i==0){
			htm = '<a class="is-checked" data-option="" data-id="'+daydate(i)+'" id="'+daydate(i)+'" label="今日">今日</a>';
		}else{
			htm = htm + '<a class="" data-option="" data-id="'+daydate(i)+'" id="'+daydate(i)+'" label="'+daydate(i)+'">'+daydate(i)+'</a>';
		}
	}
	$("#daywrapper .optionsData-wrapper").html(htm);
	hourdate();
}

function hourdate(){
	var thisday = $("#daywrapper .is-checked").text();
	if(thisday == "今日"){
		hourht = "";
		hourht  = '<a class="is-checked" data-option="" data-id="0" id="0" label="立即送达">立即送达</a>';
		hourhtml(true);
	}else{
		hourht = "";
		hourhtml();
	}
}

function hourhtml(type){
	var datea = new Date();
	var h = datea.getHours();
	var i  = datea.getMinutes();
	var hs = "";
	var is = "";
	if(h<10){
		hs = 0+"hs";
	}
	if(is){
		is = 0+"is";
	}
	if(type){
		console.log(h);
		for(var y = h; y<22;y++){
			
			if(y==h && i<30){
				hourht = hourht+'<a class="" data-option="" data-id="'+y+':30" id="'+y+':30" label="'+y+':30">'+y+':30</a>';
			}else{
				hourht = hourht+'<a class="" data-option="" data-id="'+y+':00" id="'+y+':00" label="'+y+':00">'+y+':00</a>';
				hourht = hourht+'<a class="" data-option="" data-id="'+y+':30" id="'+y+':30" label="'+y+':30">'+y+':30</a>';
			}
		}
	}else{
		for(var y = 9; y<=22;y++){
			if(y==22){
				hourht = hourht+'<a class="" data-option="" data-id="'+y+':00" id="'+y+':00" label="'+y+':00">'+y+':00</a>';
			}else{
				hourht = hourht+'<a class="" data-option="" data-id="'+y+':00" id="'+y+':00" label="'+y+':00">'+y+':00</a>';
				hourht = hourht+'<a class="" data-option="" data-id="'+y+':30" id="'+y+':30" label="'+y+':30">'+y+':30</a>';
			}
		}
	}
	$("#Hourwrapper .optionsData-wrapper").html(hourht);
	
}


function initdate(){
	var dvaltime = $("#daywrapper .is-checked").data("id");
	var datea = new Date();
	var h = datea.getHours();
	var i = datea.getMinutes();
	$("#songdatime").attr("mytime",dvaltime+" "+h+":"+i);
}