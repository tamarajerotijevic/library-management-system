package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class BibliotekarSmena implements GenericEntity, Serializable {

    private Bibliotekar bibliotekar;
    private Smena smena;
    private Date datum;

    public BibliotekarSmena() {
    }

    public BibliotekarSmena(Bibliotekar bibliotekar, Smena smena, Date datum) {
        this.bibliotekar = bibliotekar;
        this.smena = smena;
        this.datum = datum;
    }

    @Override
    public String getAttributeNames() {
        return "bibliotekarID, smenaID, datum";
    }

    @Override
    public String getTableName() {
        return "`bibliotekar-smena`";
    }

    @Override
    public String getAttributeValues() {
        return bibliotekar.getBibliotekarID() + ", " + smena.getSmenaID() + ", '" + new java.sql.Date(datum.getTime()) + "'";
    }

    @Override
    public boolean shouldAssignGeneratedId() {
        return false;
    }

    @Override
    public String setAttributeValues() {
        return "datum='" + new java.sql.Date(datum.getTime()) + "'";
    }

    @Override
    public String getQueryCondition() {
        return "bibliotekarID = " + bibliotekar.getBibliotekarID()
                + " AND smenaID = " + smena.getSmenaID()
                + " AND datum = '" + new java.sql.Date(datum.getTime()) + "'";
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        return new BibliotekarSmena(
                new Bibliotekar(rs.getInt("bibliotekarID"), null, null, null, null, null),
                new Smena(rs.getInt("smenaID"), null, null, null),
                rs.getDate("datum")
        );
    }

    @Override
    public State getState() {
        return State.UNCHANGED;
    }

    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

    public void setBibliotekar(Bibliotekar bibliotekar) {
        this.bibliotekar = bibliotekar;
    }

    public Smena getSmena() {
        return smena;
    }

    public void setSmena(Smena smena) {
        this.smena = smena;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bibliotekar, smena, datum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BibliotekarSmena other = (BibliotekarSmena) obj;
        return Objects.equals(bibliotekar, other.bibliotekar)
                && Objects.equals(smena, other.smena)
                && Objects.equals(datum, other.datum);
    }
}
