import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main{

    public int selecionarSeguradora(ArrayList<Seguradora> listaSeguradoras, Scanner entrada){
        String nome, telefone, email, endereco;
        int index, indexSeguradora;
        boolean valido = true;
        do{
            System.out.println("Escolha uma seguradora:");
            index = 0;
            for(Seguradora s: listaSeguradoras){
                index++;
                System.out.println(index + ": " + s.getNome() + ";");
            }
            System.out.println("0: Criar nova seguradora.");
            indexSeguradora = entrada.nextInt();
            if((indexSeguradora < 0) || (indexSeguradora > index)){
                System.out.println("Escolha inválida.");
                valido = false;
            }
            System.out.println("------------------------------------------------------------------");
            }while(!valido);
    
        if(indexSeguradora == 0){
            indexSeguradora = index;
            System.out.println("Dados da nova seguradora:");
            System.out.println("Nome:");
            nome = entrada.next();
            System.out.println("Telefone:");
            telefone = entrada.next();
            System.out.println("Email:");
            email = entrada.next();
            System.out.println("Endereço:");
            endereco = entrada.next();
            listaSeguradoras.add(new Seguradora(nome, telefone, email, endereco));
            System.out.println("------------------------------------------------------------------");
        }
        System.out.println("Seguradora " + listaSeguradoras.get(indexSeguradora).getNome() + " selecionada.");
        System.out.println();
        return indexSeguradora;
    }

    public void criaDadosIniciais(ArrayList<Seguradora> listaSeguradoras) throws ParseException{
        String cpf, cnpj;
        Veiculo veiculo;
        ClientePF clientePF = null;
        ClientePJ clientePJ = null;
        Date dataLiscenca, dataNascimento, dataFundacao;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
        ArrayList<Sinistro> listaSinistros = new ArrayList<Sinistro>();

        //#region Cria dados
        listaSeguradoras.add(new Seguradora("Inovação", "(11)97684-9867",
                "seguradora_inovacao@gmail.com", "R. Jardim da Jabaquara, 13"));

        dataLiscenca = sdf.parse("21/03/2022");
        dataNascimento = sdf.parse("02/04/1983");
        cpf = "639.110.630-40";
        if (new ClientePF(cpf).validarCPF(cpf))
            clientePF = new ClientePF("João Carlos", "Rua Dom Pedro I, 14", dataLiscenca,
                    "Superior completo", "Masculino", "Média",
                    cpf, dataNascimento);

        dataLiscenca = sdf.parse("29/11/2018");
        dataFundacao = sdf.parse("09/02/1991");
        cnpj = "99.793.488/0001-19";
        if (new ClientePJ(cnpj).validarCNPJ(cnpj))
            clientePJ = new ClientePJ("Júlia Santos", "Rua da Alegria, 17", dataLiscenca,
                    "Superior completo", "Feminino", "Média",
                    cnpj, dataFundacao);

        veiculo = new Veiculo("HBG-8769", "Fiat", "Cronos", 2020);
        clientePF.addVeiculo(veiculo);

        veiculo = new Veiculo("JGU-7946", "Citroën", "C4 Cactus", 2019);
        clientePJ.addVeiculo(veiculo);

        listaSeguradoras.get(0).cadastrarCliente(clientePF);
        listaSeguradoras.get(0).cadastrarCliente(clientePJ);

        dataLiscenca = sdf.parse("07/09/2001");
        dataFundacao = sdf.parse("12/04/2021");
        cnpj = "91.195.689/0001-02";
        if (new ClientePJ(cnpj).validarCNPJ(cnpj))
            clientePJ = new ClientePJ("Pedro Henrique", "Rua Machado de Assis, 223", dataLiscenca,
                    "Médio incompleto", "Masculino", "Baixa",
                    cnpj, dataFundacao);

        veiculo = new Veiculo("CUF-0663", "Volkswagen", "Gol", 2014);
        clientePJ.addVeiculo(veiculo);

        listaSeguradoras.get(0).cadastrarCliente(clientePJ);

        listaSeguradoras.get(0).removerCliente("Júlia Santos");
        //#endregion

        if(listaSeguradoras.get(0).gerarSinistro()){
            listaSeguradoras.get(0).listarSinistros().get(0).setCliente(clientePJ);
            listaSeguradoras.get(0).listarSinistros().get(0).setEndereco(clientePJ.getEndereco());
            listaSeguradoras.get(0).listarSinistros().get(0).setSeguradora(listaSeguradoras.get(0));
            listaSeguradoras.get(0).listarSinistros().get(0).setVeiculo(clientePJ.getListaVeiculos().get(0));
        }

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
        //#endregion
    }
    public static void main(String[] args) throws Exception{
        //#region Variáveis
        Scanner entrada = new Scanner(System.in).useDelimiter("\n");
        ArrayList<Seguradora> listaSeguradoras = new ArrayList<Seguradora>();
        Seguradora seguradora;
        Veiculo veiculo;
        ClientePF clientePF = null;
        ClientePJ clientePJ = null;
        Sinistro sinistro;
        int anoFabricacao, operacao = 0, indexSeguradora;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataLiscenca, dataNascimento, dataFundacao;
        String data, placa, marca, modelo, nome, telefone, email, endereco, educacao, genero, classeEconomica, cpf, cnpj;
        boolean  sinistroPossivel = false;
        //#endregion

        new Main().criaDadosIniciais(listaSeguradoras);

        indexSeguradora = new Main().selecionarSeguradora(listaSeguradoras, entrada);

        do{
            seguradora = listaSeguradoras.get(indexSeguradora);
            if(seguradora.listarClientes("todos").size() > 0)
                for(int i = 0; i < seguradora.listarClientes("todos").size(); i++)
                    if(seguradora.listarClientes("todos").get(i).getListaVeiculos().size() > 0){
                        sinistroPossivel = true;
                        break;
                    }
            System.out.println("Escolha uma operação:");
            System.out.println("1: Adicionar cliente;");
            System.out.println("2: Adicionar veículo;");
            if(sinistroPossivel)
                    System.out.println("3: Adicionar sinistro;");
            System.out.println("4: Exibir dados da seguradora;");
            System.out.println("5: Exibir dados do cliente;");
            System.out.println("6: Exibir sinistros;");
            System.out.println("7: Trocar de seguradora;");
            System.out.println("0: Encerrar.");
            
        }while(operacao != 0);
        entrada.close();
    }
}
