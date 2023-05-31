<#import "/_layout.ftl" as layout /><#-- not an error -->

<title>Test result</title>
<script src="../../static/js/jquery.min.js"></script>
<script src="../../static/js/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<meta charset="UTF-8">
<script src="../../static/js/app.js"></script>
<link rel="stylesheet" href="../../static/bulma.min.css">
<@layout.header>
    <h1 class="title is-1" style="text-align: center">Tests</h1>
        <table class="table is-bordered is-striped is-narrow is-hoverable is-fullwidth" id="conversation">
            <thead class="testTableHead">
            <tr class="testTableHeaderRow">
                <th>Nom du problème</th>
                <th>Lien</th>
            </tr>
            </thead>
            <tbody class="testTable-Body" id="result"></tbody>
                <#list keys as key>
                    <tr>

                        <td>  ${problems[key].name}</td>
                        <td>
                            <a href="/problem/${key}">${key}</a>
                        </td>
                    </tr>
                </#list>
        </table>
    </div>
</@layout.header>