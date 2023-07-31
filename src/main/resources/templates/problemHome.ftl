<#import "./_layout.ftl" as layout /><#-- not an error -->

<title>Liste des Problèmes</title>
<script src="../../static/js/jquery.min.js"></script>
<script src="../../static/js/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<meta charset="UTF-8">
<script src="../../static/js/app.js"></script>
<link rel="stylesheet" href="../../static/bulma.min.css">
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
                        <th>Avancement</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list keys as key>
                        <tr>
                            <td><a href="/problem/${problems[key].slug}">${problems[key].name}</a></td>
                            <td>
                                <#list problems[key].keywords as tag>
                                    <span class="tag">${tag}</span>
                                </#list>
                            </td>
                            <td>
                                <#if problems[key].difficulty gt 0>
                                    <#list 1..problems[key].difficulty as _>
                                        <span class="icon has-text-danger">
                                            🟐
                                        </span>
                                    </#list>
                                </#if>
                                <#if 5 - problems[key].difficulty gt 0>
                                    <#list 1..(5 - problems[key].difficulty) as _>
                                        <span class="icon has-text-grey-light">
                                            🟐
                                        </span>
                                    </#list>
                                </#if>
                            </td>
                            <td>✗</td>
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