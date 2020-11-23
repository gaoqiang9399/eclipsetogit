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
					<form  method="post" id="cusPersonJobInsert" theme="simple" name="operform" action="${webPath}/mfCusPersonJob/insertAjax">
						<dhcc:bootstarpTag property="formcusjob00002" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusPersonJobInsert');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
	<script type="text/javascript">

	$(function() {
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
			//单位性质选择组件
        /* $("select[name=corpKind]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false//单选
		}); */
	});
	function insertCallBack() {
		top.addFlag = true;
		top.formOrTable = "table";
		myclose_click();
	}; 
	
	//selectAreaDialog(selectAreaCallBack)  选择行政区域
	function selectAreaCallBack(areaInfo){
		$("input[name=address]").val(areaInfo.disName);
	};

	

    //获取当前系统的年份
    function getNowFormatDate(){
        var date = new Date();
		var year = date.getFullYear();
		var currentYear = year;
        return currentYear;
    }

    function checkBeginDate(){
        var beaginYear = $("input[name=begYearCorp]").val();
        if(!isNaN(beaginYear))
		{
		    var nowYear =  getNowFormatDate();
		    if(nowYear<beaginYear)
			{
			    alert("工作起始年份输入错误！",0);
			    return false;
			}
		}else{
            alert("工作起始年份输入错误！",0);
            return false;
		}
	}
</script>
</html>
