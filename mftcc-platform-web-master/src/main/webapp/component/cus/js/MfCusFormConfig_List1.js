var tableId = "tablecusfcon00001";
$(function(){
	if(fromPage=="devView"){//如果是开发者平台使用
		tableId="tablecusformconfigfordev";
	}
	myCustomScrollbar({
    	obj:"#content",//页面内容绑定的id
    	url:webPath+"/mfCusFormConfig/findByPageAjax?formType="+formType+"&regView=1",//列表数据查询的url
    	tableId:tableId,//列表数据查询的table编号
    	tableType:"thirdTableTag",//table所需解析标签的种类
        pageSize:30,//加载默认行数(不填为系统默认行数) 
	    callback:function(){
    		$("table").tableRcswitcher({
    			name:"isBase,useFlag,isMust",onText:"是",offText:"否"});
		}//方法执行完回调函数（取完数据做处理的时候）
    });
});

function formDesignThis(type,url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	$.ajax({
		url:url,
		data:{optCode:type},
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.flag == "success"){
				var url = webPath+'/tech/dragDesginerClient/openForm.action?formId='+data.formId;
//				if(type == "3"){//列表
//					url = webPath + '/tech/dragDesginerClient/openTable.action?tableId='+data.formId;
//				}
				window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
			}else{
				alert(top.getMessage("ERROR_DATA_CREDIT","表单文件"),0);
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL",""),0);
		}
	});
	
};

function resetForm(urlParm){
	if(urlParm.substr(0,1)=="/"){
		urlParm =webPath + urlParm; 
	}else{
		urlParm =webPath + "/" + urlParm;
	}
	url = urlParm.split("?")[0];
	var ids = urlParm.split("?")[1].split("=")[1];
	$.ajax({
		url:url,
		data:{id:ids},
		dataType:'json',
		type:'POST',
		success:function(data){
			if(data.flag == "success"){
				alert(top.getMessage("SUCCEED_OPERATION","重置"),1);
			}else{
				alert(top.getMessage("FAILED_OPERATION","重置"),0);
			}
		},error:function(){
			alert(top.getMessage("FAILED_OPERATION","重置"),0);
		}
	});
};
//字符串替换
String.prototype.replaceAll = function(s1,s2){ 
	return this.replace(new RegExp(s1,"gm"),s2); 
}
function editWeight(obj , id){
	id = id.substring(10);
	$(obj).hide();
	$(obj).after("<input name=\"weight\" style=\"width:165px;text-align: center;\" value=\"\" maxlength=\"30\" type=\"text\" onblur=\"updateWeight(this,'" + id + "');\">");
	$("input[name='weight']")[0].focus();
}
function updateWeight(obj , id){
    var weight = $(obj).val();
    if(weight != $(obj).prev().text() && "" != weight.replaceAll(" ","")){
		$.ajax({
			url:webPath+"/mfCusFormConfig/updateWeightAjax",
			data:{id:id,weight:weight},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$(obj).hide();
					$(obj).prev().text(weight);
					$(obj).prev().show();
					$(obj).remove();
				}else{
					$(obj).hide();
					$(obj).prev().show();
					$(obj).remove();
					window.top.alert(data.msg,0);
					
				}
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
    }else{
    	$(obj).hide();
		$(obj).prev().show();
		$(obj).remove();
    }
}


var MfCusFormConfig_List = function(window, $) {
    var  _refreshModle=function() {
        window.top.alert("刷新模块需稍等，确定刷新吗？",2,function(){
            $.ajax({
                url: "/mfCusFormConfig/refreshModle?formType=" + formType,
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    if (data.flag == "success") {
                        alert(top.getMessage("SUCCEED_OPERATION", "刷新客户信息模块"), 2);

                    } else {
                        alert(top.getMessage("FAILED_OPERATION", "刷新客户信息模块"), 2);
                    }
                }
            })
		})

    }
    return {
        refreshModle : _refreshModle
    };
}(window, jQuery);