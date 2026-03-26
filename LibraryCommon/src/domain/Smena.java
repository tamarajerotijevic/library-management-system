package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class Smena implements GenericEntity, Serializable {

    private int smenaID;
    private String naziv;
    private Date vremeOd;
    private Date vremeDo;

    public Smena() {
    }

    public Smena(int smenaID, String naziv, Date vremeOd, Date vremeDo) {
        this.smenaID = smenaID;
        this.naziv = naziv;
        this.vremeOd = vremeOd;
        this.vremeDo = vremeDo;
    }

    @Override
    public String getAttributeNames() {
        return "smenaID, naziv, vremeOd, vremeDo";
    }

    @Override
    public String getTableName() {
        return "smena";
    }

    @Override
    public String getAttributeValues() {
        return smenaID + ", '" + naziv + "', '" + new java.sql.Time(vremeOd.getTime())
            + "', '" + new java.sql.Time(vremeDo.getTime()) + "'";
    }

    @Override
    public String setAttributeValues() {
        return "naziv='" + naziv + "', vremeOd='" + new java.sql.Time(vremeOd.getTime())
                + "', vremeDo='" + new java.sql.Time(vremeDo.getTime()) + "'";
    }

    @Override
    public void setID(int ID) {
        this.smenaID = ID;
    }

    @Override
    public int getID() {
        return smenaID;
    }

    @Override
    public String getQueryCondition() {
        return "smenaID = " + smenaID;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        return new Smena(rs.getInt("smenaID"), rs.getString("naziv"), rs.getTime("vremeOd"), rs.getTime("vremeDo"));
    }

    @Override
    public State getState() {
        return State.UNCHANGED;
    }

    public int getSmenaID() {
        return smenaID;
    }

    public void setSmenaID(int smenaID) {
        this.smenaID = smenaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(Date vremeOd) {
        this.vremeOd = vremeOd;
    }

    public Date getVremeDo() {
        return vremeDo;
    }

    public void setVremeDo(Date vremeDo) {
        this.vremeDo = vremeDo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(smenaID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Smena other = (Smena) obj;
        return smenaID == other.smenaID;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
