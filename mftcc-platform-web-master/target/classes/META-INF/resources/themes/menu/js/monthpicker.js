/**
 * ͨ���·�ѡ����
 * @param {Object} n
 * @param {Object} format �·ݸ�ʽ
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