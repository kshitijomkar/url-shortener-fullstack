// File: frontend/src/pages/HomePage.jsx
import React, { useState, useRef } from 'react';
import api from '../api/axiosConfig';
import ReCAPTCHA from 'react-google-recaptcha';
import toast from 'react-hot-toast';

function HomePage() {
    const [longUrl, setLongUrl] = useState('');
    const [result, setResult] = useState(null);
    const [error, setError] = useState('');
    const recaptchaRef = useRef();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setResult(null);
        const toastId = toast.loading('Shortening...');

        const recaptchaToken = recaptchaRef.current.getValue();
        if (!recaptchaToken) {
            toast.error('Please complete the reCAPTCHA.', { id: toastId });
            return;
        }

        try {
            const response = await api.post('/api/public/shorten', { longUrl, recaptchaToken });
            setResult(response.data);
            setLongUrl('');
            toast.success('Link shortened!', { id: toastId });
        } catch (err) {
            let errorMessage = 'An error occurred. Please try again.';
            if (err.response) {
                if (err.response.status === 429) {
                    errorMessage = 'You have reached your free limit. Please sign up to create more links.';
                } else if (typeof err.response.data === 'string' && err.response.data) {
                    errorMessage = err.response.data;
                }
            }
            setError(errorMessage);
            toast.error('Failed to shorten link.', { id: toastId });
        } finally {
            recaptchaRef.current.reset();
        }
    };

    return (
        <div className="homepage-container">
            {/* This new "hero" section replaces the old, redundant header */}
            <div className="hero-section">
                <h1>The simplest way to shorten long URLs</h1>
                <p>Free, fast, and reliable. Create your short link in seconds, with protection against spam and malicious links.</p>
            </div>

            <div className="content-box">
                <form onSubmit={handleSubmit} className="url-form">
                    <input
                        type="text"
                        placeholder="Enter a long URL here..."
                        value={longUrl}
                        onChange={(e) => setLongUrl(e.target.value)}
                        required
                    />
                    <button type="submit">Shorten!</button>
                </form>
                <div className="recaptcha-container">
                    <ReCAPTCHA
                        ref={recaptchaRef}
                        sitekey={import.meta.env.VITE_RECAPTCHA_SITE_KEY}
                    />
                </div>
                {error && <p className="error-message">{error}</p>}
                {result && (
                    <div className="result">
                        <p>Success! Your short URL is:</p>
                        <a href={`${api.defaults.baseURL}/${result.shortCode}`} target="_blank" rel="noopener noreferrer">
                             {`${api.defaults.baseURL.replace(/^https?:\/\//, '')}/${result.shortCode}`}
                        </a>
                    </div>
                )}
            </div>
        </div>
    );
}

export default HomePage;