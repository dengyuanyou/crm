<ul id="menu" class="list-group">

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#systemMgr">
            <span>系统管理</span>
        </a>
        <ul class="in" id="systemMgr">
            <li class="department"><a href="/department/list.do">部门管理</a></li>
            <li class="employee"><a href="/employee/list.do">员工管理</a></li>
            <li class="permission"><a href="/permission/list.do">权限管理</a></li>
            <li class="role"><a href="/role/list.do">角色管理</a></li>
        </ul>
    </li>

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#dataMgr">
            <span>数据管理</span>
        </a>
        <ul class="in" id="dataMgr">
            <li class="systemDictionary"><a href="/systemDictionary/list.do">字典目录</a></li>
            <li class="systemDictionaryItem"><a href="/systemDictionaryItem/list.do">字典明细</a></li>
        </ul>
    </li>

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#customerMgr">
            <span>潜在客户管理</span>
        </a>
        <ul class="in" id="customerMgr">
            <li class="customer_list"><a href="/customer/list.do">客户列表</a></li>
            <li class="customer_potential"><a href="/customer/potentialList.do">潜在客户</a></li>
            <li class="customer_pool"><a href="/customer/poolList.do">客户池</a></li>
            <li class="customer_failList"><a href="/customer/failList.do">失败客户</a></li>
            <li class="customerTraceHistory"><a href="/customerTraceHistory/list.do">跟进历史</a></li>
            <li class="customerTransferHistory"><a href="/customerTransferHistory/list.do">移交历史</a></li>
        </ul>
    </li>

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#CustomerReport">
            <span>报表统计</span>
        </a>
        <ul class="in" id="CustomerReport">
            <li class="customerReport"><a href="/chart/list.do">潜在客户报表</a></li>
        </ul>
    </li>

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#ContractManager">
            <span>合同管理</span>
        </a>
        <ul class="in" id="ContractManager">
            <#--<li class="order_list"><a href="/order/list.do">合同订单</a></li>-->
            <@shiro.hasPermission name="contract:list">
            <li class="contract_list"><a href="/contract/list.do">合同列表</a></li>
            </@shiro.hasPermission>
            <li class="mycontract_list"><a href="/contract/list.do">我的合同</a></li>
        </ul>
    </li>

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#ServerForCustomer">
            <span>服务管理</span>
        </a>
        <ul class="in" id="ServerForCustomer">
            <@shiro.hasPermission name="weChat:distribute">
            <li class="distribute"><a href="/wechat/distribute.do">服务分配</a></li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="weChat:list">
            <li class="wechat"><a href="/wechat/list.do">消息列表</a></li>
            </@shiro.hasPermission>
            <li class="myMessage"><a href="/wechat/myMessage.do">我的消息列表</a></li>
        </ul>
    </li>

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#weChat">
            <span>微信管理</span>
        </a>
        <ul class="in" id="weChat">
            <li class="menuList"><a href="/wechat/menuList.do">创建菜单</a></li>
        </ul>
    </li>

<!--设置菜单回显-->
<script>
    $(".in li.${currentMenu}").addClass("active");
</script>