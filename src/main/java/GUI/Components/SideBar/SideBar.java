package GUI.Components.SideBar;

import Utils.Constants;
import Utils.Helper;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SideBar {

    private JPanel rootPanel;
    private JPanel contentPanel;
    private final List<NavItemElement>  items = new ArrayList<>();
    public SideBar(JPanel rootPanel, JPanel contentPanel) {
        this.rootPanel = rootPanel;
        rootPanel.setLayout(new FlowLayout());
        this.contentPanel = contentPanel;
    }
    public void initComponent(List<Constants.Tab> tabs){
        tabs.forEach(tab -> {
            NavItem item = new NavItem();
            item.setKey(tab.getKey());
            item.setText(tab.getTitle());
            item.setIcon(tab.getIcon());
            item.setContentPanel(tab.getChildren() == null ? tab.getContentPanel() : null);
            var navItemElement = new NavItemElement(new ArrayList<>(), item);
            rootPanel.add(item);
            if (tab.getChildren() != null) {
                FlatSVGIcon iconDown = new FlatSVGIcon(Helper.getResourceFile("/icons/chevron-down.svg"));
                FlatSVGIcon iconUp = new FlatSVGIcon(Helper.getResourceFile("/icons/chevron-up.svg"));
                FlatSVGIcon dotIcon = new FlatSVGIcon(Helper.getResourceFile("/icons/dot.svg"));
                item.setIconSelected( iconUp);
                item.setDefaultIcon(iconDown);
                tab.getChildren().forEach(child -> {
                    NavItem childItem = new NavItem();
                    childItem.setBorder(new EmptyBorder(20, 50, 20, 20));
                    childItem.setKey(child.getKey());
                    childItem.setText(child.getTitle());
                    childItem.setIcon(child.getIcon());
                    childItem.setContentPanel(child.getContentPanel());

                    navItemElement.getChildren().add(childItem);
                    childItem.setVisible(false);
                    rootPanel.add(childItem);
                    childItem.setDefaultIcon(dotIcon);
                });
            }
            items.add(navItemElement);
        });
        initEvent();
    }
    public void initEvent(){
        items.forEach(item -> {
            item.getParent().addActionListener(e -> {
                items.forEach(NavItemElement::closeCollapse);
                if (!item.getParent().isSelected()) {
                    item.openCollapse();

                }
                if (item.getChildren().size() == 0) {
                    contentPanel.removeAll();
                    contentPanel.add(item.getParent().getContentPanel());
                    item.getParent().getContentPanel().setVisible(true);
                    contentPanel.repaint();
                    contentPanel.revalidate();
                    item.getParent().setSelected(true);
                }

            });
            item.initChildEvent(this.contentPanel);
        });
        items.get(0).getParent().doClick(); // click first item
    }
    public void navigateTo(String key){
        items.forEach(item -> {
            if (item.getParent().getKey().equals(key)) {
                item.getParent().doClick();
            }
            item.getChildren().forEach(child -> {
                if (child.getKey().equals(key)) {
                    child.doClick();
                }
            });
        });
    }
}
