import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.TreeMap;
import java.util.Set;

public class EditPoint
{
    private JComboBox<String> jComboBox;
    private String key;
    private ObjectOutputStream objectOutputStream;
    private TreeMap<String, String> treeMap;
    private Set<String> set;
    private File file;
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    JFrame jFrame;

    public EditPoint()
    {
        try
        {
            file = new File("D://point.obj");
            jFrame = new JFrame("");
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            treeMap = (TreeMap<String, String>) objectInputStream.readObject();
            set = treeMap.keySet();
            frameInit();
        } catch (IOException e)
        {
            new mDialog("错误", "没有文件！", jFrame);
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void frameInit()
    {

        jFrame.setBounds((toolkit.getScreenSize().width - 350) / 2, (toolkit.getScreenSize().height - 450) / 2, 350, 450);

        jFrame.setLayout(new FlowLayout());

        jComboBox = new JComboBox<String>();
        jComboBox.setPreferredSize(new Dimension(270, 30));
        for (String o : set) {
            jComboBox.addItem(o);
        }
        JTextArea jTextArea = new JTextArea(15, 30);
        jTextArea.setText(treeMap.get(jComboBox.getSelectedItem()));

        jComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                jTextArea.setText(treeMap.get(jComboBox.getSelectedItem()));
            }
        });
        JButton okayButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");
        cancelButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });
        okayButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                String string = jTextArea.getText();
                treeMap.put((String) jComboBox.getSelectedItem(), string);
                try
                {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                    objectOutputStream.writeObject(treeMap);
                    new mDialog("成功", "数据成功修改", jFrame);
                    jFrame.setVisible(false);
                } catch (IOException e1)
                {
                    new mDialog("失败", "数据异常！", jFrame);
                }

            }
        });
        jFrame.add(jComboBox);
        jFrame.add(jTextArea);
        jFrame.add(cancelButton);
        jFrame.add(okayButton);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new EditPoint();
    }
}
