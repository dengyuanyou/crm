<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>客户关系管理系统</title>
    <#include "../common/header.ftl" >

    <script>
        $(function () {
            //显示模态框
            $(".btn-input").click(function () {
                //先清空表单中的数据
                $("#editForm input").val("");

                //取出按钮上绑定的json数据
                var json = $(this).data("json");
                if (json) {
                    //回显数据
                    $("input[name=id]").val(json.id);
                    $("input[name=sequence]").val(json.sequence);
                    $("input[name=title]").val(json.title);
                    $("input[name=parentTitle]").val(json.parentTitle);
                }
                $("#inputModal").modal("show");
            });

            //提交表单
            $(".btn-submit").click(function () {
                //将表单的提交方式修改为ajax异步提交
                $("#editForm").ajaxSubmit(function (data) {
                    if (data.success) {
                        $.messager.alert("温馨提示", "操作成功,2S之后关闭");
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    }
                })
            });
            //添加模态框的数据显示
            $(".btn-add").click(function () {
                //先清空表单中的数据
                $("#editForm input").val("");

                //取出按钮上绑定的json数据
                var json = $(this).data("json");
                if (json) {
                    //回显数据
                    $("input[name=parentTitle]").val(json.title);
                    $("input[name='parent.id']").val(json.id);
                }
                $("#inputModal").modal("show");
            })


        });
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="systemDictionaryItem"/>
            <#include "../common/menu.ftl">
        </div>

        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">字典明细管理</h1>
                </div>
            </div>

            <div class="row">
            <#--目录-->
                <div class="col-sm-3">
                    <ul id="menu" class="list-group">
                        <li class="list-group-item">
                            <a href="#" data-toggle="collapse" data-target="#systemMgr">
                                <span>字典目录</span>
                            </a>
                            <ul class="in" id="systemMgr">
                                <#list dictinaries as disctinary>
                                    <li class="department" id="${disctinary.id}"><a href="/systemDictionaryItem/list.do?parentId=${disctinary.id}">${disctinary.title}</a></li>
                                </#list>
                            </ul>
                        </li>
                    </ul>
                </div>
                <script>
                    //将li的id为qo中parentId相同的元素添加一个class="active"属性
                    $("ul li[id=${qo.parentId}]").addClass("active");
                </script>
            <#--明细-->

                <div class="col-sm-9">
                    <!--高级查询--->
                    <form class="form-inline" id="searchForm" action="/systemDictionaryItem/list.do" method="post">
                        <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <input type="hidden" name="parentId"  value="${qo.parentId}">
                        <a href="javascript:;" class="btn btn-success btn-add" data-json='${(parent.jsonString)!}'>添加明细</a>
                    </form>

                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>字典标题</th>
                            <th>字典序列</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                <#list pageInfo.list as entity>
                <tr>
                    <td>${entity_index + 1}</td>
                    <td>${entity.title}</td>
                    <td>${entity.sequence}</td>
                    <td>
                        <a class="btn btn-info btn-xs btn-input" href="javascript:;" data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-pencil"></span>编辑
                        <a href="javascript:;" data-url="/systemDictionaryItem/delete.do?id=${entity.id}"
                           class="btn btn-danger btn-xs btn-delete">
                            <span class="glyphicon glyphicon-trash"></span>删除
                        </a>
                    </td>
                </tr>
                </#list>
                    </table>
            <#include "../common/page.ftl" />
                </div>
            </div>


        </div>
    </div>
</div>

<#--编辑模态框-->
<div class="modal fade" id="inputModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/systemDictionaryItem/saveOrUpdate.do" method="post"
                      id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group">
                        <label for="parentTitle" class="col-sm-2 control-label">目录：</label>
                        <div class="col-sm-6">
                            <#--readonly:只读,提交表达会同时将其提交过去-->
                            <#--disabled:只读,提交表达不会同时将其提交过去-->
                            <input disabled="disabled" type="text" class="form-control" id="parentTitle" name="parentTitle"/>
                            <input type="hidden" class="form-control" id="parentId" name="parent.id"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="title" class="col-sm-2 control-label">标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title"
                                   placeholder="请输入字典明细标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sequence" class="col-sm-2 control-label">序列：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sequence" name="sequence"
                                   placeholder="请输入字典明细序列">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-submit">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>