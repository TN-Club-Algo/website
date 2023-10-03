let layoutVue = new Vue({
    el: '#app',
    methods: {
        handleMessage(messageData) {
            this.$buefy.notification.open({
                message: messageData,
                type: "is-info is-light",
                indefinite: true,
            })
        }
    }
})

const socket = new SockJS("/ws")

const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
    // Subscribe to the destination
    stompClient.subscribe('/user/queue/return/notifications', (message) => {
        // Handle the received message
        layoutVue.handleMessage(message.body);
    });
});