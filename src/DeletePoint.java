import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.TreeMap;
import java.util.Set;

public class DeletePoint
{
    private JComboBox<String> jComboBox;
    private TreeMap treeMap;
    private ObjectOutputStream objectOutputStream;
    private Set set;
    private File file;
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private JFrame jFrame;

    public DeletePoint()
    {
        try
        {
            jFrame = new JFrame();
            file = new File("D://point.obj");
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            treeMap = (TreeMap) objectInputStream.readObject();
            frameInit();
        } catch (IOException e)
        {
            new mDialog("����", "û���ļ���", jFrame);
            e.printStackTrace();

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void frameInit()
    {
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 350) / 2, (toolkit.getScreenSize().height - 250) / 2, 350, 250);

        jComboBox = new JComboBox<String>();
        jComboBox.setPreferredSize(new Dimension(270,30));//���ô�С
        jFrame.add(jComboBox);
        set = treeMap.keySet();
        for (Object o : set) {
            jComboBox.addItem((String) o);
        }

        JLabel jLabel = new JLabel();
        jLabel.setText(treeMap.get(jComboBox.getSelectedItem()).toString());//���þ���������Ϣ��ʾ
        jLabel.setPreferredSize(new Dimension(270,80));
        jFrame.add(jLabel);

        JButton cancelButton = new JButton("ȡ��");
        JButton okayButton = new JButton("ȷ��");
        jFrame.add(cancelButton);
        jFrame.add(okayButton);


        jComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                jLabel.setText(treeMap.get(jComboBox.getSelectedItem()).toString());
            }
        });

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
                try
                {
                    treeMap.remove((String) jComboBox.getSelectedItem());

                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                    objectOutputStream.writeObject(treeMap);
                    new mDialog("�ɹ�", "ɾ��" + (String) jComboBox.getSelectedItem() + "�ɹ���", jFrame);
                    jLabel.setText("");
                    jFrame.setVisible(false);
                } catch (IOException e1)
                {
                    new mDialog("ʧ��", "�����쳣��", jFrame);
                } catch (NullPointerException e1)
                {
                    new mDialog("ʧ��", "�Ѿ�û�о�����Ϣ�ˣ�", jFrame);//ɾ�����ͱ��null�ˣ����쳣�͵ô���һ��
                    jFrame.setVisible(false);
                }
            }
        });

        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new DeletePoint();
    }
}
