import { useState } from 'react';
import api from '../../api';

function ModalAlterarSenha({ aberto, onFechar}){
    const [email, setEmail] = useState('');
    const [senhaAtual, setSenhaAtual] = useState('');
    const [senhaNova, setSenhaNova] = useState('');
    const [erro, setErro] = useState('');
    const [salvando, setSalvando] = useState(false);

    if (!aberto) return null;

    function limparCampos() {
        setEmail('');
        setSenhaAtual('');
        setSenhaNova('');
    }

    async function handleEditarSenha(e){
        e.preventDefault();
        setErro('');
        setSalvando(true);
        try {
            await api.put("/usuarios/senha", {
                email, senhaAtual, senhaNova
            })
        }
        catch (err) {
            setErro('Erro ao alterar senha.');
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
        <div onClick={handleFechar}>
            <div onClick={e => e.stopPropagation()}>
                <h2>Alterar Senha</h2>
                <form onSubmit={handleEditarSenha}>
                    {erro && <div className="erro">{erro}</div>}

                    <input
                        placeholder="Confirme seu email"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        required
                    />
                    <input
                        placeholder="Senha antiga"
                        value={senhaAtual}
                        onChange={e => setSenhaAtual(e.target.value)}
                        required
                    />
                    <input
                        placeholder="Nova senha"
                        value={senhaNova}
                        onChange={e => setSenhaNova(e.target.value)}
                        required
                    />
                    <button type="submit" disabled={salvando}>
                        {salvando ? 'Salvando...' : 'Salvar'}
                    </button>
                    <button type="button" onClick={handleFechar}>Cancelar</button>
                </form>
            </div>
        </div>
    );
}

export default ModalAlterarSenha
