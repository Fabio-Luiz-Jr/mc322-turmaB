import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main{

    private static int selecionarSeguradora(ArrayList<Seguradora> listaSeguradoras, Scanner entrada){
        String nome, telefone, email, endereco;
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
            System.out.println("Nome:");
            System.out.print("▹");
            nome = entrada.next();
            System.out.println("Telefone:");
            System.out.print("▹");
            telefone = entrada.next();
            System.out.println("Email:");
            System.out.print("▹");
            email = entrada.next();
            System.out.println("Endereço:");
            System.out.print("▹");
            endereco = entrada.next();
            listaSeguradoras.add(new Seguradora(nome, telefone, email, endereco));
            System.out.println("------------------------------------------------------------------");
        }
        //#endregion
        else
            indexSeguradora--;
        System.out.println("Seguradora " + listaSeguradoras.get(indexSeguradora).getNome() + " selecionada.");
        System.out.println();
        return indexSeguradora;
    }

    private static void criaDadosIniciais(ArrayList<Seguradora> listaSeguradoras) throws ParseException{
        String cpf, cnpj;
        Veiculo veiculo;
        ClientePF clientePF = null;
        ClientePJ clientePJ = null;
        Date dataLiscenca, dataNascimento, dataFundacao;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
        ArrayList<Sinistro> listaSinistros = new ArrayList<Sinistro>();

        //#region Cria dados
        listaSeguradoras.add(new Seguradora("Inovação", "(11)97684-9867",
                "seguradora_inovacao@gmail.com", "R. Jardim da Jabaquara, 13"));

        dataLiscenca = sdf.parse("21/03/2022");
        dataNascimento = sdf.parse("02/04/1983");
        cpf = "639.110.630-40";
        if(new ClientePF(cpf).validarCPF(cpf))
            clientePF = new ClientePF("João Carlos", "Rua Dom Pedro I, 14", dataLiscenca,
                    "Superior completo", "Masculino", "Média",
                    cpf, dataNascimento);

        dataLiscenca = sdf.parse("29/11/2018");
        dataFundacao = sdf.parse("09/02/1991");
        cnpj = "99.793.488/0001-19";
        if(new ClientePJ(cnpj).validarCNPJ(cnpj))
            clientePJ = new ClientePJ("Júlia Santos", "Rua da Alegria, 17", cnpj, dataFundacao);

        veiculo = new Veiculo("HBG-8769", "Fiat", "Cronos", 2020);
        clientePF.addVeiculo(veiculo);

        veiculo = new Veiculo("JGU-7946", "Citroën", "C4 Cactus", 2019);
        clientePJ.addVeiculo(veiculo);

        listaSeguradoras.get(0).cadastrarCliente(clientePF);
        listaSeguradoras.get(0).cadastrarCliente(clientePJ);

        dataLiscenca = sdf.parse("07/09/2001");
        dataFundacao = sdf.parse("12/04/2021");
        cnpj = "91.195.689/0001-02";
        if(new ClientePJ(cnpj).validarCNPJ(cnpj))
            clientePJ = new ClientePJ("Pedro Henrique", "Rua Machado de Assis, 223", cnpj, dataFundacao);

        veiculo = new Veiculo("CUF-0663", "Volkswagen", "Gol", 2014);
        clientePJ.addVeiculo(veiculo);

        listaSeguradoras.get(0).cadastrarCliente(clientePJ);

        listaSeguradoras.get(0).removerCliente("Júlia Santos");
        //#endregion

        //#region Gera sinistro
        if(listaSeguradoras.get(0).gerarSinistro(clientePJ.getEndereco(), listaSeguradoras.get(0),
           clientePJ.getListaVeiculos().get(0), clientePJ.getNome()))
            System.out.println("Sinistro gerado com sucesso");
        else
            System.out.println("Erro");
        System.out.println();
        //#endregion

        listaClientes = listaSeguradoras.get(0).listarClientes("fisica");
        if(!listaSeguradoras.get(0).visualizarSinistro("Pedro Henrique"))
            System.out.println("O cliente não existe");
        listaSinistros = listaSeguradoras.get(0).listarSinistros();

        //#region toString()
        listaClientes.get(0).toString();
        clientePF.toString();
        listaClientes = listaSeguradoras.get(0).listarClientes("juridica");
        clientePJ.toString();
        listaSinistros.get(0).toString();
        veiculo.toString();
        System.out.println();
        System.out.println();
        //#endregion
    }

    private static int procuraIndexCliente(ArrayList<Cliente> cliente, String nome){
        int index = 0;
        for(Cliente c: cliente){
            if(Objects.equals(c.getNome(), nome))
                return index;
            index++;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception{
        //#region Variáveis
        Scanner entrada = new Scanner(System.in).useDelimiter("\n");
        ArrayList<Seguradora> listaSeguradoras = new ArrayList<Seguradora>();
        Seguradora seguradora;
        Veiculo veiculo;
        ClientePF clientePF = null;
        ClientePJ clientePJ = null;
        int anoFabricacao, operacao = 0, index, indexSeguradora;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataLiscenca, dataNascimento, dataFundacao;
        String placa, marca, modelo, nome, endereco, educacao, genero, classeEconomica, cpf, cnpj, tipoCliente;
        boolean  sinistroPossivel, existeCliente, lerNovamente;
        //#endregion

        //Condições solicitadas no enunciado
        criaDadosIniciais(listaSeguradoras);

        indexSeguradora = selecionarSeguradora(listaSeguradoras, entrada);
        System.out.println("Seguradora selecionada: " + listaSeguradoras.get(indexSeguradora).getNome());
        System.out.println();

        do{
            //#region Decide como as variáveis do tipo boolean devem ser iniciadas a cada loop
            lerNovamente = true;
            existeCliente = false;
            sinistroPossivel = false;
            seguradora = listaSeguradoras.get(indexSeguradora);
            if(seguradora.listarClientes("todos").size() > 0){
                existeCliente = true;
                for(int i = 0; i < seguradora.listarClientes("todos").size(); i++)
                    if(seguradora.listarClientes("todos").get(i).getListaVeiculos().size() > 0){
                        sinistroPossivel = true;
                        break;
                    }
            }
            //#endregion

            //#region Escolha de operação
            System.out.println("Escolha uma operação:");
            System.out.println("1: Novo(cliente/veículo/sinistro);");
            System.out.println("2: Exibir(cliente/seguradora/sinistros);");
            System.out.println("3: Excluir cliente;");
            System.out.println("4: Trocar de seguradora;");
            System.out.println("0: Encerrar");
            System.out.print("▹");
            operacao = entrada.nextInt();

            switch(operacao){
                case 0:
                    lerNovamente = false;
                    break;
                case 1:
                    System.out.println("•1: Adicionar cliente;");
                    if(existeCliente)
                        System.out.println("•2: Adicionar veículo;");
                    if(sinistroPossivel)
                        System.out.println("•3: Adicionar sinistro;");
                    operacao = 0;
                    break;
                case 2:
                    System.out.println("•1: Exibir dados da seguradora;");
                    System.out.println("•2: Exibir sinistros;");
                    if(existeCliente)
                        System.out.println("•3: Exibir dados do cliente;");
                    operacao = 3;
                    break;
                case 3:
                case 4:
                    operacao += 4;
                    lerNovamente = false;
                    break;
                default:
                    System.out.println("Operação inválida");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println();
                    continue;
            }
            //Altera a operação para o seu equivalente no switch case
            if(lerNovamente){
                System.out.print("▹");
                operacao += entrada.nextInt();
            }
            //Altera a operação caso o usuário selecione uma operação não disponível no momento
            if(((operacao == 2) && (!existeCliente)) || ((operacao == 3) && (!sinistroPossivel)) || ((operacao == 6) && (!existeCliente)))
                operacao = -1;
            
            System.out.println("------------------------------------------------------------------");
            System.out.println();
            //#endregion

            index = 0;
            switch(operacao){
                case 0://Encerrar
                    break;
                case 1://Adicionar cliente
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
                    System.out.println("Endereço:");
                    System.out.print("▹");
                    endereco = entrada.next();
                    if(operacao == 1){
                        //#region Coleta de dados da pessoa física
                        System.out.println("CPF:");
                        System.out.print("▹");
                        cpf = entrada.next();
                        //#region Validação do CPF
                        if(!new ClientePF(cpf).validarCPF(cpf)){
                            System.out.println("CPF inválido.");
                            System.out.println("------------------------------------------------------------------");
                            System.out.println();
                            continue;
                        }
                        //#endregion
                        System.out.println("Gênero:");
                        System.out.print("▹");
                        genero = entrada.next();
                        System.out.println("Data da liscença(dd/MM/aaaa):");
                        System.out.print("▹");
                        dataLiscenca = sdf.parse(entrada.next());
                        System.out.println("Educação:");
                        System.out.print("▹");
                        educacao = entrada.next();
                        System.out.println("Data de nascimento:");
                        System.out.print("▹");
                        dataNascimento = sdf.parse(entrada.next());
                        System.out.println("Classe econômica:");
                        System.out.print("▹");
                        classeEconomica = entrada.next();
                        clientePF = new ClientePF(nome, endereco, dataLiscenca, educacao, genero, classeEconomica,
                                                  cpf, dataNascimento);
                        listaSeguradoras.get(indexSeguradora).cadastrarCliente(clientePF);
                        //#endregion
                    }else{
                        //#region Coleta de dados da pessoa jurídica
                        System.out.println("CNPJ:");
                        System.out.print("▹");
                        cnpj = entrada.next();
                        //#region Validação do CNPJ
                        if(!new ClientePJ(cnpj).validarCNPJ(cnpj)){
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
                        clientePJ = new ClientePJ(nome, endereco, cnpj, dataFundacao);
                        listaSeguradoras.get(indexSeguradora).cadastrarCliente(clientePJ);
                        //#endregion
                    }
                    //#endregion
                    break;
                case 2://Adicionar veículo
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
                    System.out.println("Quem é o dono do veículo?");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("todos"))
                        System.out.println("    •" + c.getNome());
                    System.out.print("▹");
                    nome = entrada.next();
                    index = procuraIndexCliente(listaSeguradoras.get(indexSeguradora).listarClientes("todos"), nome);
                    if(index < 0){
                        System.out.println("Nome inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    //#endregion

                    listaSeguradoras.get(indexSeguradora).listarClientes("todos").get(index).addVeiculo(veiculo);
                    break;
                case 3://Adicionar sinistro
                    //#region Escolha de cliente
                    System.out.println("Gerar sinistro para qual cliente?");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("todos"))
                        System.out.println("    •" + c.getNome());
                        System.out.print("▹");
                    nome = entrada.next();
                    index = procuraIndexCliente(listaSeguradoras.get(indexSeguradora).listarClientes("todos"), nome);
                    if(index < 0){
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
                    if(!listaSeguradoras.get(indexSeguradora).gerarSinistro(endereco, seguradora, veiculo, nome))
                        System.out.println("Cliente inválido");
                    break;
                case 4://Exibir dados da seguradora
                    System.out.println("Nome: " + listaSeguradoras.get(indexSeguradora).getNome());
                    System.out.println("Telefone: " + listaSeguradoras.get(indexSeguradora).getTelefone());
                    System.out.println("Email: " + listaSeguradoras.get(indexSeguradora).getEmail());
                    System.out.println("Endereço: " + listaSeguradoras.get(indexSeguradora).getEndereco());
                    System.out.println("Lista de sinistros:");
                    if(sinistroPossivel)
                        for(Sinistro s: listaSeguradoras.get(indexSeguradora).listarSinistros())
                            System.out.println("    •" + s);
                    else
                        System.out.println("--Lista vazia--");
                    System.out.println("Lista de clientes:");
                    if(existeCliente)
                        for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("todos"))
                            System.out.println("    •" + c);
                    else
                        System.out.println("--Lista vazia--");
                    break;
                case 5://Exibir dados dos sinistros
                    for(Sinistro s: listaSeguradoras.get(indexSeguradora).listarSinistros()){
                        System.out.println("ID: " + s.getId());
                        System.out.println("Data: " + s.getData());
                        System.out.println("Endereço: " + s.getEndereco());
                        System.out.println("Seguradora: " + s.getSeguradora().getNome());
                        System.out.println("Veículo: " + s.getVeiculo());
                        System.out.println("Cliente: " + s.getCliente());
                        System.out.println("------------------------------------------------------------------");
                    }
                    break;
                case 6://Exibir cliente
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
                    index = procuraIndexCliente(listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente), nome);
                    if(index < 0){
                        System.out.println("Nome inválido");
                        System.out.println("------------------------------------------------------------------");
                        System.out.println();
                        continue;
                    }
                    //#endregion

                    System.out.println(listaSeguradoras.get(indexSeguradora).listarClientes(tipoCliente).get(index).toString());
                    break;
                case 7://Deleta cliente
                    System.out.println("Escolha o cliente:");
                    for(Cliente c: listaSeguradoras.get(indexSeguradora).listarClientes("todos"))
                        System.out.println("    •" + c.getNome());
                    System.out.print("▹");
                    nome = entrada.next();
                    if(!listaSeguradoras.get(indexSeguradora).removerCliente(nome))
                        System.out.println("Nome inválido");
                    break;
                case 8://Trocar de seguradora
                    System.out.print("▹");
                    indexSeguradora = selecionarSeguradora(listaSeguradoras, entrada);
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
