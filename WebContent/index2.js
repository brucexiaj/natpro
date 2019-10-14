var yzms = "";
var phonenumber = "";
function getyzm(){
	//监测题目是否完成
	//console.log("asdf");
	var appkey="7d72f636e0a3d1f81bca1b64360621a5";
	var flag = 0;
	for (var i = 1; i <= 18; i++) {
		var elename = "answer" + String(i);
		var elements = document.getElementsByName(elename);		
		for (var j = 0; j < elements.length; j++) {
			if (elements[j].checked) {
				flag++;
			}
		}
		//console.log(flag);
	}
	//console.log(flag);
	for (var i = 1; i <= 5; i++) {
		var eleid = "myselect" + String(i);
		var element = document.getElementById(eleid);
		if (element.selectedIndex != 0) {
			//console.log(element.selectedIndex);
				flag++;
		}
	}
	//console.log(flag);
	if (23 != flag) {
			window.alert("您有未完成的题目");
			return;
	}else{
		//发送验证码
		
		phonenumber = document.getElementById("phoneinput").value;
		if(phonenumber == "") {
			window.alert("请输入手机号码");
			return;
		}else{
			if(!(/^1[3456789]\d{9}$/.test(phonenumber))){ 
				window.alert("手机号码格式不正确");  
			        return; 
			} 
			//使用ajax发送同步请求
		 //发送异步请求
			//1.创建ajax引擎对象----所有操作都是由ajax引擎完成
			 var xmlHttp = new XMLHttpRequest();
			  //2.为引擎对象绑定监听事件
			 xmlHttp.onreadystatechange = function() {
				                            //alert(data);
				//window.alert("phoneinput");
			 //当状态变化时处理的事情
				if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				     //5.接收响应信息
				    yzms = xmlHttp.responseText;
				//window.alert(yzms);
//					window.alert(data);
//				    console.log(data);
//					yzms = data;
				}
			};
			xmlHttp.open("GET", "/NatPros/send?phonenumber="+phonenumber,true);            
			        //4.发送请求
			xmlHttp.send();	
			
			 
			 
			//按钮改变颜色
				var sendbuttontext = document.getElementById("sendbuttontext");
				var sendbutton = document.getElementById("idsendbutton");
				if(String(sendbuttontext.innerHTML) == "发送验证码") {
					sendbuttontext.innerHTML = "60S后重发";		
					//console.log(sendbutton.style);
					sendbutton.style.backgroundColor = "gray";
					sendbutton.disabled = true;
					//console.log(sendbutton.style);
					
				}
				var start = 59;
				var test2 = setInterval(function(){
					   //your codes
					sendbuttontext.innerHTML = start+"S后重发";
					start--;
					if(start==0){
						clearInterval(test2);
						sendbuttontext.innerHTML = "发送验证码";
						sendbutton.style.backgroundColor = "rgb(74,144,226)";
						sendbutton.disabled = false;
					}
				},1000)
					

					
				

		}	
	}
}

function finished(){
	var flag = 0;
	//单选题答案}
	var singleanswer =  [1,1,2,0,1,1, 1,0,0,2, 2,3,2, 1,0,2,0,2];
	var correct = 0;
	for (var i = 1; i <= 18; i++) {
		var elename = "answer" + String(i);
		var elements = document.getElementsByName(elename);		
		for (var j = 0; j < elements.length; j++) {
			if (elements[j].checked) {
				flag++;
				if (j == singleanswer[i-1]) {
					correct++;
				}
			}
		}
		//console.log(flag);
	}
	var choiceanswer =  [2,9,6,4,8];
	for (var i = 1; i <= 5; i++) {
		var eleid = "myselect" + String(i);
		var element = document.getElementById(eleid);
		if (element.selectedIndex != 0) {
				flag++;
				if (element.selectedIndex == choiceanswer[i-1]) {	
					correct++;
				}
		}
	}
	//console.log (correct);
	if (23 != flag) {
		window.alert("您有未完成的题目");
		return;
	}
	
	var userinputyzm = "";
	userinputyzm = document.getElementById("userinputyzm").value;
	if (userinputyzm == "") {
		window.alert("请输入验证码");
		return;
	}
//	console.log(typeof(yzms));
//	console.log(typeof(userinputyzm));
//	console.log(userinputyzm.length);
//	console.log(yzms.length);
//	for(var i=0;i<8;i++){
//		console.log(yzms.charAt(i));
//	}
	//这里一定要substring，因为后台返回的东西有多余的字符
	if (userinputyzm != yzms.substring(0,6)) {
		window.alert("验证码不正确");
		return;
	}
	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		console.log("store");
	};
	var gift = "青铜";
	if(10<=correct &&correct <=13) {
		gift="白银";
	}
	if(14<=correct &&correct <=19) {
		gift="黄金";
	}
	if(20<=correct &&correct <=23) {
		gift="铂金";
	}
	var surl = "/NatPros/store?phonenumber="+phonenumber+"&correct="+correct+"&gift="+gift;
	//window.alert(surl);
	xmlHttp.open("POST", surl,true);            
	        //4.发送请求
	xmlHttp.send();	
	
	if(correct < 10) {
		window.location.href = "qingtong.html?phone="+phonenumber;
			//window.location.href = "qingtong.html";
	}
	if(10<=correct &&correct <=13) {
			window.location.href = "baiying.html?phone="+phonenumber;
	}
	if(14<=correct &&correct <=19) {
			window.location.href = "huangjin.html?phone="+phonenumber;
	}
	if(20<=correct &&correct <=23) {
			window.location.href = "bojin.html?phone="+phonenumber;
	}
	//完成了几道题	
}

function clicklingqu(index){
	//是否选择了礼物
	var eleid = "myselect"+index;
	var element = document.getElementById(eleid);
	if (element.selectedIndex == 0) {
		window.alert("请选择礼包");
		return;
	}
	var libao = element.options[element.selectedIndex].value;
	//获取手机号码
	var name,value; 
	var str=location.href; //取得整个地址栏
	var num=str.indexOf("?") 
	  // str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
	var phone = str.substr(num+7);
	   //window.alert(phone);
	window.alert("领取成功，之后会有专属小助手联系您");
	//更新用户状态，存放用户领取的礼包
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		console.log("updata");
	};
	
	xmlHttp.open("GET", "/NatPros/lingqu?phonenumber="+phone+"&libao="+libao,true);            
	        //4.发送请求
	xmlHttp.send();	
}



function xianzhi(a,b){
	var name = "answer"+a;
	var eles = document.getElementsByName(name);
	for(var i=0;i<eles.length;i++){
		eles[i].checked = false;
	}
	eles[b].checked = true;
	//window.alert("领取成功，之后会有专属小助手联系您");

}