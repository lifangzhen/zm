<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/gradeUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/mlm/addGradeUi" target="dialog" rel="input" title="添加会员等级"><span>添加会员等级</span></a></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>等级名称</th>
				<th>获取条件(充值金额)</th>
				<th>享受折扣%</th>
				<th>设置</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="grade" items="${list}">
				<tr target="sid_user" rel="${grade.id}">
					<td>${grade.name}</td>
				    <td>${grade.prepay}</td>
				    <td>${grade.rate}%</td>
					<td>
							&nbsp;[<a href="/mlm/addGradeUi?id=${grade.id}" title="修改"  callback="dialogAjax" target="dialog" rel="input" style="color:blue" >修改</a>]&nbsp;&nbsp;
							&nbsp;[<a href="/mlm/deleteGrade/json?id=${grade.id}" target="ajaxTodo"  rel="input" title="确定要删除${grade.name }?" style="color:blue">删除</a>]
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
<%--     <%@include file="/page/inc/pageBar.jsp"%> --%>
</div>
