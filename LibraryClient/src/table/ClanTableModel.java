package table;

import domain.Clan;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ClanTableModel extends AbstractTableModel {

    private final String[] columns = {"Шифра", "Име", "Презиме", "Имејл", "Место"};
    private List<Clan> data = new ArrayList<>();

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
        Clan c = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getClanID();
            case 1 -> c.getIme();
            case 2 -> c.getPrezime();
            case 3 -> c.getEmail();
            case 4 -> c.getMesto();
            default -> "";
        };
    }

    public void setData(List<Clan> data) {
        this.data = data == null ? new ArrayList<>() : data;
        fireTableDataChanged();
    }

    public Clan getAt(int row) {
        if (row < 0 || row >= data.size()) {
            return null;
        }
        return data.get(row);
    }
}
