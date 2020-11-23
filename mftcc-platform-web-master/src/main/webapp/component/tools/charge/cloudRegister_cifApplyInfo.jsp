<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>	
<style type="text/css">
.title{
	font-size: 20px;
	margin-top: 25px;
	margin-bottom:25px;
	color: grey;
}
.widthInput{
	width:40px;
}
</style>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form_title">云平台账户申请结果</div>
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="insertForm" theme="simple" name="operform" action="${webPath}/accountPay/insertCifAccount"  enctype="multipart/form-data">
						<dhcc:bootstarpTag property="formcifapplyInfo0002" mode="query" />
						<input type="file" id="file" name="file1" style="display: none;"/>
						<input type="hidden" name="flag" value="update">
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
				<dhcc:thirdButton value="修改" action="保存" onclick="submitThis()"></dhcc:thirdButton>
				<dhcc:thirdButton value="清空" action="关闭" typeclass="cancel" onclick="clearForm()"></dhcc:thirdButton>
		</div>
	</div>
</body>
	<script type="text/javascript">
	var code='${code}';
	var flag='${flag}'
	$(function() {
		if($("[name=cusState]").val()=="审核中"){
    		$("[name=auditDate]").parents("[class=rows]").hide();
    		$("[name=auditState]").parents("[class=rows]").hide();
    	}
		$("[name=fileName]").addClass("widthInput");
		var str="<span id='showFileName'></span>";
		var str1="<a href='javacript:void(0)' onclick='showImg()' class='afont'>查看已上传图片</a>";
		var str2="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javacript:void(0)' class='afont' id='upImg'>重新上传</a>";
		$("[name=fileName]").parent().append(str+str1+str2);
		$("[name=fileName]").hide();
		
    	$('#upImg').on('click',function(){
    		$("#file").click();
    	})
    	if(flag=='insert'){
    		alert("资料提交成功！",2);
    	}else if(flag=='update'){
    		alert("修改成功！要结束账号申请吗？",2,function(){
    			window.location.href=webPath+"/MfBusApply/getListPage?entranceNo=2";
    		});
    	}
    	//自定义滚动条
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				theme:"minimal-dark",
// 				updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	});
	$('#file').change(function(){//当选择文件的输入框值变化时，显示文件名的输入框变化
		var filePath=$("#file").val();
		if(filePath!=null&&filePath!=""){
			if(filePath.length>25){
				filePath=filePath.substring(filePath.length-25, filePath.length);
			}
			$('#showFileName').html(filePath+"&nbsp;&nbsp;&nbsp;");
		}
	})
	function submitThis(){//新增方法
		var flag=0;
		$("[class=required]").each(function(i,o){
			var value=$(o).parents("[class=rows]").find("input").val();
			if(value==null||value==""){
				flag=1;
				return false;
			}
		})
		if(flag==1){
			alert("请填写完整资料再保存！",2);
			return false;
		}
		var filePath = $("#file").val();  
	    if(null!=filePath&&"" != filePath){  
	        var fileType = getFileType(filePath);
	        //判断上传的附件是否为图片  
	        if("jpg"!=fileType && "jpeg"!=fileType && "bmp"!=fileType && "png"!=fileType){  
	            $("#file").val("");  
	            alert("请上传JPG,JPEG,BMP,PNG格式的图片!",2);
	            return false;  
	        }  
	        else{  
	            //获取附件大小（单位：KB）  
	            var fileSize = document.getElementById("file").files[0].size / 1024;  
	            if(fileSize > 2048){  
	            	 $("#file").val("");  
	                alert("图片大小不能超过2MB!",2);
	                return false;  
	            }  
	        }  
	    }
		$("#insertForm")[0].submit();
	}
	function showImg(){
		var url=webPath+'/component/tools/charge/showLicenseImg.jsp?code='+code;
		top.createShowDialog(url,"营业执照照片",'80','80',function(){
		});
	}
    //获得文件类型
    function getFileType(filePath){  
	    var startIndex = filePath.lastIndexOf(".");  
	    if(startIndex != -1)  
	        return filePath.substring(startIndex+1, filePath.length).toLowerCase();  
	    else return "";  
	}  
	function clearForm(){
		$("#showFileName").html("");
		$("[name=companyname]").val("");
		$("[name=contactName]").val("");
		$("[name=contactTel]").val("");
		$("[name=eMail]").val("");
		$("[name=companyAds]").val("");
		$("[name=file1]").val("");
		$("#remarkState").val("");
		$("[name=licenseNo]").val("");
	}
	
	</script>
</html>