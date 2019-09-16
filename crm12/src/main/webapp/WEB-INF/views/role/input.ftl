<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "../common/header.ftl">
    <script>
        function moveSelected(src, target) { // 样式
            $('.' + target).append($('.' + src + ' > option:selected'));
        }

        function moveAll(src, target) { // 样式
            $('.' + target).append($('.' + src + ' > option'));
        }

        $(function () {
            var $role; // 抽出来
            $('#admin').click(function () {
                console.log($(this).prop('checked'));
                if ($(this).prop('checked')) {
                    $role = $('#role').detach(); // 管理员就删除
                } else {
                    $(this).closest('div').after($role); // 不是管理员还原
                }
            });


            $('#btn_submit').click(function () {
                // 把右边下拉框中option 都加上 selected 属性
                $('.selfPermissions option').prop('selected', true);

                $('#editForm').ajaxForm(function (data) {
                    if (data.success) {
                        $.messager.alert("温馨提示", "操作成功,2S之后关闭");
                        setTimeout(function () {
                            window.location.href = "/role/list.do"
                        }, 2000);
                    }
                })

            });
            var selfPermissionsIds = $.map($('.selfPermissions option'), function (option, index) { // 数组对象.map
                return $(option).val(); // 函数返回值是  selfRoleIds 数组 里面的元素
            });

            $.each($('.allPermissions option'), function (index, option) {
                var value = $(option).val();

                var index = $.inArray(value, selfPermissionsIds); // 若存在返回索引值，若不存在，返回 -1
                if (index >= 0) { // 若存在，删除 option
                    $(option).remove();
                }
            });


            // 注意放置的位置，要在角色去重后面
            // 针对编辑超管处理
            if ($('#admin').prop('checked')) {
                $role = $('#role').detach();
            }

        });

    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="role" />
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">角色编辑</h1>
                </div>
            </div>
            <div class="row col-sm-10">
                <form class="form-horizontal" action="/role/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id" value="${(role.id)!}">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name" value="${(role.name)!}"
                                   placeholder="请输入部门名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">编码：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sn" name="sn" value="${(role.sn)!}"
                                   placeholder="请输入部门编码">
                        </div>
                    </div>
                    <div class="form-group" id="role">
                        <div>
                            <label for="role" class="control-label" style="margin-left: 60px">权限：</label>
                        </div>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-sm-4 col-sm-offset-1">
                                <select multiple class="form-control allPermissions" size="15">
                                    <#list permissions as permission>
                                        <option value="${permission.id}">${permission.name}</option>
                                    </#list>
                                </select>
                            </div>

                            <div class="col-sm-2" style="margin-top: 60px;" align="center">
                                <div>
                                    <a type="button" class="btn btn-info  " style="margin-top: 10px"
                                       onclick="moveSelected('allPermissions', 'selfPermissions')">&nbsp;&gt;&nbsp;</a>
                                    <br>
                                    <a type="button" class="btn btn-info " style="margin-top: 10px"
                                       onclick="moveSelected('selfPermissions', 'allPermissions')">&nbsp;&lt;&nbsp;</a>
                                    <br>
                                    <a type="button" class="btn btn-info " style="margin-top: 10px"
                                       onclick="moveAll('allPermissions', 'selfPermissions')">&gt;&gt;</a>
                                    <br>
                                    <a type="button" class="btn btn-info " style="margin-top: 10px"
                                       onclick="moveAll('selfPermissions', 'allPermissions')">&lt;&lt;</a>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <select multiple class="form-control selfPermissions" size="15" name="ids">
                                    <#--<#if role?? && (role.permissions)??>-->
                                        <#--<#list role.permissions as permission>-->
                                            <#--<option value="${permission.id}">${permission.name}</option>-->
                                        <#--</#list>-->
                                    <#--</#if>-->
                                    <#list (role.permissions)! as permission>
                                        <option value="${permission.id}">${permission.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <button id="btn_submit" class="btn btn-default">保存</button>
                            <button type="reset" class="btn btn-default">重置</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>