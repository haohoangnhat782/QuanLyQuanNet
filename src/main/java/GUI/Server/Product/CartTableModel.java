package GUI.Server.Product;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CartTableModel extends AbstractTableModel {
    private List<CartItem> items;
    private String[] columns = {"Product","Price","Quantity","Total"};
    public CartTableModel(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CartItem item = items.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getProduct().getName();
            case 1:
                return item.getProduct().getPrice();
            case 2:
                return item.getQuantity();
            case 3:
                return item.getPrice();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
