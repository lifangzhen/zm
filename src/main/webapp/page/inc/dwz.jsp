<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/page/statics/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="/page/statics/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="/page/statics/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="/page/statics/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="/page/statics/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lt IE 9]><script src="/page/statics/dwz/js/speedup.js" type="text/javascript"></script><script src="/page/statics/dwz/js/jquery-1.11.3.min.js" type="text/javascript"></script><![endif]-->
<!--[if gte IE 9]><!--><script src="/page/statics/dwz/js/jquery-2.1.4.min.js" type="text/javascript"></script><!--<![endif]-->

<script src="/page/statics/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="/page/statics/dwz/xheditor/xheditor-1.2.2.min.js" type="text/javascript"></script>
<script src="/page/statics/dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="/page/statics/dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="/page/statics/dwz/chart/raphael.js"></script>
<script type="text/javascript" src="/page/statics/dwz/chart/g.raphael.js"></script>
<script type="text/javascript" src="/page/statics/dwz/chart/g.bar.js"></script>
<script type="text/javascript" src="/page/statics/dwz/chart/g.line.js"></script>
<script type="text/javascript" src="/page/statics/dwz/chart/g.pie.js"></script>
<script type="text/javascript" src="/page/statics/dwz/chart/g.dot.js"></script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6PYkS1eDz5pMnyfO0jvBNE0F"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>

<script src="/page/statics/dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.ui.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.theme.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.navTab.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.tab.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.resize.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.dialog.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.stable.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.ajax.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.database.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.effects.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.panel.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.history.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.combox.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.file.js" type="text/javascript"></script>
<script src="/page/statics/dwz/js/dwz.print.js" type="text/javascript"></script>

<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换时下面dwz.regional.zh.js还需要引入)
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="/page/statics/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">

$(function(){
	DWZ.init("/page/statics/dwz/dwz.frag.xml", {
// 		loginUrl:"/login", loginTitle:"登录Dwz",	// 弹出登录对话框
		loginUrl:"/login",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"/page/statics/dwz/themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});
</script>