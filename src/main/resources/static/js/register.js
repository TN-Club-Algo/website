document.getElementById('register-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const user = {
        username: username,
        password: password
        // Include other User properties as needed
    };

    try {
        const response = await fetch('/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });

        if (response.ok) {
            // Handle successful registration (e.g., redirect to the login page)
            window.location.href = '/login';
        } else {
            // Handle registration errors (e.g., display an error message)
            console.error('Registration failed:', response.status, response.statusText);
        }
    } catch (error) {
        console.error('Network error:', error);
    }
});
