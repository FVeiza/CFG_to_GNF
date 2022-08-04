
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Principal {

    public static void main(String[] args) {
        JSONObject jsonob;
        JSONParser parser = new JSONParser();
        String teste = new String();
        
        try{
            jsonob = (JSONObject) parser.parse(new FileReader(args[0]));
            teste = (String) jsonob.get("glc").toString();
            String[] array = teste.split("]");
            ArrayList<String> regras = new ArrayList<>();
            
            for(int i = 2, j = 0; i < array.length-2; i++){
                regras.add(array[i]);
                j++; 
            }
            
            GLC glc = new GLC(array[0], array[1], regras, array[array.length-1]);
            glc.defineVariaveis();
            glc.defineSimbolos();
            glc.defineRegras();
            glc.defineUltimoSimbolo();
            glc.defineOrdem();
            
            FNG fng = new FNG(glc.getVariaveis(), glc.getSimbolos(), glc.getRegras(), glc.getUltimoSimbolo());
            fng.primeiroPasso();
            fng.segundoPasso();
            fng.terceiroPasso();
            fng.quartoPasso();
            fng.tratamentoLambda();
            
            PrintResults pr = new PrintResults(fng.getVariaveis(), fng.getSimbolos(), fng.getRegras(), glc.getUltimoSimbolo());
            pr.imprimeResultado();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        catch(Exception e){
            System.out.println("Usar: ./greibach [GLC]");
            e.printStackTrace();
        }   
    }
}
