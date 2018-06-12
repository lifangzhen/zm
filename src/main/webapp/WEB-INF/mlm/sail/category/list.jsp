<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/categoryUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					类别名称：<input type="text" name="name" value="${name}" alt="模糊查找" size="30" />
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
			<li><a class="add" href="/mlm/addCategoryUi" target="dialog" rel="input" title="新增类别"><span>新增类别</span></a></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>类别</th>
				<th>排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="c" items="${list}">
				<tr target="sid_user" rel="${c.id}">
					<td>${c.name}</td>
				    <td>${c.number}</td>
					<td>
							&nbsp;[<a href="/mlm/addCategoryUi?id=${c.id}" title="修改"  callback="dialogAjax" target="dialog" rel="input" style="color:blue" >修改</a>]&nbsp;&nbsp;
			    	    	&nbsp;[<a href="/mlm/deleteCategory/json?id=${c.id}" target="ajaxTodo"  rel="input" title="确定要删除${c.name }?" style="color:blue">删除</a>]
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
