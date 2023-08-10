let stompClient = null;

connect();

const datum = {
    data() {
        return {
            data: [
                {
                    "problem_name": "abc",
                    "your_code": "abc",
                    "limits": "RAM: 35Mo, Temps: 523ms",
                    "progress": "Validé"
                }
            ],
            columns: [
                {
                    field: 'problem_name',
                    label: 'Nom du problème',
                },
                {
                    field: 'your_code',
                    label: 'Votre code',
                },
                {
                    field: 'limits',
                    label: 'Limitations',
                },
                {
                    field: 'progress',
                    label: 'Avancement',
                }
            ]
        }
    }
}

const testApp = new Vue(datum);
testApp.$mount('#testApp');


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#result").html("");
}

function connect() {
    var socket = new SockJS("/api/tests/websocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/api/tests/return', function (result) {
            showTest(JSON.parse(result.body));
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
    stompClient.send("/app/results", {}, JSON.stringify({
        'id': $("#id").val(),
        'index': $("#index").val(),
        'answer': $("#answer").val(),
        'accepted': $("#accepted").val()
    }));
}

function showTest(message) {
    $("#result").append("<tr>" + "<td>" + message.id + "</td>" + "<td>" + message.index + "</td>" + "<td>" + message.answer + "</td>" + "<td>" + message.accepted + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendTest();
    });
});