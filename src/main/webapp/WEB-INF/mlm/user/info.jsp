<%-- 操作员查看自己帐号信息 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form>
		<div class="pageFormContent" layoutH="60">
			<p style="width:99%">
				<label>登录名：</label>
				${loginName }
			</p>
			<p style="width:99%">
				<label>联系人：</label>
				${linkMan}
			</p>
			<p style="width:99%">
				<label>联系人手机：</label>
				${linkMobile}
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>