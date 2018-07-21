<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/tableUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					桌号：<input type="text" name="num" value="${num}" alt="" size="30" />
				</td>
				<td>
					门店：
					<select name="storeId">
						<option value="">全部</option>
						<c:forEach var="c" items="${storeList}">
							<option value="${c.id }" <c:if test="${storeId eq c.id }">selected="selected"</c:if>>${c.name }</option>
						</c:forEach>
					</select>
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
			<li><a class="add" href="/mlm/addStoreUi" target="dialog" height="550" rel="input" title="新增桌号"><span>新增桌号</span></a></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>桌号</th>
				<th>门店</th>
				<th>二维码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="d" items="${list}">
				<tr target="sid_user" rel="${d.id}">
					<td>${d.num}</td>
					<td>${d.store_id}</td>
				    <td><img width="50"  src="${d.store_id}" /></td>

					<td>
							&nbsp;[<a href="/mlm/deleteTable/json?id=${d.id}" target="ajaxTodo"  rel="input" title="确定要删除${d.num }?" style="color:blue">删除</a>]
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
