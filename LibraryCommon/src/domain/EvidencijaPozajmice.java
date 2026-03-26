package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class EvidencijaPozajmice implements GenericEntity, Serializable {

    private int evidencijaPozajmiceID;
    private java.util.Date datumIznajmljivanja;
    private int brojStavkiIznajmljivanja;
    private double ukupanIznosKazne;
    private Bibliotekar bibliotekar;
    private Clan clan;
    private List<StavkaPozajmice> stavkePozajmice;
    private State state;

    public EvidencijaPozajmice() {
        this.stavkePozajmice = new LinkedList<>();
        this.state = State.UNCHANGED;
    }

    public EvidencijaPozajmice(int evidencijaPozajmiceID, java.util.Date datumIznajmljivanja,
            int brojStavkiIznajmljivanja, double ukupanIznosKazne,
            Bibliotekar bibliotekar, Clan clan, List<StavkaPozajmice> stavkePozajmice) {
        this.evidencijaPozajmiceID = evidencijaPozajmiceID;
        this.datumIznajmljivanja = datumIznajmljivanja;
        this.brojStavkiIznajmljivanja = brojStavkiIznajmljivanja;
        this.ukupanIznosKazne = ukupanIznosKazne;
        this.bibliotekar = bibliotekar;
        this.clan = clan;
        this.stavkePozajmice = stavkePozajmice;
        this.state = State.UNCHANGED;
    }

    @Override
    public String getAttributeNames() {
        return "evidencijaPozajmiceID, datumIznajmljivanja, brojStavkiIznajmljivanja, ukupanIznosKazne, bibliotekarID, clanID";
    }

    @Override
    public String getTableName() {
        return "evidencijapozajmice";
    }

    @Override
    public String getAttributeValues() {
        return evidencijaPozajmiceID + ", '" + new java.sql.Date(datumIznajmljivanja.getTime()) + "', "
                + brojStavkiIznajmljivanja + ", "
                + ukupanIznosKazne + ", "
                + bibliotekar.getBibliotekarID() + ", "
                + clan.getClanID();
    }

    @Override
    public String setAttributeValues() {
        return "datumIznajmljivanja='" + new java.sql.Date(datumIznajmljivanja.getTime()) + "'"
                + ", brojStavkiIznajmljivanja=" + brojStavkiIznajmljivanja
                + ", ukupanIznosKazne=" + ukupanIznosKazne
                + ", bibliotekarID=" + bibliotekar.getBibliotekarID()
                + ", clanID=" + clan.getClanID();
    }

    @Override
    public void setID(int ID) {
        this.evidencijaPozajmiceID = ID;
    }

    @Override
    public int getID() {
        return evidencijaPozajmiceID;
    }

    @Override
    public String getQueryCondition() {
        return "evidencijaPozajmiceID = " + evidencijaPozajmiceID;
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT ep.evidencijaPozajmiceID, ep.datumIznajmljivanja, ep.brojStavkiIznajmljivanja, ep.ukupanIznosKazne, "
                + "b.bibliotekarID, b.korisnickoIme, b.sifra, b.ime as 'b.ime', b.prezime as 'b.prezime', b.email as 'b.email', "
                + "c.clanID, c.ime as 'c.ime', c.prezime as 'c.prezime', c.email as 'c.email', "
                + "m.mestoID, m.naziv as 'm.naziv', m.postanskiBroj "
                + "FROM evidencijapozajmice ep "
                + "JOIN admins b ON ep.bibliotekarID = b.bibliotekarID "
                + "JOIN clan c ON ep.clanID = c.clanID "
                + "JOIN mesto m ON c.mestoID = m.mestoID";
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        Bibliotekar b = new Bibliotekar(
                rs.getInt("bibliotekarID"),
                rs.getString("korisnickoIme"),
                rs.getString("sifra"),
                rs.getString("b.ime"),
                rs.getString("b.prezime"),
                rs.getString("b.email")
        );
        Mesto m = new Mesto(rs.getInt("mestoID"), rs.getString("m.naziv"), rs.getString("postanskiBroj"));
        Clan c = new Clan(rs.getInt("clanID"), rs.getString("c.ime"), rs.getString("c.prezime"), rs.getString("c.email"), m);

        return new EvidencijaPozajmice(
                rs.getInt("evidencijaPozajmiceID"),
                rs.getDate("datumIznajmljivanja"),
                rs.getInt("brojStavkiIznajmljivanja"),
                rs.getDouble("ukupanIznosKazne"),
                b,
                c,
                null
        );
    }

    @Override
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean equalsAll(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EvidencijaPozajmice other = (EvidencijaPozajmice) obj;
        return evidencijaPozajmiceID == other.evidencijaPozajmiceID
                && brojStavkiIznajmljivanja == other.brojStavkiIznajmljivanja
                && Double.doubleToLongBits(ukupanIznosKazne) == Double.doubleToLongBits(other.ukupanIznosKazne)
                && Objects.equals(datumIznajmljivanja, other.datumIznajmljivanja)
                && Objects.equals(bibliotekar, other.bibliotekar)
                && Objects.equals(clan, other.clan)
                && StavkaPozajmice.equalsAll(stavkePozajmice, other.stavkePozajmice);
    }

    public boolean equalsAllWithoutStavke(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EvidencijaPozajmice other = (EvidencijaPozajmice) obj;
        return evidencijaPozajmiceID == other.evidencijaPozajmiceID
                && brojStavkiIznajmljivanja == other.brojStavkiIznajmljivanja
                && Double.doubleToLongBits(ukupanIznosKazne) == Double.doubleToLongBits(other.ukupanIznosKazne)
                && Objects.equals(datumIznajmljivanja, other.datumIznajmljivanja)
                && Objects.equals(bibliotekar, other.bibliotekar)
                && Objects.equals(clan, other.clan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidencijaPozajmiceID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EvidencijaPozajmice other = (EvidencijaPozajmice) obj;
        return evidencijaPozajmiceID == other.evidencijaPozajmiceID;
    }

    public int getEvidencijaPozajmiceID() {
        return evidencijaPozajmiceID;
    }

    public void setEvidencijaPozajmiceID(int evidencijaPozajmiceID) {
        this.evidencijaPozajmiceID = evidencijaPozajmiceID;
    }

    public java.util.Date getDatumIznajmljivanja() {
        return datumIznajmljivanja;
    }

    public void setDatumIznajmljivanja(java.util.Date datumIznajmljivanja) {
        this.datumIznajmljivanja = datumIznajmljivanja;
    }

    public int getBrojStavkiIznajmljivanja() {
        return brojStavkiIznajmljivanja;
    }

    public void setBrojStavkiIznajmljivanja(int brojStavkiIznajmljivanja) {
        this.brojStavkiIznajmljivanja = brojStavkiIznajmljivanja;
    }

    public double getUkupanIznosKazne() {
        return ukupanIznosKazne;
    }

    public void setUkupanIznosKazne(double ukupanIznosKazne) {
        this.ukupanIznosKazne = ukupanIznosKazne;
    }

    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

    public void setBibliotekar(Bibliotekar bibliotekar) {
        this.bibliotekar = bibliotekar;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public List<StavkaPozajmice> getStavkePozajmice() {
        return stavkePozajmice;
    }

    public void setStavkePozajmice(List<StavkaPozajmice> stavkePozajmice) {
        this.stavkePozajmice = stavkePozajmice;
    }

    @Override
    public String toString() {
        return "Evidencija #" + evidencijaPozajmiceID + " - " + clan;
    }
}
