import java.util.Date;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AppMain{
    private static int selecionarSeguradora(ArrayList<Seguradora> listaSeguradoras, Scanner entrada){
        String cnpj, nome, telefone, endereco, email;
        int index, indexSeguradora;
        boolean valido = true;

        //#region Escolha da seguradora
        do{
            System.out.println("Escolha uma seguradora:");
            index = 0;
            for(Seguradora s: listaSeguradoras){
                index++;
                System.out.println("    •" + index + ": " + s.getNome() + ";");
            }
            System.out.println("    •0: Criar nova seguradora.");
            System.out.print("▹");
            indexSeguradora = entrada.nextInt();
            if((indexSeguradora < 0) || (indexSeguradora > index)){
                System.out.println("Escolha inválida.");
                valido = false;
            }
            System.out.println("------------------------------------------------------------------");
        }while(!valido);
        //#endregion

        //#region Coleta de dados da nova seguradora
        if(indexSeguradora == 0){
            indexSeguradora = index;
            System.out.println("Dados da nova seguradora:");
            System.out.println("CNPJ: ");
            System.out.print("▹");
            cnpj = entrada.next();
            System.out.println("Nome:");
            System.out.print("▹");
            nome = entrada.next();
            System.out.println("Telefone:");
            System.out.print("▹");
            telefone = entrada.next();
            System.out.println("Endereço:");
            System.out.print("▹");
            endereco = entrada.next();
            System.out.println("Email:");
            System.out.print("▹");
            email = entrada.next();
            listaSeguradoras.add(new Seguradora(cnpj, nome, telefone, endereco, email));
            System.out.println("------------------------------------------------------------------");
        }
        //#endregion
        else
            indexSeguradora--;
        System.out.println("Seguradora " + listaSeguradoras.get(indexSeguradora).getNome() + " selecionada.");
        System.out.println();
        return indexSeguradora;
    }

    private static int indexCliente(ArrayList<Cliente> listaClientes, String cpf_cnpj){
        int i = 0;
        for(Cliente c: listaClientes){
            if(c instanceof ClientePF && Objects.equals(((ClientePF)c).getCpf(), cpf_cnpj))
                return i;
            else if(Objects.equals(((ClientePJ)c).getCnpj(), cpf_cnpj))
                return i;
            i++;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception{
        //#region Variáveis
        Scanner entrada = new Scanner(System.in).useDelimiter("\n");
        ArrayList<Seguradora> listaSeguradoras = new ArrayList<Seguradora>();
        Veiculo veiculo;
        Cliente cliente = null;
        Frota frota = null;
        int anoFabricacao, operacao = 0, index, indexSeguradora, qtdeFuncionarios, id;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataLiscenca, dataNascimento, dataFundacao;
        String placa, marca, modelo, cpf_cnpj,
               nome, telefone, endereco, email,
               educacao, genero, classeEconomica, cpf, 
               cnpj, 
               tipoCliente, nomeCliente1, nomeCliente2,
               code;
        boolean  lerNovamente;
        EnumSet<menuOpcoes> menu = EnumSet.range(menuOpcoes.CADASTRAR,menuOpcoes.SAIR);
        //#endregion

        indexSeguradora = selecionarSeguradora(listaSeguradoras, entrada);
        System.out.println("Seguradora selecionada: " + listaSeguradoras.get(indexSeguradora).getNome());
        System.out.println();

        voltar: do{
            lerNovamente = true;

            //#region Escolha de operação
            System.out.println("Escolha uma operação: ");
            for(menuOpcoes opcao: menu)
                System.out.println((((opcao.ordinal() + 1) % 14) % 9) + ": " + opcao.getDescricao());
            System.out.print("▹");
            operacao = entrada.nextInt();
            
            switch(operacao){
                case 0: //Sair
                    int i = 1;
                    lerNovamente = false;
                    break;
                case 1: //Cadastrar
                    for(menuOpcoes subMenu: menuOpcoes.CADASTRAR.getSubMenu())
                        System.out.println(i++ % menuOpcoes.CADASTRAR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 0;
                    break;
                case 2: //Exibir
                    for(menuOpcoes subMenu: menuOpcoes.EXIBIR.getSubMenu())
                        System.out.println(i++ % menuOpcoes.EXIBIR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 4;
                    break;
                case 3: //Excluir
                    for(menuOpcoes subMenu: menuOpcoes.EXCLUIR.getSubMenu())
                        System.out.println(i++ % menuOpcoes.EXCLUIR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 9;
                    break;
                case 4: //Atualizar frota
                    operacao = 12;
                    lerNovamente = false;
                    break;
                case 5: //Autorizar condutor
                    operacao = 13;
                    lerNovamente = false;
                    break;
                case 6: //Desautorizar condutor
                    operacao = 14;
                    lerNovamente = false;
                    break;
                case 7: //Gerar sinistro
                    operacao = 15;
                    lerNovamente = false;
                    break;
                case 8: //Transferir seguro
                    operacao = 16;
                    lerNovamente = false;
                    break;
                default:
                    System.out.println("Operação inválida");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println();
                    continue;
            }
            //#endregion
            if(lerNovamente){
                int aux;
                System.out.print("▹");
                aux = entrada.nextInt();
                if(aux == 0)
                    operacao = -1;
                else
                    operacao += aux;
            }
            
            System.out.println("------------------------------------------------------------------");
            System.out.println();

            index = 0;
            switch(operacao){
                case -1://Voltar
                    continue voltar;
                case 0://Sair
                    break;
                case 1://Cadastrar cliente
                    //#region Escolha de pessoa(física/jurídica)
                    System.out.println("1: Pessoa física;");
                    System.out.println("2: Pessoa jurídica.");
                    System.out.print("▹");
                    operacao = entrada.nextInt();
                    if((operacao < 1) || (operacao > 2)){
                        System.out.println("Operação inválida.");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        operacao = 1;
                        continue;
                    }
                    //#endregion

                    //#region Coleta de dados
                    System.out.println("Nome:");
                    System.out.print("▹");
                    nome = entrada.next();
                    if(!Validacao.validaNome(nome)){
                        System.out.println("Nome inválido.");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    System.out.println("Telefone:");
                    System.out.print("▹");
                    telefone = entrada.next();

                    System.out.println("Endereço:");
                    System.out.print("▹");
                    endereco = entrada.next();

                    System.out.println("Email:");
                    System.out.print("▹");
                    email = entrada.next();

                    if(operacao == 1){
                        //#region Coleta de dados da pessoa física
                        System.out.println("CPF:");
                        System.out.print("▹");
                        cpf = entrada.next();
                        //#region Validação do CPF
                        if(!Validacao.validaCPF(cpf)){
                            System.out.println("CPF inválido.");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue;
                        }
                        //#endregion

                        System.out.println("Gênero:");
                        System.out.print("▹");
                        genero = entrada.next();

                        System.out.println("Educação:");
                        System.out.print("▹");
                        educacao = entrada.next();
                        
                        System.out.println("Data de nascimento:");
                        System.out.print("▹");
                        dataNascimento = sdf.parse(entrada.next());
                        
                        cliente = new ClientePF(nome, telefone, endereco, email, cpf, genero, educacao, dataNascimento);
                        //#endregion
                    }else{
                        //#region Coleta de dados da pessoa jurídica
                        System.out.println("CNPJ:");
                        System.out.print("▹");
                        cnpj = entrada.next();
                        //#region Validação do CNPJ
                        if(!Validacao.validaCNPJ(cnpj)){
                            System.out.println("CNPJ inválido.");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            operacao = 1;
                            continue;
                        }
                        //#endregion

                        System.out.println("Data de fundação(dd/MM/aaaa):");
                        System.out.print("▹");
                        dataFundacao = sdf.parse(entrada.next());
                        
                        cliente = new ClientePJ(nome, telefone, endereco, email, cnpj, dataFundacao);
                        //#endregion
                    }
                    listaSeguradoras.get(indexSeguradora).cadastrarCliente(cliente);
                    //#endregion
                    break;
                case 2://Cadastrar veículo
                    //#region Coleta dos dados
                    System.out.println("Placa:");
                    System.out.print("▹");
                    placa = entrada.next();
                    System.out.println("Marca:");
                    System.out.print("▹");
                    marca = entrada.next();
                    System.out.println("Modelo:");
                    System.out.print("▹");
                    modelo = entrada.next();
                    System.out.println("Ano de fabricação:");
                    System.out.print("▹");
                    anoFabricacao = entrada.nextInt();
                    //#endregion

                    veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
                    
                    //#region Escolha de cliente
                    System.out.println("Qual o CPF/CNPJ do dono?");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes())
                        System.out.println("    •" + c.getNome() + " | " + (c instanceof ClientePF ? (((ClientePF)c).getCpf()) : (((ClientePJ)c).getCnpj())));
                    System.out.print("▹");
                    cpf_cnpj = entrada.next();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        System.out.println("CPF/CNPJ inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    //#endregion

                    if(listaSeguradoras.get(indexSeguradora).listarClientes().get(index) instanceof ClientePF)
                        if(!((ClientePF)listaSeguradoras.get(indexSeguradora).listarClientes().get(index)).cadastrarVeiculo(veiculo)){
                            System.out.println("Veículo já foi registrado");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue;
                        }
                    else{
                        System.out.println("Código da frota: ");
                        for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).listarClientes().get(index)).getListaFrota())
                            System.out.println(f.getCode());
                        System.out.print("▹");
                        code = entrada.next();
                        if(!((ClientePJ)listaSeguradoras.get(indexSeguradora).listarClientes().get(index)).atualizarFrota(code, veiculo)){
                            System.out.println("Veículo já foi registrado");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue;
                        }
                    }
                    break;
                case 3://Cadastrar frota
                    System.out.println("Escolha o CNPJ do cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("juridica"))
                        System.out.println(c.getNome() + " | CNPJ: " + ((ClientePJ)c).getCnpj());
                    System.out.print("▹");
                    cnpj = entrada.next();

                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes("juridica"), cpf_cnpj) == -1){
                        System.out.println("CNPJ inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    System.out.println("Código para a frota:");
                    System.out.print("▹");
                    code = entrada.next();
                    frota = new Frota(code);
                    if(!((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).cadastrarFrota(frota)){
                        System.out.println("Código existente");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    break;
                case 4://Trocar de seguradora
                    System.out.print("▹");
                    indexSeguradora = selecionarSeguradora(listaSeguradoras, entrada);
                    break;
                case 5://Exibir cliente
                    //#region Escolha do tipo de cliente
                    System.out.println("O cliente pertence é:");
                    System.out.println("1: pessoa física;");
                    System.out.println("2: pessoa jurídica;");
                    System.out.println("3: Não tenho certeza.");
                    System.out.print("▹");
                    operacao = entrada.nextInt();
                    if((operacao < 1) || (operacao > 3)){
                        System.out.println("Operação inválida.");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        operacao = 5;
                        continue;
                    }
                    if(operacao == 1)
                        tipoCliente = "fisica";
                    else if (operacao == 2)
                        tipoCliente = "juridica";
                    else
                        tipoCliente = "todos";
                    //#endregion
                    
                    //#region Escolha de cliente
                    System.out.println("Escolha o cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente))
                        System.out.println("    •" + c.getNome());
                    System.out.print("▹");
                    nome = entrada.next();
                    //#endregion
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente), nome);
                    if(index == -1){
                        System.out.println("Nome inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    System.out.println(listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente).get(index));
                    break;
                case 6://Exibir veículos
                    System.out.println("Escolha o CPF/CNPJ do cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes())
                        System.out.println(c.getNome() + " | " + (c instanceof ClientePF ? ("CPF: " + ((ClientePF)c).getCpf()) : ("CNPJ: " + ((ClientePJ)c).getCnpj())));
                    System.out.print("▹");
                    cpf_cnpj = entrada.next();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        System.out.println("CPF/CNPJ inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                        System.out.println(((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaVeiculos());
                    else
                        System.out.println(((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaFrota());
                    break;
                case 7://Exibir dados dos sinistros
                    for(Sinistro s: listaSeguradoras.get(indexSeguradora).getListaSinistros())
                        System.out.print(s);
                    break;
                case 8://Exibir dados da seguradora
                    System.out.println("Nome: " + listaSeguradoras.get(indexSeguradora).getNome());
                    System.out.println("Telefone: " + listaSeguradoras.get(indexSeguradora).getTelefone());
                    System.out.println("Email: " + listaSeguradoras.get(indexSeguradora).getEmail());
                    System.out.println("Endereço: " + listaSeguradoras.get(indexSeguradora).getEndereco());
                    System.out.println("Lista de sinistros:");
                    for(Sinistro s: listaSeguradoras.get(indexSeguradora).getListaSinistros())
                        System.out.println("    •" + s);
                    System.out.println("Lista de clientes:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes())
                        System.out.println("    •" + c);
                    break;
                case 9://Deleta cliente
                    System.out.println("Escolha o cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes())
                        System.out.println("    •" + c.getNome());
                    System.out.print("▹");
                    nome = entrada.next();
                    if(!listaSeguradoras.get(indexSeguradora).removerCliente(nome))
                        System.out.println("Nome inválido");
                    break;
                case 10://Deleta veículo
                    System.out.println("Escolha um cliente: ");
                    System.out.println(listaSeguradoras.get(indexSeguradora).listarClientes());
                    System.out.print("▹");
                    nome = entrada.next();
                    if(!clienteExiste(listaSeguradoras.get(indexSeguradora).listarClientes(), nome)){
                        System.out.println("Nome inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    System.out.println("Escolha um veículo(pela placa): ");
                    System.out.print(listaSeguradoras.get(indexSeguradora).getCliente(nome).getListaVeiculos());
                    System.out.print("▹");
                    placa = entrada.next();
                    listaSeguradoras.get(indexSeguradora).getCliente(nome).removeVeiculo(placa);
                    break;
                case 11://Deleta sinistro
                    System.out.println("Escolha um sinistro(por ID): ");
                    System.out.print(listaSeguradoras.get(indexSeguradora).getListaSinistros());
                    id = entrada.nextInt();
                    if(!listaSeguradoras.get(indexSeguradora).deletarSinistro(id))
                        System.out.println("ID inválido");
                    break;
                case 12://Gerar sinistro
                    //#region Escolha de cliente
                    System.out.println("Gerar sinistro para qual cliente?");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes())
                        System.out.println("    •" + c.getNome());
                    System.out.print("▹");
                    nome = entrada.next();
                    if(!clienteExiste(listaSeguradoras.get(indexSeguradora).listarClientes(), nome)){
                        System.out.println("Nome inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    //#endregion

                    //#region Coleta de dados
                    System.out.println("Local do acidente:");
                    System.out.print("▹");
                    endereco = entrada.next();
                    System.out.println("--Dados do veículo--");
                    System.out.println("Placa:");
                    System.out.print("▹");
                    placa = entrada.next();
                    System.out.println("Marca:");
                    System.out.print("▹");
                    marca = entrada.next();
                    System.out.println("Modelo:");
                    System.out.print("▹");
                    modelo = entrada.next();
                    System.out.println("Ano de fabricação:");
                    System.out.print("▹");
                    anoFabricacao = entrada.nextInt();
                    //#endregion

                    veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
                    if(!listaSeguradoras.get(indexSeguradora).gerarSinistro(endereco, listaSeguradoras.get(indexSeguradora), veiculo, nome))
                        System.out.println("Cliente inválido");
                    break;
                case 13://Transferir seguro
                    System.out.println("Escolha o cliente que irá transferir o seguro: ");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes())
                        System.out.println(c.getNome());
                    System.out.print("▹");
                    nomeCliente1 = entrada.next();
                    if(!clienteExiste(listaSeguradoras.get(indexSeguradora).listarClientes(), nomeCliente1)){
                        System.out.println("Nome inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    System.out.println("Escolha o cliente que irá receber o seguro: ");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes())
                        if(!Objects.equals(c.getNome(), nomeCliente1))
                        System.out.println(c.getNome());
                    System.out.print("▹");
                    nomeCliente2 = entrada.next();
                    if(!clienteExiste(listaSeguradoras.get(indexSeguradora).listarClientes(), nomeCliente2)){
                        System.out.println("Nome inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    listaSeguradoras.get(indexSeguradora).transferenciaSeguro(listaSeguradoras.get(indexSeguradora).getCliente(nomeCliente1), listaSeguradoras.get(indexSeguradora).getCliente(nomeCliente2));
                    break;
                default:
                    System.out.println("Operação inválida");
                    break;
            }
            if(operacao != 0){
                System.out.println("------------------------------------------------------------------");
                System.out.println();
                System.out.println("Aperte Enter para continuar");
                try{
                    System.in.read();
                    entrada.nextLine();
                }
                catch(Exception error){}
                for(int i = 0; i < 35; i++)
                    System.out.println();
            }
        }while(operacao != 0);
        entrada.close();
    }
}
