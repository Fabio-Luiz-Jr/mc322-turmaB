import java.time.LocalDate;
import java.util.ArrayList;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public abstract class Seguro{
    private final int id;
    private static int id_aux = 0;
    private Date dataInicio;
    private Date dataFim;
    private Seguradora seguradora;
    private ArrayList<Sinistro> listaSinistros;
    private ArrayList<Condutor> listaCondutores;
    private double valorMensal;

    public Seguro(Date dataInicio, Date dataFim, Seguradora seguradora, double valorMensal){
        this.id = id_aux++;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.seguradora = seguradora;
        this.listaSinistros = new ArrayList<Sinistro>();
        this.listaCondutores = new ArrayList<Condutor>();
        this.valorMensal = valorMensal;
    }
    //#region Getters e Setters
    public int getId(){return this.id;}
    public Date getDataInicio(){return this.dataInicio;}
    public void setDataInicio(Date dataInicio){this.dataInicio = dataInicio;}
    public Date getDataFim(){return this.dataFim;}
    public void setDataFim(Date dataFim){this.dataFim = dataFim;}
    public Seguradora getSeguradora(){return this.seguradora;}
    public void setSeguradora(Seguradora seguradora){this.seguradora = seguradora;}
    public ArrayList<Sinistro> getListaSinistros(){return this.listaSinistros;}
    public void setListaSinistros(ArrayList<Sinistro> listaSinistros){this.listaSinistros = listaSinistros;}
    public ArrayList<Condutor> getListaCondutores(){return this.listaCondutores;}
    public void setListaCondutores(ArrayList<Condutor> listaCondutores){this.listaCondutores = listaCondutores;}
    public double getValorMensal(){return this.valorMensal;}
    public void setValorMensal(double d){this.valorMensal = d;}
    //#endregion
    @Override
    public String toString() {
        return "ID: " + getId() +
               "\nData de inicio: " + getDataInicio() +
               "\nData final: " + getDataFim() +
               "\nSeguradora: " + getSeguradora() +
               "\nLista de sinistros: " + getListaSinistros() +
               "\nLista de condutores: " + getListaCondutores() +
               "\nValor mensal: " + getValorMensal();
    }
    
    protected int calculaIdade(Date dataNascimento){
        LocalDate data = dataNascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(data, LocalDate.now()).getYears();
    }

    public abstract boolean desautorizarCondutor(Condutor condutor);
    public abstract boolean autorizarCondutor(Condutor condutor);
    public abstract double calcularValor();
    public abstract boolean gerarSinistro(String endereco, String cpf, Seguro seguro);
}
