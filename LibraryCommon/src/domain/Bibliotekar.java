package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Bibliotekar implements GenericEntity, Serializable {

    private int bibliotekarID;
    private String korisnickoIme;
    private String sifra;
    private String ime;
    private String prezime;
    private String email;

    public Bibliotekar() {
    }

    public Bibliotekar(int bibliotekarID, String korisnickoIme, String sifra, String ime, String prezime, String email) {
        this.bibliotekarID = bibliotekarID;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
    }

    @Override
    public String getAttributeNames() {
        return "bibliotekarID, korisnickoIme, sifra, ime, prezime, email";
    }

    @Override
    public String getTableName() {
        return "admins";
    }

    @Override
    public String getAttributeValues() {
        return bibliotekarID + ", '" + korisnickoIme + "', '" + sifra + "', '" + ime + "', '" + prezime + "', '" + email + "'";
    }

    @Override
    public void setID(int ID) {
        this.bibliotekarID = ID;
    }

    @Override
    public int getID() {
        return bibliotekarID;
    }

    @Override
    public String setAttributeValues() {
        return "korisnickoIme='" + korisnickoIme + "', sifra='" + sifra + "', ime='" + ime + "', prezime='" + prezime + "', email='" + email + "'";
    }

    @Override
    public String getQueryCondition() {
        return "bibliotekarID = " + bibliotekarID;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        return new Bibliotekar(
                rs.getInt("bibliotekarID"),
                rs.getString("korisnickoIme"),
                rs.getString("sifra"),
                rs.getString("ime"),
                rs.getString("prezime"),
                rs.getString("email")
        );
    }

    @Override
    public State getState() {
        return State.UNCHANGED;
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT " + getAttributeNames() + " FROM " + getTableName();
    }

    public int getBibliotekarID() {
        return bibliotekarID;
    }

    public void setBibliotekarID(int bibliotekarID) {
        this.bibliotekarID = bibliotekarID;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPunoIme() {
        return ime + " " + prezime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bibliotekarID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bibliotekar other = (Bibliotekar) obj;
        return bibliotekarID == other.bibliotekarID;
    }

    @Override
    public String toString() {
        return getPunoIme();
    }
}
