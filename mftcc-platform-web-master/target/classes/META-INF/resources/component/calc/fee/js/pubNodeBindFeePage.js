	$(function(){
		if(typeof (feeParm) != 'undefined'){
			getNodeBusFeeList(feeParm);
		}
	});
	
	/**
	 * 获取列表
	 * @param parm
	 */
	function getNodeBusFeeList_old(parm){
		myCustomScrollbar({
			obj:"#busfee-list",//页面内容绑定的id
			url:webPath+"/mfKindNodeFee/getBusFeeListAjax?"+parm,//列表数据查询的url
			tableId:"tablebusfee0004",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{},
			topHeight: 400,
			pageSize:10,//加载默认行数(不填为系统默认行数)
			callback:function(options,data){
				if(data.feePower!='3' && data.ipage.pageCounts>0){
					$('#busfee-list tr').find('th:last').remove();
					$('#busfee-list tr').find('td:last').remove();
					$('#busfee-list tr:last').remove();
				}
				
				$('#busfee-list tbody tr').each(function(){
					var trObj = $(this);
					var optPower = trObj.find('input[name=optPower]').val();
					if(optPower!='1'){
						trObj.find('input').each(function(){
							var txt = $(this).val();
							$(this).before(txt).remove();
						});
						trObj.find('select').each(function(){
							var txt = $(this).find("option:selected").text();;
							$(this).before(txt).remove();
						});
					}
				});
				$('#busfee-list').on('blur', 'input[name=rateScale]', function(){
					var trObj = $(this).parents('tr');
					var rate = $(this).val();
					if(''!=$(this).val()){
						$(this).val(rate.replace(/,/g, ""));
						var dataParam = JSON.stringify(trObj.find('input,select').serializeArray());
						if(trObj.find('select[name=takeType]').val()=='2'){
							//格式化金额
						}
						updateBusFeeRate(trObj,dataParam);
					}
				});
				if(data.ipage.pageCounts>0){
					$('.busfeeInfo').removeClass('hidden');
				}
			}//方法执行完回调函数（取完数据做处理的时候）
		});		
   		$('.table-float-head').remove();
   		$('.footer_loader').remove();
	}
	function getNodeBusFeeList(parm){
		let tableId =  "tablebusfee0004";
		if(typeof (busModel) != "undefined" && busModel == "12"){
            tableId =  "tablebusfee_GCDB";
		}
		$.ajax({
	 		type:"post",
	 		url:webPath+"/mfKindNodeFee/getBusFeeListAjax?"+parm,
	 		data:{tableId:tableId,tableType:"thirdTableTag"},
	 		async: false,
	 		beforeSend:function(){
	 			LoadingAnimate.start();
				
			},success:function(data){
				if(data.flag=="success"){
					var feePower=data.feePower;
					var ipage=data.ipage;
					var mfKindNodeFeeList=data.mfKindNodeFeeList;
		 			$('#busfee-list').html(ipage.result);
		 			var tableHtml=$('#busfee-list table');
		 			tableHtml.find("colgroup").remove();
		 			// if(feePower!='3' && ipage.pageCounts>0){
					// 	$('#busfee-list tr').find('th:last').remove();
					// 	$('#busfee-list tr').find('td:last').remove();
					// 	$('#busfee-list tr:last').remove();
					// }
		 			$('#busfee-list tbody tr').each(function(){
						var trObj = $(this);
						var optPower = trObj.find('input[name=optPower]').val();
                        var itemNo = trObj.find('input[name=itemNo]').val();
						if(optPower!='1'){//改
							trObj.find('input').each(function(){
								var txt = "<span id = '"+ $(this).attr("name") +"Span'>" + $(this).val() + "</span>";
								$(this).before(txt).hide();
							});
							trObj.find('select').each(function(){
								var txt = $(this).find("option:selected").text();
								$(this).before(txt).remove();
							});
						}
						if(busModel == "12"){
                            trObj.find('input[name=rateScale]').bind("change",function(){
                                calcFee(this);
                            });
                            trObj.find('input[name=receivableFeeAmt]').bind("change",function(){
                                calcSumFee(this);
                            });
                        }
						//费用项操作权限
						//var optPower = trObj.find("input[name=optPower]").val();
						if(optPower=="1"){//修改权限
							//setFeeMain(trObj,mfKindNodeFeeList);
						}else if(optPower=="2" || optPower=="3"){//查看、收取权限
							trObj.find(".feeMainNo").hide();
							trObj.find(".feeMainName").hide();
							trObj.find(".feeAccountId").hide();
							trObj.find(".feeAccountNo").hide();
						}
                        if(itemNo==13||itemNo==14||itemNo==15){
                            trObj.hide();
                        }
					});
					$('#busfee-list').on('blur', 'input[name=rateScale]', function(){
						var trObj = $(this).parents('tr');
						var rate = $(this).val();
						if(''!=$(this).val()){
							$(this).val(rate.replace(/,/g, ""));
							var dataParam = JSON.stringify(trObj.find('input,select').serializeArray());
							//联动计算应收费用及总额
							var takeType = trObj.find('select[name=takeType]').val();
							if(takeType=='2'||takeType=='3'){//固定金额
								
							}else if(takeType=="1"){//百分比
								
							}
							updateBusFeeRate(trObj,dataParam);
						}
					});
					$('#busfee-list').on('blur', 'input[name=feeVoucherNo]', function(){
						var trObj = $(this).parents('tr');
						if(''!=$(this).val()){
							var dataParam = JSON.stringify(trObj.find('input,select').serializeArray());
							updateBusFeeRate(trObj,dataParam);
						}
					});
					$('#busfee-list').on('change', 'select', function(){
						var trObj = $(this).parents('tr');
						var rate = trObj.find('input[name=rateScale]');
						if(''!=rate.val()){
							rate.val(rate.val().replace(/,/g, ""));
							var dataParam = JSON.stringify(trObj.find('input,select').serializeArray());
							if(trObj.find('select[name=takeType]').val()=='2'){
								//格式化金额
							}
							updateBusFeeRate(trObj,dataParam);
						}
					});
					$('#busfee-list').on('change', 'select[name=feeMainNo]', function(){
						var trObj = $(this).parents('tr');
						var feeMainNo = trObj.find('select[name=feeMainNo]');
						//setfeeAccount(trObj,feeMainNo.val());
					});
					if(ipage.pageCounts>0){
						$('.busfeeInfo').removeClass('hidden');
					}
		 			
		 			tableHtml.show();
				}
	 		},
	 		complete : function() {
	 			if (LoadingAnimate) {
	 				LoadingAnimate.stop();
				}
	 		}
	 	});
	}
	
	//修改费率
	function updateBusFeeRate(trObj,dataParam){
		jQuery.ajax({
			url: webPath+'/mfKindNodeFee/updaterateScaleByIdAjax',
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					var mfBusAppFee = data.mfBusAppFee;
					trObj.find(".receivableFeeAmt").html(mfBusAppFee.receivableFeeAmt);
// 					 alert(top.getMessage("SUCCEED_OPERATION"),1);
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	}
	
	function setFeeMain(trObj,mfKindNodeFeeList){
		$.ajax({
	 		type:"post",
	 		url:webPath+"/mfCusAssureCompany/getAssureData",
	 		data:{},
	 		async: false,
	 		beforeSend:function(){
	 			LoadingAnimate.start();
			},success:function(data){
				if(data.flag=="success"){
					var mfCusAssureCompanyList = data.mfCusAssureCompanyList;
					$.each(mfCusAssureCompanyList,function(i,obj){
						trObj.find("select[name=feeMainNo]").append("<option value='"+obj.assureCompanyId+"'>"+obj.assureCompanyName+"</option>");
					});
					$.each(mfKindNodeFeeList,function(i,obj){
						var feeMainName = obj.feeMainName;
						if(obj.feeId==trObj.find("input[name=feeId]").val()){
							trObj.find("select[name=feeMainNo]").find("option[value='"+obj.feeMainNo+"']").attr("selected",true);
							setfeeAccount(trObj,obj.feeMainNo);
							trObj.find("select[name=feeAccountId]").find("option[value='"+obj.feeAccountId+"']").attr("selected",true);
						}
					});
				}
	 		},
	 		complete : function() {
	 			if (LoadingAnimate) {
	 				LoadingAnimate.stop();
				}
	 		}
	 	});
	}
	
	function setfeeAccount(trObj,feeMainNo){
		$.ajax({
	 		type:"post",
	 		url:webPath+"/mfCusBankAccManage/getBankAccData",
	 		data:{cusNo:feeMainNo},//,useType:"7"
	 		async: false,
	 		beforeSend:function(){
	 			LoadingAnimate.start();
			},success:function(data){
				if(data.flag=="success"){
					var mfCusBankAccManageList = data.mfCusBankAccManageList;
					trObj.find("select[name=feeAccountId]").empty();
					$.each(mfCusBankAccManageList,function(i,obj){
						trObj.find("select[name=feeAccountId]").append("<option value='"+obj.id+"'>"+obj.accountNo+"</option>");
					});
				}
	 		},
	 		complete : function() {
	 			if (LoadingAnimate) {
	 				LoadingAnimate.stop();
				}
	 		}
	 	});
	}