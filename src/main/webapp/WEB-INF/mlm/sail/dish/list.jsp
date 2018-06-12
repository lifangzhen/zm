<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/dishUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					菜品名称：<input type="text" name="name" value="${name}" alt="模糊查找" size="30" />
				</td>
				<td>
					菜品类别：
					<select name="categoryId">
						<option value="">全部</option>
						<c:forEach var="c" items="${cateList}">
							<option value="${c.id }" <c:if test="${categoryId eq c.id }">selected="selected"</c:if>>${c.name }</option>
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
			<li><a class="add" href="/mlm/addDishUi" target="dialog" height="550" rel="input" title="新增菜品"><span>新增菜品</span></a></li>
			<li><a class="add" href="/mlm/modifyStockUi?categoryId=${categoryId }" target="dialog" height="550" rel="input" title="批量修改库存"><span>批量修改库存</span></a></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>名称</th>
				<th>图片</th>
				<th>类别</th>
				<th>单位</th>
				<th>价格</th>
				<th>库存</th>
				<th>排序</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="d" items="${list}">
				<tr target="sid_user" rel="${d.id}">
					<td>${d.name}</td>
				    <td><img width="50"  src="${d.pic}" /></td>
				    <td>${d.categoryName}</td>
				    <td>${d.unit}</td>
				    <td>${d.price}</td>
				    <td>${d.stock}</td>
				    <td>${d.number}</td>
					<c:if test="${d.status==1}"><td >正常</td></c:if>
				    <c:if test="${d.status==2}"><td style="color:red ;">下架</td></c:if>
					<td>
							<c:if test="${d.status==1}">  
								&nbsp;[<a href="/mlm/lockdish/json?id=${d.id}" target="ajaxTodo"  rel="input"  style="color:blue">下架</a>]
							</c:if>
				    	    <c:if test="${d.status==2}">
				    	    	&nbsp;[<a href="/mlm/lockdish/json?id=${d.id}" target="ajaxTodo"  rel="input"  style="color:blue">上架</a>]
							</c:if>
							&nbsp;[<a href="/mlm/addDishUi?id=${d.id }" title="修改"  callback="dialogAjax" target="dialog" rel="input" height="550"  style="color:blue" >修改</a>]&nbsp;&nbsp;
							&nbsp;[<a href="/mlm/deleteDish/json?id=${d.id}" target="ajaxTodo"  rel="input" title="确定要删除${d.name }?" style="color:blue">删除</a>]
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
