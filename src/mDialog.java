import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mDialog
{
    private final String title;
    private final String tips;
    private final JFrame jFrame;
    private JDialog jDialog;
    private final Toolkit toolkit;
    private final Font font;


    public mDialog(String title, String tips, JFrame jFrame)
    {
        this.tips = tips;
        this.title = title;
        this.jFrame = jFrame;
        init();

        toolkit = Toolkit.getDefaultToolkit();
        font = new Font("΢���ź�", 1, 18);
    }

    public void init()//�Ի���
    {
        jDialog = new JDialog(jFrame, title, true);
        jDialog.setTitle(title);
        jDialog.setModal(true);
        jDialog.setBounds((toolkit.getScreenSize().width - 300) / 2, (toolkit.getScreenSize().height - 100) / 2, 300, 100);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JLabel jLabel = new JLabel(tips);
        jLabel.setFont(font);

        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton jButton = new JButton("ȷ��");

        jPanel.add(jLabel, BorderLayout.CENTER);

        jPanel.add(jButton, BorderLayout.SOUTH);

        jDialog.add(jPanel);

        jDialog.validate();//ͬ�����ݣ��������ԭ��һ��

        jButton.addActionListener(new ActionListener()

        {

            @Override

            public void actionPerformed(ActionEvent e)

            {

                jDialog.setVisible(false);//���ȷ������Ϊ���ɼ�

            }

        });

        jDialog.setResizable(false);//���ɵ�����С

        jDialog.setVisible(true);


    }


}
