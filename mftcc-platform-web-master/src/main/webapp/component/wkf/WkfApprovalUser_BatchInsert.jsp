<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script language ="javascript" type="text/javascript"> 
        function clearCheckBox()
         {
             var txts=document.getElementsByTagName("input");
             for(var i=0;i <txts.length;i++)
             {
                 if(txts[i].type=="checkbox")
                 {
                     txts[i].checked=false;
                 }
             }
         }
         function selectAllCheckBox(objName)
         {
         	 var txts=document.getElementsByName(objName);
             for(var i=0;i <txts.length;i++)
             {
                 if(txts[i].type=="checkbox")
                 {
                     txts[i].checked=true;
                 }
             }
         }
    </script>
	<body class="body_bg">
	<dhcc:markPoint markPointName="WkfApprovalUser_BatchInsert"/>
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
				<div class="right_v">
				<form method="post" theme="simple" name="operform"
					action="${webPath}/wkfApprovalUser/BatchInsert">
					<dhcc:formTag property="formwkf0007" mode="query" />
					<div class="from_btn">
						<dhcc:button typeclass="button3" commit="true" value="保存" action="保存"  ></dhcc:button>
						<dhcc:button typeclass="button_form" value="重置" action="重置" onclick="clearCheckBox()"></dhcc:button>
					</div>
				</form>
				</div>
			</div>
			</div>
		</div>
	</body>
</html>