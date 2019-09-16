<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>叩丁狼权限管理系统</title>
    <#include "../common/header.ftl" >
    <script src="/js/My97DatePicker/WdatePicker.js"></script>

    <script>
        $(function () {

            //点击我要跟进的状态
            $(".transferMeBtn").click(function () {

                document.getElementById("editForm").reset();

                //获取按钮上的客户信息
                var json = $(this).data("json");
                console.log(json);
                if (json) {
                    //回显数据
                    $("input[name=id]").val(json.id);
                    $("input[name=name]").val(json.name);
                    $("input[name=age]").val(json.age);
                    $("#gender").val(json.gender);
                    $("input[name=tel]").val(json.tel);
                    $("input[name=qq]").val(json.qq);
                    $("[name='source.id']").val(json.sourceId);
                    $("[name='job.id']").val(json.jobId);
                    $("[name='seller.id']").val(json.sellerId);
                }

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

            //跟进模态框弹出
            $(".btn-trace").click(function () {
                var json = $(this).data("json");
                if (json) {
                    $("#traceForm [name=name]").val(json.name);
                    $("#traceForm [name='customer.id']").val(json.id);
                }
                $("#traceModal").modal("show");
            });

            // 为跟进时间绑定点击事件
            $("#traceForm [name=traceTime]").click(function () {
                WdatePicker({
                    readOnly: true,
                    maxDate: new Date()
                })
            });

            //提交跟进表单
            $(".traceSubmit").click(function () {
                $("#traceForm").submit();
            });

            $(".transferBtn").click(function () {
                var json = $(this).data("json");
                if (json) {
                    $("#transferForm [name='customer.id']").val(json.id)
                    $("#transferForm [name='customer.name']").val(json.name)
                    $("#transferForm [name='oldSeller.id']").val(json.sellerId)
                    $("#transferForm [name='oldSeller.name']").val(json.sellerName)
                }
                $("#transferModal").modal("show");
            });

            $(".transferSubmit").click(function () {
                //在提交之前将下拉框的禁用状态释放,将数据提交到服务器
                $("#transferForm [name='newSeller.id']").prop("disabled", false);
                $("#transferForm").submit();
            });
            //修改状态
            $(".changeStatusBtn").click(function () {
                var json = $(this).data("json");
                if (json) {
                    $("#statusForm [name=cid]").val(json.id);
                    $("#statusForm [name=name]").val(json.name);
                    $("#statusModal").modal("show");
                }
            });
            $(".statusSubmit").click(function () {
                $("#statusForm").submit();
            });


        });
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="customer_failList"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">失败客户</h1>
                </div>
            </div>
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/customer/failList.do" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <div class="form-group">
                    <label>关键字:</label>
                    <input type="text" class="form-control" name="keyword" value="${(qo.keyword)!}" placeholder="请输入姓名/电话">
                </div>
                <div class="form-group">
                    <label for="dept">营销人员:</label>
                    <select class="form-control" id="status" name="sellerId">
                        <option value="-1">全部</option>
                        <#list sellers as seller>
                            <option value="${seller.id}">${seller.name}</option>
                        </#list>
                    </select>
                    <script>
                        $("option[value='${qo.sellerId}']").prop("selected", true);
                    </script>
                </div>
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-search"></span> 查询
                </button>
            </form>

            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>年龄</th>
                    <th>电话</th>
                    <th>QQ</th>
                    <th>职业</th>
                    <th>来源</th>
                    <th>营销人员</th>
                    <th>操作</th>
                </tr>
                </thead>
                <#list pageInfo.list as entity>
                <tr>
                    <td>${entity_index + 1}</td>
                    <td>${entity.name}</td>
                    <td>${entity.genderName}</td>
                    <td>${entity.age}</td>
                    <td>${entity.tel}</td>
                    <td>${entity.qq}</td>
                    <td>${entity.job.title}</td>
                    <td>${entity.source.title}</td>
                    <td>${entity.seller.name}</td>
                    <td>
                        <a role="button" class="btn btn-warning btn-xs transferMeBtn" data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-leaf"></span>我要跟进
                        </a>
                    </td>
                </tr>
                </#list>
            </table>
            <#include "../common/page.ftl" />
        </div>
    </div>
</div>

<#--客户编辑模态框-->
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title inputTitle">客户编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customer/failTransfer.do" method="post" id="editForm">
                    <input type="hidden" value="" name="id">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">客户名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="name"
                                   placeholder="请输入客户姓名"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">客户年龄：</label>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" name="age"
                                   placeholder="请输入客户年龄"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">客户性别：</label>
                        <div class="col-sm-6">
                            <select id="gender" class="form-control" name="gender">
                                <option value="1">男</option>
                                <option value="0">女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">客户电话：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="tel"
                                   placeholder="请输入客户电话"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">客户QQ：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="qq"
                                   placeholder="请输入客户QQ"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">工作：</label>
                            <div class="col-sm-6">
                                <input type="number" class="form-control" name="job.id"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">客户来源：</label>
                            <div class="col-sm-6">
                                <input type="number" class="form-control" name="source.id"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">销售来源：</label>
                            <div class="col-sm-6">
                                <input type="number" class="form-control" name="seller.id"/>
                            </div>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>