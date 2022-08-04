
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FNG {
    private ArrayList<Character> variaveis;
    private ArrayList<Character> simbolos;
    private LinkedHashMap<String, ArrayList<String>> regras;
    private LinkedHashMap<Character, Character> simbolosTratados;
    private LinkedHashMap<String, Integer> numeracao;
    private LinkedHashMap<String, ArrayList<String>> regrasAux;
    private LinkedHashMap<String, ArrayList<String>> regrasAux2;
    private ArrayList<Character> variaveisPadrao;
    private String ultimoSimbolo;
    
    public FNG(ArrayList<Character> variaveis, ArrayList<Character> simbolos, LinkedHashMap<String, ArrayList<String>> regras, String ultimoSimbolo){
        this.variaveis = variaveis;
        this.simbolos = simbolos;
        this.regras = regras;
        this.simbolosTratados = new LinkedHashMap<>();
        this.numeracao = new LinkedHashMap<>();
        this.regrasAux = new LinkedHashMap<>();
        this.regrasAux2 = new LinkedHashMap<>();
        this.variaveisPadrao = new ArrayList<>();
        this.ultimoSimbolo = ultimoSimbolo;
    }
    
    public void primeiroPasso(){
        int num = 1;
        Character x = null;
        for(String k: regras.keySet()){
            ArrayList<String> r = regras.get(k);
            
            for(int i = 0; i < r.size(); i++){
                if(r.get(i).length() > 1){
                    for(int j = 1; j < r.get(i).length(); j++){
                        if(Character.isLowerCase(r.get(i).charAt(j)) || Character.isDigit(r.get(i).charAt(j)) || (r.get(i).charAt(j) == '.')){
                            x = r.get(i).charAt(j);
                            if(Character.isDigit(x)){
                                x = x.charValue();
                            }
                            if(simbolosTratados.containsKey(x)){
                                String novaRegra = r.get(i).replace(x, simbolosTratados.get(x));
                                r.set(i, novaRegra);
                            }
                            else{
                                ArrayList<String> regra = new ArrayList<>();
                                regra.add(Character.toString(x));
                                char novaVar = variaveis.get(variaveis.size()-1);
                                novaVar++;
                                variaveis.add((Character) novaVar);
                                String novaRegra = r.get(i).replace(x, novaVar);
                                r.set(i, novaRegra);
                                regrasAux.put(Character.toString(novaVar), regra);
                                simbolosTratados.put(x, novaVar);
                            }
                        }    
                    }
                }
            }
        }
        
        for(int i = 0; i < variaveis.size(); i++){
            variaveisPadrao.add(variaveis.get(i));
        }
        
        for(String k: regrasAux.keySet()){
            regras.put(k, regrasAux.get(k));
        }
        
        for(String k: regras.keySet()){
            numeracao.put(k, num);
            num++;
        }
        
    }
    
    public void segundoPasso(){

        int controle = 0;
        String nVar = new String();
        for(String k: regras.keySet()){
            ArrayList<String> r = regras.get(k);
            controle = 0;
            nVar = "";
            ArrayList<String> novasRegras = new ArrayList<>();
            for(int i = 0; i < r.size(); i++){
                if((r.get(i).length() > 1) && (k.compareTo(r.get(i).substring(0, 1)) != 0) && (Character.isUpperCase(r.get(i).charAt(0)))){
                    String variavel = r.get(i).substring(0, 1);
                    String regraOg = r.get(i);
                    int tam = regras.get(variavel).size();
                    if(numeracao.get(r.get(i).substring(0, 1)) < numeracao.get(k)){
                            
                        for(int b = 0; b < tam; b++){
                            String novaRegra = regraOg;
                            novaRegra = novaRegra.replaceFirst(variavel, regras.get(variavel).get(b));
                            if(b == 0){
                                r.set(i, novaRegra);
                            }
                            else{
                                r.add(i+b, novaRegra);
                            }
                            if(Character.isUpperCase(novaRegra.charAt(0)) && (Character.toString(novaRegra.charAt(0)).compareTo(k) != 0)){
                                i--;
                            }
                        }
                    } 
                }
                
                else if((r.get(i).length() > 1) && (k.compareTo(r.get(i).substring(0, 1)) == 0)){
                    novasRegras.add(r.get(i).substring(1));
                    r.remove(r.get(i));
                    if(controle == 0){
                        char novaVar = variaveis.get(variaveis.size()-1);
                        novaVar++;
                        variaveis.add((Character) novaVar);
                        nVar = Character.toString(novaVar);
                        regrasAux2.put(Character.toString(novaVar), novasRegras);
                        controle = 1;
                    }
                    else{
                        regrasAux2.replace(nVar, novasRegras);
                    }
                    i--;   
                }
            }
            
            int tamArray = r.size();
            if(controle == 1){
                for(int b = 0; b < tamArray; b++){
                    r.add(r.get(b).concat(nVar));
                }
                tamArray = novasRegras.size();
                for(int c = 0; c < tamArray; c++){
                    regrasAux2.get(nVar).add(novasRegras.get(c).concat(nVar));
                }
            }
            
        }
        for(String k: regrasAux2.keySet()){
            regras.put(k, regrasAux2.get(k));
        }
    }
    
    public void terceiroPasso(){
        for(int j = variaveisPadrao.size()-1; j >= 0; j--){
            ArrayList<String> r = regras.get(variaveisPadrao.get(j).toString());

            for(int i = 0; i < r.size(); i++){
                if((Character.isUpperCase(r.get(i).charAt(0)))){
                    String variavel = r.get(i).substring(0, 1);
                    String regraOg = r.get(i);
                    int tam = regras.get(variavel).size();
       
                    for(int b = 0; b < tam; b++){
                        String novaRegra = regraOg;
                        novaRegra = novaRegra.replaceFirst(variavel, regras.get(variavel).get(b));
                        if(b == 0){
                            r.set(i, novaRegra);
                        }
                        else{
                            r.add(i+b, novaRegra);
                        }
                        if(Character.isUpperCase(novaRegra.charAt(0))){
                            i--;
                        }
                    }
                }
            }
        }
    }
    
    public void quartoPasso(){
        for(String k: regras.keySet()){
            if(!(variaveisPadrao.contains((Character) k.charAt(0)))){
                ArrayList<String> r = regras.get(k);
                for(int i = 0; i < r.size(); i++){
                    if((Character.isUpperCase(r.get(i).charAt(0)))){
                        String variavel = r.get(i).substring(0, 1);
                        String regraOg = r.get(i);
                        int tam = regras.get(variavel).size();


                        for(int b = 0; b < tam; b++){
                            String novaRegra = regraOg;
                            novaRegra = novaRegra.replaceFirst(variavel, regras.get(variavel).get(b));
                            if(b == 0){
                                r.set(i, novaRegra);
                            }
                            else{
                                r.add(i+b, novaRegra);
                            }
                            if(Character.isUpperCase(novaRegra.charAt(0))){
                                i--;
                            }
                        }

                    }
                }
            }
        }
    }
    
    public void tratamentoLambda(){
        int controle = 0;
        for(String k: regras.keySet()){
            ArrayList<String> r = regras.get(k);
            
            for(int i = 0; i < r.size(); i++){
                if(r.get(i).equals("#")){
                    r.remove(i);
                    controle = 1;
                    i--;
                }
            }
        }
        if(controle == 1){
            for(String k: regras.keySet()){
                if(k.equals(ultimoSimbolo)){
                    regras.get(k).add("#");
                }
            }
        }
    }

    public ArrayList<Character> getVariaveis() {
        return variaveis;
    }

    public ArrayList<Character> getSimbolos() {
        return simbolos;
    }

    public LinkedHashMap<String, ArrayList<String>> getRegras() {
        return regras;
    }  
}
