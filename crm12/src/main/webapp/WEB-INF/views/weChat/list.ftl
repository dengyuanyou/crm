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
                console.log(json);

                if (json) {
                    //回显数据
                    $("input[name=id]").val(json.id)
                    $("input[name=openid]").val(json.openid)
                    $("input[name=status]").val(json.status);
                    $("#message").val(json.content);
                }
                $("#inputModal").modal("show");
            });

            //提交表单
            $(".btn-submit").click(function () {
                //将表单的提交方式修改为ajax异步提交
                $("#editForm").ajaxSubmit(function (data) {
                    if(data.success){
                        $.messager.alert("温馨提示","回复成功!,2S之后关闭");
                        setTimeout(function () {
                            window.location.reload();
                        },2000);
                    }
                })
            });


        });
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="wechat"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">消息列表</h1>
                </div>
                <form class="form-inline" id="searchForm" action="/wechat/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                </form>
            </div>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>客户id</th>
                    <th>消息内容</th>
                    <th>回复内容</th>
                    <th>消息类型</th>
                    <th>发送时间</th>
                    <th>消息状态</th>
                    <th>跟踪员工</th>
                </tr>
                </thead>
                <#list pageInfo.list as entity>
                <tr>
                    <td>${entity_index + 1}</td>
                    <td>${entity.openid}</td>
                    <td>${entity.content!}</td>
                    <td>${entity.reply!}</td>
                    <td>${entity.type}</td>
                    <td>${entity.createTime?string("yyyy-MM-dd")}</td>
                    <td>
                        <#if entity.status == 1>
                            已回复
                        <#else>
                            未回复
                        </#if>
                    </td>
                    <td>${(entity.employee.name)!}</td>
                </tr>
                </#list>
            </table>
            <#include "../common/page.ftl" />
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
                <h4 class="modal-title">回复内容</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/wechat/sendMessage.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <input type="hidden" name="openid">
                    <input type="hidden" name="status">
                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">客户消息：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="message">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">回复内容：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="content" name="content">
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