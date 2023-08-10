<#import "../_layout.ftl" as layout /><#-- not an error -->

<title>Test result</title>
<script src="../../static/js/jquery.min.js"></script>
<script src="../../static/js/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<meta charset="UTF-8">
<script src="../../static/js/app.js"></script>
<link rel="stylesheet" href="../../static/bulma.min.css">
<@layout.header>
    <h1 class="title is-1" style="text-align: center">Tests</h1>

    <div>
        <h2 class="subtitle">Test en cours</h2>
    </div>

    <div class="test" style="display: block;">
        <table class="table is-bordered is-striped is-narrow is-hoverable is-fullwidth" id="conversation">
            <thead class="testTableHead">
            <tr class="testTableHeaderRow">
                <th>Nom du problème</th>
                <th>Index du test</th>
                <th>Retour</th>
                <th>Résultat</th>
            </tr>
            </thead>
            <tbody class="testTable-Body" id="result"></tbody>
        </table>
    </div>
</@layout.header>