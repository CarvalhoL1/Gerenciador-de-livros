import { useState } from 'react';
import api from '../../api';

function ModalAlterarNome({ aberto, onFechar}){
    const [nome, setNome] = useState('');
    const [erro, setErro] = useState('');
    const [salvando, setSalvando] = useState(false);

    if (!aberto) return null;

    function limparCampos() {
        setNome('');
    }

    async function handleEditarNome(e){
        e.preventDefault();
        setErro('');
        setSalvando(true);
        try {
            await api.put("/usuarios/nome", {nomeNovo:nome})
            handleFechar();
        }
        catch (err) {
            setErro('Erro ao alterar nome.');
        } finally {
            setSalvando(false);
        }
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
                    <h2>Alterar nome</h2>
                    <button className="btn-acao btn-secundario" type="button" onClick={handleFechar}>Cancelar</button>
                </div>
                <div className="modal-body">
                <form onSubmit={handleEditarNome}>
                    {erro && <div className="erro">{erro}</div>}

                    <input
                        placeholder="Digite o novo nome"
                        value={nome}
                        onChange={e => setNome(e.target.value)}
                        required
                    />
                    <button type="submit" disabled={salvando}>
                        {salvando ? 'Salvando...' : 'Salvar'}
                    </button>
                </form>
                </div>
            </div>
        </div>
    );
}

export default ModalAlterarNome
