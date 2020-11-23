var dicType =  {
	    "y_n": {
	        "parm": [{"optName": "是","optCode": "0"},{"optName": "不是","optCode": "99"}],
	        "type": "0"
	    },
	    "num": {
	        "parm": [{"optName": "等于","optCode": "0"},{"optName": "大于","optCode": "1"},{"optName": "小于","optCode": "2"},{"optName": "在...之间","optCode": "3"},{"optName": "不在...之间","optCode": "4"}],
	        "type": "1"
	    },
	    "val": {
	    	 "parm": [{"optName": "是","optCode": "3"}],
	        "type": "2"
	    },
	    "date": {
	        "parm": [{"optName": "等于","optCode": "0"},{"optName": "大于","optCode": "1"},{"optName": "小于","optCode": "2"},{"optName": "在...之间","optCode": "3"},{"optName": "不在...之间","optCode": "4"},{"optName": "期限","optCode": "5"}],
	        "type": "4"
	    },
	    "y_n_l": {
	        "parm": [{"optName": "是","optCode": "0"},{"optName": "不是","optCode": "99"}],
	        "type": "2"
	    },
	};
//日期期限类型
var filterDateDic = [ {"optName": "本月", "optCode": "0"},
                      {"optName": "上月", "optCode": "1"},
                      {"optName": "30天内", "optCode": "2"},
                      {"optName": "未来30天","optCode": "3"},
                      {"optName": "多少天内","optCode": "4"},
                      {"optName": "未来多少天","optCode": "5"}
                      ];
var andorOpt = [
                  {"optName": "与", "optCode": "0"},
                  {"optName": "或","optCode": "1"}
              ];

var filterCol = [
//	             	{name:"cusType"},
//	             	{arr:[  
//	             	 	{"value":"1","col":"cusName,cusType,regDate,lstDate"},
//		                {"value":"2","col":"cusName,cusType,idType,idNo"},
//		                {"value":"common","col":"cusName,cusType,idType,idNo,manageOrgName,manageName,regDate,lstDate"}
//	                 ]}
              ];
var filterVal = [];//全部
var filterDateFormat = {"show":"YYYY-MM-DD","value":"YYYYMMDD"};//日期类型格式化,show是显示格式，value是后台接受到的格式
var topVal = [];//默认置顶的数据
var moreVal = [];//更多中的数据
var IDMark_A = "_a";
var IDMark_SPAN = "_span";
var IDMark_SWITCH = "_switch";
var IDMark_ICO = "_ico";
var IDMark_PILL = "_pill";
var filter_dic=[];
var filter_def =[];
var filterNo_def = null;
var globalNode = null;//处理列表页面和弹层页面选中状态同步使用
var currEidtNode = null;//筛选管理弹层页面当前选中编辑的节点
function create_def_tree(treeId,defFilterArr){
	var filter_def_setting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showLine: false,
			showIcon: true,
			addDiyDom: addDiyDomDef
		},
		callback: {
			onClick: filterOnClick
		}
	};
	$.fn.zTree.init($("#"+treeId), filter_def_setting, defFilterArr);
}


//初始化筛选条件数组
function setFilterVal(){
	globalNode =null;
	//筛选列表初始化
	var filter_setting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showLine: false,
			showIcon: true,
			addHoverDom: addEditDiv,
			removeHoverDom: hideEditDiv,
			addDiyDom: addDiyDomDef
		},
		callback: {
			onClick: filterOnClick
		}
	};
	var more_filter_setting = {
			check: {
				enable: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				showLine: false,
				showIcon: true,
				addHoverDom: addEditDiv_more,
				removeHoverDom: hideEditDiv,
				addDiyDom: addDiyDomDef
			},
			callback: {
				onClick: filterOnClick
			}
	};

	$.ajax({
		type : 'post',
		url :webPath+"/pmsUserFilter/findByList" ,
		dataType : 'json',
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
//			filterVal = data.json;
			topVal = data.topjson;//默认置顶的
			moreVal = data.morejson;//更多
			var morelistSize = data.morelistSize;
			if(morelistSize>0){
				$.fn.zTree.init($("#my_more"), more_filter_setting, moreVal);
				$("#filter_list .dropdown").removeClass("hide");
				$("#filter_list .dropdown").addClass("show");
			}else{
				$("#my_more").empty();
				$("#filter_list .dropdown").removeClass("show");
				$("#filter_list .dropdown").addClass("hide");
			}
			var nodes =$.fn.zTree.init($("#my_filter"), filter_setting, topVal).getNodes();
			if(filterNo_def != null){
				var nodeObj = null;
				$.each(nodes, function(i,obj) {
					if (obj.filterNo==filterNo_def) {
						nodeObj=obj;
					}
				});
				if(nodeObj != null){
					filterOnClick(null,"my_filter",nodeObj);
				}
			}
			//默认选中全部
			if(typeof (filterFrom) != "undefined" && filterFrom == "cusList"){
                $("#my_filter_2_a").click();
			}else{
                $("#my_filter_1").addClass("mySelectedNode");
            }
			// 公共的不允许点击，否则列表加载两次ajax请求。
			// $("#my_filter_1_a").click();
		}
	});
}

function addEditDiv (treeId, treeNode) {
	if (treeNode.optType == "0") {
		// optType为0-默认项不允许隐藏不允许编辑
		return;
	}
	
	var aObj = $("#" + treeNode.tId + "_a");
	// 自定义的控件
	if ($("#diyBtn_"+treeNode.id).length>0) {
		$("#diyBtn_"+treeNode.id).show();
		return;
	}
	//默认项-可隐藏不可编辑
	var editStr = "<span id='diyBtn_"+treeNode.id+"' class='diyBtn'>" +
				 "<i class='i i-x2' id='diyDelBtn_" + treeNode.id + "' title='取消默认' onfocus='this.blur();'></i></span>";	
	//自定义项-可隐藏可编辑
	if(treeNode.optType=="2"){
		editStr = "<span id='diyBtn_"+treeNode.id+"' class='diyBtn'>" +
		"<i class='i i-x2' id='diyDelBtn_" + treeNode.id + "' title='取消默认' onfocus='this.blur();'></i>" +
		"<br/>" +
		"<i class='i i-bi' id='diyEditBtn_" + treeNode.id + "' title='编辑' onfocus='this.blur();'></i>" +
		"</span>";
	}

	aObj.removeAttr("href");
	aObj.append(editStr);
	// 绑定事件
	if(treeNode.optType=="2"){
		var editBtn = $("#diyEditBtn_" + treeNode.id);
		if (editBtn) {
			editBtn.bind("click", function(event) {
				globalNode = treeNode;
				currEidtNode = treeNode;
				showFilterBox(treeNode.filterContent,treeNode.filterName,treeNode.filterNo);
				$("#del_filter_item").show();
				// 组织冒泡到a标签上。
				event.stopPropagation();
				return false;
			});
		}
	}
	var delBtn = $("#diyDelBtn_" + treeNode.id);
	if (delBtn) {
		delBtn.bind("click", function(event) {
			// 组织冒泡到a标签上。
			event.stopPropagation();
//			delFilterGroup(treeId,treeNode);
			if($("#"+treeNode.tId).hasClass("mySelectedNode")){
				$("#"+treeNode.tId+IDMark_PILL).remove();
			}
			alert(top.getMessage("CONFIRM_OPERATION","取消默认"),2,function(){
				updateSts(treeId, treeNode);
			});
			return false;
		});
	}
}

//更多下的筛选项编辑事件
function addEditDiv_more (treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	// 自定义的控件
	if ($("#diyBtn_"+treeNode.id).length>0) {
		$("#diyBtn_"+treeNode.id).show();
		return;
	}
	//默认项-可显示不可编辑
	var editStr = "<span id='diyBtn_"+treeNode.id+"' class='diyBtn'>" +
				"<i class='i i-duihao1' id='diyDelBtn_" + treeNode.id + "' title='设为默认' onfocus='this.blur();'></i></span>";
	//自定义项-可隐藏可编辑
	if(treeNode.optType=="2"){
		editStr =   "<span id='diyBtn_"+treeNode.id+"' class='diyBtn'>" +
					"<i class='i i-duihao1' id='diyDelBtn_" + treeNode.id + "' title='设为默认' onfocus='this.blur();'></i>" +
					"<br/>" +
					"<i class='i i-bi' id='diyEditBtn_" + treeNode.id + "' title='编辑' onfocus='this.blur();'></i>" +
					"</span>";
	}
	
	
	aObj.removeAttr("href");
	aObj.append(editStr);
	
	// 绑定事件
	if(treeNode.optType=="2"){
	var editBtn = $("#diyEditBtn_" + treeNode.id);
		if (editBtn) {
			editBtn.bind("click", function(event) {
				globalNode = treeNode;
				currEidtNode = treeNode;
				showFilterBox(treeNode.filterContent,treeNode.filterName,treeNode.filterNo);
				$("#del_filter_item").show();
				// 组织冒泡到a标签上。
				event.stopPropagation();
				return false;
			});
		}
	}
	var delBtn = $("#diyDelBtn_" + treeNode.id);
	if (delBtn) {
		delBtn.bind("click", function(event) {
			// 组织冒泡到a标签上。
			event.stopPropagation();
//			delFilterGroup(treeId,treeNode);
			if($("#my_filter li").length<5){
				
				if($("#"+treeNode.tId).hasClass("mySelectedNode")){
					$(".dropdown button").removeClass("moreCurr");
					$("#"+treeNode.tId+IDMark_PILL).remove();
				}
				alert(top.getMessage("CONFIRM_OPERATION","设为默认"),2,function(){
					updateSts(treeId, treeNode);
				});
				
			}else{
				alert(top.getMessage("NOT_FILTER_NUMBER"),0);
			}
			return false;
		});
	}
}
function hideEditDiv (treeId, treeNode) {
	$("#diyBtn_"+treeNode.id).hide();
}

//异步更新筛选项状态
function updateSts(treeId, treeNode){
	//更新选中状态
	if(treeNode.useFlag=="1"){
		treeNode.useFlag="0";
	}else{
		treeNode.useFlag="1";
	}
	var parmData ={"opNo":treeNode.opNo,"useFlag":treeNode.useFlag,"filterNo":treeNode.filterNo,"jsp":treeNode.jsp,"url":treeNode.url}
	$.ajax({
		type : 'post',
		url :webPath+"/pmsUserFilter/updateStsAjax" ,
		dataType : 'json',
		data:parmData,
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
			if(data.flag=="success"){
			
			setFilterVal();
//				if(treeId=="my_more"){
//					var treeObj = $.fn.zTree.getZTreeObj('my_filter');
//					treeObj.addNodes(null,treeNode);
//					var  icoObj =$("#my_filter").find("span.button");
//					icoObj.removeClass("ico_docu");
//					if($("#my_more li").length==0){
//						$("#filter_list .dropdown").removeClass("show");
//						$("#filter_list .dropdown").addClass("hide");
//					}
//				}else{
//					if($("#my_more li").length==0){
//						
//					}else{
//						var treeObj = $.fn.zTree.getZTreeObj('my_more');
//						treeObj.addNodes(null,treeNode);
//						var  icoObj =$("#my_more").find("span.button");
//						icoObj.removeClass("ico_docu");
//						if($("#my_more li").length>0){
//							$("#filter_list .dropdown").removeClass("hide");
//							$("#filter_list .dropdown").addClass("show");
//						}
//					}
//				}
//				$.fn.zTree.getZTreeObj(treeId).removeNode(treeNode);
//				updateTableData(true);
				
			}
		}
	});
}

$(function() {
	//展开筛选列表
//	$("#ckl").mCustomScrollbar({advanced:{
//            autoExpandHorizontalScroll:true}
//            });
//	$("#fiter_ctrl_btn").bind("click", function() {
//		$('#ckl').is(':hidden') ? $('#ckl').slideDown(function() {
//			$("#ckl").mCustomScrollbar("update");
//		}) : $('#ckl').slideUp();
//	});
	
	$("#filter_in_input").on('keydown', function(e) {
		if(e.keyCode==13){
			if(submitFilter()){
				updateTableData(true);
			}
		}
	});
	$("#filter_btn_search").on('click', function(e) {
		if(submitFilter()){
			updateTableData(true);
		}
	});
	
	//关闭按钮绑定事件	
	$("#close, #close_filter").click(function() {
		closeFilterBox();
	});
	//关闭弹出框
	$("#close").click(function(){
		closeFilterBox();
	})
	//新增按钮绑定事件
	$("#add_filter").click(function() {
		addFilterTreeObj();
	});
	//保存按钮绑定事件
	$("#save_filter").click(function() {
		$("#filter_name_box").modal("show");
	});
	$("#save_filter_db").click(function() {
		saveFilterBox();
	});
	$("#del_filter_item").click(function() {
		delFilterItem();
	});

	setFilterVal();
	
});

function submitFilter(){
	if($("#filter_action_val").length>0&&$('#filter_action').length>0){
		$("#filter_action_val").val(getFilterValArr());
		$('#filter_action').submit();
		return false;
	}else{
		return true;
	}
}

//自定义生成筛选列表
function addDiyDom(treeId, treeNode) {
	if($("#" + treeNode.tId + "_pill").length>0){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.checkNode(treeNode,true, true);
		};
	$("#" + treeNode.tId + IDMark_SWITCH).remove();
	$("#" + treeNode.tId + IDMark_ICO).remove();
	var aObj = $("#" + treeNode.tId + IDMark_A);
	var editStr = "<i class='i i-x3' id='diyDelBtn_" + treeNode.id + "' title='删除' onfocus='this.blur();'></i><i class='i i-bi' id='diyEditBtn_" + treeNode.id + "' title='编辑' onfocus='this.blur();'></i>";
	aObj.html(treeNode.filterName);
	aObj.removeAttr("href");
	aObj.after(editStr);
	var editBtn = $("#diyEditBtn_" + treeNode.id);
	if (editBtn) {
		editBtn.bind("click", function() {
			showFilterBox(treeNode.filterContent,treeNode.filterName,treeNode.filterNo);
		});
	}
	var delBtn = $("#diyDelBtn_" + treeNode.id);
	if (delBtn) {
		delBtn.bind("click", function() {
			delFilterGroup(treeId,treeNode);
		});
	}
}
function addDiyDomDef(treeId, treeNode) {
	if($("#" + treeNode.tId + "_pill").length>0){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.checkNode(treeNode,true, true);
	};
	$("#" + treeNode.tId + IDMark_SWITCH).remove();
	var aObj = $("#" + treeNode.tId + IDMark_A);
	aObj.attr("title",treeNode.filterName);
	aObj.removeAttr("href");
	var spanObj = $("#" + treeNode.tId + IDMark_SPAN).text(treeNode.filterName);
	if(treeNode.filterName.length>8){
		spanObj = $("#" + treeNode.tId + IDMark_SPAN).text(treeNode.filterName.substring(0,8)+"...");
	}
	spanObj.wrap($("<div>", {"class": "filter_option"}));
	//去掉图标
	$("#" + treeNode.tId + IDMark_ICO).remove();
	//处理图标
//	var  icoObj =$("#" + treeNode.tId + IDMark_ICO);
//	icoObj.removeClass("ico_docu");
//	icoObj.append("<i class='i i-radio1'></i>");
//	icoObj.append("<i class='i i-radio2'></i>");
	
}

function addDiyDomItemDef(treeId, treeNode) {
	if(globalNode!=null){
		var bObj = $("#" + globalNode.tId);
//		if(bObj.hasClass("mySelectedNode")){
			if(globalNode.filterNo ==treeNode.filterNo){
				$("#" + treeNode.tId).addClass("mySelectedNode");
				treeNode.checked = true;
				globalNode = null;
			}
//		}
	}

	if($("#" + treeNode.tId + "_pill").length>0){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.checkNode(treeNode,true, true);
	};
	$("#" + treeNode.tId + IDMark_SWITCH).remove();
	var aObj = $("#" + treeNode.tId + IDMark_A);
	aObj.attr("title",treeNode.filterName);
	aObj.removeAttr("href");
	var spanObj = $("#" + treeNode.tId + IDMark_SPAN).text(treeNode.filterName);
	if(treeNode.filterName.length>8){
		spanObj = $("#" + treeNode.tId + IDMark_SPAN).text(treeNode.filterName.substring(0,8)+"...");
	}
	spanObj.wrap($("<div>", {"class": "filter_option"}));
	//处理图标
	var  icoObj =$("#" + treeNode.tId + IDMark_ICO);
	icoObj.removeClass("ico_docu");
	if(treeNode.useFlag=="1"){
		icoObj.append("<i class='i i-gouxuan show'></i>");
	}else{
		icoObj.append("<i class='i i-gouxuan hide'></i>");
	}
	
}

//获取筛选值
function getFilterValArr(){ 
	var str=[];
	var rs = {};
	$(".filter-val").each(function(){
		if(eval($(this).find("input[type=hidden]").val())!=""){
			if(eval($(this).find("input[type=hidden]").val())[0].treeId!="my_filter"){
				rs[eval($(this).find("input[type=hidden]").val())[0].treeId]= [];
			}
		}
	});
	$(".filter-val").each(function(){
		if(eval($(this).find("input[type=hidden]").val())!=""){
			if(eval($(this).find("input[type=hidden]").val())[0].treeId!="my_filter"){
				rs[eval($(this).find("input[type=hidden]").val())[0].treeId].push(eval($(this).find("input[type=hidden]").val())[0]);
			}else{
				str.push(eval($(this).find("input[type=hidden]").val()));
			}
		}
	});
	
	$.each(rs,function(i,obj){
		str.push(obj);
	});
	var customQuery = {customQuery:$("#filter_in_input").val()};
	var customQuerySort = {customSorts:$("#tableSortVal").val()};
	str.push(customQuery);
	str.push(customQuerySort);
	return JSON.stringify(str);
}
//添加筛选
var addPill = function(tId,treeNode,treeId,callback) {
	var $div = $("#pills");
	$div.find("li.filter-val").remove();
	$item = $('<li class="label fa filter-val" style="display:none;" id="' +tId+IDMark_PILL+ '" >' + treeNode.filterName + '</li> ');
	var $hide = $("<input id='"+tId+"_hideVal' type='hidden'/>");
	var obj = eval('('+treeNode.filterContent+')');
	for ( var i = 0; i < obj.length; i++) {
		obj[i].tId = tId;
		obj[i].filterName = treeNode.filterName;
		if(obj[i].dicType=="4"){
			if(obj[i].type=="5"){
				if(obj[i].value=="0"){//本月
					obj[i].value = moment().startOf('month').format(filterDateFormat.value);
					obj[i].secondValue = moment().endOf('month').format(filterDateFormat.value);
				}else if(obj[i].value=="1"){//上月
					obj[i].value = moment().subtract(1,'month').startOf('month').format(filterDateFormat.value);
					obj[i].secondValue = moment().subtract(1,'month').endOf('month').format(filterDateFormat.value);
				}else if(obj[i].value=="2"){//30天内
					obj[i].value = moment().subtract(30,'days').format(filterDateFormat.value);
					obj[i].secondValue = moment().format(filterDateFormat.value);
				}else if(obj[i].value=="3"){//未来30天
					obj[i].value = moment().format(filterDateFormat.value);
					obj[i].secondValue = moment().add(30,'days').format(filterDateFormat.value);
				}else if(obj[i].value=="4"){//多少天内
					obj[i].value = moment().subtract(obj[i].secondValue,'days').format(filterDateFormat.value);
					obj[i].secondValue = moment().format(filterDateFormat.value);
				}else if(obj[i].value=="5"){//未来多少天
					obj[i].value = moment().format(filterDateFormat.value);
					obj[i].secondValue = moment().add(obj[i].secondValue,'days').format(filterDateFormat.value);
				}
			}else{
				obj[i].value = moment(obj[i].value, filterDateFormat.show).format(filterDateFormat.value);  
				if(obj[i].betweenValue){
					obj[i].secondValue = moment(obj[i].secondValue, filterDateFormat.show).format(filterDateFormat.value);  
				}
			}
		}else if(obj[i].dicType=="1"&&obj[i].unit){
			obj[i].value = convertByUnit(obj[i].value,obj[i].unit);  
			if(obj[i].betweenValue){
				obj[i].secondValue = convertByUnit(obj[i].secondValue,obj[i].unit);  
			}
		}
	}
	$hide.val(JSON.stringify(obj));
	$hide.appendTo($item);
	$div.prepend($item);
	$item.click(function() {
		delPill(tId,treeNode,treeId,filterOnCheckCallback);
	});
	if(callback){
		callback.call(this,treeId,treeNode);
	}
	if(submitFilter()){
		updateTableData(true);
	}
};
//删除筛选
/* added "isNeedUpdate" by LiuYF
 * 在单选一个筛选时，清除原条件、增加新条件， 避免两次查询、两次更新列表数据
 */
var delPill = function(tId,treeNode,treeId,callback, isNeddUpdate) {
	$("#"+tId+IDMark_PILL).remove();
	treeNode.checked=false;
	if(callback){
		callback.call(this,treeId,treeNode);
	}
	if (isNeddUpdate !== false) {
		updateTableData(true);
	}
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	treeObj.checkNode(treeNode, false, true);
};
var addPillTemp = function(tId,filterContent,treeId,callback) {
	var $div = $("#pills");
	$item = $('<li class="label fa filter-val" id="' +tId+IDMark_PILL+ '_temp" >' + filterContent.filterName + '</li> ');
	var $hide = $("<input id='"+tId+"_hideVal' type='hidden'/>");
	$hide.val("["+JSON.stringify(filterContent)+"]");
	$hide.appendTo($item);
	var $xi = $("<i class='i i-x1'></i>");
	$xi.appendTo($item);
	$div.prepend($item);
	$item.click(function() {
//		$div.find('>li:last').click(function() {
		delPillTemp(tId,filterOnCheckCallback);
	});
	if(callback){
		callback.call(this);
	}
	updateTableData(true);
};
var delPillTemp = function(tId,callback) {
	$("#"+tId+IDMark_PILL+"_temp").remove();
	if(callback){
		callback.call(this);
	}
	updateTableData(true);
};
//复选框回调方法
var filterOnCheck = function(event, treeId, treeNode){
	if(treeNode.checked){
		addPill(treeNode.tId,treeNode,treeId,filterOnCheckCallback);
	}else{
		delPill(treeNode.tId,treeNode,treeId,filterOnCheckCallback);
	}
};
//列表页面筛选按钮的onclick事件
var filterOnClick = function(event, treeId, treeNode){
	var treeIdTmp =treeId; 
	if(treeId=="my_more"){
		treeIdTmp="my_filter";
		$(".dropdown button").addClass("moreCurr");
	}else if(treeId=="my_filter"){
		treeIdTmp="my_more";
		$(".dropdown button").removeClass("moreCurr");
	}
	var tmpTree = $.fn.zTree.getZTreeObj(treeIdTmp);
	/**
	 * 初始化进来，给第一个全部节点加已选中样式，
	 * 但没有触发点击操作。当点击其他节点时去掉选中样式。
	 */
	if($("#my_filter_1").attr("class").indexOf("mySelectedNode")!="-1"){
		$("#my_filter_1").removeClass("mySelectedNode");
	}
	if (tmpTree) {
		tmpTree.cancelSelectedNode(treeNode);
		$.each($.fn.zTree.getZTreeObj(treeIdTmp).getCheckedNodes(),function(i,obj){
			$("#" + obj.tId).removeClass("mySelectedNode");
			delPill(obj.tId,obj,treeIdTmp,filterOnCheckCallback, false);
		});
	}
	$.fn.zTree.getZTreeObj(treeId).cancelSelectedNode(treeNode);
	$.each($.fn.zTree.getZTreeObj(treeId).getCheckedNodes(),function(i,obj){
		$("#" + obj.tId).removeClass("mySelectedNode");
		delPill(obj.tId,obj,treeId,filterOnCheckCallback, false);
	});
	treeNode.checked = true;
	var aObj = $("#" + treeNode.tId);
	aObj.addClass("mySelectedNode");
	addPill(treeNode.tId,treeNode,treeId,filterOnCheckCallback);
};
//筛选项管理页面按钮的onclick事件
var filterItemOnClick = function(event, treeId, treeNode){
//	var targetObj = event.target ? event.target : event.srcElement;
//	if ($(targetObj).is("a>div,a>div>span")) {
		$.fn.zTree.getZTreeObj(treeId).cancelSelectedNode(treeNode);
		$.each($.fn.zTree.getZTreeObj(treeId).getCheckedNodes(),function(i,obj){
			$("#" + obj.tId).removeClass("mySelectedNode");
		
		});
		treeNode.checked = true;
		var aObj = $("#" + treeNode.tId);
		aObj.addClass("mySelectedNode");
		$("#filterName").val(treeNode.filterName);
		$("#filterNo").val(treeNode.filterNo);
		//如果是默认的筛选项，不允许编辑，去掉保存 删除按钮
		if (treeNode.opNo == "") {
			$(".formRowCenter").hide();
			$(".default-tip").show();
			createFilterGroup(treeNode.filterContent,"uneidt");
		}else{
			$(".formRowCenter").show();
			$("#del_filter_item").show();
			$(".default-tip").hide();
			createFilterGroup(treeNode.filterContent,"edit");
			currEidtNode = treeNode;
		}
//	}else if ($(targetObj).is("a>span,a>span>i")) {
//		if (treeNode.opNo != "") {
//			//判断勾选的默认筛选项个数是否大于五个
//			var gxLen = $("#filter_item_set").find(".i-gouxuan.show").length;
//			var iObj = $("#" + treeNode.tId + IDMark_A).find(".i-gouxuan");
//			if(gxLen>=5 && iObj.hasClass("hide")){
//				alert("默认勾选不能超过5个",0)
//			}else{
//				var iObj = $("#" + treeNode.tId + IDMark_A).find(".i-gouxuan");
//				var useFlag="0";
//				if(iObj.hasClass("hide")){
//					useFlag="1";
//				}
//				var parmData ={"pmsUserFilter.opNo":treeNode.opNo,"pmsUserFilter.useFlag":useFlag,"pmsUserFilter.filterNo":treeNode.filterNo,"pmsUserFilter.jsp":treeNode.jsp,"pmsUserFilter.url":treeNode.url}
//				$.ajax({
//					type : 'post',
//					url :"PmsUserFilterAction_updateStsAjax.action" ,
//					dataType : 'json',
//					data:parmData,
//					error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
//					success : function(data) {
//						if(data.flag=="success"){
//							setFilterVal();
//							if(iObj.hasClass("hide")){
//								iObj.removeClass("hide");
//								iObj.addClass("show");
//							}else{
//								iObj.removeClass("show");
//								iObj.addClass("hide");
//							}
//						}
//					}
//				});
//			}
//		}
//	}

};

var filterOnCheckCallback = function(treeId, treeNode){
	var arr = [];
	var view = [];
	$(".filter-val input[type=hidden]").each(function(i,obj){
		var o = $(obj).val();
		var tempO = eval(o);
		for (var k = 0; k < tempO.length; k++) {
			arr.push(tempO[k]);
		}
	});
	if(filterCol.length<=0){
		return false;
	}
	$.each(arr,function(i,sub){
		if(filterCol[0].name==sub.condition){
			if($.inArray(sub.value,view)==-1){
				view.push(sub.value);
			};
		}
	});
	if(view.length>1||view.length==0){
		$.each(filterCol[1].arr,function(i,arrObj){
			if(arrObj.value=="common"){
			//	console.log("common:"+arrObj.col);
				$(".search-title").find("span").attr("value",arrObj.col);
			}
		});
	}else{
		$.each(filterCol[1].arr,function(i,arrObj){
			if(arrObj.value==view[0]){
			//	console.log("value:"+arrObj.col);
				$(".search-title").find("span").attr("value",arrObj.col);
			}
		});
	}
};


//拖拽前执行  
var dragId;  
function beforeDrag(treeId, treeNodes) {  
       for (var i=0,l=treeNodes.length; i<l; i++) {  
              dragId = treeNodes[i].pId;  
            if (treeNodes[i].drag === false) {  
                 return false;  
           }  
      }  
       return true;  
}  
 //拖拽释放之后执行  
function beforeDrop(treeId, treeNodes, targetNode, moveType) {  
    if(targetNode.pId == dragId){  
         var data = {id:treeNodes[0].id,targetId:targetNode.id,moveType:moveType};  
         var confirmVal = true;  
        return confirmVal;  
    }
}

//初始化筛选项管理弹层页面的筛选条件数组
function initFilterVal(){
	addItemBtnFlag=false;
	//筛选列表初始化
	var filter_setted = {
			check: {
				enable: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				showLine: false,
				showIcon: true,
				addDiyDom: addDiyDomItemDef
			},
			callback: {
				onClick: filterItemOnClick
			}
	};
	$.ajax({
		type : 'post',
		url :webPath+"/pmsUserFilter/findByList" ,
		dataType : 'json',
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
			filterVal = data.json;
			$.fn.zTree.init($("#filter_item_set"), filter_setted, filterVal);
			var addStr ='<li class="addItemBtn" onclick="addFilterItem();">'
				+'<span class="add-ico">'
				+'<i class="i i-jia1"></i>'
				+'</span>'
				+'<span class="add-title">定义筛选项</span>'
				+'</li>';
			$("#filter_item_set").append(addStr);
			
		}
	});
	
	
}

//打开筛选条件编辑页
var showFilterBox = function(gObj,name,no) {
	var editflag="";
	if(gObj!=undefined && name!=undefined && no!=undefined ){
		editflag = "edit";
	}
	initFilterVal();
	$(".my-filter-box").modal("show");
	$("#filterName").val(name);
	$("#filterNo").val(no);
	$("#filterName").focus();
	createFilterGroup(gObj,editflag);
	$("#del_filter_item").hide();
};

//定义筛选项按钮事件
var addFilterItem = function(gObj){
	$("#filterName").val("");
	$("#filterName").focus();
	$("#filterNo").val("");
	$("#save_filter_db").show();
	$("#del_filter_item").hide();
	$(".formRowCenter").show();
	$(".default-tip").hide();
	$("#filter_item_set").find(".mySelectedNode").removeClass("mySelectedNode");
	createFilterGroup(gObj,"");
	currEidtNode=null;
};


function delFilterItem(){
	var treeId = "filter_item_set";
	delFilterGroup(treeId,currEidtNode);

	
}


//删除
var delFilterGroup = function(treeId,treeNode){
	alert(top.getMessage("CONFIRM_DELETE"),2,function(){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var postData = {};
		postData.filterNo =treeNode.filterNo;
		$.ajax({
			type : 'post',
			url :webPath+"/pmsUserFilter/delete" ,
			dataType : 'json',
			data:postData,
			error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
			success : function(data) {
				initFilterVal();
				addFilterItem();
				setFilterVal();
			}
		});
	});
};
//关闭筛选条件编辑页
var closeFilterBox = function() {
	$(".my-filter-box").modal("hide");
};
//保存
var saveFilterBox = function(){
	var nodeObj,subArr=[];
	var treeObj = $.fn.zTree.getZTreeObj('filter_item_set');
	var nodes = treeObj.getNodes();
	if(typeof($("#filterNo").val())!="undefined"&&$("#filterNo").val()!=""&&$("#filterNo").val()!=null){
		$.each(nodes, function(i,obj) {
			if (obj.filterNo==$("#filterNo").val()) {
				nodeObj=obj;
			}
		});
	}else{
//		var newNode={name:$("#filterName").val()};
		if($("#filterName").val()==""){
			alert(top.getMessage("NOT_FORM_EMPTY","筛选名称"),0);
			return false;
		}
		//nodeObj = treeObj.addNodes(null, newNode)[0];
		nodeObj={name:$("#filterName").val(),useFlag:"0",filterNo:""};
		
	}
	if(typeof(nodeObj)!="undefined"){
		
	} 
	var subTreeObj = $.fn.zTree.getZTreeObj('my_filter_group');
	var subNodes = subTreeObj.getNodes();
	$.each(subNodes, function(i,obj) {
		var subObj = {};
		subObj.treeId="my_filter";
		subObj.checked=true;
		subObj.andOr=obj.andOr;
		subObj.condition=obj.condition;
		subObj.type=obj.type;
		subObj.dicType=obj.dicType;
		subObj.value=obj.value;
		subObj.secondValue=obj.secondValue;
		subObj.noValue=obj.noValue;
		subObj.singleValue=obj.singleValue;
		subObj.betweenValue=obj.betweenValue;
		subObj.listValue=obj.listValue;
		subObj.likeValue=obj.likeValue;
		//过滤掉没有配置的空行
		if(typeof(obj.condition)!="undefined" && typeof(obj.type) !="undefined"){
			if(typeof(obj.value)!="undefined" || typeof(obj.secondValue) !="undefined"){//过滤没有配置数值的条件
				subArr.push(subObj);
			}
		}
		
	});
	//如果没有一个条件的话，给出提示确认是否继续保存
	if(subArr.length==0){
		alert(top.getMessage("CONFIRM_FILTER_SAVE"),2,function(){
			saveFilterCondition(nodeObj,subArr);
		});
	}else{
		saveFilterCondition(nodeObj,subArr);
	}
	
};

var saveFilterCondition = function(nodeObj,subArr){
	nodeObj.name=$("#filterName").val();
	nodeObj.filterName=$("#filterName").val();
	nodeObj.filterContent=JSON.stringify(subArr); 
	var postData = {};
	if($("#" + nodeObj.tId).hasClass("mySelectedNode")){
		filterNo_def = nodeObj.filterNo;
		postData.filterNo =nodeObj.filterNo;
	}else{
		filterNo_def = null;
	}
	postData.filterName =nodeObj.filterName;
	postData.filterContent  = nodeObj.filterContent;
	postData.useFlag =  nodeObj.useFlag;
	postData.ajaxData = JSON.stringify(subArr);
	$.ajax({
		type : 'post',
		url :webPath+"/pmsUserFilter/insert" ,
		dataType : 'json',
		data:postData,
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
			//$.myAlert.Alert(data.msg);
			alert(data.msg,1);
			setFilterVal();
			initFilterVal();
			addFilterItem();
			
			///保存后不关闭弹层
//			$(".my-filter-box").modal("hide");
//			$("#filter_name_box").modal("hide");
			
		}
	});
};

//创建已存在筛选条件
var createFilterGroup = function(gObj,editflag) {
	var arr = eval("(" + gObj + ")");
	var filterGroup_setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showLine: false,
			showIcon: false,
			showTitle: false,
			addDiyDom: addFilterGroup
		}
	};
	var filterGroup_setting_edit = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				showLine: false,
				showIcon: false,
				showTitle: false,
				addDiyDom: addFilterGroupEdit
			}
	};
	
	if(editflag==""){
		$.fn.zTree.init($("#my_filter_group"), filterGroup_setting, arr);
		addFilterTreeObj();
	}else{
		$.fn.zTree.init($("#my_filter_group"), filterGroup_setting_edit, arr);
		if(editflag=="edit"){
			$.fn.zTree.init($("#my_filter_group"), filterGroup_setting_edit, arr);
			addFilterTreeObj();
		}
	}
};
//自定义筛选条件
function addFilterGroup(treeId, treeNode) {
	var treeObj =$.fn.zTree.getZTreeObj("my_filter_group");
	
	var aObj = $("#" + treeNode.tId + IDMark_A);
	$("#" + treeNode.tId + IDMark_SPAN).remove();
	$("#" + treeNode.tId + IDMark_SWITCH).remove();
	$("#" + treeNode.tId + IDMark_ICO).remove();
	$("#" + treeNode.tId + "_addText").remove();
	$("#" + treeNode.tId + "_check").remove();
	var index = treeObj.getNodeIndex(treeNode);
	var maxLen = filter_dic.length;
	if(maxLen > 4){
		maxLen = 4;
	}
	if(index<maxLen){
		var dicObj = filter_dic[index];
		treeNode.condition = dicObj.optCode;
		var type = eval("dicType."+dicObj.dicType).type;
		treeNode.type = type;
	}
    if(index == 0){
    	var $div=$('<div id="' + treeNode.tId + '_please" class="btn-andor btn-group"></div>');
    	$div.css({"border":"none","text-align": "right"});
		$div.html("请选择");
		treeNode.andOr="1";
    	aObj.before($div);
    	aObj.before(addselect(filter_dic,treeNode.tId,"_opt",treeNode.condition,"addOptParm"));    	
    	aObj.before(addOptParm(treeNode.tId,treeNode.condition,treeNode.type));
    	if(typeof(treeNode.type)!="undefined"){
    		aObj.before(addRang(treeNode.tId,treeNode.type,treeNode.value,treeNode.secondValue)); 
    	}else{
    		aObj.before(addEntry(treeNode.tId,"_inputFirstVal","",""));
    	}
		addFilterTreeObj();
	}else{
		aObj.before(addAndOr(treeNode.tId,"_andOr",treeNode));
		aObj.before(addselect(filter_dic,treeNode.tId,"_opt",treeNode.condition,"addOptParm"));				
		aObj.before(addOptParm(treeNode.tId,treeNode.condition,treeNode.type));	
		if(typeof(treeNode.type)!="undefined"){
	    		aObj.before(addRang(treeNode.tId,treeNode.type,treeNode.value,treeNode.secondValue)); 
	    }else{
	    		aObj.before(addEntry(treeNode.tId,"_inputFirstVal","",""));
	   }
		
		aObj.after(addDelBtn(treeNode.tId,"_delBtn"));
		if(index < maxLen){
			addFilterTreeObj();
		}
	}
}
//自定义筛选条件
function addFilterGroupEdit(treeId, treeNode) {
	var treeObj =$.fn.zTree.getZTreeObj("my_filter_group");
	
	var aObj = $("#" + treeNode.tId + IDMark_A);
	$("#" + treeNode.tId + IDMark_SPAN).remove();
	$("#" + treeNode.tId + IDMark_SWITCH).remove();
	$("#" + treeNode.tId + IDMark_ICO).remove();
	$("#" + treeNode.tId + "_addText").remove();
	$("#" + treeNode.tId + "_check").remove();
	var index = treeObj.getNodeIndex(treeNode);
	if(index == 0){
		var $div=$('<div id="' + treeNode.tId + '_please" class="btn-andor btn-group"></div>');
		$div.css({"border":"none","text-align": "right"});
		$div.html("请选择");
		treeNode.andOr="1";
		aObj.before($div);
		aObj.before(addselect(filter_dic,treeNode.tId,"_opt",treeNode.condition,"addOptParm"));    	
		aObj.before(addOptParm(treeNode.tId,treeNode.condition,treeNode.type));
		if(typeof(treeNode.type)!="undefined"){
			aObj.before(addRang(treeNode.tId,treeNode.type,treeNode.value,treeNode.secondValue)); 
		}else{
			aObj.before(addEntry(treeNode.tId,"_inputFirstVal","",""));
		}
	}else{
		aObj.before(addAndOr(treeNode.tId,"_andOr",treeNode));
		aObj.before(addselect(filter_dic,treeNode.tId,"_opt",treeNode.condition,"addOptParm"));				
		aObj.before(addOptParm(treeNode.tId,treeNode.condition,treeNode.type));	
		if(typeof(treeNode.type)!="undefined"){
			aObj.before(addRang(treeNode.tId,treeNode.type,treeNode.value,treeNode.secondValue)); 
		}else{
			aObj.before(addEntry(treeNode.tId,"_inputFirstVal","",""));
		}
		
		aObj.after(addDelBtn(treeNode.tId,"_delBtn"));
	}
}
//添加状态div
function addAndOr(tId,idMark,treeNode){
	
	var treeObj =$.fn.zTree.getZTreeObj("my_filter_group");
	var treeNodes=treeObj.getNodes();
	var $div=$('<div id="'+tId+idMark+'" class="btn-andor btn-group"></div>');
	$and=$('<div class="btn-status correct" id="0">并且</div>');
	$wrong=$('<div class="btn-status" id="1">或者</div>');
	if(treeNode.andOr=="1"){
		$and=$('<div class="btn-status" id="0">并且</div>');
		$wrong=$('<div class="btn-status correct" id="1">或者</div>');
	}
	$div.append($and);
	$div.append($wrong);		
	
	$div.find("div").click(function(){		
		$(this).addClass("correct").siblings().removeClass("correct");
		if($(this).hasClass("correct")){
			treeNode.andOr=$(this).attr("id");			
		}		
	})			
	return $div;
}

//删除按钮
function addDelBtn(tId,idMark){
	var $div = $('<div id="'+tId+idMark+'" class="btn-group filter_del_btn"></div>');
	$input = $("<i class='i i-x1'></i>");
	$input.bind("click",function(){
		var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
		var node = treeObj.getNodeByTId(tId);
		treeObj.removeNode(node);
	});
	$input.appendTo($div);
	return $div;
}
//switch
var addSwitch = function(aObj,tId,idMark,val,fun){
	var btn = $('<input type="checkbox" id="'+tId+idMark+'" name="'+tId+idMark+'" value="'+tId+'"/>');
	btn.prop("checked",val);
	aObj.before(btn);
	if(typeof($.isIE)!="undefined"&&$.isIE()&&window.browser['version']=="8.0"){
		btn.bind("click",function(){
			if (typeof(fun)!="undefined") {
				var gofun=fun+"('"+tId+"',"+btn.prop("checked")+")";
				eval(gofun); 
			}
		});
    }else{
    	btn.rcSwitcher({
			height: 18,
			theme: 'light',
			reverse: true,
			blobOffset: 1,
			onText:'启用',
			offText:'失效'
		}).on( 'change.rcSwitcher', function( e, data ){
			if (typeof(fun)!="undefined") {
				var gofun=fun+"('"+tId+"',"+data.$input[0].checked+")";
				eval(gofun); 
			}
		});
    }
	if (typeof(fun)!="undefined") {
		var gofun=fun+"('"+tId+"',"+val+")";
			eval(gofun); 
	}
};

//创建下拉
function addselect(arr,tId,idMark,val,fun) {
	var aObj = $("#" + tId + IDMark_A);
	var $div = $('<div id="'+tId+idMark+'" class="btn-group"></div>');
	/*var slt='<div class="select3-single-select">'+
		   +'<div class="select3-single-result-container">'+
		   +'<span class="select3-single-selected-item" data-item-id="app">筛选条件</span></div>'+
		   +'<i class="fa fa-sort-desc select3-caret"></i>'		   
		   +'</div>';*/
	/*$div.append();*/

	var data = [];
	$.each(arr, function(i, obj) {
		data.push({id:obj.optCode,text:obj.optName});
	});
	$div.select3({
			   // allowClear: true,
			    items: data,
			    showSearchInputInDropdown:false
			    , placeholder: idMark == "_opt" ? "筛选条件" : ((arr && arr.length > 0)? "" :"请先选择筛选条件")
	}).on('select3-selected',function(){
		if (typeof(fun)!="undefined") {
			var gofun=fun+"('"+tId+"','"+$(this).select3('value')+"')";
			
			if(fun=="addRang" ){
				var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
				var node = treeObj.getNodeByTId(tId);
				var index=treeObj.getNodeIndex(node);
				var trees= treeObj.getNodes();
				var result=trees.length-index;
				
				if ($(this).data("haveNewLine") !== true && result==1) {
					addFilterTreeObj();
					$(this).data("haveNewLine", true);
					}
				}
			eval(gofun);
			if(fun=="addOptParm"){
				aObj.before(addEntry(tId,"_inputFirstVal","",""));
			}
		}
	});
	$div.select3('value',val+"");
	
	return $div;
}

//回调添加筛选条件
function addOptParm(tId,code,val) {
	var aObj = $("#" + tId + IDMark_A);
	$("#" + tId + "_optParm").remove();
	$("#" + tId + "_optVal").remove();
	$("#" + tId + "_inputFirstVal").remove();
	$("#" + tId + "_inputSedVal").remove();
	$("#" + tId + "_addText").remove();
	var parm = {};
	var type;
	$.each(filter_dic, function(i,obj) {
		if (obj.optCode==code) {
			parm = eval("dicType."+obj.dicType).parm;
			type = eval("dicType."+obj.dicType).type;
		}
	});
	setCondition(tId,code,type);
	aObj.before(addselect(parm,tId,"_optParm",val,"addRang"));
}
function addRang(tId,code,val1,val2){
	var aObj = $("#" + tId + IDMark_A);
	setType(tId,code);
	var type={},nodeObj={},parm={};
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var nodes = treeObj.getNodes();
	$.each(nodes, function(i,obj) {
		if (obj.tId==tId) {
			nodeObj=obj;
		}
	});
	
	$.each(filter_dic, function(i,obj) {
		if (obj.optCode==nodeObj.condition) {
			parm = obj.parm;
			type = eval("dicType."+obj.dicType).type;
		}
	});
		$("#" + tId + "_optVal").remove();
		$("#" + tId + "_inputFirstVal").remove();
		$("#" + tId + "_inputSedVal").remove();
		$("#" + tId + "_addText").remove();
		nodeObj.singleValue=false;
		nodeObj.betweenValue=false;
		nodeObj.likeValue=false;
		nodeObj.listValue=false;
	if (type==0) {
		nodeObj.listValue=true;
		aObj.before(addselect(parm,tId,"_optVal",val1,"setValue"));
	}else if(type==1){
		if (code==0||code==1||code==2) {
			nodeObj.singleValue=true;
			aObj.before(addEntry(tId,"_inputFirstVal",val1,""));
		}else{
			nodeObj.betweenValue=true;
			aObj.before(addEntry(tId,"_inputFirstVal",val1,"short"));
			aObj.before(addText(tId,"_addText","至"));
			aObj.before(addEntry(tId,"_inputSedVal",val2,"short"));
		}
	}else if(type==2){
		nodeObj.likeValue=true;
		$("#" + tId + "_inputFirstVal").remove();
		aObj.before(addEntry(tId,"_inputFirstVal",val1,""));
	}else if(type==4){
		if (code==0||code==1||code==2) {
			nodeObj.singleValue=true;
			addEntryDate(tId,"_inputFirstVal",val1,aObj,"");
		}else if(code==4 || code==3){
			nodeObj.betweenValue=true;
			addEntryDate(tId,"_inputFirstVal",val1,aObj,"short");
			aObj.before(addText(tId,"_addText","至"));
			addEntryDate(tId,"_inputSedVal",val2,aObj,"short");
		}else if(code==5){//期限类型
			nodeObj.betweenValue=true;
			aObj.before(addselect(filterDateDic,tId,"_optVal",val1,"setOptValue"));
			if(val1=="4"||val1=="5"){
				aObj.before(addEntry(tId,"_inputSedVal",val2,"short"));
			}
		}
	}
}
//期限类型赋值
function setOptValue(tId,code){
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var node = treeObj.getNodeByTId(tId);
	node.value=code;
	if(code=="4"||code=="5"){
		var aObj = $("#" + tId + IDMark_A);
		if($("#" + tId + "_inputSedVal").length==0){
			aObj.before(addEntry(tId,"_inputSedVal","","short"));
		}
	}else{
		$("#" + tId + "_inputSedVal").remove();
	}
}

function addEntry(tId,idMark,val,widthFlag){
	var $div = $('<div id="'+tId+idMark+'" class="btn-group"></div>');
	var $input = $('<input class="form-control" type="text">');
	if(widthFlag=="short"){
		$input = $('<input class="form-control short-width" type="text">');
	}
	$input.val(val);
	$div.append($input);
	$input.bind("blur",function(){
		if (idMark=="_inputFirstVal") {
			setValue(tId,$(this).val());
		} else{
			setSecondValue(tId,$(this).val());
		}
	});
	return $div;
}

function addEntryDate(tId,idMark,val,aObj,widthFlag){
	var $div = $('<div id="'+tId+idMark+'" class="btn-group"></div>');
	var $input = $('<input id="'+tId+idMark+'_input"  class="form-control laydate-icon" type="text">');
	if(widthFlag=="short"){
		$input = $('<input id="'+tId+idMark+'_input"  class="form-control laydate-icon short-width" type="text">');
	}
	$input.val(val);
	$div.append($input);
	aObj.before($div);
	$input.bind("click",function(){
		fPopUpCalendarDlg({
			   event: 'focus',
			    format: 'YYYYMMDD', // 分隔符可以任意定义，该例子表示只显示年月
			    choose: function(datas){ //选择日期完毕的回调
			        if (idMark=="_inputFirstVal") {
						setValue(tId,datas);
					} else{
						setSecondValue(tId,datas);
					}
			    }
			});
	});
}

function addText(tId,idMark,val){
	var $div = $('<div id="'+tId+idMark+'" class="btn-group"></div>');
	$div.css("width","11px");
	$div.css("margin","0px");
	$div.html(val);
	return $div;
}
//返回节点属性
function getNodeObj(treeId,tId){
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getNodes();
	var nodeObj={};
	$.each(nodes, function(i,obj) {
		if (obj.tId==tId) {
			nodeObj=obj;
		}
	});
	return nodeObj;
}
//新增
function addFilterTreeObj(){
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var newNode = {checked: false, andOr: 0,'noValue':false,'singleValue':false,'betweenValue':false,'listValue':false,'likeValue':false};
	newNode = treeObj.addNodes(null,-1, newNode);
	$("#my_filter_tree").mCustomScrollbar("update");
}

//set值
function setAndOr(tId,code){
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var node = treeObj.getNodeByTId(tId);
	node.andOr=code;
}
function setSwitchVal(tId,flag){
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var node = treeObj.getNodeByTId(tId);
	node.switchVal=flag;
	node.checked=flag;
}
function setCondition(tId,code,dicType){
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var node = treeObj.getNodeByTId(tId);
	node.condition=code;
	node.dicType=dicType;
}
function setType(tId,code){
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var node = treeObj.getNodeByTId(tId);
	node.type=code;
}
function setValue(tId,code){
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var node = treeObj.getNodeByTId(tId);
	node.value=code;
}
function setSecondValue(tId,code){
	var treeObj = $.fn.zTree.getZTreeObj("my_filter_group");
	var node = treeObj.getNodeByTId(tId);
	node.secondValue=code;
}
///////////////////////////////////////////
//默认筛选生成
function def_filter_div(displayName,treeId,defFilterArr){
//	var $div =$('<div class="def_filter_title"><h4>'+displayName+'</h4></div>');
	var $treeUl	=$('<ul class="ztree default_filter" id="'+treeId+'" ></ul>');
	$("#filter_list").prepend($treeUl);
//	$("#filter_list").prepend($div);
	create_def_tree(treeId,defFilterArr);
}
//默认筛选配置
/*
 * type条件类型: 0字典项类型
 * displayName 默认条件标题
 * field 字段名
 * keyName 字典项名
 * optCode 显示的字典项
 */
function addDefFliter(type,displayName,field,keyName,optCode){
	if(type==0){
		addOptFilter(field,displayName,keyName,optCode);
	}else if(type==1){
		//暂时不用
		addSinglValue(field,displayName,keyName);
	}else if(type==2){
		//暂时不用
		addSedlValue(field,displayName,keyName,optCode);
	}else if(type==3){
		addOptLikeFilter(field,displayName,keyName,optCode);
	}
}

function addOptFilter(field,displayName,keyName,optCode){
	var filter_def = [];
	var	optArr =[];
	if(optCode.indexOf(",")>=0){
		optArr = optCode.split(",");
	}else{
		optArr[0] = optCode;
	}
	$.ajax({
		type : 'post',
		url :webPath+"/pmsUserFilter/getCacheParmDic" ,
		dataType : 'json',
		async: false,
		data:{filterName:keyName},
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
			$.each(data.json, function(iArgs, node) {
				for(var i = 0;i<optArr.length;i++){
					if(optArr[i]==node.optCode){
						var base={};
						var arr = [];
						var obj = {};
						obj.checked = true;
						obj.andOr = 1;
						obj.condition = field;
						obj.treeId = field+"Tree";
						obj.treeId = field+"Tree";
						obj.type = 0;
						obj.value = node.optCode;
						obj.noValue = false;
						obj.singleValue = true;
						obj.betweenValue = false;
						obj.listValue = false;
						obj.likeValue = false;
						arr.push(obj);
						var timestamp = Date.parse(new Date());
						base.filterNo=timestamp+node.optCode;
						base.filterName = node.optName;
						base.condition = field;
						base.filterContent = JSON.stringify(arr);
						filter_def.push(base);
					}
				}
			});
			var treeId = field+"Tree";
			def_filter_div(displayName,treeId,filter_def);
		}
	});
}
function addOptLikeFilter(field,displayName,keyName,optCode){
	var filter_def = [];
	var	optArr =[];
//	if(optCode.indexOf(",")>=0){
//		optArr = optCode.split(",");
//	}else{
//		optArr[0] = optCode;
//	}
	$.ajax({
		type : 'post',
		url :webPath+"/pmsUserFilter/getCacheParmDic" ,
		dataType : 'json',
		async: false,
		data:{filterName:keyName},
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
			$.each(data.json, function(i, node) {
				//for(var i = 0;i<optArr.length;i++){
				//	if(optArr[i]==node.optCode){
						var base={};
						var arr = [];
						var obj = {};
						obj.checked = true;
						obj.andOr = 1;
						obj.condition = field;
						obj.treeId = field+"Tree";
						obj.treeId = field+"Tree";
						obj.type = 0;
						obj.value = node.optCode;
						obj.noValue = false;
						obj.singleValue = false;
						obj.betweenValue = false;
						obj.listValue = false;
						obj.likeValue = true;
						arr.push(obj);
						var timestamp = Date.parse(new Date());
						base.filterNo=timestamp+node.optCode;
						base.filterName = node.optName;
						base.condition = field;
						base.filterContent = JSON.stringify(arr);
						filter_def.push(base);
				//	}
				//}
			});
			var treeId = field+"Tree";
			def_filter_div(displayName,treeId,filter_def);
		}
	});
}
