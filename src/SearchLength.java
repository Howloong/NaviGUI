import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class SearchLength
{
    private final JFrame jFrame;
    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2;
    private JLabel jLabel;

    private TreeMap treeMap;
    private ArrayList<LengthInfo> arrayList;
    private Set set;

    private LengthInfo lengthInfo;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public SearchLength()
    {

        jFrame = new JFrame();
        try
        {
            File lengthFile = new File("D://length.obj");
            File pointFile = new File("D://point.obj");
            ObjectInputStream objectInputStream1 = new ObjectInputStream(new FileInputStream(lengthFile));
            ObjectInputStream objectInputStream2 = new ObjectInputStream(new FileInputStream(pointFile));
            arrayList = (ArrayList<LengthInfo>) objectInputStream1.readObject();
            lengthInfo = arrayList.get(0);

            treeMap = (TreeMap) objectInputStream2.readObject();


        } catch (IOException e)
        {
            new mDialog("����", "�޾�����Ϣ��", jFrame);
        } catch (ClassNotFoundException e)
        {
            new mDialog("����", "�ļ���Ϣ����", jFrame);
        }


        try
        {
            set = treeMap.keySet();
        } catch (NullPointerException e)
        {
            new mDialog("����", "�޵�·������Ϣ��", jFrame);
        }

        frameInit();
    }

    public void frameInit()
    {
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 200) / 2, (toolkit.getScreenSize().height - 200) / 2, 400, 200);

        jComboBox1 = new JComboBox<String>();
        jComboBox1.setPreferredSize(new Dimension(180, 30));
        jComboBox1.setFont(new Font("΢���ź�", 1, 20));
        jComboBox2 = new JComboBox<String>();
        jComboBox2.setPreferredSize(new Dimension(180, 30));
        jComboBox2.setFont(new Font("΢���ź�", 1, 20));

        for (Object o : set) {
            String string = (String) o;
            jComboBox1.addItem(string);
            jComboBox2.addItem(string);
        }
        jLabel = new JLabel();
        jLabel.setPreferredSize(new Dimension(350, 80));
        jLabel.setFont(new Font("΢���ź�", 1, 20));
        double str1 = lengthInfo.getMin(0, 1, treeMap);
        jComboBox1.addItemListener(e -> {
            double str11 = lengthInfo.getMin(jComboBox1.getSelectedIndex(), jComboBox2.getSelectedIndex(), treeMap);
            String str2 = lengthInfo.getStringBuilder();
            jLabel.setText("<html><body>" + "����·��:  " + str2 + "<br>" + "��̣�  " + str11 + "m" + "<body></html>");
        });
        jComboBox2.addItemListener(e -> {
            double str112 = lengthInfo.getMin(jComboBox1.getSelectedIndex(), jComboBox2.getSelectedIndex(), treeMap);
            String str2 = lengthInfo.getStringBuilder();
            jLabel.setText("<html><body>" + "����·��:  " + str2 + "<br>" + "��̣�  " + str112 + "m" + "<body></html>");
        });

        JButton jButton = new JButton("ȷ��");
        jButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });

        jFrame.add(jComboBox1);
        jFrame.add(jComboBox2);
        jFrame.add(jLabel);
        jFrame.add(jButton);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }


}
