var mainConfig = {
	maxH:window.innerHeight,
	maxW:window.innerWidth,
	mainIndex:"",
	maintools:[
	          {"title":"撤销","name":"reply","icon":"fa fa-reply","click":"replyVersion()"},
	          {"title":"重做","name":"cancle","icon":"fa fa-reply fa-flip-horizontal","click":"cancleVersion()"},
	          {"title":"属性","name":"prop","icon":"fa fa-cog","click":""},
	          {"title":"表单属性","name":"layout","icon":"fa fa-columns","click":"formProp()"},
	          {"title":"隐藏域","name":"hiddenNodes","icon":"fa fa-eye-slash","click":"showHiddenNodes()"},
	          {"title":"复制表单","name":"copyForm","icon":"fa fa-copy","click":"copyForm()"},
	          {"title":"删除表单","name":"delForm","icon":"fa fa-trash-o","click":"delForm()"},
	          {"title":"重置容器","name":"panelLvReset","icon":"fa fa-window-restore","click":"panelLvReset()"}
	          ]
};
function showProp(callback) {
	if ($("#layer-configTree").length > 0) {
		callback.call(this);
		$("#configTree").show();
		$(".main-right2").show();
		$(".main-console").hide();
	}else{
		var posLeft = $("#buttonsInDevice").width()+$("#buttonsInDevice").offset().left+50;
		mainConfig.mainIndex = layer.open({
			title : "属性",
			shade : 0,
			type : 1,
			moveOut: true,
			offset: ['100px', posLeft+'px'],
			area: '320px',
			id : "layer-configTree",
			success : function(){
				$("#configTree").show();
				$(".main-right2").show();
				$(".main-console").hide();
				callback.call(this);
				$("#layer-configTree").mCustomScrollbar({theme:"minimal-dark"});
				$(".main-console").mCustomScrollbar({theme:"minimal-dark"});
				$(".main-right2").css("max-height","570px").mCustomScrollbar({theme:"minimal-dark"});
				},
			content : '<div id="configTree" class="weui_cells weui_cells_form" ></div><div class="main-right2" ><ul id="configTree2" style="display: none;"></ul></div><div class="main-console"><ul id="consoleTree"class=""></ul></div>'
		});
	}
}

function showHiddenNodes(){
	if ($("#layer-configTree").length > 0) {
		$("#configTree").hide();
		$(".main-right2").hide();
		$(".main-console").show();
		getHiddenNodes();
	}else{
		var posLeft = $("#buttonsInDevice").width()+$("#buttonsInDevice").offset().left+50;
		mainConfig.mainIndex = layer.open({
			title : "属性",
			shade : 0,
			type : 1,
			offset: ['100px', posLeft+'px'],
			moveOut: true,
			area: '320px',
			id : "layer-configTree",
			success : function(){
				getHiddenNodes();
				$("#configTree").hide();
				$(".main-right2").hide();
				$(".main-console").show();
				$("#layer-configTree").mCustomScrollbar({theme:"minimal-dark"});
				$(".main-console").mCustomScrollbar({theme:"minimal-dark"});
				$(".main-right2").css("max-height","570px").mCustomScrollbar({theme:"minimal-dark"});
				},
			content : '<div id="configTree" class="weui_cells weui_cells_form" style="display:none;"></div><div class="main-right2" style="display:none;" ><ul id="configTree2" style="display: none;"></ul></div><div class="main-console"><ul id="consoleTree"class=""></ul></div>'
		});
	}
	activeBtn("hiddenNodes");
}
function mainMouseDown(){
	var e = event || window.event;
	$(".mainDragDashed").remove();
	var para=document.createElement("div");
	para.style.width=e.target.offsetWidth+2+"px";
	para.style.height=e.target.offsetHeight+2+"px";
	para.style.position="absolute";
	para.style.pointerEvents="none";
	para.style.zIndex="19911027";
	para.className = "mainDragDashed";
	para.id = "mainDragDashed";
	$(para).data("top",t);
	var l = getElementLeft(e.target);
	var t = getElementTop(e.target);
	var pos = mainGetPageLocation();
	para.setAttribute("sl",pos.x-(l-5));
	para.setAttribute("st",pos.y-(t-5));
	para.style.left = ((l-5))+"px";
	para.style.top  = ((t-5))+"px";
	document.body.appendChild(para);
}
function mainDrag(){
	var e = event || window.event;
	var pos = mainGetPageLocation();
	var para = document.getElementById("mainDragDashed");
	var l = getElementLeft(e.target);
	var t = getElementTop(e.target);
	para.style.border="4px dotted #a8afb4";
	para.className = "mainDragDashed fa fa-arrows";
	$(document).bind("dragover",function(){
		getMouseLoc(para,l,t,pos);
	});
}
function removeDargDashed(){
	$(".mainDragDashed").remove();
}
function getMouseLoc(para,sl,st,spos){
	var pos = mainGetPageLocation();
	para.style.left = (pos.x - (spos.x - sl) - 5)+"px";
	para.style.top  = (pos.y - (spos.y - st) - 5)+"px";
}
function getElementLeft(element){
	var actualLeft = element.offsetLeft;
	var current = element.offsetParent;
	while (current !== null){
		actualLeft += current.offsetLeft;
		current = current.offsetParent;
	}
	return actualLeft;
}
function getElementTop(element){
	var actualTop = element.offsetTop;
	var current = element.offsetParent;
	while (current !== null){
		actualTop += current.offsetTop;
		current = current.offsetParent;
	}
	return actualTop;
}
//========== 
//= 获取鼠标所在的坐标位置 = 
//========== 
var mainGetPageLocation = function(event) {
	var e = event || window.event;
	var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
	var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
	var x = e.pageX || e.clientX + scrollX;
	var y = e.pageY || e.clientY + scrollY;
	return {
		'x': x,
		'y': y
	};
};
$(function() {
	$.getJSON("file/menu.json", function(data) {
		tagPropertys = data.property;
		tagDatatypes = data.datatype;
		var $ul = $(".cpList");
		var $subul = $(".sub_cpList");
		$.each(data.zNodes, function(i, node) {
			if(node.pId == 0) {
				var $a = $('<span href="javascript:;" class="weui_grid js_grid" data-id="button" ></span>');
				$a.attr("id", node.property).attr("title", node.name);
				/*$a.attr("ondragstart", "mainDrag(event)");
				$a.attr("onmousedown", "mainMouseDown(event)");
				$a.attr("onmouseup", "removeDargDashed()");*/
                var $div;
				if(node.hasOwnProperty('icon')){
					$div = $('<div class="weui_grid_icon"><i class="dhccwx "></i></div>');
					$div.find("i.dhccwx").addClass(node.icon);
					$div.appendTo($a);
				}else{
					$div = $('<div class="weui_grid_icon"><img src="./images/icon_nav_button.png" alt=""></div>');
					$div.find("img").attr("src", "img/icon_nav_button.png");
					$div.appendTo($a);
				}
				$('<p  class="weui_grid_label">' + node.name + '</p>').appendTo($a);
				$.each(node, function(k, v) {
					$a.attr("data-" + k, v);
				});
				if(node.lv == 1) {
					$a.appendTo($ul);
				} else {
					$a.appendTo($subul);
				}
			}
		});
		/*layer.open({
			moveOut: true,
			title : false,
			skin: 'device-class',
			area:['416px','705px'],
			shade : 0,
			type : 1,
			offset: ['55px', '280px'],
			closeBtn: 0,
			id : "main-center",
			move:'#buttonsInDevice',
			success:function(){
				
			},
			//content : '<div class="device device-fixed"id="buttonsInDevice"><div class="status-bar"></div><div class="window"id="window"><iframe src="frame.html"width="316"height="524"frameborder="0"name="mobileFrame"></iframe></div><div id="qrcode-btn"></div></div><div class="device-shadow"></div>'
		});*/
		initDraggable();
	});
	$("#ctrl-console").click(function(){
		$(".main-console").hasClass("main-console-show")?$(".main-console").removeClass("main-console-show"):$(".main-console").addClass("main-console-show");
	});
	
	var input = document.getElementById("formsearch");
	var awesomplete = new Awesomplete(input,{autoFirst:true});
	$(input).bind("focus",function(){
		$.ajax({
			type:"post",
			async:false,
			cache:false,
			url:"../GetFileList",
			dataType:"json",
			success:function(jsonData){
				awesomplete.list = jsonData;
			},
			error:function(){
				layer.msg('读取文件路径失败！',{zIndex:19911027,offset:'t'});
			}
		});
	});
	
	$(input).on("awesomplete-selectcomplete",function(){
		layer.close(mainConfig.mainIndex);
		getData(this.value);
		showTools();
	});
	document.addEventListener("drop", function(){
		event.preventDefault();
		removeDargDashed();
	});
	document.addEventListener("dragover", function(){event.preventDefault();});
	if(window.innerWidth<=1366){
		layer.msg('分辨率过低，建议缩放75%~90%之间',{zIndex:19911027,offset:'t'});
	}
});
function viewForm(){
	var formId = $("#formsearch").val();
	window.open("view.jsp?formId="+formId,"_blank",'width=320,height=549,channelmode=yes,location=no,menubar=no,resizable=no,status=no,toolbar=no');
}
function showTools(){
	var arr = mainConfig.maintools;
	if($(".main-tools").length==0){
		var $ul = $('<ul class="main-tools"></ul>');
		$.each(arr,function(i,tool){
			var $li = $('<li></li>');
			$li.addClass(tool.name);
			var $i = $('<i class="'+tool.icon+'"></i>');
			$i.appendTo($li);
			$li.hover(function(){
				  layer.tips(tool.title, this); //在元素的事件回调体中，follow直接赋予this即可
			}, function(){
				layer.closeAll('tips');
			});
			$li.bind("click",function(){
				eval(tool.click);
			});
			$li.appendTo($ul);
		});
		$ul.appendTo($("#buttonsInDevice"));
	}
}

var form = layui.form();
function formProp(){
	var formLayer = layer.open({
				  type: 1,
				  title: "表单属性",
				  area: ['635px', '385px'],
				  shadeClose: true,
				  success:function(layero, index){
					  $(layero).find("input[name=layout][value="+formData.layout+"]").attr("checked",true);
					  $(layero).find("input[name=formId]").val(formData.formId);
					  $(layero).find("input[name=desc]").val(formData.desc);
					  $(layero).find("input[name=webFormId]").val(formData.webFormId);
					  $(layero).find("input[name=css]").val(formData.css);
					  $(layero).find("#cancle").bind("click",function(){
						  layer.close(index);
					  });
					  form.render('radio'); //刷新select选择框渲染
					  form.on('radio(layout)', function(data){
						  changeLayout(data.value);
						}); 
					  form.on('submit(formData)', function(data){
						  saveFormData(data.field);
						  layer.close(formLayer);
						  return false; 
					  });
					  
				  },
				  //skin: 'yourclass',
				  content: '<div style="width: 90%;margin-top: 20px;"class="layui-form form-prop"><div class="layui-form-item"><label class="layui-form-label">表单ID</label><div class="layui-input-block"><input disabled type="text"name="formId"autocomplete="off"class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">Web表单ID</label><div class="layui-input-block"><input type="text"name="webFormId"placeholder="请输入WebFormId"autocomplete="off"class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">描述</label><div class="layui-input-block"><input type="text"name="desc"placeholder="请输入描述"autocomplete="off"class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">样式</label><div class="layui-input-block"><input type="text"name="css"placeholder="请输入样式"autocomplete="off"class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">布局</label><div class="layui-input-block"><input type="radio"name="layout"lay-filter="layout"value="horizontal"title="水平"><input type="radio"name="layout"lay-filter="layout"value="vertical"title="垂直"></div></div><div class="layui-form-item"><div class="layui-input-block"style="text-align:right;"><button lay-submit lay-filter="formData"class="layui-btn">保存</button><button id="cancle" class="layui-btn layui-btn-primary">取消</button></div></div></div>'
				});
}
//新建表单
function createNewForm(){
	var newFormlayer = layer.open({
							area: ['545px', '360px'],
						  content: '<div style="width: 90%;margin-top: 20px;"class="layui-form newform-prop"><div class="layui-form-item"><label class="layui-form-label">表单ID</label><div class="layui-input-block"><input type="text"name="formId"placeholder="请输入FormId"autocomplete="off"class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">Web表单ID</label><div class="layui-input-block"><input type="text"name="webFormId"placeholder="请输入WebFormId"autocomplete="off"class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">描述</label><div class="layui-input-block"><input type="text"name="desc"placeholder="请输入描述"autocomplete="off"class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">样式</label><div class="layui-input-block"><input type="text"name="css"placeholder="请输入样式"autocomplete="off"class="layui-input"></div></div></div>'
						  ,btn: ['新增', '取消']
						  ,yes: function(index, layero){
							  formData.formId = layero.find("input[name=formId]").val();
							  formData.desc = layero.find("input[name=desc]").val();
							  formData.webFormId = layero.find("input[name=webFormId]").val();
							  formData.css = layero.find("input[name=css]").val();
							  formData.layout = "horizontal";
							  formData.nodes=[];
							  console.log(formData);
							  if(!formData.formId||formData.formId==""){
								  layer.tips('表单ID不能为空！', '.newform-prop input[name=formId]', {
									  tips: 1
									});
								  return;
							  }
							  $.ajax({
									type:"post",
									async:false,
									cache:false,
									url:"../CreateXml",
									dataType:"text",
									data: {
										ajaxData: JSON.stringify(formData)
									},
									success:function(data){
										layer.msg(data);
										layer.close(newFormlayer);
									},
									error:function(){
										layer.msg("系统错误，请联系开发人员！");
									}
								});
								 
						  },btn2: function(index, layero){
						    
						  }
						  ,cancel: function(){ 
						    //右上角关闭回调
						  }
						});
}
//复制表单
function copyForm(){
	layer.open({
		area: ['545px', '260px'],
	  content: '<div style="width: 90%;margin-top: 20px;"class="layui-form newform-prop"><div class="layui-form-item"><label class="layui-form-label">表单ID</label><div class="layui-input-block"><input disabled type="text"name="formId"placeholder="请输入表单ID"autocomplete="off"class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">新表单ID</label><div class="layui-input-block"><input type="text"name="newFormId"placeholder="请输入新表单ID"autocomplete="off"class="layui-input"></div></div></div>',
	  success:function(layero, index){
		  console.log(layero);
		  layero.find("input[name=formId]").val(formData.formId);
	  },
	  btn: ['复制', '取消'],
	  yes: function(index, layero){
		  formData.formId = layero.find("input[name=formId]").val();
		  formData.newFormId = layero.find("input[name=newFormId]").val();
		  if(!formData.formId||formData.formId==""){
			  layer.tips('表单ID不能为空！', '.newform-prop input[name=formId]', {
				  tips: 1
			  });
			  return;
		  }
		  if(!formData.newFormId||formData.newFormId==""){
			  layer.tips('新表单ID不能为空！', '.newform-prop input[name=newFormId]', {
				  tips: 1
				});
			  return;
		  }
		  $.ajax({
				type:"post",
				async:false,
				cache:false,
				url:"../CopyXml",
				dataType:"text",
				data: {
					ajaxData: JSON.stringify(formData)
				},
				success:function(data){
					layer.msg(data);
					layer.close(index);
				},
				error:function(){
					layer.msg("系统错误，请联系开发人员！");
				}
			});
			 
		  
	  },btn2: function(index, layero){
	    
	  }
	  ,cancel: function(){ 
	    //右上角关闭回调
	  }
	});
}
//删除表单
function delForm(){
	layer.confirm('确认删除表单?', {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type:"post",
				async:false,
				cache:false,
				url:"../DelXml",
				dataType:"text",
				data: {
					ajaxData: JSON.stringify(formData)
				},
				success:function(data){
						layer.msg(data, function(){
							window.location.reload();
						});
						layer.close(index);
				},
				error:function(){
					layer.close(index);
					layer.msg("系统错误，请联系开发人员！");
				}
			});
		});
}
function activeBtn(name){
	$(".main-tools").find("."+name).addClass("on").siblings('li').removeClass("on");
}

function resetLv(elem) {
		elem.each(function(){
		if($(this).children(".column").children("[data-property=panel]").length>0){
			var panellv = Number(elem.attr("panellv"))+1;
			$(this).children(".column").children("[data-property=panel]").attr("panellv",panellv);
			resetLv($(this).children(".column").children("[data-property=panel]"));
		}
	})
}

function panelLvReset(elem){
		elem = $("#dropArea").children("[data-property=panel]");
		elem.attr("panellv","0");
		resetLv(elem);
	$.toast("容器层级重置成功",1000);
}