;
var DataCleanList = function(window, $) {
	//初始化
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/mfDataClean/findByPageAjax",//列表数据查询的url
			tableId : "tabledataclean0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30//加载默认行数(不填为系统默认行数)
		});
	};

	var _password;

	var _inputPassword = function (obj) {
        _password = obj.value;
    }

	//一键清理（清理所有）
	var _cleanAllData = function(){
		alert(top.getMessage("CONFIRM_CLEAN_ALL"),2,function(){
            var html =
                "<table>" +
                "<tr>" +
                "<td style='vertical-align:middle;'>" +
                "<i class='i i-wenhao1' style='color:#4889FF; margin-right: 10px; font-size:24px;'></i>" +
                "</td>" +
                "<td style='vertical-align:middle;'>" +
                "<div style='font-size: 16px; min-width: 200px; max-width: 1024px;word-break: break-all;'>" +
                "请输入密码:<input id='passwordTemp' type='password'/>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</table>";
            var d = dialog({
                title: '系统提示',
                padding : 25,
                okValue : "确定",
                content: html,
                cancelValue : "取消",
                ok: function () {
                    var passwordTemp = $('#passwordTemp').val();
                    $.ajax({
                        url:webPath+"/mfDataClean/deleteBatchAjax",
                        type:"POST",
                        dataType:"json",
                        data:{"password":passwordTemp},
                        beforeSend:function(){
                            LoadingAnimate.start();
                        },success:function(data){
                            if(data.flag == "success"){
                                alert(data.msg,1);
                                window.updateTableData();
                            }else{
                                alert(data.msg,0);
                            }
                        },error:function(data){

                        },complete: function(){
                            LoadingAnimate.stop();
                        }
                    });
                    this.close(passwordTemp);
                    this.remove();
                },
                cancel: function () {
                    var value = $('#passwordTemp').val();
                    this.close(value);
                    this.remove();
                },
            });
            d.show();
		});
	};
	//清理单条数据
	var _cleanSingleData = function(obj,url){
		//处理url，区分客户没有业务的情况以及有业务的情况，分别处理清理的交互
		var resObj = StringUtil.urlParamsToObj(url);
		url = webPath+"/mfDataClean/getDetailPage?cusNo="+resObj.cusNo+"&appId="+resObj.appId+"&pactId="+resObj.pactId;
		if(resObj.appId==""){//1、没有业务的情况，检查该客户是否没其他业务关联
			$.ajax({
				url:webPath+"/mfDataClean/verifyCusCleanFlagAjax",
				type:"POST",
				dataType:"json",
				data:{cusNo:resObj.cusNo},
				success:function(data){
					if(data.flag == "success"){
						if(data.cleanFlag=="0"){
							//1）关联，提示“该客户为项目XXXX提供保证担保，不支持清理。”或“该客户的押品为项目XXXX提供担保，不支持清理”
							alert(data.msg,0);
							return false;
						}else{
							//2）没有关联，提示“此操作不可逆，请谨慎操作！确定要清理客户XXXX（客户名称）吗？”
							alert(top.getMessage("CONFIRM_CLEAN_CUS",resObj.cusName),2,function(){
								_cleanCus(obj,resObj.cusNo);
							});
						}
					}else{
						alert(data.msg,0);
					}
				},error:function(data){
				 	
				}
			});
		
		}else{//2、有业务的情况，弹层实现清理的详情页面
			top.openBigForm(url,"数据清理",function(){
				LoadingAnimate.start();
				window.updateTableData();
				LoadingAnimate.stop();
			});
		}
	};
	
	//清理客户信息
	var _cleanCus = function(obj,cusNo){
		$.ajax({
			url:webPath+"/mfDataClean/deleteCusAjax",
			type:"POST",
			dataType:"json",
			data:{cusNo:cusNo},
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag == "success"){
					alert(data.msg,1);
          		    $(obj).parents("tr").remove();
					updateMyCustomScrollbar.delTrData();
				}else{
					alert(data.msg,0);
				}
			},error:function(data){
			 	
			},complete: function(){
 				LoadingAnimate.stop();
 			}
		});
	}
	
	return {
		init : _init,
		cleanAllData:_cleanAllData,
		cleanSingleData:_cleanSingleData,
        inputPassword:_inputPassword
	};
}(window, jQuery);