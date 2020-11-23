	//打开还款撤销页面
	
	function mfBusUndoRecordJsp(url){
	var msg="还款撤销"
	alert(top.getMessage("CONFIRM_OPERATION",msg),2,function(){
		top.flag=false;
		top.window.openBigForm(encodeURI(url),'还款撤销信息',callBackFunction);
		
	});
	}
	function callBackFunction(){//重新加载列表数据
		isCheckAll = false;
		updateTableData();
	}

	//单笔借据复核
	function singleRecheck(obj,url){
	var repayId = url.split("?")[1].split("=")[1];
		$.ajax({
			url:webPath+"/mfRepayHistory/singleRecheckAjax",
			type:'post',
			data : {repayId:repayId},
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					alert(data.msg, 1);
					callBackFunction();//重新加载列表数据
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}				
			},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
	}
		function initAjax(obj,url){//处理还款撤销的刷新列表
			$.ajax({
				url:webPath+"/mfRepayHistory/getRepayRecheckList",
				type:'post',
				data : {"repayId":repayId},
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						$(".content").html(data.tableHtml);
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}				
				},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
	}
	
			//获取选择的编号
			function getCheckedNos(){
				var nos = '';
	            var no = '';
	            var vals=0;
	            $(".review_list").find(':checkbox').each(function() {
	            	if($(this).is(":checked")){
	            		no = $(this).val().substring(8);
	            		if(no != '' && no != null){
		            		nos = nos + "," + no;
		            		vals++;
	            		}
	            	}
	            });
	            if(vals==0){
	            	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的数据"), 1);
// 	            	alert("请选择需要操作的数据。", 1);
	            }else{
	            	nos = nos.substring(1);
	            }
	            return nos;
			}
			
			function batchPrint(){//批量复核
				var repayIds = getCheckedNos();
				if(repayIds != ''){
					var url = webPath+'/mfRepayHistory/batchPrint?repayIds='+repayIds;
					top.window.open(encodeURI(url),'批量打印还款凭证',null);
				}
			}
				function batchPrintIntst(){//批量打印利息单
					var repayIds = getCheckedNos();
					if(repayIds != ''){
						var url = webPath+'/mfRepayHistory/batchPrintIntst?repayIds='+repayIds;
						top.window.open(encodeURI(url),'批量打印利息单',null);
					}
				}
				function busRecheck(){//批量打印还款凭证
				var repayIds = getCheckedNos();
				if(repayIds != ''){
					var url = webPath+'/mfRepayHistory/busRecheckAjax';
					var dataParam = '[{"name":"repayIds","value":"'+repayIds+'"}]'; 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								alert(data.msg, 1);
								 callBackFunction();//重新加载列表数据
							}else if(data.flag == "error"){
								 alert(data.msg,0);
							}
						},error:function(data){
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}