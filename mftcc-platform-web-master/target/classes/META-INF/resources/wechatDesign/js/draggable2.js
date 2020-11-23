var startdrag,
	stopsave;
var deraction = {
	index : -1,
	deraction : 0, //-1上，0第一个，1下
	flag : -1,
	prefix : "dhcc",
	pkno : 0
};
//正在拖拽的元素
var dragTarget;
var target = {
	dropArea : null
};
function initDraggable() {
	initContainer();
	$(".weui_tab_bd .weui_grid").draggable({
		connectToSortable : ".column",
		helper : function(e) {
			var node = cearteNodeHtml(e.target);
			$(node).width(250);
			return $(node);
		},
		start : function(e, t) {
			if (!startdrag) stopsave++;
			startdrag = 1;
		},
		drag : function(e, t) {},
		stop : function(e, t) {
			if (t.helper.attr("data-property") == "panel") {
				t.helper.height("auto");
				$(".column").sortable({
					opacity : .35,
					connectWith : ".column",
					start : function(e, t) {
						var width = $("#dropArea").width();
						t.helper.width(width - 28);
						t.helper.css("z-index", "1000");
						t.helper.removeClass("moveNode");
						t.placeholder.removeClass("weui_panel");
						if (!startdrag) stopsave++;
						startdrag = 1;
					},
					stop : function(e, t) {
						t.item.width("auto");
						t.item.height("auto");
						t.item.css("position", "relative");
						t.item.css("z-index", "0");
						t.item.addClass("moveNode");
						if (stopsave > 0) stopsave--;
						startdrag = 0;
						saveData();
					}
				});
			}
			dragEnd(t.helper);

			if (stopsave > 0) stopsave--;
			startdrag = 0;
		}
	});
	$(".window").mCustomScrollbar({
		theme : "minimal-dark"
	});
	$("#mCSB_1_scrollbar_vertical").css("right", "-6px");
}

function dragEnd(node) {
	node.hover(showBtn, hideBtn);
	node.bind("click", function(e) {
		nodeOnClick(e, this);
	});
//	node.addClass(formData.layout);
}
function initContainer() {
	$(".column").sortable({
		connectWith : ".column",
		opacity : .35,
		//		handle: ".weui_cell",
		start : function(e, t) {
			var width = $("#dropArea").width();
			t.helper.width(width - 28);
			t.helper.css("z-index", "1000");
			t.helper.removeClass("moveNode");
			t.placeholder.removeClass("weui_panel");
			if (!startdrag) stopsave++;
			startdrag = 1;
		},
		stop : function(e, t) {
			t.item.width("auto");
			t.item.height("auto");
			t.item.css("position", "relative");
			t.item.css("z-index", "0");
			t.item.addClass("moveNode");
			if (stopsave > 0) stopsave--;
			startdrag = 0;
			saveData();
		}
	});
}

var cearteNodeHtml = function(node) {
	var html;
	switch (node.id) {
	case "jgg":
		html = jggHtml(node);
		break;
	case "btn":
		html = btnHtml(node);
		break;
	case "submit":
		html = submitHtml(node);
		break;
	case "label":
		html = labelHtml(node);
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
	$div.attr("data-pkNo", deraction.prefix + "-" + (++deraction.pkno));
};
//按钮
var btnHtml = function(node) {
	var $div = $('<a href="javascript:;" class="weui_btn weui_btn_default">按钮</a>');

	$div.addClass("moveNode");
	$(node).data("css", "weui_btn_default");
	setPkNo(node, $div);
	return $div.get(0);
};
var submitHtml = function(node) {
	var $div = $('<a href="javascript:;" class="weui_btn weui_btn_default">提交按钮</a>');

	$div.addClass("moveNode");
	$(node).data("css", "weui_btn_default");
	setPkNo(node, $div);
	return $div.get(0);
};
var labelHtml = function(node) {
	var $div = $('<p class="weui_grid_label">变量值</p>');
	$div.addClass("moveNode");
	setPkNo(node, $div);
	return $div.get(0);
};

//容器
var panelHtml = function(node) {
	var $div = $('<div class="weui_panel weui_panel_access"><div class="weui_panel_hd">容器</div><div class="weui_panel_bd column" style="min-height:20px;"></div></div>');
	$div.addClass("moveNode")
		.css("height", "auto")
		.css("border", "1px solid #e5e5e5");
	$div.attr("data-titleShow", "0");
	$div.attr("data-grid", "1");
	setPkNo(node, $div);
	return $div.get(0);
};
//输入框
var textHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">输入框</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="请输入"></div></div>');

	$div.addClass("moveNode");
	setPkNo(node, $div);
	return $div.get(0);
};
//密码域
var passwordHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">输入框</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="password"placeholder="请输入"></div></div>');

	$div.addClass("moveNode");
	setPkNo(node, $div);
	return $div.get(0);
};
//文本域
var textareaHtml = function(node) {
	var $div = $('<div data-datatype="0"><div class="weui_cells_title">文本域</div><div class="weui_cells weui_cells_form"><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><textarea class="weui_textarea"placeholder="请输入评论"rows="3"></textarea></div></div></div></div>');

	$div.addClass("moveNode");
	setPkNo(node, $div);
	return $div.get(0);
};
//下拉框
var selectHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell weui_cell_select weui_cell_select_label"><div class="weui_cell_hd"><label class="weui_label">下拉选择</label></div><div class="weui_cell_bd weui_cell_primary"><select class="weui_select"><option selected="">选择</option></select></div></div>');

	$div.addClass("moveNode");
	setPkNo(node, $div);
	return $div.get(0);
};
//复选框
var checkboxHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">复选框</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="复选框"></div></div>');

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
	setPkNo(node, $div);
	return $div.get(0);
};
//单选框
var radioHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">单选框</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="单选框"></div></div>');

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
	setPkNo(node, $div);
	return $div.get(0);
};

//选择器
var pickerHtml = function(node) {
	var $div = $('<div data-datatype="0" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">选择器</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="选择器"></div></div>');

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
	setPkNo(node, $div);
	return $div.get(0);
};
//日历
var datepickerHtml = function(node) {
	var $div = $('<div data-datatype="5" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">日期</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="选择器"></div></div>');

	$div.addClass("moveNode");
	//$div.find("input.weui_input").calendar();
	setPkNo(node, $div);
	return $div.get(0);
};
//日期时间选择器
var timepickerHtml = function(node) {
	var $div = $('<div data-datatype="6" class="weui_cell"><div class="weui_cell_hd"><label class="weui_label">日期时间</label></div><div class="weui_cell_bd weui_cell_primary"><input class="weui_input"type="text"placeholder="选择器"></div></div>');

	$div.addClass("moveNode");
	//$div.find("input.weui_input").datetimePicker();
	setPkNo(node, $div);
	return $div.get(0);
};
//隐藏域
var hiddenHtml = function(node) {
	var $div = $('<input data-datatype="0" type="hidden">');
	$div.addClass("moveNode");
	setPkNo(node, $div);
	$div.attr("id", $div.data("pkno"));
	var $li = $('<li pkno="' + $div.data("pkno") + '"><span>隐藏域' + $div.data("pkno") + '</span><i class="fa fa-trash-o"></i></li>');
	$("#consoleTree", document).append($li);
	$li.bind("click", function(e) {
		nodeOnClick(e, $("#" + $(this).attr("pkno")).get(0));
	});
	$li.find(".fa-trash-o").bind("click", function() {
		$.confirm("确认删除该元素吗？", function() {
			//点击确认后的回调函数
			$("#" + $li.attr("pkno")).remove();
			saveData();
			$li.remove();
		});
	});
	return $div.get(0);
};