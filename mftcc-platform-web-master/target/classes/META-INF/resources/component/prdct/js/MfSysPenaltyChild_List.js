var oldVal = '';
function addTDEvent(){
	$("#tablist").find("tr").eq(1).hide();
	$("#tablist>tbody").on('click', 'td', function(){
		var trObj = $(this).parents('tr');
		var trIndex = trObj.index();
		var tdIndex = $(this).index();
		if(trIndex == 0){
			trObj.next().find('td:eq(0)').click();
		}else{
			var text = $(this).text().replace(/(^\s*)|(\s*$)/g, "");
			var inputCount = $(this).children("input").length;
			if(inputCount == 0) {
				var inputStr;
				if(tdIndex < 5){
					oldVal = text;
					/*if(tdIndex==4){//阶梯比例百分制
						var debit = tdIndex == 3 ? "debit" : "";
						inputStr = $("<input type='text' class='editbox money " + debit + "' onkeyup='clearSiblsMoney(this)' datatype='12' onkeydown='enterKey();' autocomplete='off'>");
						if(text!='') text = text.replace(/[^\d\.-]/g, "");
					}else{*/
					inputStr = $("<input type='text' class='editbox'  maxlength='2' onkeydown='enterKey();' autocomplete='off'>");
					//$(this).html(inputStr).addClass('pad0');
					$(this).html(inputStr);
					$(this).children('.editbox').focus().val(text);
				}
			}else{
				$(this).children('.editbox').focus();
			}
		}
	});
	
	//光标离开事件，触发保存
	$("#tablist>tbody").on('blur', '.editbox', function(){
		var trObj = $(this).parents('tr');
		var trIndex = trObj.index();
		var isdate = $(this).hasClass('cw-date-icon');
		var val = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
		if(!isdate){
			var $parent = $(this).parent();
			var ismoney = $(this).hasClass('money');
			if(val!='' && ismoney){
				toMoney(this);
				func_uior_valTypeImm(this);
				val = $(this).val();
			}
			//比较上下限
			var minValue = trObj.find(".min-value").text();
			var $minInput =trObj.find(".min-value").find("input"); 
			if($minInput.length>0){
				minValue = $minInput.val();
			}
			var maxValue = trObj.find(".max-value").text();
			var $maxInput =trObj.find(".max-value").find("input"); 
			if($maxInput.length>0){
				maxValue = $maxInput.val();
			}
			if(minValue*1>maxValue*1){
				alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":$("th[name=surplusLoanMouthMax]").text(),"timeTwo":$("th[name=surplusLoanMouthMin]").text()}),0);
				$parent.html(oldVal);
				return false;
			}
			$parent.html(val);
		}
		if(oldVal != val && trIndex > 0){
			insertPenaltyChild(trObj);
		}
	});
	
	operatEvent();
}

function operatEvent(){
	//添加一行
	$('.ui-icon-plus').unbind().on('click', function(){
		var trObj = $(this).parents('tr');
		var nextTr = $(trObj.prop('outerHTML'));
		nextTr.find('td').slice(1,5).html('');
		nextTr.find('td').slice(3,5).children('input').val('');
		trObj.after(nextTr);
		operatEvent();
	});
	//删除一行
	$('.ui-icon-trash').unbind().on('click', function(){
		var trObj = $(this).parents('tr');
		var id = trObj.find('input[name=id]').val();
		if(id!=''){
			deletePenaltyChild(id);
		}
		trObj.remove();
	});
}


//保存违约金阶梯比例
function insertPenaltyChild(trObj){
	var dataParm = getTrData(trObj);
	if(dataParm.surplusLoanMouthMax!='' && dataParm.surplusLoanMouthMin!='' && dataParm.multiStepRatio!=''){
		var url = dataParm.id!=''?webPath+'/mfSysPenaltyChild/updateAjax':webPath+'/mfSysPenaltyChild/insertAjax';
		jQuery.ajax({
			url: url,
			data:{ajaxData: JSON.stringify(dataParm)},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("SUCCEED_SAVE"), 1);
					updateTableData();//重新加载列表数据
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
}

//封装违约金阶梯比例
function getTrData(trObj){
	var data = {};
	data.loanMouthMax=trObj.children('td:eq(0)').text();//贷款期限上限
	data.loanMouthMin=trObj.children('td:eq(1)').text();//贷款期限下限
	data.surplusLoanMouthMax=trObj.children('td:eq(2)').text();//剩余贷款期限上限
	data.surplusLoanMouthMin=trObj.children('td:eq(3)').text();//剩余贷款期限下限
	data.multiStepRatio=trObj.children('td:eq(4)').text();//阶梯比例百分制
	data.id=trObj.children('td:eq(5)').children('input').val();//唯一序号
	data.idMain=trObj.children('td:eq(6)').children('input').val();//主表mf_sys_penalty_main的id
	return data;
}

//删除违约金阶梯比例
function deletePenaltyChild(id){
	jQuery.ajax({
		url: webPath+'/mfSysPenaltyChild/deleteAjax',
		data:{id: id},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				alert(top.getMessage("SUCCEED_DELETE"), 1);
				updateTableData();//重新加载列表数据
			}else if(data.flag == "error"){
				alert(data.msg,0);
			}
		},error:function(data){
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}
			