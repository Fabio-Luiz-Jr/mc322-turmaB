import java.util.*;

public enum menuOpcoes{
    CADASTRAR_CLIENTE           ("Cadastrar cliente", null),
    CADASTRAR_VEICULO           ("Cadastrar veículo", null),
    CADASTRAR_FROTA             ("Cadastrar frota", null),
    AUTORIZAR_CONDUTOR          ("Autorizar condutor", null),
    CADASTRAR_SEGURADORA        ("Cadastrar seguradora", null),
    
    EXIBIR_CLIENTES             ("Exibir clientes", null),
    EXIBIR_VEICULOS_FROTA       ("Exibir veículos por frota", null),
    EXIBIR_VEICULOS_CLIENTE     ("Exibir veículos por cliente", null),
    EXIBIR_SEGUROS_CLIENTE      ("Exibir seguros por cliente", null),
    EXIBIR_SINISTROS_CLIENTE    ("Exibir sinístros por cliente", null),
    DADOS_SEGURADORA            ("Dados da seguradora", null),

    EXCLUIR_CLIENTE             ("Excluir cliente", null),
    DESAUTORIZAR_CONDUTOR       ("Desautorizar condutor", null),
    EXCLUIR_VEICULO             ("Excluir veículo", null),

    VOLTAR                      ("Voltar", null),

    CADASTRAR                   ("Cadastros", Arrays.asList(CADASTRAR_CLIENTE, CADASTRAR_VEICULO, CADASTRAR_FROTA, AUTORIZAR_CONDUTOR, CADASTRAR_SEGURADORA, VOLTAR)),
    EXIBIR                      ("Exibir", Arrays.asList(EXIBIR_CLIENTES, EXIBIR_VEICULOS_FROTA, EXIBIR_VEICULOS_CLIENTE, EXIBIR_SEGUROS_CLIENTE, EXIBIR_SINISTROS_CLIENTE, DADOS_SEGURADORA, VOLTAR)),
    EXCLUIR                     ("Excluir", Arrays.asList(EXCLUIR_CLIENTE, DESAUTORIZAR_CONDUTOR, EXCLUIR_VEICULO, VOLTAR)),
    GERAR_SEGURO                ("Gerar seguro", null),
    GERAR_SINISTRO              ("Gerar sinistro", null),
    TRANSFERIR_SEGURO           ("Transferir seguro", null),
    SAIR                        ("Sair", null);

    private final String descricao;
    private final List<menuOpcoes> subMenu;
    

    menuOpcoes(String descricao, List<menuOpcoes> subMenu){
        this.descricao = descricao;
        this.subMenu = subMenu;
    }

    public String getDescricao(){return descricao;}
    public List<menuOpcoes> getSubMenu(){return subMenu;}
}
