<#import "../_layout.ftl" as layout />
<!DOCTYPE html>
<meta charset="UTF-8">
<@layout.header>
    <div style="width: 90%; margin: auto">
        <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap;">
            <h2 class="subtitle is-2">
                Compétition: ${contest.name}
            </h2>
            <a href="/contest/leaderboard/${contest.uuid}">
                <div class="button">
                    Classement
                </div>
            </a>
        </div>
    </div>
    <div>
        <div class="card">
            <div class="card-content">
                <div style="padding: 0em 0em 2em 1em;" id="description">${contest.description}</div>
                <div rounded class="box">
<#--                    <div class="container">Problems :</div>-->
                    <table class="table is-fullwidth is-striped">
                        <thead>
                        <tr>
                            <th>Problème</th>
                            <th>Thèmes</th>
                            <th>Difficulté</th>
                            <th></th>
                            <th>Score</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list allProblem as problem>
                            <tr>
                                <td><a href="/problem/${problem["problem"].slug}">${problem["problem"].name}</a>
                                    <#if email??>
                                        <#if problem["problem"].usersWhoSolved?seq_contains(email)>
                                            <span class="tag is-success">RÉUSSI</span>
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#list problem["problem"].keywords as tag>
                                        <span class="tag">${tag}</span>
                                    </#list>
                                </td>
                                <td>
                                    ${problem["problem"].difficulty}
                                </td>
                                <td>${problem["problem"].getUsersWhoSolvedCount()} résolution<#if problem["problem"].getUsersWhoSolvedCount() gt 1>s</#if>
                                </td>
                                <td>${problem["score"]}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</@layout.header>