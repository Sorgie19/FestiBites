// AuthContext.js
import React, { createContext, useState } from 'react';

export const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [email, setEmail] = useState('');

  const login = (userEmail) => {
    setIsLoggedIn(true);
    setEmail(userEmail);
  };

  const logout = () => {
    setIsLoggedIn(false);
    setEmail('');
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, email, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
