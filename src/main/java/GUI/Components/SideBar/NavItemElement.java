package GUI.Components.SideBar;

import lombok.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Data
public class NavItemElement   {


    private List<NavItem> children = new ArrayList<>();
    private NavItem parent;
    public void closeCollapse(){
        children.forEach(child -> {
            child.setVisible(false);
            child.setSelected(false);
        });
        parent.setSelected(false);
        parent.revalidate();
        parent.repaint();
    }
    public void openCollapse(){
        children.forEach(child -> {
            child.setVisible(true);
        });
        parent.setSelected(true);
        parent.revalidate();
        parent.repaint();
    }
    public void initChildEvent(JPanel contentPanel){
        children.forEach(child -> {
            child.addActionListener(e -> {
                contentPanel.removeAll();
                contentPanel.add(child.getContentPanel());
                child.getContentPanel().setVisible(true);
                contentPanel.repaint();
                contentPanel.revalidate();
                children.forEach(c -> {
                    c.setSelected(false);
                });
                child.setSelected(true);

            });
        });
    }

}