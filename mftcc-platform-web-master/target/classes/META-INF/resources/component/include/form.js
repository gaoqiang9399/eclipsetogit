$(function(){
	formRang();//范围输入域初始化
});

/**
 *表单公共js
 */

/*表单赋值方法*/
function setFormEleValue(name, val,obj){
    var i;
    var htmlType;
    var vals;
	if(obj!=null&&typeof(obj)!==undefined){
		$obj = $(obj);
		if(val == ""||val != ""){
	        htmlType = $obj.find("[name='"+name+"']").attr("type");
	        if(htmlType == "text" || htmlType == "select-one" || htmlType == "hidden" || htmlType == "button"){
	           $obj.find("[name='"+name+"']").val(val);
	        }else if(htmlType == "radio"){
	            if(val==""){
	            	checkValue(name,obj);
	            }else{
	            	 $obj.find("input[type=radio][name='"+name+"'][value='"+val+"']").attr("checked",true);
	            }
	        }else if(htmlType == "checkbox"){
	            if(val==""){
	            	checkValue(name,obj);
	            }else{
	            	//var vals;
	            	if(val.indexOf(",")>-1){
	            		vals = val.split(",");
	            	}else{
	            		vals = val.split("|");
	            	}
		            for(i=0; i < vals.length; i++){
		                $obj.find("input[type=checkbox][name='"+name+"'][value='"+vals[i]+"']").attr("checked",true);
		            }
	            }
	        }else{
	        	$obj.find("textarea[name='"+name+"']").val(val);
	        	if($obj.find("select[name='"+name+"']").html()!==undefined&&$("select[name='"+name+"'] option").length>0){
	        		if(val==""){
	        			$obj.find("select[name='"+name+"']").get(0).options[0].selected = true;
	        		}else{
	        			 for(i=0;i<$obj.find("select[name='"+name+"'] option").length;i++){
		        			if($obj.find("select[name='"+name+"']").get(0).options[i].value == val){
		        				$obj.find("select[name='"+name+"']").get(0).options[i].selected = true; 
		        				break;  
		        			}
	        			 } 
	        		}
	        	}
	        }
	    }
	}else{
		if(val == ""||val != ""){
	        htmlType = $("[name='"+name+"']").attr("type");
	        if(htmlType == "text" || htmlType == "select-one" || htmlType == "hidden" || htmlType == "button"){
	            $("[name='"+name+"']").val(val);
	        }else if(htmlType == "radio"){
	            if(val==""){
	            	checkValue(name);
	            }else{
	            	 $("input[type=radio][name='"+name+"'][value='"+val+"']").attr("checked",true);
	            }
	        }else if(htmlType == "checkbox"){
	            if(val==""){
	            	checkValue(name);
	            }else{
	            	//var vals;
	            	if(val.indexOf(",")>-1){
	            		vals = val.split(",");
	            	}else{
	            		vals = val.split("|");
	            	}
		            for(i=0; i < vals.length; i++){
		                $("input[type=checkbox][name='"+name+"'][value='"+vals[i]+"']").attr("checked",true);
		            }
	            }
	        }else{
	        	$("textarea[name='"+name+"']").val(val);
	        	if($("select[name='"+name+"']").html()!==undefined&&$("select[name='"+name+"'] option").length>0){
	        		if(val==""){
	        			$("select[name='"+name+"']").get(0).options[0].selected = true;
	        		}else{
	        			 for(i=0;i<$("select[name='"+name+"'] option").length;i++){
		        			if($("select[name='"+name+"']").get(0).options[i].value == val){
		        				$("select[name='"+name+"']").get(0).options[i].selected = true; 
		        				break;  
		        			}
	        			 } 
	        		}
	        	}
	        }
	    }
	}
}
/*判断check/radio是否选中*/
function checkValue(name,obj){
    var $ckox;
	if(obj!==undefined&&obj!=null){
		$obj = $(obj);
		$ckox = $obj.find("input[name='"+name+"']");
		if($ckox==null) return;
		if($ckox[0].type == 'radio'){
			$ckox.removeAttr("checked");
			return;
		}else if($ckox[0].type == 'checkbox'){
			$ckox.removeAttr("checked");
			return;
		}
	}else{
		$ckox = $("input[name='"+name+"']");
		if($ckox==null) return;
		if($ckox[0].type == 'radio'){
			$ckox.removeAttr("checked");
			return;
		}else if($ckox[0].type == 'checkbox'){
			$ckox.removeAttr("checked");
			return;
		}
	}
}

/**
 * 表单异步保存(保函更新和新增)
 * @param {Object} obj
 * @param {Object} url
 */
window.ajaxSave = function(obj,url,callback){
	var contentForm = $(obj).parents(".content_table");
	var tableId = contentForm.find(".ls_list").attr("title");
	var flag = submitJsMethod(obj, '');
	if(flag){
		var ajaxUrl = $(obj).attr("action");
		if(ajaxUrl===undefined||ajaxUrl==null||ajaxUrl==""){
			ajaxUrl = url;
		}
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		jQuery.ajax({
			url:ajaxUrl,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("SUCCEED_OPERATION"),1);
					 if(data.tableData!=undefined&&data.tableData!=null){
						var tableHtml = $(data.tableData).find("tbody").html();
					 	contentForm.find(".ls_list tbody").html(tableHtml);
					 }
					 $(obj).removeAttr("action");
					 contentForm.find(".content_form").hide();
					 initIfreamHeight();
					 if(callback){
					 	callback.call(this);
					 }
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	}
};
/**
 * 异步提交方法（大表单一体）
 * @param {Object} obj
 * @param {Object} url
 * @param {Object} callback
 */
window.ajaxSubmit = function(obj,url,callback){
	var contentForm = $(obj).parents(".content_table");
	var tableId = contentForm.find(".ls_list").attr("title");
	var flag = submitJsMethod(obj, '');
	alert("确定提交此操作吗？",2,function(){
		if(flag){
			var ajaxUrl = $(obj).attr("action");
				if(ajaxUrl===undefined||ajaxUrl==null||ajaxUrl==""){
					ajaxUrl = url;
				}
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				jQuery.ajax({
					url:ajaxUrl,
					data:{ajaxData:dataParam,tableId:tableId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							alert(top.getMessage("SUCCEED_OPERATION"),1);
							 if(data.tableData!=undefined&&data.tableData!=null){
								var tableHtml = $(data.tableData).find("tbody").html();
							 	contentForm.find(".ls_list tbody").html(tableHtml);
							 }
							 $(obj).removeAttr("action");
							 contentForm.find(".content_form").hide();
							 initIfreamHeight();
							 if(callback){
							 	callback.call(this);
							 }
						}else if(data.flag=="error"){
						     alert(data.msg,0);
						}
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"),0);
					}
				});
			}
		});
};
/**
 * 异步更新方法
 * @param {Object} obj
 */
window.ajaxUpdate = function(obj){
	var flag = submitJsMethod(obj, '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					 alert(top.getMessage("SUCCEED_OPERATION"),1);
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	}
};
/**
 * 异步新增方法
 * @param {Object} obj
 */
window.ajaxInsert = function(obj,callback){
	var flag = submitJsMethod(obj, '');
	if(flag){
		//var contentForm = $(obj).parents(".content_table");
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
					  $.each(data,function(name,value) {
						   setFormEleValue(name, value);//调用公共js文件的方法表单赋值
					  });
					  if(callback&&typeof(callback)=="function"){
						  callback.call(this,data);
					  }
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	}
};
/**
 * 大表单时一体删除方法
 * @param {Object} obj
 * @param {Object} url
 * @param {Object} callback
 */
window.ajaxFormDelete = function(obj,url,callback){
	var dataParam = JSON.stringify($(obj).serializeJSON());
	var contentForm = $(obj).parents(".content_table");
	var tableId = contentForm.find(".ls_list").attr("title");
	alert(top.getMessage("CONFIRM_DELETE"),2,function(){
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  alert(data.msg,1);
					  $.each(data,function(name,value) {
						   setFormEleValue(name, "");//调用公共js文件的方法表单赋值
					  });
					  if(data.tableData!=undefined&&data.tableData!=null){
						var tableHtml = $(data.tableData).html();
						contentForm.find(".ls_list").html(tableHtml);
					 }
					 $(obj).removeAttr("action");
					 contentForm.find(".content_form").hide();
					 initIfreamHeight();
					 if(callback){
					 	callback.call(this);
					 }
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	});
};
/**
 * 一体式加载删除方法
 * @param {Object} obj
 * @param {Object} url
 * @param {Object} callback
 */
window.ajaxDelete = function(obj,url,callback){
	var dataParam = JSON.stringify($(obj).serializeArray());
	var contentForm = $(obj).parents(".content_table");
	var tableId = contentForm.find(".ls_list").attr("title");
	alert(top.getMessage("CONFIRM_DELETE"),2,function(){
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  alert(top.getMessage("SUCCEED_OPERATION"),1);
					  $.each(data,function(name,value) {
						   setFormEleValue(name, "");//调用公共js文件的方法表单赋值
					  });
					  if(data.tableData!=undefined&&data.tableData!=null){
						var tableHtml = $(data.tableData).html();
						contentForm.find(".ls_list").html(tableHtml);
					 }
					 $(obj).removeAttr("action");
					 contentForm.find(".content_form").hide();
					 if(typeof(callback)=="function"){
						 callback.call(this);
					 }
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	});
};
/**
 * 瀑布式加载删除方法
 * @param {Object} obj
 * @param {Object} url
 */
window.ajaxTrDelete = function (obj,url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	window.top.alert(top.getMessage("CONFIRM_DELETE"),2,function(){
		var ajaxParam = {};
		if(url.indexOf("ActionAjax_")!=-1&&url.indexOf("?")!=-1){//ajax提交
			var urlParams = url.split("?");
			url = urlParams[0];
			$.each(urlParams[1].split("&"), function(index,val){
				var key = val.split("=")[0];
				var value = val.split("=")[1];
				ajaxParam[key] = value;
			});
		}
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				data:ajaxParam,
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						alert(top.getMessage("SUCCEED_OPERATION"),1);
						$(obj).parents("tr").remove();
						updateMyCustomScrollbar.delTrData();
					}else if(data.flag == "error"){
						window.top.alert(data.msg, 0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"),0);
				}
			});
			$(obj).parents(".content_form").hide();
		});
};
//连接url
function connectionUrl(url,tableId){
	if(url.indexOf("?")>-1){
		url+="&tableId="+tableId;
	}else{
		url+="?tableId="+tableId;
	}
	return url;
}

/**
 * 通过url直接更新状态 并返回列表信息处理
 * @param {Object} obj(this对象)
 * @param {Object} url
 * @param {Object} callback(回调函数)
 */
window.ajaxUpdateSts = function (obj,url,callback){
	var table = $(obj).parents(".ls_list");
	var tableId = table.attr("title");
	alert("是否继续进行此操作!",2,function(){
		jQuery.ajax({
			url:connectionUrl(url,tableId),
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("SUCCEED_OPERATION"),1);
					if(data.tableData!=undefined&&data.tableData!=null){
						var tableHtml = $(data.tableData).find("tbody").html();
					 	table.find("tbody").html(tableHtml);
					}
					if(typeof(callback)=="function"){
					 	callback.call(this);
					}
				}else if(data.flag == "error"){
					alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	});
};
/*input新增*/
window.ajaxInput = function(obj,url,adjustHeightFlag){
	$(obj).parents(".content_table").find(".selected").removeClass("selected");
	var contentForm = $(obj).parents(".content_table").find(".content_form");
	var params = url.split("?");
	var ajaxUrl = params[0];
	var param = new Array();
	if(params[1]!==undefined){
		param = params[1].split("&");
	}
	contentForm.find("form").attr("action",ajaxUrl);
	var data = contentForm.find("form").serializeJSON(); 
	 $.each(data,function(name,value) {
	   		setFormEleValue(name, "",contentForm.find("form"));//调用公共js文件的方法表单赋值
  	});
  	if(param!=null&&typeof(param)!==undefined){
  		$.each(param,function(index,value) {
  			var col = value.split("=");
  			setFormEleValue(col[0], col[1],contentForm.find("form"));
	 	});
  	}
	contentForm.show();
	if(adjustHeightFlag){
		$(".content_form").css("height",$(".content_form").find("form").height()+10);

	}
	initIfreamHeight();
};
/**
 * 新增返回form
 * inputUrl 跳转input链接
 * insertUrl 新增保存链接
 */
window.ajaxInputForForm = function(obj,inputUrl,insertUrl){
	$(obj).parents(".content_table").find(".selected").removeClass("selected");
	var $content_from = $(obj).parents(".content_table").find(".content_form");
	var params = inputUrl.split("?");
	var ajaxUrl = params[0];
	var param = new Array();
	if(params[1]!==undefined){
		param = params[1].split("&");
	}
	var data = {};
	if(param!=null&&typeof(param)!==undefined){
  		$.each(param,function(index,value) {
  			var col = value.split("=");
  			data[col[0]]=col[1];
	 	});
  	}
	jQuery.ajax({
		url:ajaxUrl,
		type:"POST",
		dataType:"json",
		data:data,
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				$content_from.find("form").attr("action",insertUrl);
			  	var $formHidden = $(data.formHtml).filter("input[type=hidden]");
		  	    $content_from.find("tbody").html($(data.formHtml).find("tbody").html());
		  	    $content_from.find("form").find("table").parent().find("input[type='hidden']").remove();
		  	    $formHidden.each(function(){
					$content_from.find("form").find("table").parent().append($(this).prop('outerHTML'));
				});
		  	  	$(obj).parents("table").find(".selected").removeClass("selected");
		  	  	$(obj).parents("tr").addClass("selected");
		  		$content_from.show();
			}else{
				 alert(data.msg,0);
			}
		},error:function(data){
			 alert("执行失败！",0);
		}
	});
};
window.colseBtn = function(obj){
	$(obj).parents(".content_form").hide();
	$(obj).parents(".content_table").find(".selected").removeClass("selected");
	$(obj).parents(".content_form").find("td").css({"color":"black","font-weight":"normal"});
	$(obj).parents(".content_form").find("td .Required-font").remove();
	$(obj).parents(".content_form").find("td .Required").removeClass("Required");
	initIfreamHeight();
};
window.ajaxGetById = function(obj,ajaxUrl){
	var $obj = $(obj);
	if(ajaxUrl!==undefined&&ajaxUrl!=null&&ajaxUrl!=""){
		jQuery.ajax({
			url:ajaxUrl,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$obj.parents(".content_table").find(".content_form").find("form").attr("action","");
					  var $content_from =  $(".content_table").find(".content_form");
				  	 $.each(data.formData,function(name,value) {
					   	setFormEleValue(name, value,$content_from.find("form"));
					  });
				  	  $obj.parents("table").find(".selected").removeClass("selected");
				  	  if( $obj.parents("tr").length>0){
				  	  	$obj.parents("tr").addClass("selected");
				  	  }else{
				  	  	$obj.addClass("selected");
				  	  }
					  $content_from.show();
					  initIfreamHeight();
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert("查询失败！",0);
			}
		});
	}else{
		 alert("请检查列表链接",0);
	}
	 if(window.event){
        //e.returnValue=false;//阻止自身行为
        window.event.cancelBubble=true;//阻止冒泡
     }else if(arguments.callee.caller.arguments[0].preventDefault){
        //e.preventDefault();//阻止自身行为
        arguments.callee.caller.arguments[0].stopPropagation();//阻止冒泡
     }
};

window.ajaxGetByIdForForm = function(obj,ajaxUrl){
	if(ajaxUrl!==undefined&&ajaxUrl!=null&&ajaxUrl!=""){
		jQuery.ajax({
			url:ajaxUrl,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$(obj).parents(".content_table").find(".content_form").find("form").attr("action","");
				  	var $content_from =  $(".content_table").find(".content_form");
				  	
				  	var $formHidden = $(data.formHtml).filter("input[type=hidden]");
			  	    $content_from.find("tbody").html($(data.formHtml).find("tbody").html());
			  	    $content_from.find("form").find("table").parent().find("input[type='hidden']").remove();
			  	    $formHidden.each(function(){
						$content_from.find("form").find("table").parent().append($(this).prop('outerHTML'));
					});
			  	  	$(obj).parents("table").find(".selected").removeClass("selected");
			  	  	$(obj).parents("tr").addClass("selected");
			  		$content_from.show();
				  	initIfreamHeight();
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert("查询失败！",0);
			}
		});
	}else{
		alert("请检查列表链接",0);
	}
	 if(window.event){
	    //e.returnValue=false;//阻止自身行为
	    window.event.cancelBubble=true;//阻止冒泡
	 }else if(arguments.callee.caller.arguments[0].preventDefault){
	    //e.preventDefault();//阻止自身行为
	    arguments.callee.caller.arguments[0].stopPropagation();//阻止冒泡
	 }
};

var initIfreamHeight = function(callback){
	if(window.parent.resizeHeight&&$("body .bigform_content").length>0){
		var timeStamp = (new Date()).valueOf();
		$("body").append('<div id="timeStamp" timeStamp="'+timeStamp+'"></div>');
		var bodyHeight = $("body .bigform_content").height();
		//console.log("bodyHeight:"+bodyHeight);
		//bodyHeight = document.body.clientHeight;
		//if(bodyHeight<=150){
			//bodyHeight =150;
		//}
		window.parent.resizeHeight(timeStamp,bodyHeight,callback);
	}
	$("body").find("#timeStamp").remove();
};
window.trClick = function(obj){
	var $obj = $(obj);
	var callback = $obj.find("td").eq(0).find("a").attr("onclick");
	if(callback!==undefined){
		callback = callback.replace("return false;","");
		eval(callback);
		$obj.parents("table").find(".selected").removeClass("selected");
		$obj.addClass("selected");
	}
};

window.ajaxTrUpdate = function (obj,callback){
	var contentForm = $(obj).parents("#rightFormInfo");
	var tr = $(obj).data("elem");
	var table = tr.parents("table");
	var tableId = table.attr("title");
	var flag = submitJsMethod(contentForm[0], '');
	if(flag){
		var ajaxUrl = contentForm.attr("action");
		var dataParam = JSON.stringify(contentForm.serializeArray()); 
		jQuery.ajax({
			url:ajaxUrl,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					 alert(top.getMessage("SUCCEED_OPERATION"),1);
					 if(data.tableData!=undefined&&data.tableData!=null){
						var trHtml = $(data.tableData).find("tbody tr").html();
						tr.html(trHtml);
					 }
					 if(typeof(callback)=="function"){
					 	callback.call(this);
					 }
				}else if(data.flag=="error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	}
};

window.ajaxTrUpdateSts = function (obj,parm,url){
	var table = $(obj).parents(".ls_list");
	var tr = $(obj).parents("tr");
		jQuery.ajax({
			url:url,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					tr.find("td").eq(table.find("th[name="+parm+"]").index()).html(data.parm);
					alert(top.getMessage("SUCCEED_OPERATION"),1);
				}else if(data.flag == "error"){
					alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
};
//判断值是否为空 返回boolean
function isNull(obj){
	if(typeof(obj)!="undefined"&&obj!=""&&obj!=null){
		return false;
	}else{
		return true;
	}
};


//列表展示详情，单字段编辑
window.listShowDetail=function(obj,url,oneUpdateUrl){
	if($("body").data("listElem")&&$("body").data("listElem")==obj){
		return false;
	}
	var $tr = $(obj).parents("tr");
	var col = $tr.find("td").length;
	var oneUrl = "";
	if(oneUpdateUrl){
			oneUrl = oneUpdateUrl;
	}else if(getQueryString(url,"oneUrl")!=null){
		oneUrl = getQueryString(url,"oneUrl");
	}else{
		alert("没有配置单字段编辑URL",0);
		return false;
	}
	if(!$("body").data("listevent")){
		$(window).bind("click",function(e){
			if($(e.target).parents(".listshow-tr").length == 0&&$(e.target).parents(".changeval").length == 0&&$("body").data("listElem")!=e.target ) {
				$(".listshow-tr .info").animate({height: '0px', opacity: 'toggle'}, 500,function(){
					// 姚文豪：增加特殊class来确定样式 + 层级关系不正确 20170707
					//处理点其他连接时样式不能及时清除问题
					$(this).parent().parent().prev().removeClass("add-border-listShow");
					$(this).parent().parent().remove();
					$("body").data("listElem","");
				});
			}
		});
		$("body").data("listevent",true);
	}
	//追加pageView参数，用来区分信息块是来自哪个视图（客户还是业务，解决客户信息块在业务这边展示下拉表单获取不到问题）
	if(typeof(pageView)!="undefined"){
		url = url+"&pageView="+pageView;
	}
	url = webPath+"/"+url;
	jQuery.ajax({
		url:url,
		type:"POST",
		dataType:"json",
		global : false,
		beforeSend:function(){
			window.top.loadding();
		},success:function(data){
			if(data.flag == "success"){
				var tempTr = $('<tr class="listshow-tr"><td colspan="'+col+'"><div class="info"><form id="listForm" name="operform" action="'+webPath + oneUrl+'" method="post">'+data.formHtml+'</form></div></td></tr>');
				$tr.next(".listshow-tr").remove();//6460 微服务-客户首页-股东信息点开显示错误  20190118刘美好将empty改为remove
				$tr.after(tempTr);
				$(".listshow-tr .info").find(".fieldShow").each(function(){
					addTextToNone(this);
				});
				if(typeof(formEditFlag)!="undefined" && formEditFlag=="query"){//业务视角/客户视角登记的表单信息editFlag=query时在不允许单字段编辑
					tempTr.find(".font-smallup").removeClass("dblclickflag");
					tempTr.find(".font-smallup").removeAttr("ondblclick");
				}
				// 姚文豪：增加特殊class来确定样式 20170707
				$tr.addClass("add-border-listShow");
				tempTr.find(".info").animate({ opacity: '1'}, 1000,function(){
					$("body").data("listElem",obj);
				});
			}else if(data.flag == "error"){
				alert(data.msg,0);
			}
			window.top.loaded();
		},error:function(data){
			 alert("操作失败！",0);
			 window.top.loaded();
		}
	});
};
//获取url参数
function getQueryString(url,name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = url.match(reg); 
	if (r != null) return unescape(r[2]); return null; 
} 
window.showDialogS = function(url,title){
	if(title===undefined||title==""||title==null){ title = "提示";}
	window.top.window.showDialog(url,title,50,57);
};
window.showDialogM = function(url,title){
	if(title===undefined||title==""||title==null){ title = "提示";}
	window.top.window.showDialog(url,title,70,60);
};
window.showDialogL = function(url,title){
	if(title===undefined||title==""||title==null){ title = "提示";}
	window.top.window.showDialog(url,title,90,80);
};
function formRang(){
	if($(".startRang").length>0){
		if($(".startRang").get(0).tagName=="LABEL"){
			if($(".startRang").hasClass("control-label")){
				$.each($(".startRang"),function(i,node){
					var $end = $(node).parents("td").next().next().find(".endRang");
					if($end.length>0){
						var $div = $(node).parents("td").next().find(".input-group");
						var $endInput = $end.parents("td").next().find(".input-group").children();
						$div.append('<span class="input-group-addon">至 </span>');
						$endInput.appendTo($div);
						$(node).parents("td").next().next().next().remove();
						$(node).parents("td").next().next().remove();
						$(node).parents("td").next().attr("colspan","3");
					}
				});
			}else{
				$.each($(".startRang"),function(i,node){
					var $end = $(node).parents("td").next().find(".endRang");
					if($end.length>0){
						$(node).next().addClass("form-inline");
						var groupStart = $('<div class="form-group"></div>');
						var groupEnd = $('<div class="form-group"></div>');
						groupStart.appendTo($(node).next());
						$(node).next().append('<span class="formRang">-</span>');
						groupEnd.appendTo($(node).next());
						var $startInput = $(node).next().find("input");
						var $endInput = $end.next().find("input");
						$startInput.appendTo(groupStart);
						$endInput.appendTo(groupEnd);
						$startInput.addClass("formRang");
						$endInput.addClass("formRang");
						$end.parents("td").remove();
					}
				});
			}
		}else{
			$.each($(".startRang"),function(i,node){
				var $end = $(node).next().next();
				if($end.hasClass("endRang")){
					$(node).next().addClass("form-inline");
					var $startInput = $(node).next().find("input");
					var $endInput = $end.next().find("input");
					var groupStart = $('<div class="form-group"></div>');
					var groupEnd = $('<div class="form-group"></div>');
					groupStart.appendTo($(node).next());
					$(node).next().append('<span class="formRang">-</span>');
					groupEnd.appendTo($(node).next());
					$startInput.appendTo(groupStart);
					$endInput.appendTo(groupEnd);
					$startInput.addClass("formRang");
					$endInput.addClass("formRang");
					$end.next().remove();
					$end.remove();
				}
			});
		}
	}

	$(".bootstarpTag").each(function(){
		// 计算表单最大列数。
		var maxTds = 0;
		$("tr:first", this).find("td").each(function(index, td){
			maxTds += parseInt($(td).attr("colspan")||1);
		});
		
		/* 判断某行是否多于1个字段 */
		var gtOneField = false;
		$("tr", this).each(function(indextr, tr){
			if (!gtOneField && $("td.tdlable", this).length > 1) {
				gtOneField = true;
				return false;
			}
		});
		
		if(maxTds >2 && gtOneField) {
			$(this).addClass("fourColumn");
		}
	});
	
}
