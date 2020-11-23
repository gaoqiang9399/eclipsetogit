//辅助核算项
var nameIndex=["ZY","BM","KH","ZDY"];
var assistantItem = [{"id":"1000001","name":"职员","status":"1","iscustom":"0"},
					 {"id":"1000002","name":"部门","status":"1","iscustom":"0"},
					 {"id":"1000003","name":"客户","status":"1","iscustom":"0"},
					 {"id":"100000x","name":"自定义","status":"1","iscustom":"1"}];

//初始化科目分类信息
var subData;
var subCallBackFunction;
function checkSubInfo(obj, accNo,subCallBack,width){
	subCallBackFunction=subCallBack;
	var itemCount = $('body').children("#isItem").length;
	if(itemCount == 0){
		var isItem = $('<div id="isItem" style="top: 255px; left: 251px;display: none;"></div>')
		var itemStr= $("<ul></ul>");
		for(var i=0;i<assistantItem.length;i++){
			itemStr.append("<li style='display:none;'><label>"+assistantItem[i].name+":</label><span class='ui-combo-wrap ui-combo-active' id='item"+nameIndex[i]+"'>"
					+"<input type='text' class='input-txt' items='"+assistantItem[i].id+"' itemName='"+assistantItem[i].name+"' data-value='' autocomplete='off' readonly />"
					+"<i class='glyphicon glyphicon-search combo_query'></i></span></li>");
		}
		isItem.append(itemStr);
		$('body').append(isItem);
	}
	if(subData == null){
		getSubData(obj, accNo,width);
	}else{
		showSubInfo(obj,accNo,width);
	}
}

/**
 * 获取辅助核算数据
 */
function getSubData(obj, accNo,width){
	jQuery.ajax({
		url: webPath+"/cwRelation/getRelaForVchAjax",
		data:{},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				subData = data;
				showSubInfo(obj,accNo,width);
			}else if(data.flag == "error"){
				alert(data.msg,0);
			}
		},error:function(data){
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}

//显示科目分类信息
function showSubInfo(obj, accNo,width) {
	var subObj = subData[accNo];//科目实体
    var editsCount;
	if(subObj==null){
		//就算没有辅助核算项，也会生成一个input框
		editsCount = $(obj).siblings(".edit_items").length;
		if(editsCount == 0){
			$(obj).after("<input type='hidden' class='edit_items' autocomplete='off'>");
		}
		$editItems=obj.parent().find(".edit_items");
		if($editItems.length>0){
			$editItems.val("");//清空之前的选择。
		}
		return false;
	}
	var isShowFlag = [subObj.employ, subObj.dept, subObj.custom, subObj.item];
	//初始化科目的分类信息
	$("#isItem").children("ul").children("li").css("display","none");
	$("div[id^='div']").children("div").html("");
	$("span[id^='item']").children("input").val("");
	var showCount = 0;
	for(var i=0;i<nameIndex.length;i++){
		if(isShowFlag[i]){
			$("#item"+nameIndex[i]).parent().css("display","block");
			$("#item"+nameIndex[i]).attr("showCount",showCount);
			$("#item"+nameIndex[i]).children("input").attr("data-value", '').val('');
			showCount++;
		}
	}
	
	//是否增加自定义分类
	if(subObj.itemClassName!="" && typeof(subObj.itemClassName)!="undefined"){
		$("#itemZDY").siblings("label").html(subObj.itemClassName+":");
		$("#itemZDY").children("input").attr("items", subObj.itemClassId).attr("itemName", subObj.itemClassName);
	}	
	$("#selSubject").parent().css("display", "none");

	//改变分类信息框位置
	var trTop = obj.offset().top;
	var trLeft = obj.offset().left;
	if(width==null||width==""){
		width = obj[0].offsetWidth;
	}
	var height = obj[0].offsetHeight;
	/*"left": (trLeft-9) + "px","left": 828.656 + "px",*/
	//alert(height);
	$("#isItem").css({
		"display": "block",
		"top": (trTop + height - 2+14) + "px",
		"left": (trLeft-9) + "px",
		"width": width + "px"
	});
	var subValue = obj.val();
	$("#isItem").attr("subValue", subValue).attr("subId", obj[0].name);//.attr("trIndex", trIndex)
	
	
	//设置辅助核算值
	editsCount = $(obj).siblings(".edit_items").length;
	if(editsCount == 0){
		$(obj).after("<input type='hidden' class='edit_items' autocomplete='off'>");
	}else{
		var subAll=$(obj).siblings(".edit_items").val();
		var items = subAll.split('|');
		$('#isItem').find('input').each(function(index){
			for(var i in items){
				var item = items[i].split('@');
				if(item[0]==$(this).attr('items')){
					$(this).attr('data-value', item[2]);
					$(this).val(item[3]);
				}
			}
		});
	}
	
	//科目分类信息单击事件
	$("#isItem .combo_query").off('click').on("click",function(){
		var prevInput = $(this).prev('input');
		var txType = prevInput.attr('items');
		openAssistDialog(txType, function(data){
			if(data){
				prevInput.val(data.txName);
				prevInput.attr('data-value', data.txCode);
			}
		});
	});
	
	//页面单击事件
	$("body").click(function(e){
		var target  = e.target || e.srcElement;
		if(!$("#isItem").is(":hidden")){
			var subId = $("#isItem").attr("subId");
			var editInput = $("[name="+subId+"]");
			var indexHm = editInput.val();
			var elem = false;
			if($(target).closest(editInput).length == 0 && $(target).closest($("#isItem")).length == 0){
				elem = true;
			}else{
				if(indexHm == ''){
					$("#isItem").css("display","none");
				}
			}
			var appendVal="";////往科目输入框里追加的辅助核算名称，如"1002005/中国建设银行_管理员_李四"
			if(elem){
				var flag = true;
				var subAll = "";
				$("#isItem>ul>li:visible input").each(function(){
				
					if($(this).val()==""){
						alert(top.getMessage("NOT_FORM_EMPTY", "核算项目"), 1);
						flag=false;
						return false;
					}else{
						subAll += "|" + $(this).attr('items') + "@" + $(this).attr('itemName') + "@" + $(this).attr('data-value') + "@" + $(this).val();
						appendVal+="_"+$(this).val();////
					}
				});
				
				if(flag){
					editInput.siblings(".edit_items").val(subAll.substring(1));
					$("#isItem").css("display","none");
					////往科目输入框里展示辅助核算项
					var subid=$("#isItem").attr("subid");
					var subvalue=$("#isItem").attr("subvalue");
					$("[name="+subid+"]").val(subvalue+appendVal);
					if(subCallBackFunction&&typeof(subCallBackFunction)=="function"){
						subCallBackFunction.call(this);
						subCallBackFunction="";
					}
				}
			}
		}
	});
}
//窗口放大缩小时，使插件跟着输入框
$(window).resize(function(){
	changePosition();
});
function changePosition(){
	var subid=$("#isItem").attr("subid");//输入框的name值
	var obj=$("[name="+subid+"]");
	if(subid){
		var width=obj[0].offsetWidth;
		//改变分类信息框位置
		var trTop = obj.offset().top;
		var trLeft = obj.offset().left;
		var height = obj[0].offsetHeight;
		/*"left": trLeft + "px","left": (trLeft-9) + "px","left": 828.656 + "px",*/
		$("#isItem").css({
			"top": (trTop + height - 2+14) + "px",
			"left": (trLeft-9) + "px",
			"width": width + "px"
		});
	}
}