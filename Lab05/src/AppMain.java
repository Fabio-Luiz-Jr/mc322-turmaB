import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AppMain{
    private static int selecionarSeguradora(ArrayList<Seguradora> listaSeguradoras, Scanner entrada){
        String cnpj = null, nome = null, telefone, endereco, email;
        int index, indexSeguradora;
        boolean valido = false;

        //#region Escolha da seguradora
        System.out.println("Escolha uma seguradora:");
        index = 0;
        for(Seguradora s: listaSeguradoras)
            System.out.println("    •" + ++index + ": " + s.getNome() + ";");
        System.out.println("    •0: Criar nova seguradora.");
        System.out.print("▹");
        indexSeguradora = Integer.parseInt(entrada.nextLine());
        if((indexSeguradora < 0) || (indexSeguradora > index)){
            System.out.println("Escolha inválida.");
            indexSeguradora = -1;
        }
        System.out.println("------------------------------------------------------------------");
        //#endregion

        //#region Coleta de dados da nova seguradora
        if(indexSeguradora == 0){
            indexSeguradora = index;
            System.out.println("Dados da nova seguradora:");
            while(!valido){
                System.out.println("CNPJ: ");
                System.out.print("▹");
                cnpj = entrada.next();
                valido = Validacao.validaCNPJ(cnpj);
                if(!valido){
                    System.out.println("CNPJ inválido");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println();
                }
            }
            valido = false;
            while(!valido){
                System.out.println("Nome:");
                System.out.print("▹");
                nome = entrada.next();
                valido = Validacao.validaNome(nome);
                if(!valido){
                    System.out.println("CNPJ inválido");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println();
                }
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
            listaSeguradoras.add(new Seguradora(cnpj, nome, telefone, endereco, email));
            System.out.println("------------------------------------------------------------------");
        }
        //#endregion
        else
            indexSeguradora--;
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

    private static void escolhaClientes(ArrayList<Cliente> listaClientes){
        for(Cliente c: listaClientes)
            System.out.println(c.getNome() + " | " + ((c instanceof ClientePF) ? ("CPF: " + ((ClientePF)c).getCpf()) : ("CNPJ: " + ((ClientePJ)c).getCnpj())));
        System.out.print("▹");
    }

    private static void escolhaSeguro(Seguradora seguradora){
        for(Seguro s: seguradora.getListaSeguros()){
                        System.out.println(s instanceof SeguroPF ? ("Nome: " + ((SeguroPF)s).getClientePF().getNome() +
                                                                    " | CPF: " + ((SeguroPF)s).getClientePF().getCpf()) : 
                                                                    ("Nome: " + ((SeguroPJ)s).getClientePJ().getNome() + 
                                                                    " | CNPJ: " + ((SeguroPJ)s).getClientePJ().getCnpj()));
                        System.out.println("\t\t\tSeguro");
                        System.out.println("\tID: " + s.getId());
                        System.out.println(s instanceof SeguroPF ? ("\tVeículo: " + ((SeguroPF)s).getVeiculo()) : 
                                                                   ("\tFrota: " + ((SeguroPJ)s).getFrota().getCode()));    
                    }
        System.out.print("▹");
    }

    private static void dadosIniciais(ArrayList<Seguradora> listaSeguradoras, File file){
        int operacao, indexSeguradora = 0, anoFabricacao, index, id = 0, i = 0;
        String nome, telefone, endereco, email, cpf, genero, educacao, cnpj, cpf_cnpj, 
               placa, marca, modelo, 
               code;
        boolean ehSeguroPF = false;
        Date dataNascimento, dataFundacao, dataInicio, dataFim;
        Cliente cliente;
        Veiculo veiculo = null;
        Frota frota = null;
        Condutor condutor;
        try (Scanner scanner = new Scanner(file)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            cnpj = scanner.nextLine();
            nome = scanner.nextLine();
            telefone = scanner.nextLine();
            endereco = scanner.nextLine();
            email = scanner.nextLine();
            listaSeguradoras.add(new Seguradora(cnpj, nome, telefone, endereco, email));
            
            do{
                operacao = Integer.parseInt(scanner.nextLine());
                switch(operacao){
                    case 1://Cadastrar cliente
                        operacao = Integer.parseInt(scanner.nextLine());
                        nome = scanner.nextLine();
                        telefone = scanner.nextLine();
                        endereco = scanner.nextLine();
                        email = scanner.nextLine();
                        if(operacao == 1){
                            cpf = scanner.nextLine();
                            genero = scanner.nextLine();
                            educacao = scanner.nextLine();
                            dataNascimento = sdf.parse(scanner.nextLine());
                            cliente = new ClientePF(nome, telefone, endereco, email, cpf, genero, educacao, dataNascimento);
                        }else{
                            cnpj = scanner.nextLine();
                            dataFundacao = sdf.parse(scanner.nextLine());
                            cliente = new ClientePJ(nome, telefone, endereco, email, cnpj, dataFundacao);
                        }
                        listaSeguradoras.get(indexSeguradora).cadastrarCliente(cliente);
                        break;
                    case 2://Cadastrar veículo
                        placa = scanner.nextLine();
                        marca = scanner.nextLine();
                        modelo = scanner.nextLine();
                        anoFabricacao = Integer.parseInt(scanner.nextLine());
                        veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
                        cpf_cnpj = scanner.nextLine();
                        index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                        if(listaSeguradoras.get(indexSeguradora).listarClientes().get(index) instanceof ClientePF)
                            ((ClientePF)listaSeguradoras.get(indexSeguradora).listarClientes().get(index)).cadastrarVeiculo(veiculo);
                        else{
                            code = scanner.nextLine();
                            ((ClientePJ)listaSeguradoras.get(indexSeguradora).listarClientes().get(index)).atualizarFrota(code, veiculo);
                        }
                        break;
                    case 3://Cadastrar frota
                        cnpj = scanner.nextLine();
                        code = scanner.nextLine();
                        frota = new Frota(code);
                        ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cnpj)).cadastrarFrota(frota);
                        break;
                    case 4://Autorizar condutor
                        nome = scanner.nextLine();
                        cpf = scanner.nextLine();
                        telefone = scanner.nextLine();
                        endereco = scanner.nextLine();
                        email = scanner.nextLine();
                        dataNascimento = sdf.parse(scanner.nextLine());
                        id = Integer.parseInt(scanner.nextLine());
                        listaSeguradoras.get(indexSeguradora).getSeguro(id).autorizarCondutor(new Condutor(cpf, nome, telefone, endereco, email, dataNascimento));
                        break;
                    case 5://Deleta cliente
                        cpf_cnpj = scanner.nextLine();
                        listaSeguradoras.get(indexSeguradora).removerCliente(cpf_cnpj);
                        break;
                    case 6://Desautorizar condutor
                        cpf_cnpj = scanner.nextLine();
                        cpf = scanner.nextLine();
                        condutor = listaSeguradoras.get(indexSeguradora).getCondutor(cpf_cnpj, cpf, id);
                        listaSeguradoras.get(indexSeguradora).getSeguro(id).desautorizarCondutor(condutor);
                        break;
                    case 7://Deleta veículo
                        cpf_cnpj = scanner.nextLine();
                        index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                        placa = scanner.nextLine();
                        if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                            for(Seguro s: listaSeguradoras.get(indexSeguradora).getSegurosPorCliente(cpf_cnpj))
                                if(Objects.equals(((SeguroPF)s).getVeiculo().getPlaca(), placa)){
                                    listaSeguradoras.get(indexSeguradora).getSinistrosPorCliente(cpf_cnpj).removeAll(listaSeguradoras.get(indexSeguradora).getSinistrosPorCliente(cpf_cnpj));
                                    listaSeguradoras.get(indexSeguradora).getSegurosPorCliente(cpf_cnpj).removeIf(seguro -> seguro.getId() == s.getId());
                                }
                        else
                            for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaFrota())
                                ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).atualizarFrota(f.getCode(), placa);

                        break;
                    case 8://Gerar seguro
                        cpf_cnpj = scanner.nextLine();
                        index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                        if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                            ehSeguroPF = true;
                        if(ehSeguroPF){
                            placa = scanner.nextLine();
                            veiculo = ((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getVeiculo(placa);
                        }else{
                            code = scanner.nextLine();
                            frota = ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getFrota(code);
                        }
                        dataInicio = sdf.parse(scanner.nextLine());
                        dataFim = sdf.parse(scanner.nextLine());
                        if(ehSeguroPF) listaSeguradoras.get(indexSeguradora).gerarSeguro(listaSeguradoras.get(indexSeguradora), dataInicio, dataFim, veiculo, (ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
                        else listaSeguradoras.get(indexSeguradora).gerarSeguro(listaSeguradoras.get(indexSeguradora), dataInicio, dataFim, frota, (ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
                        break;
                    case 9://Gerar sinistro
                        id = Integer.parseInt(scanner.nextLine());
                        for(i = id; i >= 0; i--)
                            if(listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getId() == id)
                                break;
                        cpf = scanner.nextLine();
                        endereco = scanner.nextLine();
                        listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).gerarSinistro(endereco, cpf, listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i));
                        int indexSinistro = listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getListaSinistros().size() - 1;
                        listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getListaSinistros().add(listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getListaSinistros().get(indexSinistro));
                        break;
                    case 10://Transferir seguro
                        id = Integer.parseInt(scanner.nextLine());
                        ehSeguroPF = true;
                        ehSeguroPF = listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i) instanceof SeguroPF ? true : false;
                        cpf_cnpj = scanner.nextLine();
                        for(i = 0; i < listaSeguradoras.get(indexSeguradora).getListaSeguros().size(); i++)
                            if(listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getId() == id){
                                if(ehSeguroPF)
                                    ((SeguroPF)listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i)).setClientePF((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
                                else
                                    ((SeguroPJ)listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i)).setClientePJ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
                            }
                        break;
                }
            }while(scanner.hasNextLine());
            scanner.close();
        } catch (FileNotFoundException | ParseException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        //#region Variáveis
        Scanner entrada;
        ArrayList<Seguradora> listaSeguradoras = new ArrayList<Seguradora>();
        Veiculo veiculo = null;
        Cliente cliente = null;
        Condutor condutor;
        Frota frota = null;
        int anoFabricacao, operacao = 0, index, indexSeguradora, id = 0, i;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascimento, dataFundacao, dataInicio, dataFim;
        String placa, marca, modelo, cpf_cnpj,
               nome, telefone, endereco, email,
               educacao, genero, cpf, 
               cnpj, 
               tipoCliente, 
               code;
        boolean  lerNovamente, ehSeguroPF;
        EnumSet<menuOpcoes> menu = EnumSet.range(menuOpcoes.CADASTRAR,menuOpcoes.SAIR);
        File file = new File("Lab05/src/dados.txt");
        //#endregion
        if(file.exists()){
            dadosIniciais(listaSeguradoras, file);
            System.out.println("Dados iniciais criados");
        }else System.out.println("Dados iniciais não gerados");
        entrada = new Scanner(System.in).useDelimiter("\n");
        do{
            indexSeguradora = selecionarSeguradora(listaSeguradoras, entrada);
            if(indexSeguradora == -1)
                System.out.println("Seguradora inválida");
            else{
                System.out.println("Seguradora selecionada: " + listaSeguradoras.get(indexSeguradora).getNome());
                System.out.println();
            }
        }while(indexSeguradora == -1);

        voltar: do{
            lerNovamente = true;

            //#region Escolha de operação
            System.out.println("Escolha uma operação: ");
            for(menuOpcoes opcao: menu)
                System.out.println((((opcao.ordinal()) % 14) % 7) + ": " + opcao.getDescricao());
            System.out.print("▹");
            operacao = Integer.parseInt(entrada.nextLine());
            i = 0;
            
            switch(operacao){
                case 0: //Sair
                    lerNovamente = false;
                    break;
                case 1: //Cadastrar
                    for(menuOpcoes subMenu: menuOpcoes.CADASTRAR.getSubMenu())
                        System.out.println(++i % menuOpcoes.CADASTRAR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 0;
                    break;
                case 2: //Exibir
                    for(menuOpcoes subMenu: menuOpcoes.EXIBIR.getSubMenu())
                        System.out.println(++i % menuOpcoes.EXIBIR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 5;
                    break;
                case 3: //Excluir
                    for(menuOpcoes subMenu: menuOpcoes.EXCLUIR.getSubMenu())
                        System.out.println(++i % menuOpcoes.EXCLUIR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 11;
                    break;
                case 4: //Gerar seguro
                    operacao = 15;
                    lerNovamente = false;
                    break;
                case 5: //Gerar sinistro
                    operacao = 16;
                    lerNovamente = false;
                    break;
                case 6: //Transferir seguro
                    operacao = 17;
                    lerNovamente = false;
                    break;
                default:
                    System.out.println("Operação inválida");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println();
                    continue voltar;
            }
            //#endregion
            if(lerNovamente){
                int aux;
                System.out.print("▹");
                aux = Integer.parseInt(entrada.nextLine());
                if(aux == 0)
                    operacao = -1;
                else
                    operacao += aux;
            }
            
            System.out.println("------------------------------------------------------------------");
            System.out.println();

            index = 0;
            ehSeguroPF = false;
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
                    operacao = Integer.parseInt(entrada.nextLine());
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
                    anoFabricacao = Integer.parseInt(entrada.nextLine());
                    //#endregion

                    veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
                    
                    //#region Escolha de cliente
                    System.out.println("Qual o CPF/CNPJ do dono?");
                    escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes());
                    cpf_cnpj = entrada.next();
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                    if(index == -1){
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

                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes("juridica"), cnpj) == -1){
                        System.out.println("CNPJ inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    System.out.println("Código para a frota:");
                    System.out.print("▹");
                    code = entrada.next();
                    frota = new Frota(code);
                    if(!((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cnpj)).cadastrarFrota(frota)){
                        System.out.println("Código existente");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    break;
                case 4://Autorizar condutor
                    System.out.println("Nome do condutor:");
                    System.out.println("▹");
                    nome = entrada.next();
                    if(!Validacao.validaNome(nome)){
                        System.out.println("Nome inválido.");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    System.out.println("CPF do condutor:");
                    System.out.println("▹");
                    cpf = entrada.next();
                    if(!Validacao.validaCPF(cpf)){
                        System.out.println("CPF inválido.");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    System.out.println("Telefone:");
                    System.out.println("▹");
                    telefone = entrada.next();
                    System.out.println("Endereço:");
                    System.out.println("▹");
                    endereco = entrada.next();
                    System.out.println("Email:");
                    System.out.println("▹");
                    email = entrada.next();
                    System.out.println("Data de nascimento: (dd/MM/aaaa)");
                    System.out.println("▹");
                    dataNascimento = sdf.parse(entrada.next());

                    System.out.println("Escolha o ID do seguro:");
                    escolhaSeguro(listaSeguradoras.get(indexSeguradora));
                    System.out.print("▹");
                    id = Integer.parseInt(entrada.nextLine());
                    
                    if(listaSeguradoras.get(indexSeguradora).getSeguro(id) == null){
                        System.out.println("ID inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue voltar;
                    }

                    listaSeguradoras.get(indexSeguradora).getSeguro(id).autorizarCondutor(new Condutor(cpf, nome, telefone, endereco, email, dataNascimento));
                    break;
                case 5://Trocar de seguradora
                    System.out.print("▹");
                    indexSeguradora = selecionarSeguradora(listaSeguradoras, entrada);
                    break;
                case 6://Exibir cliente
                    //#region Escolha do tipo de cliente
                    System.out.println("O cliente pertence é:");
                    System.out.println("1: pessoa física;");
                    System.out.println("2: pessoa jurídica;");
                    System.out.println("3: Não tenho certeza.");
                    System.out.print("▹");
                    operacao = Integer.parseInt(entrada.nextLine());
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
                    System.out.println("Escolha o " + (Objects.equals(tipoCliente, "fisica") ? "CPF" : (Objects.equals(tipoCliente, "juridica")) ? "CNPJ" : "CPF/CNPJ") + " do cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente))
                        System.out.println("    •" + c.getNome());
                    System.out.print("▹");
                    cpf_cnpj = entrada.next();
                    //#endregion
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente), cpf_cnpj);
                    if(index == -1){
                        System.out.println((Objects.equals(tipoCliente, "fisica") ? "CPF" : (Objects.equals(tipoCliente, "juridica")) ? "CNPJ" : "CPF/CNPJ") + " inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    System.out.println(listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente).get(index));
                    break;
                case 7://Exibir veículos por frota
                    System.out.println("Escolha o CNPJ do cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("juridica"))
                        System.out.println(c.getNome() + " | CNPJ: " + ((ClientePJ)c).getCnpj());
                    System.out.print("▹");
                    cnpj = entrada.next();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes("juridica"), cnpj) == -1){
                        System.out.println("CPF/CNPJ inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    
                    for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cnpj)).getListaFrota()){
                        System.out.print("Frota " + f.getCode() + ": ");
                        for(Veiculo v: f.getListaVeiculos())
                            System.out.print(v);
                    }
                    break;
                case 8://Exibir veículos por cliente
                    System.out.println("Escolha o CPF do cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("fisica"))
                        System.out.println(c.getNome() + " | CPF: " + ((ClientePF)c).getCpf());
                    System.out.print("▹");
                    cpf = entrada.next();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf) == -1){
                        System.out.println("CPF inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    for(Veiculo v: ((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf)).getListaVeiculos())
                        System.out.print(v);
                    break;
                case 9://Exibir seguros por cliente
                    System.out.println("Escolha o CPF/CNPJ do cliente: ");
                    escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes());
                    cpf_cnpj = entrada.next();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        System.out.println("CPF/CNPJ inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                        tipoCliente = "fisica";
                    else
                        tipoCliente = "juridica";
                    for(Seguro s: listaSeguradoras.get(indexSeguradora).getListaSeguros())
                        if(Objects.equals(tipoCliente, "fisica") && Objects.equals(((SeguroPF)s).getClientePF(), listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)) || (Objects.equals(tipoCliente, "juridica") && Objects.equals(((SeguroPJ)s).getClientePJ(), listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj))))
                            System.out.println(s);
                    break;
                case 10://Exibir sinistros por cliente
                    System.out.println("Escolha o CPF/CNPJ do cliente: ");
                    escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes());
                    cpf_cnpj = entrada.next();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        System.out.println("CPF/CNPJ inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                        tipoCliente = "fisica";
                    else
                        tipoCliente = "juridica";
                    
                    for(Seguro s: listaSeguradoras.get(indexSeguradora).getListaSeguros())
                        if(Objects.equals(tipoCliente, "fisica") && Objects.equals(((SeguroPF)s).getClientePF(), listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)) || (Objects.equals(tipoCliente, "juridica") && Objects.equals(((SeguroPJ)s).getClientePJ(), listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj))))
                            for(Sinistro si: s.getListaSinistros())
                                System.out.println(si);
                    break;
                case 11://Exibir dados da seguradora
                    System.out.println(listaSeguradoras.get(indexSeguradora));
                    break;
                case 12://Deleta cliente
                    System.out.println("Escolha o CPF/CNPJ do cliente:");
                    escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes());
                    cpf_cnpj = entrada.next();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) != -1){
                        System.out.println("CPF/CNPJ inválido inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    listaSeguradoras.get(indexSeguradora).removerCliente(cpf_cnpj);
                    break;
                case 13://Desautorizar condutor
                    System.out.println("Qual o CPF/CNPJ do cliente?");
                    escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes());
                    cpf_cnpj = entrada.next();

                    if(indexCliente(listaSeguradoras.get(indexSeguradora).getListaClientes(), cpf_cnpj) == -1){
                        System.out.println("CPF/CNPJ inválido inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    System.out.println("Qual o CPF do condutor?");
                    for(Seguro s: listaSeguradoras.get(indexSeguradora).getSegurosPorCliente(cpf_cnpj))
                        for(Condutor c: s.getListaCondutores())
                            System.out.println("Condutor: " + c.getNome() + " | CPF: " + c.getCpf());
                    System.out.print("▹");
                    cpf = entrada.next();

                    condutor = listaSeguradoras.get(indexSeguradora).getCondutor(cpf_cnpj, cpf, id);
                    if(!listaSeguradoras.get(indexSeguradora).getSeguro(id).desautorizarCondutor(condutor)){
                        System.out.println("CPF inválido inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    break;
                case 14://Deleta veículo
                    System.out.println("Escolha o CPF/CNPJ do cliente:");
                    escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes());
                    cpf_cnpj = entrada.next();
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                    if(index == -1){
                        System.out.println("CPF/CNPJ inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    System.out.println("Escolha um veículo(pela placa): ");
                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                        System.out.println(((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaVeiculos());
                    else
                        for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaFrota())
                            System.out.println("Frota " + f.getCode() + ": " + f.getListaVeiculos());
                    System.out.print("▹");
                    placa = entrada.next();
                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF){
                        if(!((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).removerVeiculo(placa)){
                            System.out.println("Placa inválida");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue;
                        }
                        for(Seguro s: listaSeguradoras.get(indexSeguradora).getSegurosPorCliente(cpf_cnpj))
                            if(Objects.equals(((SeguroPF)s).getVeiculo().getPlaca(), placa)){
                                listaSeguradoras.get(indexSeguradora).getSinistrosPorCliente(cpf_cnpj).removeAll(listaSeguradoras.get(indexSeguradora).getSinistrosPorCliente(cpf_cnpj));
                                listaSeguradoras.get(indexSeguradora).getSegurosPorCliente(cpf_cnpj).removeIf(seguro -> seguro.getId() == s.getId());
                            }
                    }
                    else
                        for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaFrota())
                            ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).atualizarFrota(f.getCode(), placa);

                    break;
                case 15://Gerar seguro
                    System.out.println("Digite o CPF/CNPJ do cliente:");
                    escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes());
                    System.out.print("▹");
                    cpf_cnpj = entrada.next();
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                    if(index == -1){
                            System.out.println("CPF/CNPJ inválido");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue;
                    }
                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                        ehSeguroPF = true;
                    if(ehSeguroPF){
                        System.out.println("Escolha a placa do veículo");
                        for(Veiculo v: ((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaVeiculos())
                            System.out.println(v);
                        placa = entrada.next();
                        veiculo = ((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getVeiculo(placa);
                        if(veiculo == null){
                            System.out.println("Placa inválida");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue;
                        }
                    }else{
                        System.out.println("Escolha o código da frota");
                        for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaFrota())
                            System.out.println(f.getCode());
                        code = entrada.next();
                        frota = ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getFrota(code);
                        if(frota == null){
                            System.out.println("Frota inválida");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue;
                        } 
                    }
                    System.out.println("Data de início do seguro:");
                    System.out.print("▹");
                    dataInicio = sdf.parse(entrada.next());
                    System.out.println("Data do fim do seguro:");
                    System.out.print("▹");
                    dataFim = sdf.parse(entrada.next());
                    if(ehSeguroPF)
                        if(!listaSeguradoras.get(indexSeguradora).gerarSeguro(listaSeguradoras.get(indexSeguradora), dataInicio, dataFim, veiculo, (ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj))){
                            System.out.println("Veículo já possui seguro");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                        }
                    else if(!listaSeguradoras.get(indexSeguradora).gerarSeguro(listaSeguradoras.get(indexSeguradora), dataInicio, dataFim, frota, (ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj))){
                        System.out.println("Frota já possui seguro");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                    }
                    break;
                case 16://Gerar sinistro
                    //#region Escolha de cliente
                    System.out.println("Qual o ID do seguro:");
                    escolhaSeguro(listaSeguradoras.get(indexSeguradora));
                    id = Integer.parseInt(entrada.nextLine());
                    for(i = 0; i < listaSeguradoras.get(indexSeguradora).getListaSeguros().size(); i++){
                        if(listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getId() == id)
                            break;
                        else if(i == listaSeguradoras.get(indexSeguradora).getListaSeguros().size() - 1){
                            System.out.println("ID inválido");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue voltar;
                        }
                    }
                    //#endregion

                    System.out.println("Qual o CPF do condutor?");
                    for(Condutor c: listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getListaCondutores())
                        System.out.println("Nome: " + c.getNome() + " | CPF: " + c.getCpf());
                    cpf = entrada.next();

                    //#region Coleta de dados
                    System.out.println("Local do acidente:");
                    System.out.print("▹");
                    endereco = entrada.next();
                    //#endregion

                    if(!listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).gerarSinistro(endereco, cpf, listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i))){
                        System.out.println("CPF inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    int indexSinistro = listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getListaSinistros().size() - 1;
                    listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getListaSinistros().add(listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getListaSinistros().get(indexSinistro));
                    break;
                case 17://Transferir seguro
                    System.out.println("Escolha o ID do seguro: ");
                    escolhaSeguro(listaSeguradoras.get(indexSeguradora));
                    System.out.print("▹");
                    id = Integer.parseInt(entrada.nextLine());

                    if(listaSeguradoras.get(indexSeguradora).getSeguro(id) == null){
                        System.out.println("ID inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue voltar;
                    }
                    ehSeguroPF = listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i) instanceof SeguroPF ? true : false;

                    System.out.println("Escolha o " + (ehSeguroPF ? "CPF" : "CNPJ") + " do cliente que irá receber o seguro: ");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes()){
                        if(ehSeguroPF && c instanceof ClientePF)
                            System.out.println("Nome: " + c.getNome() + " | CPF: " + ((ClientePF)c).getCpf());
                        else if(!ehSeguroPF && c instanceof ClientePJ)
                            System.out.println("Nome: " + c.getNome() + " | CNPJ: " + ((ClientePJ)c).getCnpj());
                        }
                    System.out.print("▹");
                    cpf_cnpj = entrada.next();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        System.out.println((ehSeguroPF ? "CPF" : "CNPJ") + " inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }

                    for(i = 0; i < listaSeguradoras.get(indexSeguradora).getListaSeguros().size(); i++)
                        if(listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getId() == id){
                            if(ehSeguroPF)
                                ((SeguroPF)listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i)).setClientePF((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
                            else
                                ((SeguroPJ)listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i)).setClientePJ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
                        }
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
                for(i = 0; i < 35; i++)
                    System.out.println();
            }
        }while(operacao != 0);
        entrada.close();
    }
}
