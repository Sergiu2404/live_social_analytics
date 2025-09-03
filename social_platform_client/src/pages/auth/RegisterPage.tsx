import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register } from "../../api/auth/auth";

import "./AuthPages.css";


export default function RegisterPage(){
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    async function handleRegister(event: React.FormEvent){
        event.preventDefault();

        try{
            const result = await register(username, email, password);
            console.log("User registered: ", result);
            navigate("/login");
        }catch(error){
            if(error instanceof Error){
                setError(error.message);
            }
        }
    }


    return (
        <div className="auth-container">
            <form onSubmit={handleRegister} className="auth-form">
                <h1>Register</h1>
                {error && <p className="error-message">{error}</p>}

                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
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
                <button type="submit" className="register-btn">Register</button>

                <p className="switch-link">
                    Already have an account?{' '}
                    <span onClick={() => navigate('/login')}>Login</span>
                </p>
            </form>
        </div>
    );
}