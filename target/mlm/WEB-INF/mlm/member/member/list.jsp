<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/memberListUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					会员名称：<input type="text" name="trueName" value="${trueName}" alt="模糊查找" size="30" />
				</td>
				<td>
					手机号：<input type="text" name="phone" value="${phone}" alt="模糊查找" size="30" />
				</td>
				<td>
					会员等级：
					<select name="gradeId">
						<option value="">全部</option>
						<c:forEach var="g" items="${gradeList}">
							<option value="${g.id }" <c:if test="${gradeId eq g.id }">selected="selected"</c:if>>${g.name }</option>
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

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th width="120">会员openid</th>
				<th>微信头像</th>
				<th>会员名称</th>
				<th>手机号</th>
				<th>会员等级</th>
				<th>享受折扣</th>
				<th>性别</th>
				<th>预存</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="member" items="${list}">
				<tr target="sid_user" rel="${member.id}">
					<td>${member.id}</td>
					<td><img width="20"  src="${member.headImg}" /></td>
				    <td>${member.trueName}</td>
				    <td>${member.phone }</td>
				    <td>${member.grade.name}</td>
				    <td>${member.grade.rate}%</td>
				    <td><c:if test="${member.sex==0 }">未知</c:if><c:if test="${member.sex==1 }">男</c:if><c:if test="${member.sex==2 }">女</c:if></td>
					<td>${member.prepay}</td>
					<td>
							&nbsp;[<a href="/mlm/updateMemberUi?id=${member.id}" title="修改"  callback="dialogAjax" target="dialog"  height="450"  rel="input" style="color:blue" >修改</a>]&nbsp;&nbsp;
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
