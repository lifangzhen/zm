<%-- 添加会员等级 --%>
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
	<form id="form" method="post" action="/mlm/addGrade/json" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<p style="width:99%">
				<label></label>
				<span style="color:red;">提示：折扣越低说明等级越高</span>
			</p>
			<input type="hidden"  name="id" value="${grade.id }"/>
			<p style="width:99%">
				<label>等级名称：</label>
				<input type="text" name="name" class="required " value="${grade.name }" maxlength="8" size="30" />
			</p>
			<p style="width:99%">
				<label>需充值金额(元)：</label>
				<input name="prepayStr" class="required number" value="${grade.prepay }" minlength="1" maxlength="8" size="30" />
			</p>
			<p style="width:99%">
				<label>享受折扣：</label>
				<input name="rateStr" class="required digits" value="${grade.rate }" minlength="1" maxlength="3" size="30" />%
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
		$("#form").submit();
	}
	
	$(function() {
	});
</script>