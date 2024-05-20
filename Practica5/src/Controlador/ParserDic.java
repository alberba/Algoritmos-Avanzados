package Controlador;

import Modelo.Diccionario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParserDic {
    private final String [] pathsDiccionarios;

    public ParserDic(String [] pathsDiccionarios) {
        this.pathsDiccionarios = pathsDiccionarios;
    }

    public ArrayList<Diccionario> parse() {
        ArrayList<Diccionario> diccionarios = new ArrayList<>();
        for(String path : pathsDiccionarios) {
            ArrayList<String> diccionario = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("src/"+ path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    diccionario.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            diccionarios.add(new Diccionario(diccionario));
        }

        return diccionarios;
    }
}