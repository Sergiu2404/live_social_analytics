// import {User} from '../../models/auth/User';

const API_URL = 'http://localhost:8080/api/auth';

interface LoginResponse{
    message: string;
    token?: string;
}
interface RegisterResponse{
    message: string;
}

export async function login(email: string, password: string){
    // const response = await axios.post(`${API_URL}/login`, {email, password});
    // return response.data;
    try{
        const response = await fetch(`${API_URL}/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({email, password})
        });        
        const result = await response.json();

        if(!response.ok){
            console.log(`Request failed with status: ${response.status}`);
            throw new Error(result.error || "unknown error");
        }

        console.log(result);
        return result;
    }
    catch(error: unknown){
        if(error instanceof Error){
            console.error(error.message);
            throw new Error(error.message);
        }
        throw new Error("Unknown error login");
    }
}
export async function register(username: string, email: string, password: string){
    // const response = await axios.post(`${API_URL}/register`, {username, email, password});
    // return response.data;
    try{
        const response = await fetch(`${API_URL}/register`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({username, email, password})
        });

        if(!response.ok){
            throw new Error(`Request failed with status ${response.status}`);
        }

        const result: RegisterResponse = await response.json();
        return result;
    }catch(error: unknown){
        if(error instanceof Error){
            console.error(error.message);
            throw new Error(error.message);
        }
        throw new Error("Unknown Error register");
    }
}
export async function verify(){
    try{
        const response = await fetch(`${API_URL}/verify`, {
            method: "GET",
            "headers": {"Content-Type": "application/json"},
        });

        if(!response.ok){
            throw new Error(`Response failed with status ${response.status}`);
        }

        const result = await response.json();
        return result;
    }catch(error: unknown){
        if(error instanceof Error){
            console.error(error.message);
            throw new Error(error.message);
        }
        throw new Error("Unknown error verify");
    }
}