<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no" />
<title>MyTest</title>
	 <link rel="stylesheet" href="${ctx}/CSS/weui.min.css"/>
	 <script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
	<script type="text/javascript" src="${ctx}/JS/jquery-3.2.1.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/JS/jweixin-1.4.0.js" charset="utf-8"></script>
	<script type="text/javascript">
		$(function(){
			var phone = $("#phone").val();
			var count = 5;
			$("#getCode").click(function(){
				$(this).addClass("weui-btn_disabled");
				var timer = setInterval(autoplay, 1000);
				function autoplay(){
					count--;
					if(count>=0){
						$("#getCode").html(count+"s后重发");
					}else{
						$("#getCode").html("重新发送验证码");
						$("#getCode").removeClass("weui-btn_disabled");
						clearInterval(timer);
						count = 5;
					}
				}
				
				//请求后台发送短信验证码
				$.ajax({
					url:"SendMessages",
					type:"POST",
					dataType:"json",
					data:{
						phone:$("#phone").val()
					},
					success:function(data){
						console.log(data);
						if(data.respCode == "00000"){
							console.log("发送成功,验证码:"+data.verificationCode);
							weui.alert("发送成功");
							$("#verificationCode").val(data.verificationCode);
						}else{
							alert("发送失败，请稍后再试。错误代码："+data.respCode);
						}
					},
					error:function(data){
						alert("发送失败，请稍后再试");
					}
				});	
			});
			$("#formSubmitBtn").click(function(){
				var verificationCode_User = $("#verificationCode_User").val();
				var verificationCode = $("#verificationCode").val();
				if(verificationCode == verificationCode_User&&verificationCode!=null&&verificationCode!=""){
					alert("登录成功");
				}else if(verificationCode == null || verificationCode == ""){
					alert("请先获取验证码");
				}else{
					weui.alert("验证码错误");
				}
			});
		});
	</script>
</head>
<body>
	<div style="text-align: center;font-size: 25px;font-family:Microsoft YaHei;">
		<h1>index页面</h1>
		<table style="margin:auto;width: 80%;" border=1px cellspacing="0">
			<tr >
				<td >姓名</td>
				<td>${nickname }</td>
			</tr>
			<tr>
				<td>姓别</td>
				<td>${sex }</td>
			</tr>
			<tr>
				<td>城市</td>
				<td>${city}</td>
			</tr>
			<tr>
				<td>头像</td>
				<td id="previewImage"><img alt="用户头像" src="${headimgurl}" style="vertical-align: top;" id="headImg" width="50%"/></td>
			</tr>
		</table>
		<div style="margin-top: 5px"><a class="weui-btn weui-btn_primary" style="width: 40%" id="openLocationButton">查看当前位置</a></div>
		<div style="font-size: 15px">
		<div class="weui-cell weui-cell_vcode">
	        <div class="weui-cell__hd">
	            <label class="weui-label">手机号</label>
	        </div>
	        <div class="weui-cell__bd">
	            <input class="weui-input" type="tel" id="phone" required pattern="[0-9]{11}" emptyTips="请输入手机号" notMatchTips="请输入正确的手机号" placeholder="请输入手机号">
	        </div>
	        <div class="weui-cell__ft" style="width: ">
	            <a class="weui-btn weui-btn_primary weui-btn_mini" id="getCode">获取验证码</a>
	        </div>
    	</div>
	        <hr>
		<div class="weui-cell weui-cell_vcode" >
	        <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
	        <div class="weui-cell__bd">
	            <input class="weui-input" type="number" placeholder="请输入验证码" id="verificationCode_User"/>
	        </div>
    	</div>
    	<hr>
    	</div>
    	  <div class="weui-btn-area">
                    <a id="formSubmitBtn" href="javascript:" class="weui-btn weui-btn_primary">提交</a>
          </div>
	
        	<div style="margin-top: 5px"><a class="weui-btn weui-btn_primary"  id="uploadImg" style="width: 40%">上传图片</a></div>
        	<a class="weui-btn weui-btn_primary"  id="DownloadImg" style="width: 40%;font-size: 16px">下载已上传图片</a></div>
        	<!-- 
        <div class="">
            <input id="uploaderInput" class="weui-uploader__input" type="file" accept="image/*" multiple />
        </div>
         -->
	</div>
	 <!-- <div class="weui-uploader__bd">
        <ul class="weui-uploader__files" id="uploaderFiles">
           
        </ul>
    </div> -->
	
	 <div class="weui-cells weui-cells_form" id="uploader">
     <div class="weui-cell">
         <div class="weui-cell__bd">
             <div class="weui-uploader">
                 <div class="weui-uploader__hd">
                     <p class="weui-uploader__title">图片上传</p>
                     <div class="weui-uploader__info"><span id="uploadCount">0</span>/5</div>
                 </div>
                 <div class="weui-uploader__bd">
                     <ul class="weui-uploader__files" id="uploaderFiles"></ul>
                     <div class="weui-uploader__input-box">
                         <input id="uploaderInput" class="weui-uploader__input" type="file" accept="image/*" capture="camera" multiple="" />
                     </div>
                 </div>
             </div>
         </div>
     </div>
 </div>
 <script type="text/javascript">
 var uploadCount = 0;
 weui.uploader('#uploader', {
    url: 'http://localhost:8081',
    auto: true,
    type: 'file',
    fileVal: 'fileVal',
    compress: {
        width: 1600,
        height: 1600,
        quality: .8
    },
    onBeforeQueued: function(files) {
        // `this` 是轮询到的文件, `files` 是所有文件

        if(["image/jpg", "image/jpeg", "image/png", "image/gif"].indexOf(this.type) < 0){
            weui.alert('请上传图片');
            return false; // 阻止文件添加
        }
        if(this.size > 10 * 1024 * 1024){
            weui.alert('请上传不超过10M的图片');
            return false;
        }
        if (files.length > 5) { // 防止一下子选择过多文件
            weui.alert('最多只能上传5张图片，请重新选择');
            return false;
        }
        if (uploadCount + 1 > 5) {
            weui.alert('最多只能上传5张图片');
            return false;
        }

        ++uploadCount;

        // return true; // 阻止默认行为，不插入预览图的框架
    }
 });
 </script>
	
	
	
		<input type="hidden" id="latitude" />
		<input type="hidden" id="longitude" />
		<input type="hidden" id="verificationCode"  value="${verificationCode}"/>
	<script>
		$(function(){
			var domainName;
			$.ajax({
				url:"GetConfig",
				type:"POST",
				dataType:"json",
				data:{
					pageUrl:location.href
				},
				success:function(data){//后台Servlet使用response.getWriter().print（）将数据传至前台
					//console.log(data);
					domainName = data.pageUrl
					//获取签名成功后,初始化微信组件
					wx.config({
					    debug: false,
					    appId: data.appId,
					    timestamp: data.timestamp,
					    nonceStr: data.nonceStr,
					    signature: data.signature,

					    jsApiList: [
					       //加载用到的微信接口，需要一一对应
							'onMenuShareAppMessage',
							'onMenuShareTimeline',
							'getLocation',
							'openLocation','previewImage',
							'hideMenuItems','chooseImage','uploadImage','downloadImage'
					    ]
					});
				},error:function(data,textStatus,errorThrown){
					alert("长得丑，重新进入");
					//console.log(data);
					//alert(JSON.stringify(data)+'==='+textStatus+'==='+errorThrown);
				}
			});
		wx.ready(function(){
			var message ={
				title: '微信个人基本资料',
				desc: '点击查看微信个人基本信息',
				link: 'http://'+domainName+'/Test_WeChat/Oauth2',
				imgUrl: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541745154703&di=9f28f4bfd5d16eaac40c9aabb1a2db8a&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F5%2F589436647b938.jpg',
				success: function(msg){ alert('分享成功!'); }
			};
			//隐藏所有非基础按钮接口
			//wx.hideAllNonBaseMenuItem();
			//显示需要的菜单
			//wx.showMenuItems({
			//    menuList:['menuItem:share:appMessage','menuItem:share:timeline'] // 要显示的菜单项，所有menu项见附录3,'menuItem:copyUrl'
			//});
			//隐藏某些功能
			wx.hideMenuItems({
				menuList: ['menuItem:refresh'] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
			});
			//分享给朋友
			wx.onMenuShareAppMessage(message);
			//分享给朋友圈
			wx.onMenuShareTimeline(message);
			
			wx.getLocation({
				type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
				success: function (res) {
					$("#latitude").val(res.latitude); // 纬度，浮点数，范围为90 ~ -90
					$("#longitude").val(res.longitude); // 经度，浮点数，范围为180 ~ -180。
					var speed = res.speed; // 速度，以米/每秒计
					var accuracy = res.accuracy; // 位置精度
					console.log('getLocation::res--->');
					console.log(res);
				},
				cancel : function(res) {  
		              alert('未能获取地理位置');  
		            }
				});
		});
		wx.error(function(res){
			console.log(res);
			alert("发生错误");
			});
		});
		
		$("#openLocationButton").click(function(){
			wx.openLocation({
				latitude: $("#latitude").val(), // 纬度，浮点数，范围为90 ~ -90
				longitude: $("#longitude").val(), // 经度，浮点数，范围为180 ~ -180。
				name: '当前位置', // 位置名
				address: '', // 地址详情说明
				scale: 15, // 地图缩放级别,整形值,范围从1~28。默认为最大
				infoUrl: 'https://www.baidu.com' // 在查看位置界面底部显示的超链接,可点击跳转
			});
		});
		
		//JS-JDK的拍照或从手机相册中选图接口,并上传
		var images = {
		    localId: [],
		    serverId: []
		};
		//上传图片
		$("#uploadImg").click(function(){
			wx.chooseImage({
			    count: 9, // 默认9
			    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			    success: function (res) {
					console.log(res);
			        // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			        images.localId = res.localIds;
			        alert('已选择 ' + res.localIds.length + ' 张图片');
			        uplodaImages();//上传图片
			    }
			});
		});
		
		//上传图片
		function uplodaImages() {
			if (images.localId.length == 0) {
				alert('请先选择图片');
				return;
			}
			var i = 0,length = images.localId.length;
			images.serverId = [];
		    upload();
		    
		    function upload() {
		      wx.uploadImage({
		        localId: images.localId[i].toString(),//
		        success: function (res) {
		          i++;
		          //alert('已上传：' + i + '/' + length);
		          images.serverId.push(res.serverId);
		          if (i < length) {
		            upload();//嵌套递归
		          }else{
		        	  alert('已上传：' + i + '/' + length);
		          }
		        },
		        fail: function (res) {
		          alert(JSON.stringify(res));
		        }
		      });
		    }
		}
		
		//下载图片
		$("#DownloadImg").click(function(){
			if (images.serverId.length == 0) {
			      alert('请先上传图片');
			      return;
			    }
			var i = 0, length = images.serverId.length;
			download();
			
			function download() {
				//images.localId = [];
				  wx.downloadImage({
				    serverId: images.serverId[i].toString(),
				    success: function (res) {
				      i++;
				      alert('已下载：' + i + '/' + length);
				      $(".weui-uploader__files").append($("<img art='下载图片' class='weui-uploader__file' src='"+res.localId+"'/>"));
				      if (i < length) {
				        download();//嵌套递归
				      }else{
			        	  alert('已上传：' + i + '/' + length);
			          }
				    }
				  });
				}	
		});
		
	</script>
	
	<!-- 如果有多张图片，通过此脚本可以预览每张 -->
	<script>
		$(document).on('click', 'img',function(event) {
			var imgArray = [];
			var curImageSrc = $(this).attr('src');
			var oParent = $(this).parent();
			if (curImageSrc && !oParent.attr('href')) {
			$('img').each(function(index, el) {
				var itemSrc = $(this).attr('src');
				imgArray.push(itemSrc);
			});
			wx.previewImage({
				current: curImageSrc,
				urls: imgArray
				});
			}
		});
	</script>
	
</body>
</html>