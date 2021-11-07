package serverudp;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class LoginFrame {

    JFrame login = new JFrame("Login to server");
    JLabel label2 = new JLabel("Password:");
    JTextField field2 = new JTextField(10);
    JButton loginBut = new JButton("Login");
    JPanel loginPan = new JPanel();

    public LoginFrame() {
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(800, 400);
        login.setVisible(true);

        GroupLayout loginLayout = new GroupLayout(loginPan);
        loginPan.setLayout(loginLayout);

        loginLayout.setAutoCreateGaps(true);
        loginLayout.setAutoCreateContainerGaps(true);
        loginLayout.setHorizontalGroup(loginLayout.createSequentialGroup()
                .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label2)
                )
                .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(field2)
                        .addComponent(loginBut))
        );

        loginLayout.setVerticalGroup(loginLayout.createSequentialGroup()
                .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(field2))
                .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginBut))
        );

        login.add(loginPan);

    }
}
