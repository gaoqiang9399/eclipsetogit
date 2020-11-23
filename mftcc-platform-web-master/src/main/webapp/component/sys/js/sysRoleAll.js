var dataMap;
var view_div_css;
$(window).resize(function() {
    var bW = (parseInt($(document.body).outerWidth(true)/234))*234+5;
	$(".div_role_body").css("width",bW);
	$("#div_role_body_content").height("");
	var bodyHeight = document.body.clientHeight-50;
	$("#div_role_body_content").height(bodyHeight);
});
$(function(){
		var bodyHeight = document.body.clientHeight-50;
		$("#div_role_body_content").height(bodyHeight);
		$("#div_role_body_content").mCustomScrollbar({
			axis:"y",
			theme:"minimal-dark",
			advanced:{ 
				updateOnBrowserResize:true 
			},autoHideScrollbar: true
		});
		var bW = (parseInt($(document.body).outerWidth(true)/234))*234+5;
		$(".div_role_body").css("width",bW);
		var role = dataMap.sysRoleList;
		$.each(role,function(i,obj){
			add_role_obj(obj);
		});
		new_role_obj();
		$('#searchvalue').bind('keypress',function(event){
            if(event.keyCode == "13"){searchRole();}
        });
	});
	
	(function($) {
		$.fn.role_btn_back = function() {
//			 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			$(this).bind('click', function() {
				var obj = $(this).parent().parent();
//				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
//					obj.find(".div_role_front").removeClass("sub_move_front");
//					obj.find(".div_role_back").removeClass("sub_move_back");
//			    }else{
					obj.addClass("move_back").removeClass("move_front");
//			    };
			});
		};
		$.fn.role_btn_front = function() {
//			 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			$(this).bind('click', function() {
				var obj = $(this).parent().parent().parent();
//				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
//					$(".div_role_front").removeClass("sub_move_front");
//					$(".div_role_back").removeClass("sub_move_back");
//					obj.find(".div_role_front").addClass("sub_move_front");
//					obj.find(".div_role_back").addClass("sub_move_back");
//				}else{
					$(".div_role_obj").removeClass("move_front");
					obj.addClass("move_front").removeClass("move_back");
//				}
			});
		};
		//删除
		$.fn.role_btn_del = function(roleNo) {
			$(this).bind('click', function() {
				var $this = $(this).parent().parent().parent().parent();
				window.top.alert("是否确认删除该角色！！！",2,function(){
				$.ajax({
					 type: "POST",
					 url: webPath+"/sysRole/del?roleNo="+roleNo,
		             dataType: "json",
		             success: function(data){
		            	 console.log(data);
						if(data.sts==1){
							$this.animate({width:0,padding:0,opacity:0},"300","linear",function(){$(this).remove();});
						}else{
							alert(top.getMessage("FAILED_DELETE"),0);
						}
		             },
		             error:function(xmlhq,ts,err){
		            	alert(top.getMessage("FAILED_DELETE"),0);
		             }
					});
				},function(){
				});
			});
		};
		//修改保存
		$.fn.role_btn_update = function() {
			$(this).bind('click', function() {
				var $this = $(this).parent();
				var postData = {};
				postData.roleNo=$this.find("#roleNo_back").val();
				postData.roleName=$this.find("#roleName_back").val();
				postData.roleType=$this.find("#roleType_back").val();
				$.ajax({
					 type: "POST",
		             url: webPath+"/sysRole/update",
		             data:{ajaxData:JSON.stringify(postData)},
		             dataType: "json",
		             success: function(data){
		            	 $this.parent().find(".div_role_name").html(data.roleName);
//		            	 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
//		     				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
//		     					$(".div_role_front").removeClass("sub_move_front");
//		     					$(".div_role_back").removeClass("sub_move_back");
//		     				}else{
		     					$(".div_role_obj").removeClass("move_front");
//		     				}
		             },
		             error:function(xmlhq,ts,err){
		            	alert("修改失败！",0);
		             }
				});
			});
		};
		//新增角色事件
		$.fn.role_btn_add = function() {
			$(this).bind('click', function() {
				var $this = $(this).parent();
				var postData = {};
				postData.roleNo=$this.find("#roleNo_back").val();
				postData.roleName=$this.find("#roleName_back").val();
				postData.roleType=$this.find("#roleType_back").val();
				if(postData.roleNo==""||postData.roleNo=="undefined"||postData.roleName==""||postData.roleName=="undefined"){
					alert("不能有空值！",0);
					return false;
				}
				$.ajax({
					type: "POST",
					url: webPath+"/sysRole/insert",
					data:{ajaxData:JSON.stringify(postData)},
					dataType: "json",
					success: function(data){
						if(data.sts==1){
							add_role_obj(data);
							$(".div_role_add").animate({width:0,opacity:0},function(){
								$(".div_role_add").appendTo("#div_role_body").animate({opacity:1,width:224},"100",function(){
								});
//								var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
//								if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
//									$(".div_role_front").removeClass("sub_move_front");
//									$(".div_role_back").removeClass("sub_move_back");
//								}else{
									$(".div_role_obj").removeClass("move_front");
//								}
								$(".div_role_add").find("input").val("");
							});
						}else{
							alert("该角色号已存在！",0);
						}
					},
					error:function(xmlhq,ts,err){
						alert(top.getMessage("ERROR_INSERT"),0);
					}
				});
			});
		};
		$.fn.role_btn_view = function(roleNo) {
			$(this).bind("click",function(){
				window.location.href = "SysBtnDefAction_getListTreeRole.action?roleNo="+roleNo;
			});
		};
	})(jQuery);
	function new_role_obj(){
		var $div_rotate_add = $("<div class='div_role_rotate div_role_add'></div>");
		var $div_obj_add = $("<div class='div_role_obj'></div>");
		var $div_front_add = $('<div class="div_role_front btn26"><div class="ovrly"></div></div>');
		//背面
		var $div_back_add = $("<div class='div_role_back'></div>");
		var $i_role_btn_back_add=$("<i class = 'fa fa-times-circle-o i_role_btn'></i>");//取消
		var $i_role_btn_save_add=$("<i class = 'fa fa-check-circle-o i_role_btn'></i>");//保存
		var $div_role_back_ctrl_add = $("<div class='div_role_back_ctrl'></div>");
		//添加按钮
		var $input_role_ctrl1_add = $("<div><label>角色号</label><input type='text' name='roleNo_back' id='roleNo_back' value=''/></div>");
		var $input_role_ctrl2_add = $("<div><label>角色名</label><input type='text' name='roleName_back' id='roleName_back' value=''/></div>");
		var $input_role_ctrl3_add = $("<div style='height:26px;'><label class='label_select'>角色类型</label><div class='div_select'><select name='roleType_back' id='roleType_back'><option value='1'>外部</option><option value='2'>内部</option></select></div></div>");
		$div_role_back_ctrl_add.append($input_role_ctrl1_add);
		$div_role_back_ctrl_add.append($input_role_ctrl2_add);
		$div_role_back_ctrl_add.append($input_role_ctrl3_add);
		$div_back_add.append($i_role_btn_back_add);
		$div_back_add.append($i_role_btn_save_add);
		$div_back_add.append($div_role_back_ctrl_add);
		//鼠标悬停
		var $div_hover_add = $('<div class="buttons"></div>');
		var $div_hover_view_add = $('<a title="新增" alt="新增" href="#" class="fa fa-plus"></a>');
		var $i_icon_add = $("<i class = 'fa fa-plus i_role_plus'></i>");
		$div_hover_add.appendTo($div_front_add);
		$i_icon_add.appendTo($div_front_add);
		$div_hover_view_add.appendTo($div_hover_add);
		$div_obj_add.append($div_front_add);
		$div_obj_add.append($div_back_add);
		$div_obj_add.appendTo($div_rotate_add);
		$div_rotate_add.appendTo($("#div_role_body"));
		$div_hover_view_add.role_btn_front();
		$i_role_btn_back_add.role_btn_back();//翻转查看
		$i_role_btn_save_add.role_btn_add();
		$div_front_add.hover(function(){
			$(this).find(".ovrly").css("opacity","1");
			$div_hover_add.find("a").css({
				"opacity":"1",
				"transform":"scale(1)"
			});
		},function(){
			$(this).find(".ovrly").css("opacity","0");
			$div_hover_add.find("a").css({
				"opacity":"0",
				"transform":"scale(0)"
			});
		});
	}
	function add_role_obj(obj){
		var $div_rotate = $("<div class='div_role_rotate'></div>");
		var $div_obj = $("<div class='div_role_obj'></div>");
		var $div_front = $('<div class="div_role_front btn26"><div class="ovrly"></div></div>');
		//鼠标悬停
		var $div_hover = $('<div class="buttons"></div>');
		var $div_hover_view = $('<a title="修改" alt="修改" href="#" class="fa fa-edit"></a>');
		var $div_hover_menu = $('<a title="按钮" alt="按钮" href="#" class="fa fa-bars"></a>');
		var $div_hover_pms = $('<a title="权限" alt="权限" href="PmsConfigureAction_Configure.action?roleNo='+obj.roleNo+'" class="fa fa-gears"></a>');
		var $div_hover_del = $('<a title="删除" alt="删除" href="#" class="fa fa-trash-o"></a>');
		$div_hover.appendTo($div_front);
		$div_hover_view.appendTo($div_hover);
		$div_hover_menu.appendTo($div_hover);
		$div_hover_pms.appendTo($div_hover);
		$div_hover_del.appendTo($div_hover);
		$div_front.hover(function(){
			$(this).find(".ovrly").css("opacity","1");
			$div_hover.find("a").css({
				"opacity":"1",
				"transform":"scale(1)"
			});
		},function(){
			$(this).find(".ovrly").css("opacity","0");
			$div_hover.find("a").css({
				"opacity":"0",
				"transform":"scale(0)"
			});
		});
		
		var $div_role_no = $("<div class='div_role_no'><i class='role_dian'></i>"+obj.roleNo+"</div>");
		var $div_role_name = $("<div class='div_role_name'>"+obj.roleName+"</div>");
		var $i_role_user=$("<i class = 'i_role_user'></i>");
		
		
		 //背面
		var $div_back = $("<div class='div_role_back'></div>");
		var $i_role_btn_back=$("<i class = 'fa fa-times-circle-o i_role_btn'></i>");//取消
		var $i_role_btn_save=$("<i class = 'fa fa-check-circle-o i_role_btn'></i>");//保存
		var $div_role_back_ctrl = $("<div class='div_role_back_ctrl'></div>");
		//添加按钮
		var $input_role_ctrl1 = $("<div><label>角色号</label><input readonly='readonly' type='text' name='roleNo_back' id='roleNo_back' value='"+obj.roleNo+"'/></div>");
		var $input_role_ctrl2 = $("<div><label>角色名</label><input type='text' name='roleName_back' id='roleName_back' value='"+obj.roleName+"'/></div>");
		var $input_role_ctrl3 = $("<div style='height:26px;'><label class='label_select'>角色类型</label><div class='div_select'><select name='roleType_back' id='roleType_back'><option value='1'>外部</option><option value='2'>内部</option></select></div></div>");
		$input_role_ctrl3.find("#roleType_back").val(obj.roleType);
		$div_role_back_ctrl.append($input_role_ctrl1);
		$div_role_back_ctrl.append($input_role_ctrl2);
		$div_role_back_ctrl.append($input_role_ctrl3);
		$div_back.append($i_role_btn_back);
		$div_back.append($i_role_btn_save);
		$div_back.append($div_role_back_ctrl);
		
		$div_front.append($div_role_no);
		$div_front.append($div_role_name);
		$div_front.append($i_role_user);
		$div_obj.append($div_front);
		$div_obj.append($div_back);
		$div_obj.appendTo($div_rotate);
		$div_rotate.appendTo($("#div_role_body"));
		$i_role_btn_back.role_btn_back();//翻转查看
		$div_hover_view.role_btn_front();//翻转编辑
		$div_hover_menu.role_btn_view(obj.roleNo);//绑定打开编辑页面
		$div_hover_del.role_btn_del(obj.roleNo);//绑定删除事件
		$i_role_btn_save.role_btn_update();//修改
	}
	function close_view(){
		$("#div_role_edit").delay(100).fadeOut();
		var $this = $("#div_role_view");
		$this.animate(view_div_css,"slow");
		vT = view_div_css.height/2;
		vL = view_div_css.width/2;
		$this.animate({"width":0,"height":0,top:'+='+vT+'px',left:'+='+vL+'px'},"slow");
		//$this.empty();
	}
	function searchRole(){
		var val = $("#searchvalue").val();
		$.ajax({
			 type: "POST",
             url: webPath+"/sysRole/search",
             data:{ajaxData:val},
             dataType: "json",
             success: function(data){
            	$("#div_role_body").animate({opacity:0},"fast",function(){
            		$(this).empty();
            		var role = data.sysRoleList;
					$.each(role,function(i,obj){
						add_role_obj(obj);
					});
					new_role_obj();
            		});
            		$("#div_role_body").animate({opacity:1},"fast");
            	
             },
             error:function(xmlhq,ts,err){
            	alert(top.getMessage("ERROR_SELECT"),0);
             }
		});
	}
