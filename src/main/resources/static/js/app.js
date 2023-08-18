const datum = {
    data() {
        return {
            data: [],
            total: 0,
            loading: false,
            sortField: 'date',
            sortOrder: 'desc',
            defaultSortOrder: 'desc',
            page: 1,
            perPage: 20
        }
    },
    methods: {
        async loadAsyncData() {
            const params = [
                `sort_by=${this.sortField}.${this.sortOrder}`,
                `page=${this.page}`
            ].join('&')

            this.loading = true
            const response = await axios.get(`/api/tests/all?${params}`); // Make the GET request
            const responseData = response.data;

            const finalData = [];

            if (responseData.error) {
                console.error('Error fetching data:', responseData.error);
                this.data = []
                this.total = 0
                this.loading = false
                return
            }
            if (responseData.inProgress) {
                // Process the in-progress test if present
                const inProgressTest = responseData.inProgress;

                let progress = ""
                if (inProgressTest.validated) {
                    progress = "Validé"
                } else {
                    progress = `${inProgressTest.progress}`
                }

                finalData.push({
                    problem_name: inProgressTest.problemName,
                    your_code: inProgressTest.codeURL,
                    progress: progress,
                    date: inProgressTest.timestamp
                });
            }
            if (responseData.completed) {
                // Process the completed tests if present
                const completedTests = responseData.completed;
                completedTests.forEach(completedTest => {
                    let progress = ""
                    if (completedTest.validated) {
                        progress = "Validé"
                    } else {
                        progress = `${completedTest.info}`
                    }

                    finalData.push({
                        problem_name: completedTest.problemName,
                        your_code: completedTest.codeURL,
                        progress: progress,
                        date: completedTest.timestamp
                    });
                });
            }

            this.total = responseData.total
            this.data = finalData;
            this.loading = false
        },
        onPageChange(page) {
            this.page = page
            this.loadAsyncData()
        },
        onSort(field, order) {
            this.sortField = field
            this.sortOrder = order
            this.loadAsyncData()
        },
    },
    mounted() {
        this.loadAsyncData()
    }
}

const testApp = new Vue(datum);
testApp.$mount('#testApp');

const socket = new SockJS("/ws")

const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
    // Subscribe to the destination
    stompClient.subscribe('/user/queue/return/tests', (message) => {
        const messageData = JSON.parse(message.body);

        // Handle the received message
        handleMessage(messageData);
    });
});

function handleMessage(messageData) {
    // TODO: handle messageData if it is to tell the place in the queue
    // Queue position

    // Test result
    const newData = {
        problem_name: messageData.problemName,
        your_code: messageData.codeURL,
        progress: messageData.validated ? "Validé" : `${messageData.progress}`,
        date: messageData.timestamp,
        validated: messageData.validated
    };

    const testAppData = testApp.data;

    if (testAppData.length > 0) {
        let index = testAppData.length - 1
        while (index >= 0 && testAppData[index].your_code !== newData.your_code) {
            index--
        }
        if(index <= 0) {
            testAppData.unshift(newData)
        } else {
            testAppData[index] = newData
        }
    } else {
        testAppData.unshift(newData); // Treat as the first index or no previous test
    }

    testApp.data = [...testAppData]; // Update the data property to trigger reactivity
}
