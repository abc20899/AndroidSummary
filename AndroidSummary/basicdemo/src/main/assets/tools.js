function parse(url){
  return "dl";
}
const SOURCE_YOUKU = 1;
const SOURCE_QQ = 2;
const SOURCE_IQIYI = 3;
const SOURCE_SOHU = 4;
const SOURCE_MGTV = 5;
const parseType1 = "dl";
const parseType2 = "1717";
const parseType3 = "sg";
const parseType4 = "vfans";
function test2(source){
  var parseType = parseType1;
  switch(source){
      case SOURCE_YOUKU:
          parseType = parseType1;
          break;
      case SOURCE_QQ:
          parseType = parseType3;
          break;
      case SOURCE_IQIYI:
          parseType = parseType3;
          break;
      case SOURCE_SOHU:
          parseType = parseType1;
          break;
      case SOURCE_MGTV:
          parseType = parseType1;
          break;
  }
  return parseType;
}
function vipSource(){
  return parseType3;
}
function test(s){
   var sites0 = 'https://jx.wslmf.com/?url=';
   var sites1 = 'https://www.1717yun.com/jx/ty.php?url=';
   return sites0 + '#' + sites1;
}
function filterUrl(sourceIndex,url){
   switch(sourceIndex){
       case 1:
         return filter1(url);
         break;
       case 2:
         break;
       case 3:
         break;
   }
}
function filter1(url){
    if(url.indexOf("pl-ali.youku.com") != -1 || url.indexOf("pl-m3u8.youku.com") != -1 ||
         url.indexOf("pl.cp12.wasu.tv") != -1 || url.indexOf("cdn.vod.mgspcloud.migucloud.com") != -1 ||
         url.indexOf("pl.cp31.ott.cibntv.net") != -1 ||
         (url.indexOf("cn2.okokyun.com") != -1 && url.indexOf("jx.618g.com") == -1)){
       return url;
    } else if(url.indexOf("ts.m3u8") != -1 && url.indexOf("qq.com") != -1
        && url.indexOf("btrace.video.qq.com") == -1){
       return url;
    } else if(url.indexOf("v.smtcdns.com") != -1 && url.indexOf("qq.com") != -1 && url.indexOf("apd-") != -1
        && url.indexOf("btrace.video.qq.com") == -1){
       return url;
    } else if(url.indexOf("data.vod.itc.cn") != -1){
       return url;
    } else if(url.indexOf("dy.video.ums.uc.cn") != -1 || url.indexOf("disp.titan.mgtv.com") != -1 ||
       url.indexOf("titan.mgtv.com") != -1 || url.indexOf("pcvideoyd.titan.mgtv.com") != -1){
       return url;
    } else if((url.indexOf("cache.m.iqiyi.com") != -1 && url.indexOf("qd_originate") != -1) ||
       (url.indexOf("vod.hcs.cmvideo.cn") != -1 && url.indexOf("jx.618g.com") == -1) ||
       (url.indexOf("yingshi.yazyzw.com") != -1 && url.indexOf("jx.618g.com") == -1) ||
       (url.indexOf("youku163.zuida-bofang.com") != -1 && url.indexOf("jx.618g.com") == -1) ||
       (url.indexOf("pcdowncmnet.titan.mgtv.com") != -1 && url.indexOf("jx.618g.com") == -1) ||
       (url.indexOf("youku.cdn2-youku.com") != -1 && url.indexOf("jx.618g.com") == -1) ||
       (url.indexOf("163.com-www-letv.com") != -1 && url.indexOf("jx.618g.com") == -1) ||
       (url.indexOf("v-360kan.com") != -1 && url.indexOf("jx.618g.com") == -1) ||
       (url.indexOf("bobo.okokbo.com") != -1 && url.indexOf("jx.618g.com") == -1) ||
       url.indexOf("bobo.kukucdn.com") !=-1 || url.indexOf("index.m3u8") !=-1){
       return url;
    }else{
       return "noUrl";
    }
}
function md5Str(md5){
    var md5 = eval('('+md5+')');
    return md5;
}
var line = ['/yunjx/','/yunjx2/','/yunjx3/'];
var hasLine = function(url){
  if(url && line.indexOf(url)>-1)return "1.1";
  return "0.1";
}
