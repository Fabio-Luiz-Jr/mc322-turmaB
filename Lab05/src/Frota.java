import java.util.*;
import java.util.stream.Collectors;

public class Frota{
    private String code;
    private ArrayList<Veiculo> listaVeiculos;

    public Frota(String code){
        this.code = code;
        this.listaVeiculos = new ArrayList<Veiculo>();
    }
    //#region Getters e Setters
    public String getCode(){return this.code;}
    public void setCode(String code){this.code = code;}
    public ArrayList<Veiculo> getListaVeiculos(){return this.listaVeiculos;}
    public void setListaVeiculos(ArrayList<Veiculo> listaVeiculos){this.listaVeiculos = listaVeiculos;}
    //#endregion
    @Override
    public String toString(){
        return "Code: " + code + 
               "\nLista de veiculos: " + listaVeiculos.stream().map(Object::toString).collect(Collectors.joining("\n                   "));
    }
    
    public boolean addVeiculo(Veiculo veiculo){
        if(getListaVeiculos().contains(veiculo))
            return false;
        this.listaVeiculos.add(veiculo);
        return true;
    }

    public boolean removeVeiculo(String placa){
        for(Veiculo v: getListaVeiculos())
            if(Objects.equals(v.getPlaca(), placa)){
                this.listaVeiculos.remove(v);
                return true;
            }
        return false;
    }
}
