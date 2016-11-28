var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        $.get("populate", function (data, status) {
            console.log(data);
            //start
        });

        stompClient.subscribe('/topic/start', function (response) {
            var parse = JSON.parse(response.body);
            console.log(parse);
            //start
        });
        stompClient.subscribe('/topic/stop', function () {
            //stop
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendStart() {
    //TODO: Convert to websocket
    $.get("start", function (data, status) {
    });
}
function sendPause() {
    //TODO: Convert to websocket
    $.get("stop", function (data, status) {
    });
}

//Button logic
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
    $("#start").click(function () {
        sendStart();
    });
    $("#pause").click(function () {
        sendPause();
    });
});