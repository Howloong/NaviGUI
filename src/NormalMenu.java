import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class NormalMenu
{

    private final JFrame jFrame;

    public NormalMenu()
    {
        jFrame = new JFrame("功能菜单");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        jFrame.setBounds((toolkit.getScreenSize().width - 250) / 2, (toolkit.getScreenSize().height - 200) / 2, 250, 200);
        jFrame.setLayout(new FlowLayout());
        JButton visitButton = new JButton("1.浏览景点信息");
        Font font = new Font("微软雅黑", 1, 20);
        visitButton.setFont(font);
        JButton searchButton = new JButton("2.查询最短路径");
        searchButton.setFont(font);
        JButton okayButton = new JButton("关闭");
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

