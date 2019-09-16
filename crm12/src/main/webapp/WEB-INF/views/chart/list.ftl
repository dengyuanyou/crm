<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>客户关系管理系统</title>
    <#include "../common/header.ftl" >

    <script src="/js/My97DatePicker/WdatePicker.js"></script>
    <script>
        $(function () {
            //日期插件的使用
            $("#searchForm [name=beginDate]").click(function () {
                WdatePicker({
                    readOnly:true,
                    maxDate:new Date()
                });
            });
            $("#searchForm [name=endDate]").click(function () {
                WdatePicker({
                    readOnly:true,
                    maxDate:new Date(),
                    minDate:$("#searchForm [name=beginDate]").val()
                });
            });

            //显示柱状报表
            $(".barBtn").click(function () {
                //禁用缓存
                //keyword=xxx&beginDate=2018-01-01&endDate=
                $("#barModal").removeData("bs.modal").modal({
                    remote:"/chart/chartByBar.do?"+$("#searchForm").serialize()
                });
            });

            //显示饼状图
            $(".pieBtn").click(function () {
                //禁用缓存
                //keyword=xxx&beginDate=2018-01-01&endDate=
                $("#pieModal").removeData("bs.modal").modal({
                   remote:"/chart/chartByPie.do?"+$("#searchForm").serialize()
                });
            })
        });
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="customerReport"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">潜在客户报表管理</h1>
                </div>
            </div>
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/chart/list.do" method="post">
                <div class="form-group">
                    <label>销售员:</label>
                    <input type="text" class="form-control" name="keyword" style="width: 150px"
                           value="${(qo.keyword)!}" placeholder="请输入销售员姓名">
                </div>
                <div class="form-group">
                    <label>时间:</label>
                    <input type="text" class="form-control" name="beginDate" style="width: 150px"
                           value="${(qo.beginDate?string('yyyy-MM-dd'))!}"
                           placeholder="请输入开始时间"> ~
                    <input type="text" class="form-control" name="endDate" style="width: 150px"
                           value="${(qo.endDate?string('yyyy-MM-dd'))!}"
                           placeholder="请输入结束时间">
                </div>
                <div class="form-group">
                    <label>分组类型:</label>
                    <select class="form-control" name="groupType">
                        <option value="seller.name">销售员</option>
                        <option value="DATE_FORMAT(c.input_time, '%Y')">年份</option>
                        <option value="DATE_FORMAT(c.input_time, '%Y-%m')">月份</option>
                        <option value="DATE_FORMAT(c.input_time, '%Y-%m-%d')">日期</option>
                    </select>
                    <script>
                        $("#searchForm select[name='groupType']").val("${qo.groupType}");
                    </script>
                </div>
                <button  class="btn btn-primary">
                    <span class="glyphicon glyphicon-search"></span> 查询
                </button>
                <a role="button" class="btn btn-success barBtn">
                    <span class="glyphicon glyphicon-film"></span> 柱状图
                </a>
                <a role="button" class="btn btn-success pieBtn">
                    <span class="glyphicon glyphicon-film"></span> 饼状图
                </a>
            </form>

            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>分组类型</th>
                    <th>新增客户数</th>
                </tr>
                </thead>
                <#list charts as entity>
                <tr>
                    <td>${entity_index + 1}</td>
                    <td>${entity.groupByType}</td>
                    <td>${entity.number}</td>
                </tr>
                </#list>
            </table>
        </div>
    </div>
</div>

<#--柱状模态框-->
<div class="modal fade" id="barModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
<#--饼状模态框-->
<div class="modal fade" id="pieModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
</body>
</html>