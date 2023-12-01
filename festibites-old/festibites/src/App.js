import logo from './logo.svg';
import React from 'react';
import './App.css';
import NavBar from './NavBar'; // Import NavBar component
import { AuthProvider } from './AuthContext'; // Import AuthProvider

function App() {
  return (
    <AuthProvider> {/* Wrap your application in AuthProvider */}
      <div className="App">
        <NavBar /> {/* Include NavBar component */}
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
      </div>
    </AuthProvider>
  );
}

export default App;
