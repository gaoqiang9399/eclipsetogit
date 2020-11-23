var dataMap;
var view_div_css;
$(window).resize(function() {
    var bW = (parseInt($(document.body).outerWidth(true)/234))*234+5;
	$(".div_role_body").css("width",bW);
});
$(function(){
		var bW = (parseInt($(document.body).outerWidth(true)/234))*234+5;
		$(".div_role_body").css("width",bW);
		var role = dataMap.mfTemplateModelList;
		$.each(role,function(i,obj){
			add_role_obj(obj);
		});
		new_role_obj();
		$('#searchvalue').bind('keypress',function(event){
            if(event.keyCode == "13"){searchRole();}
        });
	});
	
	(function($) {
		$.fn.role_btn_back1 = function() {
			 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			$(this).bind('click', function() {
				var obj = $(this).parents(".div_role_obj");
				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
					obj.find(".div_role_front").removeClass("sub_move_front");
					obj.find(".div_role_back").removeClass("sub_move_back");
			    }else{
					obj.addClass("move_back").removeClass("move_front");
			    };
			});
		};
		$.fn.role_btn_front1 = function() {//获得编辑块
			 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			$(this).bind('click', function() {
				var obj = $(this).parents(".div_role_obj");
				obj.attr("name","role_btn_front1");
				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
					$(".div_role_front").removeClass("sub_move_front");
					$(".div_role_back").removeClass("sub_move_back");
					obj.find(".div_role_front").addClass("sub_move_front");
					obj.find(".div_role_back").addClass("sub_move_back");
				}else{
					$(".div_role_obj").removeClass("move_front");
					obj.addClass("move_front").removeClass("move_back");
					
				}
			});
		};
		//删除
		$.fn.role_btn_del1 = function(modelNo) {
			$(this).bind('click', function() {
				var $this = $(this).parents(".div_role_rotate");
				$.ajax({
					 type: "POST",
					 url: webPath+"/mfTemplateModel/deleteAjax?modelNo="+modelNo,
		             dataType: "json",
		             success: function(data){
		            	 console.log(data);
		            	if(data.flag=="success"){
							$this.animate({width:0,padding:0,opacity:0},"300","linear",function(){$(this).remove();});
						}else{
							alert(top.getMessage("FAILED_DELETE"));
						}
		             },
		             error:function(xmlhq,ts,err){
		            	alert(top.getMessage("FAILED_DELETE"));
		             }
				});
			});
		};
		//修改保存post
		$.fn.role_btn_update1 = function() {
			$(this).bind('click', function() {
				var $this = $(this).parents(".div_role_back");
				var mydata = [];
				var postData = {};
				var modelNo = $this.find("#roleNo_back").val();
				var modelName = $this.find("#roleName_back").val();
				var useFlag = $this.find("input[name=useFlag]").is(':checked')?'1':'0';
				
				if(modelNo==""||modelName=="undefined" ||modelName=="" ||useFlag==""){
					alert("不能有空值！");
					return false;
				}
				
				postData.name = "modelNo";
				postData.value= modelNo;
				mydata.push(postData);
				
				postData = {};
				postData.name = "modelName";
				postData.value= modelName;
				mydata.push(postData);
				
				postData = {};
				postData.name = "useFlag";
				postData.value= useFlag;
				mydata.push(postData);
				$.ajax({
					 type: "POST",
		             url: webPath+"/mfTemplateModel/updateAjax",
		             data:{ajaxData:JSON.stringify(mydata)},
		             dataType: "json",
		             success: function(data){
		            	 $this.parent().find(".div_role_name").html(data.modelName);
		            	 $this.parent().find(".div_useFlag").html(useFlag == 1?'启用':'禁用');
		            	 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		     				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
		     					$(".div_role_front").removeClass("sub_move_front");
		     					$(".div_role_back").removeClass("sub_move_back");
		     				}else{
		     					$(".div_role_obj").removeClass("move_front");
		     				}
		             },
		             error:function(xmlhq,ts,err){
		            	alert("修改失败！");
		             }
				});
			});
		};
		//新增角色事件
		$.fn.role_btn_add1 = function() {
			$(this).bind('click', function() {
				var $this = $(this).parents(".div_role_back");
				var mydata = [];
				var postData = {};
				var modelNo = $this.find("#roleNo_back").val();
				var modelName = $this.find("#roleName_back").val();
				var useFlag = $this.find("input[name=useFlag]").is(':checked')?'1':'0';
				//useFlag = "1";
				if(modelNo==""||modelName=="undefined" ||modelName=="" ||useFlag==""){
					alert("不能有空值！");
					return false;
				}
				
				postData.name = "modelNo";
				postData.value= modelNo;
				mydata.push(postData);
				
				postData = {};
				postData.name = "modelName";
				postData.value= modelName;
				mydata.push(postData);
				
				postData = {};
				postData.name = "useFlag";
				postData.value= useFlag;
				mydata.push(postData);
				
				$.ajax({
					type: "POST",
					url: webPath+"/mfTemplateModel/insertAjax",
					data:{ajaxData:JSON.stringify(mydata)},
					dataType: "json",
					success: function(data){
						if(data.flag=="success"){
							add_role_obj(data);
							$(".div_role_add").animate({width:0,opacity:0},function(){
								$(".div_role_add").appendTo("#div_role_body").animate({opacity:1,width:224},"100",function(){
								});
								var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
								if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
									$(".div_role_front").removeClass("sub_move_front");
									$(".div_role_back").removeClass("sub_move_back");
								}else{
									$(".div_role_obj").removeClass("move_front");
								}
								$(".div_role_add").find("input").val("");
							});
						}else{
							alert("该模型编号已存在！");
						}
					},
					error:function(xmlhq,ts,err){
						alert(top.getMessage("ERROR_INSERT"));
					}
				});
			});
		};
		$.fn.role_btn_view1 = function(roleNo) {
			$(this).bind("click",function(){
				var $view_div = $("#div_role_view");
				var $this = $(this).parent().parent().find(".imgWrap");
				var vT,vL,vW,vH;
				vW = $this.outerWidth(true);
				vH = $this.outerHeight(true);
				vT = $this.offset().top;
				vL = $this.offset().left;
				view_div_css = {"width":vW+10,"height":vH+10,"top":vT,"left":vL};
				var view_div_open = {"width":"100%","height":"100%","top":0,"left":0};
				$view_div.css(view_div_css);
				$view_div.animate(view_div_open,"slow");
				$('#div_role_edit').delay(500).fadeIn();
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
		var $input_role_ctrl1_add = $("<div><label>模型编号</label><input type='text'  name='roleNo_back' id='roleNo_back' value=''/></div>");
		var $input_role_ctrl2_add = $("<div><label>模型名称</label><input type='text' name='roleName_back' id='roleName_back' value=''/></div>");
		var $input_useFlag = $('<input type="checkbox" name="useFlag" value="0"/>');
		//var $input_role_ctrl3_add = $("<div><label class='label_select'>启用</label><div class='div_select'><select name='roleType_back' id='roleType_back'><option value='1'>启用</option><option value='0'>禁用</option></select></div></div>");
		//var $input_role_ctrl3_add = $("<div><input name='roleType_back' type='checkbox' name='useFlag' value='0'/><button type='button' name='save-btn' style='background: #32b52b;width:43px;height: 21px;border:none; '>保存</button><button type='button' name='back-btn' style='background: #d8cdcd;width:43px;height: 21px;border:none;margin-left:10px; '>取消</button></div>");
		var $input_role_ctrl3_add = $("<div style='padding-top:6px;'><input type='checkbox' name='useFlag' value='1' checked><button type='button' class='save-btn' name='save-btn'>保存</button><button type='button' class='back-btn' name='back-btn'>取消</button></div>");

		$div_role_back_ctrl_add.append($input_role_ctrl1_add);
		$div_role_back_ctrl_add.append($input_role_ctrl2_add);
		$div_role_back_ctrl_add.append($input_role_ctrl3_add);
		
		//$div_back_add.append($i_role_btn_back_add);
		//$div_back_add.append($i_role_btn_save_add);
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
		$div_hover_view_add.role_btn_front1();
		$i_role_btn_back_add.role_btn_back1();//翻转查看
		$i_role_btn_save_add.role_btn_add1();
		$div_back_add.find('button[name=back-btn]').role_btn_back1();
		$div_back_add.find('button[name=save-btn]').role_btn_add1();
		$input_role_ctrl3_add.find("input[type=checkbox]").rcSwitcher({
			width: 50,
			height: 18,
			theme: 'light',
			blobOffset: 1,
			onText:'启用',
			offText:'禁用'
		});
	}
	function add_role_obj(obj){
		var $div_rotate = $("<div class='div_role_rotate'></div>");
		var $div_obj = $("<div class='div_role_obj'></div>");
		var $div_front = $('<div class="div_role_front btn26"><div class="ovrly"></div></div>');
		//鼠标悬停
		var $div_hover = $('<div class="buttons"></div>');
		var $div_hover_view = $('<a title="修改" alt="修改" href="#" class="fa fa-edit"></a>');
		var $div_hover_menu = $('<a title="详情" alt="详情" href="#" class="fa fa-bars"></a>');
		var $div_hover_pms = $('<a title="配置" alt="配置" href="${webPath}/mfTemplateModelConfig/getListPage?modelNo='+obj.modelNo+'" class="fa fa-gears"></a>');
		var $div_hover_del = $('<a title="删除" alt="删除" href="#" class="fa fa-trash-o"></a>');
		$div_hover.appendTo($div_front);
		$div_hover_view.appendTo($div_hover);
		//$div_hover_menu.appendTo($div_hover);
		$div_hover_pms.appendTo($div_hover);
		$div_hover_del.appendTo($div_hover);
		
		var $div_role_no = $("<div class='div_role_no'>"+obj.modelNo+"</div>");
		var $div_role_name = $("<div class='div_role_name'>"+obj.modelName+"</div>");
		var useFlag = obj.useFlag == 1?'启用':'禁用';
		var $div_useFlag = $("<div class='div_useFlag'>"+useFlag +"</div>");
		var $i_role_user=$("<div class = 'i_role_user'><img src='themes/factor/images/dollar1.png' style='margin-top: 15px;'></div>");
		
		
		 //背面
		var $div_back = $("<div class='div_role_back'></div>");
		var $i_role_btn_back=$("<i class = 'fa fa-times-circle-o i_role_btn'></i>");//取消
		var $i_role_btn_save=$("<i class = 'fa fa-check-circle-o i_role_btn'></i>");//保存
		
		var $div_role_back_ctrl = $("<div class='div_role_back_ctrl'></div>");
		//添加按钮
		var $input_role_ctrl1 = $("<div><label>模型编号</label><input  type='text' readonly='readonly' name='roleNo_back' id='roleNo_back' value='"+obj.modelNo+"'/></div>");
		var $input_role_ctrl2 = $("<div><label>模型名称</label><input type='text' name='roleName_back' id='roleName_back' value='"+obj.modelName+"'/></div>");
		//var $input_role_ctrl3 = $("<div style='height:26px;'><label class='label_select'>启用</label><div class='div_select'><select name='roleType_back' id='roleType_back'><option value='1'>启用</option><option value='0'>禁用</option></select></div></div>");
		//$input_role_ctrl3.find("#roleType_back").val(obj.feeStdNo);
		var $input_role_ctrl3 = $("<div style='padding-top:6px;'><input type='checkbox' name='useFlag' value='1' checked><button type='button' class='save-btn' name='save-btn'>保存</button><button type='button' class='back-btn' name='back-btn'>取消</button></div>");
		if(useFlag == '0'){
			$input_role_ctrl3 = $("<div style='padding-top:6px;'><input type='checkbox' name='useFlag' value='0'><button type='button' class='save-btn' name='save-btn'>保存</button><button type='button' class='back-btn' name='back-btn'>取消</button></div>");
		}
		$div_role_back_ctrl.append($input_role_ctrl1);
		$div_role_back_ctrl.append($input_role_ctrl2);
		$div_role_back_ctrl.append($input_role_ctrl3);
		//$div_back.append($i_role_btn_back);
		//$div_back.append($i_role_btn_save);
		$div_back.append($div_role_back_ctrl);
		
		$div_front.append($div_role_no);
		$div_front.append($div_role_name);
		$div_front.append($div_useFlag);
		$div_front.append($i_role_user);
		
		$div_obj.append($div_back);
		$div_obj.append($div_front);
		$div_obj.appendTo($div_rotate);
		$div_rotate.appendTo($("#div_role_body"));
		$i_role_btn_back.role_btn_back1();//取消
		$div_hover_view.role_btn_front1();//翻转编辑，获得编辑页
		$div_hover_menu.role_btn_view1(obj.modelNo);//详情
		$div_hover_del.role_btn_del1(obj.modelNo);//绑定删除事件
		$i_role_btn_save.role_btn_update1();//保存
		$div_back.find("button[name=save-btn]").role_btn_update1();
		$div_back.find("button[name=back-btn]").role_btn_back1();
		$input_role_ctrl3.find("input[type=checkbox]").rcSwitcher({
			width: 50,
			height: 18,
			theme: 'light',
			blobOffset: 1,
			onText:'启用',
			offText:'禁用'
		});
//		$('.swraper.light .stoggler.on').css("background","#32b5cb");
//		$('.swraper.light .stoggler.off').css("background","#e7e7e7");
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
		var val = [];
		var customQuery = {customQuery:$("#searchvalue").val()};
		val.push(customQuery);
		val = JSON.stringify(val);
		$.ajax({
			 type: "POST",
             url: webPath+"/mfTemplateModel/searchAjax",
             data:{ajaxData:val},
             dataType: "json",
             success: function(data){
            	$("#div_role_body").animate({opacity:0},"fast",function(){
            		$(this).empty();
            		//data = eval("(" + data + ")");
            		var role = data.mfTemplateModelList;
					$.each(role,function(i,obj){
						add_role_obj(obj);
					});
					new_role_obj();
            		});
            		$("#div_role_body").animate({opacity:1},"fast");
            	
             },
             error:function(xmlhq,ts,err){
            	alert(top.getMessage("ERROR_SELECT"));
             }
		});
	}
