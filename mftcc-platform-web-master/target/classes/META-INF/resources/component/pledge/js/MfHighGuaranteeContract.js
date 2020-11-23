var MfHighGuaranteeContract = function(window,$){
	var _toApplyInsert = function(){
		window.location.href=webPath+"/mfHighGuaranteeContract/input"; 
	};
	//新增保存申请
	var _insertSave = function(formObj){
		var type = $("select[name=type]").val();
		var url = $(formObj).attr("action");
		var dataForm = JSON.stringify($(formObj).serializeArray());
		top.LoadingAnimate.start();
		$.ajax({
			url : url,
			data : {
				ajaxData : dataForm,
			},
			type : "post",
			dataType : "json",
			success : function(data) {
				top.flag=true;
				top.LoadingAnimate.stop();
				if (data.flag == "success") {
					window.top.alert(data.msg, 1);
					window.location.href=webPath+"/mfHighGuaranteeContract/getById?highGrtContractId="+data.highGrtContractId;
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				window.top.alert(data.msg, 0);
			}
		});
	};

	var _getById = function(obj,url){
        window.location.href=webPath+url;
    }
	//返回
	var _myclose=function(){
		window.location.href=webPath+"/mfHighGuaranteeContract/getListPage";
	};

	//删除最高额担保合同
    var _ajaxDelete = function(obj,urlArgs){
        var url = webPath+urlArgs;
        jQuery.ajax({
            url:url,
            data:{},
            type:"POST",
            dataType:"json",
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                    alert(data.msg,1);
                    //回调处理
                    window.updateTableData();
                }else if(data.flag == "error"){
                    alert(data.msg,0);
                }
            },error:function(data){
                alert(top.getMessage("FAILED_OPERATION"," "),0);
            },complete:function(){
                LoadingAnimate.stop();
            }
        });

    };
    //提交最高额担保合同
   /* var _ajaxDelete = function(obj,urlArgs){
        var url = webPath+urlArgs;
        jQuery.ajax({
            url:url,
            data:{},
            type:"POST",
            dataType:"json",
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                    alert(data.msg,1);
                    //回调处理
                    window.updateTableData();
                }else if(data.flag == "error"){
                    alert(data.msg,0);
                }
            },error:function(data){
                alert(top.getMessage("FAILED_OPERATION"," "),0);
            },complete:function(){
                LoadingAnimate.stop();
            }
        });

    };*/
    //撤销关联
    var _ajaxDeleteSubRel = function(obj,urlArgs){
        var url = webPath+urlArgs;
        jQuery.ajax({
            url:url,
            data:{},
            type:"POST",
            dataType:"json",
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                    alert(data.msg,1);
                    //重新加载明细记录
                    _showHighGrtContractSubList(highGrtContractId);
                }else if(data.flag == "error"){
                    alert(data.msg,0);
                }
            },error:function(data){
                alert(top.getMessage("FAILED_OPERATION"," "),0);
            },complete:function(){
                LoadingAnimate.stop();
            }
        });

    };
    var _updatePactEndDate=function() {
        var beginDate = $("input[name=startDate]").val();
        var term = $("input[name=term]").val();
        if (term == ''||beginDate=='') {
            return;
        }
        var termType =1;
        var intTerm = parseInt(term);
        var d,str;
        if (1 == termType) { //融资期限类型为月
            d = new Date(beginDate);
            d.setMonth(d.getMonth() + intTerm);
            str = d.getFullYear()+ "-"+ (d.getMonth() >= 9 ? d.getMonth() + 1 : "0"+ (d.getMonth() + 1)) + "-"+ (d.getDate() > 9 ? d.getDate() : "0" + d.getDate());
            $("input[name=endDate]").val(str);
        } else { //融资期限类型为日
            d = new Date(beginDate);
            d.setDate(d.getDate() + intTerm);
            str = d.getFullYear()+ "-"+ (d.getMonth() >= 9 ? beginDate.getMonth() + 1 : "0"+ (d.getMonth() + 1)) + "-"+ (d.getDate() > 9 ? d.getDate() : "0" + d.getDate());
            $("input[name=endDate]").val(str);
        }
    }

    //客户弹出层数据
    var _selectCustomerList = function(){
        //客户新组件
        $("input[name=cusName]").popupList({//随便填写一个隐藏域，防止这个字段不能填写的问题
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfCusCustomer/findByPageForSelectAjax?removeCusId=&cusBaseType=",//请求数据URL
            valueElem:"input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            changeCallback:function(elem){//回调方法
                var cusNo = elem.data("values").val();
                $("input[name=cusName]").val(elem.data("text"));
                //查询是否重名
                $.ajax({
                    url : webPath+"/mfCusCustomer/getCusByIdAjax",
                    type : "post",
                    async: false,
                    data : {cusNo:cusNo},
                    dataType : "json",
                    success : function(data) {
                        if (data.flag == "success") {
                            if (data.listCus == "1") {
                                dialog({
                                    id: 'cusMoreDialog',
                                    title: "重名客户",
                                    url: webPath + "/mfCusCustomer/getCusListByName?sign=high&cusNo=" + cusNo,
                                    width: 500,
                                    height: 300,
                                    backdropOpacity: 0,
                                    onshow: function () {
                                        this.returnValue = null;
                                    },
                                    onclose: function () {
                                        if (this.returnValue) {
                                            //返回对象的属性:实体类MfCusCustomer中的所有属性
                                            if (typeof(callback) == "function") {
                                                callback(this.returnValue);
                                            } else {
                                                getCusInfoArtDialog(this.returnValue);
                                            }
                                        }
                                        var cusNo = $("input[name=cusNo]").val();
                                        if(cusNo ==null || cusNo==""){//取消
                                            $("input[name=cusName]").parent().find(".pops-value").click();
                                        }
                                    }
                                }).showModal();
                            }
                        }else{

                        }
                    },
                    error : function() {
                        window.top.alert("根据客户号获取授信流程失败",1);
                    }
                });
                },
            tablehead:{//列表显示列配置
                "cusNo":{"disName":"客户编号","align":"center"},
                "cusName":{"disName":"客户名称","align":"center"}
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
    }

    //显示明细列表
    var _showHighGrtContractSubList = function(highGrtContractId){
        _refreshHeadMsg(highGrtContractId);
        $.ajax({
            url:webPath+"/mfHighGuaranteeContract/getSubListAjax",
            data:{highGrtContractId:highGrtContractId},
            success:function(data){
                if(data.flag == "error"){
                    alert(data.msg, 0);
                }else{
                    $("#highGrtSubListInfo").empty().html(data.tableData);
                }
            },
            error:function(a,b,c){
                alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    }
    //添加明细
    var _insertSub = function(id){
        top.window.openBigForm(encodeURI(webPath+"/mfHighGuaranteeContract/insertSub?highGrtContractId="+id), '新增反担保信息', function(){
            _showHighGrtContractSubList(highGrtContractId);
        });
    }
    //保存明细
    var _insertSubAjax=function(obj,skipFlag){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            _insertBase(obj,skipFlag);
        }
    };
    var _insertBase=function(obj,skipFlag){
        if(typeof(collateralType)=='undefined' || collateralType==''){
            collateralType = 'pledge';
        }
        var url = webPath+'/mfHighGuaranteeContract/insertSubAjax';
        var dataParam = JSON.stringify($(obj).serializeArray());
        var param =  {ajaxData : dataParam,highGrtContractId:highGrtContractId,type:highGrtContractType,entrFlag:entrFlag,isQuote:isQuote,'collateralType':collateralType};
        jQuery.ajax({
            url : url,
            data : param,
            type : "POST",
            dataType : "json",
            beforeSend : function() {},
            success : function(data) {
                if (data.flag == "success") {
                    if(top.collaFlag!=undefined){
                        top.collaFlag=true;
                    }
                    if(top.addCollateral!=undefined){
                        top.addCollateral=true;
                        top.collateralId =data.pledgeNo;
                        top.collateralName =data.pledgeName;
                        top.classId=data.classId;
                        top.vouType=data.pledgeMethod;
                    }
                    if(top.addCreditCollaFlag!=undefined){
                        top.addCreditCollaFlag=true;
                        top.creditAppId=appId;
                    }
                    if(top.extenFlag!=undefined){
                        top.skipFlag=skipFlag;
                        top.extenFlag=true;
                        top.addCollateral=true;
                        top.pledgeId=data.pledgeNo;
                    }
                    alert(data.msg, 1);
                    myclose_click();
                } else if (data.flag == "error") {
                    alert(data.msg, 0);
                }
            },
            error : function(data) {
                alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    };

    var _refreshHeadMsg=function(highGrtContractId){
        jQuery.ajax({
            url : webPath+'/mfHighGuaranteeContract/refreshHighGrtContract',
            data : {highGrtContractId:highGrtContractId},
            type : "POST",
            dataType : "json",
            beforeSend : function() {},
            success : function(data) {
                if (data.flag == "success") {
                    var highGuaranteeContract = data.mfHighGuaranteeContract;
                    if(highGuaranteeContract){
                        var colCnt = highGuaranteeContract.colCnt;
                        var amt = highGuaranteeContract.amt;
                        var colAmt = highGuaranteeContract.colAmt;
                        var bal = highGuaranteeContract.bal;
                        if(colCnt==''||colCnt==null){
                            colCnt = 0;
                        }
                        if(amt==''||amt==null){
                            amt = 0.0;
                        }
                        amt = Math.round(parseFloat(amt)/100.0,2)/100.0;
                        if(colAmt==''||colAmt==null){
                            colAmt = 0.0;
                        }
                        colAmt = Math.round(parseFloat(colAmt)/100.0,2)/100.0;
                        if(bal==''||bal==null){
                            bal = 0.0;
                        }
                        bal = Math.round(parseFloat(bal)/100.0,2)/100.0;
                        $("#colCnt").text(colCnt);
                        $("#amt").text(amt);
                        $("#colAmt").text(colAmt);
                        $("#bal").text(bal);
                    }
                } else if (data.flag == "error") {
                    alert(data.msg, 0);
                }
            },
            error : function(data) {
                alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    }
	return{
		toApplyInsert:_toApplyInsert,
		insertSave:_insertSave,
        getById:_getById,
		myclose:_myclose,
		ajaxDelete:_ajaxDelete,
        ajaxDeleteSubRel:_ajaxDeleteSubRel,
        updatePactEndDate:_updatePactEndDate,
        selectCustomerList:_selectCustomerList,
        showHighGrtContractSubList:_showHighGrtContractSubList,
        insertSub:_insertSub,
        insertSubAjax:_insertSubAjax,
        refreshHeadMsg:_refreshHeadMsg
	}
}(window,jQuery)	
