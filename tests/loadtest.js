import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080/api/auth';

const users = [
    { firstName: 'John', lastName: 'Doe', prefix: 'van', email: 'john.doe@example.com', password: 'password123' },
    { firstName: 'Jane', lastName: 'Smith', prefix: 'de', email: 'jane.smith@example.com', password: 'password123' },
    { firstName: 'Alice', lastName: 'Johnson', prefix: 'the', email: 'alice.johnson@example.com', password: 'password123' },
];

export let options = {
    vus: 50,
    duration: '30s',
};

export default function () {
    let user = users[Math.floor(Math.random() * users.length)];

    // REGISTRATION
    let registrationPayload = JSON.stringify({
        firstName: user.firstName,
        lastName: user.lastName,
        prefix: user.prefix,
        email: user.email,
        password: user.password,
    });

    let registerResponse = http.post(`${BASE_URL}/register`, registrationPayload, {
        headers: { 'Content-Type': 'application/json' },
    });

    check(registerResponse, {
        'is registration successful': (r) => r.status === 201,
    });

    sleep(1);

    // LOGIN
    let loginPayload = JSON.stringify({
        username: user.email,
        password: user.password,
    });

    let loginResponse = http.post(`${BASE_URL}/login`, loginPayload, {
        headers: { 'Content-Type': 'application/json' },
    });

    check(loginResponse, {
        'is login successful': (r) => r.status === 200,
    });

    let authToken = loginResponse.cookies['JWT'];

    // 3. LOGOUT
    let logoutResponse = http.post(
        `${BASE_URL}/logout`,
        null,
        {
            headers: {
                'Content-Type': 'application/json',
                Cookie: `JWT=${authToken}`,
            },
        }
    );

    check(logoutResponse, {
        'is logout successful': (r) => r.status === 200,
    });

    sleep(1);
}