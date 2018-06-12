<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/mlm/orderUi" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					微信openid：<input type="text" name="openid" value="${openid}" alt="精确查找" size="35" />
				</td>
				<td>
					手机号：<input type="text" name="phone" value="${phone}" alt="模糊查找" size="35" />
				</td>
				<td>
					会员姓名：<input type="text" name="trueName" value="${trueName}" alt="模糊查找" size="35" />
				</td>
			</tr>
			<tr>
				<td>
					会员等级：
					<select name="gradeId">
						<option value="">全部</option>
						<c:forEach var="g" items="${gradeList}">
							<option value="${g.id }" <c:if test="${gradeId eq g.id }">selected="selected"</c:if>>${g.name }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					下 单  时 间：
					<input type="text" name="from" value="${from}" class="date" size="15"  dateFmt="yyyy-MM-dd HH:mm:ss"  readonly="readonly" />-
					<input type="text" name="to" value="${to}" class="date" size="15"  dateFmt="yyyy-MM-dd HH:mm:ss"  readonly="readonly" />
				</td>
				<td>
					送 货  时 间：
					<input type="text" name="sendfrom" value="${sendfrom}" class="date" size="15"  dateFmt="yyyy-MM-dd HH:mm:ss"  readonly="readonly" />-
					<input type="text" name="sendto" value="${sendto}" class="date" size="15"  dateFmt="yyyy-MM-dd HH:mm:ss"  readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>
					订单类型：
					<select name="takeType">
						<option value="">全部</option>
						<option value="1" <c:if test="${takeType eq 1 }">selected="selected"</c:if>>外卖</option>
						<option value="2" <c:if test="${takeType eq 2 }">selected="selected"</c:if>>自取</option>
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
			<li><a class="icon" href="/mlm/exportOrder/json?openid=${openid }&trueName=${trueName}&phone=${phone}&gradeId=${gradeId}&from=${from}&to=${to}&sendfrom=${sendfrom}&sendto=${sendto}&takeType=${takeType}" ><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th style="width:20">订单号</th>
				<th>下单时间</th>
				<th>收货时间</th>
				<th>会员姓名</th>
				<th>会员等级</th>
				<th>手机号</th>
				<th>地址</th>
				<th>支付总金额(元)</th>
				<th>微信支付(元)</th>
				<th>预存支付(元)</th>
				<th>购买数量</th>
				<th>订单类型</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="order" items="${list}">
				<tr target="sid_user" rel="${order.id}">
					<td style="width:20">${order.id}</td>
					<td>${order.updateTimeStr}</td>
					<td>${order.sendTimeStr }</td>
					<td>${order.trueName}</td>
					<td>${order.gradeName}</td>
					<td>${order.phone}</td>
					<td>${order.addr}</td>
					<td>${order.pay}</td>
					<td>${order.wxpay}</td>
					<td>${order.prepay}</td>
					<td>${order.dishNum}</td>
					<c:if test="${order.takeType==2}"><td style="color:red ;">自取</td></c:if>
					<c:if test="${order.takeType==1}"><td >外卖</td></c:if>
					<td>${order.mark}</td>
					<td>
							<c:if test="${order.takeStatus==0}">
								&nbsp;[<a style="color:orange" rel="input" target="ajaxTodo" href="/mlm/changeTakeStatus/json?orderid=${order.id }">未取</a>]
                            </c:if>
                            <c:if test="${order.takeStatus==1}">
	                             &nbsp;[<a href="/mlm/changeTakeStatus/json?orderid=${order.id }" target="ajaxTodo"  rel="input" title="确定要将${order.id }改为未收取状态?" style="color:blue">已取</a>]
                            </c:if>
							&nbsp;[<a href="/mlm/orderDetailUi?orderId=${order.id }" title="订单[${order.id }]的详情"  callback="dialogAjax" target="dialog" rel="input" width="950"  style="color:blue" >详情</a>]&nbsp;&nbsp;
							&nbsp;[<a href="javascript:PrintIframeByURL('${order.id }');" style="color:blue">打印</a>]
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
<script language="javascript" type="text/javascript"> 
      var LODOP; //声明为全局变量      
 	function PrintIframeByURL(id){
 		LODOP=getLodop();  
 		var host = "http://"+window.location.host;
 		LODOP.PRINT_INIT("订单打印");
 		LODOP.ADD_PRINT_URL(10,10, "100%", "100%",host+"/h5/orderPrint?id="+id);	
 		LODOP.PRINT();		
 	};
 
</script>
