const socket = new SockJS("/ws")

const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
    // Subscribe to the destination
    stompClient.subscribe('/user/queue/return/notifications', (message) => {
        const messageData = JSON.parse(message.body);

        // Handle the received message
        handleMessage(messageData);
    });
});

function handleMessage(messageData) {
     this.$buefy.notification.open({
         message: messageData,
         type: "is-info",
     })
}