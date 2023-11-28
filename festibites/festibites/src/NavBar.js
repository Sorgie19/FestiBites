import React, { useState, useContext } from 'react';
import { AuthContext } from './AuthContext'; // Import AuthContext
import LoginModal from './LoginModal'; // Import the modal component

const NavBar = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { isLoggedIn, email, logout } = useContext(AuthContext); // Access authentication state

  return (
    <nav className="navbar">
      <div className="navbar-brand">Food Pickup</div>
      <div className="navbar-links">
        {isLoggedIn ? (
          <div>
            <span className="navbar-email">{email}</span>
            <button className="logout-button" onClick={logout}>Logout</button>
          </div>
        ) : (
          <button className="login-button" onClick={() => setIsModalOpen(true)}>Login</button>
        )}
      </div>
      {isModalOpen && <LoginModal onClose={() => setIsModalOpen(false)} />}
    </nav>
  );
}

export default NavBar;
