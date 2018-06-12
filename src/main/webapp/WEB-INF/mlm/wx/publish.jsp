<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
	<%@include file="/page/inc/pageForm.jsp"%>
<div class="pageContent">	
	<link rel="stylesheet" type="text/css" href="/set/css/style.css" />
	<div class="pageFormContent" layoutH="0">
		<div class="menu_setting_area">
			<!--菜单-->
			<div class="menu_preview_area">
				<div class="mobile_menu_preview">
					<ul class="pre_menu_list no_menu"><!--no_menu-->
						<!--<li class="pre_menu_item grid_item selected size1of3">
							<a href="javascript:void(0);" class="pre_menu_link">
	                            <i class="icon20_common sort_gray"></i>
	                            <span class="js_l1Title">菜单名称</span>
	                        </a>
	                        <div class="sub_pre_menu_box">
	                        	<ul class="sub_pre_menu_list">
	                        		<li class="jslevel2 selected current">
	                        			<a href="javascript:void(0)" class="jsSubView">
	                        				<span class="js_l2Title">子菜单名称</span>
	                        			</a>
	                        		</li>
	                        		<li class="js_addMenuBox">
	                        			<a href="javascript:void(0)" class="jsSubView js_addL2Btn">
	                        				<span class="sub_pre_menu_inner js_sub_pre_menu_inner">
	                        					<i class="icon14_menu_add"></i>
	                        				</span>
	                        			</a>
	                        		</li>
	                        	</ul>
	                        	<i class="arrow arrow_out"></i>
	                        	<i class="arrow arrow_in"></i>
	                        </div>
						</li>-->
						<!--<li class="pre_menu_item grid_item size1of3">
							<a href="javascript:void(0);" class="pre_menu_link">
	                            <i class="icon20_common sort_gray"></i>
	                            <span class="js_l1Title">菜单名称</span>
	                        </a>
						</li>-->
						<li class="js_addMenuBox grid_item">
							<a href="javascript:void(0);" class="pre_menu_link js_addL1Btn">
								<i class="icon14_menu_add"></i>
								<span class="js_addMenuTips">添加菜单</span>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<!--菜单 end-->
			<!--右侧-->
			<div class="menu_form_area" style="display: none;">
				<div id="js_rightBox" class="portable_editor to_left">
					<div class="editor_inner">
						<div class="js_second_title_bar">
							<h4 class="global_info">菜单名称</h4>
							<div class="global_extra">
								<a href="javascript:void(0);" id="jsDelBt">删除菜单</a>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">                                    
								菜单名称                       
							</label>
							<div class="frm_controls">
								<span class="frm_input_box counter_in">                                        
									<input type="text" class="frm_input js_menu_name">                                    
								</span>
								<p class="fail">字数超过上限</p>
								<p class="fail">请输入菜单名称</p>
								<p class="frm_tips">字数不超过4个汉字或8个字母</p>
							</div>
						</div>
						<div class="frm_control_group">
							<label class="frm_label">                                    
								菜单内容                          
							</label>
							<div class="frm_controls frm_vertical_pt">
								<label class="frm_radio_label selected" data-type="text">                                        
									<i class="icon_radio"></i>                                        
									<span class="lbl_content">发送消息</span>                                        
								</label>
								<label class="frm_radio_label" data-type="view">                                        
									<i class="icon_radio"></i>                                        
									<span class="lbl_content">跳转网页</span>                                        
								</label>
							</div>
						</div>
						<div class="menu_content_container" >
							<ul class="tab_navs">
								<li class="tab_nav_li tab_appmsg selected" data-type="text">
									<a href="javascript:void(0)">
										<i class="icon_msg_sender"></i>
										<span class="msg_tab_title">文字消息</span>
									</a>
								</li>
								<li class="tab_nav_li tab_img" data-type="image">
									<a href="javascript:void(0)">
										<i class="icon_msg_sender"></i>
										<span class="msg_tab_title">图片</span>
									</a>
								</li>
							</ul>
							<div class="tab_panel">
								<div class="tab_content" style="display: block;">
									<div class="inner">
										<textarea ></textarea>
									</div>
								</div>
								<div class="tab_content" >
									<div class="tab_cont_cover">
										<div class="media_cover">
											<div class="create_access">
												<a href="javascript:void(0)" onclick="file.click()" class="add_gray_wrp ">
													<i class="add_gray"></i>
													<span>上传图片</span>
												</a>
												<input type="file" id="file" onchange="readFile(this)" accept="image/*" style="display: none;">
											</div>
											<div class="appmsgSendedItem">
												<img src="/sail/images/c65.jpg"  />
												<a href="javascript:void(0)" id="delBtn">删除</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="menu_content" style="display: none;">
							<p class="menu_content_tips">订阅者点击该子菜单会跳到以下链接</p>
							<div class="frm_control_group" style="margin-top: 0px;">
								<label for="" class="frm_label">页面地址</label>
								<div class="frm_controls">
									<span class="frm_input_box disabled">                                                    
										<input type="text" class="frm_input" id="urlText" name="urlText">                                                
									</span>
								</div>
							</div>
						</div>
						<div class="subbox">
							<span id="submitbtn" class="btn btn_input btn_primary"><button>保存</button></span>
						</div>
					</div>
					
					<span class="editor_arrow_wrp">
						<i class="editor_arrow editor_arrow_in"></i>
					</span>
					
				</div>
				<div class="tool_bar">
					<input type="hidden"  id="id" value=""/>
					<input type="hidden"  id="father" value=""/>
					<input type="hidden"  id="level" value=""/>
					<input type="hidden"  id="type" value="text"/>
					<input type="hidden"  id="src" value=""/>
					<input type="hidden"  id="caozuo" value=""/>
					<span id="pubBt" class="btn btn_input btn_primary"><button>菜单发布</button></span>
				</div>
			</div>
			<!--右侧 end-->
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		//获取菜单
		Minit();
	});
</script>

