// File: frontend/src/App.jsx
import React, { useContext } from 'react';
import { Routes, Route, Navigate, useLocation } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion'; // Import AnimatePresence
import AuthContext from './context/AuthContext';

// Import Components
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';
import PageLayout from './components/PageLayout'; // Import our new layout
import { Toaster } from 'react-hot-toast';

// Import Pages
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import NotFoundPage from './pages/NotFoundPage';

import './App.css';

function App() {
    const { token } = useContext(AuthContext);
    const location = useLocation(); // This hook is needed for AnimatePresence

    return (
        <>
            <Navbar />
            <Toaster position="top-center" reverseOrder={false} />
            <main className="main-container">
                <AnimatePresence mode="wait">
                    {/* The key prop is crucial for AnimatePresence to detect page changes */}
                    <Routes location={location} key={location.pathname}>
                        <Route 
                            path="/" 
                            element={token ? <Navigate to="/dashboard" /> : <PageLayout><HomePage /></PageLayout>} 
                        />
                        <Route 
                            path="/login" 
                            element={token ? <Navigate to="/dashboard" /> : <PageLayout><LoginPage /></PageLayout>} 
                        />
                        <Route 
                            path="/register" 
                            element={token ? <Navigate to="/dashboard" /> : <PageLayout><RegisterPage /></PageLayout>} 
                        />
                        <Route
                            path="/dashboard"
                            element={
                                <ProtectedRoute>
                                    <PageLayout><DashboardPage /></PageLayout>
                                </ProtectedRoute>
                            }
                        />
                        <Route path="*" element={<PageLayout><NotFoundPage /></PageLayout>} />
                    </Routes>
                </AnimatePresence>
            </main>
        </>
    );
}

export default App;