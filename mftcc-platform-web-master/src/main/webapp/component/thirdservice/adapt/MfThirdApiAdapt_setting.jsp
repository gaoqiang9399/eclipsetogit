<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp" %>
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href='${webPath}/component/thirdservice/adapt/css/dragula.css' rel='stylesheet' type='text/css' />
<link href='${webPath}/component/thirdservice/adapt/css/example.css' rel='stylesheet' type='text/css' />
<link href='${webPath}/component/thirdservice/adapt/css/test.css' rel='stylesheet' type='text/css' />
<title>dragula</title>
<div class='examples'>
  <div class='parent'>
    <label style="color: #000;">请将左侧的三方字段配置到右侧的接口字段中</label>
    <div class="errorText">保存失败：一个字段只能配置一个来源</div>
    <div class='wrapper' style="width: 100%">
      <div id='left-defaults' class='container' style="width: 50%">
        <div class="options-div" data-value="NSRSBH" data-name="JCXX" data-nameCn="云平台">JCXX.NSRSBH</div>
        <div class="options-div" data-value="NSRMC" data-name="JCXX" data-nameCn="云平台">JCXX.NSRMC</div>
        <div class="options-div" data-value="ZZJGDM" data-name="JCXX" data-nameCn="云平台">JCXX.ZZJGDM</div>
        <div class="options-div" data-value="GSZCH" data-name="JCXX" data-nameCn="云平台">JCXX.GSZCH</div>
        <div class="options-div" data-value="ZCDZ" data-name="JCXX" data-nameCn="云平台">JCXX.ZCDZ</div>
        <div class="options-div" data-value="SCJYDZ" data-name="JCXX" data-nameCn="云平台">JCXX.SCJYDZ</div>
        <div class="options-div" data-value="ZCD_DHHM" data-name="JCXX" data-nameCn="云平台">JCXX.ZCD_DHHM</div>
        <div class="options-div" data-value="HYMX_DM" data-name="JCXX" data-nameCn="云平台">JCXX.HYMX_DM</div>
        <div class="options-div" data-value="HYMX_MC" data-name="JCXX" data-nameCn="云平台">JCXX.HYMX_MC</div>
        <div class="options-div" data-value="JYFW" data-name="JCXX" data-nameCn="云平台">JCXX.JYFW</div>
        <div class="options-div" data-value="DJRQ" data-name="JCXX" data-nameCn="云平台">JCXX.DJRQ</div>
        <div class="options-div" data-value="CYRS" data-name="JCXX" data-nameCn="云平台">JCXX.CYRS</div>
        <div class="options-div" data-value="DJZCLX_DM" data-name="JCXX" data-nameCn="云平台">JCXX.DJZCLX_DM</div>
        <div class="options-div" data-value="ZCZB_ZE" data-name="JCXX" data-nameCn="云平台">JCXX.ZCZB_ZE</div>
        <div class="options-div" data-value="ZCBZ_MC" data-name="JCXX" data-nameCn="云平台">JCXX.ZCBZ_MC</div>
        <div class="options-div" data-value="SYKJZD_DM" data-name="JCXX" data-nameCn="云平台">JCXX.SYKJZD_DM</div>
        <div class="options-div" data-value="NSRZT_MC" data-name="JCXX" data-nameCn="云平台">JCXX.NSRZT_MC</div>
        <div class="options-div" data-value="NSRLX_MC" data-name="JCXX" data-nameCn="云平台">JCXX.NSRLX_MC</div>
        <div class="options-div" data-value="XYDJ" data-name="JCXX" data-nameCn="云平台">JCXX.XYDJ</div>
        <div class="options-div" data-value="XYPFSJ" data-name="JCXX" data-nameCn="云平台">JCXX.XYPFSJ</div>
        <div class="options-div" data-value="XYPFFS" data-name="JCXX" data-nameCn="云平台">JCXX.XYPFFS</div>
        <div class="options-div" data-value="ZGSWJG_MC" data-name="JCXX" data-nameCn="云平台">JCXX.ZGSWJG_MC</div>
        <div class="options-div" data-value="SS" data-name="JCXX" data-nameCn="云平台">JCXX.SS</div>
        <div class="options-div" data-value="DS" data-name="JCXX" data-nameCn="云平台">JCXX.DS</div>
        <div class="options-div" data-value="FDDBRMC" data-name="JCXX" data-nameCn="云平台">JCXX.FDDBRMC</div>
        <div class="options-div" data-value="FR_GDDHHM" data-name="JCXX" data-nameCn="云平台">JCXX.FR_GDDHHM</div>
        <div class="options-div" data-value="FR_YDDHHM" data-name="JCXX" data-nameCn="云平台">JCXX.FR_YDDHHM</div>
        <div class="options-div" data-value="FR_DYDZ" data-name="JCXX" data-nameCn="云平台">JCXX.FR_DYDZ</div>
        <div class="options-div" data-value="FR_ZJLX_MC" data-name="JCXX" data-nameCn="云平台">JCXX.FR_ZJLX_MC</div>
        <div class="options-div" data-value="FR_ZJHM" data-name="JCXX" data-nameCn="云平台">JCXX.FR_ZJHM</div>


        <div class="options-div" data-value="SBRQ" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.SBRQ</div>
        <div class="options-div" data-value="SBQX" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.SBQX</div>
        <div class="options-div" data-value="ZSXM" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.ZSXM</div>
        <div class="options-div" data-value="SSSQQ" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.SSSQQ</div>
        <div class="options-div" data-value="SSSQZ" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.SSSQZ</div>
        <div class="options-div" data-value="QBXSSR" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.QBXSSR</div>
        <div class="options-div" data-value="YSXSSR" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.YSXSSR</div>
        <div class="options-div" data-value="YNSE" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.YNSE</div>
        <div class="options-div" data-value="YJSE" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.YJSE</div>
        <div class="options-div" data-value="YBTSE" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.YBTSE</div>
        <div class="options-div" data-value="JMSE" data-name="SBXX_LIST" data-nameCn="云平台">SBXX_LIST.JMSE</div>


        <div class="options-div" data-value="SSSQ_Q" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.SSSQ_Q</div>
        <div class="options-div" data-value="SSSQ_Z" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.SSSQ_Z</div>
        <div class="options-div" data-value="JKQX" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.JKQX</div>
        <div class="options-div" data-value="JKFSRQ" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.JKFSRQ</div>
        <div class="options-div" data-value="SKZT" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.SKZT</div>
        <div class="options-div" data-value="ZSXM" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.ZSXM</div>
        <div class="options-div" data-value="SKZL" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.SKZL</div>
        <div class="options-div" data-value="XSSR" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.XSSR</div>
        <div class="options-div" data-value="SL" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.SL</div>
        <div class="options-div" data-value="SE" data-name="ZSXX_LIST" data-nameCn="云平台">ZSXX_LIST.SE</div>


        <div class="options-div" data-value="DJRQ" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.DJRQ</div>
        <div class="options-div" data-value="ZYWFWZSS" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.ZYWFWZSS</div>
        <div class="options-div" data-value="ZYWFWZSDDM" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.ZYWFWZSDDM</div>
        <div class="options-div" data-value="ZYWFWZSDMC" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.ZYWFWZSDMC</div>
        <div class="options-div" data-value="WFWZLXDM" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.WFWZLXDM</div>
        <div class="options-div" data-value="WFWZLXMC" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.WFWZLXMC</div>
        <div class="options-div" data-value="WFWZZTMC" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.WFWZZTMC</div>
        <div class="options-div" data-value="CLCFJDRQ" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.CLCFJDRQ</div>
        <div class="options-div" data-value="CLBF" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.CLBF</div>
        <div class="options-div" data-value="LARQ" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.LARQ</div>
        <div class="options-div" data-value="XGZT" data-name="QYWFWZXX_LIST" data-nameCn="云平台">QYWFWZXX_LIST.XGZT</div>


        <div class="options-div" data-value="AYDJRQ" data-name="SWJCXX_LIST" data-nameCn="云平台">SWJCXX_LIST.AYDJRQ</div>
        <div class="options-div" data-value="AJLXMC" data-name="SWJCXX_LIST" data-nameCn="云平台">SWJCXX_LIST.AJLXMC</div>
        <div class="options-div" data-value="WGWZLXDM" data-name="SWJCXX_LIST" data-nameCn="云平台">SWJCXX_LIST.WGWZLXDM</div>
        <div class="options-div" data-value="WGWZLXMC" data-name="SWJCXX_LIST" data-nameCn="云平台">SWJCXX_LIST.WGWZLXMC</div>
        <div class="options-div" data-value="JCLXMC" data-name="SWJCXX_LIST" data-nameCn="云平台">SWJCXX_LIST.JCLXMC</div>
        <div class="options-div" data-value="JCZTMC" data-name="SWJCXX_LIST" data-nameCn="云平台">SWJCXX_LIST.JCZTMC</div>
        <div class="options-div" data-value="AJCLYJMC" data-name="SWJCXX_LIST" data-nameCn="云平台">SWJCXX_LIST.AJCLYJMC</div>


        <div class="options-div" data-value="BGRQ" data-name="QYBGDJXX_LIST" data-nameCn="云平台">QYBGDJXX_LIST.BGRQ</div>
        <div class="options-div" data-value="BGXM" data-name="QYBGDJXX_LIST" data-nameCn="云平台">QYBGDJXX_LIST.BGXM</div>
        <div class="options-div" data-value="BGQNR" data-name="QYBGDJXX_LIST" data-nameCn="云平台">QYBGDJXX_LIST.BGQNR</div>
        <div class="options-div" data-value="BGHNR" data-name="QYBGDJXX_LIST" data-nameCn="云平台">QYBGDJXX_LIST.BGHNR</div>

      </div>
      <div class="container chosen-box">
        <%--<div class="chosen-li">--%>
          <%--<div class="label">姓名</div><div id='name' class="text-area" data-key="name"></div>--%>
        <%--</div> --%>
        <%--<div  class="chosen-li">--%>
          <%--<div class="label">性别</div><div id='sex' class='text-area' data-key="sex"></div>--%>
        <%--</div>--%>
        <%--<div  class="chosen-li">--%>
          <%--<div class="label">年龄</div><div id='age' class='text-area' data-key="age"></div>--%>
        <%--</div>--%>
      </div>
      
    </div>
    <input type="button" name="" value="保存" class="save-button" onclick="saveConfig()">
  </div>
</div>
<script src='${webPath}/component/thirdservice/adapt/js/dragula.js'></script>
<script src='${webPath}/component/thirdservice/adapt/js/example.min.js'></script>
<script src="${webPath}/component/thirdservice/adapt/js/jquery-1.10.2.js"></script>
<script type="text/javascript">
  $(function () {
      $.ajax({
          url:"${webPath}/mfThirdApiAdapt/findListAjax",
          type:"POST",
          dataType:"json",
          success:function(data){
              var list = data.list;
              var dragulaList = [$('#left-defaults')[0]];
              for (var i = 0; i < list.length; i++) {
                  var setting = list[i];
                  $chosenLi = $("<div></div>").addClass("chosen-li");
                  $labelDiv = $("<div></div>").addClass("label").text(setting.ext1);
                  $textArea = $("<div></div>").addClass("text-area")
                      .attr({
                              "data-key":setting.serviceApiKey,
                              "data-serviceApiName":setting.serviceApiName,
                              "id":setting.serviceApiName+setting.serviceApiKey
                            });
                  $chosenLi.append($labelDiv).append($textArea);
                  if(typeof (setting.thirdNameKey)!="undefined" && setting.thirdNameKey != ''
                      && setting.thirdNameKey != null  ){
                      if($(".options-div[data-value="+setting.thirdNameKey+"][data-name="+setting.thridName+"]").length>0){
                          $(".options-div[data-value="+setting.thirdNameKey+"][data-name="+setting.thridName+"]").remove();
                      }
                      $optionsDiv = $("<div></div>").addClass("options-div")
                          .attr({"data-value":setting.thirdNameKey,
                              "data-nameCn":setting.thirdNameCn,
                              "data-name":setting.thridName
                          })
                          .text(setting.thridName+"."+setting.thirdNameKey);
                      $textArea.append($optionsDiv);
                  }
                  $(".chosen-box").append($chosenLi);
                  dragulaList.push($("#"+setting.serviceApiName+setting.serviceApiKey)[0]);

              }
              doDragulaInit(dragulaList);
          },
          error:function(){
              alert("获取数据失败，请稍后重试!");
          }
      })
  })
  function doDragulaInit(list) {
      dragula(list);
  }


  function saveConfig(){
    var requestParam = {};
    var errorFlag = false;
    $chosenBox = $(".chosen-box");
    $(".chosen-li").each(function(){
      $thisTextArea = $(this).find(".text-area");
      $optionsDiv = $(this).find(".options-div");
      if($optionsDiv.length>1){
          $(this).find(".label").css({"color":"red"});
          errorFlag = true;
      }else{
          $(this).find(".label").css({"color":"white"});
          var key = $thisTextArea.data("key");
          var serviceApiName = $thisTextArea.data("serviceapiname");
          var obj = {"thirdNameKey":"","thirdNameCn":"","thirdName":"","serviceApiName":serviceApiName};
          if($optionsDiv.length>0){
              var value = $optionsDiv.data("value");
              var nameCn = $optionsDiv.data("namecn");
              var name = $optionsDiv.data("name");
              obj.thirdNameKey = value;
              obj.thirdNameCn=nameCn;
              obj.thirdName = name;
          }
          requestParam[serviceApiName+"@"+key]=obj;
      }
    })
    if(errorFlag){
       $('.errorText').show();
       return;
    }else{
      $('.errorText').hide();
    }
    var paramString = JSON.stringify(requestParam);
    $.ajax({
        url:"${webPath}/mfThirdApiAdapt/saveSettingAjax",
        type:"POST",
        data:{"ajaxData":paramString},
        dataType:"json",
        success:function(){
          alert("保存成功");
        },
        error:function(){
          alert("保存失败，请稍后重试!");
        }
    })
  }
</script>
