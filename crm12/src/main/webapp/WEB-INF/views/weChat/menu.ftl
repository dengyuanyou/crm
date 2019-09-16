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

                //如果json是视图菜单则弹出视图菜单的模态框
                if(json=="视图菜单"){

                    $("#ViewinputModal").modal("show");

                }
                //如果json是点击菜单则弹出点击菜单的模态框
                else if (json=="点击菜单"){

                    $("#ClickinputModal").modal("show");

                }
                //否则弹出组合菜单模态框
                else {
                    $("#GroupinputModal").modal("show");
                }

            });

            //提交视图菜单表单
            $(".btn-view").click(function () {

                //将表单的提交方式修改为ajax异步提交
                $("#btn-view").ajaxSubmit(function (data) {
                    if(data.success){
                        $.messager.alert("温馨提示","创建成功!,2S之后关闭");
                        setTimeout(function () {
                            window.location.reload();
                        },2000);
                    }
                })
            });

            //提交点击菜单表单
            $(".btn-click").click(function () {

                //将表单的提交方式修改为ajax异步提交
                $("#btn-click").ajaxSubmit(function (data) {
                    if(data.success){
                        $.messager.alert("温馨提示","创建成功!,2S之后关闭");
                        setTimeout(function () {
                            window.location.reload();
                        },2000);
                    }
                })
            });

            //提交组合菜单表单
            $(".btn-group").click(function () {

                //将表单的提交方式修改为ajax异步提交
                $("#btn-group").ajaxSubmit(function (data) {
                    if(data.success){
                        $.messager.alert("温馨提示","创建成功!,2S之后关闭");
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
            <#assign currentMenu="menuList"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">创建菜单</h1>
                </div>
                <form class="form-inline" id="searchForm" action="/wechat/menuList.do" method="post">

                </form>
            </div>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>菜单类型</th>
                    <th>操作</th>
                </tr>
                </thead>
                <#list MenuList as menu>
                <tr>
                <td>${menu_index+1}</td>
                <td>${menu}</td>
                <td>
                    <a class="btn btn-info btn-xs btn-input" href="javascript:;" data-json='${menu}'>
                        <span class="glyphicon glyphicon-pencil"></span>创建
                    </a>
                </td>
                </tr>
                </#list>
            </table>
        </div>
    </div>
</div>

<#--创建点击菜单模态框-->
<div class="modal fade" id="ClickinputModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">创建点击菜单</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/wechat/createClickMenu.do" method="post" id="btn-click">
                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">菜单名字：</label>
                        <div class="col-sm-6">
                            <select type="text" class="form-control" name="name" onchange="get()">
                                <option value="公司介绍">公司介绍</option>
                                <option value="关注我们">关注我们</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">菜单类型：</label>
                        <div class="col-sm-6">
                            <select id="menu_type" class="form-control" name="type" readonly="true">
                                <option value="click">点击菜单</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" id="click_key" style="visibility:hidden">
                        <label for="sn" class="col-sm-2 control-label">key：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="key">
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-click">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<#--创建视图菜单模态框-->
<div class="modal fade" id="ViewinputModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">创建视图菜单</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/wechat/createViewMenu.do" method="post" id="btn-view">
                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">菜单名字：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">菜单类型：</label>
                        <div class="col-sm-6">
                            <select id="menu_type" class="form-control" name="type" readonly="true">
                                <option value="view">视图</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" id="click_url">
                        <label for="sn" class="col-sm-2 control-label">跳转地址：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="url">
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-view">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<#--创建组合菜单模态框-->
<div class="modal fade" id="GroupinputModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">创建组合菜单</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/wechat/createGroupMenu.do" method="post" id="btn-group">
                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">菜单名字：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="viewButton.name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">菜单类型：</label>
                        <div class="col-sm-6">
                            <select id="menu_type" class="form-control" name="viewButton.type" readonly="true">
                                <option value="view">视图</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" id="click_url">
                        <label for="sn" class="col-sm-2 control-label">跳转地址：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="viewButton.url">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">二级菜单名字：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="button.name">
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-group">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
    //当点击客户姓名的时候触发查询客户电话的二级联动
    function get() {
        var request = $("select option:selected");
        var value = request.val();
        console.log(value);

        //如果选择的值为公司介绍将key的值设置1
        if(value=="公司介绍"){
            //设置key值为1
            $("#btn-click input[name=key]").val(1);
        }
        //如果选择的值为关注我们将key的值设置为2
        else {
            $("#btn-click input[name=key]").val(2);
        }

    }
</script>

</body>
</html>