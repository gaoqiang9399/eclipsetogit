var wall,wsData,roleMenuData,roleNames;
$(document).ready(function(){
	getRoleMenuData();
	initList();
	$("body").click(function(e){
		if($(e.target).attr("type")=="button"){
			return false;
		}else if(e.target.tagName=="INPUT"){
			return false;
		}else if($(e.target).parents("table").length>0){
			return false;
		}else if($(e.target).hasClass("control")||$(e.target).parent().hasClass("control")){
			return false;
		}else if(e.target.className==".select3-backdrop"){
			return false;
		}else{
			clearControl();
		}
	});
});
function getRoleMenuData(){
	roleMenuData = "<option value='null' selected = 'selected' >&nbsp</option>";
	$.ajax({
		url : realPath+"/BwmTaskRoleRelActionArray_getRoleMenu.action",
		type : "post",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			for(var i in data){
				roleMenuData += "<option value='"+data[i].roleNo+"'>"+data[i].roleName+"</option>";
			}
		}
	});
}
function initList(){
	$.ajax({
		url : realPath+"/BwmTaskRoleRelActionObj_getOptList.action",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		success : function(data) {
			wsData = data;
			for(var i in data){
				var roleNames = "";
				if(data[i].role){
					for(var r in data[i].role){
						roleNames += data[i].role[r].roleName;
						if(r<data[i].role.length-1){
							roleNames += ",";
						}
					}
				}
				var $tr = $("<tr taskType='"+i+"'>" +
						"<td>"+data[i].optName+"</td>" +
						"<td><div class='taskType'><span class='row-one' name='1'></span><span class='row-two' name='2'></span><span class='row-three' name='3'></span></div></td>" +
						"<td>"+roleNames+"</td>" +
						"<td></td>" +
						"<td><a class='update' href='javascript:void(0)'>修改</a>" +
						"<a class='save' href='javascript:void(0)'>保存</a>" +
						//"<a class='delete' href='javascript:void(0)'>删除</a></td>" +
						"</tr>");
				$(".workspace-table").find("tbody").append($tr);
				$tr.find(".taskType span").hide();
				if(data[i].layoutType=="1"){
					$tr.find(".taskType .row-one").show();
				}else if(data[i].layoutType=="2"){
					$tr.find(".taskType .row-two").show();
				}else if(data[i].layoutType=="3"){
					$tr.find(".taskType .row-three").show();
				}
				$tr.find(".update").bind("click",{taskType:i},wsUpdate);
				$tr.find(".save").bind("click",{taskType:i},wsSave);
//				$tr.find(".delete").bind("click",{taskType:i},wsDelete);
			}
			$(".content-table").mCustomScrollbar({
				theme:"minimal-dark",
			});
		}
	});
}
function wsUpdate(e){
	clearControl();
	var taskType = e.data.taskType;
	var $tds = $(this).parents("tr").find("td");

	var $layoutType = $tds.eq(1);
	offset = $layoutType.offset();
	$layoutType.find(".taskType").hide();
	var $div = $("<div class='control layoutType' style='position: relative;'><span class='row-one' name='1'></span><span class='row-two' name='2'></span><span class='row-three' name='3'></span></div>").appendTo($layoutType);
	$div.find("span").bind("click",function(){
		$div.find("span").removeClass("curr");
		$(this).addClass("curr");
	});
	$div.css("background-color",$(this).parents("tr").css("background-color"));
	$div.find("span[name='"+wsData[taskType].layoutType+"']").addClass("curr");
	
	var $role = $tds.eq(2);
	roleNames = $role.html();
	$role.empty();
	var $select = $("<select class='control roleNo' name='roleNo' style=''>"+roleMenuData+"</select>").appendTo($role);
	$select.select3({multiple: true});
	$select.select3("remove","null");
	if(wsData[taskType].role){
		var arr = [];
		for(var r in wsData[taskType].role){
			arr.push(wsData[taskType].role[r].roleNo);
		}
		$select.select3("value",arr);
	}
}
function wsSave(e){
	var taskType = e.data.taskType;
	
	var lt = $(this).parents("tr").find(".control.layoutType");
	var layoutType = lt.find(".curr").attr("name");
	wsData[taskType]["layoutType"] = layoutType;
	$(this).parents("tr").find("td").eq(1).find(".taskType").show();
	$(this).parents("tr").find("td").eq(1).find(".taskType span").hide();
	$(this).parents("tr").find("td").eq(1).find(".taskType span[name='"+layoutType+"']").show();
	lt.remove();
	
	var role = $(this).parents("tr").find(".control.roleNo");
	if(role.length==0){
		return false;
	}
	wsData[taskType]["role"] = [];
	var roleName= "";
	var roleNo = "";
	var roles = role.select3("data");
	for(var i in roles){
		if(roles[i]!="null"){
			roleName += roles[i].text;
			roleNo += roles[i].id;
			wsData[taskType]["role"].push({roleNo:roles[i].id,roleName:roles[i].text});
			if(i<roles.length-1){
				roleName += ",";
				roleNo +=  ",";
			}
		}
	}
	role.parent().html(roleName);
	$.ajax({
		url : realPath+"/BwmTaskRoleRelActionObj_saveTask.action",
		type : "post",
		dataType : "json",
		cache : false,
		data:{
			ajaxData:JSON.stringify({
				taskNo:wsData[taskType].taskNo,
				taskName:wsData[taskType].optName,
				taskType:wsData[taskType].optCode,
				layoutType:wsData[taskType].layoutType,
				roleNo:roleNo
			})
		},
		success : function(data) {
			if(data.flag){
				wsData[taskType]["taskNo"] = data.taskNo;
				window.top.alert(data.msg,1);
			}else{
				window.top.alert(data.msg,0);
			}
		}
	});
}
//function wsDelete(e){
//	var taskType = e.data.taskType;
//	$.ajax({
//		url : realPath+"/BwmTaskRoleRelActionObj_delete.action",
//		type : "post",
//		dataType : "json",
//		cache : false,
//		data:{
//			ajaxData:taskType
//		},
//		success : function(data) {
//			if(data.flag){
//				window.top.alert(data.msg,1);
//			}else{
//				window.top.alert(data.msg,0);
//			}
//		}
//	});
//}
function clearControl(){
	$(".control.roleNo").parent().html(roleNames);
	$(".control").remove();
}