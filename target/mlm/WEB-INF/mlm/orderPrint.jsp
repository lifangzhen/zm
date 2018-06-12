<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>orderPrint</title>
</head>
<body>
		<div style="font-size: 14px; ">&nbsp;&nbsp;建外SOHO店</div>
		</br>
		</br>
		<div style="font-size: 12px; font-family:YouYuan">
			类型:<c:if test="${order.takeType eq 1 }">外卖订单</c:if>
					 <c:if test="${order.takeType eq 2 }">自取订单</c:if>
		</div>
		</br>
		<div style="font-size: 12px; font-family:YouYuan">下单时间:${order.updateTimeStr }</div>
		</br>
		<div>***************************</div>
		<c:forEach var="d" items="${list}">
			<div style="font-size: 12px; clear: both; font-family:YouYuan"><span style="float:right; margin-right: 50px;">${d.dishNum}</span>${d.dishName}</div>
		</c:forEach>
		<div>***************************</div>
		</br>
		<c:if test="${order.takeType eq 1 }">
			<div style="font-size: 12px;  font-family:YouYuan">送达时间:${order.sendTimeStr }</div>
			</br>
			<div style="font-size: 12px;  font-family:YouYuan">地址:${order.addr }</div>
			</br>
		</c:if>
		<div style="font-size: 12px;  font-family:YouYuan">姓名:${order.trueName }</div>
		</br>
		<div style="font-size: 12px;  font-family:YouYuan">电话:${order.phone }</div>
		<div style="font-size: 12px;  font-family:YouYuan">备注:${order.mark }</div>
</body>
</html>