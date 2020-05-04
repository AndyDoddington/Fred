package net.doddington.fred;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.SystemPropertiesPropertySource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TreePopup extends JPopupMenu {
    private static final Logger logger = LogManager.getLogger(TreePopup.class);

    public TreePopup(FileTreeNode node) {

        if (node.canBeDeleted()) {
            JMenuItem delete = new JMenuItem("Delete");
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    logger.info("Delete child");
                }
            });

            add(delete);
        };

        add(new JSeparator());

        if (node.canHaveChildrenAdded()) {
            JMenuItem add = new JMenuItem("Add");
            add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    logger.info("Add child");
                    node.addDummyChild();
                }
            });
            add(add);
        };
    }
}
