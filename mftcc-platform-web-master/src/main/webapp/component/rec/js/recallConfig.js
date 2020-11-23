var c,tr,revtype;
function manageNameCallback(data) {
	$("input[name=manageNo]").val(data.opNo);
	$("input[name=manageName]").val(data.opName);
}
function specialNameCallback(data) {
	$("input[name=specialNo]").val(data.opNo);
	$("input[name=specialName]").val(data.opName);
}
function sysRoleCallBack(data){
	$("input[name=roleNo]").val(data.roleNo);
}
function sysUserCallBack(data){
	$("input[name=recNo]").val(data.opNo);
}

function startRecallWayOnChange(obj){
	var recallWaySelected = $(obj).val();
	if(recallWaySelected == ''){
		$("td[lablename=recType]").prop("hidden",false);
		$("select[name=recType]").val("");
		$("td[lablename=roleName]").prop("hidden",false);
		$("input[name=roleName]").val("");
		$("td[lablename=roleNo]").prop("hidden",false);
		$("input[name=roleNo]").val("");		
		$("td[lablename=revName]").prop("hidden",false);
		$("input[name=revName]").val("");
		$("td[lablename=recNo]").prop("hidden",false);
		$("input[name=recNo]").val("");
	}
	var recallType = $("select[name=recallType]").val();
	if(recallWaySelected == '1' && recallType == '1'){
		$("select[name=recallWay]").val("1");
		
		$("td[lablename=recType]").prop("hidden",true);
		$("select[name=recType]").val("");
		$("td[lablename=roleName]").prop("hidden",true);
		$("input[name=roleName]").val("");
		$("td[lablename=roleNo]").prop("hidden",true);
		$("input[name=roleNo]").val("");		
		$("td[lablename=revName]").prop("hidden",true);
		$("input[name=revName]").val("");
		$("td[lablename=recNo]").prop("hidden",true);
		$("input[name=recNo]").val("");
	}else{
		$("select[name=recallWay]").val("");
	}
}

function recallTypeOnChange(obj){
	var recallType = $(obj).val();
	if(recallType == "1"){
		$("select[name=recallWay]").val("1");
		
		$("td[lablename=recType]").prop("hidden",true);
		$("select[name=recType]").val("");
		$("td[lablename=roleName]").prop("hidden",true);
		$("input[name=roleName]").val("");
		$("td[lablename=roleNo]").prop("hidden",true);
		$("input[name=roleNo]").val("");		
		$("td[lablename=revName]").prop("hidden",true);
		$("input[name=revName]").val("");
		$("td[lablename=recNo]").prop("hidden",true);
		$("input[name=recNo]").val("");
	}else{
		$("select[name=recallWay]").val("");
		$("select[name=recallWay] option[value=1]").remove();
		
		$("td[lablename=recType]").prop("hidden",false);
		$("select[name=recType]").val("");
		$("td[lablename=roleName]").prop("hidden",false);
		$("input[name=roleName]").val("");
		$("td[lablename=roleNo]").prop("hidden",false);
		$("input[name=roleNo]").val("");		
		$("td[lablename=revName]").prop("hidden",false);
		$("input[name=revName]").val("");
		$("td[lablename=recNo]").prop("hidden",false);
		$("input[name=recNo]").val("");
	}
}
function clearRevNo(obj){
	revType=$(obj).val();
	if(revType==1){//角色
		$($("input[name=recName]").parents("tr").get(0)).hide();
		$($("input[name=roleName]").parents("tr").get(0)).show();
		$("input[name=recName]").val("");
		$("input[name=roleName]").val("");
		$("input[name=recNo]").val("");
		$("input[name=roleNo]").val(""); 
		$("input[name=recName]").attr("mustinput","");
		$("input[name=roleName]").attr("mustinput","1");
	}else if(revType==2){//用户
		$($("input[name=recName]").parents("tr").get(0)).show();
		$($("input[name=roleName]").parents("tr").get(0)).hide();
		$("input[name=recName]").val("");
		$("input[name=roleName]").val("");
		$("input[name=recNo]").val("");
		$("input[name=roleNo]").val("");
		$("input[name=recName]").attr("mustinput","1");
		$("input[name=roleName]").attr("mustinput","");
	}else if(revType==3){//客户经理
		$($("input[name=recName]").parents("tr").get(0)).hide();
		$($("input[name=roleName]").parents("tr").get(0)).hide();
		$("input[name=recName]").val("");
		$("input[name=roleName]").val("");
		$("input[name=recNo]").val("");
		$("input[name=roleNo]").val("");
		$("input[name=recName]").attr("mustinput","");
		$("input[name=roleName]").attr("mustinput","");
	}else{
		$($("input[name=recName]").parents("tr").get(0)).show();
		$($("input[name=roleName]").parents("tr").get(0)).show();
		$("input[name=recName]").val("");
		$("input[name=roleName]").val("");
		$("input[name=recNo]").val("");
		$("input[name=roleNo]").val("");
		$("input[name=recName]").attr("mustinput","");
		$("input[name=roleName]").attr("mustinput","");
	}
}
// 修改
function func_getById(obj, url) {
	tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");	
	c = new RightForm({
		actionUrl : url,
		formUrl : webPath+"/recallConfig/updateAjax" + url.substring(url.indexOf("?")),
		title : "催收配置",
		btns : [ {
			value : "保存",
			type : "button",
			onClick : "ajaxTrUpdate(this,reload)",
			"data-elem" : tr
		} ]
	});
	var recallType = $("select[name=recallType]").val();
	if(recallType == '1'){
		$("td[lablename=recType]").prop("hidden",true);
		$("select[name=recType]").val("");
		$("td[lablename=roleName]").prop("hidden",true);
		$("input[name=roleName]").val("");
		$("td[lablename=roleNo]").prop("hidden",true);
		$("input[name=roleNo]").val("");		
		$("td[lablename=revName]").prop("hidden",true);
		$("input[name=revName]").val("");
		$("td[lablename=recNo]").prop("hidden",true);
		$("input[name=recNo]").val("");
	}
	
	var revType=$("select[name=recType]").val();
	if(revType==1){//角色
		$("td[lablename=revName]").prop("hidden",true);
		$("td[lablename=recNo]").prop("hidden",true);
		$("td[lablename=roleName]").prop("hidden",false);
		$("td[lablename=roleNo]").prop("hidden",false);
		$("input[name=roleName]").after("<i class='mustinput'>*</i>");
		$("input[name=recNo]").val("");
		$("input[name=revName]").val("");
	}else if(revType==2){//用户
		$("td[lablename=revName]").prop("hidden",false);
		$("td[lablename=recNo]").prop("hidden",false);
		$("input[name=revName]").after("<i class='mustinput'>*</i>");
		$("td[lablename=roleName]").prop("hidden",true);
		$("td[lablename=roleNo]").prop("hidden",true);
		$("input[name=roleNo]").val("");
		$("input[name=roleName]").val("");
	}else{//客户经理
		$("td[lablename=revName]").prop("hidden",true);
		$("td[lablename=recNo]").prop("hidden",true);
		$("td[lablename=roleName]").prop("hidden",true);
		$("td[lablename=roleNo]").prop("hidden",true);
		$("input[name=recNo]").val("");
		$("input[name=revName]").val("");
		$("input[name=roleNo]").val("");
		$("input[name=roleName]").val("");
	}
}
// 新增
function func_input(url) {
	c = new RightForm({
		actionUrl : url,
		formUrl : webPath+"/recallConfig/insertAjax",
		title : "催收配置",
		btns : [ {
			value : "保存",
			type : "button",
			onClick : "ajaxInsert(this.form)"
		} ]
		//select3Flag : false,
		//callback : reload
	});
	$("select[name=startRecallWay] option[value=2]").remove();
	$("select[name=recallType] option[value=3]").remove();
}
function reload() {
	$("table").tableRcswitcher({name:"defSts"});
	c.close();
	$(tr).parents("table").find(".selected").removeClass("selected");
	
}
function ajaxInsert(obj) {
	var flag = submitJsMethod(obj, '');
	if (flag) {
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url : url,
			data : {
				ajaxData : dataParam
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					//$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					alert(top.getMessage("SUCCEED_OPERATION"),1);
					reload();
					myCustomScrollbar({
						obj : "#content",// 页面内容绑定的id
						url : webPath+"/recallConfig/findByPageAjax",// 列表数据查询的url
						tableId : "tablerec0001",// 列表数据查询的table编号
						tableType : "thirdTableTag",// table所需解析标签的种类
						myFilter : true,// 是否有我的筛选
						data:{},
						callback:function(){
				    		$("table").tableRcswitcher({
				    		name:"defSts"});
				    	}//方法执行完回调函数（取完数据做处理的时候）
					
					});
				}
			},
			error : function(data) {
				//$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
}
