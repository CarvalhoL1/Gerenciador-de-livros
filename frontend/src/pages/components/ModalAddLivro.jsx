import { useState } from 'react';
import api from '../../api';

function ModalAddLivro({ aberto, onFechar, onLivroAdicionado }) {
    const [titulo, setTitulo] = useState('');
    const [descricao, setDescricao] = useState('');
    const [totalPaginas, setTotalPaginas] = useState('');
    const [status, setStatus] = useState('quero_ler');
    const [erro, setErro] = useState('');
    const [salvando, setSalvando] = useState(false);

    if (!aberto) return null;

    async function handleSubmit(e) {
        e.preventDefault();
        setErro('');
        setSalvando(true);
        try {
            await api.post('/livros', {
                titulo,
                descricao,
                totalPaginas: totalPaginas ? Number(totalPaginas) : 0,
                status,
            });
            limparCampos();
            onLivroAdicionado();
            onFechar();
        } catch (err) {
            setErro('Erro ao cadastrar livro.');
        } finally {
            setSalvando(false);
        }
    }

    function limparCampos() {
        setTitulo('');
        setDescricao('');
        setTotalPaginas('');
        setStatus('quero_ler');
    }

    function handleFechar() {
        limparCampos();
        setErro('');
        onFechar();
    }

    return (
        <div className="modal-overlay" onClick={onFechar}>
            <div className="modal-container" onClick={e => e.stopPropagation()}>
                <div className="modal-header">
                    <h2>Cadastrar novo livro</h2>
                    <button type="button" className="btn-acao btn-secundario" onClick={handleFechar}>Cancelar</button>
                </div>
                <div className="modal-body">
                <form onSubmit={handleSubmit}>
                    {erro && <div className="erro">{erro}</div>}

                    <input
                        placeholder="Título"
                        value={titulo}
                        onChange={e => setTitulo(e.target.value)}
                        required
                    />
                    <textarea
                        placeholder="Descrição"
                        value={descricao}
                        onChange={e => setDescricao(e.target.value)}
                    />
                    <input
                        type="number"
                        placeholder="Total de páginas"
                        value={totalPaginas}
                        onChange={e => setTotalPaginas(e.target.value)}
                        min="0"
                    />
                    <select value={status} onChange={e => setStatus(e.target.value)}>
                        <option value="quero_ler">Quero ler</option>
                        <option value="lendo">Lendo</option>
                        <option value="pausado">Pausado</option>
                        <option value="lido">Lido</option>
                        <option value="abandonado">Abandonado</option>
                    </select>

                    <div style={{ display: 'flex', gap: '10px', marginTop: '10px' }}>
                        <button type="submit" disabled={salvando}>
                            {salvando ? 'Salvando...' : 'Salvar'}
                        </button>

                    </div>
                </form>
                </div>
            </div>
        </div>
    );
}

export default ModalAddLivro;