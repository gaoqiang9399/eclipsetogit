define(function(require, exports, module) {

    //×Ö·û´®½Ø³ý'px'   150px  -> 150
   exports.splitPx = function(pxStr){
       //ÅÐ¶Ï×Ö·û´®ÊÇ²»ÊÇÒÔpx½áÎ²
       var lastStr =    pxStr.substr(pxStr.length-2,pxStr.length);
       if(lastStr == "px"){
           return pxStr.substr(0,pxStr.length-2);
       }
   }

});