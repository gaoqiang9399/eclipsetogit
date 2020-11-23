;
var MfSysFeeItemForPrdct = function(window, $){
	//新增页面的初始化方法
	var _init = function(){
		//自定义滚动条初始化
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//基准项
		var feeStdNo =$("input[name=feeStdNo]").val();
		var options = $("select[name=standard]").find("option");
		//makeOptionsJQ(options, '2,5,7,8');//显示合同金额、放款金额、合同本息、放款本息
		$("select[name=standard]").val("2");//默认选中合同金额
		//退费联动
		$("select[name=refundCusType]").parents("tr").hide();
		$("select[name=refundCusType]").attr("disabled",true);
		$("select[name=itemType]").bind("change",function(){
			if($(this).val()!="2"){//不是退费
				$("select[name=refundCusType]").parents("tr").hide();
				$("select[name=refundCusType]").attr("disabled",true);
			}else{//是退费
				$("select[name=refundCusType]").attr("disabled",false);
				$("select[name=refundCusType]").parents("tr").show();
				$("select[name=refundCusType]").val("");
				$("select[name=refundCusType]").attr("mustinput","1");
				$("select[name=refundCusType]").parents("tr").find("td:eq(0)").find("label").html("<font color='#FF0000'>*</font>返费对象类型");
			}
		});
		
		var nodeNo = $("input[name=nodeNo]").val();
		//费用项选择组件初始化
		$("select[name=itemNo]").popupSelection({		
			searchOn:true,//启用搜索
			ajaxUrl:webPath+"/mfKindNodeFee/getPageFeeItemForPrdctAjax?kindNo="+feeStdNo+"&nodeNo="+nodeNo,//异步获取选项的url
			inline:true,//下拉模式
			multiple:false,//多选选
		});
	};
	
	//保存配置的费用项
	var _ajaxInsert = function(obj,type){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			$.ajax({
				url:url,
				data:{ajaxData:dataParam},
				success:function(data){
					if(data.flag == "success"){
						  window.top.alert(data.msg,1);
						  top.addFlag = true;
						  top.feeData = data;
						  // if(type=="insert"){
							//   top.feeList = data.feeList;
						  // }else if(type=="update"){
							//  top.mfKindNodeFee = data.mfKindNodeFee;
						  // }
						  myclose_click();
					}else{
						  alert(data.msg,0);
					}
				},error:function(){
					alert(top.getMessage("ERROR_INSERT"),0);
				}
			}); 
		}
	};
	//详情页面的初始化方法
	var _initDetail = function(){
		//自定义滚动条初始化
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		
		//处理退费联动
		var itemType=$("select[name=itemType]").val();
		if(itemType=='2'){//是退费
			$("select[name=refundCusType]").attr("disabled",false);
			$("select[name=refundCusType]").attr("mustinput","1");
			$("select[name=refundCusType]").parents("tr").find("td:eq(0)").find("label").html("<font color='#FF0000'>*</font>返费对象类型");
		}else{//不是退费
			$("select[name=refundCusType]").parents("tr").hide();
			$("select[name=refundCusType]").attr("disabled",true);
		}
		$("[name=itemType]").bind("change",function(){
			if($(this).val()!="2"){//不是退费
				$("select[name=refundCusType]").parents("tr").hide();
				$("select[name=refundCusType]").attr("disabled",true);
			}else{//是退费
				$("select[name=refundCusType]").attr("disabled",false);
				$("select[name=refundCusType]").parents("tr").show();
				$("select[name=refundCusType]").parents("tr").find("td:eq(0)").find("label").html("<font color='#FF0000'>*</font>返费对象类型");
			}
		});
	};
	return{
		init:_init,
		initDetail:_initDetail,
		ajaxInsert:_ajaxInsert,
	};
}(window, jQuery);
	