import java.util.List;

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