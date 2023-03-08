import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class CreateLength
{
    private JComboBox jComboBox1, jComboBox2;
    private JTextField jTextField;
    private ObjectInputStream objectInputStream1;
    private ObjectOutputStream objectOutputStream;
    private final File lengthFile;
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    private TreeMap treeMap;
    private Set set;

    private LengthInfo lengthInfo;
    private ArrayList arrayList;

    public CreateLength(JFrame jFrame)
    {
        lengthFile = new File("D://length.obj");
        File pointFile = new File("D://point.obj");
        try
        {
            objectInputStream1 = new ObjectInputStream(new FileInputStream(pointFile));
        } catch (IOException e)
        {
            new mDialog("����", "û�о�����Ϣ��", jFrame);
        }

        try
        {
            ObjectInputStream objectInputStream2 = new ObjectInputStream(new FileInputStream(lengthFile));
            treeMap = (TreeMap) objectInputStream1.readObject();
            arrayList = (ArrayList) objectInputStream2.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            lengthInfo = new LengthInfo();
            lengthInfo.init();
            arrayList = new ArrayList<>();
            arrayList.add(lengthInfo);
            try
            {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(lengthFile));
                objectOutputStream.writeObject(arrayList);
                objectOutputStream.flush();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
        frameInit();


    }

    public void frameInit()
    {
        JFrame jFrame = new JFrame();
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 350) / 2, (toolkit.getScreenSize().height - 200) / 2, 350, 200);

        jTextField = new JTextField(27);
        jComboBox1 = new JComboBox();
        jComboBox1.setPreferredSize(new Dimension(270, 30));
        jComboBox2 = new JComboBox();
        jComboBox2.setPreferredSize(new Dimension(270, 30));

        set = treeMap.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext())
        {
            String string = (String) iterator.next();
            jComboBox1.addItem(string);
            jComboBox2.addItem(string);
        }

        int from = jComboBox1.getSelectedIndex();
        int to = jComboBox2.getSelectedIndex();

        lengthInfo = (LengthInfo) arrayList.get(0);
        jTextField.setText(lengthInfo.getLength(from, to) + "");
        jComboBox1.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                jTextField.setText(lengthInfo.getLength(jComboBox1.getSelectedIndex(), jComboBox2.getSelectedIndex()) + "");
            }
        });
        jComboBox2.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                jTextField.setText(lengthInfo.getLength(jComboBox1.getSelectedIndex(), jComboBox2.getSelectedIndex()) + "");
            }
        });

        JButton cancelButton = new JButton("ȡ��");
        JButton okayButton = new JButton("ȷ��");


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
                    double weight = Double.parseDouble(jTextField.getText().toString());
                    lengthInfo.editLength(jComboBox1.getSelectedIndex(), jComboBox2.getSelectedIndex(), weight);
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(lengthFile));
                    objectOutputStream.writeObject(arrayList);

                    new mDialog("�ɹ�", "�����޸ĳɹ���", jFrame);
                    jFrame.setVisible(false);
                } catch (NumberFormatException e1)
                {
                    e1.printStackTrace();
                    new mDialog("����", "��������ȷ��Ϣ��", jFrame);
                } catch (IOException e1)
                {
                    new mDialog("����", "��Ϣд��ʧ�ܣ�", jFrame);
                }


            }
        });

        jFrame.add(jComboBox1);
        jFrame.add(jComboBox2);
        jFrame.add(jTextField);
        jFrame.add(cancelButton);
        jFrame.add(okayButton);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
    }

    public static void main(String[] args)
    {
        new CreateLength(new JFrame());
    }

}
