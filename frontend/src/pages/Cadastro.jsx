import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { FiEye, FiEyeOff } from "react-icons/fi";
import api from '../api'

function Cadastro() {
    const navigate = useNavigate()
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');
    const [carregando, setCarregando] = useState(false);
    const [mostrarSenha, setMostrarSenha] = useState(false);
    function alternarSenha() {
        setMostrarSenha(prev => !prev);
    }
    async function handleCadatro(e) {
        e.preventDefault();
        setErro('');
        setCarregando(true);
        try {
            await api.post('/auth/cadastro', {nome, email, senha});
            alert("Conta criada com sucesso!")
            navigate("/login");
        }
        catch (e) {
            setErro('Conta já existe')
        }
        finally{
            setCarregando(false)
        }
    }
    return(
        <div className='container-cadastro'>
            <h1>Cadastro</h1>
            <a href={"/login"}>Já tem uma conta? entrar</a>
            <form onSubmit={handleCadatro}>
                {erro && !carregando && <div className="erro">{erro}</div>}
                {carregando && <div className="carregando">Carregando...</div>}
                <input
                    placeholder="Nome"
                    value={nome}
                    onChange={e => setNome(e.target.value)}
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                />
                <div>
                    <input
                        type={mostrarSenha ? "text" : "password"}
                        placeholder="Senha"
                        value={senha}
                        onChange={e => setSenha(e.target.value)}
                    />
                        <span
                            onClick={alternarSenha}>
                    {mostrarSenha ? <FiEyeOff /> : <FiEye />}

                        </span>
                </div>
                <button type="submit">Criar Conta</button>
            </form>
        </div>
    )
}
export default Cadastro;