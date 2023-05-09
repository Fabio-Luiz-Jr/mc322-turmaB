public enum menuOpcoes{
    CADASTRAR_CLIENTE    ("Cadastrar cliente", null),
    CADASTRAR_VEICULO    ("Cadastrar veículo", null),
    CADASTRAR_SEGURADORA ("Cadastrar seguradora", null),
    
    LISTAR_CLIENTES      ("Listar clientes", null),
    LISTAR_VEICULOS      ("Listar veículos", null),
    LISTAR_SINISTROS     ("Listar sinístros", null),

    EXCLUIR_CLIENTE      ("Excluir cliente", null),
    EXCLUIR_VEICULO      ("Excluir veículo", null),
    EXCLUIR_SINISTRO     ("Excluir sinistro", null),

    VOLTAR               ("Voltar", null),

    CADASTRAR            ("Cadastros", new menuOpcoes[]{menuOpcoes.CADASTRAR_CLIENTE,
                                                                  menuOpcoes.CADASTRAR_VEICULO,
                                                                  menuOpcoes.CADASTRAR_SEGURADORA,
                                                                  menuOpcoes.VOLTAR}),
    LISTAR               ("Listar", new menuOpcoes[]{   menuOpcoes.LISTAR_CLIENTES,
                                                                  menuOpcoes.LISTAR_VEICULOS,
                                                                  menuOpcoes.LISTAR_SINISTROS,
                                                                  menuOpcoes.VOLTAR}),
    EXCLUIR              ("Excluir", new menuOpcoes[]{  menuOpcoes.EXCLUIR_CLIENTE,
                                                                  menuOpcoes.EXCLUIR_VEICULO,
                                                                  menuOpcoes.EXCLUIR_SINISTRO,
                                                                  menuOpcoes.VOLTAR}),
    GERAR_SINISTRO       ("Gerar sinistro", null),
    TRANSFERIR_SEGURO    ("Transferir seguro", null),
    SAIR                 ("Sair", null);

    private final String descricao;
    private final menuOpcoes[] subMenu;

    menuOpcoes(String descricao, menuOpcoes[] subMenu){
        this.descricao = descricao;
        this.subMenu = subMenu;
    }

    public String getDescricao(){
        return descricao;
    }
    
    public menuOpcoes[] getSubMenu(){
        return subMenu;
    }
}
