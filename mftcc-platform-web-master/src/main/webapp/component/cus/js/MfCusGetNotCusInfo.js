var MfCusGetNotCusInfo = function(window, $) {
    var _init = function() {
        myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url:webPath+"/mfCusCustomer/getNotCusInfoPageAjax",
            tableId:"tablegetnotcusinfo",//列表数据查询的table编号
            tableType:"thirdTableTag",//table所需解析标签的种类
            ownHeight:true,
            pageSize:30//加载默认行数(不填为系统默认行数)
        });
    };
    /**
     * 选择所有未入业务的客户信息
     * @private
     */
var _getcusmanager=function (parm) {
        parm=parm.split("?")[1];
        var parmArray=parm.split("&");
        var cusno = parmArray[0].split("=")[1];
        top.cusno=cusno;
        var url="/component/cus/Mfcusslipinfo.jsp";
        top.applyUserName="";
        openCreatShowDialog(url,"选择客户经理",70,70,function(data){
            if(top.applyUserName==""){
                return
            }else {
                $("input[name='applyName']").val(top.applyUserName);
            }
        });
    };
    var _getcusmanagers=function () {
        var url="/mfCusCustomer/getCusmngList?cusmugno="+cusmugno;
        top.cusMngNo="";
        top.cusMngName="";
        openCreatShowDialog(url,"选择客户经理",70,70,function(data){
            if(top.cusMngNo==""){
                return
            }else {
                $("input[name='recOpName']").val(top.cusMngName);
                $("input[name='recOpNo']").val(top.cusMngNo);
            }
        });
    };
      var  _getcusslip=function (parm) {
          parm=parm.split("?")[1];
          var parmArray=parm.split("&");
          var applyUserName = parmArray[0].split("=")[1];
          var cusno=top.cusno;
          top.LoadingAnimate.start();
          $.ajax({
              url: webPath+"/mfCusCustomer/cusslipAjax",
              data: {
                  applyUserName:applyUserName,
                  cusno:cusno,
              },
              type: "post",
              dataType: "json",
              success: function (data) {
                  top.flag = true;
                  top.LoadingAnimate.stop();
                  if (data.flag == "success") {
                      window.top.alert(data.msg, 3);
                      myclose_showDialogClick();
                  } else {
                      window.top.alert(data.msg, 0);
                  }
              }
          });
          MfCusGetNotCusInfo.init();
    };
    var _getcusslips=function (parm) {
        parm=parm.split("?")[1];
        var parmArray=parm.split("&");
        var cusMngName = parmArray[0].split("=")[1];
        var cusMngNo = parmArray[1].split("=")[1];
        top.cusMngName=cusMngName;
        top.cusMngNo=cusMngNo;
        myclose_showDialogClick();
    };
    //详情页面关闭
    var _register =function(){
        window.location.href=webPath+"/mfCusCustomer/getNotCusInfoList";
    };
    var _cusslip=function (formObj) {
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
                    window.top.alert(data.msg, 3);
                    myclose_click();
                } else {
                    window.top.alert(data.msg, 0);
                }
            },
            error : function(data) {
                top.loadingAnimate.stop();
                window.top.alert(data.msg, 0);
            }
        });
    };
    var _judgecusslip=function (cusNo) {
        $.ajax({
            url : webPath+"/mfCusCustomer/judgecusslip?cusNo="+cusNo,
            type : 'post',
            dataType : 'json',
            success : function(data) {
                   if(data.flag == "true"){
                    $("#slip").attr("disabled",true);
                    $("#slip").attr("class", "btn btn-opt-dont");
                }
            }
        });
    };
    return {
        init : _init,
        getcusmanager:_getcusmanager,
        getcusslip:_getcusslip,
        register:_register,
        getcusmanagers:_getcusmanagers,
        getcusslips:_getcusslips,
        cusslip:_cusslip,
        judgecusslip:_judgecusslip
    };
}(window, jQuery);