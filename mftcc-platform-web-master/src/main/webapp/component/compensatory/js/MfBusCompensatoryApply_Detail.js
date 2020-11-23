;
var MfBusCompensatoryApplyDetail = function(window,$){
	var _init = function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		/*$('#mfBusCompensatoryApplyList tbody tr').each(function(){
			var trObj = $(this);
			trObj.find('input[name=compensatoryFee]').blur(function(){
				var compensatoryFee = trObj.find('input[name=compensatoryFee]').val();
				var compensatoryPrcp = trObj.find('.compensatoryPrcp').html().replace(/,/g, "");
				var compensatoryIntst = trObj.find('.compensatoryIntst').html().replace(/,/g, "");
				var compensatoryPenalty = trObj.find('.compensatoryPenalty').html().replace(/,/g, "");
				var compensatoryOverIntst = trObj.find('.compensatoryOverIntst').html().replace(/,/g, "");
				var sum = Number(compensatoryFee)+ Number(compensatoryPrcp)+ Number(compensatoryIntst)
							+ Number(compensatoryPenalty) + Number(compensatoryOverIntst);
				trObj.find('.compensatoryFeeSum').html(sum.toFixed(2));
				sum = 0.00;
				var compensatoryFeeSum = 0.00;
				$('#mfBusCompensatoryApplyList tbody tr').each(function(){
					var trObjTmp = $(this);
					sum = sum + Number(trObjTmp.find('.compensatoryFeeSum').html().replace(/,/g, ""));
					compensatoryFeeSum += Number(trObjTmp.find('input[name=compensatoryFee]').val().replace(/,/g, ""));
				});
				$('#mfBusCompensatoryApplyForm input[name=compensatoryFee]').val(compensatoryFeeSum);
				$('#mfBusCompensatoryApplyForm input[name=compensatoryFeeSum]').val(sum.toFixed(2));
			});

		});*/
	};
	
	//更新操作
	var _ajaxInsert = function(obj){
		var datas = [];
		$.each($("#mfBusCompensatoryApplyList").find("tbody tr"),function(index) {
			var entity = {};
			$thisTr = $(this);
			entity.termNum = $thisTr.find("input[name=termNum]").val();
			entity.planId = $thisTr.find("input[name=planId]").val();
			entity.fincId = $thisTr.find("input[name=fincId]").val();
			entity.compensatoryPrcp = $thisTr.find(".compensatoryPrcp").html().replace(/,/g, "");
			entity.compensatoryIntst = $thisTr.find(".compensatoryIntst").html().replace(/,/g, "");
			entity.compensatoryOverIntst = $thisTr.find(".compensatoryOverIntst").html().replace(/,/g, "");
			entity.compensatoryPenalty = $thisTr.find(".compensatoryPenalty").html().replace(/,/g, "");
			entity.compensatoryFee =  $thisTr.find("input[name=compensatoryFee]").val();
			entity.compensatoryFeeSum = $thisTr.find(".compensatoryFeeSum").html().replace(/,/g, "");
			datas.push(entity);

		});
		// if(datas == false){
		// 	alert("请选择代偿期号",1);
		// 	return;
		// }
		var dataParam = JSON.stringify($(obj).serializeArray());
		$.ajax({
			url:webPath+"/mfBusCompensatoryApply/insertAjax",
			data:{
				ajaxData : dataParam,
				ajaxDataList : JSON.stringify(datas)
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg,3);
					myclose_click();
				}else{
					alert(data.msg,0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_INSERT"),0);
			}
		});
	};

	var _getSts = function(){
        var sts;
        var obj;
        var fincId;
        var compensatoryId;
        //循环table
        $("#tablist").find("tbody").find("tr").each(function (i) {
            obj = $(this);
            fincId = obj.find("td").find("input[name=fincId]").val();
            sts = obj.find("td").find("input[name=ext5]").val();//获取代偿状态
            compensatoryId =  obj.find("td").find("input[name=ext4]").val();

            if(sts=="3"){//代偿审批完成,代偿链接换为代偿确认
                obj.find("td").eq(6).text("");
                obj.find("td").eq(5).text("");
                obj.find("td").eq(6).text("代偿待确认");
				// obj.find("td").eq(6).append("<a href=\"javascript:void(0);\" onclick=\'MfBusCompensatoryApplyDetail.getCompensatoryConfirm("+"\""+fincId+"\","+"\""+compensatoryId+"\")\';return false;\" class=\"abatch\">代偿待确认</a>");
            }else if(sts=="1" || sts=="2"){
                obj.find("td").eq(6).text("代偿中");
            }else if(sts=="7" || sts=="5"){//已经代偿完毕
                obj.find("td").eq(5).text("");
                obj.find("td").eq(6).text("追偿待登记");
               // obj.find("td").eq(6).append("<a href=\"javascript:void(0);\" onclick=\'MfBusCompensatoryApplyDetail.getRecourseConfirm("+"\""+fincId+"\","+"\""+compensatoryId+"\")\';return false;\" class=\"abatch\">追偿登记</a>");
            }else if(sts=="6"){//不允许再追偿
                obj.find("td").eq(6).text("追偿登记");
                obj.find("td").eq(5).text("");
            }else{
                obj.find("td").eq(6).text("");
                obj.find("td").eq(5).text("");
                obj.find("td").eq(6).append("<a href=\"javascript:void(0);\" onclick=\'MfBusCompensatoryApplyDetail.getCompensatoryApply("+"\""+fincId+"\")\';return false;\" class=\"abatch\">代偿申请</a>");
            }
        });
	};

	var _getCompensatoryApply = function(fincId){
        top.flag=false;
        top.window.openBigForm(webPath+"/mfBusCompensatoryApply/input?fincId="+fincId,'代偿申请',function(){
           // MfBusCompensatory.getCompensatoryType();
            updateTableData();
        });
	}

    var _getCompensatoryConfirm = function(fincId,compensatoryId){
        top.flag=false;
        top.window.openBigForm(webPath+"/mfBusCompensatoryConfirm/input?fincId="+fincId+"&compensatoryId="+compensatoryId,'代偿确认',function(){
           // MfBusCompensatory.getCompensatoryConfirmBack();
            updateTableData();
        });
    };
    var _getRecourseConfirm = function(fincId,compensatoryId){
        top.flag=false;
        top.window.openBigForm(webPath+"/mfBusRecourseApply/inputRec?fincId="+fincId+"&compensatoryId="+compensatoryId,'追偿登记',function(){
            // MfBusCompensatory.getCompensatoryConfirmBack();
            MfBusRecourseApplyDetail.getRecourseStatus();
        });
    }
	return{
		init:_init,
		ajaxInsert:_ajaxInsert,
        getSts : _getSts,
        getCompensatoryApply : _getCompensatoryApply,
        getCompensatoryConfirm : _getCompensatoryConfirm,
        getRecourseConfirm : _getRecourseConfirm
	};
}(window,jQuery);