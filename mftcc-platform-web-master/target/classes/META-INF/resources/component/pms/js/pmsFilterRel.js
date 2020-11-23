function valiParma(obj){
	if(valiFormat($(obj).find("[name=actionJspSour]"))|valiFormat($(obj).find("[name=actionJspComp]"))){
		alert("格式不正确",0);
		return false;
	}
	return true;
}
function valiFormat(obj){
	var val = $(obj).val();
	var flag = true;
	if(val.indexOf("+")>-1){
		var arr = val.split("+");
		arr[0].indexOf(".action")>-1&&arr[1].indexOf(".jsp")>-1?flag=false:flag=true;
	}
	if(flag){
		var $label = $('<label class="Required-font"><i class="fa fa-exclamation-circle"></i>格式不正确!</label>');
		$label.appendTo($(obj).parent());
		$(obj).addClass("Required");
	}
	return flag;
}