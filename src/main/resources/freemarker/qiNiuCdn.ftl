<style>
    .tb td{
        border: 1px solid #aaa;
        text-align: center;
    }
    .tb{
        border-collapse:collapse;
    }
</style>
<table border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <td width="30">
            &nbsp;</td>
        <td>
            <table cellpadding="0" cellspacing="0" width="896">
                <tbody>
                <tr>
                    <td>
                        <img src="" height="43.4" width="185"/></td>
                </tr>
                <tr>
                    <td>
                        <div style="line-height: 26px; font-family: 微软雅黑; color: #fb9558; font-size: 22px; padding-top: 23px">
                            您好,PP云用户添加修改通知,详情如下: </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div style="line-height: 28px; margin-top: 32px; font-family: 微软雅黑; color: #555; font-size: 14px">
                            域名:${domainName}于${nowDate}${operate}${ruleNum}条${ruleName}规则<br>
                            请及时进行刷新操作
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div style="line-height: 28px; margin-top: 32px; font-family: 微软雅黑; color: #555; font-size: 14px">
                            Referer防盗链</div>
                        <div>
                            <table class="tb" width="896" style="border:1px solid #aaa;font-family: 微软雅黑;padding: 5px;color: #555;font-size: 14px">
                                <tr>
                                    <td>来源域名/URL</td>
                                    <td>防盗类型</td>
                                    <td>内容</td>
                                </tr>
                            <#if refererDOs??>
                                <#list refererDOs as refererDO>
                                    <tr>
                                        <td>${refererDO.referContent}</td>
                                        <td>${refererDO.typeContent}</td>
                                        <td>${refererDO.referPattern}</td>
                                    </tr>
                                </#list>
                            </#if>
                            </table>

                        </div>

                        <div style="line-height: 28px; margin-top: 32px; font-family: 微软雅黑; color: #555; font-size: 14px">
                            IP黑白名单</div>
                        <div>
                            <table class="tb" width="896" style="border:1px solid #aaa;font-family: 微软雅黑;padding: 5px;color: #555;font-size: 14px">
                                <tr>
                                    <td>黑名单IP/IP段</td>
                                    <td>防盗类型</td>
                                    <td>规则内容</td>
                                </tr>
                            <#if forbiddenDOs??>
                                <#list forbiddenDOs as forbiddenDO>
                                    <tr>
                                        <td>${forbiddenDO.forbiddenIps}</td>
                                        <td>${forbiddenDO.typeContent}</td>
                                        <td>${forbiddenDO.forbiddenPattern}</td>
                                    </tr>
                                </#list>
                            </#if>
                            </table>

                        </div>

                        <div style="line-height: 28px; margin-top: 32px; font-family: 微软雅黑; color: #555; font-size: 14px">缓存规则</div>
                        <div>
                            <table class="tb" width="896" style="border:1px solid #aaa;font-family: 微软雅黑;padding: 5px;color: #555;font-size: 14px">
                                <tr>
                                    <td>缓存类型</td>
                                    <td>缓存路径规则</td>
                                    <td>缓存时间</td>
                                    <td>缓存时间单位</td>
                                    <td>缓存优先级(数字越大，优先级越高)</td>
                                </tr>
                            <#if cacheRuleDOs??>
                                <#list cacheRuleDOs as cacheRuleDO>
                                    <tr>
                                        <td>${cacheRuleDO.type}</td>
                                        <td>${cacheRuleDO.rule}</td>
                                        <td>${cacheRuleDO.time}</td>
                                        <td>${cacheRuleDO.timeunit}</td>
                                        <td>${cacheRuleDO.priority}</td>
                                    </tr>
                                </#list>
                            </#if>
                            </table>

                        </div>

                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                </tr>

                </tbody>
            </table>
        </td>
        <td width="30">
            &nbsp;</td>
    </tr>
    </tbody>
</table>
<p>
    &nbsp;</p>