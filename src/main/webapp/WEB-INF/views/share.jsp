<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>微信JSSDK测试</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="//res.wx.qq.com/open/libs/weui/1.0.2/weui.min.css">
</head>
<body>

<a href="javascript:scan()" class="weui-btn weui-btn_primary">扫码</a>

<a href="javascript:chooseImage()" class="weui-btn weui-btn_primary">选择图片</a>

<img id="img" style="width: 100px; height: 100px;">

<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script>

    <%-- 微信配置 --%>
    wx.config({
        debug: false,
        appId: "${appId}",
        timestamp: "${timestamp}",
        nonceStr: "${noncestr}",
        signature: "${signature}",
        jsApiList: ["scanQRCode","onMenuShareTimeline", "onMenuShareAppMessage", "chooseImage"]
    });

    wx.ready(function () {
        /*分享到朋友圈*/
        wx.onMenuShareTimeline({
            title: "标题",
            link: "http://www.baidu.com",
            imgUrl: "https://www.baidu.com/img/bd_logo1.png",
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });

        /*分享给好友*/
        wx.onMenuShareAppMessage({
            title: "标题啊",
            desc: "分享描述",
            link: "http://www.baidu.com",
            imgUrl: "https://www.baidu.com/img/bd_logo1.png",
            type: "link",
            dataUrl: '',
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
    });

    wx.error(function (msg) {
        alert("error " + msg);
    });

    function chooseImage() {
        /*选择图片*/
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                alert(localIds);
                var img = document.getElementById("img");
                img.src = localIds;
            }
        });
    }

    function scan() {
        /*扫码*/
        wx.scanQRCode({
            needResult: 1,
            desc: 'scanQRCode desc',
            success: function (res) {
                //扫码后获取结果参数:htpp://xxx.com/c/?6123，截取到url中的防伪码后，赋值给Input
                var url = res.resultStr;
                var tempArray = url.split('?');
                var tempNum = tempArray[1];
                console.log(tempNum);
            }
        });
    }

</script>

</body>
</html>