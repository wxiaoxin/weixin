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

<p>${paySignMap.appId}</p>
<p>${paySignMap.timeStamp}</p>
<p>${paySignMap.nonceStr}</p>
<p>${paySignMap.packagee}</p>
<p>${paySignMap.signType}</p>
<p>${paySignMap.paySign}</p>

<a href="javascript:pay();" class="weui-btn weui-btn_primary" id="payA">支付</a>

<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script>

    // 微信配置
    wx.config({
        debug: true,
        appId: "${appId}",
        timestamp: "${jssdkMap.timestamp}",
        nonceStr: "${jssdkMap.noncestr}",
        signature: "${jssdkMap.signature}",
        jsApiList: ["onMenuShareTimeline", "onMenuShareAppMessage", "chooseWXPay"]
    });

    wx.ready(function () {

        /*支付*/
        $("#payA").click(function () {
            WeixinJSBridge.invoke('getBrandWCPayRequest', {
                "appId": "${paySignMap.appId}",
                "timeStamp": "${paySignMap.timeStamp}",
                "nonceStr": "${paySignMap.nonceStr}",
                "package": "${paySignMap.packagee}",
                "signType": "${paySignMap.signType}",
                "paySign": "${paySignMap.paySign}"
            }, function (res) {
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    alert("支付成功");
                } else if (res.err_msg == "get_brand_wcpay_request：cancel") {
                    alert("支付取消");
                } else {
                    alert("支付失败");
                }
            });
        });

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

</script>

</body>
</html>