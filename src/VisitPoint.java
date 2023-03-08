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
import java.util.Set;
import java.util.TreeMap;

public class VisitPoint
{
    private JFrame jFrame;
    private JComboBox<String> jComboBox;
    private JLabel jLabel;
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    private ObjectInputStream objectInputStream;

    private TreeMap treeMap;
    private Set set;

    public VisitPoint()
    {
        try
        {
            File file = new File("D://point.obj");
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            jFrame = new JFrame();
        } catch (IOException e)
        {
            new mDialog("错误", "无景点信息文件！", jFrame);
        }
        frameInit();
    }

    public void frameInit()
    {
        try
        {
            jFrame.setLayout(new BorderLayout());
            jFrame.setBounds((toolkit.getScreenSize().width - 350) / 2, (toolkit.getScreenSize().height - 250) / 2, 350, 250);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        jComboBox = new JComboBox<String>();
        jComboBox.setPreferredSize(new Dimension(270,30));
        try
        {
            treeMap = (TreeMap) objectInputStream.readObject();
            set = treeMap.keySet();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Object o : set) {
            jComboBox.addItem((String) o);
        }

        jLabel = new JLabel();
        jLabel.setPreferredSize(new Dimension(270,20));
        jLabel.setFont(new Font("宋体", 1, 20));
        jLabel.setText((String) treeMap.get(jComboBox.getSelectedItem()));

        jComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                jLabel.setText((String) treeMap.get(jComboBox.getSelectedItem()));
            }
        });

        JButton okayButton = new JButton("确定");
        okayButton.setFont(new Font("微软雅黑", 1, 20));
        okayButton.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });
        jFrame.add(jComboBox,BorderLayout.NORTH);
        jFrame.add(jLabel,BorderLayout.CENTER);
        jFrame.add(okayButton,BorderLayout.SOUTH);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new VisitPoint();
    }
}
