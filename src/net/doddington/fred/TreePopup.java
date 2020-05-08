package net.doddington.fred;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.SystemPropertiesPropertySource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

        if (node.canHaveChildrenAdded()) {
            add(new JSeparator());

            JMenuItem add = new JMenuItem("Add");
            add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    logger.info("Add child");
                    node.addDummyChild();
                }
            });
            add(add);
        };

        // not directory, means normal file
        if (!node.canHaveChildrenAdded()) {
            add(new JSeparator());

            JMenuItem add = new JMenuItem("Type");
            add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    logger.info("Type contents");
                    try {
                        node.typeContents();
                    } catch (IOException e) {
                        System.out.println("Error typing file: " + e.getMessage() );
                    }
                }
            });

            add(add);
        };

        this.pack();
    }
}
