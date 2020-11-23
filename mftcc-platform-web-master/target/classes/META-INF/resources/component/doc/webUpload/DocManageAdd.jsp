<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="docManageForm" theme="simple" enctype="multipart/form-data" name="operform" action="${webPath}/mfCusCustomer/insertForBusAjax">
						<dhcc:bootstarpTag property="formDocManageAdd" mode="query"/>
					</form>
				</div>
			</div>
		</div>
		<input type="hidden" id="type" value="1"></input>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" typeclass="save" onclick="uploadDocManage('#cusInsertForm','insert');"></dhcc:thirdButton>
		</div>
	</div>
	</body>
	<script>
		var docListByType = ${docTypeList};
        var typeList = ${docList};
		var relNo = '${relNo}';
		var cusNo = '${cusNo}';
		$(function () {
            $("input[name=docSplitNo]").popupSelection({
                searchon:false,//启用搜索
                inline:false,//下拉模式
                multiple:false,//多选
				groupFlag:true,
                labelshow:false,//选择区域显示已选择项
                title : "文件类型",//标题
                items:docListByType
            });
			$("input[name=upload]").attr({"multiple":"",
				"type":"file"});

        })


        function uploadDocManage(){
            var flag = submitJsMethod($("#docManageForm").get(0), '');
            if (!flag) {
                return false;// 校验表单, 如文件类型必填
            }

		    var docSplitNo = $("input[name=docSplitNo]").val();
            for (var i = 0; i < typeList.length; i++) {
                if(typeList[i].docSplitNo==docSplitNo){
                    var obj = typeList[i];
                    $("input[name=relNo]").val(relNo);
                    $("input[name=docType]").val(obj.docType);
                    $("input[name=docBizNo]").val(obj.docBizNo);
                    $("input[name=scNo]").val(obj.scNo);
                    $("input[name=cusNo]").val(cusNo);
                    break;
                }
            }

            var formData = new FormData(document.getElementById("docManageForm"));//获取form值
            if($("input[type='file']").val()==""){
                alert("请先选择文件！",1);
                return;
            }
            $.ajax({
                url: webPath+"/docUpLoad/uploadFileToServiceByForm",
                type: "POST",
                data: formData,
                processData: false,  // 不处理数据
                contentType: false,   // 不设置内容类型
                success:function(data){
					var list = data.resultList;
                    var msg = "";
					for (var o in list){
					    var msg =msg+list[o].docManage.docName+"——"+list[o].msg+"<br/>";
					}
                    top.alert(msg,3);
                    myclose_click();
                }
            });
        }
	</script>
</html>
