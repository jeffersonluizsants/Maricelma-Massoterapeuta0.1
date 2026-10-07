import {createContext, useState, useEffect } from 'react';
import api from "../services/api";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    
    useEffect(() => {
        const token = localStorage.getItem("@MaricelmaApp:token");
        const storedUser = localStorage.getItem("@MaricelmaApp:user");
        if(token && storedUser){
            setUser(JSON.parse(storedUser));
        }
        setLoading(false);
    }, []);

    async function Login(email, password){
        const response = await api.post("/login", {email, password});
        const {token} = response.data;

        localStorage.setItem("@MaricelmaApp:token", token);

        const userData = {email}
            localStorage.setItem("@MaricelmaApp:user", JSON.stringify(userData));
            setUser(userData);
        }

    function Logout(){
        localStorage.removeItem("@MaricelmaApp:token");
        localStorage.removeItem("@MaricelmaApp:user");
        setUser(null);
    }
    return(
        <AuthContext.Provider value={{authenticated: !!user, user, Login, Logout}}>
            {children}
        </AuthContext.Provider>
    );
}