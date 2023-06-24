import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AppMain{
    private static void clear(){
        for(int i = 0; i < 35; i++)
            System.out.println();
    }

    private static int tryInteger(Scanner scanner){//Tenta ler um inteiro
        int tentativas = 3;
        while(true){//Loop infinito
            try{
                return Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException nfe){
                System.out.println(nfe);
                System.out.println(--tentativas + " tentativas restantes");
                if(tentativas == 0) throw nfe;//throw caso erre muitas vezes
            }
        }
    }

    private static Date tryDate(Scanner scanner) throws ParseException{//Tenta ler uma data
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int tentativas = 3;
        while(true){//Loop infinito
            try{
                return sdf.parse(scanner.nextLine());
            }catch(ParseException pe){
                System.out.println(pe);
                System.out.println("Use o formato dd/MM/aaaa");
                System.out.println("d = dia");
                System.out.println("M = mês");
                System.out.println("a = ano");
                System.out.println(--tentativas + " tentativas restantes");
                if(tentativas == 0) throw pe;//throw caso erre muitas vezes
            }
        }
    }

    private static boolean dentroDoAlcance(int numeroEscolhas, int input){
        //Verifica se a entrada está dentro do alcance
        if(input < 0 || input > numeroEscolhas){
            System.out.println("Valor fora do alcance");
            return false;
        }
        return true;
    }

    private static int selecionarSeguradora(ArrayList<Seguradora> listaSeguradoras, Scanner scanner){
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
        indexSeguradora = tryInteger(scanner);
        if(!dentroDoAlcance(index, indexSeguradora))
            return -1;
        //#endregion

        //#region Coleta de dados da nova seguradora
        if(indexSeguradora == 0){
            indexSeguradora = index;
            System.out.println("Dados da nova seguradora:");
            while(!valido){
                System.out.println("CNPJ: ");
                System.out.print("▹");
                cnpj = scanner.nextLine();
                valido = Validacao.validaCNPJ(cnpj);
                if(!valido){
                    clear();
                    System.out.println("CNPJ inválido");
                }
            }
            valido = false;
            while(!valido){
                System.out.println("Nome:");
                System.out.print("▹");
                nome = scanner.nextLine();
                valido = Validacao.validaNome(nome);
                if(!valido){
                    clear();
                    System.out.println("Nome inválido");
                }
            }
            System.out.println("Telefone:");
            System.out.print("▹");
            telefone = scanner.nextLine();
            System.out.println("Endereço:");
            System.out.print("▹");
            endereco = scanner.nextLine();
            System.out.println("Email:");
            System.out.print("▹");
            email = scanner.nextLine();
            listaSeguradoras.add(new Seguradora(cnpj, nome, telefone, endereco, email));
        }
        //#endregion
        else
            indexSeguradora--;
        return indexSeguradora;
    }

    private static int indexCliente(ArrayList<Cliente> listaClientes, String cpf_cnpj){
        //Retorna o index do cliente
        int i = 0;
        for(Cliente c: listaClientes){
            if(c instanceof ClientePF){
                if(Objects.equals(((ClientePF)c).getCpf().replaceAll("\\.|-|/", ""), cpf_cnpj.replaceAll("\\.|-|/", "")))
                    return i;
            }else if(c instanceof ClientePJ){
                if(Objects.equals(((ClientePJ)c).getCnpj().replaceAll("\\.|-|/", ""), cpf_cnpj.replaceAll("\\.|-|/", "")))
                    return i;
            }
            i++;
        }
        return -1;
    }

    private static boolean escolhaClientes(ArrayList<Cliente> listaClientes){
        //Imprime a lista de clientes
        boolean listaVazia = true;
        for(Cliente c: listaClientes){
            System.out.println(c.getNome() + " | " + ((c instanceof ClientePF) ? ("CPF: " + ((ClientePF)c).getCpf()) : ("CNPJ: " + ((ClientePJ)c).getCnpj())));
            listaVazia = false;
        }
        if(listaVazia)
            return false;
        System.out.print("▹");
        return true;
    }

    private static boolean escolhaSeguro(Seguradora seguradora){
        //Imprime a lista de seguros
        boolean listaVazia = true;
        for(Seguro s: seguradora.getListaSeguros()){
            listaVazia = false;
            System.out.println(s instanceof SeguroPF ? ("Nome: " + ((SeguroPF)s).getClientePF().getNome() +
                                                        " | CPF: " + ((SeguroPF)s).getClientePF().getCpf()) : 
                                                        ("Nome: " + ((SeguroPJ)s).getClientePJ().getNome() + 
                                                        " | CNPJ: " + ((SeguroPJ)s).getClientePJ().getCnpj()));
            System.out.println("\t\t\tSeguro");
            System.out.println("\tID: " + s.getId());
            System.out.println(s instanceof SeguroPF ? ("\tVeículo: " + ((SeguroPF)s).getVeiculo()) : 
                                                        ("\tFrota: " + ((SeguroPJ)s).getFrota().getCode()));    
        }
        if(listaVazia)
            return false;
        System.out.print("▹");
        return true;
    }

    private static void dadosIniciais(ArrayList<Seguradora> listaSeguradoras, File file){
        //Este método não verifica se qualquer entrada é válida, então certifique-se que os dados estão certos
        //caso for alterar o arquivo "dados.txt"
        //Uma cópia do main, mas que não irá imprimir nada na tela
        int operacao, indexSeguradora = 0, anoFabricacao, index, id = 0, i = 0;
        String nome, telefone, endereco, email, cpf, genero, educacao, cnpj, cpf_cnpj, 
               placa, marca, modelo, 
               code;
        boolean ehSeguroPF;
        Date dataNascimento, dataFundacao, dataInicio, dataFim;
        Cliente cliente;
        Veiculo veiculo = null;
        Frota frota = null;
        Condutor condutor;
        try(Scanner scanner = new Scanner(file)){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            cnpj = scanner.nextLine();
            nome = scanner.nextLine();
            telefone = scanner.nextLine();
            endereco = scanner.nextLine();
            email = scanner.nextLine();
            listaSeguradoras.add(new Seguradora(cnpj, nome, telefone, endereco, email));
            
            do{
                ehSeguroPF = false;
                operacao = tryInteger(scanner);
                switch(operacao){
                    case 1://Cadastrar cliente
                        operacao = tryInteger(scanner);
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
                        anoFabricacao = tryInteger(scanner);
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
                        id = tryInteger(scanner);
                        listaSeguradoras.get(indexSeguradora).getSeguro(id).autorizarCondutor(new Condutor(cpf, nome, telefone, endereco, email, dataNascimento));
                        break;
                    case 5://Deleta cliente
                        cpf_cnpj = scanner.nextLine();
                        listaSeguradoras.get(indexSeguradora).removerCliente(cpf_cnpj);
                        break;
                    case 6://Desautorizar condutor
                        cpf_cnpj = scanner.nextLine();
                        cpf = scanner.nextLine();
                        condutor = listaSeguradoras.get(indexSeguradora).getCondutor(cpf_cnpj, cpf);
                        id = tryInteger(scanner);
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
                        id = tryInteger(scanner);
                        for(i = 0; i < id; i++)
                            if(listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getId() == id)
                                break;
                        cpf = scanner.nextLine();
                        endereco = scanner.nextLine();
                        listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).gerarSinistro(endereco, cpf, listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i));
                        break;
                    case 10://Transferir seguro
                        id = tryInteger(scanner);
                        ehSeguroPF = listaSeguradoras.get(indexSeguradora).getSeguro(id) instanceof SeguroPF ? true : false;
                        cpf_cnpj = scanner.nextLine();
                        if(ehSeguroPF)
                            ((SeguroPF)listaSeguradoras.get(indexSeguradora).getSeguro(id)).setClientePF((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
                        else
                            ((SeguroPJ)listaSeguradoras.get(indexSeguradora).getSeguro(id)).setClientePJ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
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
        Scanner scanner;
        ArrayList<Seguradora> listaSeguradoras = new ArrayList<Seguradora>();
        Veiculo veiculo = null;
        Cliente cliente = null;
        Condutor condutor;
        Frota frota = null;
        int anoFabricacao, operacao = 0, index, indexSeguradora = 0, id = 0, i, numeroEscolhas = 0;
        Date dataNascimento, dataFundacao, dataInicio, dataFim;
        String placa, marca, modelo, cpf_cnpj,
               nome, telefone, endereco, email,
               educacao, genero, cpf, 
               cnpj, 
               tipoCliente, 
               code;
        boolean  lerNovamente, ehSeguroPF, listaVazia;
        EnumSet<menuOpcoes> menu = EnumSet.range(menuOpcoes.CADASTRAR,menuOpcoes.SAIR);
        File file = new File("Lab05/src/dados.txt");
        //#endregion
        if(file.exists()){//Verifica se o arquivo existe
            dadosIniciais(listaSeguradoras, file);//Gera os dados iniciais
            System.out.println("Dados iniciais criados");
        }else System.out.println("Dados iniciais não gerados");
        scanner = new Scanner(System.in).useDelimiter("\n");
        do{
            indexSeguradora = selecionarSeguradora(listaSeguradoras, scanner);
            if(indexSeguradora == -1)
                System.out.println("Seguradora inválida");
            else{
                System.out.println("Seguradora selecionada: " + listaSeguradoras.get(indexSeguradora).getNome());
                System.out.println();
            }
        }while(indexSeguradora == -1);

        do{
            listaVazia = true;
            lerNovamente = true;
            //#region Escolha de operação
            System.out.println("Escolha uma operação: ");
            for(menuOpcoes opcao: menu)
                System.out.println((((opcao.ordinal()) % 14) % 7) + ": " + opcao.getDescricao());
            System.out.print("▹");
            operacao = tryInteger(scanner);
            i = 0;
            
            switch(operacao){
                case 0: //Sair
                    lerNovamente = false;
                    break;
                case 1: //Cadastrar
                    for(menuOpcoes subMenu: menuOpcoes.CADASTRAR.getSubMenu())
                        System.out.println(++i % menuOpcoes.CADASTRAR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 0;
                    numeroEscolhas = 5;
                    break;
                case 2: //Exibir
                    for(menuOpcoes subMenu: menuOpcoes.EXIBIR.getSubMenu())
                        System.out.println(++i % menuOpcoes.EXIBIR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 5;
                    numeroEscolhas = 6;
                    break;
                case 3: //Excluir
                    for(menuOpcoes subMenu: menuOpcoes.EXCLUIR.getSubMenu())
                        System.out.println(++i % menuOpcoes.EXCLUIR.getSubMenu().size() + ": " + subMenu.getDescricao());
                    operacao = 11;
                    numeroEscolhas = 3;
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
                    clear();
                    System.out.println("Operação inválida");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println();
                    continue;
            }
            //#endregion
            if(lerNovamente){
                int aux = 0;
                System.out.print("▹");
                aux = tryInteger(scanner);
                if(!dentroDoAlcance(numeroEscolhas, aux))
                    aux = 0;
                if(aux == 0)
                    operacao = -1;
                else
                    operacao += aux;
            }
            clear();
            index = 0;
            ehSeguroPF = false;
            switch(operacao){
                case -1://Voltar
                    continue;
                case 0://Sair
                    break;
                case 1://Cadastrar cliente
                    //#region Escolha de pessoa(física/jurídica)
                    System.out.println("1: Pessoa física;");
                    System.out.println("2: Pessoa jurídica.");
                    System.out.print("▹");
                    operacao = tryInteger(scanner);
                    if(!dentroDoAlcance(2, operacao)){
                        clear();
                        continue;
                    }
                    //#endregion

                    //#region Coleta de dados
                    System.out.println("Nome:");
                    System.out.print("▹");
                    nome = scanner.nextLine();
                    if(!Validacao.validaNome(nome)){
                        clear();
                        System.out.println("Nome inválido.");
                        continue;
                    }

                    System.out.println("Telefone:");
                    System.out.print("▹");
                    telefone = scanner.nextLine();

                    System.out.println("Endereço:");
                    System.out.print("▹");
                    endereco = scanner.nextLine();

                    System.out.println("Email:");
                    System.out.print("▹");
                    email = scanner.nextLine();

                    if(operacao == 1){
                        //#region Coleta de dados da pessoa física
                        System.out.println("CPF:");
                        System.out.print("▹");
                        cpf = scanner.nextLine();
                        //#region Validação do CPF
                        if(!Validacao.validaCPF(cpf)){
                            clear();
                            System.out.println("CPF inválido.");
                            continue;
                        }
                        //#endregion

                        System.out.println("Gênero:");
                        System.out.print("▹");
                        genero = scanner.nextLine();

                        System.out.println("Educação:");
                        System.out.print("▹");
                        educacao = scanner.nextLine();
                        
                        System.out.println("Data de nascimento:");
                        System.out.print("▹");
                        dataNascimento = tryDate(scanner);
                        
                        cliente = new ClientePF(nome, telefone, endereco, email, cpf, genero, educacao, dataNascimento);
                        //#endregion
                    }else{
                        //#region Coleta de dados da pessoa jurídica
                        System.out.println("CNPJ:");
                        System.out.print("▹");
                        cnpj = scanner.nextLine();
                        //#region Validação do CNPJ
                        if(!Validacao.validaCNPJ(cnpj)){
                            clear();
                            System.out.println("CNPJ inválido.");
                            
                            operacao = 1;
                            continue;
                        }
                        //#endregion

                        System.out.println("Data de fundação(dd/MM/aaaa):");
                        System.out.print("▹");
                        dataFundacao = tryDate(scanner);
                        
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
                    placa = scanner.nextLine();
                    System.out.println("Marca:");
                    System.out.print("▹");
                    marca = scanner.nextLine();
                    System.out.println("Modelo:");
                    System.out.print("▹");
                    modelo = scanner.nextLine();
                    System.out.println("Ano de fabricação:");
                    System.out.print("▹");
                    anoFabricacao = tryInteger(scanner);
                    //#endregion

                    veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
                    
                    //#region Escolha de cliente
                    System.out.println("Qual o CPF/CNPJ do dono?");
                    if(!escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes())){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    cpf_cnpj = scanner.nextLine();
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                    if(index == -1){
                        clear();
                        System.out.println("CPF/CNPJ inválido");
                        
                        continue;
                    }
                    //#endregion

                    if(listaSeguradoras.get(indexSeguradora).listarClientes().get(index) instanceof ClientePF)
                        if(!((ClientePF)listaSeguradoras.get(indexSeguradora).listarClientes().get(index)).cadastrarVeiculo(veiculo)){
                            System.out.println("Veículo já foi registrado");
                            clear();
                            continue;
                        }
                    else{
                        System.out.println("Código da frota: ");
                        for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).listarClientes().get(index)).getListaFrota())
                            System.out.println(f.getCode());
                        System.out.print("▹");
                        code = scanner.nextLine();
                        if(!((ClientePJ)listaSeguradoras.get(indexSeguradora).listarClientes().get(index)).atualizarFrota(code, veiculo)){
                            System.out.println("Veículo já foi registrado");
                            clear();
                            continue;
                        }
                    }
                    break;
                case 3://Cadastrar frota
                    System.out.println("Escolha o CNPJ do cliente:");
                    //Lista os clientes PJ
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("juridica"))
                        System.out.println(c.getNome() + " | CNPJ: " + ((ClientePJ)c).getCnpj());
                    System.out.print("▹");
                    cnpj = scanner.nextLine();

                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes("juridica"), cnpj) == -1){
                        clear();
                        System.out.println("CNPJ inválido");
                        continue;
                    }

                    System.out.println("Código para a frota:");
                    System.out.print("▹");
                    code = scanner.nextLine();
                    frota = new Frota(code);
                    if(!((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cnpj)).cadastrarFrota(frota)){
                        System.out.println("Código existente");
                        clear();
                        continue;
                    }
                    break;
                case 4://Autorizar condutor
                    System.out.println("Nome do condutor:");
                    System.out.println("▹");
                    nome = scanner.nextLine();
                    if(!Validacao.validaNome(nome)){
                        clear();
                        System.out.println("Nome inválido.");
                        continue;
                    }
                    System.out.println("CPF do condutor:");
                    System.out.println("▹");
                    cpf = scanner.nextLine();
                    if(!Validacao.validaCPF(cpf)){
                        clear();
                        System.out.println("CPF inválido.");
                        continue;
                    }
                    System.out.println("Telefone:");
                    System.out.println("▹");
                    telefone = scanner.nextLine();
                    System.out.println("Endereço:");
                    System.out.println("▹");
                    endereco = scanner.nextLine();
                    System.out.println("Email:");
                    System.out.println("▹");
                    email = scanner.nextLine();
                    System.out.println("Data de nascimento: (dd/MM/aaaa)");
                    System.out.println("▹");
                    dataNascimento = tryDate(scanner);

                    System.out.println("Escolha o ID do seguro:");
                    if(!escolhaSeguro(listaSeguradoras.get(indexSeguradora))){
                        clear();
                        System.out.println("---Lista vazia---");
                        continue;
                    }
                    id = tryInteger(scanner);
                    
                    if(listaSeguradoras.get(indexSeguradora).getSeguro(id) == null){
                        clear();
                        System.out.println("ID inválido");
                        continue;
                    }

                    listaSeguradoras.get(indexSeguradora).getSeguro(id).autorizarCondutor(new Condutor(cpf, nome, telefone, endereco, email, dataNascimento));
                    break;
                case 5://Trocar de seguradora
                    indexSeguradora = selecionarSeguradora(listaSeguradoras, scanner);
                    break;
                case 6://Exibir cliente
                    //#region Escolha do tipo de cliente
                    System.out.println("O cliente pertence é:");
                    System.out.println("1: pessoa física;");
                    System.out.println("2: pessoa jurídica;");
                    System.out.println("3: Não tenho certeza.");
                    System.out.print("▹");
                    operacao = tryInteger(scanner);
                    if((operacao < 1) || (operacao > 3)){
                        System.out.println("Operação inválida.");
                        clear();
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
                        System.out.println("    •" + c.getNome() + " | " + (c instanceof ClientePF ? "CPF: " + ((ClientePF)c).getCpf() : "CNPJ: " + ((ClientePJ)c).getCnpj()));
                    System.out.print("▹");
                    cpf_cnpj = scanner.nextLine();
                    //#endregion
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente), cpf_cnpj);
                    if(index == -1){
                        clear();
                        System.out.println((Objects.equals(tipoCliente, "fisica") ? "CPF" : (Objects.equals(tipoCliente, "juridica")) ? "CNPJ" : "CPF/CNPJ") + " inválido");
                        
                        continue;
                    }
                    System.out.println(listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente).get(index));
                    break;
                case 7://Exibir veículos por frota
                    System.out.println("Escolha o CNPJ do cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("juridica")){
                        listaVazia = false;
                        System.out.println(c.getNome() + " | CNPJ: " + ((ClientePJ)c).getCnpj());
                    }
                    if(listaVazia){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    System.out.print("▹");
                    cnpj = scanner.nextLine();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes("juridica"), cnpj) == -1){
                        clear();
                        System.out.println("CPF/CNPJ inválido");
                        continue;
                    }
                    
                    for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cnpj)).getListaFrota()){
                        System.out.print("Frota " + f.getCode() + ":\n");
                        for(Veiculo v: f.getListaVeiculos())
                            System.out.println("\t" + v);
                    }
                    break;
                case 8://Exibir veículos por cliente
                    System.out.println("Escolha o CPF do cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("fisica")){
                        listaVazia = false;
                        System.out.println(c.getNome() + " | CPF: " + ((ClientePF)c).getCpf());
                    }
                    if(listaVazia){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    System.out.print("▹");
                    cpf = scanner.nextLine();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf) == -1){
                        clear();
                        System.out.println("CPF inválido");
                        continue;
                    }

                    for(Veiculo v: ((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf)).getListaVeiculos())
                        System.out.println(v);
                    break;
                case 9://Exibir seguros por cliente
                    System.out.println("Escolha o CPF/CNPJ do cliente: ");
                    if(!escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes())){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    cpf_cnpj = scanner.nextLine();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        clear();
                        System.out.println("CPF/CNPJ inválido");
                        
                        continue;
                    }

                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                        tipoCliente = "fisica";
                    else
                        tipoCliente = "juridica";
                    for(Seguro s: listaSeguradoras.get(indexSeguradora).getListaSeguros())
                        if(((Objects.equals(tipoCliente, "fisica")) && (s instanceof SeguroPF) && (Objects.equals(((SeguroPF)s).getClientePF(), listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj))))
                        || ((Objects.equals(tipoCliente, "juridica")) && (s instanceof SeguroPJ) && (Objects.equals(((SeguroPJ)s).getClientePJ(), listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)))))
                            System.out.println(s);
                    break;
                case 10://Exibir sinistros por cliente
                    System.out.println("Escolha o CPF/CNPJ do cliente: ");
                    if(!escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes())){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    cpf_cnpj = scanner.nextLine();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        clear();
                        System.out.println("CPF/CNPJ inválido");
                        continue;
                    }
                    
                    for(Seguro s: listaSeguradoras.get(indexSeguradora).getListaSeguros())
                        if(((s instanceof SeguroPF) && Objects.equals(((SeguroPF)s).getClientePF(), listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)))
                             //Se for SeguroPF verifica se o CPF é igual
                        || ((s instanceof SeguroPJ) && Objects.equals(((SeguroPJ)s).getClientePJ(), listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj))))
                             //Se for SeguroPJ verifica se o CNPJ é igual
                            for(Sinistro si: s.getListaSinistros())
                                System.out.println(si);
                    break;
                case 11://Exibir dados da seguradora
                    System.out.println(listaSeguradoras.get(indexSeguradora));
                    break;
                case 12://Deleta cliente
                    System.out.println("Escolha o CPF/CNPJ do cliente:");
                    if(!escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes())){
                        clear();
                        System.out.println("---Lista vazia---");
                        continue;
                    }
                    cpf_cnpj = scanner.nextLine();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        clear();
                        System.out.println("CPF/CNPJ inválido inválido");
                        continue;
                    }
                    listaSeguradoras.get(indexSeguradora).removerCliente(cpf_cnpj);
                    break;
                case 13://Desautorizar condutor
                    System.out.println("Qual o CPF/CNPJ do cliente?");
                    if(!escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes())){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    cpf_cnpj = scanner.nextLine();

                    if(indexCliente(listaSeguradoras.get(indexSeguradora).getListaClientes(), cpf_cnpj) == -1){
                        clear();
                        System.out.println("CPF/CNPJ inválido inválido");
                        continue;
                    }

                    System.out.println("Qual o CPF do condutor?");
                    for(Seguro s: listaSeguradoras.get(indexSeguradora).getSegurosPorCliente(cpf_cnpj)){
                        if(s.getListaCondutores().size() > 0)
                            listaVazia = false;
                        for(Condutor c: s.getListaCondutores())
                            System.out.println("Condutor: " + c.getNome() + " | CPF: " + c.getCpf());
                    }
                    if(listaVazia){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    System.out.print("▹");
                    cpf = scanner.nextLine();

                    condutor = listaSeguradoras.get(indexSeguradora).getCondutor(cpf_cnpj, cpf);
                    ID: for(Seguro s: listaSeguradoras.get(indexSeguradora).getSegurosPorCliente(cpf_cnpj))
                            for(Condutor c: s.getListaCondutores())
                                if(Objects.equals(c.getCpf().replaceAll("\\.|-|/", ""), cpf.replaceAll("\\.|-|/", ""))){
                                    id = s.getId();
                                    break ID;
                                }
                    if(!listaSeguradoras.get(indexSeguradora).getSeguro(id).desautorizarCondutor(condutor)){
                        clear();
                        System.out.println("CPF inválido inválido");
                        continue;
                    }
                    break;
                case 14://Deleta veículo
                    System.out.println("Escolha o CPF/CNPJ do cliente:");
                    if(!escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes())){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    cpf_cnpj = scanner.nextLine();
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                    if(index == -1){
                        clear();
                        System.out.println("CPF/CNPJ inválido");
                        continue;
                    }

                    System.out.println("Escolha um veículo(pela placa): ");
                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF){
                        listaVazia = false;
                        System.out.println(((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaVeiculos());
                    }else
                        for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaFrota()){
                            listaVazia = false;
                            System.out.println("Frota " + f.getCode() + ": " + f.getListaVeiculos());
                        }
                    if(listaVazia){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    System.out.print("▹");
                    placa = scanner.nextLine();
                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF){
                        if(!((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).removerVeiculo(placa)){
                            System.out.println("Placa inválida");
                            clear();
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
                    if(!escolhaClientes(listaSeguradoras.get(indexSeguradora).listarClientes())){
                        clear();
                        System.out.println("---Lista vazia---");
                        continue;
                    }
                    cpf_cnpj = scanner.nextLine();
                    index = indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj);
                    if(index == -1){
                        clear();    
                        System.out.println("CPF/CNPJ inválido");
                            continue;
                    }
                    if(listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj) instanceof ClientePF)
                        ehSeguroPF = true;
                    if(ehSeguroPF){
                        System.out.println("Escolha a placa do veículo");
                        for(Veiculo v: ((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaVeiculos()){
                            listaVazia = false;
                            System.out.println(v);
                        }
                        if(listaVazia){
                            clear();
                            System.out.println("---Lista vazia---");
                            continue;
                        }
                        placa = scanner.nextLine();
                        veiculo = ((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getVeiculo(placa);
                        if(veiculo == null){
                            System.out.println("Placa inválida");
                            clear();
                            continue;
                        }
                    }else{
                        System.out.println("Escolha o código da frota");
                        for(Frota f: ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getListaFrota()){
                            listaVazia = false;
                            System.out.println(f.getCode());
                        }
                        if(listaVazia){
                            clear();
                            System.out.println("---Lista vazia---");
                            continue;
                        }
                        code = scanner.nextLine();
                        frota = ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)).getFrota(code);
                        if(frota == null){
                            System.out.println("Frota inválida");
                            clear();
                            continue;
                        } 
                    }
                    System.out.println("Data de início do seguro:");
                    System.out.print("▹");
                    dataInicio = tryDate(scanner);
                    System.out.println("Data do fim do seguro:");
                    System.out.print("▹");
                    dataFim = tryDate(scanner);
                    if(ehSeguroPF){
                        if(!listaSeguradoras.get(indexSeguradora).gerarSeguro(listaSeguradoras.get(indexSeguradora), dataInicio, dataFim, veiculo, (ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)))
                            System.out.println("Veículo já possui seguro");
                    }else if(!listaSeguradoras.get(indexSeguradora).gerarSeguro(listaSeguradoras.get(indexSeguradora), dataInicio, dataFim, frota, (ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj)))
                        System.out.println("Frota já possui seguro");
                    clear();
                    break;
                case 16://Gerar sinistro
                    //#region Escolha de cliente
                    System.out.println("Qual o ID do seguro:");
                    if(!escolhaSeguro(listaSeguradoras.get(indexSeguradora))){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    id = tryInteger(scanner);
                    for(i = 0; i < listaSeguradoras.get(indexSeguradora).getListaSeguros().size(); i++){
                        if(listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getId() == id)
                            break;
                        else if(i == listaSeguradoras.get(indexSeguradora).getListaSeguros().size() - 1){
                            clear();
                            System.out.println("ID inválido");
                            continue;
                        }
                    }
                    //#endregion

                    System.out.println("Qual o CPF do condutor?");
                    for(Condutor c: listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).getListaCondutores()){
                        listaVazia = false;
                        System.out.println("Nome: " + c.getNome() + " | CPF: " + c.getCpf());
                    }
                    if(listaVazia){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    cpf = scanner.nextLine();

                    //#region Coleta de dados
                    System.out.println("Local do acidente:");
                    System.out.print("▹");
                    endereco = scanner.nextLine();
                    //#endregion

                    if(!listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i).gerarSinistro(endereco, cpf, listaSeguradoras.get(indexSeguradora).getListaSeguros().get(i))){
                        clear();
                        System.out.println("CPF inválido");
                        continue;
                    }
                    break;
                case 17://Transferir seguro
                    System.out.println("Escolha o ID do seguro: ");
                    if(!escolhaSeguro(listaSeguradoras.get(indexSeguradora))){
                        clear();
                        System.out.println("---Lista vazia---");
                        break;
                    }
                    System.out.print("▹");
                    id = tryInteger(scanner);

                    if(listaSeguradoras.get(indexSeguradora).getSeguro(id) == null){
                        clear();
                        System.out.println("ID inválido");
                        continue;
                    }
                    ehSeguroPF = listaSeguradoras.get(indexSeguradora).getSeguro(id) instanceof SeguroPF ? true : false;

                    System.out.println("Escolha o " + (ehSeguroPF ? "CPF" : "CNPJ") + " do cliente que irá receber o seguro: ");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes()){
                        listaVazia = false;
                        if(ehSeguroPF && c instanceof ClientePF)
                            System.out.println("Nome: " + c.getNome() + " | CPF: " + ((ClientePF)c).getCpf());
                        else if(!ehSeguroPF && c instanceof ClientePJ)
                            System.out.println("Nome: " + c.getNome() + " | CNPJ: " + ((ClientePJ)c).getCnpj());
                    }
                    if(listaVazia){
                        clear();
                        System.out.println();
                        break;
                    }
                    System.out.print("▹");
                    cpf_cnpj = scanner.nextLine();
                    if(indexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(), cpf_cnpj) == -1){
                        clear();
                        System.out.println((ehSeguroPF ? "CPF" : "CNPJ") + " inválido");
                        continue;
                    }
                    
                    if(ehSeguroPF)
                        ((SeguroPF)listaSeguradoras.get(indexSeguradora).getSeguro(id)).setClientePF((ClientePF)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
                    else
                        ((SeguroPJ)listaSeguradoras.get(indexSeguradora).getSeguro(id)).setClientePJ((ClientePJ)listaSeguradoras.get(indexSeguradora).getCliente(cpf_cnpj));
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
                    scanner.nextLine();
                }
                catch(Exception error){}
                clear();
            }
        }while(operacao != 0);
        scanner.close();
    }
}
