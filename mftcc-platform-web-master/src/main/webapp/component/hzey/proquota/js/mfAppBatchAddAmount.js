var mfAppBatchAddAmount = {	
		toBatchAddAmt:function(){
			//获取选择的编号
			var pactNoList = '';
            var pactNo = '';
            var vals=0;
            $(".review_list").find(':checkbox').each(function() {
            	if($(this).is(":checked")){
            		pactNo = $(this).val();
            		if(pactNo != '' && pactNo != null){
            			pactNoList = pactNoList + "," + pactNo;
	            		vals++;
            		}
            	}
            });
            if(vals==0){
            	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的数据"), 1);
            }else{
            	pactNoList = pactNoList.substring(1);
            	console.log(pactNoList);
            	top.openBigForm(webPath+"/mfBusAddAmtRecord/inputAmt?pactNoList="+pactNoList,"请输入提升金额",function(){
            		window.updateTableData();
            	},'50','40'); 
            }
			
		},

		//保存提升金额
		saveAddAmt: function(){
			var addAmt = $("#addAmt").val();
			$.ajax({
				url : webPath+"/mfBusAddAmtRecord/insertBatchAjax",
				type : "post",
				data : {addAmt:addAmt,pactNoList:pactNoList}, 
				dataType : "json",
				success : function(data) {
					if (data.flag == "success") {// 存在业务拒绝
						LoadingAnimate.stop();
						alert(data.msg,3,function(){//生成还款计划成功之后要提示一下，再执行原来的方法
							top.flag=true;
							top.putoutReviewFlag=true;
							top.tableHtml=data.tableHtml;
							myclose_click();
							window.updateTableData();
						});
					} else {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_OPERATION",data.msg),0);
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});

	 	},
		
}