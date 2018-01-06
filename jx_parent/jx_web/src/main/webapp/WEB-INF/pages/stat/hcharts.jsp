<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highcharts Example</title>

		<script src="${ctx }/js/hcharts/js/jquery-1.8.3.min.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript">
$(function () {
	var data = ${chartData};
	
	
    $('#container').highcharts({
        title: {
            text: '在线人数统计',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: itcast.cn',
            x: -20
        },
        xAxis: {
            categories: ['00', '01', '02', '03', '04', '05',
                '06', '07', '08', '09', '10', '11',
                '12', '13', '14', '15', '16', '17',
                '18', '19', '20', '21', '22', '23']
        },
        yAxis: {
            title: {
                text: '人数 (人)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '人'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: data
    });
});
		</script>
	</head>
	<body>
<script src="${ctx }/js/hcharts/js/highcharts.js"></script>
<script src="${ctx }/js/hcharts/js/modules/exporting.js"></script>

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

	</body>
</html>
