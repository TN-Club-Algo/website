<#import "../_layout.ftl" as layout /><#-- not an error -->

<title>Test result</title>
<script src="../../static/js/jquery.min.js"></script>
<script src="../../static/js/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../static/bulma.min.css">
<@layout.header>
    <script type="module" src="../../static/js/app.js"></script>
    <h1 class="title is-1" style="text-align: center">Tests</h1>

    <div>
        <h2 class="subtitle">Test en cours</h2>
    </div>

    <div id="testApp">
        <template>
            <b-table :data="data" :columns="columns"></b-table>
        </template>
    </div>
</@layout.header>