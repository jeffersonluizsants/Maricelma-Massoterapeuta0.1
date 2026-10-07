import {Routes, Route, Navigate} from 'react-router-dom';
import {PrivateRoute} from './privateRoute';

import { Login }from '../pages/login';
import { EsqueciSenha } from '../pages/EsqueciSenha';
import { Dashboard } from '../pages/dashboard';
import { Cadastro } from '../pages/Cadastro';
export function AppRoutes() {
    return (
        <Routes>
            {/* Rotas Públicas */}
            <Route path="/login" element={<Login />} />
            <Route path="/esqueci-senha" element={<EsqueciSenha />} />
            <Route path="/cadastro" element={<Cadastro />} />

            {/* Rotas Protegidas */}
            <Route element={<PrivateRoute />}>
                <Route path="/dashboard" element={<Dashboard />} />
            </Route>

            {/* Redirecionamento Padrão */}
            <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
    );
}