;
var MfBusApply_RiskManagement = function(window, $) {
    var _init = function(){
        myCustomScrollbar({
            obj : "#content",//页面内容绑定的id
            url : webPath+"/apiReturnRecord/findByPageAjax?cusNo="+cusNo,//列表数据查询的url
            tableId : "tableapiReturnRecordlist",//列表数据查询的table编号
            tableType : "thirdTableTag",//table所需解析标签的种类
            pageSize : 30,//加载默认行数(不填为系统默认行数)
            topHeight:235
        });
        //三方数据查询类型初始化
        $.each(typeList, function(i, parmDic) {
            $("#serviceType").append('<li role="presentation" onclick="MfBusApply_RiskManagement.queryThirdData(\''+parmDic.optCode+'\',\''+parmDic.optName+'\');" style="cursor:pointer;"><a role="menuitem">'+parmDic.optName+'</a></li>');
        });
        $(".dropdown-menu").mCustomScrollbar({
            advanced : {
                updateOnContentResize : true
            }
        });
        //根据分数判断颜色
        _color();
    };
    /**
     * 根据分数判断颜色
     */
    var _color = function(){
        $('#content tbody tr').each(function(){
            var score = $(this).find('td').eq(2).text();
            if("" != score && score != null){
                if(score>="90"){
                    $(this).find('td').eq(2).css("color","red");
                    $(this).find('td').eq(3).css("color","red");
                }else if(score>="80" && score<"90"){
                    $(this).find('td').eq(2).css("color","Violet");
                    $(this).find('td').eq(3).css("color","Violet");
                }else if(score>="65" && score<"80"){
                    $(this).find('td').eq(2).css("color","yellow");
                    $(this).find('td').eq(3).css("color","yellow");
                }else if(score>="50" && score<"65"){
                    $(this).find('td').eq(2).css("color","blue");
                    $(this).find('td').eq(3).css("color","blue");
                }else {
                    $(this).find('td').eq(2).css("color","green");
                    $(this).find('td').eq(3).css("color","green");
                }
            }
        });
    }
    /**
     * 三方数据查询
     */
    var _queryThirdData = function(type,name,parm){
        var parmArray,cusname, cusnexus, becusno, contactsTel, commAddress;
        if(type == "TTYY"){
            alert("查询一次收费0.63元，确认继续查询？",2,function(){
                top.window.openBigForm(webPath+"/apiReturnRecord/getYouYuAjax?cusNo="+cusNo+"&type="+type,'天天有余反欺诈查询',function(){
                    updateTableData();
                });
            });
        }else if (type == "JXL_MF"){
            if(parm!=undefined){
                parm=parm.split("?")[1];
                parmArray=parm.split("&");
                cusname = parmArray[0].split("=")[1];
                cusnexus= parmArray[1].split("=")[1];
                becusno=  parmArray[2].split("=")[1];
                contactsTel= parmArray[3].split("=")[1];
                commAddress= parmArray[4].split("=")[1];
                top.window.openBigForm(webPath+"/apiReturnRecord/getJXLMFResultAjax?cusNo="+cusNo+"&cusname="+cusname+"&cusnexus="+cusnexus+"&becusno="+becusno+"&contactsTel="+contactsTel+"&commAddress="+commAddress,'聚信立-蜜蜂查询',function(){

                });
            }else {
                top.window.openBigForm(webPath+"/apiReturnRecord/getJXLMFResultAjax?cusNo="+cusNo+"&appid="+appId,'聚信立-蜜蜂查询',function(){

                });
            }
        }else{
            if(parm!=undefined){
                parm=parm.split("?")[1];
                parmArray=parm.split("&");
                cusname = parmArray[0].split("=")[1];
                cusnexus= parmArray[1].split("=")[1];
                becusno=  parmArray[2].split("=")[1];
                contactsTel= parmArray[3].split("=")[1];
                commAddress= parmArray[4].split("=")[1];
                alert(top.getMessage("CONFIRM_OPERATION","(" + name + ")风控查询"),2,function(){
                    $.ajax({
                        url : webPath+ "/apiReturnRecord/getThirdAjax",
                        data : {appId: appId, cusNo : cusNo,type : type,cusname:cusname,cusnexus:cusnexus,becusno:becusno,contactsTel:contactsTel,commAddress:commAddress},
                        async : false,
                        success : function(data) {
                            if (data.flag == "success") {
                                window.location.href=webPath+"/apiReturnRecord/riskManagement?appId=" + appId +"&cusNo="+cusNo+"&query=query";
                                window.top.alert(data.msg, 3);
                            }else if (data.flag == "error") {
                                window.top.alert(data.msg, 3);
                            }
                        },error : function() {
                            alert(top.getMessage("FAILED_OPERATION"," "),0);
                        }
                    });
                });
            }else{
                alert(top.getMessage("CONFIRM_OPERATION","(" + name + ")风控查询"),2,function(){
                    $.ajax({
                        url : webPath+ "/apiReturnRecord/getThirdAjax",
                        data : {appId: appId, cusNo : cusNo,type : type},
                        async : false,
                        success : function(data) {
                            if (data.flag == "success") {
                                window.location.href=webPath+"/apiReturnRecord/riskManagement?appId=" + appId +"&cusNo="+cusNo+"&query=query";
                                window.top.alert(data.msg, 3);
                            }else if (data.flag == "error") {
                                window.top.alert(data.msg, 3);
                            }
                        },error : function() {
                            alert(top.getMessage("FAILED_OPERATION"," "),0);
                        }
                    });
                });
            }
        }
    };
    //一键查询三方数据
    var _onechackfind=function (parm) {
        parm=parm.split("?")[1];
        var parmArray=parm.split("&");
        var cusname = parmArray[0].split("=")[1];
        var cusnexus= parmArray[1].split("=")[1];
        var becusno=  parmArray[2].split("=")[1];
        var contactsTel= parmArray[3].split("=")[1];
        var commAddress= parmArray[4].split("=")[1];
        var JXLMG="JXL_MG";
        var BRDATA="BR_DATA";
        alert(top.getMessage("CONFIRM_OPERATION","百融贷前管理风控查询"),2,function(){
            $.ajax({
                url : webPath+ "/apiReturnRecord/getThirdAjax",
                data : {appId: appId, cusNo : cusNo,type : BRDATA,cusname:cusname,cusnexus:cusnexus,becusno:becusno,contactsTel:contactsTel,commAddress:commAddress},
                async : false,
                success : function(data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        alert(top.getMessage("CONFIRM_OPERATION","聚信立蜜罐风控查询"),2,function(){
                            $.ajax({
                                url : webPath+ "/apiReturnRecord/getThirdAjax",
                                data : {appId: appId, cusNo : cusNo,type : JXLMG,cusname:cusname,cusnexus:cusnexus,becusno:becusno,contactsTel:contactsTel,commAddress:commAddress},
                                async : false,
                                success : function(data) {
                                    if (data.flag == "success") {
                                        window.top.alert(data.msg, 3);
                                        alert(top.getMessage("CONFIRM_OPERATION","聚信立蜜蜂风控查询"),2,function() {
                                            top.window.openBigForm(webPath+"/apiReturnRecord/getJXLMFResultAjax?cusNo="+cusNo+"&cusname="+cusname+"&cusnexus="+cusnexus+"&becusno="+becusno+"&contactsTel="+contactsTel+"&commAddress="+commAddress,'聚信立-蜜蜂查询',function(){
                                            });
                                        });
                                    }else if (data.flag == "error") {
                                        alert(data.msg, 3);
                                    }
                                },error : function() {
                                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                                }
                            });
                        });
                    }else if (data.flag == "error") {
                       alert(data.msg, 3);
                    }
                },error : function() {
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });
        });
    }
    /**
     * 打开报告
     */
    var _openResultHtml = function(obj,url){
        var strUrl = url.toString();
        var array = strUrl.split("?")[1].split("&");
        var returnId = array[0].split("=")[1];
        var threeParty = array[1].split("=")[1];
        var orderNo = array[2].split("=")[1];
        var reqSts = array[3].split("=")[1];
        if(array.length>4){
            var token = array[4].split("=")[1];
        }
        if(array.length>5){
            var id = array[5].split("=")[1];
        }
        if(reqSts == "1"){//请求成功的直接展示
        	if("TD_DATA" == threeParty){
        		top.window.openBigForm( webPath+ "/apiReturnRecord/inputTDResult?returnId="+returnId+"&threeParty="+threeParty+"&id="+id,"风险查询结果",function(){
        			
        		});
        	}else if("BR_STRATEGY_ID_1" == threeParty || "BR_STRATEGY_ID_2" == threeParty|| "BR_DATA" == threeParty){
        		top.window.openBigForm( webPath+ "/apiReturnRecord/inputBRResult?orderNo=" + orderNo,"风险查询结果",function(){
        			
        		});
        	}
        	else{
        		top.window.openBigForm( webPath+ "/apiReturnRecord/inputResult?returnId="+returnId+"&threeParty="+threeParty+"&token="+token+"&id="+id+"&appId="+appId,"风险查询结果",function(){
        			
        		});
        	}
        }else{//请求失败的进行异常处理
        	alert("三方数据查询时发生异常，报告不可查看",3);
        }

    }
    /**
     * 在return方法中声明公开接口。
     */
    return {
        init : _init,
        queryThirdData:_queryThirdData,
        openResultHtml:_openResultHtml,
        onechackfind:_onechackfind
    };
}(window, jQuery);