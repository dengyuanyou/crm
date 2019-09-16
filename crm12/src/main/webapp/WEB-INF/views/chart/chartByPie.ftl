<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>报表</title>
    <script src="/js/echarts/echarts.common.min.js"></script>
</head>
<body>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 550px;height:400px; margin: 30px auto"></div>

<script type="text/javascript">

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.querySelector("#main"));

    // 指定图表的配置项和数据
    var option = {

        //中心的标题和副标题
        title : {
            text: "销售报表",
            subtext: '${groupTypeName}',
            left:'center'
        },

        //控制鼠标放在饼图上是否显示相关数据
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

        //以折线图，饼状图显示数据
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: true},
                magicType : {show: true, type: ['line']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },

        //左边组成图标显示
        legend: {
            orient : 'vertical',
            x : 'left',
            //组成成分数据
            data:${legendDate}
        },

        //右边工具图标
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },

        calculable : true,

        //饼状图数据
        series : [
            {
                //鼠标放在饼图上显示的标题
                name:"客户总数",
                type:'pie',
                avoidLabelOverlap:false,
                //标签
                label:{
                    normal:{
                        show:true,
                        position:'inside',
                        formatter:'{d}%',
                        textStyle:{
                            align:'center',
                            baseline:'middle',
                            fontFamily:'微软雅黑',
                            fontSize:15,
                            fontWeight:'bolder'
                        }
                    },
                },
                radius : '55%',
                center: ['50%', '60%'],
                //饼图的组成各个部分的百分比，同时鼠标放在饼图上显示成分和百分比
                data: ${seriesDate},
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: "rgba(0, 0, 0, 0.5)"
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>