<#import "../_layout.ftl" as layout />

<title>Liste des Compétitions</title>
<script src="../../static/js/jquery.min.js"></script>
<script src="../../static/js/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<meta charset="UTF-8">
<script src="../../static/js/app.js"></script>
<@layout.header>
    <div id="app" class="card">
        <header class="card-header" style="position: relative;height: 54px;">
            <p class="subtitle is-4 p-3">
                Compétitons en cours
            </p>
            <div class="container">
                <b-button id="head_centered" tag="a"
                          href="/contest/submit"
                          target="_blank"
                          style="position: absolute;top: 50%;transform: translateY(-50%);right: 20px;">
                    Créer une Compétition
                </b-button>
            </div>
        </header>
        <div>
            <div class="card-content">
                <div class="content">
                    <table class="table is-fullwidth is-striped">
                        <thead>
                        <tr>
                            <th>Compétitions</th>
                            <th>Début</th>
                            <th>Durée</th>
                            <th>Inscrits</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list contests as contest>
                            <tr>
                                <td><a href="/contest/${contest.uuid}">${contest.contestName}</a></td>
                                <#--                                                <td>-->
                                <#--                                                    <#list contest[key].keywords as tag>-->
                                <#--                                                        <span class="tag">${tag}</span>-->
                                <#--                                                    </#list>-->
                                <#--                                                </td>-->
                                <td>
                                    <div>${contest.beginning}</div>
                                </td>
                                <td>${contest.end}</td>
                                <td>${contest.nbUser}
                                    <b-button @click="register">Register</b-button>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                        <#--                    <script>-->
                        <#--                        const app = new Vue()-->
                        <#--                        app.$mount('#app')-->
                        <#--                    </script>-->
                    </table>
                </div>
            </div>
        </div>
    </div>


    <div class="card mt-6">
        <header class="card-header">
            <p class="subtitle is-4 p-3">
                Toutes les Compétitions
            </p>
        </header>
        <div class="card-content">
            <div class="content">
                <table class="table is-fullwidth is-striped">
                    <thead>
                    <tr>
                        <th>Compétitions</th>
                        <th>Début</th>
                        <th>Durée</th>
                        <th>Inscrits</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#--                    <#list keys as key>-->
                    <#--                        <tr>-->
                    <#--                            <td><a href="/problem/${problems[key].slug}">${problems[key].name}</a></td>-->
                    <#--                            <td>-->
                    <#--                                <#list problems[key].keywords as tag>-->
                    <#--                                    <span class="tag">${tag}</span>-->
                    <#--                                </#list>-->
                    <#--                            </td>-->
                    <#--                            <td>-->
                    <#--                                <#if problems[key].difficulty gt 0>-->
                    <#--                                    <#list 1..problems[key].difficulty as _>-->
                    <#--                                        <span class="icon has-text-danger">-->
                    <#--                                            🟐-->
                    <#--                                        </span>-->
                    <#--                                    </#list>-->
                    <#--                                </#if>-->
                    <#--                                <#if 5 - problems[key].difficulty gt 0>-->
                    <#--                                    <#list 1..(5 - problems[key].difficulty) as _>-->
                    <#--                                        <span class="icon has-text-grey-light">-->
                    <#--                                            🟐-->
                    <#--                                        </span>-->
                    <#--                                    </#list>-->
                    <#--                                </#if>-->
                    <#--                            </td>-->
                    <#--                            <td>✗</td>-->
                    <#--                        </tr>-->
                    <#--                    </#list>-->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</@layout.header>
