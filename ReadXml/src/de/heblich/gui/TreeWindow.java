package de.heblich.gui;


import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import de.heblich.logic.TreeElement;

public class TreeWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private TreeElement root;
	private File path;
	private JLabel image = new JLabel();
	private JTree tree;
	
	public TreeWindow(TreeElement root, File path) {
		super("TreeWindow");
		this.root = root;
		this.path = path;
		tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		JScrollPane sp = new JScrollPane(tree);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, new JScrollPane(image));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(600);
		
		this.add(splitPane);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreeElement te = (TreeElement) e.getNewLeadSelectionPath().getLastPathComponent();
				File f = new File(TreeWindow.this.path, te.getImage());
				ImageIcon ii = new ImageIcon(f.getAbsolutePath());
				image.setIcon(ii);
			}
		});
	}
	
	
}
