;
var MfOaBudgetInsert = function(window, $) {
	
	var _init = function () {
		var amttbody = $('#budgetamt-div').find('#tab');
		var trCount = amttbody.find("tr").length;
		if(query==''){
			if(trCount==0){
				_addNewTrHtml(amttbody);
				_operatEvent();
			}else{
				_addTrData(trCount, amttbody);
				_operatEvent();
			}
		}else{
			$('.hidden-content').addClass('hidden');
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
	};
	
	var trName = ['budgetType', 'itemName', 'compleTarget', 'compleDate', 'zrOpName', 'khOpName', 'accName', 'budgetClass', 'budgetAmt', 'remark', 'operat'];
	var _addNewTrHtml = function(table){
		var $tr = $('<tr></tr>');
		for(var i in trName){
			if(trName[i] == 'operat'){
				$tr.append('<td><p class="operating" data-id="3"><a class="ui-icon ui-icon-plus" title="新增">新增</a><a class="ui-icon ui-icon-trash" title="删除">删除</a></p></td>');
			}else if(trName[i] == 'budgetType'){
				$tr.append('<td class="'+trName[i]+'"><select class="editbox" onkeydown="enterKey();" autocomplete="off"><option value="0">有计划无预算</option><option value="1">有计划有预算</option><option value="2">无计划有预算</option></select></td>');
			}else if(trName[i] == 'compleDate' || trName[i] == 'zrOpName' || trName[i] == 'khOpName' || trName[i] == 'accName'){
				$tr.append('<td class="'+trName[i]+'"><input type="text" class="editbox" onkeydown="enterKey();" readonly autocomplete="off"></td>');
			}else if(trName[i] == 'budgetAmt'){
				$tr.append('<td class="'+trName[i]+'"><input type="text" class="editbox" maxlength="10" datatype="15" onblur="func_uior_valTypeImm(this);resetTimes();sumBudgetAmt();" onkeydown="enterKey();" autocomplete="off"></td>');
			}else
				$tr.append('<td class="'+trName[i]+'"><input type="text" class="editbox" onkeydown="enterKey();" autocomplete="off"></td>');
		}
		table.append($tr);
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
				}else if(trName[i] == 'budgetType'){
					$td.html('<select class="editbox" onkeydown="enterKey();" autocomplete="off"><option value="0">有计划无预算</option><option value="1">有计划有预算</option><option value="2">无计划有预算</option></select>');
					$td.children('select').val(val);
				}else if(trName[i] == 'compleDate' || trName[i] == 'zrOpName' || trName[i] == 'khOpName' || trName[i] == 'accName'){
					$td.html('<input type="text" class="editbox" value="'+val+'" onkeydown="enterKey();" readonly autocomplete="off">');
				}else if(trName[i] == 'budgetAmt'){
					$td.html('<input type="text" class="editbox" value="'+val+'" maxlength="10" datatype="15" onblur="func_uior_valTypeImm(this);resetTimes();sumBudgetAmt();" onkeyup="toMoney(this)" onkeydown="enterKey();" autocomplete="off">');
				}else
					$td.html('<input type="text" class="editbox" value="'+val+'" onkeydown="enterKey();" autocomplete="off">');
			}
		}
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
		//绑文本事件
		$('.compleDate').children('input').unbind().on('click', function(){
			fPopUpCalendarDlg({});
		});
		$('.accName').children('input').unbind().on('click', function(){
			var input = $(this);
			openComItemDialog('0', function(data){
				input.val(data.showName);
			});
		});
		$('.zrOpName, .khOpName').children('input').unbind().on('click', function(){
			var input = $(this);
			selectUserDialog(function(data){
				input.val(data.opNo + '/' + data.opName);
			});
		});
	};
	
	var _sumBudgetAmt = function(){
		var amt = 0;
		$('#budgetamt-div').find('.budgetAmt').each(function(){
			var val = $(this).children('input').val();
			amt = amt + Number(val.replace(/,/g, ""));
		});
		$('input[name=budgetSum]').val(amt).keyup();
	};
	
	//审批提交
	var _doSubmit = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var budgetSum = $('input[name=budgetSum]').val();
			if(Number(budgetSum)==0){
				top.alert(top.getMessage("NOT_SMALL_TIME", {"timeOne":"预算总额" , "timeTwo": "0"}),1);
				return false;
			}
			var datas = [];
			//封装列表
			$("#budgetamt-div").find("tbody tr").each(function(index){
				var entity = {};
				$thisTr = $(this);
				$thisTr.find("td").each(function(){
					var cls = $(this).attr('class');
					var val = $(this).children('input').val();
					if(cls=='budgetType'){
						val = $(this).children('select').val();
					}
					if( cls== 'budgetAmt'){
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
						backList();						
					}else{
						alert(data.msg,0);
					}
				}
			});	
		}
		
	};
	
	var _backList = function(){
		myclose_click();
	};
	
  var _ajaxGetById = function (obj,msg,url){
	    top.addFlag = false;
		top.openBigForm(url,msg, function() {
		myclose();
		if (top.addFlag) {
			window.location.reload();
		}
	});
  };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxGetById : _ajaxGetById,
		sumBudgetAmt : _sumBudgetAmt,
		backList : _backList,
		doSubmit : _doSubmit
	};
}(window, jQuery);