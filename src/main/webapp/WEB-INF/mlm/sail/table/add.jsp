<%-- 添加菜品 --%>
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
	<form id="form" method="post" action="/mlm/addTable/json"  enctype="multipart/form-data"  class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogCallBack);">
		<div class="pageFormContent" layoutH="60">
			<p style="width:99%">
				<label></label>
				<span style="color:red;"></span>
			</p>
			<input type="hidden"  name="id" value="${table.id }"/>
			<p style="width:99%">
				<label>桌号：</label>
				<input type="text" name="num" value="${table.num }" class="required " maxlength="30" size="30" />
			</p>
			<p style="width:99%">
				<label>门店：</label>
				<select name="storeId" class="required combox">
					<option value="">-请选择-</option>
					<c:forEach items="${storeList}" var="c">
						<option value="${c.id}" <c:if test="${c.id eq table.storeId }">selected="selected"</c:if>>
							${c.name}
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
	
	function dialogCallBack(json){
		$.pdialog.closeCurrent();
// 		$.pdialog.reload("/mlm/dishUi", { data: {}, dialogId: "dishUi", callback: null })
	}
	
	$(function() {
	});
</script>