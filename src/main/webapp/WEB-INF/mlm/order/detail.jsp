<%-- 订单详情 --%>
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="60">
		<thead>
			<tr>
				<th>菜品名称</th>
				<th>单价</th>
				<th>购买数量</th>
				<th>单位</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="d" items="${list}">
				<tr target="sid_user" rel="${d.id}">
					<td>${d.dishName}</td>
					<td>${d.price}</td>
					<td>${d.dishNum}</td>
					<td>${d.unit}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
</div>