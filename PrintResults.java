
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PrintResults {
    private ArrayList<Character> variaveis;
    private ArrayList<Character> simbolos;
    private LinkedHashMap<String, ArrayList<String>> regras;
    private String ultimoSimbolo;
    
    public PrintResults(ArrayList<Character> variaveis, ArrayList<Character> simbolos, LinkedHashMap<String, ArrayList<String>> regras, String ultimoSimbolo){
        this.regras = regras;
        this.simbolos = simbolos;
        this.variaveis = variaveis;
        this.ultimoSimbolo = ultimoSimbolo;
    }
    
    public void imprimeResultado(){
        System.out.println("[ \"glc\": [");
        System.out.printf("    [");
        for(int i = 0; i < variaveis.size(); i++){
            if(i == variaveis.size()-1){
                System.out.printf("\""+variaveis.get(i)+"\"],\n");
            }
            else{
                System.out.printf("\""+variaveis.get(i)+"\", ");
            }
        }
        
        System.out.printf("    [");
        for(int j = 0; j < simbolos.size(); j++){
            if(j == simbolos.size()-1){
                System.out.printf("\""+simbolos.get(j)+"\"],\n");
            }
            else{
                System.out.printf("\""+simbolos.get(j)+"\", ");
            }
        }
        
        int tam = 0;
        System.out.println("    [");
        for(String k: regras.keySet()){
            if(tam == regras.size()-1){
                for(int a = 0; a < regras.get(k).size(); a++){
                    if(a == regras.get(k).size()-1){
                        System.out.println("        [\""+k+"\", \""+regras.get(k).get(a)+"\"]");
                    }
                    else{
                        System.out.println("        [\""+k+"\", \""+regras.get(k).get(a)+"\"],");
                    }
                }
            }
            else{
                for(int a = 0; a < regras.get(k).size(); a++){
                    System.out.println("        [\""+k+"\", \""+regras.get(k).get(a)+"\"],");
                }
            }
            tam++;
        }
        
        System.out.println("    ],");
        System.out.println("    \""+ultimoSimbolo+"\"");
        System.out.println("]}");
    }
}
