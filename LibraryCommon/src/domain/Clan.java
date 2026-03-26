package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Clan implements GenericEntity, Serializable {

    private int clanID;
    private String ime;
    private String prezime;
    private String email;
    private Mesto mesto;

    public Clan() {
    }

    public Clan(int clanID, String ime, String prezime, String email, Mesto mesto) {
        this.clanID = clanID;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.mesto = mesto;
    }

    @Override
    public String getAttributeNames() {
        return "clanID, ime, prezime, email, mestoID";
    }

    @Override
    public String getTableName() {
        return "clan";
    }

    @Override
    public String getAttributeValues() {
        return clanID + ", '" + ime + "', '" + prezime + "', '" + email + "', " + mesto.getMestoID();
    }

    @Override
    public String setAttributeValues() {
        return "ime='" + ime + "', prezime='" + prezime + "', email='" + email + "', mestoID=" + mesto.getMestoID();
    }

    @Override
    public void setID(int ID) {
        this.clanID = ID;
    }

    @Override
    public int getID() {
        return clanID;
    }

    @Override
    public String getQueryCondition() {
        return "clanID = " + clanID;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        Mesto m = new Mesto(rs.getInt("mestoID"), rs.getString("m.naziv"), rs.getString("postanskiBroj"));
        return new Clan(rs.getInt("clanID"), rs.getString("ime"), rs.getString("prezime"), rs.getString("email"), m);
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT c.clanID, c.ime, c.prezime, c.email, m.mestoID, m.naziv as 'm.naziv', m.postanskiBroj "
            + "FROM clan c JOIN mesto m ON c.mestoID = m.mestoID";
    }

    @Override
    public State getState() {
        return State.UNCHANGED;
    }

    public int getClanID() {
        return clanID;
    }

    public void setClanID(int clanID) {
        this.clanID = clanID;
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

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clanID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Clan other = (Clan) obj;
        return clanID == other.clanID;
    }

    public boolean equalsAll(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Clan other = (Clan) obj;
        return clanID == other.clanID
                && Objects.equals(ime, other.ime)
                && Objects.equals(prezime, other.prezime)
                && Objects.equals(email, other.email)
                && Objects.equals(mesto, other.mesto);
    }

    @Override
    public String toString() {
        return clanID + " - " + ime + " " + prezime;
    }
}
