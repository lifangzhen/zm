<%--更改会员信息 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="/mlm/updateMember/json" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="openid" value="${member.id}">
			<p>
				<label>联系人:</label>
				<input type="text" name="trueName" value="${member.trueName }" class="required"  maxlength="10"  size="30" >
			</p>
			<p>
				<label>联系电话:</label>
				<input type="text"  name="phone" value="${member.phone }"  class="required phone"  size="30" >
			</p>
			<p>
				<label >联系地址:</label>
				<input name="addr" value="${member.addr }"  class="required"  maxlength="100" size="30" />
			</p>
			<p style="width:99%">
				<label>会员等级：</label>
				<select name="gradeId" class="required combox">
					<option value="">-请选择-</option>
					<c:forEach items="${gradeList}" var="g">
						<option value="${g.id}" <c:if test="${g.id eq member.gradeId }">selected="selected"</c:if>>
							${g.name}
						</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</p>
			<p style="width:99%;height:50px;">
				<label>预存：</label>
				<input type="text" value="${member.prepay }" name="prepay" class="number"  size="30" >
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>