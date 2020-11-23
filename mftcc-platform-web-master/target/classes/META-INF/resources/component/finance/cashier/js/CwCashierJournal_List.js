var oldVal = '';
function addTDEvent(){
	$("#tablist>tbody").on('click', 'td', function(){
		/*if($('#accountNo')[0].options.length==0){
			alert('暂无账户存在，请先添加账户', 2, function(){
				toCashierAccount();
			})
			return false;
		}*/
		var accountNo = $('#accountNo').val();
		if(!accountNo){
			//top.getMessage("WAIT_OPERATION", "处理")'暂无账户存在，请先添加账户'
			alert(top.getMessage("FIRST_OPERATION", "先添加账户，暂无账户"), 2, function(){
				toCashierAccount();
			})
			return false;
		}
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
					if(tdIndex==0){//日期
						inputStr = $("<input type='text' class='editbox cw-date-icon' onkeydown='enterKey();' autocomplete='off' readonly onclick='fPopUpCalendarDlg({choose:vchDateBack});'>");
					}else if(tdIndex > 2){
						var debit = tdIndex == 3 ? "debit" : "";
						inputStr = $("<input type='text' class='editbox money " + debit + "' onkeyup='clearSiblsMoney(this)' datatype='12' onkeydown='enterKey();' autocomplete='off'>");
						if(text!='') text = text.replace(/[^\d\.-]/g, "");
					}else{
						inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
					}
					$(this).html(inputStr).addClass('pad0');
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
				$parent.prev('td').removeAttr('style');
			}
			$parent.html(val).removeClass('pad0');
		}
		if(oldVal != val && trIndex > 0){
			insertJournal(trObj);
		}
	});
	
	//生成凭证
//	$('#tablist>tbody').off('click', '.createVoucher,.openLink,.deleteVoucher');
	$('.createVoucher').off('click').on('click', function(){
		var uid = $(this).data('id') + '';
		var param = JSON.stringify({'which': 'cashier', 'uid': uid});
		window.parent.openBigForm(encodeURI(webPath+'/cwVoucherMst/toVoucherAddSet?ajaxData='+param), '凭证新增',updateTableData);
	});
	
	//凭证详情
	$('.openLink').off('click').on('click', function(){
		var voucherid = $(this).data('voucherid') + '';
		window.parent.openBigForm(encodeURI(webPath+'/cwVoucherMst/toVoucherEdit?which=cashier&voucherNo='+voucherid), '凭证详情',updateTableData);
	});
	
	//凭证删除
	$('.deleteVoucher').off('click').on('click', function(){
		var voucherid = $(this).data('voucherid') + '';
		jQuery.ajax({
			url:webPath+'/cwVoucherMst/deleteAjax',
			data:{voucherNo:voucherid},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("SUCCEED_OPERATION") ,1);
					updateTableData();//重新加载列表数据
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	});
	
	
	operatEvent();
}

function operatEvent(){
	//添加一行
	$('.ui-icon-plus').unbind().on('click', function(){
		var trObj = $(this).parents('tr');
		var nextTr = $(trObj.prop('outerHTML'));
		nextTr.find('td:eq(1)').children('input').val('');
		nextTr.find('td').slice(2,7).html('');
		nextTr.find('td').slice(7,11).children('input').val('');
		trObj.after(nextTr);
		operatEvent()
	});
	//删除一行
	$('.ui-icon-trash').unbind().on('click', function(){
		var trObj = $(this).parents('tr');
		var uid = trObj.find('input[name=uid]').val();
		if(uid!=''){
			deleteJournal(uid);
		}
		trObj.remove();
	});
}

//清理金额， 如果是借方输入金额， 贷方清空
function clearSiblsMoney(obj){
	var $parent = $(obj).parent();
	if($(obj).hasClass('debit')){
		$parent.next().html('');
	}else{
		$parent.prev().html('');
	}
}

//日期选择回调处理
function vchDateBack(obj){
	var date = obj.replace(/\-/g, "");
	var wk = $('#selected-period>span').text().replace('年', '').replace('期', '');
	if(wk==date.substring(0, 6)){
		$('.pad0').html(obj);
		if(oldVal != obj){
			var trObj = $('.pad0').parents('tr');
			insertJournal(trObj);
		}
		$('.pad0').removeClass('pad0');
	}else{
		alert(top.getMessage("FIRST_SELECT_FIELD",wk.substring(0 , 4) + "年" + wk.substring(4) +"月的时间"), 1);
//		alert('请选择'+ wk.substring(0 , 4) + '年' + wk.substring(4) +'月的时间！', 1);
	}
	
}

//保存日记账数据
function insertJournal(trObj){
	var dataParm = getTrData(trObj);
	if(dataParm.voucherDate!='' && dataParm.remark!='' && (dataParm.chrDebit>0 || dataParm.chrCredit>0)){
		jQuery.ajax({
			url: webPath+'/cwCashierJournal/insertAjax',
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

//封装日记账数据
function getTrData(trObj){
	var data = {};
	data.accountNo= $('#accountNo').val();
	data.voucherDate=trObj.children('td:eq(0)').text();
	data.tranceNo=trObj.children('td:eq(1)').children('input').val();
	data.remark=trObj.children('td:eq(2)').text();
	data.chrDebit=trObj.children('td:eq(3)').text().replace(/[^\d\.-]/g, "")||'0';
	data.chrCredit=trObj.children('td:eq(4)').text().replace(/[^\d\.-]/g, "")||'0';
	data.uid=trObj.children('td:eq(7)').children('input').val();
	return data;
}

//保存日记账数据
function deleteJournal(uid){
	jQuery.ajax({
		url: webPath+'/cwCashierJournal/deleteAjax',
		data:{uid: uid},
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
			