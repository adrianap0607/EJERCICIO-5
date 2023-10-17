public class Auxiliares extends Jugador {
    private int ataques;
    private int bloqueosEfectivos;
    private int bloqueosFallidos;

    public Auxiliares(String nombre, String pais, int errores, int aces, int totalServicios, int ataques, int bloqueosEfectivos, int bloqueosFallidos) {
        super(nombre, pais, errores, aces, totalServicios);
        this.ataques = ataques;
        this.bloqueosEfectivos = bloqueosEfectivos;
        this.bloqueosFallidos = bloqueosFallidos;
    }

    @Override
    public double calcularEfectividad() {
        return ((ataques + bloqueosEfectivos - bloqueosFallidos - getErrores()) * 100.0 /
                (ataques + bloqueosEfectivos + bloqueosFallidos + getErrores())) + (getAces() * 100.0 / getTotalServicios());
    }

    public int getAtaques() {
        return ataques;
    }

    public int getBloqueosEfectivos() {
        return bloqueosEfectivos;
    }

    public int getBloqueosFallidos() {
        return bloqueosFallidos;
    }

    @Override
    public String getTipoJugador() {
        return "Auxiliar";
    }
}
