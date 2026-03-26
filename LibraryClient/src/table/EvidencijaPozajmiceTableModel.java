package table;

import domain.EvidencijaPozajmice;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import util.Utility;

public class EvidencijaPozajmiceTableModel extends AbstractTableModel {

    private final String[] columns = {"Шифра", "Датум", "Библиотекар", "Члан", "Број ставки", "Укупна казна"};
    private List<EvidencijaPozajmice> data = new ArrayList<>();

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EvidencijaPozajmice e = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> e.getEvidencijaPozajmiceID();
            case 1 -> formatDate(e.getDatumIznajmljivanja());
            case 2 -> e.getBibliotekar();
            case 3 -> e.getClan();
            case 4 -> e.getBrojStavkiIznajmljivanja();
            case 5 -> e.getUkupanIznosKazne();
            default -> "";
        };
    }

    private String formatDate(Date date) {
        return date == null ? "" : Utility.DATE_FORMAT.format(date);
    }

    public void setData(List<EvidencijaPozajmice> data) {
        this.data = data == null ? new ArrayList<>() : data;
        fireTableDataChanged();
    }

    public EvidencijaPozajmice getAt(int row) {
        if (row < 0 || row >= data.size()) {
            return null;
        }
        return data.get(row);
    }
}
