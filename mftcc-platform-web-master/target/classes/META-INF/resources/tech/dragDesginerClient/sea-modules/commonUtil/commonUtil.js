define(function(require, exports, module) {

    //�ַ����س�'px'   150px  -> 150
   exports.splitPx = function(pxStr){
       //�ж��ַ����ǲ�����px��β
       var lastStr =    pxStr.substr(pxStr.length-2,pxStr.length);
       if(lastStr == "px"){
           return pxStr.substr(0,pxStr.length-2);
       }
   }

});