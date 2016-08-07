<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>兑换中心</title>
		<meta name="author" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
	</head>
	<body>
		<#include "/common_top.ftl">
		<section>
			<div class="content detail">
				<div class="conTit">
					<img src="${couponInfo.brandLogo}">
					<h3>${couponInfo.couponName}</h3>
					<div>
						<span>有效期至 ${couponInfo.expireDate? string('yyyy.MM.dd')}</span>
						<span>已领取：${couponInfo.receivedTimes}</span>
					</div>
				</div>
				<div class="place"></div>
				<div class="conTxt">
					<p>${couponInfo.usingRule}</p>
					<div class="btn"><a href="">立即领取</a></div>
				</div>
				
			</div>
		</section>
	</body>
	<#include "/common_bottom.ftl">
</html>