import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws Exception{
        Scanner entrada = new Scanner(System.in).useDelimiter("\n");
        int id, idade, operacao, subOperacao, operacoesRealizadas = 0;
        Veiculo dadosVeiculo = null;
        Cliente dadosCliente = new Cliente();
        Seguradora dadosSeguradora = null;
        Sinistro dadosSinistro = null;
        String placa, marca, modelo, //Strings do Veiculo.
               nomeCliente, cpf, dataNascimento, enderecoCliente, //Strings do Cliente.
               nomeSeguradora, telefone, email, enderecoSeguradora, //Strings da Seguradora.
               data, enderecoSinistro; //Strings do Sinistro.
        boolean veiculoVazio = true,
                clienteVazio = true,
                seguradoraVazio = true,
                sinistroVazio = true,
                finalizar = false;
        
        do{
            System.out.println("Digite a operação desejada:");
            if(veiculoVazio)
                System.out.println("1: Novo veículo;");
            if(clienteVazio)
                System.out.println("2: Novo cliente;");
            if(seguradoraVazio)
                System.out.println("3: Nova seguradora;");
            if(sinistroVazio)
                System.out.println("4: Novo sinistro;");
            System.out.println("9: Próximo.");
            operacao = entrada.nextInt();
            System.out.println("");

            //Impede o usuário de executar uma mesma operação mais de uma vez.
            switch(operacao){
                case 1:
                    if(!veiculoVazio)
                        operacao = 0;
                    break;
                case 2:
                    if(!clienteVazio)
                        operacao = 0;
                    break;
                case 3:
                    if(!seguradoraVazio)
                        operacao = 0;
                    break;
                case 4:
                    if(!sinistroVazio)
                        operacao = 0;
                    break;
            }

            //Executa a operação escolhida pelo usuário e marca-a como preenchida.
            switch(operacao){
                case 1:
                    System.out.println("Placa: ");
                    placa = entrada.next();
                    System.out.println("Marca: ");
                    marca = entrada.next();
                    System.out.println("Modelo: ");
                    modelo = entrada.next();

                    dadosVeiculo = new Veiculo(placa, marca, modelo);

                    veiculoVazio = false;
                    operacoesRealizadas++;

                    break;
                case 2:
                    System.out.println("Nome: ");
                    nomeCliente = entrada.next();
                    System.out.println("CPF: ");
                    cpf = entrada.next();
                    if(!dadosCliente.validarCPF(cpf)){
                        System.out.println("CPF inválido.");
                        break;
                    }
                    System.out.println("Data de nascimento: ");
                    dataNascimento = entrada.next();
                    System.out.println("Idade: ");
                    idade = entrada.nextInt();
                    System.out.println("Endereço: ");
                    enderecoCliente = entrada.next();

                    dadosCliente.setNome(nomeCliente);
                    dadosCliente.setCpf(cpf);
                    dadosCliente.setDataNascimento(dataNascimento);
                    dadosCliente.setIdade(idade);
                    dadosCliente.setEndereco(enderecoCliente);

                    clienteVazio = false;
                    operacoesRealizadas++;

                    break;
                case 3:
                    System.out.println("Nome: ");
                    nomeSeguradora = entrada.next();
                    System.out.println("Telefone: ");
                    telefone = entrada.next();
                    System.out.println("Email: ");
                    email = entrada.next();
                    System.out.println("Endereço");
                    enderecoSeguradora = entrada.next();

                    dadosSeguradora = new Seguradora(nomeSeguradora, telefone, email, enderecoSeguradora);

                    seguradoraVazio = false;
                    operacoesRealizadas++;
                    
                    break;
                case 4:
                    System.out.println("Data: ");
                    data = entrada.next();
                    System.out.println("Endereço: ");
                    enderecoSinistro = entrada.next();

                    dadosSinistro = new Sinistro(data, enderecoSinistro);

                    sinistroVazio = false;
                    operacoesRealizadas++;
                    
                    break;
                case 9:
                    operacoesRealizadas = 4;
                    break;
                default:
                    System.out.println("Operação inválida.");
                    break;
            }
            System.out.println("-----------------------------------------------------------------------");
        } while(operacoesRealizadas < 4);

        //Permite o usuário a editar e visualizar os dados anteriormente criados.
        do{
            System.out.println("Digite a operação desejada: ");
            if(!veiculoVazio)
                System.out.println("1: Editar/Visualizar veículo;");
            if(!clienteVazio)
                System.out.println("2: Editar/Visualizar cliente;");
            if(!seguradoraVazio)
                System.out.println("3: Editar/Visualizar seguradora;");
            if(!sinistroVazio)
                System.out.println("4: Editar/Visualizar sinistro;");
            System.out.println("9: Finalizar.");
            operacao = entrada.nextInt();
            System.out.println("-----------------------------------------------------------------------");

            //Não deixa o usuário acessar uma classe na qual ele não preencheu.
            switch(operacao){
                case 1:
                    if(veiculoVazio)
                        operacao = 0;
                    break;
                case 2:
                    if(clienteVazio)
                        operacao = 0;
                    break;
                case 3:
                    if(seguradoraVazio)
                        operacao = 0;
                    break;
                case 4:
                    if(sinistroVazio)
                        operacao = 0;
                    break;
            }
            
            if(operacao == 1){
                System.out.println("1: Editar placa; ");
                System.out.println("2: Editar marca; ");
                System.out.println("3: Editar modelo; ");
                System.out.println("4: Visualizar placa; ");
                System.out.println("5: Visualizar marca; ");
                System.out.println("6: Visualizar modelo.");
                subOperacao = entrada.nextInt();
                System.out.println("-----------------------------------------------------------------------");

                switch(subOperacao){
                    case 1:
                        System.out.println("Placa: ");
                        placa = entrada.next();
                        dadosVeiculo.setPlaca(placa);
                        break;
                    case 2:
                        System.out.println("Marca: ");
                        marca = entrada.next();
                        dadosVeiculo.setMarca(marca);
                        break;
                    case 3:
                        System.out.println("Modelo: ");
                        modelo = entrada.next();
                        dadosVeiculo.setModelo(modelo);
                        break;
                    case 4:
                        placa = dadosVeiculo.getPlaca();
                        System.out.println(placa);
                        break;
                    case 5:
                        marca = dadosVeiculo.getMarca();
                        System.out.println(marca);
                        break;
                    case 6:
                        modelo = dadosVeiculo.getModelo();
                        System.out.println(modelo);
                        break;
                    default:
                        System.out.println("Operação inválida.");
                        break;
                }
            }else if(operacao == 2){
                System.out.println("0: Editar o nome; ");
                System.out.println("1: Editar o CPF; ");
                System.out.println("2: Editar a data de nascimento; ");
                System.out.println("3: Editar a idade; ");
                System.out.println("4: Editar o endereço; ");
                System.out.println("5: Visualizar o nome; ");
                System.out.println("6: Visualizar o CPF; ");
                System.out.println("7: Visualizar a data de nascimento; ");
                System.out.println("8: Visualizar a idade; ");
                System.out.println("9: Visualizar o endereco.");
                subOperacao = entrada.nextInt();

                switch(subOperacao){
                    case 0:
                        System.out.println("Nome: ");
                        nomeCliente = entrada.next();
                        dadosCliente.setNome(nomeCliente);
                        break;
                    case 1:
                    System.out.println("CPF: ");
                        cpf = entrada.next();
                        if(!dadosCliente.validarCPF(cpf)){
                            System.out.println("CPF inválido.");
                            break;
                        }
                        dadosCliente.setCpf(cpf);
                        break;
                    case 2:
                        System.out.println("Data de nascimento: ");
                        dataNascimento = entrada.next();
                        dadosCliente.setDataNascimento(dataNascimento);
                        break;
                    case 3:
                        System.out.println("Idade: ");
                        idade = entrada.nextInt();
                        dadosCliente.setIdade(idade);
                        break;
                    case 4:
                        System.out.println("Endereço: ");
                        enderecoCliente = entrada.next();
                        dadosCliente.setEndereco(enderecoCliente);
                        break;
                    case 5:
                        nomeCliente = dadosCliente.getNome();
                        System.out.println(nomeCliente);
                        break;
                    case 6:
                        cpf = dadosCliente.getCpf();
                        System.out.println(cpf);
                        break;
                    case 7:
                        dataNascimento = dadosCliente.getDataNascimento();
                        System.out.println(dataNascimento);
                        break;
                    case 8:
                        idade = dadosCliente.getIdade();
                        System.out.println(idade);
                        break;
                    case 9:
                        enderecoCliente = dadosCliente.getEndereco();
                        System.out.println(enderecoCliente);
                        break;
                    default:
                        System.out.println("Operação inválida.");
                        break;
                }
            }else if(operacao == 3){
                System.out.println("1: Editar o nome; ");
                System.out.println("2: Editar o telefone; ");
                System.out.println("3: Editar o email; ");
                System.out.println("4: Editar o endereço; ");
                System.out.println("5: Visualizar o nome; ");
                System.out.println("6: Visualizar o telefone; ");
                System.out.println("7: Visualizar o email; ");
                System.out.println("8: Visualizar o endereço.");
                subOperacao = entrada.nextInt();

                switch(subOperacao){
                    case 1:
                        System.out.println("Nome: ");
                        nomeSeguradora = entrada.next();
                        dadosSeguradora.setNome(nomeSeguradora);
                        break;
                    case 2:
                        System.out.println("Telefone: ");
                        telefone = entrada.next();
                        dadosSeguradora.setTelefone(telefone);
                        break;
                    case 3:
                        System.out.println("Email: ");
                        email = entrada.next();
                        dadosSeguradora.setEmail(email);
                        break;
                    case 4:
                        System.out.println("Endereço: ");
                        enderecoSeguradora = entrada.next();
                        dadosSeguradora.setEndereco(enderecoSeguradora);
                        break;
                    case 5:
                        nomeSeguradora = dadosSeguradora.getNome();
                        System.out.println(nomeSeguradora);
                        break;
                    case 6:
                        telefone = dadosSeguradora.getTelefone();
                        System.out.println(telefone);
                        break;
                    case 7:
                        email = dadosSeguradora.getEmail();
                        System.out.println(email);
                        break;
                    case 8:
                        enderecoSeguradora = dadosSeguradora.getEndereco();
                        System.out.println(enderecoSeguradora);
                        break;
                    default:
                        System.out.println("Operação inválida.");
                        break;
                }
            }else if(operacao == 4){
                System.out.println("1: Atualizar o ID; ");
                System.out.println("2: Editar a data; ");
                System.out.println("3: Editar o endereço; ");
                System.out.println("4: Visualizar o ID; ");
                System.out.println("5: Visualizar a data; ");
                System.out.println("6: Visualizar o endereço.");
                subOperacao = entrada.nextInt();

                switch(subOperacao){
                    case 1:
                        dadosSinistro.setId();
                        break;
                    case 2:
                        System.out.println("Data: ");
                        data = entrada.next();
                        dadosSinistro.setData(data);
                        break;
                    case 3:
                        System.out.println("Endereço: ");
                        enderecoSinistro = entrada.next();
                        dadosSinistro.setEndereco(enderecoSinistro);
                        break;
                    case 4:
                        id = dadosSinistro.getId();
                        System.out.println(id);
                        break;
                    case 5:
                        data = dadosSinistro.getData();
                        System.out.println(data);
                        break;
                    case 6:
                        enderecoSinistro = dadosSinistro.getEndereco();
                        System.out.println(enderecoSinistro);
                        break;
                    default:
                        System.out.println("Operação inválida.");
                        break;
                }
            }else if(operacao == 9)
                finalizar = true;
            else
                System.out.println("Operação inválida");
            
            System.out.println("-----------------------------------------------------------------------");
        } while(!finalizar);

     entrada.close();     
    }
}
