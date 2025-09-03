import {useState} from 'react';
import { useNavigate } from 'react-router-dom';

import {login} from "../../api/auth/auth";

import "./AuthPages.css";

export default function LoginPage(){
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();


    async function handleLogin(event: React.FormEvent){
        event.preventDefault();
        try{
            const result = await login(email, password);
            console.log("Logged In: ", result);
            navigate("/home")
        } catch(error: unknown){
            if(error instanceof Error) setError(error.message);
        }
    }

    return (
        <div className="auth-container">
            <form onSubmit={handleLogin} className="auth-form">
                <h1>Login</h1>
                {error && <p className="error-message">{error}</p>}

                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
                <button type="submit" className="login-btn">Login</button>

                <p className="switch-link">
                    Don’t have an account?{' '}
                    <span onClick={() => navigate('/register')}>Register</span>
                </p>
            </form>
        </div>
    );
}