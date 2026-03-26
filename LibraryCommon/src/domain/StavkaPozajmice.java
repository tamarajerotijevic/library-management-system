package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class StavkaPozajmice implements GenericEntity, Serializable {

    private EvidencijaPozajmice evidencijaPozajmice;
    private int rb;
    private java.util.Date datumVracanja;
    private java.util.Date rokZaVracanje;
    private double iznosKazne;
    private double cenaKasnjenja;
    private int brojDanaKasnjenja;
    private Knjiga knjiga;
    private State state;

    public StavkaPozajmice() {
        this.state = State.UNCHANGED;
    }

    public StavkaPozajmice(EvidencijaPozajmice evidencijaPozajmice, int rb, java.util.Date datumVracanja,
            java.util.Date rokZaVracanje, double iznosKazne, double cenaKasnjenja,
            int brojDanaKasnjenja, Knjiga knjiga) {
        this.evidencijaPozajmice = evidencijaPozajmice;
        this.rb = rb;
        this.datumVracanja = datumVracanja;
        this.rokZaVracanje = rokZaVracanje;
        this.iznosKazne = iznosKazne;
        this.cenaKasnjenja = cenaKasnjenja;
        this.brojDanaKasnjenja = brojDanaKasnjenja;
        this.knjiga = knjiga;
        this.state = State.UNCHANGED;
    }

    @Override
    public String getAttributeNames() {
        return "evidencijaPozajmiceID, rb, datumVracanja, rokZaVracanje, iznosKazne, cenaKasnjenja, brojDanaKasnjenja, knjigaID";
    }

    @Override
    public String getTableName() {
        return "stavkapozajmice";
    }

    @Override
    public String getAttributeValues() {
        azurirajKaznu();
        String datumVracanjaValue = datumVracanja == null ? "NULL" : "'" + new java.sql.Date(datumVracanja.getTime()) + "'";
        return evidencijaPozajmice.getEvidencijaPozajmiceID() + ", "
                + rb + ", "
                + datumVracanjaValue + ", '"
                + new java.sql.Date(rokZaVracanje.getTime()) + "', "
                + iznosKazne + ", "
                + cenaKasnjenja + ", "
                + brojDanaKasnjenja + ", "
                + knjiga.getKnjigaID();
    }

    @Override
    public String setAttributeValues() {
        azurirajKaznu();
        String datumVracanjaValue = datumVracanja == null ? "NULL" : "'" + new java.sql.Date(datumVracanja.getTime()) + "'";
        return "datumVracanja=" + datumVracanjaValue
                + ", rokZaVracanje='" + new java.sql.Date(rokZaVracanje.getTime()) + "'"
                + ", iznosKazne=" + iznosKazne
                + ", cenaKasnjenja=" + cenaKasnjenja
                + ", brojDanaKasnjenja=" + brojDanaKasnjenja
                + ", knjigaID=" + knjiga.getKnjigaID();
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT sp.evidencijaPozajmiceID, sp.rb, sp.datumVracanja, sp.rokZaVracanje, sp.iznosKazne, "
                + "sp.cenaKasnjenja, sp.brojDanaKasnjenja, k.knjigaID, k.autori, k.izdavac, k.naslov, k.isbn "
                + "FROM stavkapozajmice sp JOIN knjiga k ON sp.knjigaID = k.knjigaID";
    }

    @Override
    public String getQueryCondition() {
        return "evidencijaPozajmiceID = " + evidencijaPozajmice.getEvidencijaPozajmiceID()
                + " AND rb = " + rb;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        Knjiga k = new Knjiga(rs.getInt("knjigaID"), rs.getString("autori"), rs.getString("izdavac"), rs.getString("naslov"), rs.getString("isbn"));
        return new StavkaPozajmice(
                null,
                rs.getInt("rb"),
                rs.getDate("datumVracanja"),
                rs.getDate("rokZaVracanje"),
                rs.getDouble("iznosKazne"),
                rs.getDouble("cenaKasnjenja"),
                rs.getInt("brojDanaKasnjenja"),
                k
        );
    }

    @Override
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static boolean equalsAll(List<StavkaPozajmice> left, List<StavkaPozajmice> right) {
        if (left.size() != right.size()) {
            return false;
        }

        for (int i = 0; i < left.size(); i++) {
            if (!left.get(i).equalsAll(right.get(i))) {
                return false;
            }
        }

        return true;
    }

    public boolean equalsAll(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StavkaPozajmice other = (StavkaPozajmice) obj;
        return rb == other.rb
                && Double.doubleToLongBits(iznosKazne) == Double.doubleToLongBits(other.iznosKazne)
                && Double.doubleToLongBits(cenaKasnjenja) == Double.doubleToLongBits(other.cenaKasnjenja)
                && brojDanaKasnjenja == other.brojDanaKasnjenja
                && Objects.equals(datumVracanja, other.datumVracanja)
                && Objects.equals(rokZaVracanje, other.rokZaVracanje)
                && Objects.equals(knjiga, other.knjiga);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidencijaPozajmice, rb);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StavkaPozajmice other = (StavkaPozajmice) obj;
        return rb == other.rb && Objects.equals(evidencijaPozajmice, other.evidencijaPozajmice);
    }

    public EvidencijaPozajmice getEvidencijaPozajmice() {
        return evidencijaPozajmice;
    }

    public void setEvidencijaPozajmice(EvidencijaPozajmice evidencijaPozajmice) {
        this.evidencijaPozajmice = evidencijaPozajmice;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public java.util.Date getDatumVracanja() {
        return datumVracanja;
    }

    public void setDatumVracanja(java.util.Date datumVracanja) {
        this.datumVracanja = datumVracanja;
    }

    public java.util.Date getRokZaVracanje() {
        return rokZaVracanje;
    }

    public void setRokZaVracanje(java.util.Date rokZaVracanje) {
        this.rokZaVracanje = rokZaVracanje;
    }

    public double getIznosKazne() {
        iznosKazne = izracunajIznosKazne();
        return iznosKazne;
    }

    public void setIznosKazne(double iznosKazne) {
        this.iznosKazne = iznosKazne;
    }

    public double getCenaKasnjenja() {
        return cenaKasnjenja;
    }

    public void setCenaKasnjenja(double cenaKasnjenja) {
        this.cenaKasnjenja = cenaKasnjenja;
    }

    public int getBrojDanaKasnjenja() {
        brojDanaKasnjenja = izracunajBrojDanaKasnjenja();
        return brojDanaKasnjenja;
    }

    public void setBrojDanaKasnjenja(int brojDanaKasnjenja) {
        this.brojDanaKasnjenja = brojDanaKasnjenja;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public int izracunajBrojDanaKasnjenja() {
        if (rokZaVracanje == null) {
            return 0;
        }

        LocalDate rok = toLocalDate(rokZaVracanje);
        LocalDate datumPoredjenja = datumVracanja == null ? LocalDate.now() : toLocalDate(datumVracanja);

        if (!datumPoredjenja.isAfter(rok)) {
            return 0;
        }

        return (int) ChronoUnit.DAYS.between(rok, datumPoredjenja);
    }

    public double izracunajIznosKazne() {
        return izracunajBrojDanaKasnjenja() * cenaKasnjenja;
    }

    public void azurirajKaznu() {
        brojDanaKasnjenja = izracunajBrojDanaKasnjenja();
        iznosKazne = brojDanaKasnjenja * cenaKasnjenja;
    }

    private LocalDate toLocalDate(java.util.Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
