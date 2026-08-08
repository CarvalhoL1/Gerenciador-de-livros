import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FiEye, FiEyeOff } from "react-icons/fi";
import { FaBook } from "react-icons/fa";
import '../index.css';
import api from '../api';

function Login() {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');
    const [carregando, setCarregando] = useState(false);
    const [mostrarSenha, setMostrarSenha] = useState(false);
    const alternarSenha = () => {
        setMostrarSenha(!mostrarSenha);
    };
    const navigate = useNavigate()

    async function handleLogin(e) {
        e.preventDefault();
        setErro('');
        setCarregando(true);
        try {
            const response = await api.post('/auth/login', {email, senha});
            const { token, usuario } = response.data;
            localStorage.setItem('token', token);
            navigate("/inicio");
        } catch (e) {
            setErro('Email ou senha incorretos')
        }
        finally{
            setCarregando(false);
        }
    }

    return (
        <div className='container-login'>
            <h1>Gerenciador de Livros <FaBook /></h1>
            <a href={"/cadastro"}>Não tem conta? crie uma</a>
            <p>Login</p>
            <form onSubmit={handleLogin}>
                {erro && !carregando && <div className="erro">{erro}</div>}
                {carregando && <div className="carregando">Carregando...</div>}
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    autoComplete="email"
                />
                <div className='campo-senha'>
                    <input
                        type={mostrarSenha ? "text" : "password"}
                        placeholder="Senha"
                        value={senha}
                        onChange={e => setSenha(e.target.value)}
                        autoComplete="current-password"
                    />
                    <span
                        className="toggle-senha"
                        onClick={alternarSenha}>
                        {mostrarSenha ? <FiEyeOff /> : <FiEye />}
                    </span>
                </div>
                <button type="submit">Entrar</button>
            </form>

    </div>
    )
}

export default Login