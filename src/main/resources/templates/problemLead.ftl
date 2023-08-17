<#import "./_layout.ftl" as layout /><#-- not an error -->

<title>CLassement</title>
<meta charset="UTF-8">
<@layout.header>
    <div style="width: 90%; margin: auto">
        <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap;">
            <h2 class="subtitle is-2">
                Classement: ${slug}
            </h2>
        </div>
    </div>
    <div>
        <div class="card">
            <div class="card-content">
<#--                <div style="padding: 0em 0em 2em 1em;" id="description">${contest.description}</div>-->
                <div rounded class="box">
                    <#--                    <div class="container">Problems :</div>-->
                    <table class="table is-fullwidth is-striped">
                        <thead>
                        <tr>
                            <th>Utilisateur</th>
                            <th>Score</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list  users as user>
                            <tr>
                                <td>
                                    ${user}
                                </td>
                                <td>
                                    ${problemLead.getScore(user)}
                                </td>
<#--                                <td>-->
<#--                                    ${problem["problem"].difficulty}-->
<#--                                </td>-->
<#--                                <td>${problem["problem"].getUsersWhoSolvedCount()} résolution-->
<#--                                    <#if problem["problem"].getUsersWhoSolvedCount() gt 1>-->
<#--                                        s-->
<#--                                    </#if>-->
<#--                                </td>-->
<#--                                <td>${problem["score"]}</td>-->
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
<#--    <#list users as user>-->
<#--        <div>${user}</div>-->

<#--    </#list>-->
</@layout.header>