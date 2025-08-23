import React from 'react';
import logo from './logo.svg';
import './App.css';
import EnvironmentTest from './components/EnvironmentTest';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Task Management System</h1>
        <p>
          Environment Variables & JWT Authentication Setup
        </p>
      </header>
      
      <main>
        <EnvironmentTest />
      </main>
    </div>
  );
}

export default App;
