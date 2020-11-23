;
var assureCusInfo = function(window, $) {
	
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
		});
		$("select[name=cusType]").val("202");
	};
	//新增
	var _chooseCusType = function(){
		var val = $("select[name=cusType]").val().substring(0,1);	//表单客户类型
		var type = $("select[name=cusType]").val();
		if($("select[name=cusType]").val() == '109'){
			$("#type").val("1");
			$("#cusAndAppInsertForm").empty().html(htmlZJ);
		}else{
			if(val == "2"){//企业客户变成个人客户
				$("#type").val("2");
				$("#cusAndAppInsertForm").empty().html(htmlStrper);
			}else{//个人客户变成企业客户
				$("#type").val("1");
				$("#cusAndAppInsertForm").empty().html(htmlStrCorp);
//			formRang();
			}	
		}
		$("select[name=cusType]").val(type);
	};
	var _addProOption = function(jsonObj){
		$.ajax({
			type:"post",
			url:webPath+"/mfSysKind/getAllKindListAjax",
			success:function(data){
				var kindList = data.kindList;
				var len = data.len;
				for(var i=0;i<len;i++){
					var kindNo = kindList[i].kindNo;
					var kindName = kindList[i].kindName;
					var busModel = kindList[i].busModel;
					if('15'==busModel){
						$("select[name=kindNo]").append("<option selected='selected' name='"+busModel+"'"+"value='"+kindNo+"'>"+kindName+"</option>");
					}else
						$("select[name=kindNo]").append("<option name='"+busModel+"'"+"value='"+kindNo+"'>"+kindName+"</option>");
						
				}
			}
		});
	};
	var _getCusNo = function(){
		var cusType = $("select[name=cusType]").val();
		$.ajax({
			type:"post",
			url:webPath+"/mfCusCustomer/getFormCusNo?cusType="+cusType,
			async:false,
			success:function(data){
				if(data.flag == 'success'){
					cusNo = data.cusNo;
					docParm = "cusNo="+cusNo+"&relNo="+cusNo+"&appId="+appId;
				}
			},error:function(){
				alert(top.getMessage("FAILED_SAVE"),0);
			}
		});
	};
	var _insertAppAndCus = function(obj){
		var url = $(obj).attr("action");
		var flag= submitJsMethod($(obj).get(0), '');
			if(flag){
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				LoadingAnimate.start();
				//资金机构单独处理
//				if($("select[name=cusType]").val() == '109'){
//					url = webPath+"/mfCusCustomer/insertForBusAjax";
//					$.ajax({
//						url:url,
//						data:{ajaxData:dataParam, cusType : $("select[name=cusType]").find("option:selected").val()},
//						type:'post',
//						dataType:'json',
//						success:function(data){
//							LoadingAnimate.stop();
//							if(data.flag == "success"){
//								  var cusNo = data.cusNo;
//								  var cusType=data.cysType;
//								  var url=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId=&cusType="+cusType;
//								  $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
//								  myclose();
//							}else{
//								LoadingAnimate.stop();
//								window.top.alert(data.msg,1);
//							}
//						},error:function(){
//							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
//						}
//					}); 
//				}else{
					$.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:'post',
						dataType:'json',
						success:function(data){
							LoadingAnimate.stop();
							if(data.flag=="success"){
								var url = webPath+'/mfBusApply/getSummary?appId='+data.appId;
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								$(top.window.document).find("#showDialog").remove();
							}else{
								alert(top.getMessage("FAILED_SAVE"),0);
							}
						},error:function(){
							LoadingAnimate.stop();
							alert(top.getMessage("FAILED_SAVE"),0);
						}
					});
//				}
			}
	};
	var _cancleBack = function(){
		window.location.href=webPath+"/mfBusApply/getListPage";
	};
	var _checkByKindInfo = function(obj){
		var name = $(obj).attr("name");
		var title = $(obj).attr("title").split("(")[0];
		//申请金额
		if(name=="appAmt"){
			if(maxAmt!=null && minAmt!=null && maxAmt!="" && minAmt!=""){
				maxAmtNum = new Number(maxAmt);
				minAmtNum = new Number(minAmt); 
				var appAmt = $(obj).val();
				appAmt = appAmt.replace(/,/g, "");
				if(parseFloat(appAmt)<parseFloat(minAmtNum)||parseFloat(appAmt)>parseFloat(maxAmtNum)){
					$(obj).val(0);
					alert(title+"必须在"+new Number(minAmt)+"到"+new Number(maxAmt)+"之间",0);
				}
			}
		}
	};
	
	var _checkRate = function (obj){
		var name = $(obj).attr("name");
		var val = $("input[name='"+name+"']").val();
		if(val<0){
			alert("利率必须大于0！",3);
		}
	};
	return {
		init : _init,
		chooseCusType :_chooseCusType,
		addProOption :_addProOption,
		insertAppAndCus :_insertAppAndCus,
		cancleBack :_cancleBack,
		checkByKindInfo :_checkByKindInfo,
		getCusNo :_getCusNo,
		checkRate :_checkRate,
	};
}(window, jQuery);
