import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { AuthContext } from "../contexts/AuthContext";


export function PrivateRoute() {
    const{authenticated, loading} = useContext(AuthContext);

    if(loading){
        return(
            <div className = "mi-h-screen flex  items-center justify-center bg-slate-50">
                <div className= "animate-spin rounded-full h-12 w-12 border-b-2 border-emerald-600"></div>
            </div>
        )
    }
    return authenticated ? <Outlet/> : <Navigate to="/login" replace/>
        }

