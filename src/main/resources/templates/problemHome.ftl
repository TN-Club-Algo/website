<#import "./_layout.ftl" as layout /><#-- not an error -->

<title>Liste des Problèmes</title>
<meta charset="UTF-8">
<@layout.header>
    <div class="card mt-6">
        <header class="card-header">
            <p class="subtitle is-4 p-3">
                Tous les problèmes
            </p>
        </header>
        <div class="card-content">
            <div class="content">
                <table class="table is-fullwidth is-striped">
                    <thead>
                    <tr>
                        <th>Problème</th>
                        <th>Thèmes</th>
                        <th>Difficulté</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list keys as key>
                        <tr>
                            <td><a href="/problem/${problems[key].slug}">${problems[key].name}</a>
                                <#if email??>
                                    <#if problems[key].usersWhoSolved?seq_contains(email)>
                                        <span class="tag is-success">RÉUSSI</span>
                                    </#if>
                                </#if>
                            </td>
                            <td>
                                <#list problems[key].keywords as tag>
                                    <span class="tag">${tag}</span>
                                </#list>
                            </td>
                            <td>
                                ${problems[key].difficulty}
                            </td>
                            <td>${problems[key].getUsersWhoSolvedCount()} résolution
                                <#if problems[key].getUsersWhoSolvedCount() gt 1>
                                    s
                                </#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="card-footer">
            <div class="card-footer-item">
                <#if currentPage gt 1>
                    <a href="/problem?page=${currentPage - 1}" class="link-info">
                        Page précédente
                    </a>
                </#if>
                <span class="ml-4 mr-4">${currentPage} / ${pageCount}</span>
                <#if currentPage lt pageCount>
                    <a href="/problem?page=${currentPage + 1}" class="link-info">
                        Page suivante
                    </a>
                </#if>
            </div>
        </div>
    </div>
</@layout.header>