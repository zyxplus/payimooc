<html>
<head>
    <meta charset="utf-8">
    <title>支付</title>
</head>

<body>

<div id="qrcodeCanvas"></div>

<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<#--<script src="https://cdn.bootcdn.net/ajax/libs/qrcodejs/1.0.0/qrcode.js"></script>-->
<script src="https://cdn.bootcdn.net/ajax/libs/qrcodejs/1.0.0/qrcode.min.js"></script>
<script type="text/javascript" src="http://cdn.staticfile.org/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<script>
    jQuery('#qrcodeCanvas').qrcode({
        text	: "${codeUrl}"
    });

    $(function () {
        //定时器
        setInterval(function () {
            console.log("开始查询支付状态")
            $.ajax({
                url: "/pay/queryByOrderId",
                data: {
                    'orderId': '123456'
                },
                success: function (result) {
                    console.log(result)
                },
                error: function (result) {
                    alert(result)
                }
            })
        }, 2000)
    });

</script>

</body>
</html>
