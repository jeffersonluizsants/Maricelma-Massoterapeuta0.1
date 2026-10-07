import { useState } from "react";
import {Link, useNavigate} from "react-router-dom";
import {User,Mail,Phone,ArrowLeft,AlertCircle,Sparkles,Lock as LockIcon } from "lucide-react";
import  api  from "../services/api";

export function Cadastro() {
    const [nome, setNome] = useState("");
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [confirmarSenha, setConfirmarSenha] = useState("");
    const [telefone, setTelefone] = useState("");
    const [erro, setErro] = useState("");
    const [loading, setLoading] = useState(false);


    const navigate = useNavigate();

    async function handleCadastro(e) {
        e.preventDefault();
        setErro("");
        setLoading(true);

        if (senha !== confirmarSenha) {
            setErro("As senhas não coincidem.");
            setLoading(false);
            return;
        }

        try {
            await api.post("/login/cadastar", {
                nome,
                email,
                senha,
                telefone,
                role: 'CLIENTE'
            });

            navigate('/login'); {state : {message: "Cadastro realizado com sucesso! Faça login para continuar."}};
        } catch (error) {
            setErro("Erro ao cadastrar usuário. Tente novamente.");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-slate-100 p-4">
            <div className="w-full max-w-md bg-white rounded-lg shadow-xl p-8 space-y-6 border border-slate-200">
                <Link to="/login" className="inline-flex items-center gap-2 text-sm text-slate-500 hover:text-slate-800 transition-colors"
                >
                <ArrowLeft className="w-4 h-4" />
                Voltar para Login
                </Link>
                <div className="text-center space-y-2">
                    <div className="inlene-flex items cennter justify-center 2-12 h-12 rounded-full bg-emerald-100 text-emerald-500 mb-2">
                        <Sparkles className="w-6 h-6" />
                    </div>
                    <h2 className="text-2xl font-bold text-slate-800">Cadastro</h2>
                    <p className="text-sm text-slate-500">
                        Crie sua conta para agendar suas sessões.
                    </p>
                </div>

                {erro && (
                    <div className="flex items-center gap-2 bg-red-50 text-red-700 text-sm rounded-lg border border-red-200">
                        <AlertCircle className="w-5 h-5 shrink-0" />
                        <span>{erro}</span>
                    </div>
                )}
                <form onSubmit={handleCadastro} className="space-y-4">
                    <div>
                        <label className="block text-sm font-medium text-slate-700">
                            Nome Completo</label>
                            <div className="relative">
                            <User className="w-5 h-5 absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" />
                            <input
                                type="text"
                                required
                                value={nome}
                                onChange={(e) => setNome(e.target.value)}
                                placeholder="Digite seu nome completo"
                                className="w-full pl-10 pr-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-emerald-500 outline-none text-sm"
                                />
                            </div>
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-slate-700 mb-1">E-mail</label>
                        <div className="relative">
                            <Mail className="w-5 h-5 absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" />
                            <input
                                type="email"
                                required
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                placeholder="Digite seu e-mail"
                                className="w-full pl-10 pr-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-emerald-500 outline-none text-sm"
                            />
                        </div>
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-slate-700 mb-1">Telefone / WhatApp</label>
                        <div className="relative">
                            <Phone className="w-5 h-5 absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" />
                            <input
                                type="tel"
                                required
                                value={telefone}
                                onChange={(e) => setTelefone(e.target.value)}
                                placeholder="(xx) xxxxx-xxxx"
                                className="w-full pl-10 pr-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-emerald-500 outline-none text-sm"
                            />
                        </div>
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-slate-700 mb-1">Senha</label>
                        <div className="relative">
                            <LockIcon className="w-5 h-5 absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" />
                            <input
                                type="password"
                                required
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                                placeholder="************"
                                className="w-full pl-10 pr-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-emerald-500 outline-none text-sm"
                            />
                        </div>
                        <div>
                        <label className="block text-sm font-medium text-slate-700 mb-1">Confirmar Senha</label>
                        <div className="relative">
                            <LockIcon className="w-5 h-5 absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" />
                            <input
                                type="password"
                                required
                                value={confirmarSenha}
                                onChange={(e) => setConfirmarSenha(e.target.value)}
                                placeholder="************"
                                className="w-full pl-10 pr-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-emerald-500 outline-none text-sm"
                            />
                        </div>
                        </div>
                    </div>
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-emerald-500 hover:bg-emerald-700 text-white font-medium py-2.5 rounded-lg transition-colors shadow-md disabled:opacity-50 flex items-center justify-center"
                    >
                        {loading ? "Cadastrando..." : "Finalizar Cadastro"}
                    </button>
                    </form>
                    </div>
        </div>
    );
}
