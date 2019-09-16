<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>客户关系管理系统</title>
    <#include "../common/header.ftl" >
    <script type="text/javascript" src="/jquery/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/jquery/plugins/jquery.bootstrap.min.js"></script>
    <script>

        $(function () {
            //禁用表单的序列化功能,去除数组名称后面的[]
            jQuery.ajaxSettings.traditional = true;

            //全选或全不选
            $(".checkAll").change(function () {
                $(".check").prop("checked", this.checked);
            });

            //批量删除
            $(".batchDelete").click(function () {
                //如果用户没有选择要删除的数据,给出提示
                var size = $(".check:checked").size();
                if (size == 0) {
                    $.messager.alert("温馨提示", "亲,请选择要删除的数据");
                    return;
                }
                var ids = $.map($(".check:checked"), function (checkbox) {
                    return $(checkbox).data("id");
                })
                console.log(ids);
                $.messager.confirm("温馨提示", "您确定要删除选中的数据吗?", function () {
                    //发送请求,执行批量删除
                    $.get("/employee/batchDelete.do", {ids: ids}, function (data) {
                        successAlert(data);
                    })
                })
            });

            //导出
            $(".btn-export").click(function () {
                //提交高级查询的表单,但是action最开始是/employee/list.do
                //而我们需要访问/employee/exportExcel.do
                //所以将表单的action修改为/employee/exportExcel.do,再提交表单
                //最后记得将action修改回来
                $("#searchForm").prop("action","/employee/exportExcel.do");
                $("#searchForm").submit();


            });

            //导入的模态框
            $(".btn-import").click(function () {
                $("#importModal").modal("show");
            });

            $(".submitBtn").click(function () {
                $("#editForm").submit();
            });

        })
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="employee"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">员工管理</h1>
                </div>

                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/employee/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <div class="form-group">
                        <label for="keyword">关键字:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!}"
                               placeholder="请输入姓名/邮箱">
                    </div>
                    <div class="form-group">
                        <label for="dept">部门:</label>
                        <select class="form-control" id="dept" name="deptId">
                            <option value="-1">全部</option>
                            <#list depts as d>
                                <option value="${d.id}">${d.name}</option>
                            </#list>
                        </select>
                        <script>
                            $("#dept option[value='${(qo.deptId)!}']").prop("selected", true);
                        </script>
                    </div>
                    <input type="submit" id="btn_query" class="btn btn-default" value="查询">
                    <a href="/employee/input.do" class="btn btn-success">添加</a>
                    <@shiro.hasPermission name="employee:batchDelete">
                        <a role="button" class="btn btn-danger batchDelete">
                            <span class="glyphicon glyphicon-trash"></span> 批量删除
                        </a>
                    </@shiro.hasPermission>

                    <a href="javascript:;" target="_blank" class="btn btn-warning btn-export">
                        <span class="glyphicon glyphicon-export"></span> 导出
                    </a>
                    <a role="button" class="btn btn-warning btn-import">
                        <span class="glyphicon glyphicon-import"></span> 导入
                    </a>
                </form>
            </div>

            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th><input type="checkbox" class="checkAll"/></th>
                    <th>编号</th>
                    <th>名称</th>
                    <th>email</th>
                    <th>年龄</th>
                    <th>部门</th>
                    <th>操作</th>
                </tr>
                </thead>
                <#list pageInfo.list as entity>
                    <tr>
                        <td><input type="checkbox" class="check" data-id="${entity.id}"/></td>
                        <td>${entity_index+1}</td>
                        <td>${entity.name}</td>
                        <td>${entity.email}</td>
                        <td>${entity.age}</td>
                        <td>${(entity.dept.name)!}</td>
                        <td>
                            <a class="btn btn-info btn-xs" href="/employee/input.do?id=${entity.id}">
                                <span class="glyphicon glyphicon-pencil"></span>编辑
                            </a>
                            <@shiro.hasPermission name="employee:delete">
                                <a data-url="/employee/delete.do?id=${entity.id}" class="btn btn-danger btn-xs btn-delete">
                                    <span class="glyphicon glyphicon-trash"></span>删除
                                </a>
                            </@shiro.hasPermission>
                        </td>
                    </tr>
                </#list>
            </table>
            <#include "../common/page.ftl" />
        </div>
    </div>
</div>
<!-- 导入员工 -->
<div class="modal fade" id="importModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">导入员工</h4>
            </div>
            <div class="modal-body">
                <!--填充编辑界面-->
                <form id="editForm" class="form-horizontal" action="/employee/importExcel.do"
                      enctype="multipart/form-data" method="post">
                    <input type="hidden" name="id"/>
                    <div class="form-group" >
                        <label for="name" class="col-lg-4 control-label">上传文件：</label>
                        <div class="col-lg-6">
                            <input type="file" name="xls" accept="application/vnd.ms-excel" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-lg-4 control-label">参考模板：</label>
                        <div class="col-lg-6">
                            <a href="/template/employee.xls" class="btn btn-success btn-block">
                                <span class="glyphicon glyphicon-download"></span> 下载模板
                            </a>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary submitBtn">保存</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>