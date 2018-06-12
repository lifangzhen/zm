<%-- 权限分配 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="60">
	
	<input type="hidden"  id="roleId" value="${roleId }" />
		
		<div class="tabs" style="width:500px;float:left;" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:;"><span>菜单权限</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>
					<div id="treeDiv" layoutH="100" style="float:left; display:block;overflow:auto; width:489px; border:solid 1px #CCC; line-height:21px; background:#fff">
					    ${menuTree }
					</div>
				</div>
			</div>
		</div>
		
		<!-- 用户 -->
		<div class="tabs" style="width: 400px;float:left; " >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:;"><span>关联此角色的操作员</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>
					<div layoutH="100" style="float:left; display:block; overflow:auto; width:389px; border:solid 1px #CCC; line-height:21px; background:#fff" id="userDiv">
						<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="123">
							<thead>
								<tr>
									<th>登录名</th>
									<th>联系人</th>
									<th>联系电话</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="ol"  items="${operatorList }">
								<tr target="sid_user" rel="${ol.id}">
										<td>${ol.loginName }</td>
										<td>${ol.linkMan }</td>
										<td>${ol.linkMobile }</td>
									</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<c:if test="${roleId!='superadmin'}">
			<li><div class="buttonActive"><div class="buttonContent"><button type="button"  onclick="saveMenu()" >保存</button></div></div></li>
			</c:if>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	function saveMenu(){
		var  menuIds = getCheckedMenuIds();
		if(menuIds!=""){
			menuIds=menuIds.substring(0,menuIds.length-1);
		}
		$.post("/mlm/authAssign/json",
			{
				roleId:$("#roleId").val(),
				menuIds:menuIds
			},
			function(res){
				if(res.statusCode==200){
					$.pdialog.closeCurrent();
					alertMsg.correct("操作成功,要重新登录才能生效！");
				}else{
					alertMsg.error(res.message);
				}
			},"json");
	}
	
	/**获取所有被选中的菜单ID*/
	function getCheckedMenuIds(){
		var menuIds = "";
		$("#treeDiv").find("a[menuid]").each(function(){
			var $ckbox = $(this).siblings('.ckbox');
			var b = $ckbox.hasClass('checked') || $ckbox.hasClass('indeterminate');	
			if (b) {
				menuIds += $(this).attr("menuid")+",";
			}
		});
		return menuIds;
	}
	
</script>
