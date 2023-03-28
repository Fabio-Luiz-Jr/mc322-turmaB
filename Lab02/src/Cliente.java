public class Cliente{
    private String nome;
    private String cpf;
    private String dataNascimento;
    private int idade;
    private String endereco;

    public Cliente(){}

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public String getDataNascimento(){
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento){
        this.dataNascimento = dataNascimento;
    }

    public int getIdade(){
        return idade;
    }

    public void setIdade(int idade){
        this.idade = idade;
    }

    public String getEndereco(){
        return endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public int digitoVerificador(String cpf, int caso){
        int soma = 0, aux = 0, digito;

        if(caso == 2)
            aux = 1;
        for(int i = 10 + aux; i >= 2; i--)
            soma += (cpf.charAt(10 + aux - i) - '0') * i;
        
        if((soma % 11 == 0) || (soma % 11 == 1))
            digito = 0;
        else
            digito = 11 - (soma % 11);
        
        return digito;
    }

    public boolean validarCPF(String cpf){
        int digito_1, digito_2;

        cpf = cpf.replaceAll("\\.|-", "");
        if(cpf.length() != 11)
            return false;
        
        digito_1 = digitoVerificador(cpf, 1);
        digito_2 = digitoVerificador(cpf, 2);
        if((digito_1 != cpf.charAt(9) - '0') || (digito_2 != cpf.charAt(10) - '0'))
            return false;

        return true;
    }
}
