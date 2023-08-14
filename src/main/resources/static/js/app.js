const datum = {
    async created() {
        try {
            const response = await axios.get('/api/tests/all'); // Make the GET request
            const responseData = response.data;

            const data = [];

            if (responseData.error) {
                console.error('Error fetching data:', responseData.error);
                return;
            }

            if (responseData.inProgress) {
                // Process the in-progress test if present
                const inProgressTest = responseData.inProgress;
                data.push({
                    problem_name: inProgressTest.problemSlug,
                    your_code: inProgressTest.codeURL,
                    limits: `RAM: ${inProgressTest.memoryUsed}, Temps: ${inProgressTest.timeElapsed}`,
                    progress: inProgressTest.progress,
                    validated: inProgressTest.validated
                });
            }

            if (responseData.completed) {
                // Process the completed tests if present
                const completedTests = responseData.completed;
                completedTests.forEach(completedTest => {
                    data.push({
                        problem_name: completedTest.problemSlug,
                        your_code: completedTest.codeURL,
                        limits: `RAM: ${completedTest.memoryUsed}, Temps: ${completedTest.timeElapsed}`,
                        progress: completedTest.progress,
                        validated: completedTest.validated
                    });
                });
            }

            this.data = data;
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    },
    data() {
        return {
            data: [],
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
                },
                {
                    field: 'validated',
                    label: 'Validé',
                }
            ]
        }
    }
};

const testApp = new Vue(datum);
testApp.$mount('#testApp');

const socket = new SockJS("/ws")

const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
    console.log('WebSocket connection established.');

    // Subscribe to the destination
    stompClient.subscribe('/user/queue/return/tests', (message) => {
        const messageData = JSON.parse(message.body);
        console.log('Received message:', messageData);

        // Handle the received message
        handleMessage(messageData);
    });
});

function handleMessage(messageData) {
    const newData = {
        problem_name: messageData.problemSlug,
        your_code: messageData.codeURL,
        limits: `RAM: ${messageData.memoryUsed}, Temps: ${messageData.timeElapsed}`,
        progress: messageData.progress,
        validated: messageData.validated
    };

    const testAppData = testApp.data;

    if (messageData.currentIndex === -1) {
        if (testAppData.length > 0) {
            // check if last test has the same uuid
            if (testAppData[testAppData.length - 1].your_code === newData.your_code) {
                // Edit the last test
                testAppData[testAppData.length - 1] = newData;
                return;
            } else {
                // Add the new test
                testAppData.unshift(newData);
            }
        }
        // Edit the previous test and move on
        testAppData[testAppData.length - 1] = newData;
    } else if (messageData.currentIndex > 0 && testAppData.length > 0) {
        const indexToEdit = Math.min(messageData.currentIndex - 1, testAppData.length - 1);
        testAppData[indexToEdit] = newData; // Edit the previous test
    } else {
        testAppData.unshift(newData); // Treat as the first index or no previous test
    }

    testApp.data = [...testAppData]; // Update the data property to trigger reactivity
}
