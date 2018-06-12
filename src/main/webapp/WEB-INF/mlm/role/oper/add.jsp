<%-- 添加操作员 --%>
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
	<form id="form" method="post" action="/m/addOper/json" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<p style="width:99%">
				<label>登录名：</label>
				<input type="text" name="loginName" class="required " maxlength="30" size="30" />
			</p>
			<p style="width:99%">
				<label>密码：</label>
				<input type="password" id="pwd" name="pwd" class="required" maxlength="20" size="30" />
				<span class="info"></span>
			</p>
			<p style="width:99%">
				<label>确认密码：</label>
				<input type="password" name="pwd2" class="required"  equalTo="#pwd"  maxlength="20" size="30" />
				<span class="info"></span>
			</p>
			<p style="width:99%">
				<label>操作员姓名：</label>
				<input name="linkMan" class="required" minlength="2" maxlength="45" size="30" />
			</p>
			<p style="width:99%">
				<label>手机号：</label>
				<input name="linkMobile" type="linkMobile" class="required mobile" maxlength="12" size="30" />
			</p>
			<p style="width:99%">
				<label>角色：</label>
				<select name="role" class="required combox">
					<option value="">-请选择-</option>
					<c:forEach items="${roleList}" var="role">
						<option value="${role.id}">
							${role.name}
						</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
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