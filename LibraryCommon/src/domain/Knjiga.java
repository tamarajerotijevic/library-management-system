package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Knjiga implements GenericEntity, Serializable {

    private int knjigaID;
    private String autori;
    private String izdavac;
    private String naslov;
    private String isbn;

    public Knjiga() {
    }

    public Knjiga(int knjigaID, String autori, String izdavac, String naslov, String isbn) {
        this.knjigaID = knjigaID;
        this.autori = autori;
        this.izdavac = izdavac;
        this.naslov = naslov;
        this.isbn = isbn;
    }

    @Override
    public String getAttributeNames() {
        return "knjigaID, autori, izdavac, naslov, isbn";
    }

    @Override
    public String getTableName() {
        return "knjiga";
    }

    @Override
    public String getAttributeValues() {
        return knjigaID + ", '" + autori + "', '" + izdavac + "', '" + naslov + "', '" + isbn + "'";
    }

    @Override
    public String setAttributeValues() {
        return "autori='" + autori + "', izdavac='" + izdavac + "', naslov='" + naslov + "', isbn='" + isbn + "'";
    }

    @Override
    public void setID(int ID) {
        this.knjigaID = ID;
    }

    @Override
    public int getID() {
        return knjigaID;
    }

    @Override
    public String getQueryCondition() {
        return "knjigaID = " + knjigaID;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        return new Knjiga(
                rs.getInt("knjigaID"),
                rs.getString("autori"),
                rs.getString("izdavac"),
                rs.getString("naslov"),
                rs.getString("isbn")
        );
    }

    @Override
    public State getState() {
        return State.UNCHANGED;
    }

    public int getKnjigaID() {
        return knjigaID;
    }

    public void setKnjigaID(int knjigaID) {
        this.knjigaID = knjigaID;
    }

    public String getAutori() {
        return autori;
    }

    public void setAutori(String autori) {
        this.autori = autori;
    }

    public String getIzdavac() {
        return izdavac;
    }

    public void setIzdavac(String izdavac) {
        this.izdavac = izdavac;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(knjigaID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Knjiga other = (Knjiga) obj;
        return knjigaID == other.knjigaID;
    }

    @Override
    public String toString() {
        return knjigaID + " - " + naslov + " - " + autori;
    }
}
