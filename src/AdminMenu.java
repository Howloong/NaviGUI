import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

public class AdminMenu
{
    private final JFrame jFrame;


    public AdminMenu()
    {
        jFrame = new JFrame("����Ա�˵�");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        jFrame.setBounds((toolkit.getScreenSize().width - 250) / 2, (toolkit.getScreenSize().height - 310) / 2, 250, 310);
        jFrame.setLayout(new FlowLayout());

        JPanel childPanel = new JPanel();
        childPanel.setLayout(new FlowLayout());
        JButton cancelButton = new JButton("�ر�");
        childPanel.add(cancelButton);


        cancelButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });

        JButton createPoint = new JButton("1.����������Ϣ");
        Font font = new Font("΢���ź�", 1, 20);
        createPoint.setFont(font);
        createPoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new CreatePoint();
            }
        });

        JButton editPoint = new JButton("2.�޸ľ�����Ϣ");
        editPoint.setFont(font);
        editPoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new EditPoint();
            }
        });

        JButton deletePoint = new JButton("3.ɾ��������Ϣ");
        deletePoint.setFont(font);
        deletePoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new DeletePoint();
            }
        });

        JButton createLength = new JButton("4.������·��Ϣ");
        createLength.setFont(font);
        createLength.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new CreateLength(jFrame);
            }
        });

        JButton editLength = new JButton("5.�޸ĵ�·��Ϣ");
        editLength.setFont(font);
        editLength.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new CreateLength(jFrame);
            }
        });

        jFrame.add(createPoint);
        jFrame.add(editPoint);
        jFrame.add(deletePoint);
        jFrame.add(createLength);
        jFrame.add(editLength);
        jFrame.add(childPanel);
        jFrame.setVisible(true);
    }


}