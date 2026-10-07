import {useContext} from "react";
import { AuthContext } from "../contexts/AuthContext";
import { LogOut } from "lucide-react";

export function Dashboard() {
    const { user, logout } = useContext(AuthContext);

    return (
        <div className="min-h-screen bg-slate-50 p-6">
            <header className="max-w-7xl mx-auto flex justify-between items-center bg-white p-4 rounded-xl shadow-md mb-6 border border-slate-200">
                <h1 className="text-xl font-bold text-slate-800">Painel Administrativo</h1>
                <div className="flex items-center gap-4">
                    <span className="text-sm text-slate-800">{user?.email}</span>
                    <button
                        onClick={logout}
                        className="flex items-center gap-2 text-sm  text-red-600 hover:bg-red-50 p-2 rounded-lg trabsition-colors"
                    >
                        <LogOut className="w-4 h-4" />
                        Sair
                    </button>
                </div>
                </header>
                <main className="max-w-7xl mx-auto bg-white p-8 rounded-xl shadow-md border border-slate-200">
                    <p className="text-slate-600">Bem-vindo ao painel administrativo da Maricelma Massoterapia!</p>

                </main>
        </div>
    );
}