import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// 1. Clase para las opciones de respuesta
class Opcion {
    private String valor;
    private boolean correcta;

    public String getValor() {
        return valor;
    }

    public boolean isCorrecta() {
        return correcta;
    }
}

// 2. Clase abstracta base
abstract class Pregunta {
    private String enunciado;
    private List<Opcion> opciones;

    public String getEnunciado() {
        return enunciado;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }
}

// 3. Clase hija para banderas (y otras preguntas)
class PreguntaBandera extends Pregunta {
    private String rutaImagenBandera; // Atributo extra exclusivo de banderas

    public String getRutaImagenBandera() {
        return rutaImagenBandera;
    }
}

// 4. Clase principal para probar en consola
public class TestTrivia {
    public static void main(String[] args) {
        Gson gson = new Gson();
        List<PreguntaBandera> listaPreguntas = null;

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

        Collections.shuffle(listaPreguntas);

        for (int i = 0; i < listaPreguntas.size(); i++) {
            PreguntaBandera p = listaPreguntas.get(i);
            
            System.out.println("Pregunta " + (i + 1) + ": " + p.getEnunciado());
            
            // Si la pregunta tiene imagen, aquí simularíamos mostrarla en JavaFX
            if (p.getRutaImagenBandera() != null) {
                System.out.println("[Imagen de la bandera: " + p.getRutaImagenBandera() + "]");
            }

            // Mostrar opciones numeradas
            List<Opcion> opciones = p.getOpciones();
            Collections.shuffle(opciones);
            
            for (int j = 0; j < opciones.size(); j++) {
                System.out.println("  (" + (j + 1) + ") " + opciones.get(j).getValor());
            }

            System.out.print("Elige tu opción (número): ");
            int respuestaUsuario = scanner.nextInt() - 1;

            // Validar si la respuesta es correcta
            if (respuestaUsuario >= 0 && respuestaUsuario < opciones.size()) {
                if (opciones.get(respuestaUsuario).isCorrecta()) {
                    System.out.println("¡Correcto! 🎉\n");
                    puntaje++;
                } else {
                    System.out.println("Incorrecto. ❌\n");
                }
            } else {
                System.out.println("Opción inválida.\n");
            }
        }

        System.out.println("Fin del test. Tu puntaje final es: " + puntaje + "/" + listaPreguntas.size());
        scanner.close();
    }
}