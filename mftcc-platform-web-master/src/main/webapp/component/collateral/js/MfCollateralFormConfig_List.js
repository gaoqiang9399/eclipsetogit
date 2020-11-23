$(function(){
	myCustomScrollbar({
    	obj:"#content",//页面内容绑定的id
    	url:webPath+"/mfCollateralFormConfig/findByPageAjax",//列表数据查询的url
    	data:{id:id},
    	tableId:"tabledlcollateralformconfig0001",//列表数据查询的table编号
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
				var url = webPath+'/tech/dragDesginer/openForm.action?formId='+data.formId;
				window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
			}else{
				alert("获取表单文件出错",0);
			}
		},error:function(){
			alert("请求出错",0);
		}
	});
	
};

function resetForm(urlParm){
	url = urlParm.split("?")[0];
	var ids = urlParm.split("?")[1].split("=")[1];
	$.ajax({
		url:url,
		data:{id:ids},
		dataType:'json',
		type:'POST',
		success:function(data){
			if(data.flag == "success"){
				alert("重置成功",1);
			}else{
				alert("重置失败",0);
			}
		},error:function(){
			alert("重置失败",0);
		}
	});
};