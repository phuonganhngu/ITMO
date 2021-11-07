package serverudp;

import classes.Human;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.*;
import javax.swing.tree.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import myPack.Humandb;

public class JTreeList extends JFrame{
        Set<Human> human;
        ArrayList<DatagramPacket> packetList;
        DatagramSocket socket;
        HumanORM db = new HumanORM();
        
        public JTreeList(Set<Human> human, ArrayList<DatagramPacket> packetList, DatagramSocket socket){
            this.human = human;
            this.packetList = packetList;
            this.socket = socket;
        }   
        private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Human List");
        private JTree tree = new JTree(root);
        JFrame frame = new JFrame();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
      
        JTextField nameField = new JTextField();
        
        String fileName = "D:\\NetBeansProjects\\ServerUDP_7_Exp\\src\\serverudp\\HumanList.xml";
        
            
    public void creatFrame(){

        createMenuBar();
        createFunctionPanel();
        
        tree.setEditable(true);
        frame.add(tree);
        frame.add(new JScrollPane(tree));
        
        frame.setTitle("Human List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setVisible(true);   
    }
    
    public void createFunctionPanel(){
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);     
        panel.setLayout(layout); 
        layout.setAutoCreateGaps(true);  
        layout.setAutoCreateContainerGaps(true); 
  
        JButton addBut = new JButton("Add");
        JButton delBut = new JButton("Delete");
        JButton editBut = new JButton("Edit"); 
        
        addBut.setPreferredSize(new Dimension(40, 40));
  
        layout.setVerticalGroup(layout.createSequentialGroup()  
                .addGroup(layout.createParallelGroup(TRAILING).addComponent(addBut))  
                .addGroup(layout.createParallelGroup(TRAILING).addComponent(delBut))
                .addGroup(layout.createParallelGroup(TRAILING).addComponent(editBut)));  
  
        layout.setHorizontalGroup(layout.createSequentialGroup()  
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(addBut)
                        .addComponent(delBut)
                        .addComponent(editBut)))
                ;  
        
        delBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                removeSelectedNode();
            }
        });
        
        addBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                createAddWindow();
            }
        });
        
        editBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                createEditWindow();
            }
      
        });
        frame.add(panel, BorderLayout.WEST);
    }
    
    public void createAddWindow(){
        JFrame addFrame = new JFrame("Add human");
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setSize(200,250);
        addFrame.setVisible(true);
        JButton closeBut = new JButton("Ok");
        
        JLabel label1 = new JLabel("Name:");
        JTextField field1 = new JTextField(20);
        JLabel label2 = new JLabel("Color:");
        JTextField field2 = new JTextField(20);
        JLabel label3 = new JLabel("Size:");
        JTextField field3 = new JTextField(20);
        JLabel label4 = new JLabel("X:");
        JTextField field4 = new JTextField(20);
        JLabel label5 = new JLabel("Y:");
        JTextField field5 = new JTextField(20);
        JLabel label6 = new JLabel("Time:");
        JTextField field6 = new JTextField(20);
        JPanel addPan = new JPanel();
        GroupLayout aLayout = new GroupLayout(addPan);
        addPan.setLayout(aLayout);
        
        aLayout.setAutoCreateGaps(true);
        aLayout.setAutoCreateContainerGaps(true);
        aLayout.setHorizontalGroup(aLayout.createSequentialGroup()
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(label1)
                .addComponent(label2)
                .addComponent(label3)
                .addComponent(label4)
                .addComponent(label5)
                .addComponent(label6)
            )
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(field1)
                .addComponent(field2)
                .addComponent(field3)
                .addComponent(field4)
                .addComponent(field5)
                .addComponent(field6)
                .addComponent(closeBut))
            
        );
        
        aLayout.setVerticalGroup(aLayout.createSequentialGroup()
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(label1)
                .addComponent(field1))
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(label2)
                .addComponent(field2))
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(label3)
                .addComponent(field3))
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(label4)
                .addComponent(field4))
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(label5)
                .addComponent(field5))
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(label6)
                .addComponent(field6))
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(closeBut))
        );
        
        addFrame.add(addPan);
        
        closeBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                try {
                    int id = db.getNextId();
                    Human p = new Human(id, field1.getText(), 
                            field2.getText(), 
                            Integer.parseInt(field3.getText()),
                            Integer.parseInt(field4.getText()),
                            Integer.parseInt(field5.getText()),
                            field6.getText());
                    p.setAction("Add");
                    addNode(p);
                    model.reload(root);
                    System.out.println(p);
                    human.add(p);
                    Humandb person = new Humandb(p);
                    db.create(person);
                    ServerUpdateThread updThread = new ServerUpdateThread(socket, packetList, p);
                    new Thread(updThread).start();
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Input value is not match the given type!");
                }
                addFrame.dispose();
            }
        });          
    }
    
    public void createEditWindow(){
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    if (selectedNode != null){
        JFrame editFrame = new JFrame("Edit content");
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setSize(200,250);
        editFrame.setVisible(true);
        JButton closeBut = new JButton("Replace");
        
        JLabel label1 = new JLabel("New value:");
        JTextField field1 = new JTextField(20);
        JPanel editPan = new JPanel();
        GroupLayout aLayout = new GroupLayout(editPan);
        editPan.setLayout(aLayout);
        
        aLayout.setAutoCreateGaps(true);
        aLayout.setAutoCreateContainerGaps(true);
        aLayout.setHorizontalGroup(aLayout.createSequentialGroup()
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(label1)
            )
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(field1)
                .addComponent(closeBut))   
        );
        
        aLayout.setVerticalGroup(aLayout.createSequentialGroup()
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(label1)
                .addComponent(field1))
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addGroup(aLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(closeBut)))
        );
        
        editFrame.add(editPan);
        
        closeBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                DefaultMutableTreeNode node = selectedNode;
                String oldString = selectedNode.toString();
                String newValue = field1.getText();
                if (selectedNode == root){
                    selectedNode.setUserObject(newValue);
                    editFrame.dispose();
                } else 
                if (selectedNode.getParent() != root){ 
                    while (node.getParent() != root){
                        node = (DefaultMutableTreeNode) node.getParent();
                    }
                }  
                Human p = getInfor(node);
                Human newP = p;
                Human oldP = new Human(p.id, p.name, p.color, p.size, p.PosX, p.PosY, p.time);
                oldP.setAction("Delete");
                ServerUpdateThread updThread = new ServerUpdateThread(socket, packetList, oldP);
                new Thread(updThread).start();
                final Human exp;
                human.stream().
                        filter(h -> (h.equals(oldP))).
                        forEach(h -> db.delete(new Humandb(h)));
                try {
                    if (oldString.startsWith("COLOR: ")) {
                        newP.setColor(newValue);
                        human.stream().filter(h -> h.equal(p)).forEach((Human h) -> h.setColor(newValue));
                        selectedNode.setUserObject("COLOR: " + newValue);
                        
                    } else
                    if (oldString.startsWith("SIZE: ")) {
                        newP.setSize(Integer.parseInt(newValue));
                        human.stream().filter(h -> h.equal(p)).forEach((Human h) -> h.setSize(Integer.parseInt(newValue)));
                        selectedNode.setUserObject("SIZE: " + newValue);
                    } else
                    if (oldString.startsWith("X: ")) {
                        newP.setPosX(Integer.parseInt(newValue));
                        human.stream().filter(h -> h.equal(p)).forEach((Human h) -> h.setPosX(Integer.parseInt(newValue)));
                        selectedNode.setUserObject("X: " + newValue);
                    } else
                    if (oldString.startsWith("Y: ")) {
                        newP.setPosY(Integer.parseInt(newValue));
                        human.stream().filter(h -> h.equal(p)).forEach((Human h) -> h.setPosY(Integer.parseInt(newValue)));
                        selectedNode.setUserObject("Y: " + newValue);
                    } else
                    if (oldString.startsWith("TIME: ")) {
                        newP.setTime(newValue);
                        human.stream().filter(h -> h.equal(p)).forEach((Human h) -> h.setTime(newValue));
                        selectedNode.setUserObject("TIME: " + newValue);
                    } else {
                        newP.setName(newValue);
                        human.stream().filter(h -> h.equal(p)).forEach((Human h) -> h.setName(newValue));
                        selectedNode.setUserObject(newValue);
                    };
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Input value is not match the given type!");
                } finally {editFrame.dispose();}         
                model.reload(root); 
                newP.setAction("Add");

                ServerUpdateThread updThread1 = new ServerUpdateThread(socket, packetList, newP);
                new Thread(updThread1).start();
            }
        });  
        } else JOptionPane.showMessageDialog(null,"Please choose a target first!");         
    }
    
    public void addNode(Human h){
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(h.name); 
        root.add(node);
        node.add(new DefaultMutableTreeNode("COLOR: " + h.color));
        node.add(new DefaultMutableTreeNode("SIZE: " + h.size));
        node.add(new DefaultMutableTreeNode("X: " + h.PosX));
        node.add(new DefaultMutableTreeNode("Y: " + h.PosY));       
        node.add(new DefaultMutableTreeNode("TIME: " + h.time));   
    }
    
    public void createMenuBar(){
        JMenuBar bar = new JMenuBar();
        JMenu menuEdit = new JMenu("File");
        JMenu menuAct = new JMenu("Action");
        
        JMenuItem saveItem = new JMenuItem("Save list");
        JMenuItem readItem = new JMenuItem("Read file");
        JMenuItem expandItem = new JMenuItem("Expand all");
        JMenuItem collapseItem = new JMenuItem("Collapse");
        
        expandItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                setAllNodeState(tree, root, true);
            }
        });
        
        collapseItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                setAllNodeState(tree, root, false);
            }
        });
        
        readItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                root.removeAllChildren();
                model.reload();
                human.removeAll(human);
                Human exp = new Human(0,"","Black",0,0,0,"");
                exp.setAction("Reset");

                List <Humandb> humanDB = db.read();
                humanDB.stream().forEach(h -> human.add(new Human(h.getHumanid(), h.getName(),
                h.getColor(), h.getSize(), h.getPosx(),h.getPosy(), h.getTime(), h.getCreated())));
                System.out.println(human);
                human.stream().forEach((p) -> {
                    p.setAction("Add");
                    System.out.println(p);
                    addNode(p);});
                model.reload(root);
                int i=0;
                new Thread(new ServerUpdateThread(socket, packetList, exp)).start(); 
                
                human.stream().forEach((p) ->{
                    new Thread(new ServerUpdateThread(socket, packetList, p)).start(); 
                });
            }
        });
        
        saveItem.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent ev){
                try {
                    XmlFile.write(fileName, human);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(JTreeList.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(JTreeList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        menuEdit.add(saveItem);
        menuEdit.add(readItem);
        menuAct.add(expandItem);
        menuAct.add(collapseItem);
        
        bar.add(menuEdit);
        bar.add(menuAct);
        frame.setJMenuBar(bar);
    }
    
    public void removeSelectedNode(){
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode != null){
            if (selectedNode == root){
                JOptionPane.showMessageDialog(null, "You can not delete the entire list!!");
            }
            if (selectedNode.getParent() != root){
                while (selectedNode.getParent() != root){
                    selectedNode = (DefaultMutableTreeNode) selectedNode.getParent();
                }  
            }
            Human p = getInfor(selectedNode); 
            model.removeNodeFromParent(selectedNode);
            p.setAction("Delete");
            ServerUpdateThread updThread = new ServerUpdateThread(socket, packetList, p);
            new Thread(updThread).start();
            human.stream().filter(h -> h.equal(p)).forEach((Human h) -> {
                    db.delete(new Humandb(h));
                    human.remove(h);
                    });
            System.out.println("Deleted " + p);
            //System.out.println(human);
        } else JOptionPane.showMessageDialog(null,"Please choose a target first!");
    }  
       
    public Human getInfor(DefaultMutableTreeNode node){
        Human h = new Human(0,"","",0,0,0,"");
        h.setName(node.toString());
        h.setColor(node.getChildAt(0).toString().replaceFirst("COLOR: ", ""));
        h.setSize(Integer.parseInt(node.getChildAt(1).toString().replaceFirst("SIZE: ", "")));
        h.setPosX(Integer.parseInt(node.getChildAt(2).toString().replaceFirst("X: ", "")));
        h.setPosY(Integer.parseInt(node.getChildAt(3).toString().replaceFirst("Y: ", "")));
        h.setTime(node.getChildAt(4).toString().replaceFirst("TIME: ", ""));
        return h;
    }
    
    public static void setAllNodeState(JTree tree, DefaultMutableTreeNode node, boolean expanded) {
      ArrayList<DefaultMutableTreeNode> list = Collections.list(node.children());
      list.forEach((treeNode) -> {
          setAllNodeState(tree, treeNode, expanded);
            });
      if (!expanded && node.isRoot()) {
          return;
      }
      TreePath path = new TreePath(node.getPath());
      if (expanded) {
          tree.expandPath(path);
      } else {
          tree.collapsePath(path);
      }
  }   
}