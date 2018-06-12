$(function(){
	$(".bottom .row > div").css("width","33.3%");

	$('.list-li').touchWipe({itemDelete: '.btn'});
	$(document).on("click",".btn",function(){
		$(this).parent().remove();
	})
})