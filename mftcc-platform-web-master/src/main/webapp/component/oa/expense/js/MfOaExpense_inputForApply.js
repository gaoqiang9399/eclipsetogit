;
var MfOaExpenseInputForApply = function(window, $) {
	
	var _init = function () {
		var amttbody = $('#expenseList-div').find('#tab');
		var trCount = amttbody.find("tr").length;
			if(trCount==0){
				_addNewTrHtml(amttbody);
				_operatEvent();
			}else{
				_addTrData(trCount,amttbody);
				_operatEvent();
			}
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_doSubmit("#OaExpenseInsert");
	};
	
	var trName = ['accName', 'accNo', 'expenseAmt', 'remark', 'operat'];
	var _addNewTrHtml = function(table){
			var $tr = $('<tr></tr>');
			for(var i in trName){
				if(trName[i] == 'operat'){
					$tr.append('<td><p class="operating" data-id="3"><a class="ui-icon ui-icon-plus" title="新增">新增</a><a class="ui-icon ui-icon-trash" title="删除">删除</a></p></td>');
				}else if(trName[i] == 'expenseAmt'){
					$tr.append('<td class="'+trName[i]+'"><input type="text" class="editbox"  placeholder="必填" datatype="12" maxlength="20" onblur="func_uior_valTypeImm(this);resetTimes();MfOaExpenseInputForApply.sumBudgetAmt();" onfocus="selectInput(this);" onkeyup="toMoney(this)" autocomplete="off"></td>');
				}else if(trName[i] == 'accName'){
					$tr.append('<td class="'+trName[i]+'"><input type="text" class="editbox"  placeholder="必填" datatype="0" maxlength="100"onblur="func_uior_valTypeImm(this);" onkeydown="enterKey();" autocomplete="off"></td>');
				}else if(trName[i] == 'accNo'){
					$tr.append('<td class="'+trName[i]+'"><input type="text" class="editbox"  placeholder="必填" datatype="0" maxlength="50" onblur="func_uior_valTypeImm(this);" onkeydown="enterKey();" readonly autocomplete="off"></td>');
				}else if(trName[i] == 'remark'){
					$tr.append('<td class="'+trName[i]+'"><input type="text" class="editbox"  placeholder="200字以内" datatype="0" maxlength="400" onblur="func_uior_valTypeImm(this);" onkeydown="enterKey();" autocomplete="off"></td>');
				}
			table.append($tr);
		};
	};
	var _addTrData = function(len, table){		
		for(var j=0; j < len; j++){
			var $tr = $(table).find('tr:eq('+j+')');
			for(var i in trName){
				var $td = $tr.find('td:eq('+i+')');
				$td.addClass(trName[i]);
				var val = $td.text();
				if(trName[i] == 'operat'){
					$td.html('<p class="operating" data-id="3"><a class="ui-icon ui-icon-plus" title="新增">新增</a><a class="ui-icon ui-icon-trash" title="删除">删除</a></p>');
				}else if(trName[i] == 'accName' || trName[i] == 'accNo'){
					$td.html('<input type="text" class="editbox" value="'+val+'" onkeydown="enterKey();" readonly autocomplete="off">');
				}else if(trName[i] == 'expenseAmt'){
					$td.html('<input type="text" class="editbox" value="'+val+'" datatype="12" onblur="func_uior_valTypeImm(this);resetTimes();MfOaExpenseInputForApply.sumBudgetAmt();" onkeyup="toMoney(this)" onkeydown="enterKey();" autocomplete="off">');
				}else {
					$td.html('<input type="text" class="editbox" value="'+val+'" onkeydown="enterKey();" autocomplete="off">');
				}
			}
		};
	};
	
	var _operatEvent = function(){
		//添加一行
		$('.ui-icon-plus').unbind().on('click', function(){
			var trObj = $(this).parents('tr');
			var nextTr = $(trObj.prop('outerHTML'));
			nextTr.find('td').children('input').val('');
// 			nextTr.find('td').slice(2,7).html('');
// 			nextTr.find('td').slice(7,11).children('input').val('');
			trObj.after(nextTr);
			_operatEvent();
		});
		//删除一行
		$('.ui-icon-trash').unbind().on('click', function(){
			var trObj = $(this).parents('tr');
			var length = $(this).parents('tbody').find('tr').length;
			if(length==1){
				trObj.find('td').children('input').val('');
			}else
				trObj.remove();
		});
		//对科目输入框添加选择事件
		$('.accNo').children('input').unbind().on('click', function(){
			var input = $(this);
			openComItemDialog('0', function(data){
				input.val(data.showName);
			});
		});
		
		
	};
	
	var _sumBudgetAmt = function(){
		var amt = 0;
		$('#expenseList-div').find('.expenseAmt').each(function(){
			var val = $(this).children('input').val();
			amt = amt + Number(val.replace(/,/g, ""));
		});
		$('input[name=amt]').val(amt).keyup();
	};
	
	//审批提交
	var _doSubmit = function(obj){
		$(".insert").bind("click", function(event){			
			alert(top.getMessage("CONFIRM_OPERATION","立即提交"),2,function(){
				$('input[name=expenseSts]').val("2");	
				_insrt(obj);
			},function(){
				$('input[name=expenseSts]').val("1");
				_insrt(obj);
			});
			
		});
	};
	var _insrt = function (obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
		var datas = [];
		//封装列表
		$("#expenseList-div").find("tbody tr").each(function(index){
			var entity = {};
			$thisTr = $(this);
			$thisTr.find("td").each(function(){
				var cls = $(this).attr('class');
				var val = $(this).children('input').val();
				if( cls== 'expenseAmt'){
					val = Number(val.replace(/,/g, ""));
				}
				entity[cls] = val;
			});
			datas.push(entity);
		});
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		var ajaxUrl = $(obj).attr("action");
		$.ajax({
			url : ajaxUrl,
			type : "POST",
			dataType : "json",
			data:{ajaxData:dataParam, ajaxDataList:JSON.stringify(datas)},
			success : function(data) {
				if(data.flag=="success"){
					window.top.alert(data.msg,3);
					top.addFlag = true;
					myclose_click();	
				}else{
					alert(data.msg,0);
				}
			}
		});	
	}
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		sumBudgetAmt : _sumBudgetAmt,
		doSubmit : _doSubmit
	};
}(window, jQuery);
