var MfBusBankAccCom = function(window, $) {
    var _bankAccInit = function () {
        /*var payMethod = $("[name=payMethod]").val();//支付方式1-自主支付 2-受托支付, 3-信贷放款, 4-信贷放款-内转, 5-信贷放款-下拨, 6-信贷放款-付款 7-信贷放款-内转-下拨
        //if(payMethod=="1" || payMethod=="3" || payMethod=="4" || payMethod=="5" || payMethod=="6"){//如果是自主支付，隐藏掉收款账户的相关字段
            $("input[name=collectAccId]").parents("tr").css("display","none");
            $("input[name=collectAccId]").removeAttr("mustinput","0");
            $("input[name=collectBank]").parents("tr").css("display","none");
       // }*/
    };

    var formatBankNo = function (accountNo){
        if(/\S{5}/.test(accountNo)){
            var value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
            return value;
        }
        return accountNo;
    }

    var getCusBankInfo = function(type,id){
        $.ajax({
            url:webPath+"/mfCusBankAccManage/getByIdAjax",
            data:{id:id},
            type:"POST",
            dataType:"json",
            beforeSend:function(){
            },success:function(data){
                LoadingAnimate.stop();
                if(data.flag == "success"){
                    if(type=="loanAcc"){
                        $("input[name=incomAccountName]").val(data.mfCusBankAccManage.accountName);
                        $("input[name=incomBank]").val(data.mfCusBankAccManage.bank);
                    }else if(type=="repayAcc"){
                        $("input[name=repayAccName]").val(data.mfCusBankAccManage.accountName);
                        $("input[name=repayBank]").val(data.mfCusBankAccManage.bank);
                    }else if(type=="collectAcc"){
                        $("input[name=collectAccName]").val(data.mfCusBankAccManage.accountName);
                        $("input[name=collectBank]").val(data.mfCusBankAccManage.bank);
                    }
                }
                if(data.flag == "error"){
                    alert(data.msg,0);
                }
            },error:function(data){
            }
        });
    };

    //新增账户信息
    var addBankAccFun = function(useType,title,cusNo,callback){
        dialog({
            id:"addBankAccDialog",
            title:title,
            url: webPath+'/mfCusBankAccManage/inputForBiz?cusNo='+cusNo+'&relNo='+cusNo+'&useType='+useType,
            width:1000,
            height:500,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    if(typeof(callback)== "function"){
                        callback(this.returnValue);
                    }else{
                    }
                }
            }
        }).showModal();
    };

    //支付方式
    var _payMethodChangeEvent = function(obj){
        var payMethod = $(obj).val();//支付方式1-自主支付 2-受托支付, 3-信贷放款, 4-信贷放款-内转, 5-信贷放款-下拨, 6-信贷放款-付款 7-信贷放款-内转-下拨
        if(payMethod=="1" || payMethod=="3" || payMethod=="4" || payMethod=="5" || payMethod=="6"){//如果是自主支付，隐藏掉收款账户的相关字段
            $("input[name=collectAccId]").parents("tr").css("display","none");
            $("input[name=collectAccId]").attr("mustinput","0");
            $("input[name=collectBank]").parents("tr").css("display","none");
            $("[name=ifImmediateTransfer]").parents("td").css("display","none");
            $("[name=ifImmediateTransfer]").parents("td").prev("td").css("display","none");
        }else{
            $("input[name=collectAccId]").parents("tr").removeAttr("style");
            $("input[name=collectAccId]").attr("mustinput","1");
            $("input[name=collectBank]").parents("tr").removeAttr("style");
            $("[name=ifImmediateTransfer]").parents("td").removeAttr("style");
            $("[name=ifImmediateTransfer]").parents("td").prev("td").removeAttr("style");
        }
    };

    //借款账号、还款账号绑定事件
    function bindAccIdDataSource(type) {
        var accIdElem = "";
        var $_accId = null;
        var $_accountName = null;
        var $_bank = null;
        if (type == "loanAcc") {
            accIdElem = "input[name=bankAccId]";
            $_accId = $("input[name=bankAccId]");
            $_accountName = $("input[name=incomAccountName]");
            $_bank = $("input[name=incomBank]");
        } else if (type == "repayAcc") {
            accIdElem = "input[name=repayAccId]";
            $_accId = $("input[name=repayAccId]");
            $_accountName = $("input[name=repayAccName]");
            $_bank = $("input[name=repayBank]");
        } else if(type=="collectAcc"){
            accIdElem = "input[name=collectAccId]";
            $_accNo = $("input[name=collectAccount]")
            $_accId = $("input[name=collectAccId]");
            $_accountName = $("input[name=collectAccName]");
            $_bank = $("input[name=collectBank]");
        }
        $_accId.val("");
        $_accountName.val("");
        $_bank.val("");

        $_accNo.popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfCusBankAccManage/getAccIdDataAjax?cusNo=" + cusNo,// 请求数据URL
            valueElem : accIdElem,//真实值选择器
            title : "选择账号",//标题
            changeCallback : function(elem) {//回调方法
                BASE.removePlaceholder($_accId);//借款账号
                BASE.removePlaceholder($_accountName);//借款账号名称
                BASE.removePlaceholder($_bank);//借款账号开户行
                var sltVal = elem.data("selectData");
                $_accId.val(sltVal.id);
                $_accountName.val(sltVal.accountName);
                $_bank.val(sltVal.bank);
            },
            tablehead : {//列表显示列配置
//				"id" : "id",
                "accountNo" : "卡号/折号",
                "accountName" : "账户名称",
                "useType" : {"disName":"账号用途","typepara":"FUND_ACC_TYPE"},
                "bank" : "开户行",
                "bankFullName" : "开户行全称"
            },
            returnData : {//返回值配置
                disName : "accountNo",//显示值
                value : "id"//真实值
            }
        });
        $_accNo.next().click();
    };

    //获取收款银行账号
    var  _getBankCusAccMange  =	function  (){
        var  cusNo =  $("input[name=cusNo]").val();
        $("input[name=collectAccId]").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl : webPath+"/cwCusBankAccManage/getbusCwBankCusAccMangeAjax?cusNo="+cusNo, // 请求数据URL
            valueElem:"input[name='collectAccId']",//真实值选择器
            title: "选择机构",//标题
            changeCallback:function(elem){//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=popscollectAccId]").val(tmpData.id);
                $("input[name=collectAccName]").val(tmpData.accountName);
                $("input[name=collectBank]").val(tmpData.bank);
            },
            tablehead:{//列表显示列配置
                "accountNo":"账号",
                "accountName":"账号名称",
                "bank":"开户行"
            },
            returnData:{//返回值配置
                disName:"accountNo",//显示值
                value:"id"//真实值
            }
        });
        $("input[name=collectAccId]").next().click();
    };


    return {
        bankAccInit : _bankAccInit,
        payMethodChangeEvent:_payMethodChangeEvent,
        getBankCusAccMange:_getBankCusAccMange,
        bindAccIdDataSource:bindAccIdDataSource,
    };
}(window, jQuery);