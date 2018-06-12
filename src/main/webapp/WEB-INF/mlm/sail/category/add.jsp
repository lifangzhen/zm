<%-- 添加类别 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<style>
<!--
.pageFormContent fieldset label{
	width: 200px;
}
-->
</style>
<div class="pageContent">
	<form id="form" method="post" action="/mlm/addCategory/json" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<p style="width:99%">
				<label></label>
				<span style="color:red;">提示：排序值越小越靠前！</span>
			</p>
			<input type="hidden"  name="id" value="${cate.id }"/>
			<p style="width:99%">
				<label>类别名称：</label>
				<input type="text" name="name" class="required " value="${cate.name }" maxlength="8" size="30" />
			</p>
			<p style="width:99%">
				<label>类别排序：</label>
				<input name="numberStr" class="required digits" value="${cate.number }" minlength="1" maxlength="45" size="30" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitForm() {
		var str = "";
		$(":checkbox:checked").each(function() {
			if ($(this).hasClass('selectOperatorRole')){
				// 加样式判断，避免与其他复选框冲突
				str += $(this).val() + ",";
			}
		});
		$("#selectVal").val(str);
		
		$("#form").submit();
	}
	
	$(function() {
	});
</script>