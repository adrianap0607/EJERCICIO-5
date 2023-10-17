public abstract class Jugador {
    private String nombre;
    private String pais;
    private int errores;
    private int aces;
    private int totalServicios;

    public Jugador(String nombre, String pais, int errores, int aces, int totalServicios) {
        this.nombre = nombre;
        this.pais = pais;
        this.errores = errores;
        this.aces = aces;
        this.totalServicios = totalServicios;
    }

    public abstract double calcularEfectividad();

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public int getErrores() {
        return errores;
    }

    public int getAces() {
        return aces;
    }

    public int getTotalServicios() {
        return totalServicios;
    }
    
    public abstract String getTipoJugador();
}
