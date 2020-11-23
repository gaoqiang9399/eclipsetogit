var MfRepaymentTrial = function(window,$){

    /*
     *判断日期事件
     */
    function _pdRiQiEvent(){
        var tiQianHuanBen = $("#tiQianHuanBen").val();
        if(tiQianHuanBen!="" &&tiQianHuanBen!=0.00){
            _tiQianHuanKuanRiQiChange();
        }else{
           _repayDateChange();
            $("#normalpaymoney").css("display","block");
            $("#beforpaymoney").css("display","none");
        }
    }

    /*
    * 判断 还本金额中是否有值（若有值：视为展示提前还款信息 ；若无：视为展示正常还款信息）
    * */
    function _pdIsNotValue(obj){
        var tiQianHuanBen = $("#tiQianHuanBen").val();
       var msg =  _func_uior_valType(obj);
        if(tiQianHuanBen!=""){
            if(msg==''){
                $("#beforpaymoney").css("display","block");
                $("#normalpaymoney").css("display","none");
                $("#yingShouZongJiAll").text(yingShouZongJiAllFormat);// 实收总额
                _shiShouBenJinByTiQianHuanKuanInputOnblur();
            }
        }
    }
    /**
     * 提前还款  还款日期 变化时 执行的方法
     * @author WD
     */
    function  _tiQianHuanKuanRiQiChange(){

        /*$("#YouHuiZongEr").val("0.00");
        $("#YouHuiZongEr_input_text").val("0.00");*/
        $("#shiShouLiXi").text("0.00");
        $("#shiShouLiXi_input_text").val("0.00")
        _shiShouBenJinByTiQianHuanKuanInputOnblur();
    }
    /**
     * 提前还本金额变化 实收利息 和违约金都变化
     */
    function _shiShouBenJinByTiQianHuanKuanInputOnblur(){

        // 获取提前还本金额
        var tiQianHuanBen=$("#tiQianHuanBen").val().replace(/,/g, "");
        // 获取当期本金
        var dangQiBenJin=$("#dangQiBenJin_input_text").val();
        //剩余本金
        var shengYuBenJin=$("#shengYuBenJin_input_text").val();
        //还款日期
        var repayDate=$("#systemDateLong").val();
        //借据号
        var fincId=$("#fincId").val();
        var money1,money2;
        //如果是提前结清
        if(preRepayType=="1"|| preRepayType=="3"){
            if(tiQianHuanBen*1<shengYuBenJin*1){
                money1="提前还款本金";
                money2="剩余本金："+shengYuBenJin+"元";
                alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
                $("#tiQianHuanBen").val("0.00");
                return ;
            }
        }

        //判断提前还款是否结清本息
        if(termInstMustBack=="1"){//当期本息是否必须归还：1-是、0-否
            //NOT_FORM_TIME  不能大于        NOT_SMALL_TIME  不能小于
            if(tiQianHuanBen*1<dangQiBenJin*1){
                money1="提前还款本金";
                money2="当期本金："+dangQiBenJin+"元";
                alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
                $("#tiQianHuanBen").val("0.00");
                return ;
            }
        }
        // 提前还款本金 不能大于剩余本金
        if(tiQianHuanBen*1>shengYuBenJin*1){
            money1="提前还款本金";
            money2="剩余本金："+shengYuBenJin+"元";
            alert(top.getMessage("NOT_FORM_TIME",{"timeOne":money1,"timeTwo":money2}),0);
            $("#tiQianHuanBen").val("0.00");
            return ;
        }
        //计算提前还款 利息 和 违约金
        $.ajax({
            url:webPath+'/mfRepayment/calcLiXiTiQianHuanKuanAjax',
            data:{"repayDate":repayDate,"fincId":fincId,"tiQianHuanBen":tiQianHuanBen},
            type:"POST",
            dataType:"json",
            beforeSend:function(){
            },
            success:function(data){
                if(data.flag == "success"){
                    //实收利息 shiShouLiXiFormat 和违约金 tiQianHuanKuanWeiYueJinFormat 重新赋值
                    $("#shiShouLiXi").text(data.shiShouLiXiFormat);
                    $("#shiShouLiXi_input_text1").val(data.shiShouLiXi);
                    $("#shiShouLiXiTip_input_text1").val(data.shiShouLiXi);
                    $("#tiQianHuanKuanWeiYueJin").text(data.tiQianHuanKuanWeiYueJin);
                    $("#tiQianHuanKuanWeiYueJin_input_text1").val(data.tiQianHuanKuanWeiYueJin);
                    $("#tiQianHuanKuanWeiYueJinTip_input_text1").val(data.tiQianHuanKuanWeiYueJin);
                    // 计算实收总额
                    _calcshiShouZongJiCallByTiQian();
                }
            },error:function(data){
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION"," "),0);
            }
        });
    }
    // 计算实收总额
    function _calcshiShouZongJiCallByTiQian() {
        //提前还本
        var tiQianHuanBen=$("#tiQianHuanBen").val().replace(/,/g, "");
        //实收利息
        var shiShouLiXi=$("#shiShouLiXi_input_text1").val();
        //提前还款违约金
        var tiQianHuanKuanWeiYueJin=$("#tiQianHuanKuanWeiYueJin_input_text1").val();
        //本次结余
        var benCiJieYu=$("#benCiJieYu_input_text1").val();
        //本次冲抵
        var benCiChongDi=$("#benCiChongDi_input_text1").val();
        //实收总计
        var shiShouZongJi="0.00";
        shiShouZongJi=tiQianHuanBen*1+shiShouLiXi*1+tiQianHuanKuanWeiYueJin*1+benCiJieYu*1-benCiChongDi*1
        shiShouZongJi=round(shiShouZongJi*1,returnPlanPoint);

        //计算实收总计
        $("#shiShouZongJi").text(shiShouZongJi);
        $("#shiShouZongJi_input_text1").val(shiShouZongJi);
        $("#yingShouZongJiAll").text(shiShouZongJi);
    }

    //验证数据类型
    function _func_uior_valType(obj){

        var msg='';
        var str=obj.value;
        var type=obj.getAttribute('datatype');
        var reg;
        if(str!=undefined){
            if(type!='undefined'&&func_uior_trim(type)!=''){

                if(type==1){
                    if(str==""){
                        obj.value="0";
                    } else {
                        //var reg=/^-?[1-9]\d*|0$/;
                        reg=/^-?[0-9]*$/;//此写法允许首字符为0
                        if(!reg.test(str)){
                            msg=obj.getAttribute('title')+'中的内容不是整数!\n';
                            obj.value="0";
                        }
                    }
                }else if(type==2){
                    if(str==""){
                        obj.value="0";
                    } else {
                        reg=/^-?[1-9]\d*|0$/;
                        if(!reg.test(str)){
                            msg=obj.getAttribute('title')+'中的内容不是长整数!\n';
                            obj.value="0";
                        }
                    }
                }else if(type==3 ||type==8 || type==9 ||type==13||type==14||type==15||type==16||type==17||type==18||type==19){
                    if(str==""){
                        obj.value="0.00";
                    } else {
                        reg=/^-?([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/;
                        if(!reg.test(str)){
                            msg=obj.getAttribute('title')+'中的内容不是数字!\n';
                            obj.value="0";
                        }
                    }
                }else if(type==4){
                    if(str==""){
                        obj.value="0.00";
                    } else {
                        reg=/^[1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$/;
                        if(!reg.test(str)){
                            msg=obj.getAttribute('title')+'中的内容不是数字(正数)!\n';
                            obj.value="0";
                        }
                    }
                }else if(type==5){
                    if(str==""){
                        obj.value="";
                    } else {
                        if(str!='true'&&str!='false'){
                            msg=obj.getAttribute('title')+'中的内容不是布尔值!\n';
                        }
                    }
                }else if(type==6){
//			if(str.length==10){
//	   			 	var yy = parseInt(str.substring(0,4));
//	        		var mm = parseInt(str.substring(5,7));
//	        		var dd = parseInt(str.substring(8,10));
//	        		if(mm > 12 || mm <= 0 || dd <= 0 || dd > 31)
//	        			return obj.getAttribute('title')+'中的内容不是日期!\n';
//	       		 	var rndd = ((yy%4==0)&&(yy%100!=0)||(yy%400 == 0))?29:28;
//	        		switch(mm){
//	         			 case 4:
//	          			case 6:
//	          			case 9:
//	          			case 11:
//	            		if(dd > 30 || dd<=0)
//	              			msg=obj.getAttribute('title')+'中的内容不是日期!\n'
//	            			break;
//	          			case 1:
//	          			case 3:
//	          			case 5:
//	          			case 7:
//	          			case 8:
//	          			case 10:
//	          			case 12:
//	            		if(dd>31 || dd<=0)
//	                		msg=obj.getAttribute('title')+'中的内容不是日期!\n'
//	            		break;
//	          			case 2:
//	            			if(dd > rndd || dd <= 0)
//	                		msg=obj.getAttribute('title')+'中的内容不是日期!\n'
//	            		break;
//	       				}
//					}else{
//	   				msg=obj.getAttribute('title')+'中的内容不是日期!\n'
//	   				}
                }else if(type==12){
                    reg=/^([+-]?)((\d{1,3}(,\d{3})*)|(\d+))(\.\d*)?$/;
                    if(!reg.test(str)){
                        if(str==""){
                            obj.value="0.00";
                        }else{
                            msg = obj.getAttribute('title')+'中的内容不是金额类型!\n';
                            obj.value="0.00";
                        }
                    } else {
                        //为金额型输入框末尾添加.00
                        var strPoints = str.split("\.");	//判断字符串包含几个小数点符号
                        var dotSize = obj.getAttribute('size');
                        if (strPoints.length==2){
                            if(strPoints[1].length==0){
                                obj.value = strPoints[0] + ".00";
                            }else if(strPoints[1].length==1){
                                obj.value = str+"0";
                            }else if(strPoints[1].length>2&&!dotSize){
                                obj.value = "0.00";
                                msg = obj.getAttribute('title')+'金额只保留2位小数点!\n';
                            }
                        } else if(strPoints.length==1){
                            obj.value = str+".00";
                        } else {
                            msg = obj.getAttribute('title')+'中的内容不是金额类型!\n';
                            obj.value="0.00";
                        }
                        try{
                            if(document.getElementsByName("bigAmt_"+obj.getAttribute('name'))[0]){
                                document.all["bigAmt_"+obj.getAttribute('name')].value = bigAmt(obj.value);
                            }
                        }catch(e){}
                    }
                }

            }

        }
        return msg;
    }
    /**
     * 正常还款试算还款日期修改使用
     */
    function _repayTrialDateChange() {

        var repayDate = $("#systemDateLong").val().replace(/-/g, "");
        var fincId = $("#fincId").val();
        var yujiguihuan = $("#yujiguihuan").val();
        $.ajax({
            url : webPath+'/mfRepayPlanTrial/rapayTrialDateChangeAjax',
            data : {
                "repayDate" : repayDate,
                "fincId" : fincId,
                "yujiguihuan":yujiguihuan
            },
            type : "POST",
            dataType : "json",
            beforeSend : function() {
            },
            success : function(data) {
                if (data.flag == "success" && data.ifchange == "change") {
                    $("#normalYingShouZongJiAll").text(data.yinshouheji);
                    _pdIsNotValue();
                }

            },
            error : function(data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    }
    /**
     * 还款日期修改使用 1 判断是否存在逾期的还款计划 2 如果存在逾期的还款计划则需要修改还款页面相关值 3 如果不存在逾期的还款计划 则还款页面不必进行修改
     */
    function _repayDateChange() {

        var repayDate = $("#systemDateLong").val().replace(/-/g, "");
        var fincId = $("#fincId").val();
        $.ajax({
            url : webPath+'/mfRepayment/rapayDateChangeTrialAjax',
            data : {
                "repayDate" : repayDate,
                "fincId" : fincId
            },
            type : "POST",
            dataType : "json",
            beforeSend : function() {
            },
            success : function(data) {
                if (data.flag == "success" && data.ifchange == "change") {
                    var mfRepaymentBean = data.mfRepaymentBean;
                    $("#normalShiShouBenJin").text(mfRepaymentBean.shiShouBenJinFormat);
                    $("#shiShouBenJin_input_text2").text(mfRepaymentBean.shiShouBenJin);
                    $("#normalShiShouLiXi").text(mfRepaymentBean.shiShouLiXiFormat);// 实收利息
                    $("#shiShouLiXi_input_text2").text(mfRepaymentBean.shiShouLiXi);// 实收利息
                    $("#shiShouYuQiLiXi").text(mfRepaymentBean.shiShouYuQiLiXiFormat);// 实收逾期利息
                    $("#shiShouYuQiLiXi_input_text2").text(mfRepaymentBean.shiShouYuQiLiXi);// 实收逾期利息
                    $("#shiShouFuLiLiXi").text(mfRepaymentBean.shiShouFuLiLiXiFormat);// 实收复利利息
                    $("#shiShouFuLiLiXi_input_text2").text(mfRepaymentBean.shiShouFuLiLiXi);// 实收复利利息
                    $("#shiShouFuLiLiXiPart").text(mfRepaymentBean.shiShouFuLiLiXiPartFormat);// 实收复利利息浮动部分
                    $("#shiShouFuLiLiXiPart_input_text2").text(mfRepaymentBean.shiShouFuLiLiXiPart);// 实收复利利息浮动部分
                    $("#shiShouFeiYong").text(mfRepaymentBean.shiShouFeiYongFormat);// 实收费用
                    $("#shiShouFeiYong_input_text2").text(mfRepaymentBean.shiShouFeiYong);// 实收费用
                    $("#shiShouFeiYongFaXi").text(mfRepaymentBean.shiShouFeiYongFaXiFormat);// 实收费用罚息
                    $("#shiShouFeiYongFaXi_input_text2").text(mfRepaymentBean.shiShouFeiYongFaXi);// 实收费用罚息
                    $("#shiShouYuQiWeiYueJin").text(mfRepaymentBean.shiShouYuQiWeiYueJinFormat);// 实收违约金
                    $("#shiShouYuQiWeiYueJin_input_text2").text(mfRepaymentBean.shiShouYuQiWeiYueJin);// 实收违约金
                    $("#yingShouZongJiAll").text(mfRepaymentBean.yingShouZongJiAllFormat);// 实收总额
                    $("#normalYingShouZongJiAll").text(mfRepaymentBean.yingShouZongJiAllFormat);// 正常实收总额
                    $("#yingShouZongJiAll_input_text2").text(mfRepaymentBean.yingShouZongJiAll);// 实收总额
                    $("#shiShouZongJi").attr('placeholder', '本次应收总额 ' + mfRepaymentBean.shiShouZongJi + '元');// 实收总额

                }
            },
            error : function(data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    }
    return {
        pdRiQiEvent:_pdRiQiEvent,
        func_uior_valType:_func_uior_valType,
        shiShouBenJinByTiQianHuanKuanInputOnblur : _shiShouBenJinByTiQianHuanKuanInputOnblur,
        tiQianHuanKuanRiQiChange:_tiQianHuanKuanRiQiChange,
        calcshiShouZongJiCallByTiQian:_calcshiShouZongJiCallByTiQian,
        pdIsNotValue:_pdIsNotValue,
        repayDateChange:_repayDateChange,
        repayTrialDateChange:_repayTrialDateChange,
    };
}(window, jQuery);