package table;

import domain.Bibliotekar;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class BibliotekarTableModel extends AbstractTableModel {

    private final String[] columns = {"Шифра", "Корисничко име", "Име", "Презиме", "Имејл"};
    private final List<Bibliotekar> data = new ArrayList<>();

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
        Bibliotekar b = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> b.getBibliotekarID();
            case 1 -> b.getKorisnickoIme();
            case 2 -> b.getIme();
            case 3 -> b.getPrezime();
            case 4 -> b.getEmail();
            default -> "";
        };
    }

    public void addBibliotekar(Bibliotekar bibliotekar) {
        if (bibliotekar == null || data.contains(bibliotekar)) {
            return;
        }
        data.add(bibliotekar);
        fireTableDataChanged();
    }

    public void removeBibliotekar(Bibliotekar bibliotekar) {
        if (bibliotekar == null) {
            return;
        }
        data.remove(bibliotekar);
        fireTableDataChanged();
    }

    public void clear() {
        data.clear();
        fireTableDataChanged();
    }
}
