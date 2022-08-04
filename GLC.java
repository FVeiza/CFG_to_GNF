
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class GLC {
    private ArrayList<Character> variaveis;
    private ArrayList<Character> simbolos;
    private LinkedHashMap<String, ArrayList<String>> regras;
    private String arrayVariaveis;
    private String arraySimbolos;
    private ArrayList<String> arrayRegras;
    private String ultimoSimbolo;

    public GLC(String arrayVariaveis, String arraySimbolos, ArrayList<String> arrayRegras, String ultimoSimbolo){
        variaveis = new ArrayList<>();
        simbolos = new ArrayList<>();
        regras = new LinkedHashMap<>();
        this.arrayRegras = arrayRegras;
        this.arraySimbolos = arraySimbolos;
        this.arrayVariaveis = arrayVariaveis;
        this.ultimoSimbolo = ultimoSimbolo;
    }
    
    public void defineVariaveis(){
        Character c;
        for(int i = 0; i < arrayVariaveis.length(); i++){
            c = arrayVariaveis.charAt(i);
            if(Character.isLetter(c)){
                variaveis.add(c);
            }
            
        }
    }
    
    public void defineSimbolos(){
        Character c;
        for(int i = 0; i < arraySimbolos.length(); i++){
            c = arraySimbolos.charAt(i);
            if(Character.isLetter(c) || Character.isDigit(c) || c.equals('#') || c.equals('.')){
                simbolos.add(c);
            }
            
        }
        
    }
    
    public void defineRegras(){
        Character c;
        Character c2;
        Character CR = null;
        String s;
        int controle1 = 0;
        int controle2 = 0;
        
        for(int i = 0; i < arrayRegras.size(); i++){
            s = arrayRegras.get(i);
            controle1 = 0;
            controle2 = 0;
            for(int j = 0; j < s.length(); j++){
                ArrayList<String> r = new ArrayList<>();
                c = s.charAt(j);
                if(Character.isLetter(c) && controle1 == 0){
                    if(regras.containsKey(c.toString())){
                        controle2 = 1;
                        CR = c;
                    }
                    if(controle2 == 0){
                        regras.put(c.toString(), r);
                        CR = c;
                    }
                    controle1 = 1;
                }
                else if((Character.isLetter(c) || Character.isDigit(c) || c.equals('#') || c.equals('.')) && controle1 == 1){
                    String s2 = new String();
                    for(int k = j; k < s.length(); k++){
                        c2 = s.charAt(k); 
                        if(Character.isLetter(c2) || Character.isDigit(c2) || c2.equals('#') || c2.equals('.')){
                            s2 = s2.concat(c2.toString());
                        }
                    }

                    regras.get(CR.toString()).add(s2);
                    break;
                }  
            }
        }
    }
    
    public void defineUltimoSimbolo(){
        Character c;
        
        for(int i = 0; i < ultimoSimbolo.length(); i++){
            c = ultimoSimbolo.charAt(i);
            if(Character.isLetter(c)){
                ultimoSimbolo = c.toString();
                break;
            }
            
        }
    }
    
    public void defineOrdem(){
        for(String k: regras.keySet()){
            if(!ultimoSimbolo.equals(k)){
                ArrayList<String> regrasUS = regras.get(ultimoSimbolo);
                LinkedHashMap<String, ArrayList<String>> regrasAux = new LinkedHashMap<>();
                regras.remove(ultimoSimbolo);
                regrasAux.put(ultimoSimbolo, regrasUS);
                for(String j: regras.keySet()){
                    regrasAux.put(j, regras.get(j));
                }
                regras = regrasAux;
            }
            break;
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

    public String getUltimoSimbolo() {
        return ultimoSimbolo;
    }  
}
