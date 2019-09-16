<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>客户关系管理系统</title>
    <#include "../common/header.ftl" >
    <script src="/js/My97DatePicker/WdatePicker.js"></script>

    <script>
        $(function () {
            //显示模态框
            $(".btn-input").click(function () {
                //先清空表单中的数据
                // $("#editForm input").val("");
                //DOM中的reset函数,可以将整个表单重置
                document.getElementById("editForm").reset();
                // $("#editForm :input").val("");
                //取出按钮上绑定的json数据
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
                $("#editModal").modal("show");
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
            })
        });
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="customer_potential"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">潜在客户管理</h1>
                </div>
            </div>
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/customer/potentialList.do" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <div class="form-group">
                    <label>关键字:</label>
                    <input type="text" class="form-control" name="keyword"
                           value="${(qo.keyword)!}" placeholder="请输入姓名/电话">
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
                <a role="button" class="btn btn-success btn-input">
                    <span class="glyphicon glyphicon-plus"></span> 添加
                </a>
            </form>

            <table class="table table-hover table-bordered">
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
                        <a class="btn btn-info btn-xs btn-input" href="javascript:;" data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-pencil"></span>编辑
                        </a>
                        <a role="button" class="btn btn-primary btn-xs btn-trace" data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-tag"></span> 跟进
                        </a>
                        <a role="button" class="btn btn-success btn-xs changeStatusBtn"
                           data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-plane"></span> 修改状态
                        </a>
                        <@shiro.hasPermission name="customerTransferHistory:save">
                        <a role="button" class="btn btn-warning btn-xs transferBtn" data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-leaf"></span> 移交
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
                <form class="form-horizontal" action="/customer/saveOrUpdate.do" method="post" id="editForm">
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
                        <label class="col-sm-3 control-label">客户工作：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="job.id">
                                <#list jobs as j>
                                    <option value="${j.id}">${j.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">客户来源：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="source.id" placeholder="请选择客户来源">
                                <#list sources as s>
                                    <option value="${s.id}">${s.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">销售人员：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="seller.id">
                               <#list sellers as e>
                                   <option value="${e.id}">${e.name}</option>
                               </#list>
                            </select>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-submit">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>


<#--跟进历史-->
<div class="modal fade" id="traceModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">跟进</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customerTraceHistory/saveOrUpdate.do" method="post"
                      id="traceForm">
                <#--新增,新增跟进历史没有ID,客户应该要有ID,不然就不知道当前是哪个客户的跟进历史-->
                    <input type="hidden" name="customer.id"/>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">客户姓名：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" readonly name="name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">跟进时间：</label>
                        <div class="col-lg-6 ">
                            <input type="text" class="form-control " name="traceTime" placeholder="请输入跟进时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">跟进记录：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" name="traceDetails"
                                   placeholder="请输入跟进记录"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">交流方式：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="traceType.id">
                                <#list types as c>
                                    <option value="${c.id}">${c.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">跟进结果：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="traceResult">
                                <option value="1">优</option>
                                <option value="2">中</option>
                                <option value="3">差</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">备注：</label>
                        <div class="col-lg-6">
                            <textarea type="text" class="form-control" name="remark"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary traceSubmit">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<#--移交模态框-->
<div id="transferModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customerTransferHistory/save.do" method="post" id="transferForm"
                      style="margin: -3px 118px">
                    <input type="hidden" name="customer.id" id="customerTransferId"/>
                    <div class="form-group">
                        <label for="name" class="col-sm-4 control-label">客户姓名：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="customer.name" readonly>
                            <input type="hidden" class="form-control" name="customer.id">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">旧营销人员：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="oldSeller.name" readonly>
                            <input type="hidden" class="form-control" name="oldSeller.id">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">新营销人员：</label>
                        <div class="col-sm-8">
                            <select name="newSeller.id" id="newSeller" class="form-control">
                                <#list sellers as e>
                                    <option value="${e.id}">${e.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">移交原因：</label>
                        <div class="col-sm-8">
                            <textarea type="text" class="form-control" id="reason" name="reason" cols="10"></textarea>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary transferSubmit">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<#--修改状态-->
<div class="modal fade" id="statusModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">修改客户状态</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customer/updateStatus.do" method="post" id="statusForm">
                    <input type="hidden" name="cid"/>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">客户名称：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" name="name"
                                   readonly placeholder="请输入客户名称"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">客户状态：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="status">
                                <option value="1">潜在客户</option>
                                <option value="2">移入客户池</option>
                                <option value="3">客户流失</option>
                                <option value="4">开发失败</option>
                                <option value="5">开发成功</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary statusSubmit">保存</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>