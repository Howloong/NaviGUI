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
        jFrame = new JFrame("管理员菜单");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        jFrame.setBounds((toolkit.getScreenSize().width - 250) / 2, (toolkit.getScreenSize().height - 310) / 2, 250, 310);
        jFrame.setLayout(new FlowLayout());

        JPanel childPanel = new JPanel();
        childPanel.setLayout(new FlowLayout());
        JButton cancelButton = new JButton("关闭");
        childPanel.add(cancelButton);


        cancelButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });

        JButton createPoint = new JButton("1.创建景点信息");
        Font font = new Font("微软雅黑", 1, 20);
        createPoint.setFont(font);
        createPoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new CreatePoint();
            }
        });

        JButton editPoint = new JButton("2.修改景点信息");
        editPoint.setFont(font);
        editPoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new EditPoint();
            }
        });

        JButton deletePoint = new JButton("3.删除景点信息");
        deletePoint.setFont(font);
        deletePoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new DeletePoint();
            }
        });

        JButton createLength = new JButton("4.创建道路信息");
        createLength.setFont(font);
        createLength.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new CreateLength(jFrame);
            }
        });

        JButton editLength = new JButton("5.修改道路信息");
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