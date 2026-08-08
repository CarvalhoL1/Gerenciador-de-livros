import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ModalAddLivro from './components/ModalAddLivro';
import ModalAlterarNome from "./components/ModalAlterarNome";
import ModalAlterarSenha from "./components/ModalAlterarSenha";
import { IoMdInformationCircleOutline } from "react-icons/io";

import api from '../api';


function Inicio() {
    const [livros, setLivros] = useState([]);
    const [mensagem, setMensagem] = useState('');
    const [nome, setNome] = useState('');
    const navigate = useNavigate();
    const [editando, setEditando] = useState(null);
    const [valorEdicao, setValorEdicao] = useState('');

    function iniciarEdicao(livro, campo) {
        setEditando({ livroId: livro.id, campo });
        setValorEdicao(livro[campo] ?? '');
    }

    const [modalAddLivro, setModalAddLivro] = useState(false);
    const abrirModalAddLivro = () => setModalAddLivro(true);

    const [modalAlterarNome, setModalAlterarNome] = useState(false);
    const abrirModalAlterarNome = () => setModalAlterarNome(true);

    const [modalAlterarSenha, setModalAlterarSenha] = useState(false)
    const abrirModalAlterarSenha = () => setModalAlterarSenha(true);
    useEffect(() => {
        carregarLivros();
        carregarPerfil();
    }, []);

    const carregarPerfil = async () => {
        try {
            const response = await api.get('/usuarios/eu');
            setNome(response.data.nome);
        } catch (err) {
            console.error("Erro ao buscar perfil:", err);
        }
    };

    const carregarLivros = async () => {
        try {
            const response = await api.get('/livros');
            setLivros(response.data);
        } catch (err) {
            console.error("Erro ao buscar livros:", err);
            setMensagem("Erro ao carregar a tabela de livros.");
        }
    };
    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    async function salvarEdicao(livro) {
        const atualizado = { ...livro, [editando.campo]: valorEdicao };
        try {
            await api.put(`/livros/${livro.id}`, {
                titulo: atualizado.titulo,
                descricao: atualizado.descricao,
                pagAtual: Number(atualizado.paginaAtual) || 0,
                pagTotal: atualizado.totalPag ? Number(atualizado.totalPag) : null,
                status: atualizado.status,
            });
            setEditando(null);
            carregarLivros();
        } catch (err) {
            alert('Erro ao salvar edição.');
        }
    }

    async function apagarLivro(id) {
        if (!window.confirm("Apagar este livro?")) return;
        try {
            await api.delete(`/livros/${id}`);
            carregarLivros();
        } catch (err) {
            alert('Erro ao apagar livro.');
        }
    }

    const apagarConta = async () => {
        const confirmou = window.confirm("Tem certeza que deseja apagar sua conta?");
        if (!confirmou) return;
        const senha = window.prompt("Digite sua senha para confirmar:");
        if (!senha) return;
        try {
            await api.post('/usuarios/deletar', {  senha  });
            handleLogout();
        } catch (err) {
            alert('Senha incorreta.');
        }
    };

    const OPCOES_STATUS = ['quero_ler', 'lendo', 'pausado', 'lido', 'abandonado'];

    function celulaEditavel(livro, campo) {
        const emEdicao = editando?.livroId === livro.id && editando?.campo === campo;

        if (emEdicao) {
            if (campo === 'status') {
                return (
                    <td>
                        <select
                            autoFocus
                            value={valorEdicao}
                            onChange={e => setValorEdicao(e.target.value)}
                            onBlur={() => salvarEdicao(livro)}
                        >
                            {OPCOES_STATUS.map(opcao => (
                                <option key={opcao} value={opcao}>{opcao}</option>
                            ))}
                        </select>
                    </td>
                );
            }
            return (
                <td>
                    <input
                        autoFocus
                        value={valorEdicao}
                        onChange={e => setValorEdicao(e.target.value)}
                        onKeyDown={e => {
                            if (e.key === 'Enter') e.target.blur();
                            if (e.key === 'Escape') setEditando(null);
                        }}
                        onBlur={() => salvarEdicao(livro)}
                    />
                </td>
            );
        }

        return (
            <td onDoubleClick={() => iniciarEdicao(livro, campo)}>
                {livro[campo]}
            </td>
        );
    }
    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <div className="dashboard-textos">
                    <span className="dashboard-boas-vindas">Bem-vindo(a), {nome}</span>
                    {mensagem && <span className="mensagem-feedback"> - {mensagem}</span>}
                    <span className="dashboard-dica"><IoMdInformationCircleOutline /> Clique duas vezes para editar!</span>
                </div>
                <button onClick={handleLogout} className="btn-acao btn-secundario">
                    Sair
                </button>
            </header>
            <div className="tabela-container">
                <table className="tabela-livros">
                <thead>
                <tr style={{ background: '#f4f4f4' }}>
                    <th>Título</th>
                    <th>Descrição</th>
                    <th>Páginas</th>
                    <th>Página Atual</th>
                    <th>% Lida</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                {livros.length > 0 ? (
                    livros.map((livro) => (
                        <tr key={livro.id}>
                            {celulaEditavel(livro, 'titulo')}
                            {celulaEditavel(livro, 'descricao')}
                            {celulaEditavel(livro, 'totalPag')}
                            {celulaEditavel(livro, 'paginaAtual')}
                            <td>{livro.totalPag ? Math.round((livro.paginaAtual / livro.totalPag) * 100) : '—'}%</td>
                            {celulaEditavel(livro, 'status')}
                            <td><button className='btn-deletar-tabela' onClick={() => apagarLivro(livro.id)}>Apagar</button></td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="7" style={{ textAlign: 'center' }}>Nenhum livro cadastrado.</td>
                    </tr>
                )}
                </tbody>
            </table>
            </div>
            <div className="acoes-bar">
                <button className="btn-acao btn-primario" onClick={abrirModalAddLivro}>
                    + Cadastrar novo livro
                </button>
                <button className="btn-acao btn-secundario" onClick={abrirModalAlterarNome}>
                    Alterar nome
                </button>
                <button className="btn-acao btn-secundario" onClick={abrirModalAlterarSenha}>
                    Alterar senha
                </button>
                <button className="btn-acao btn-perigo" onClick={apagarConta}>
                    Apagar conta
                </button>
            </div>
            <ModalAddLivro
                aberto={modalAddLivro}
                onFechar={() => setModalAddLivro(false)}
                onLivroAdicionado={carregarLivros}
            />
            <ModalAlterarNome
                aberto={modalAlterarNome}
                onFechar={() => {
                    setModalAlterarNome(false)
                    carregarPerfil()
                }
            }
            />
            <ModalAlterarSenha
                aberto={modalAlterarSenha}
                onFechar={() => setModalAlterarSenha(false)}
            />
        </div>
    );
}

export default Inicio;