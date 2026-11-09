import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// 1. Clase para las opciones de respuesta

// 2. Clase abstracta base

// 3. Clase hija para banderas (y otras preguntas)

// 4. Clase principal para probar en consola
public class TestTrivia {
    public static void main(String[] args) {
        Gson gson = new Gson();
        List<Pregunta> listaPreguntas = new ArrayList<>();

        // Leer el archivo JSON

        try { 
            FileReader reader = new FileReader("preguntas.json");
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for(JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                String tipo = obj.get("tipo").getAsString();
                switch(tipo) {
                    case "bandera":
                        PreguntaBandera pB = gson.fromJson(obj, PreguntaBandera.class);
                        listaPreguntas.add(pB);
                        break;
                    case "capital":
                        PreguntaCapital pC = gson.fromJson(obj, PreguntaCapital.class);
                        listaPreguntas.add(pC);
                        break;
                    case "mapa":
                        PreguntaMapa pM = gson.fromJson(obj, PreguntaMapa.class);
                        listaPreguntas.add(pM);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



        Scanner scanner = new Scanner(System.in);
        int puntaje = 0;
        int counter = 1;

        Collections.shuffle(listaPreguntas);

        for(Pregunta p : listaPreguntas) {
            System.out.println("Pregunta " + counter);
            System.out.println(p.getEnunciado());

            if(p instanceof PreguntaBandera pB) {
                System.out.println(pB.getRutaImagenBandera());
            }
            else if(p instanceof PreguntaMapa pM) {
                System.out.println(pM.getRutaImagenMapa());
            }
            else {
                System.out.println("Pregunta de capital");
            }
            List<Opcion> opciones = p.getOpciones();
            Collections.shuffle(opciones);
            for(int i = 0; i < opciones.size(); i++) {
                System.out.println((i + 1) + ": " + opciones.get(i).getValor());

            }
            System.out.println("Escribe el número: ");
            int respuesta = scanner.nextInt() - 1;

            if(respuesta >= 0 && respuesta < opciones.size()) {

                if(opciones.get(respuesta).isCorrecta()) {
                    System.out.println("¿Correcta!");
                    puntaje++;
                }
                else {
                    System.out.println("Incorrecta.");
                }
            }
            else {
                System.out.println("Opción fuera del rango");
            }

            counter++;
        }

        System.out.println("Fin del test. Tu puntaje final es: " + puntaje + "/" + listaPreguntas.size());
        scanner.close();
    }
}