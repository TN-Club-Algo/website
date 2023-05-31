var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#result").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/return/tests', function (result) {
            showTest(JSON.parse(result.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendTest() {
    stompClient.send("/app/results", {}, JSON.stringify({'id': $("#id").val(), 'index': $("#index").val(), 'answer': $("#answer").val(), 'accepted': $("#accepted").val()}));
}

function showTest(message) {
    $("#result").append("<tr>" + "<td>" + message.id + "</td>" + "<td>" + message.index + "</td>"+ "<td>" + message.answer + "</td>" + "<td>" + message.accepted + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendTest(); });
});