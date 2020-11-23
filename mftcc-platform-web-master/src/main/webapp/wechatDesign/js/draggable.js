//初始化拖拽组件  
window.onload = function() {
	init(dropAreaId);
};
var dropAreaId = "dropArea";
	//后面用于判断鼠标移动的时候是把元素底部的边变蓝还是上部的边变蓝
var deraction = {
		index: -1,
		deraction: 0, //-1上，0第一个，1下
		flag: -1,
		prefix: "dhcc",
		pkno: 0
	};
	//正在拖拽的元素
var dragTarget;
//document.getElementById捕捉到的节点
var target = {
		dropArea: null
	};
	//判断是否为空
var empty = function(obj) {
		if(obj == undefined || obj == null || obj == "")
			return true;

		return false;
	};
	//异常处理
var exception = function(tip) {
		console.log("dragErr:" + tip);
		throw new Error(tip);
	};
	//获取dom对象
var getTarget = function(id) {
	var target = document.getElementById(id);
	if(empty(target)){
		throw new Error("无法找到这个id");
	}
	return target;
};

//  ========== 
//  = 获取鼠标所在的坐标位置 = 
//  ========== 
var getPageLocation = function(event) {
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
	//元素之后插入
var insertAfter = function(newElement, targetElement) {
		var parent = targetElement.parentNode;
		if(parent.lastChild == targetElement) {
			// 如果最后的节点是目标元素，则直接添加。因为默认是最后
			parent.appendChild(newElement);
		} else {
			//如果不是，则插入在目标元素的下一个兄弟节点的前面。也就是目标元素的后面
			parent.insertBefore(newElement, targetElement.nextSibling);
		}
	};
	//元素之前插入
var insertBefore = function(newElement, targetElement) {
	targetElement.parentNode.insertBefore(newElement, targetElement)
};

//  ========== 
//  = 获取CSS = 
//  ========== 
var getCss = function(o, key) {
		return o.currentStyle ? o.currentStyle[key] : document.defaultView.getComputedStyle(o, false)[key];
	};
	//阻止冒泡
var preventDefault = function(e) {
	e.preventDefault();
};

//  ========== 
//  = 初始化 = 
//  ========== 
var init = function(dropArea) {
		if(!empty(dropArea)) {
			target.dropArea = getTarget(dropArea);
			target.dropArea.addEventListener("drop", drop);
			target.dropArea.addEventListener("dragover", dragOver);
		} else {
			exception("请设置存取地址");
		}

	};
	/**  
	 * @description 事件绑定，兼容各浏览器  
	 * @param target 事件触发对象   
	 * @param type   事件  
	 * @param func   事件处理函数  
	 */
function addEvents(target, type, func) {
	if(target.addEventListener) //非ie 和ie9  
		target.addEventListener(type, func, false);
	else if(target.attachEvent) //ie6到ie8  
		target.attachEvent("on" + type, func);
	else target["on" + type] = func; //ie5  
};
/**  
 * @description 事件移除，兼容各浏览器  
 * @param target 事件触发对象  
 * @param type   事件  
 * @param func   事件处理函数  
 */
function removeEvents(target, type, func) {
	if(target.removeEventListener)
		target.removeEventListener(type, func, false);
	else if(target.detachEvent)
		target.detachEvent("on" + type, func);
	else target["on" + type] = null;
};
//  ========== 
//  = 开始拖动 = 
//  ========== 
var dragStart = function(e) {
		dragTarget = e.target;
		//区分拖拽的元素是要新增呢还是要交换位置，记录到flag上，1表示要新增，2表示交换位置
		if($(dragTarget).hasClass("moveNode") || $(dragTarget).hasClass("moveSubNode")) {
			deraction.flag = 2;
		} else {
			deraction.flag = 1;
		}
	};
	//  ========== 
	//  = 拖动经过 = 
	//  ========== 
var dragOver = function(e) {
		e.preventDefault();
		var pageLocation = getPageLocation();
		var index = -1;
		//检测目前鼠标正落在哪个表单元素上面
		var nodes = $("#"+dropAreaId).find(".moveNode");
    	var pos;
		if(nodes.length > 0) {
		/*	$.each(nodes,function(i){
				var pos = nodes[i].getBoundingClientRect();
			})*/
			for(var i = 0; i < nodes.length; i++) {
				pos = nodes[i].getBoundingClientRect();
				if(pageLocation.y >= pos.bottom)
					continue;
				index = i;
				break;
			}
		} else {
			index = 0;
			deraction["deraction"] = 0;
			deraction["index"] = -1;
			deraction["flag"] = 1;
			return;
		}

		if(index != -1) {
			pos = nodes[index].getBoundingClientRect();
			setBorderDefault();
			//鼠标落在表单元素宽度中间以上的部分，则上边变蓝
			if((pos.bottom + pos.top) / 2 > pageLocation.y) //元素的上边变蓝
			{
				deraction["deraction"] = -1;
				deraction["index"] = index;
				nodes[index].style.borderTop = "2px solid blue";
			} else //元素的下边变蓝
			{
				deraction["deraction"] = 1;
				deraction["index"] = index;
				nodes[index].style.borderBottom = "2px solid blue";
			}

		} else //当前拖拽的是第一个表单元素
		{
			deraction["deraction"] = 0;
			deraction["index"] = -1;
		}
	};
	//  ========== 
	//  = 放置 = 
	//  ========== 
var drop = function(e) {
		var node;
		var nodes = $("#"+dropAreaId).find(".moveNode");
		e.preventDefault();
		if(deraction.index != -1) {
			var index = deraction.index;
			if(deraction.deraction > 0) {

				//flag为1，插入表单元素，否则就是换位置
				if(deraction.flag == 1) {
					node = cearteNodeHtml(dragTarget);
					node.addEventListener("dragstart", dragStart);
					node.addEventListener("dragend", dragEnd);
				} else {
					node = dragTarget
				}
				insertAfter(node, nodes[index]);
			} else if(deraction.deraction < 0) {
				if(deraction.flag == 1) {
					node = cearteNodeHtml(dragTarget);
					node.addEventListener("dragstart", dragStart);
					node.addEventListener("dragend", dragEnd);
				} else {
					node = dragTarget
				}
				insertBefore(node, nodes[index]);
			}
		} else if(deraction.flag == 1) //第一个插入的表单元素
		{
			node = cearteNodeHtml(dragTarget);
			node.addEventListener("dragstart", dragStart);
			node.addEventListener("dragend", dragEnd);
			target.dropArea.appendChild(node)
		}
		deraction.index = -1;
		if(node.id!="hidden"){
			$(node).hover(showBtn, hideBtn);
			$(node).bind("click", function(e){
				nodeOnClick(e,this);
			});
			$(node).addClass(formData.layout);
		}
		nodes = $("#"+dropAreaId).find(".moveNode");
		$(target.dropArea).removeClass("level1");
		$.each(nodes,function(i){
			$(nodes[i]).data("order",i);
//			$(nodes[i]).data("pkno","dhcc-"+i);
			var lv = $(nodes[i]).data("lv");
			if((lv=="1")&&!$(target.dropArea).hasClass("level1")){
				$(target.dropArea).addClass("level1");
			}
		});
		saveData();
		parent.removeDargDashed();
	};
	//  ========== 
	//  = 拖动结束 = 
	//  ========== 
var dragEnd = function(e) {
	setBorderDefault();
};

//  ========== 
//  = 将每个表单区的上下边界恢复成原样 = 
//  ========== 
var setBorderDefault = function() {
	var nodes = $("#"+dropAreaId).find(".moveNode");
	$.each(nodes,function(i){
			$(nodes[i]).css({
				"border-bottom":"",
				"border-top":""
			});
	});
};

var cearteNodeHtml = function(node) {
	var html;
	switch(node.id) {
		case "jgg":
			html = jggHtml(node);
			break;
		case "btn":
			html = btnHtml(node);
			break;
		case "submit":
			html = submitHtml(node);
			break;
		case "xxy":
			html = xxyHtml(node);
			break;
		case "jdt":
			html = jdtHtml(node);
			break;
		case "bd":
			html = bdHtml(node);
			break;
		case "tabbar":
			html = tabbarHtml(node);
			break;
		case "dhl":
			html = dhlHtml(node);
			break;
		case "panel":
			html = panelHtml(node);
			break;
		case "text":
			html = textHtml(node);
			break;
		case "password":
			html = passwordHtml(node);
			break;
		case "textarea":
			html = textareaHtml(node);
			break;
		case "select":
			html = selectHtml(node);
			break;
		case "radio":
			html = radioHtml(node);
			break;
		case "checkbox":
			html = checkboxHtml(node);
			break;
		case "picker":
			html = pickerHtml(node);
			break;
		case "datepicker":
			html = datepickerHtml(node);
			break;
		case "timepicker":
			html = timepickerHtml(node);
			break;
		case "hidden":
			html = hiddenHtml(node);
			break;
		default:
			//html = $('<div draggable="true" class="moveNode">未知的类型</div>').get(0);
			break;
	}
	return html;
};
var setPkNo = function(node, $div) {
		$.each($(node).data(), function(k, v) {
			$div.attr("data-" + k, v);
		});
//		var nodes = $("#"+dropAreaId).find(".moveNode");
//		deraction.pkno = nodes.length;
		$div.attr("data-pkNo", deraction.prefix + "-" + (++deraction.pkno));
	};
	//九宫格
var jggHtml = function(node) {
		var $div = $('<div class="weui_grids" data-num="9"></div>');
		for(var i = 0; i < 9; i++) {
			var html = $('<a  data-name="按钮'+i+'" data-classtype="img" href="javascript:;"class="weui_grid js_grid"data-id="button"><div class="weui_grid_icon"><img src="img/icon_nav_button.png"alt=""></div><p class="weui_grid_label">Button</p></a>');
			html.attr("draggable", "false");
			$div.append(html);
		}
		$div.attr("draggable", "true");
		$div.addClass("moveNode");
		setPkNo(node,$div);
		return $div.get(0);
	};
//Tabbar
var tabbarHtml = function(node) {
	var $div = $('<div data-num="4" class="weui_tab"><div class="weui_tab_bd"></div><div class="weui_tabbar"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	var tab =  $div.find(".weui_tabbar");
	for (var i = 0; i < 4; i++) {
		var html = $('<a data-name="按钮'+i+'"  data-classtype="img" href="javascript:;"class="weui_tabbar_item weui_bar_item_on"><div class="weui_tabbar_icon"><img src="img/icon_nav_button.png"alt=""></div><p class="weui_tabbar_label">微信</p></a>');
		tab.append(html);
		html.attr("draggable", "false");
	}
	setPkNo(node,$div);
	return $div.get(0);
};
//导航栏
var dhlHtml = function(node) {
	var $div = $('<div  data-num="3" class="weui_tab"><div class="weui_navbar"></div><div class="weui_tab_bd"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	var nav = $div.find(".weui_navbar");
	var tab = 	$div.find(".weui_tab_bd");
	for (var i = 0; i < 3; i++) {
		$('<a href="#tab'+i+'" class="weui_navbar_item " data-name="选项'+i+'">选项'+i+'</a>').appendTo(nav);
		$('<div id="tab'+i+'"class="weui_tab_bd_item "><h1 class="doc-head">页面'+i+'</h1></div>').appendTo(tab);
	}
	setPkNo(node,$div);
	return $div.get(0);
};
//按钮
var btnHtml = function(node) {
	var $div = $('<a href="javascript:;" class="weui_btn weui_btn_default">按钮</a>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	$(node).data("css","weui_btn_default");
	setPkNo(node,$div);
	return $div.get(0);
};
//提交按钮
var submitHtml = function(node) {
	var $div = $('<a href="javascript:;" class="weui_btn weui_btn_default">提交按钮</a>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	$(node).data("css","weui_btn_default");
	setPkNo(node,$div);
	return $div.get(0);
};
	//消息页
var xxyHtml = function(node) {
	var $div = $('<div class="weui_msg"><div class="weui_icon_area"><i class="weui_icon_success weui_icon_msg"></i></div><div class="weui_text_area"><h2 class="weui_msg_title">操作成功</h2><p class="weui_msg_desc">内容详情，可根据实际需要安排</p></div><div class="weui_opr_area"><p class="weui_btn_area"><a href="javascript:;"class="weui_btn weui_btn_primary">确定</a><a href="javascript:;"class="weui_btn weui_btn_default">取消</a></p></div><div class="weui_extra_area"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	setPkNo(node,$div);
	return $div.get(0);
};
//进度条
var jdtHtml = function(node) {
	var $div = $('<div class="weui_progress"><div class="weui_progress_bar"><div class="weui_progress_inner_bar js_progress"style="width: 50%;"></div></div><a href="javascript:;"class="weui_progress_opr"></a></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	setPkNo(node,$div);
	return $div.get(0);
};
//表单
var bdHtml = function(node) {
	var $div = $('<div class="weui_cells weui_cells_form" style="min-width:100px;"></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	setPkNo(node,$div);
	return $div.get(0);
};

//图文列表
var panelHtml = function(node) {
	var $div = $('<div class="weui_panel weui_panel_access"><div class="weui_panel_hd">列表标题</div><div class="weui_panel_bd"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	var bd = 	$div.find(".weui_panel_bd");
	for (var i = 0; i < 2; i++) {
		$('<a href="javascript:void(0);"class="weui_media_box weui_media_appmsg"><div class="weui_media_hd"><img class="weui_media_appmsg_thumb" src="img/icon_nav_button.png" alt=""></div><div class="weui_media_bd"><h4 class="weui_media_title">小标题'+i+'</h4><p class="weui_media_desc">业务描述</p></div></a>').appendTo(bd);
	}
	setPkNo(node,$div);
	return $div.get(0);
};
//输入框
var textHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">输入框</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="请输入"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	setPkNo(node,$div);
	return $div.get(0);
};
//密码域
var passwordHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">输入框</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="password"placeholder="请输入"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	setPkNo(node,$div);
	return $div.get(0);
};
//文本域
var textareaHtml = function(node) {
	var $div = $('<div data-datatype="0"><div class="weui_cells_title">文本域</div><div class="weui_cells weui_cells_form"><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><textarea class="weui_textarea"placeholder="请输入评论"rows="3"></textarea></div></div></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	setPkNo(node,$div);
	return $div.get(0);
};
//下拉框
var selectHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell weui_cell_select weui_cell_select_label"><div class="weui_cell_hd"><label class="weui_label">下拉选择</label></div><div class="weui_cell_bd weui_cell_primary"><select class="weui_select"><option selected="">选择</option></select></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	setPkNo(node,$div);
	return $div.get(0);
};
//复选框
var checkboxHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">复选框</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="复选框"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	/*$div.find("input.weui_input").select({
		  title: "请选择",
		  multi: true,
		  items: [
		    {
		      title: "选项一",
		      value: "001",
		    },
		    {
		      title: "选项二",
		      value: "002",
		    },
		    {
		      title: "选项三",
		      value: "003",
		    }
		  ]
		});*/
	setPkNo(node,$div);
	return $div.get(0);
};
//单选框
var radioHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">单选框</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="单选框"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	/*$div.find("input.weui_input").select({
		  title: "请选择",
		  items: [
		    {
		      title: "选项一",
		      value: "001",
		    },
		    {
		      title: "选项二",
		      value: "002",
		    }
		  ]
		});*/
	setPkNo(node,$div);
	return $div.get(0);
};

//选择器
var pickerHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">选择器</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="选择器"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	/*$div.find("input.weui_input").picker({
		title: "请选择",
		  cols: [
		    {
		      textAlign: 'center',
		      values: ['1', '2', '3', '4', '5', '6', '7', '8'],
		      displayValues: ['选项一', '选项二', '选项三', '选项四', '选项五', '选项六', '选项七', '选项八']
		    }
		  ]
	});*/
	setPkNo(node,$div);
	return $div.get(0);
};
//日历
var datepickerHtml = function(node) {
	var $div = $('<div data-datatype="5" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">日期</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="选择器"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	$div.find("input.weui_input").calendar();
	setPkNo(node,$div);
	return $div.get(0);
};
//日期时间选择器
var timepickerHtml = function(node) {
	var $div = $('<div data-datatype="6" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">日期时间</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="选择器"></div></div>');
	$div.attr("draggable", "true");
	$div.addClass("moveNode");
	$div.find("input.weui_input").datetimePicker();
	setPkNo(node,$div);
	return $div.get(0);
};
//隐藏域
var hiddenHtml = function(node) {
	var $div = $('<input data-datatype="0" type="hidden">');
	$div.addClass("moveNode");
	setPkNo(node,$div);
	$div.attr("id",$div.data("pkno"));
	var $li = $('<li pkno="'+$div.data("pkno")+'"><span>隐藏域'+$div.data("pkno")+'</span><i class="fa fa-trash-o"></i></li>');
	$("#consoleTree", parent.document).append($li);
	$li.bind("click",function(e){
		nodeOnClick(e,$("#"+$(this).attr("pkno")).get(0));
	});
	$li.find(".fa-trash-o").bind("click",function(){
		$.confirm("确认删除该元素吗？", function() {
			  //点击确认后的回调函数
				$("#"+$li.attr("pkno")).remove();
				saveData();
				$li.remove();
			});
	});
	return $div.get(0);
};