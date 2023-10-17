public class Libero extends Jugador {
    private int recibos;

    public Libero(String nombre, String pais, int errores, int aces, int totalServicios, int recibos) {
        super(nombre, pais, errores, aces, totalServicios);
        this.recibos = recibos;
    }

    @Override
    public double calcularEfectividad() {
        return ((recibos - getErrores()) * 100.0 / (recibos + getErrores())) + (getAces() * 100.0 / getTotalServicios());
    }

    public int getRecibos() {
        return recibos;
    }

    @Override
    public String getTipoJugador() {
        return "Libero";
    }
}
