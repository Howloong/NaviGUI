import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class NormalMenu
{

    private final JFrame jFrame;

    public NormalMenu()
    {
        jFrame = new JFrame("���ܲ˵�");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        jFrame.setBounds((toolkit.getScreenSize().width - 250) / 2, (toolkit.getScreenSize().height - 200) / 2, 250, 200);
        jFrame.setLayout(new FlowLayout());
        JButton visitButton = new JButton("1.���������Ϣ");
        Font font = new Font("΢���ź�", 1, 20);
        visitButton.setFont(font);
        JButton searchButton = new JButton("2.��ѯ���·��");
        searchButton.setFont(font);
        JButton okayButton = new JButton("�ر�");
        okayButton.setFont(font);


        visitButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new VisitPoint();
            }
        });

        searchButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new SearchLength();
            }
        });

        okayButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });

        jFrame.add(visitButton);
        jFrame.add(searchButton);
        jFrame.add(okayButton);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

    }

}

