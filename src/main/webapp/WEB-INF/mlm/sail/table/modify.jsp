<%-- 批量修改库存 --%>
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
	<form id="form" method="post" action="/mlm/modifyStock/json" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<c:forEach var="d" items="${list}">
				<p style="width:99%">
					<label>${d.name }：</label>
					<input type="text" name="${d.id }"  class="required intnum"  class="intnum" value="${d.stock }"  size="30" />
				</p>
			</c:forEach>
			
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