import React, { useState, useContext } from 'react';
import { Link } from 'react-router-dom'; // Import the Link component
import { AuthContext } from './AuthContext';
import LoginModal from './LoginModal';
import './NavBar.css';

const NavBar = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const { isLoggedIn, logout } = useContext(AuthContext);

    const handleLogoutClick = (e) => {
        e.preventDefault();
        logout();
        setIsModalOpen(false); // Close any open modals or dropdowns
    };

    return (
        <nav className="navbar">
            {/* ... other navbar content ... */}
            <div className="navbar-links">
                {isLoggedIn ? (
                    <div className="dropdown">
                        <button className="dropbtn">Account</button>
                        <div className="dropdown-content">
                            <Link to="/profile">Profile</Link> {/* Use Link instead of <a> */}
                            <Link to="/settings">Settings</Link> {/* Use Link instead of <a> */}
                            <a href="/" onClick={handleLogoutClick}>Logout</a> {/* Keep the logout as <a> with preventDefault */}
                        </div>
                    </div>
                ) : (
                    <button onClick={() => setIsModalOpen(true)}>Login</button>
                )}
            </div>
            {isModalOpen && <LoginModal onClose={() => setIsModalOpen(false)} />}
        </nav>
    );
};

export default NavBar;
