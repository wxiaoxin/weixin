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


<a href="javascript:pay();" class="weui-btn weui-btn_primary" id="payA">支付</a>

<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script>

    // 微信配置
    wx.config({
        debug: false,
        appId: "${appId}",
        timestamp: "${timestamp}",
        nonceStr: "${noncestr}",
        signature: "${signature}",
        jsApiList: ["scanQRCode", "onMenuShareTimeline", "onMenuShareAppMessage", "chooseImage"]
    });

    wx.ready(function () {

        // 支付
        $("#payA").click(function () {
            WeixinJSBridge.invoke('getBrandWCPayRequest', {
                "appId": '${appId}',
                "timeStamp": '${paytimestamp}',
                "nonceStr": '${paynonceStr}',
                "package": '${paypackage}',
                "signType": '${paysignType}',
                "paySign": '${paypaySign}'
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
    });

    wx.error(function (msg) {
        alert("error " + msg);
    });

</script>

</body>
</html>