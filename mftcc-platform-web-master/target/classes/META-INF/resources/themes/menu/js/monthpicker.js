/**
 * 通用月份选择器
 * @param {Object} n
 * @param {Object} format 月份格式
 */
function fPopUpMonthDlg(n){
	$('input[name='+n.name+']').monthPicker({
		dateFormat:"yymm",
		changeMonth:true,
		changeYear:	true,	
		yearRange:"1900:2100",
		duration : 'fast',   
		showOtherMonths:true,
		showButtonPanel:true
	});
	$('input[name='+n.name+']').focus();
}