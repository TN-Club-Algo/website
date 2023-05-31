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
    <div class="test" style="display: block;">
        <div class="row">
            <div class="col-md-6">
                <form class="form-inline">
                    <div class="form-group">
                        <label for="connect">WebSocket connection:</label>
                        <button id="connect" class="btn btn-default" type="submit">Connect</button>
                        <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
                        </button>
                    </div>
                </form>
            </div>
            <div class="col-md-6">
                <form class="form-inline">
                    <div class="form-group">
                        <label for="id">id</label>
                        <input type="text" id="id" class="form-control" placeholder="id">
                        <label for="index">index</label>
                        <input type="text" id="index" class="form-control" placeholder="index">
                        <label for="answer">answer</label>
                        <input type="text" id="answer" class="form-control" placeholder="answer">
                        <label for="accepted">accepted</label>
                        <input type="text" id="accepted" class="form-control" placeholder="accepted">
                    </div>
                    <button id="send" class="btn btn-default" type="submit">Send</button>
                </form>
            </div>
        </div>

        <table class="table is-bordered is-striped is-narrow is-hoverable is-fullwidth" id="conversation">
            <thead class="testTableHead">
            <tr class="testTableHeaderRow">
                <th>Id test</th>
                <th>Index test</th>
                <th>Answer</th>
                <th>Accepted</th>
            </tr>
            </thead>
            <tbody class="testTable-Body" id="result"></tbody>
        </table>
    </div>
</@layout.header>