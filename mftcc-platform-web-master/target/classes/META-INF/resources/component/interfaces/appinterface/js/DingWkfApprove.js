;
var DingWkfApprove=function(window,$){
	var _opinionTypeChange=function(){
		var opinionType = $("[name=opinionType]").val();
		if(opinionType != "1"){
			$("#nextApprovePerson-div").hide();
		}else{
			addNextApprovalPerson();
		}
		var $sendBackNode = $("[name=rollbackName]");// 发回岗位(发回重审)
		$sendBackNode.parents(".weui_cell_select").hide();// 选择框正行

		if (opinionType == "4") {// 发回重审
			if(befNodesjsonArray!=""){
				$sendBackNode.parents(".weui_cell_select").show();// 字段td
				/*if($("[name=nextOpNo]").length > 0){//若有选择下一执行人操作，退回时去掉此选项
					var $nextOpNo=$("[name=nextOpNo]");
					$nextOpNo.removeAttr("mustinput");
					$nextOpNo.parents("tr").hide();
				}
				if($("[name=isChairman]").length > 0){//若有选择是否提交董事长操作，退回时去掉此选项
					var $isChairman=$("[name=isChairman]");
					$isChairman.removeAttr("mustinput");
					$isChairman.parents("tr").hide();
				}*/
			}
		} else{
			/*if($("[name=nextOpNo]").length > 0){
				var $nextOpNo=$("[name=nextOpNo]");
				$nextOpNo.attr("mustinput","1");
				$nextOpNo.parents("tr").show();
			}
			if($("[name=isChairman]").length > 0){
				var $isChairman=$("[name=isChairman]");
				$isChairman.attr("mustinput","1");
				$isChairman.parents("tr").show();
			}*/
		}
	};
	//退回岗位下拉框赋值
	var _setRollbackOption=function(befNodes){
		var optionStr = "";
		$.each(befNodes,function(i,node){
			optionStr +='<option value="'+node.name+'">'+node.desc+'</option>';
		});
		$("[name=rollbackName]").html(optionStr);
	}
	return{
		opinionTypeChange:_opinionTypeChange,
		setRollbackOption:_setRollbackOption
	}
}(window,jQuery)