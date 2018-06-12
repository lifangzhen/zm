<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/chargeListUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					openid：<input type="text" name="openid" value="${openid}" alt="精确查找" size="30" />
				</td>
				<td>
					会员姓名：<input type="text" name="trueName" value="${trueName}" alt="模糊查找" size="30" />
				</td>
				<td>
					<label>充值日期：</label>
					<input type="text" name="day" value="${day}" class="date" size="10" readonly="readonly" />
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
				<th>openid</th>
				<th>会员名称</th>
				<th>充值金额</th>
				<th>充值时间</th>
				<th>微信预支付ID</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="log" items="${list}">
				<tr target="sid_user" rel="${log.id}">
					<td>${log.openid}</td>
				    <td>${log.trueName}</td>
				    <td>${log.prepay}</td>
				    <td>${log.updateTimeStr}</td>
				    <td>${log.prepayId}</td>
					<td>
							--
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
