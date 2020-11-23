var termNum=1;
//新增
function planInput(){
	//parmDicUrl = url;
	var $tableObj = $("#replan-div").find("table.ls_list");
	var trHtml = $('<tr class="addTr">');
	var tdHtml = '<td align="center"></td>';
	$("button[name=addBtn]").remove();
	$("button[name=delBtn]").remove();
	$tableObj.find("thead tr th").each(function(index,obj){
		var width = $(obj).width();
		var name = $(obj).attr("name");
		var $tdHtml = $(tdHtml).css("width",width+"px");
		if(name=="termNum"){
			$tdHtml.append(termNum + '<input class="readonly_border" datatype="0" name="'+name+'" readonly="" type="text" style="display:none;" value="'+termNum+'"/>');
			termNum=termNum+1;
		}else if(name=="beginDate" || name=="endDate"){
			$tdHtml.append('<input name="'+name+'" datatype="6" maxlength="8" readonly="" type="text"   onfocus="fPopUpCalendarDlg({min: new Date().toLocaleDateString()});" onmousedown="enterKey()" onkeydown="enterKey();" style="width:auto;text-align: center;height:30px;" class="datelogo BOTTOM_LINE"/>');
		}else if(name=="editBtn"){
			$tdHtml.append('<button name="addBtn" class="btn addRow btn-mini showBtn curSelectedBtn showBtna" onclick="planInput();"></button><button name="delBtn" class="btn delRow btn-mini showBtn curSelectedBtn showBtnb" onclick="planCancel();"></button>');
		}else{
			var html = "<input name='"+name+"' datatype='12' maxlength='20' type='text' style='width:auto;text-align: center;height:30px;'  onblur=num_validate('"+name+"');  onkeyup='toMoney(this);' onmousedown='enterKey()' onkeydown='enterKey();''/>";
			$tdHtml.append(html);
		}
		trHtml.append($tdHtml);
	});
	$tableObj.find("tbody").append(trHtml);
	$(".col_content").find(".table_content").mCustomScrollbar("scrollTo","top");
}
function updateEndDateFinc(){
	var putoutDate =  $("input[name=putoutDate]").val();
	var termShow = $("input[name=termShow]").val();
	var term = $("input[name=termMonth]").val();
	var termType = $("[name=termType]").val();
	if(term=='') term = '0';
	var intTerm = parseInt(term);
    var d,str;
	if(1==termType){ //融资期限类型为月 
		d = new Date(putoutDate);
		d.setMonth(d.getMonth()+intTerm);
		str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
		$("input[name=intstEndDate]").val(str);
	}else{ //融资期限类型为日 
		d = new Date(putoutDate);
	 	d.setDate(d.getDate()+intTerm);
		str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
		$("input[name=intstEndDate]").val(str);
	}
}
//取消操作
function planCancel(){
	if($("#replan-div tbody tr").length == 1){
		return false;
	}
	$("#replan-div tbody tr:last").remove();
	termNum=termNum-1;
	$("#replan-div tbody tr:last").find("td:last").html('<button name="addBtn" class="btn addRow btn-mini showBtn curSelectedBtn showBtna" onclick="planInput();"></button><button name="delBtn" class="btn delRow btn-mini showBtn curSelectedBtn showBtnb" onclick="planCancel();"></button>');
}