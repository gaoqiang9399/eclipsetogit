var mfCusCreditContractDetail = function(window,$){
	var _init = function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
//			}
//		});
		if($("input[name=creditSum]").val()==null || $("input[name=creditSum]").val() =='' || $("input[name=creditSum]").val()==0.00 || $("input[name=creditSum]").val()==undefined){
			$("#amount").show();
		}$("#amount").show();
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
	/*	$("input[name=beginDate]").removeAttr("onclick");
		$("input[name=endDate]").removeAttr("onclick");*/
		
		setTimeout(function(){
			$('.footer_loader').remove();
		},200);
		
		//计算到期日
		var creditTermVal =  Number($("input[name=creditTerm]").val());
		var beginDateVal = $("input[name=beginDate]").val();
		/*var endDateVal = "";
		if(creditTermVal != "" && beginDateVal != ""){
			endDateVal = creditHandleUtil.getAddMonthRes(beginDateVal,creditTermVal,"m");
		}
		$("input[name=endDate]").val(endDateVal);*/
		
		//产品信息
		if(creditType=="2"){
			MfCusCreditAdjustApplyInsert.getKindAmtAdjInfo(creditAppIdPro,adjAppIdPro);
		}else{
			for(var i=0; i<mfCusPorductCreditList.length; i++){
				index++;
				var optionStr = "";
				var labelName="产品";
				var inputName="kindNo";
				if(cusType=="101"){
					inputName="companyName";
					labelName="链属企业";
					optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+mfCusPorductCreditList[i].kindName+'"><input type="hidden" value="" name="companyName_'+index+'" readonly="readonly">';
				}else{
					for(var j=0;j<mfSysKinds.length;j++){
						if(mfCusPorductCreditList[i].kindNo == mfSysKinds[j].kindNo){
							optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+mfCusPorductCreditList[i].kindName+'"><input type="hidden" value="'+mfCusPorductCreditList[i].kindNo+'" name="kindNo_'+index+'" readonly="readonly">';
						}
					}
				}
				var porductInfo = '<tr><td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label ">'+labelName+'</label>'+
				'</td>'+
				'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
				'<div class="input-group">';
				optionStr +='</div>'+
				'</td>'+
				'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label ">授信额度</label>'+
				'</td>'+
				'<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
				'<div class="input-group">'+
				'<input value="'+creditHandleUtil.numFormat(mfCusPorductCreditList[i].creditAmt)+'"  type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valFormat_tips(this, "nonnegative");func_uior_valTypeImm(this);resetTimes();" onchange="mfCusCreditContractDetail.creditAmtCount(this);mfCusCreditContractDetail.checkCreditAmt(this)"; class="form-control" mustinput="" datatype="12" id="kindNo_'+index+'" name="creditAmt_'+index+'" title="授信额度" maxlength = "14">'+
				'<span class="input-group-addon">元</span></div>'+
				'</td>'+
				'</tr>';
				$("input[name=creditAmt]").parents("tr").after(porductInfo+optionStr);
				//金额格式化
				creditHandleUtil.moneyFormatting("creditAmt_"+index);
			}
			$("input[name=creditAmt]").parents("tr").remove();
		 }

		//处理其他合同类型-开始
        var $list = $(".input-group input[name='otherPact']");
        var arr = [[],[],[],[]];
        var content = '';
        $list.each(function(){
        	var value = $(this).val();
            var listHtml ="";
			if (value.startsWith("1")){
				listHtml = $(this).prop('outerHTML') + $(this).attr('datavalue');
                arr[0].push(listHtml)
			}
            if (value.startsWith("2")){
                listHtml = $(this).prop('outerHTML') + $(this).attr('datavalue');
                arr[1].push(listHtml)
            }
            if (value.startsWith("3")){
                listHtml = $(this).prop('outerHTML') + $(this).attr('datavalue');
                arr[2].push(listHtml)
            }
        });
        content +="<div class='form-tips'>除系统生成合同以外，可以添加其他合同，如下：</div><br>";
        arr.forEach(function (item,index) {
            if(index == 0){
                content+="<label style='font-size: larger;font-weight: bold'>授信：</label>";
			}
            if(index == 1){
                content+="<label style='font-size: larger;font-weight: bold'>单笔：</label>";
            }
            if(index == 2){
                content+="<label style='font-size: larger;font-weight: bold'>其他：</label>";
            }
            item.forEach(function (item2) {
                content += item2
            });
            content += '</br></br>'
        });
        $(".input-group input[name='otherPact']").parent().html(content);
        //处理其他合同类型-结束
	};
	
	
	/**
	 * 动态的条件产品信息
	 */
	var _addOneProductTypeLineNew = function(obj){
		n++;
		index++;
		var tmp = "add_"+index;
		var porductInfo3 = "",porductInfo4 = "";
		var labelName ="产品";
		porductInfo4 ='<select onkeydown="enterKey();" onmousedown="enterKey()" onchange="creditHandleUtil.checkKindNo(this);" onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="产品" name="kindNo_'+index+'">'+
			'<option selected="" value=""></option>';
			var optionStr = "";
			for(var i=0; i<mfSysKinds.length; i++){
				optionStr +='<option value="'+mfSysKinds[i].kindNo+'">'+mfSysKinds[i].kindName+'</option>';
			 }
			 porductInfo4 = porductInfo4+optionStr+'</select>';
			 porductInfo3 = '<tr name='+tmp+' class="addPro">'+
			 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label"><font color="#FF0000">*</font>'+labelName+'</label>'+
				'<div style="margin-left:-20px;margin-top:-33px;position:absolute;display:block;" id="delButtonNewHope">'+
					
				    '<button style="color:red;margin-top: 10px; margin-left: -17px;" class="btn btn-link list-add" onclick=mfCusCreditContractDetail.delOneProductTypeLineForProNew(this,"'+tmp+'","'+index+'"); title="删除">'+
						'<i class="i i-x5"></i>'+
					'</button>'+
				
				 '</div>'+
			 '</td>'+
			 '<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
				'<div class="input-group">'+porductInfo4+'</div>'+
			 '</td>'+
			 
			 
			 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label "><font color="#FF0000">*</font>存栏量</label>'+
			 '</td>'+
			 '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
				'<div class="input-group"><input id="kindNo_'+index+'" type="text" title="存栏量" name="amountLand_'+index+'" datatype="1" mustinput="1" class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onchange="MfCusCreditApply_input.limitAmtCount(this)" onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div>'+
			 '</td>' +
			 '</tr>'+
			 
			 '<tr id = "newHope_'+index+'">' +
			 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label "><font color="#FF0000">*</font>授信额度</label>'+
			 '</td>'+
			 '<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
				'<div class="input-group"><input  id="kindNo_'+index+'"; type="text" onkeydown="enterKey();"  onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);"  onchange="mfCusCreditContractDetail.creditAmtCount(this);MfCusCreditApply_input.checkCreditAmt(this)";  onblur="func_uior_valFormat_tips(this, \'nonnegative\');func_uior_valTypeImm(this);resetTimes();MfCusCreditApply_input.checkKindCreditSumForPro(this)" class="form-control" mustinput="1" datatype="12" name="creditAmt_'+index+'" title="授信额度" maxlength="14"><span class="input-group-addon">元</span></div>'+
			 '</td>'+
			  /*'<td ></td><td ></td>'+*/
			 
			 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label "><font color="#FF0000">*</font>产品综合费率</label>'+
			 '</td>'+
			 '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
				'<div class="input-group"><input type="text" title="产品综合费率" name="monthTotalRate_'+index+'" datatype="17" mustinput="1"  class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onchange="mfCusCreditContractDetail.checkMonthTotalRate(this)";  onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();"><span class="input-group-addon">‰</span></div>'+
			 '</td>' +
			 
			 '<td style="width:32%;" rowspan="1" colspan="3" class="tdvalue  half right">'+
			   '<div class="input-group"><input type="hidden" title="测算额度" name="hidcreditAmt_'+index+'" datatype="12" mustinput="" class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" ></div>'+
			 '</td>' +
			 
			 
			 '<td style="width:32%;" rowspan="1" colspan="4" class="tdvalue  half right">'+
			   '<div class="input-group"><input type="hidden" title="授信产品综合费率" name="hidmonthTotalRate_'+index+'" datatype="17" mustinput="" class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" ></div>'+
			 '</td>' +
			 
			 '</tr>';
			
			 var len=$("input[name=creditAmt]").parents("form").find(".addPro").length;
			 var indexNew  ; 
			 
			    for(indexNew = n; indexNew >=1; indexNew -- ){
			    	if($("input[name=creditAmt_"+indexNew+"]").length>0){
			    		break;
			    	}
			    }
			 if(len==0){
				 $("input[name=creditAmt_"+indexNew+"]").parents("tr").after(porductInfo3 + porductInfo4);
			 }else{
				 $("input[name=creditAmt_"+indexNew+"]").parents("form").find(".addPro").eq(len-1).next("tr").after(porductInfo3 + porductInfo4);
			 }
			
			 if(cusType != "101"){
				_setKindPopupSelection("kindNo_"+index);
			 }
	 };
	
	
	    //动态删除一行产品
		var _delOneProductTypeLineForProNew = function(obj,divName,a){
			//index--;
			/*if(n == a){
				n--;
				m++;
			}*/
			
			a =  "newHope_" + a;
			var  id =  $(obj).parents("div").attr("id");
			
			   $("tr[name="+divName+"]").remove();
			   $("tr[id="+a+"]").remove();
				_checkKindCreditSumForPro();
			
			return false;
		};
		
		//客户可以单独新增授信产品时才会配置此方法
		var _checkKindCreditSumForPro=function(obj){
			var productAmtSum = 0;
			if(index != 0){
				for(var i = 1;i<=index;i++){
					if($("[name=kindNo_"+i+"]").length>0){//对象存在
						productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
					}
				}
			}
			$("input[name=creditSum]").val(fmoney(productAmtSum, 2));
		}
		
		
	
	//追加产品的时候做授信产品额度控制
	var  _checkCreditAmt  = function(obj){
		var limitCreditAmt = $(obj).val().replace(/,/g, "");
		var limitCreditAmtName = $(obj).attr("name");
		var limitCreditAmtNameNew = "hid" + limitCreditAmtName;
		var limitCreditAmtNew = $("input[name=" + limitCreditAmtNameNew + "]")
				.val().replace(/,/g, "");

		if (CalcUtil.subtract(limitCreditAmt, limitCreditAmtNew) > 0) {
			window.top.alert(top.getMessage("NOT_FORM_TIME", {
				"timeOne" : "修改的授信额度:",
				"timeTwo" : "测算出的授信额度:"
						+ CalcUtil.formatMoney(limitCreditAmtNew, 2)
			}), 0);
			$("input[name=" + limitCreditAmtName + "]").val("");
		} else {
	   var name = $(obj).attr("id");
	   var kindNo = $("[name="+name+"]").val();
	   $.ajax({
			url : webPath+"/mfSysKind/getByIdAjaxNew",
			data : {kindNo:kindNo},
			type : "post",
			dataType : "json",
			success : function(data) {
			var   creditAmt =   $(obj).val().replace(/,/g,"");
			if(data.mfSysKind.maxAmt < creditAmt){
			window.top.alert("授信产品额度不可超过产品额度设置上限", 0);
			var  creditAmtName  =   $(obj).attr("name");
			$("input[name="+creditAmtName+"]").val("");	
			}
			
			}
	        
	     })
		}
	 };
	//求出授信总额
     var     _creditAmtCount  = function(obj){
         var productAmtSum = 0;
    	 var $form=$(obj).parents("form");
    	 if(index != 0){
		    for(var i = 1;i<=index;i++){
		    	if($form.find("[name=kindNo_"+i+"]").length>0){//对象存在
					productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
				}
			}
			}
    	 $("input[name=creditSum]").val(_formatMoney(productAmtSum, 2));
     }
	
      var    _formatMoney =   function (s, type) {
    	     if (/[^0-9\.]/.test(s)){
    	        return "0.00";
    	     }
    	     if (s == null || s == "null" || s == ""){
    	    	 return "0.00";
    	     }
    	      s = s.toString().replace(/^(\d*)$/, "$1.");
    	      s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
    	      s = s.replace(".", ",");
    	      var re = /(\d)(\d{3},)/;
    	      while (re.test(s))
    	      s = s.replace(re, "$1,$2");
    	      s = s.replace(/,(\d\d)$/, ".$1");
    	     if (type == 0) {
    	          var a = s.split(".");
    	         if (a[1] == "00") {
    	             s = a[0];
    	          }
    	      }
    	  return s;
   }

    //暂存
    var _updateForm = function(formObj, temporaryStorage){
        var flag = submitJsMethod($(formObj).get(0), '');
        //if (flag) {
            var url =  "/mfCusCreditContract/updateFormAjax";
            $("input[name=kindName]").val($("select[name=kindNo]").text());
            var dataForm = JSON.stringify($(formObj).serializeArray());
            //把所有的授信产品存入到集合里面
            var kindNos = [];
            var creditAmts = [];
            var amountLands = [];
            var monthTotalRates = [];
            if(index != 0){
                for(var i = 1;i<=index;i++){
                    if($(formObj).find("[name=kindNo_"+i+"]").length>0){   //对象存在
                        kindNo = $(formObj).find("[name=kindNo_"+i+"]").val();
                        if(kindNo==""||kindNo==null){
                            continue;
                        }
                        kindNos.push(kindNo);
                        creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
                        amountLands.push($("input[name=amountLand_"+i).val());
                        monthTotalRates.push($("input[name=monthTotalRate_"+i+"]").val());
                    }
                }
            }
            LoadingAnimate.start();
            $.ajax({
                url : url,
                data: {
                    ajaxData: dataForm,
                    kindNos: JSON.stringify(kindNos),
                    creditAmts: JSON.stringify(creditAmts),
                    amountLands: JSON.stringify(amountLands),
                    monthTotalRates: JSON.stringify(monthTotalRates),
                    wkfAppId: wkfAppId,
                    commitType: "PROTOCOL",
                    'temporaryStorage': temporaryStorage
                },
                type : "post",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        window.top.alert(data.msg,1);
                        window.location.reload();
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    loadingAnimate.stop();
                    alert(data.msg, 0);
                }
            });
        //}
    };

	//更新操作
	var _ajaxUpdate = function(formObj, temporaryStorage){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			$("input[name=kindName]").val($("select[name=kindNo]").text());
			var dataForm = JSON.stringify($(formObj).serializeArray());
			//把所有的授信产品存入到集合里面
			var kindNos = [];
			var creditAmts = [];
			var amountLands = [];
			var monthTotalRates = [];
			if(index != 0){
				for(var i = 1;i<=index;i++){
					if($(formObj).find("[name=kindNo_"+i+"]").length>0){   //对象存在
						kindNo = $(formObj).find("[name=kindNo_"+i+"]").val();
						if(kindNo==""||kindNo==null){
							continue;
						}
						kindNos.push(kindNo);
						creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
						amountLands.push($("input[name=amountLand_"+i).val());
						monthTotalRates.push($("input[name=monthTotalRate_"+i+"]").val());
					}
				}
			}
			
			LoadingAnimate.start();
			$.ajax({
				url : url,
                data: {
                    ajaxData: dataForm,
                    kindNos: JSON.stringify(kindNos),
                    creditAmts: JSON.stringify(creditAmts),
                    amountLands: JSON.stringify(amountLands),
                    monthTotalRates: JSON.stringify(monthTotalRates),
                    wkfAppId: wkfAppId,
                    commitType: "PROTOCOL",
                    'temporaryStorage': temporaryStorage
                },
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						myclose_click();
						//window.top.alert(data.msg, 1);
						//提交到下一个节点
//						$.ajax({
//							url: webPath+"/mfCusCreditApply/doCommitWkf",
//							type:"post",
//							dataType:"json",
//							data:{
//								wkfAppId:wkfAppId,
//								commitType:"PROTOCOL",
//							},
//							error:function(){
//								alert('提交到下一个节点时发生异常', 0);
//							},
//							success:function(data){
//								top.creditFlag=true;
//								myclose_click();
//							}
//						});
					} else {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					loadingAnimate.stop();
					alert(data.msg, 0);
				}
			});
		}
	};
	
	//调用PageOffice
	var _openDocTemplate = function(){
		$.ajax({
				url: webPath+"/mfCusCreditApply/getIfSaveModleInfo",
				type:"post",
				dataType:"json",
				data:{
					relId:pactId,
					creditAppId:creditAppId,
					tempType:"PROTOCOL"
				},
				error:function(){
					alert('error');
				},
				success:function(data){
					var type = "add";
					var filepath = "";
					if(data.flag != "0"){
						filepath = "/component/model/docword/";
					}
					var modelId = data.modelid;
					var filename = data.filename;
					var templateNo=data.templateNo;
					//var traceNo = "";
					var returnUrl = window.location.href + "&modelNo="+modelNo;
					if(modelId!=""&&modelId!=null){
						modelNo=modelId;
					}
					var urlParm=returnUrl.split("?")[1];
					returnUrl=returnUrl.split("?")[0];
					urlParm = encodeURIComponent(urlParm);
					window.location.href = basePath+"component/model/toPageOffice.jsp?relNo="+pactId+"&cifno="+cusNo+"&filename="+filename+"&templateNo="+templateNo+"&pactno=&appno=&loanNo=&traceNo=&returnUrl="+returnUrl+"&type="+type+"&filepath="+filepath+"&modelNo="+modelNo+"&urlParm="+urlParm;
				}
			});
	};
	
	//关闭
	var _close = function(){
		myclose();
	};
	//添加合同明细
	var _addPactDetail = function(){
		top.pactDetailFlag = false;
		top.openBigForm(webPath+"/mfCusCreditContractDetail/input?creditAppId="+creditAppId, "添加合同明细", function(){
			if(top.pactDetailFlag){
				$.ajax({
					url: webPath+"/mfCusCreditContractDetail/getPactDetailListHtmlAjax",
					type:"post",
					dataType:"json",
					data:{
						creditAppId:creditAppId
					},
					error:function(){
						alert('error');
					},
					success:function(data){
						if(data.flag=="success"){
							$("#contractDetailList").html(data.htmlStr);
						}
					}
				});
			}
		});
	};
	//编辑合同信息
	var _editPactDetail = function(obj,url){
		top.pactDetailFlag = false;
		top.openBigForm(webPath+url, "编辑合同明细", function(){
			if(top.pactDetailFlag){
				$.ajax({
					url: webPath+"/mfCusCreditContractDetail/getPactDetailListHtmlAjax",
					type:"post",
					dataType:"json",
					data:{
						creditAppId:creditAppId
					},
					error:function(){
						alert('error');
					},
					success:function(data){
						if(data.flag=="success"){
							$("#contractDetailList").html(data.htmlStr);
						}
					}
				});
			}
		});
	};
	//添加资金机构合同
	var _addChildPact = function(){
		top.childPactFlag = false;
		top.htmlStr = "";
		top.openBigForm(webPath+"/mfCusCreditChildContract/addChildPact?creditAppId="+creditAppId, "添加合同明细", function(){
			if(top.childPactFlag){
				$("#creditChildPactList").html(top.htmlStr);
			}
		});
	};
	
	var _checkinfo = function(obj){
		$.ajax({
			type:'get',
			dataType : 'json',
			url:webPath+"/mfCusCreditApply/getcreditSumAjax?creditAppId="+creditAppId+"&cusNo="+cusNo,
			success:function(data){	
			//	LoadingAnimate.stop();
				if(data.msgFlag== "1"){
					$("input[name=creditSum]").val(data.creditSum);
				}else{
					alert(data.msg,0);
				}	
			}
		});
	};
	
	var _creditPactTermOnBlur = function(obj){
		var creditTermVal =  Number($("input[name=creditTerm]").val());
		var beginDateVal = obj.value;
		var endDateVal = "";
		if(creditTermVal != "" && beginDateVal != ""){
			endDateVal = creditHandleUtil.getAddMonthRes(beginDateVal,creditTermVal,"m");
		}
		$("input[name=endDate]").val(endDateVal);
	};
	
	//产品使用选择组件
	var _setKindPopupSelection = function(name) {
		var kinds = new Array();
		for (var i = 0; i < mfSysKinds.length; i++) {
			kinds.push({"id" : mfSysKinds[i].kindNo, "name" : mfSysKinds[i].kindName});
		}
		$("select[name=" + name + "]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			itemsCount : 8,
			multiple : false,//单选
			items: kinds,
			changeCallback : function(obj,elem) {
		        var  id =  obj.data("values").attr("name");
			    if(id.indexOf("kindNo_") != -1){
					$("input[id="+id+"]").val("");
				}else{
					$("input[name=creditAmt]").val("");
					$("input[name=amountLand]").val("");

				}
				
			}
		});
	};
	
	// 获取产品信息
	var _getMysKind = function(thisKindNo){
		var mfSysKind = null;
		$.ajax({
			url : webPath+"/mfSysKind/getByIdAjaxNew",
			data : {kindNo:thisKindNo},
			type : "post",
			async: false,
			dataType : "json",
			success : function(data) {
				mfSysKind = data.mfSysKind;
			}
		 })
		 return mfSysKind;
	}
	
    //产品是在申请处已经选择好的产品(重新选择产品找到新产品的综合费率，存栏量赋值为空)
    var  	 _turnKindNo  =  function  (obj){
	    var  id =	$(obj).attr("name");
	    if(id.indexOf("kindNo_") != -1){
		    //存栏量赋值为空
	    	$("input[id="+id+"]").val("");
			var thisKindNo = $(obj).val(); 
			var mfSysKind = _getMysKind(thisKindNo);
			var thisIdIndex = id.substr(7);
			$("input[name=monthTotalRate_"+thisIdIndex+"]").val(CalcUtil.formatMoney(mfSysKind.monthTotalRate,2));	 
		}
	 }
	
     //核对修改的产品不可小于原产品的综合费率
     var    _checkMonthTotalRate  = function(obj){
		  var   monthTotalRate  =   $(obj).val();
		  var   monthTotalRateName  =   $(obj).attr("name");
		  var   monthTotalRateNameNew = "hid" + monthTotalRateName;
		  var   monthTotalRateNew = $("input[name="+monthTotalRateNameNew+"]").val();
		  
		  if(CalcUtil.subtract( monthTotalRate,monthTotalRateNew)<0){
			  window.top.alert("修改的产品综合费率不可小于原产品的综合费率:"+CalcUtil.formatMoney(monthTotalRateNew,2), 0);
			  $("input[name="+monthTotalRateName+"]").val("");
		  }else{
			 return true; 
		  }
	   };
	   
	  //申请处已有的产品产品费率进行改变的时候进行判断不可小于产品中所定的产品综合费率
	 var   _checkMonthTotalRateApply  = function(obj){
		 var   monthTotalRateName  =   $(obj).attr("name"); 
		 var   thisNameIndex  =  monthTotalRateName.substr(15);
		 var   thisKindNo  =  $("[name = kindNo_"+thisNameIndex+"]").val();
		 var   mfSysKind = _getMysKind(thisKindNo);
		 var   monthTotalRate  =   $(obj).val();
		 var   monthTotalRateNew  =	CalcUtil.formatMoney(mfSysKind.monthTotalRate,2);
		
		 if(CalcUtil.subtract( monthTotalRate,monthTotalRateNew)<0){
			  window.top.alert("修改的产品综合费率不可小于原产品的综合费率:"+CalcUtil.formatMoney(monthTotalRateNew,2), 0);
			  $("input[name="+monthTotalRateName+"]").val("");
		  }else{
			 return true; 
		  }
	 }
    var   _addPactExtend  = function(queryType){
        top.openBigForm(webPath+"/mfCusCreditApply/addPactExtend?creditAppId="+creditAppId+"&cusNo="+cusNo+"&queryType="+queryType, "添加非系统生成相关合同", function(){
            _getExendListHtmlAjax(queryType);
        });
    }
    var   _pactExtendDetail  = function(obj,url){
        top.window.openBigForm( webPath + url + "&creditAppId="+creditAppId,'编辑非系统生成相关合同',function() {
                _getExendListHtmlAjax();
        });
    }
    var _deleteExtendAjax = function(obj,url){
        alert(top.getMessage("CONFIRM_DELETE"),2,function() {
            $.ajax({
                url: webPath + url + "&creditAppId=" + creditAppId,
                success: function (data) {
                    if (data.flag == "success") {
                        _getExendListHtmlAjax();
                    } else {
                    }
                }, error: function () {

                }
            });
        });
    };
    //刷新权证列表数据
    var _getExendListHtmlAjax = function  (queryType){
        var tableId = "tablemfBusPactExtendList";
        var queryType1 = "";
        if(typeof (queryType) != "undefined" && "2" == queryType){
            tableId = "tablepactExtendStamp";
            queryType1 = queryType;
        }
        $.ajax({
            url: webPath + "/mfCusCreditApply/getExendListHtmlAjax",
            data:{creditAppId:creditAppId,
                tableId:tableId,queryType:queryType1},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#mfBusPactExtendList").html(data.tableData);
                } else {

                }
            }, error: function () {
                LoadingAnimate.stop();
            }
        });
    };

    var _creditSumChangeByBank = function (){
        var bankCreditAmt = $("input[name=bankCreditAmt]").val().replace(/,/g,"");
        $("input[name=creditSum]").val(bankCreditAmt);
        $("input[name=authBal]").val(bankCreditAmt);
    };
   
	return{
		init:_init,
		close:_close,
		ajaxUpdate:_ajaxUpdate,
		openDocTemplate:_openDocTemplate,
		addPactDetail:_addPactDetail,
		editPactDetail:_editPactDetail,
		addChildPact:_addChildPact,
		checkinfo:_checkinfo,
		creditPactTermOnBlur:_creditPactTermOnBlur,
		creditAmtCount:_creditAmtCount,
		formatMoney:_formatMoney,
		checkCreditAmt:_checkCreditAmt,
		addOneProductTypeLineNew:_addOneProductTypeLineNew,
		setKindPopupSelection:_setKindPopupSelection,
		delOneProductTypeLineForProNew:_delOneProductTypeLineForProNew,
		checkKindCreditSumForPro:_checkKindCreditSumForPro,
		turnKindNo:_turnKindNo,
		checkMonthTotalRate:_checkMonthTotalRate,
		getMysKind:_getMysKind,
		checkMonthTotalRateApply:_checkMonthTotalRateApply,
		addPactExtend:_addPactExtend,
        pactExtendDetail:_pactExtendDetail,
        deleteExtendAjax:_deleteExtendAjax,
		creditSumChangeByBank:_creditSumChangeByBank,
        updateForm:_updateForm
	};
}(window,jQuery);