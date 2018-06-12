<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/operatorUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
	</div>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/mlm/addOperUi" target="dialog"  height="450"  rel="input" title="添加操作员"><span>添加操作员</span></a></li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th width="120">登录名</th>
				<th>操作员</th>
				<th>联系电话</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="oper" items="${list}">
				<tr target="sid_user" rel="${oper.id}">
					<td>${oper.loginName}</td>
					<td>${oper.linkMan}</td>
				    <td>${oper.linkMobile}</td>
					<td>
						<c:if test="${oper.roleId!='superadmin'}">
			    	    	&nbsp;[<a href="/mlm/delOper/json?id=${oper.id}" target="ajaxTodo"  rel="input" title="确定要删除${oper.linkMan }?" style="color:blue">删除</a>]
			    	    </c:if>
			    	    <c:if test="${oper.roleId=='superadmin'}">
			    	   		 --
			    	    </c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
