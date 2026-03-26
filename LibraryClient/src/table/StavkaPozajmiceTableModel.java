package table;

import domain.Knjiga;
import domain.State;
import domain.StavkaPozajmice;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import util.Utility;

public class StavkaPozajmiceTableModel extends AbstractTableModel {

    private final String[] columns = {"Редни број", "Књига", "Рок враћања", "Датум враћања", "Цена кашњења", "Број дана", "Износ казне"};
    private List<StavkaPozajmice> data = new ArrayList<>();

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
        StavkaPozajmice s = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> s.getKnjiga();
            case 2 -> formatDate(s.getRokZaVracanje());
            case 3 -> formatDate(s.getDatumVracanja());
            case 4 -> s.getCenaKasnjenja();
            case 5 -> s.getBrojDanaKasnjenja();
            case 6 -> s.getIznosKazne();
            default -> "";
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 2 && columnIndex <= 4;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        StavkaPozajmice s = data.get(rowIndex);
        switch (columnIndex) {
            case 2 -> s.setRokZaVracanje(parseDate(aValue, false, s.getRokZaVracanje()));
            case 3 -> s.setDatumVracanja(parseDate(aValue, true, s.getDatumVracanja()));
            case 4 -> s.setCenaKasnjenja(parseDouble(aValue));
            default -> {
            }
        }
        s.azurirajKaznu();
        if (s.getState() == State.UNCHANGED) {
            s.setState(State.CHANGED);
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    private double parseDouble(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Number n) {
            return n.doubleValue();
        }
        String normalized = value.toString().trim();
        if (normalized.isBlank()) {
            return 0;
        }
        return Double.parseDouble(normalized);
    }

    private Date parseDate(Object value, boolean allowNull, Date fallback) {
        if (value == null) {
            return allowNull ? null : fallback;
        }

        if (value instanceof Date dateValue) {
            return dateValue;
        }

        String text = value.toString().trim();
        if (text.isBlank()) {
            return allowNull ? null : fallback;
        }

        try {
            return Utility.DATE_FORMAT.parse(text);
        } catch (ParseException ex) {
            return fallback;
        }
    }

    private String formatDate(Date date) {
        return date == null ? "" : Utility.DATE_FORMAT.format(date);
    }

    public void setData(List<StavkaPozajmice> data) {
        this.data = data == null ? new ArrayList<>() : data;
        for (StavkaPozajmice item : this.data) {
            item.azurirajKaznu();
        }
        fireTableDataChanged();
    }

    public List<StavkaPozajmice> getData() {
        return data;
    }

    public void addItem(Knjiga knjiga) {
        StavkaPozajmice s = new StavkaPozajmice();
        s.setRb(nextRb());
        s.setKnjiga(knjiga);
        s.setRokZaVracanje(new Date());
        s.setCenaKasnjenja(10);
        s.azurirajKaznu();
        s.setState(State.CREATED);
        data.add(s);
        fireTableDataChanged();
    }

    public void removeItem(int row) {
        if (row < 0 || row >= data.size()) {
            return;
        }
        data.remove(row);
        fireTableDataChanged();
    }

    public List<StavkaPozajmice> getAllForSave() {
        return data;
    }

    private int nextRb() {
        int max = 0;
        for (StavkaPozajmice s : data) {
            if (s.getRb() > max) {
                max = s.getRb();
            }
        }
        return max + 1;
    }
}
