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
	<form id="form" method="post" action="/mlm/addDish/json"  enctype="multipart/form-data"  class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogCallBack);">
		<div class="pageFormContent" layoutH="60">
			<p style="width:99%">
				<label></label>
				<span style="color:red;">提示：排序值越小越靠前！</span>
			</p>
			<input type="hidden"  name="id" value="${dish.id }"/>
			<p style="width:99%">
				<label>菜品名称：</label>
				<input type="text" name="name" value="${dish.name }" class="required " maxlength="30" size="30" />
			</p>
			<p style="width:99%">
				<label>菜品展示：</label>
				<div class="unit"  style="float:left; width:120px; padding:0 130px;">
					<div class="upload-wrap" >
						<div class="thumbnail"><img width="300" src="${dish.pic}" alt="" > </div>
						<input type="file" name="myfile" class="upFile"  accept="image/*">
					</div>
				</div>
			</p>
			<p style="width:99%">
				<label>类别：</label>
				<select name="categoryId" class="required combox">
					<option value="">-请选择-</option>
					<c:forEach items="${cateList}" var="c">
						<option value="${c.id}" <c:if test="${c.id eq dish.categoryId }">selected="selected"</c:if>>
							${c.name}
						</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</p>
			<p style="width:99%">
				<label>价格：</label>
				<input type="price" id="priceStr" value="${dish.price }" name="priceStr" class="required number" minlength="1"  maxlength="20" size="30" />
				<span class="info"></span>
			</p>
			<p style="width:99%">
				<label>单位：</label>
				<input type="unit" name="unit" value="${dish.unit }"  class="required"  minlength="1"   maxlength="4" size="30" />
				<span class="info"></span>
			</p>
			<p style="width:99%">
				<label>排序：</label>
				<input name="numberStr" class="required digits" value="${dish.number }" minlength="1" maxlength="45" size="30" />
			</p>
			<p style="width:99%">
				<label>库存：</label>
				<input type="text" name="stockStr" class="intnum" value="${dish.stock }"  size="30" />-1表示库存无限
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