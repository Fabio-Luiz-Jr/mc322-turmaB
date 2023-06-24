import java.util.*;

public class Validacao{
    private final static List<Character> charEspeciais = new ArrayList<>(Arrays.asList('æ','á','à','ã','â','ä','å','ç','é','è','ê','ë','í','ì','î','ï','ñ','ó','ò','õ','ô','ö','ú','ù','û','ü','ÿ','Æ','Á','À','Ã','Â','Ä','Å','Ç','É','È','Ê','Ë','Í','Ì','Î','Ï','Ñ','Ó','Ò','Õ','Ô','Ö','Ú','Ù','Û','Ü'));
    private static int digitoVerificador(String sequenciaNumerica, int digVerificador){
        int soma = 0, aux = 0, digito, identificador = sequenciaNumerica.length();

        switch(identificador){
            case 11:
                //#region Converte cada caracter em int e aplica a fórmula adequada para calcular os digitos verificadores
                if(digVerificador == 2)
                    aux = 1;
                for(int i = 10 + aux; i >= 2; i--)
                    soma += (sequenciaNumerica.charAt(10 + aux - i) - '0') * i;
                
                if((soma % 11 == 0) || (soma % 11 == 1))
                    digito = 0;
                else
                    digito = 11 - (soma % 11);
                //#endregion
                
                return digito;
            case 14:
                //#region Converte cada caracter em int e aplica a fórmula adequada para calcular os digitos verificadores
                if(digVerificador == 1)
                    aux = 1;
                for(int i = 5 + aux; i <= 9; i++)
                    soma += (sequenciaNumerica.charAt(i - 5 - aux) - '0') * i;
                for(int i = 2; i <= 9; i++)
                    soma += (sequenciaNumerica.charAt(3 - aux + i) - '0') * i;
                digito = (soma % 11) % 10;
                //#endregion
    
                return digito;
        }
        return -1;
    }

    public static boolean validaCPF(String cpf){
        String cpfFormatado;
        int digito_1, digito_2;

        //Remove caracteres desnecessários
        cpfFormatado = cpf.replaceAll("\\.|-", "");
        if(cpfFormatado.length() != 11)
            return false;
        
        digito_1 = digitoVerificador(cpfFormatado, 1);
        digito_2 = digitoVerificador(cpfFormatado, 2);
        if((digito_1 != cpfFormatado.charAt(9) - '0') || (digito_2 != cpfFormatado.charAt(10) - '0'))
            return false;

        return true;
    }

    public static boolean validaCNPJ(String cnpj){
        String cnpjFormatado;
        int digito_1, digito_2;

        //Remove caracteres desnecessários
        cnpjFormatado = cnpj.replaceAll("\\.|-|/", "");
        if(cnpjFormatado.length() != 14)
            return false;
        
        digito_1 = digitoVerificador(cnpjFormatado, 1);
        digito_2 = digitoVerificador(cnpjFormatado, 2);
        if((digito_1 != cnpjFormatado.charAt(12) - '0') || (digito_2 != cnpjFormatado.charAt(13) - '0'))
            return false;

        return true;
    }

    public static boolean validaNome(String nome){
        for(char c: nome.toCharArray()){
            if((c == 32) || ((c >= 65)&&(c <= 90)) || ((c >= 97)&&(c <= 122)) || (charEspeciais.contains(c)))
                continue;
            return false;
        }
        return true;
    }
}
