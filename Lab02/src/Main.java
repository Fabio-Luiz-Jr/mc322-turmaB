import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws Exception{
        Scanner entrada = new Scanner(System.in);
        int operacao, operacoesRealizadas = 0;
        boolean veiculoVazio = true,
                clienteVazio = true,
                seguradoraVazio = true,
                sinistroVazio = true;
        
        do{
            System.out.println("Digite a operação desejada:");
            if(veiculoVazio)
                System.out.println("1: Novo veículo;");
            if(clienteVazio)
                System.out.println("2: Novo cliente;");
            if(seguradoraVazio)
                System.out.println("3: Nova seguradora;");
            if(sinistroVazio)
                System.out.println("Novo sinistro;");
            System.out.println("9: Próximo.");
            operacao = entrada.nextInt();

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
                    veiculoVazio = false;
                    operacoesRealizadas++;
                    break;
                case 2:
                    clienteVazio = false;
                    operacoesRealizadas++;
                    break;
                case 3:
                    seguradoraVazio = false;
                    operacoesRealizadas++;
                    break;
                case 4:
                    sinistroVazio = false;
                    operacoesRealizadas++;
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Operação inválida.");
                    break;
            }
            System.out.println("-----------------------------------------------------------------------");
        } while(operacoesRealizadas < 4);


     entrada.close();       
    }
}
