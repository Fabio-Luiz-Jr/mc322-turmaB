import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public abstract class Seguro{
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final int id;
    private static int id_aux = 0;
    private Date dataInicio;
    private Date dataFim;
    private Seguradora seguradora;
    private ArrayList<Sinistro> listaSinistros;
    private ArrayList<Condutor> listaCondutores;
    private double valorMensal;

    public Seguro(Date dataInicio, Date dataFim, Seguradora seguradora){
        this.id = ++id_aux;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.seguradora = seguradora;
        this.listaSinistros = new ArrayList<Sinistro>();
        this.listaCondutores = new ArrayList<Condutor>();
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
    public void setValorMensal(double valorMensal){this.valorMensal = valorMensal;}
    //#endregion
    @Override
    public String toString(){
        StringBuilder listaSinistros = new StringBuilder(),
                      listaCondutores = new StringBuilder();
        for(Sinistro s: this.listaSinistros){
            listaSinistros.append("\n\t" + s.getId());
            listaSinistros.append("\n\t\tData: " + s.getData());
            listaSinistros.append("\n\t\tLocal: " + s.getEndereco());
            listaSinistros.append("\n\t\tCondutor: " + s.getCondutor().getNome() + " | CPF: " + s.getCondutor().getCpf());
        }
        for(Condutor c: this.listaCondutores)
            listaCondutores.append("\n\tNome: " + c.getNome() + " | CPF: " + c.getCpf());
        return "ID: " + id +
               "\nData de inicio: " + sdf.format(dataInicio) +
               "\nData final: " + sdf.format(dataFim) +
               "\nSeguradora: " + seguradora.getNome() + " | CNPJ: " + seguradora.getCnpj() +
               "\nLista de sinistros: " + listaSinistros +
               "\nLista de condutores: " + listaCondutores +
               "\nValor mensal: " + valorMensal;
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
