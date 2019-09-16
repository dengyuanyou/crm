<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>叩丁狼权限管理系统</title>
    <#include "../common/header.ftl">
    <script>
        $(function () {
            //加载权限
            $(".btn-load").click(function () {
                $.messager.confirm("温馨提示","亲,该操作比较耗时,您确定要执行该操作吗?",function () {
                    //发送ajax请求,执行权限加载
                    $.get("/permission/load.do",function (data) {
                        successAlert(data);
                    })
                })
            })
        })
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="permission"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">权限管理</h1>
                </div>
            </div>
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/permission/list.do" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <input type="button" class="btn btn-success btn-load" value="加载权限">
            </form>

            <table class="table table-striped table-hover" >
                <thead>
                <tr>
                    <th>编号</th>
                    <th>名称</th>
                    <th>权限表达式</th>
                    <th>操作</th>
                </tr>
                </thead>
                <#list pageInfo.list as entity>
                    <tr>
                        <td>${entity_index+1}</td>
                        <td>${entity.name}</td>
                        <td>${entity.expression}</td>
                        <td>
                            <a data-url="/permission/delete.do?id=${entity.id}" class="btn btn-danger btn-xs btn-delete" >
                                <span class="glyphicon glyphicon-trash"></span>删除
                            </a>
                        </td>
                    </tr>
                </#list>
            </table>
            <#include "../common/page.ftl"/>
        </div>
    </div>
</div>
</body>
</html>