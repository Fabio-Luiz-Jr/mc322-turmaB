import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class ClientePF extends Cliente{
    private final String cpf;
    private String genero;
    private Date dataLiscenca;
    private String educacao;
    private Date dataNascimento;
    private String classeEconomica;

    public ClientePF(String nome, String endereco, Date dataLiscenca, String educacao, String genero,
                     String classeEconomica, String cpf, Date dataNascimento){
        super(nome, endereco);
        this.cpf = cpf;
        this.genero = genero;
        this.dataLiscenca = dataLiscenca;
        this.educacao = educacao;
        this.dataNascimento = dataNascimento;
        this.classeEconomica = classeEconomica;
    }

    public String getCpf(){
        return this.cpf;
    }

    public String getGenero(){
        return this.genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public Date getDataLiscenca(){
        return this.dataLiscenca;
    }

    public void setDataLiscenca(Date dataLiscenca){
        this.dataLiscenca = dataLiscenca;
    }

    public String getEducacao(){
        return this.educacao;
    }

    public void setEducacao(String educacao){
        this.educacao = educacao;
    }

    public Date getDataNascimento(){
        return this.dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento){
        this.dataNascimento = dataNascimento;
    }

    public String getClasseEconomica(){
        return this.classeEconomica;
    }

    public void setClasseEconomica(String classeEconomica){
        this.classeEconomica = classeEconomica;
    }

    @Override
    public String toString(){
        return super.toString() +
            "CPF: " + getCpf() + "\n" +
            "Gênero: " + getGenero() + "\n" +
            "Data da liscença: " + getDataLiscenca() + "\n" +
            "Educação: " + getEducacao() + "\n" +
            "Data de nascimento: " + getDataNascimento() + "\n" +
            "Classe econômica: " + getClasseEconomica() + "\n";
    }

    public double calculaScore(){
        int caso = 0;
        double valor;
        Calendar data = Calendar.getInstance();
        data.setTime(dataNascimento);
        int idade = Period.between(LocalDate.of(data.get(Calendar.YEAR), data.get(Calendar.MONTH), data.get(Calendar.DAY_OF_MONTH)), LocalDate.now()).getYears();

        if((idade >= 18) && (idade < 30))
            caso = 1;
        else if((idade >= 30) && (idade <  60))
            caso = 2;
        else if((idade >= 60) && (idade < 90))
            caso = 3;
        
            switch(caso){
                case 1:
                    valor = CalcSeguro.FATOR_18_30.getValor();
                    break;
                case 2:
                    valor = CalcSeguro.FATOR_30_60.getValor();
                    break;
                case 3:
                    valor = CalcSeguro.FATOR_60_90.getValor();
                    break;
                default:
                    System.out.println("Idade inválida");
                    return -1;
            }
        return CalcSeguro.VALOR_BASE.getValor() * valor * getListaVeiculos().size();
    }
}
