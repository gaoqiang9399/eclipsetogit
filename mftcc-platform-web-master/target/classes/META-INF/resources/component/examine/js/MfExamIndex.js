;
var MfExamIndex=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	var _ajaxInsertThis=function(obj){
		var indexSubDatas = [];
		$.each($("#examIndexSubList").find("tbody tr"),function(i,obj){
			var data={};
			var subName=$(obj).find("input[name=subName]").val();
			data["subName"]=subName;
			data["remark"]=$(obj).find("input[name=remark]").val();
			if(subName!=""){
				indexSubDatas.push(data);
			}
		});
		if(indexSubDatas.length==0){
			alert(top.getMessage("FIRST_COMPLETE_INFORMAATION","指标子项"),0);
			return;
		};
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam,indexSubDataList:JSON.stringify(indexSubDatas)},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							window.top.alert(data.msg, 1);
							top.addFlag=true;
							myclose_click();
						}
					},error:function(data){
						LoadingAnimate.stop();
						 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			
			}
	};
	var _ajaxUpdateThis=function(obj){
		var indexSubDatas = [];
		$.each($("#examIndexSubList").find("tbody tr"),function(i,obj){
			var data={};
			var subName=$(obj).find("input[name=subName]").val();
			data["subName"]=subName;
			data["remark"]=$(obj).find("input[name=remark]").val();
			if(subName!=""){
				indexSubDatas.push(data);
			}
		});
		if(indexSubDatas.length==0){
			alert(top.getMessage("FIRST_COMPLETE_INFORMAATION","指标子项"),0);
			return;
		};
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam,indexSubDataList:JSON.stringify(indexSubDatas)},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							window.top.alert(data.msg, 1);
							top.addFlag=true;
							myclose_click();
						}
					},error:function(data){
						LoadingAnimate.stop();
						 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			
			}
	};
	var _addExamIndexSub=function(){
		var propertyId =$("input[name=propertyId]").val();
		var propertyNo =$("input[name=propertyNo]").val();
		var htmlStr ='<tr>'+
		'<td align="center" style="display:none"><input type="hidden" name="subId" value=""></td>'+
		'<td align="center" class="subName"><input type="text" name="subName" value=""></td>'+
		'<td align="center" class="remark"><input type="text" name="remark" value=""></td>'+
		'<td align="center" class="editEvalSub"><a href="javascript:void(0);" onclick="MfExamIndex.cancelAdd(this);return false;" class="abatch" >取消</a></td>';
		//'<td align="center"><a href="javascript:void(0);" onclick="insertEvalSub(this,\webPath+'/mfEvalIndexSub/insertAjax?subId=\');return false;" class="abatch" >保存</a></td>'+
		//'<td id = "delete" align="center" style="color:#c4c4c4">删除</td></tr>';
		$("tbody[id=tab]").append(htmlStr);
	}
	var _cancelAdd=function(obj){
		$(obj).parents("tr").remove();
	}
	var _deleteIndexSub=function(obj,url){
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			jQuery.ajax({
				url:url,
				data:{},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						$(obj).parents("tr").remove();
					}
				},error:function(data){
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	}
	return{
		init:_init,
		ajaxInsertThis:_ajaxInsertThis,
		ajaxUpdateThis:_ajaxUpdateThis,
		addExamIndexSub:_addExamIndexSub,
		cancelAdd:_cancelAdd,
		deleteIndexSub:_deleteIndexSub
	};
}(window,jQuery);