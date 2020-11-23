<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit_List.js"></script>
		<script type="text/javascript" >
		var cusNo = '${cusNo}';
        var sign = '${sign}';
			$(function(){
                $.ajax({
					url:webPath+"/mfCusCustomer/getByCusName?cusNo="+ cusNo,
                    dataType:"json",
                    type:"POST",
                    success:function(data){
						if(data.flag == "1"){
                           $("#title").html("所选客户"+data.mfCusCustomer.cusName+"在系统中有重名客户，请确认是否正确：");
                           $("#cusname").val(data.mfCusCustomer.cusName);
                            $("#cusno").val(data.mfCusCustomer.cusNo);
                           $("#content").html(data.customer);
                            $("#content1").html(data.cusList);
						}
					}
				});
			});
        function save(){
          if(sign=="credit" || sign=="sure" || sign=="high"){//授信，进件，担保
              parent.dialog.get('cusMoreDialog').close().remove();
		  }else{
              $("input[name=cusNo]",parent.parent.document).val($("#cusno").val());
              $("input[name=cusName]",parent.parent.document).val($("#cusname").val());
              parent.dialog.get('cusMoreDialog').close().remove();
		  }

		}

        function cancle(){
            if(sign=="credit" || sign=="high"){//授信，进件
                $("input[name=cusNo]",parent.document).val("");
                $("input[name=cusName]",parent.document).val("");
                parent.dialog.get('cusMoreDialog').close().remove();
			}else if(sign=="sure"){//保证
                $("input[name=assureNo]",parent.document).val("");
                $("input[name=assureName]",parent.document).val("");
                $("input[name='idNum']",parent.document).val("");
                $("select[name='idType']",parent.document).val("");
                parent.dialog.get('cusMoreDialog').close().remove();
			}else{
                $("input[name=cusNo]",parent.parent.document).val("");
                $("input[name=cusName]",parent.parent.document).val("");
                parent.dialog.get('cusMoreDialog').close().remove();
			}
        }
		</script>
	</head>
	<body>
	<input type="hidden" id="cusname" value="">
	<input type="hidden" id="cusno" value="">
	<div class="container form-container" style="width:500px; height:300px; overflow:auto;">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips" id="title"></div>
					<div class="form-tips">所选客户:</div>
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div id="content" class="table_content"  style="height: auto;">
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">其他重名客户:</div>
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div id="content1" class="table_content"  style="height: auto;">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="formRowCenter">
			<dhcc:thirdButton value="确定" action="确定"  onclick="save();"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass=""  onclick="cancle();"></dhcc:thirdButton>
		</div>
	</div>
	</body>	

</html>
