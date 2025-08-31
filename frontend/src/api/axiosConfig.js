// File: frontend/src/api/axiosConfig.js
import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080',
});

// Request Interceptor - THIS IS THE KEY FIX
api.interceptors.request.use(
    (config) => {
        // Get the token from localStorage on every request
        const token = localStorage.getItem('token');
        if (token) {
            // If the token exists, add it to the Authorization header
            config.headers['Authorization'] = `Bearer ${token}`;
        }

        // --- Logging ---
        console.log('--- Sending Request ---');
        console.log('URL:', `${config.baseURL}${config.url}`);
        console.log('Method:', config.method.toUpperCase());
        console.log('Headers:', config.headers);
        if (config.data) {
            console.log('Data:', config.data);
        }
        console.log('-----------------------');
        
        return config;
    },
    (error) => {
        console.error('Request Error:', error);
        return Promise.reject(error);
    }
);

// Response Interceptor (no changes here, but kept for completeness)
api.interceptors.response.use(
    (response) => {
        console.log('--- Received Response ---');
        console.log('Status:', response.status);
        console.log('Data:', response.data);
        console.log('-------------------------');
        return response;
    },
    (error) => {
        if (error.response) {
            console.error('--- Response Error ---');
            console.error('Status:', error.response.status);
            console.error('Data:', error.response.data);
        } else if (error.request) {
            console.error('--- Network Error ---');
            console.error('No response received:', error.request);
        } else {
            console.error('--- General Error ---');
            console.error('Error:', error.message);
        }
        return Promise.reject(error);
    }
);

export default api;