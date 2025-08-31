// File: frontend/src/pages/DashboardPage.jsx
import React, { useState, useEffect, useContext } from 'react';
import AuthContext from '../context/AuthContext';
import api from '../api/axiosConfig';
import toast from 'react-hot-toast';
// Import the icons we need
import { FiCopy, FiTrash2, FiBarChart2 } from 'react-icons/fi';

function DashboardPage() {
    const { logoutAction } = useContext(AuthContext);
    const [longUrl, setLongUrl] = useState('');
    const [urls, setUrls] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const fetchUrls = async () => {
        try {
            const response = await api.get('/api/v1/urls/my-urls');
            setUrls(response.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)));
        } catch (err) {
            console.error('Failed to fetch URLs', err);
            toast.error('Could not load your URLs.');
        }
    };

    useEffect(() => {
        fetchUrls();
    }, []);

    const handleCopy = (shortCode) => {
        const url = `${api.defaults.baseURL}/${shortCode}`;
        navigator.clipboard.writeText(url);
        toast.success('Copied to clipboard!');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);
        const toastId = toast.loading('Shortening URL...');

        try {
            await api.post('/api/v1/urls/shorten', { longUrl });
            setLongUrl('');
            fetchUrls();
            toast.success('Link shortened successfully!', { id: toastId });
        } catch (err) {
            console.error('Failed to shorten URL', err);
            toast.error('Failed to shorten URL.', { id: toastId });
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (shortCode) => {
        if (window.confirm('Are you sure you want to delete this link?')) {
            const toastId = toast.loading('Deleting link...');
            try {
                await api.delete(`/api/v1/urls/${shortCode}`);
                fetchUrls();
                toast.success('Link deleted.', { id: toastId });
            } catch (err) {
                console.error('Failed to delete URL', err);
                toast.error('Failed to delete link.', { id: toastId });
            }
        }
    };

    return (
        <div>
            <header className="dashboard-header">
                <h1>Your Dashboard</h1>
            </header>

            <div className="content-box">
                <h3>Create a new Short URL</h3>
                <form onSubmit={handleSubmit} className="url-form">
                    <input
                        type="text"
                        placeholder="https://example.com"
                        value={longUrl}
                        onChange={(e) => setLongUrl(e.target.value)}
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? 'Working...' : 'Shorten!'}
                    </button>
                </form>
                {error && <p className="error-message">{error}</p>}
            </div>

            <div className="content-box">
                <h3>Your Links</h3>
                <div className="url-list">
                    {urls.length > 0 ? (
                        urls.map((url) => (
                            <div key={url.id} className="url-item">
                                <div className="url-details">
                                    <p className="short-url">
                                        <a href={`${api.defaults.baseURL}/${url.shortCode}`} target="_blank" rel="noopener noreferrer">
                                            {`${api.defaults.baseURL.replace(/^https?:\/\//, '')}/${url.shortCode}`}
                                        </a>
                                    </p>
                                    <p className="long-url" title={url.longUrl}>{url.longUrl}</p>
                                </div>
                                <div className="url-actions">
                                    <div className="clicks-info">
                                        <FiBarChart2 />
                                        <span>{url.clickCount}</span>
                                    </div>
                                    <button onClick={() => handleCopy(url.shortCode)} className="icon-button" title="Copy link">
                                        <FiCopy size={18} />
                                    </button>
                                    <button onClick={() => handleDelete(url.shortCode)} className="icon-button delete-button" title="Delete link">
                                        <FiTrash2 size={18} />
                                    </button>
                                </div>
                            </div>
                        ))
                    ) : (
                        <p>You haven't shortened any URLs yet.</p>
                    )}
                </div>
            </div>
        </div>
    );
}
export default DashboardPage;