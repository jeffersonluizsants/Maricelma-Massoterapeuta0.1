import { useState, useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";
import { useNavigate, Link } from "react-router-dom";
import { Lock, Mail, AlertCircle, Sparkles, UserPlus} from "lucide-react";

export function Login() {
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const { login } = useContext(AuthContext);
    const navigate = useNavigate();

    async function handleLogin(e) {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            await login(email, senha);
            navigate("/dashboard");
        }catch (err) {
            setError("Email ou senha inválidos");
        }finally {
            setLoading(false);
        }
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-slate-100 p-4">
            <div className="max-w-md w-full bg-white rounded-2xl shadow-xl p-8 space-y-6 border border-slate-200">
            <div className="text-center space-y-2">
                <div className="inline-flex items-center justify-center w-12 h-12 rounded-full bg-emerald-100 text-emerald-600 mb-2">
                    <Sparkles className="w-6 h-6" />
                </div>
                <h2 className="text-2xl font-bold text-slate-800">Maricelma Massoterapia</h2>
                <p className="text-sm text-slate-500">Faça login para acessar o painel administrativo</p>
                </div>

                {error && (
                    <div className="flex items-center gap-2 p-3 bg-red-50 text-red-700 text-sm rounded-md border border-red-200">
                        <AlertCircle className="w-5 h-5 shrink-0"/>
                        <span>{error}</span>
                    </div>
                )}
                <form onSubmit={handleLogin} className="space-y-4">
                    <div>
                        <label className= "block text-sm font-medium text-slate-700 mb-1">Email</label>
                        <div className="relative">
                            <Mail className="w-5 h-5 absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"/>
                            <input
                                type="email"
                                required
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="w-full pl-10 pr-4 py-2 border-slate-300 rounded-lg focus:ring-emerald-500 focus:border-emeraldo-500 ouline-none text-sm transition-all"
                                placeholder="seu.email@exemplo.com"
                            />
                        </div>
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-slate-700 mb-1">Senha</label>
                        <Link to="/esqueci-senha" className="text-sm text-emerald-600 hover:text-emerald-700 font-medium hover:underline">
                        Esqueci minha senha
                        </Link>
                        <div className="relative">
                            <Lock className="w-5 h-5 absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"/>
                            <input
                                type="password"
                                required
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                                className="w-full pl-10 pr-4 py-2 border-slate-300 rounded-lg focus:ring-emerald-500 focus:border-emeraldo-500 ouline-none text-sm transition-all"
                                placeholder="••••••••"
                            />
                        </div>
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-emerald-600 hover:bg-emerald-700 text-white font-medium rounded-lg transition-collors shadow-md hover:shadow-lg disabled:opacity-50 flex items-center justify-center">
                        {loading ? 'Entrando...' : 'Entrar'}
                        </button>
                </form>

                <div className="pt-4 border-t border-slate-200 text-center">
                    <p className="text-sm text-slate-600">Não tem uma conta?</p>
                    <Link to="/cadastro" className="w-full inline-flex items-center justify-center gap-2 bg-slate-100 hover:bg-slate-700 font-medium rounded-lg transition-collors text-sm">
                    <UserPlus className="w-4 h-4" />
                    Criar conta
                    </Link>
                </div>
            </div>
        </div>
    );
}