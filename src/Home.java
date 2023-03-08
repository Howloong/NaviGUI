import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;


public class Home
{
    private JFrame jFrame;
    private final Font titleFont;
    private final Font charFont;
    private final Toolkit toolkit;

    private final File pointFile;
    private final File lengthFile;
    private final File mapFile;

    private boolean isAdmin;

    public Home(boolean isAdmin)
    {
        this.isAdmin = isAdmin;//ȷ���û����
        init();
        charFont = new Font("΢���ź�", 1, 20);
        titleFont = new Font("΢���ź�", 1, 28);
        toolkit = Toolkit.getDefaultToolkit();
        pointFile = new File("D://point.obj");
        lengthFile = new File("D://length.obj");
        mapFile = new File("D://map.png");
    }

    public void init()
    {
        jFrame = new JFrame();
        jFrame.setLayout(new BorderLayout());

        titleInit();//��ʼ��������
        mapInit();//��ʼ����ͼ

        jFrame.setBounds((toolkit.getScreenSize().width - 700) / 2, (toolkit.getScreenSize().height - 450) / 2, 700, 450);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton admin = new JButton("����Ա�˵�");
        admin.setFont(charFont);
        admin.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new AdminMenu();
            }
        });

        JButton menu = new JButton("���ܲ˵�");
        menu.setFont(charFont);
        menu.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new NormalMenu();
            }
        });
        buttonPanel.add(menu);
        if (isAdmin)
        {
            buttonPanel.add(admin);
            adminTips();
        }
        JButton close = new JButton("�ر�");
        close.setFont(charFont);
        close.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.exit(0);
            }
        });
        buttonPanel.add(close);

        jFrame.add(buttonPanel, BorderLayout.SOUTH);

        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public void titleInit()
    {
        JLabel title = new JLabel("У԰����ϵͳ", SwingConstants.CENTER);
        title.setFont(titleFont);
        jFrame.add(title, BorderLayout.NORTH);//��������
    }

    public void mapInit()
    {
        ImageIcon imageIcon = new ImageIcon(mapFile.getPath());
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth(),
                imageIcon.getIconHeight(), Image.SCALE_DEFAULT));
        JLabel map = new JLabel();
        map.setBounds(0, 0, 690, 400);
        map.setHorizontalAlignment(0);
        map.setIcon(imageIcon);
        JPanel mapLabel = new JPanel();
        mapLabel.setSize(690, 400);
        mapLabel.add(map);
        jFrame.add(mapLabel, BorderLayout.CENTER);//��ͼ��ʾ
    }


    public void adminTips()
    {
        String errorTitle = "���ݴ���";
        try
        {
            checkFile(mapFile, "��ͼ");
        } catch (IOException e)
        {
            e.printStackTrace();
            new mDialog(errorTitle, "�����Ա��¼���ͼ���ݣ�", jFrame);
            //writeMap
        }
        try
        {
            checkFile(pointFile, "����");

        } catch (IOException e)
        {
            e.printStackTrace();
            new mDialog(errorTitle, "�����Ա��¼�뾰�����ݣ�", jFrame);
            //writePoint
        }
        try
        {
            checkFile(lengthFile, "����");

        } catch (IOException e)
        {
            e.printStackTrace();
            new mDialog(errorTitle, "�����Ա��¼��������ݣ�", jFrame);
            //writeLength
        }
    }

    public void checkFile(File file, String string) throws IOException
    {
        if (!file.exists() || file.length() == 0)
        {
            throw new IOException(string + "�ļ���ʧ�ܣ�");
        }
    }


}
