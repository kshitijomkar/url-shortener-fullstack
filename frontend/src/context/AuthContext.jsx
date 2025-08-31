// File: frontend/src/context/AuthContext.jsx
import React, { createContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';
import toast from 'react-hot-toast'; // Import toast
const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('token'));
    const navigate = useNavigate();

    // This effect now only syncs the token state with localStorage.
    // The Axios interceptor is responsible for attaching the token to headers.
    useEffect(() => {
        if (token) {
            localStorage.setItem('token', token);
        } else {
            localStorage.removeItem('token');
        }
    }, [token]);

    const loginAction = async (data) => {
        try {
            const response = await api.post('/api/auth/login', data);
            const newToken = response.data.jwt;
            if (newToken) {
                setToken(newToken);
                navigate('/dashboard');
            }
			toast.success('Logged in successfully!'); // Success toast
        } catch (err) {
            console.error('Login error:', err);
            toast.error('Login failed! Please check your credentials.'); // Error toast
        }
    };

    const logoutAction = () => {
        setToken(null);
        navigate('/login');
    };

    const contextValue = { token, loginAction, logoutAction };

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthContext;