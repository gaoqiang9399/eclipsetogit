function createModeMenu(list){
	var $ul = $("#mode-menu");
	$ul.empty();
	$.each(list, function(i,node){
		var $li = $('<li>'+node.wkfModeName+'('+node.wkfModeNo+')<i class="i i-lajitong1"></i></li>');
		$li.appendTo($ul);
		$li.data("node",node);
		$li.bind("click",function(){
			 $("#saveBtn").show();
			 $("#wkfModeNo").val(node.wkfModeNo);
			 selectModeMenu(node);
			 $(this).addClass("on").siblings('li').removeClass("on");
		});
		$li.hover(function(){
			$li.find("i").show();
		},function(){
			$li.find("i").hide();
		});
		$li.find("i").bind("click",function(){
			delModeMenu(node);
		});
	});
}

function selectModeMenu(node){
	$.ajax({
		type:"post",
		async:false,
		cache:false,
		url:webPath+"/wkfVp/findByRoleAjax",
		dataType:"json",
		data:node,
		success:function(jsonData){
			if(jsonData.flag=="success"){
				createModeTable(jsonData.list);
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error:function(){
			alert("error");
		}
	});
}
function delModeMenu(node){
	$.ajax({
		type:"post",
		async:false,
		cache:false,
		url:webPath+"/wkfModeRole/deleteByModeNoAjax",
		dataType:"json",
		data:node,
		success:function(jsonData){
			if(jsonData.flag=="success"){
				window.top.alert(jsonData.msg,1);
				window.location.reload();
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error:function(){
			alert("error");
		}
	});
}

function createModeTable(list){
	console.log(list);
	var $tbody = $("#wkf-tab");
	$tbody.empty();
	$.each(list, function(i,node){
		var $tr = $('<ul></ul>');
		if(node.wkfModeNo!=null&&node.wkfModeNo!=""){
			$tr.append('<li class="li_td li_td1" align="center"><input name="menoNobox" type="checkbox" checked="true" value="'+node.wkfVpMenuNo+'"/></li>');
		}else{
			$tr.append('<li class="li_td li_td1" align="center"><input name="menoNobox" type="checkbox" value="'+node.wkfVpMenuNo+'"/></li>');
		}
		$tr.append('<li class="li_td li_td2" align="center">'+node.wkfVpMenuNo+'</li>');
		$tr.append('<li class="li_td li_td3" align="center">'+node.wkfVpMenuName+'</li>');
		$tr.append('<li class="li_td li_td4" align="left">'+node.wkfVpRemark+'</li>');
		var expr = node.expr!=null?node.expr:"";
		$tr.append('<li class="li_td li_td5" align="left">'+expr+'</li>');
		$tr.append('<li class="li_td li_td6" align="center">'+node.urlType+'</li>');
		$tr.appendTo($tbody);
	});
}

function saveMenuNo(){
	var wkfVpMenuNo="";
    $("[name='menoNobox']").each(function(){
    	if($(this).prop("checked")){
    		wkfVpMenuNo+=$(this).val()+",";
    	}
    });
    var wkfVpNo = $("#wkfVpNo").val();
    var wkfModeNo = $("#wkfModeNo").val();
    if(wkfVpNo==""&&typeof(wkfVpNo)=="undefined"){
    	window.top.alert("请刷新重试！",0);
    	return false;
    }
    if(wkfModeNo==""&&typeof(wkfModeNo)=="undefined"){
    	window.top.alert("请刷新重试！",0);
    	return false;
    }
   $.ajax({
		type:"post",
		async:false,
		cache:false,
		url:webPath+"/wkfModeRole/insertRoleAjax",
		dataType:"json",
		data:{wkfVpMenuNo:wkfVpMenuNo,wkfModeNo:wkfModeNo,wkfVpNo:wkfVpNo},
		success:function(jsonData){
			if(jsonData.flag=="success"){
				window.top.alert(jsonData.msg,1);
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error:function(){
			alert("error");
		}
	});
}

function addWkfModeRole(){
	var wkfModeNo = $("#modeNo").val();
    var wkfModeName = $("#modeName").val();
    var wkfVpNo = $("#wkfVpNo").val();
    if(wkfModeName==""&&typeof(wkfModeName)=="undefined"){
    	window.top.alert(top.getMessage("NOT_FORM_EMPTY", "模块描述"),0);
    	return false;
    }
    if(wkfModeNo==""&&typeof(wkfModeNo)=="undefined"){
    	window.top.alert(top.getMessage("NOT_FORM_EMPTY", "模块编号"),0);
    	return false;
    }
    if(wkfVpNo==""&&typeof(wkfVpNo)=="undefined"){
    	window.top.alert("请刷新重试！",0);
    	return false;
    }
    $.ajax({
		type:"post",
		async:false,
		cache:false,
		url:webPath+"/wkfModeRole/insertModeAjax",
		dataType:"json",
		data:{wkfModeName:wkfModeName,wkfModeNo:wkfModeNo,wkfVpNo:wkfVpNo},
		success:function(jsonData){
			if(jsonData.flag=="success"){
				window.top.alert(jsonData.msg,1);
				window.location.reload();
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error:function(){
			alert("error");
		}
	});
}