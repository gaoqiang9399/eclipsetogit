var cusFinCommon = function(window,$){
	//当增加计算项时，根据选择的输入项还是运算项控制是否增加运算项
	var _inputTypeChange = function(obj){
		if($(obj).val() == "1"){
			$("#ys-div").hide();
		}else{
			$("#ys-div").find("tbody").empty();
			var htmlStr = '<tr> <td style="width: 50%;"><input  name="formulaName" onclick="cusFinCommon.selectParm(this);" style="width: 70%;"><input name="formulaColumn" style="display: none;"></td>'
							+ '<td style="width: 30%;"><select  name="formulaType" style="width: 60%;"><option value="1">+</option><option value="2">-</option></select></td>'
							+ '<td ><button type="button" name="addBtn" class="btn addRow btn-mini showBtn curSelectedBtn showBtna" onclick="cusFinCommon.planInput(this);"></button><button type="button" name="delBtn" class="btn delRow btn-mini showBtn curSelectedBtn showBtnb" onclick="cusFinCommon.planCancel(this);"></button></td>'
							+'</tr>';
			$("#ys-table tbody").append(htmlStr);
			$("#ys-div").show();
		}
	};
	
	//添加运算项时，选择指标项
	var _selectParm = function(objThis){
		var url = webPath+"/cusFinParm/getListPageForSelect?reportType="+reportType;
		if(reportType == "1"){ //资产负债表
			var zcfzType = $("#newParm-div").find("select[name=zcfzType]").val();
			url = webPath+"/cusFinParm/getListPageForSelect?reportType="+reportType+"&zcfzType="+zcfzType;
		}
		$formulaNameObj = $(objThis);
		dialog({
			title:'选择报表项',
			id:"selectParmDialog",
			backdropOpacity:0,
			height:400,
			width:500,
			url:url,
			onclose:function(){
    			if(typeof(this.returnValue) != "undefined" || typeof(this.returnValue) != null || this.returnValue != ''){
    				$formulaNameObj.val(this.returnValue.codeName);
    				$formulaNameObj.parent().find("input[name=formulaColumn]").val(this.returnValue.codeColumn);
    			}
    		}
		}).showModal();
	};
	
	//删除运算项
	var _planCancel = function(obj){
		if($("#ys-table tbody tr").length == 1){
			return false;
		}
		$(obj).parent().parent().remove();
	};
	
	//增加运算项
	var _planInput = function(obj){
		var htmlStr = '<tr> <td style="width: 50%;"><input name="formulaName" onclick="cusFinCommon.selectParm(this);" style="width: 70%;"><input name="formulaColumn" style="display: none;"></td>'
						+ '<td style="width: 30%;"><select name="formulaType" style="width: 60%;"><option value="1">+</option><option value="2">-</option></select></td>'
						+ '<td ><button type="button" name="addBtn" class="btn addRow btn-mini showBtn curSelectedBtn showBtna" onclick="cusFinCommon.planInput(this);"></button><button type="button" name="delBtn" class="btn delRow btn-mini showBtn curSelectedBtn showBtnb" onclick="cusFinCommon.planCancel(this);"></button></td>'
					+'</tr>';
		$("#ys-table tbody").append(htmlStr);
	};
	
	//关闭
	var _newParmClose = function(){
		dialog.get("newParmDialog").close();
	};
	
	return{
		inputTypeChange:_inputTypeChange,
		newParmClose:_newParmClose,
		selectParm:_selectParm,
		planCancel:_planCancel,
		planInput:_planInput
	};
}(window,jQuery);