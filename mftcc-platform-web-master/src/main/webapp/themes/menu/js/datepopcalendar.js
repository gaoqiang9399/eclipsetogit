
function fPopUpCalendarDlg(n){
	$('input[name='+n.name+']').datepicker({
		dateFormat:"yy-mm-dd",
		changeMonth:true,
		changeYear:	true,	
		yearRange:"1900:2100",
		duration : 'fast',   
		showOtherMonths:true,
		showButtonPanel:true
	});
	$('input[name='+n.name+']').focus();
}