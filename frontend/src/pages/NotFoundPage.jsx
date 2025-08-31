// File: frontend/src/pages/NotFoundPage.jsx
import React from 'react';
import { Link } from 'react-router-dom';

function NotFoundPage() {
    return (
        <div style={{ textAlign: 'center', marginTop: '5rem' }}>
            <h1>404 - Page Not Found</h1>
            <p>The page you are looking for does not exist.</p>
            <Link to="/" style={{ color: 'var(--primary-color)', fontWeight: 500 }}>
                Go to Homepage
            </Link>
        </div>
    );
}

export default NotFoundPage;