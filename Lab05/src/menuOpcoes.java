import java.util.EnumSet;

public enum menuOpcoes{
    CADASTRAR_CLIENTE           ("Cadastrar cliente", null, null, null),
    CADASTRAR_VEICULO           ("Cadastrar veículo", null, null, null),
    CADASTRAR_FROTA             ("Cadastrar frota", null, null, null),
    CADASTRAR_SEGURADORA        ("Cadastrar seguradora", null, null, null),
    
    EXIBIR_CLIENTES             ("Exibir clientes", null, null, null),
    EXIBIR_VEICULOS_FROTA       ("Exibir veículos por frota", null, null, null),
    EXIBIR_VEICULOS_CLIENTE     ("Exibir veículos por cliente", null, null, null),
    EXIBIR_SEGUROS_CLIENTE      ("Exibir seguros por cliente", null, null, null),
    EXIBIR_SINISTROS_CLIENTE    ("Exibir sinístros por cliente", null, null, null),
    DADOS_SEGURADORA            ("Dados da seguradora", null, null, null),

    EXCLUIR_CLIENTE             ("Excluir cliente", null, null, null),
    EXCLUIR_VEICULO             ("Excluir veículo", null, null, null),
    EXCLUIR_SINISTRO            ("Excluir sinistro", null, null, null),

    VOLTAR                      ("Voltar", null, null, null),

    CADASTRAR                   ("Cadastros", CADASTRAR_CLIENTE, CADASTRAR_SEGURADORA, VOLTAR),
    EXIBIR                      ("Exibir", EXIBIR_CLIENTES, DADOS_SEGURADORA, VOLTAR),
    EXCLUIR                     ("Excluir", EXCLUIR_CLIENTE, EXCLUIR_SINISTRO, VOLTAR),                                                              
    ATUALIZAR_FROTA             ("Atualizar frota", null, null, null),
    AUTORIZAR_CONDUTOR          ("Autorizar condutor", null, null, null),
    DESAUTORIZAR_CONDUTOR       ("Desautorizar condutor", null, null, null),
    GERAR_SINISTRO              ("Gerar sinistro", null, null, null),
    TRANSFERIR_SEGURO           ("Transferir seguro", null, null, null),
    SAIR                        ("Sair", null, null, null);

    private final String descricao;
    private final EnumSet<menuOpcoes> subMenu;
    private EnumSet<menuOpcoes> aux;

    menuOpcoes(String descricao, menuOpcoes inicio, menuOpcoes fim, menuOpcoes voltar){
        aux = EnumSet.range(inicio, fim);
        aux.add(voltar);
        this.descricao = descricao;
        this.subMenu = EnumSet.copyOf(aux);
    }

    public String getDescricao(){return descricao;}
    public EnumSet<menuOpcoes> getSubMenu(){return subMenu;}
}
