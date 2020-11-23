function manageNameCallback() {
	$("input[name=manageNo]").val(data.opNo);
	$("input[name=manageName]").val(data.opName);
}
function specialNameCallback() {
	$("input[name=specialNo]").val(data.opNo);
	$("input[name=specialName]").val(data.opName);
}
var c;
// 修改
var tr;
function func_getById(obj, url) {
	tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	c = new RightForm({
		actionUrl : url,
		formUrl : webPath+"/wkfBusinessConfig/updateAjax",
		title : "工作流业务配置",
		btns : [ {
			value : "提交",
			type : "button",
			onClick : "ajaxTrUpdate(this,reload)",
			"data-elem" : tr
		} ]
	});
}
// 新增
function func_input(url) {
	c = new RightForm({
		actionUrl : url,
		formUrl : webPath+"/wkfBusinessConfig/insertAjax",
		title : "工作流业务配置",
		btns : [ {
			value : "保存",
			type : "button",
			onClick : "ajaxInsert(this.form)"
		} ],
		select3Flag : false
	});
}
function reload() {
	c.close();
	$("table").tableRcswitcher({name:"sts"});
	$(tr).parents("table").find(".selected").removeClass("selected");
}
function ajaxInsert(obj) {
	var contentForm = $("#content");
	var tableId = contentForm.find(".ls_list").attr("title");
	var flag = submitJsMethod(obj, '');
	if (flag) {
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url : url,
			data : {
				ajaxData : dataParam,
				tableId:tableId,
				tableType:"thirdTableTag",
				pageSize:25,
				pageNo:1
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					//$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					alert(top.getMessage("SUCCEED_OPERATION"),1);
					updateMyCustomScrollbar.setPageer($("#content"),data.ipage,reload);
				}
			},
			error : function(data) {
				//$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
}
