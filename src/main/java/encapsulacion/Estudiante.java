package encapsulacion;

/**
 * Created by vacax on 18/06/17.
 */
public class Estudiante {

    int matricula;
    String nombre;

    public Estudiante(int matricula, String nombre) {
        this.matricula = matricula;
        this.nombre = nombre;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
