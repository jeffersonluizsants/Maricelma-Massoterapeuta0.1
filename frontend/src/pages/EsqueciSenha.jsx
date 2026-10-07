import { useState } from "react";
import { Link } from "react-router-dom";
import { Mail, ArrowLeft, CheckCircle2, AlertCircle } from "lucide-react";
import api from "../services/api";

export function EsqueciSenha() {
const [email, setEmail] = useState("");
const [sucesso, setSucesso] = useState(false);
const [erro, setErro] = useState("");
const [loading, setLoading] = useState(false);

async function handleSubmit(e) {
    e.preventDefault();
    setErro("");
    setLoading(true);

    try {
        await api.post("/login/esqueci-senha", { email });
        setSucesso(true);
    } catch (error) {
        setErro("Ocorreu um erro ao enviar o e-mail. Por favor, tente novamente.");
    } finally {
        setLoading(false);
    }
}
return (
    <div className="min-h-screen flex items-center justify-center bg-slate-100 p-4">
        <div className="max-w-md w-full bg-white rounded-2xl shadow-xl p-8 space-y-6 border border-slate-200">
            <Link to="/login"className="inline-flex items-center gap-2 text-sm text-slate-500 hover:text-slate-800 transition-colors mb-2">
                <ArrowLeft className="w-4 h-4" />
                Voltar para o login
            </Link>
            <div className="text-center space-y-2">
                <h2 className="text-2xl font-bold text-slate-800">Esqueci minha senha</h2>
                <p className="text-sm text-slate-500">
                    Digite seu e-mail abaixo e enviaremos um link para redefinir sua senha.</p>
            </div>
            {sucesso ? (
                <div className="p-4 bg-emerald-50 text-emerald-800 rounded-lg border border-emerald-200 text-center space-y-2">
                    <CheckCircle2 className="w-8 h-8 text-emerald-600 mx-auto" />
                    <p className="font-semibold">E-mail enviado!</p>
                    <p className="text-xs text-emerald-700">
                        Verifique sua caixa de entrada e siga as instruções para redefinir sua senha.
                    </p>
                </div>
            ) : (
                <form onSubmit={handleSubmit} className="space-y-4">
                    {erro && (
                        <div className="flex items-center gap-2 p-3 bg-red-50 text-red-700 text-sm rounded-lg border border-red-200">
                            <AlertCircle className="w-5 h-5 shrink-0" />
                            <span>{erro}</span>
                        </div>
                    )}
                    <div>
                        <label className="block text-sm font-medium text-slate-700 mb-1"> E-mail registrado </label>
                        <div className="relative">
                            <Mail className="w-5 h-5 absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"/>
                            <input
                                type="email"
                                required
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="w-full pl-10 pr-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 outline-none text-sm transition-all"
                                placeholder="Digite seu e-mail"
                            />
                        </div>
                    </div>
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-emerald-600 hover:bg-emerald-700 text-white font-medium py-2.5 rounded-lg transition-collors shadow-md disabled:opacity-50 flex items-center justify-center"
                    >
                        {loading ? "Enviando..." : "Enviar Link de Redefinição"}
                    </button>
                </form>
            )}
        </div>
    </div>
);
}