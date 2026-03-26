package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Mesto implements GenericEntity, Serializable {

    private int mestoID;
    private String naziv;
    private String postanskiBroj;

    public Mesto() {
    }

    public Mesto(int mestoID, String naziv, String postanskiBroj) {
        this.mestoID = mestoID;
        this.naziv = naziv;
        this.postanskiBroj = postanskiBroj;
    }

    @Override
    public String getAttributeNames() {
        return "mestoID, naziv, postanskiBroj";
    }

    @Override
    public String getTableName() {
        return "mesto";
    }

    @Override
    public String getAttributeValues() {
        return mestoID + ", '" + naziv + "', '" + postanskiBroj + "'";
    }

    @Override
    public String setAttributeValues() {
        return "naziv='" + naziv + "', postanskiBroj='" + postanskiBroj + "'";
    }

    @Override
    public void setID(int ID) {
        this.mestoID = ID;
    }

    @Override
    public int getID() {
        return mestoID;
    }

    @Override
    public String getQueryCondition() {
        return "mestoID = " + mestoID;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        return new Mesto(rs.getInt("mestoID"), rs.getString("naziv"), rs.getString("postanskiBroj"));
    }

    @Override
    public State getState() {
        return State.UNCHANGED;
    }

    public int getMestoID() {
        return mestoID;
    }

    public void setMestoID(int mestoID) {
        this.mestoID = mestoID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mestoID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Mesto other = (Mesto) obj;
        return mestoID == other.mestoID;
    }

    @Override
    public String toString() {
        return naziv + " (" + postanskiBroj + ")";
    }
}
