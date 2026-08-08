import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ModalAddLivro from './components/ModalAddLivro';
import ModalAlterarNome from "./components/ModalAlterarNome";
import ModalAlterarSenha from "./components/ModalAlterarSenha";

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

    function celulaEditavel(livro, campo) {
        const emEdicao = editando?.livroId === livro.id && editando?.campo === campo;
        if (emEdicao) {
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
        <div style={{ padding: '20px', maxWidth: '1000px', margin: '0 auto' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                <button onClick={handleLogout} style={{ fontWeight: 'bold' }}>
                    Logout
                </button>
                <span style={{ color: 'blue' }}>Bem vindo/a, {nome}</span>
                <span style={{ color: 'blue' }}>{mensagem}</span>

                <span style={{ fontStyle: 'italic', color: '#666' }}>
          Clique duas vezes para editar!
        </span>
            </div>
            <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse', marginBottom: '20px' }}>
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
                            <td><button onClick={() => apagarLivro(livro.id)}>Apagar</button></td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="7" style={{ textAlign: 'center' }}>Nenhum livro cadastrado.</td>
                    </tr>
                )}
                </tbody>
            </table>

            <div style={{ display: 'flex', gap: '10px' }}>
                <button onClick={abrirModalAddLivro}>Cadastrar novo livro</button>
                <button onClick={apagarConta} style={{ color: 'red' }}>Apagar conta</button>
                <button onClick={abrirModalAlterarNome}>Alterar nome</button>
                <button onClick={abrirModalAlterarSenha}>Alterar senha</button>
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