
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript">$(function(){
            var PUSH_CAPITAL_TYPE =${ajaxData};
            $("input[name=pushCapitalType]").popupSelection({
                searchOn:true,//启用搜索
                inline:false,//弹出模式
                multiple:true,//多选
                labelShow: false,//不显示已选选项
                title:"出资方式",
                items:PUSH_CAPITAL_TYPE
            });
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });
        });
		function calculateRate(){
            var toCorpRegCapital = $("input[name=toCorpRegCapital]").val();
            var pushCapitalAtm = $("input[name=pushCapitalAtm]").val();
            if(pushCapitalAtm != null && pushCapitalAtm != ""){
                pushCapitalAtm = pushCapitalAtm.replace(/,/g,'');
                toCorpRegCapital = toCorpRegCapital.replace(/,/g,'');
                var pushCapitalScale =CalcUtil.divide(pushCapitalAtm,toCorpRegCapital);
                pushCapitalScale = CalcUtil.multiply(pushCapitalScale,100);
                pushCapitalScale =  CalcUtil.formatMoney(pushCapitalScale,2);
                if(pushCapitalScale>100){
                    pushCapitalScale = 100;
                }
                $("input[name=pushCapitalScale]").val(pushCapitalScale);
            }else{
                $("input[name=pushCapitalScale]").val("");
            }
        }
		</script>
	</head>
	<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusEquityInfoInsert" theme="simple" name="operform" action="${webPath}/mfCusEquityInfo/insertAjax">
							<dhcc:bootstarpTag property="formcusequ00003" mode="query"/>
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
		        <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusEquityInfoInsert');"></dhcc:thirdButton>
	   		 	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
