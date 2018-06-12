<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = "";
	final String serverName = request.getServerName();
	path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>卓玛管理平台</title>
<jsp:include page="/page/inc/dwz.jsp" />
<script src="/set/js/script.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/LodopFuncs.js" type="text/javascript" charset="utf-8"></script>
</head>
<body scroll="no">
	<div id="layout">
		<div id="header">
		    <!-- navMenu begin -->
			<div class="headerNav">
				<img alt="" src="" height="50" />
				<ul class="nav">
					<li style="color:#fff;">欢迎您（${loginName}）！ </li>
					<li><a href="/mlm/info" target="dialog" width="500" height="400" style="color:#fff;">帐号信息</a></li>
					<li><a href="/mlm/changePwd" target="dialog" width="550" height="300" style="color:#fff;">修改密码</a></li>
					<li>
						<a href="/logoutConfirm" title="退出登录确认" target="dialog" width="300" height="200" style="color:#fff;">退出</a>
					</li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div>
					</li>
					<li theme="azure"><div>天蓝</div>
					</li>
					<li theme="green"><div>绿色</div>
					</li>
					<li theme="purple"><div>紫色</div>
					</li>
					<li theme="silver"><div>银色</div>
					</li>
				</ul>
			</div>
			<!-- navMenu end -->
		</div>
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2></h2><div>收缩</div></div>
				<div class="accordion" fillSpace="sidebar">${tree}</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">主页</span>
								</span>
							</a>
							</li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主页</a>
					</li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<p>
								<span>卓玛管理系统</span>
							</p>
						</div>
						<!-- 系统参数  -->
<!-- 						<form id="form" method="post" action="/mlm/updateParam/json" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);"> -->
<!-- 						</form> -->
						<!-- 系统参数  -->				
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">Copyright &copy; 版权所有</div>
	<script type="text/javascript">
		$(function() {

		});
	</script>
</body>
</html>