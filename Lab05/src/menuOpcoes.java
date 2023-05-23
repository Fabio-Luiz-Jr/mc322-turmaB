public enum menuOpcoes{
    CADASTRAR_CLIENTE    ("Cadastrar cliente", null),
    CADASTRAR_VEICULO    ("Cadastrar veículo", null),
    CADASTRAR_SEGURADORA ("Cadastrar seguradora", null),
    
    EXIBIR_CLIENTES      ("Exibir clientes", null),
    EXIBIR_VEICULOS      ("Exibir veículos", null),
    EXIBIR_SINISTROS     ("Exibir sinístros", null),
    DADOS_SEGURADORA     ("Dados da seguradora", null),

    EXCLUIR_CLIENTE      ("Excluir cliente", null),
    EXCLUIR_VEICULO      ("Excluir veículo", null),
    EXCLUIR_SINISTRO     ("Excluir sinistro", null),

    VOLTAR               ("Voltar", null),

    CADASTRAR            ("Cadastros", new menuOpcoes[]{menuOpcoes.CADASTRAR_CLIENTE,
                                                                  menuOpcoes.CADASTRAR_VEICULO,
                                                                  menuOpcoes.CADASTRAR_SEGURADORA,
                                                                  menuOpcoes.VOLTAR}),
    EXIBIR               ("Exibir", new menuOpcoes[]{   menuOpcoes.EXIBIR_CLIENTES,
                                                                  menuOpcoes.EXIBIR_VEICULOS,
                                                                  menuOpcoes.EXIBIR_SINISTROS,
                                                                  menuOpcoes.DADOS_SEGURADORA,
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
