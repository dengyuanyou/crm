<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>叩丁狼权限管理系统</title>
    <#include "../common/header.ftl" >

    <script>
        $(function () {
            $(".transferBtn").click(function () {
                var json = $(this).data("json");
                if(json){
                    $("#transferForm [name='customer.id']").val(json.id)
                    $("#transferForm [name='customer.name']").val(json.name)
                    $("#transferForm [name='oldSeller.id']").val(json.sellerId)
                    $("#transferForm [name='oldSeller.name']").val(json.sellerName)
                }
                $("#transferModal").modal("show");
            });

            $(".transferSubmit").click(function () {
                //在提交之前将下拉框的禁用状态释放,将数据提交到服务器
                $("#transferForm [name='newSeller.id']").prop("disabled",false);
                $("#transferForm").submit();
            })

            //移交给我
            $(".transferMeBtn").click(function () {
                var json = $(this).data("json");
                if(json){
                    $("#transferForm [name='customer.id']").val(json.id)
                    $("#transferForm [name='customer.name']").val(json.name)
                    $("#transferForm [name='oldSeller.id']").val(json.sellerId)
                    $("#transferForm [name='oldSeller.name']").val(json.sellerName)
                    $("#transferForm [name='newSeller.id']").val(<@shiro.principal property="id"/>)
                    //disabled:表单元素的值不会提交
                    $("#transferForm [name='newSeller.id']").prop("disabled",true);
                }
                $("#transferModal").modal("show");
            });
        })
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="customer_pool"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">客户池管理</h1>
                </div>
            </div>
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/customer/poolList.do" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <div class="form-group">
                    <label>关键字:</label>
                    <input type="text" class="form-control" name="keyword"
                           value="${(qo.keyword)!}" placeholder="请输入姓名/电话">
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
                    <td>
                        <a role="button" class="btn btn-warning btn-xs transferMeBtn" data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-leaf"></span> 移交给我
                        </a>
                        <a role="button" class="btn btn-warning btn-xs transferBtn" data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-leaf"></span> 移交
                        </a>
                    </td>
                </tr>
                </#list>
            </table>
            <#include "../common/page.ftl" />
        </div>
    </div>
</div>

<#--移交模态框-->
<div id="transferModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customerTransferHistory/save.do" method="post" id="transferForm" style="margin: -3px 118px">
                    <input type="hidden" name="customer.id" id="customerTransferId"/>
                    <div class="form-group" >
                        <label for="name" class="col-sm-4 control-label">客户姓名：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control"  name="customer.name"   readonly >
                            <input type="hidden" class="form-control"  name="customer.id"  >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">旧营销人员：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control"  name="oldSeller.name" readonly >
                            <input type="hidden" class="form-control"    name="oldSeller.id"  >
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
                            <textarea type="text" class="form-control" id="reason" name="reason" cols="10" ></textarea>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary transferSubmit" >保存</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>