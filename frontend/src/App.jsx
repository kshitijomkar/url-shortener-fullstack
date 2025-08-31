// File: frontend/src/App.jsx
import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import AuthContext from './context/AuthContext';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import ProtectedRoute from './components/ProtectedRoute';
import Navbar from './components/Navbar'; // Import Navbar
import NotFoundPage from './pages/NotFoundPage'; // Import the new page
import { Toaster } from 'react-hot-toast'; // Import Toaster
import './App.css';

function App() {
    const { token } = useContext(AuthContext);

    return (
        <>
            <Navbar />
			<Toaster position="top-center" reverseOrder={false} /> {/* Add this */}
            <main className="main-container">
                <Routes>
                    <Route path="/" element={token ? <Navigate to="/dashboard" /> : <HomePage />} />
                    <Route path="/login" element={token ? <Navigate to="/dashboard" /> : <LoginPage />} />
                    <Route path="/register" element={token ? <Navigate to="/dashboard" /> : <RegisterPage />} />
                    <Route
                        path="/dashboard"
                        element={
                            <ProtectedRoute>
                                <DashboardPage />
                            </ProtectedRoute>
                        }
                    />
					<Route path="*" element={<NotFoundPage />} /> {/* Add this catch-all route at the end */}
                </Routes>
            </main>
        </>
    );
}

export default App;