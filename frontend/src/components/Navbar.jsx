// File: frontend/src/components/Navbar.jsx
import React, { useContext } from 'react';
import { NavLink, Link } from 'react-router-dom';
import AuthContext from '../context/AuthContext';
import './Navbar.css';

function Navbar() {
    const { token, logoutAction } = useContext(AuthContext);

    return (
        <nav className="navbar">
            <div className="navbar-container">
                <Link to="/" className="navbar-logo-container"> {/* Changed class name */}
                    <img src="/shortly.ico" alt="Shortly Logo" className="navbar-logo-icon" /> {/* Your new logo */}
                    <span className="navbar-logo-text">Shortly</span> {/* Your text */}
                </Link>
                <div className="navbar-links">
                    {token ? (
                        <>
                            <NavLink to="/dashboard" className="nav-item">Dashboard</NavLink>
                            <button onClick={logoutAction} className="nav-button">Logout</button>
                        </>
                    ) : (
                        <>
                            <NavLink to="/login" className="nav-item">Login</NavLink>
                            <NavLink to="/register" className="nav-button">Sign Up</NavLink>
                        </>
                    )}
                </div>
            </div>
        </nav>
    );
}

export default Navbar;