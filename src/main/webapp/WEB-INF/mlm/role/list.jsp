<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/listRoleUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					角色名称：<input type="text" name="roleName" value="${roleName}" alt="模糊查找" size="30" />
				</td>
				<td colspan="4">
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/mlm/addRoleUi" target="dialog" rel="input" title="添加角色"><span>添加角色</span></a></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>角色名称</th>
				<th>创建时间</th>
				<th>设置</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="role" items="${list}">
				<tr target="sid_user" rel="${role.id}">
					<td>${role.name}</td>
				    <td>${role.createTimeStr}</td>
					<td>
							&nbsp;[<a href="/mlm/authAssignUi?roleId=${role.id }" title="为角色[${role.name }]分配权限"  callback="dialogAjax" target="dialog" rel="input" width="950"  style="color:blue" >分配权限</a>]&nbsp;&nbsp;
							<c:if test="${role.id!='superadmin'}">
							&nbsp;[<a href="/mlm/deleteRole/json?id=${role.id}" target="ajaxTodo"  rel="input" title="确定要删除${role.name }?" style="color:blue">删除</a>]
							</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
