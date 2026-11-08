import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
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
        List<Pregunta> listaPreguntas = null;

        // Leer el archivo JSON
        try (FileReader reader = new FileReader("preguntas.json")) {
            Type listType = new TypeToken<List<PreguntaBandera>>(){}.getType();
            listaPreguntas = gson.fromJson(reader, listType);
            System.out.println("¡Datos cargados con éxito desde el JSON!\n");
        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
            return;
        }

        // Simular el juego en la terminal
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

        // for (int i = 0; i < listaPreguntas.size(); i++) {
        //     PreguntaBandera p = (PreguntaBandera) listaPreguntas.get(i);
            
        //     System.out.println("Pregunta " + (i + 1) + ": " + p.getEnunciado());
            
        //     // Si la pregunta tiene imagen, aquí simularíamos mostrarla en JavaFX
        //     if (p.getRutaImagenBandera() != null) {
        //         System.out.println("[Imagen de la bandera: " + p.getRutaImagenBandera() + "]");
        //     }

        //     // Mostrar opciones numeradas
        //     List<Opcion> opciones = p.getOpciones();
        //     Collections.shuffle(opciones);

        //     for (int j = 0; j < opciones.size(); j++) {
        //         System.out.println("  (" + (j + 1) + ") " + opciones.get(j).getValor());
        //     }

        //     System.out.print("Elige tu opción (número): ");
        //     int respuestaUsuario = scanner.nextInt() - 1;

        //     // Validar si la respuesta es correcta
        //     if (respuestaUsuario >= 0 && respuestaUsuario < opciones.size()) {
        //         if (opciones.get(respuestaUsuario).isCorrecta()) {
        //             System.out.println("¡Correcto! 🎉\n");
        //             puntaje++;
        //         } else {
        //             System.out.println("Incorrecto. ❌\n");
        //         }
        //     } else {
        //         System.out.println("Opción inválida.\n");
        //     }
        // }

        System.out.println("Fin del test. Tu puntaje final es: " + puntaje + "/" + listaPreguntas.size());
        scanner.close();
    }
}