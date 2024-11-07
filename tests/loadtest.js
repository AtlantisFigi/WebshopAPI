import http from 'k6/http';
import { check, sleep } from 'k6';
import SharedArray from 'k6/data';

const BASE_URL = 'http://localhost:8080/api';

const users = [
        { username: 'user1', password: 'password123' },
        { username: 'user2', password: 'password123' },
        { username: 'user3', password: 'password123' },
];

export let options = {
    vus: 50,
    duration: '30s',
};

export default function () {
    let user = users[Math.floor(Math.random() * users.length)];

    // REGISTRATION
    let registrationPayload = JSON.stringify({
        username: user.username,
        password: user.password,
        email: `${user.username}@example.com`,
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
        username: user.username,
        password: user.password,
    });

    let loginResponse = http.post(`${BASE_URL}/login`, loginPayload, {
        headers: { 'Content-Type': 'application/json' },
    });

    check(loginResponse, {
        'is login successful': (r) => r.status === 200,
    });

    let authToken = loginResponse.json().token;

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