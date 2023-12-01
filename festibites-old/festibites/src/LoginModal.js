import React, { useState, useContext } from 'react';
import { AuthContext } from './AuthContext'; // Import AuthContext
import './LoginModal.css';

const LoginModal = ({ onClose }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(''); // Add state for error message
    const { login } = useContext(AuthContext); // Use the login function from AuthContext

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError(''); // Clear any existing error messages

        try {
            const response = await fetch('http://localhost:8080/api/authenticate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
                credentials: 'include' // Include cookies with the request
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Login successful:', data);
                localStorage.setItem('jwtToken', data.jwtToken); // Store the token in localStorage
                login(data.email); // Update authentication state on successful login
                onClose(); // Close the modal on successful login
            } else {
                setError('Login failed. Please check your credentials.'); // Set error message on failed login
                console.error('Login failed:', response);
            }
        } catch (error) {
            setError('An error occurred during login. Please try again.'); // Set error message on exception
            console.error('Error during API call', error);
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close-button" onClick={onClose}>&times;</span>
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <button type="submit">Login</button>
                    {error && <div className="error-message">{error}</div>} {/* Display error message */}
                </form>
            </div>
        </div>
    );
};

export default LoginModal;
