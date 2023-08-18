<#import "./_layout.ftl" as layout /><#-- not an error -->

<title>Classement</title>
<meta charset="UTF-8">
<@layout.header>
    <div style="width: 90%; margin: auto">
        <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap;">
            <a href="/contest/${slug}">
                <h2 class="subtitle is-2">
                    Classement de la compétition : ${name}
                </h2>
            </a>
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
                        <#list users as user>
                            <tr>
                                <td>
                                    ${nicknames[user]}
                                </td>
                                <td>
                                    ${leaderboard[user]}
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</@layout.header>