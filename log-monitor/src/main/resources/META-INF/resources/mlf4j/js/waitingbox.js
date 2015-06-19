/*!
 * WaitingBox.js 
 * A simple tool for waiting for page processing
 * https://github.com/zollty-org/waitingbox
 * Version 1.0 (Released on 2014-11-07)
 * Licensed under the MIT license. Please see LICENSE for more information.
 */
;(function(window){

	
//取得浏览器环境的waitingbox命名空间，非浏览器环境符合commonjs规范exports出去
var waitingbox = typeof module === 'undefined' ? (window.waitingbox = window.waitingbox || {}) : module.exports;


var wb_div, isInit = false, alertText = "正在处理中，请稍等 ..."; // Processing, please wait...

/**
 * @param [String] 开关，如果设置为true，则会在文档加载时自动调用waiting box
 */
function init(isWaitOnInit) {
	
	if(!isInit) {
		var newElement = document.createElement("div");
		newElement.setAttribute("id", "waitingbox");  
		newElement.setAttribute("style", "width:200px; height:60px; position:absolute; z-index:99; left:50%; top:50%; margin:-30px 0 0 -100px; border: 2px solid #CCE8CF; display:none;line-height: 60px; vertical-align: middle; text-align:center; z-index:100; font-size:12px; background-color:#F1FEDD; font-family:'微软雅黑';filter: alpha(opacity = 70);opacity: 0.7;");  
		var newElementHtmlContent = document.createTextNode(alertText);
		var bodyElement = document.getElementsByTagName('body')[0];
		bodyElement.appendChild(newElement);
		newElement.appendChild(newElementHtmlContent);
		
		wb_div = document.getElementById("waitingbox");
		
		if(isWaitOnInit){
			showWBox();
			if (document.all){//IE
				window.attachEvent('onload',hideWBox);
				window.attachEvent('onbeforeunload',showWBox);
			}else{
				window.addEventListener('load',hideWBox,false);
				window.addEventListener('beforeunload',showWBox,false);
			}
		}
	}
	
	isInit = true;
}

function zoutyMouseHandle(event) {
	alert(alertText);
	event.cancelBubble = true;
}
function zoutyBlockMouseEvent() {
	if (document.all) {
		window.document.attachEvent('onmousedown', zoutyMouseHandle);
	} else {
		window.document.addEventListener('mousedown', zoutyMouseHandle, false);
	}
}
function zoutyReleaseMouseEvent() {
	if (document.all) {
		window.document.detachEvent('onmousedown', zoutyMouseHandle);
	} else {
		window.document.removeEventListener('mousedown', zoutyMouseHandle, false);
	}
}


function hideWBox() {
	if(!isInit) init(false);
	zoutyReleaseMouseEvent();
	document.body.style.cursor = "";
	wb_div.style.display = "none";
}

function showWBox() {
	zoutyBlockMouseEvent();
	document.body.style.cursor = "wait";
	if(!isInit) init(false);
	wb_div.style.display = "";
}

waitingbox.alertText = alertText;
waitingbox.init = init;
waitingbox.hide = hideWBox;
waitingbox.show = showWBox;
///////////////waiting box END////////////////

})(window);