import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

public class Client
{
    private JFrame jFrame;
    private JTextField accountText, passwdText, inviteText;
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    private final Font font;

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    private String tips;
    private boolean isAdmin = false;

    public Client()
    {
        init();
        font = new Font("΢���ź�", 1, 18);
    }

    public void init()
    {
        jFrame = new JFrame("�û���¼");
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 270) / 2, (toolkit.getScreenSize().height - 200) / 2, 270, 200);

        componentInit( new JPanel(),  new JLabel(), accountText = new JTextField(), "   �ʺ�");

        componentInit(new JPanel(), new JLabel(), passwdText = new JTextField(), "   ����");
        JPanel invitejPanel;
        JLabel inviteLabel;
        componentInit(invitejPanel = new JPanel(), inviteLabel = new JLabel(), inviteText = new JTextField(), "������");

        loginButtonInit();
        registButtonInit();

        jFrame.setVisible(true);
        jFrame.setResizable(false);

    }

    public void componentInit(JPanel jPanel, JLabel jLabel, JTextField jTextField, String str)
    {
        jPanel.setLayout(new FlowLayout());

        jLabel.setText(str);
        jLabel.setFont(font);

        jTextField.setText("");
        jTextField.setColumns(14);

        jPanel.add(jLabel);
        jPanel.add(jTextField);

        jFrame.add(jPanel);
    }

    public void loginButtonInit()
    {
        JButton loginButton = new JButton("��¼");

        loginButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    Socket socket = new Socket("localhost", 10001);
                    //ÿ���һ�α����½�һ���µ�Socket�������޷�һֱ��ȡ����˵����ݣ�����ԭ�������պ�֤
                    sendInfo(0, socket);
                    if (tips.contains("�ɹ�"))
                    {
                        Home home = new Home(isAdmin);
                    }
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
        jFrame.add(loginButton);
    }

    public void registButtonInit()
    {
        JButton registerButton = new JButton("ע��");
        registerButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    Socket socket = new Socket("localhost", 10001);
                    sendInfo(1, socket);
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
        jFrame.add(registerButton);
    }

    public void sendInfo(int code, Socket socket)//��װ��ע���¼�Ĺ��Է���
    {

        String account = accountText.getText();
        String passwd = passwdText.getText();
        String string;
        if (code == 0)
        {
            string = "��¼";
        }
        else
            string = "ע��";
        try
        {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //����ͬ��Ҫʹ��ÿ�ε��µ�Socket��ȡд����

            bufferedWriter.write(code + "\r\n");
            bufferedWriter.flush();//�����ʾ�����߷�����ǵ�¼����ע�ᣬ��¼Ϊ0��ע��Ϊ1

            bufferedWriter.write(account + "\r\n");//����Ҫ�н�����ʾ���������˲���ֹͣ��ȡ
            bufferedWriter.flush();                    //ˢ����
            bufferedWriter.write(passwd + "\r\n");
            bufferedWriter.flush();
            if (code == 1)          //ע��Ļ���һ�������룬��Ҫ�ഫ��һ��
            {
                bufferedWriter.write(inviteText.getText() + "\r\n");
                bufferedWriter.flush();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            tips = bufferedReader.readLine();
            if (tips.contains("����Ա"))
            {
                isAdmin = true;
            }
        } catch (IOException e1)
        {
            new mDialog(string + "���", "��������ʧ�ܣ�",jFrame);

        } catch (NullPointerException e1)
        {
            new mDialog(string + "���", "����˹رգ����ȴ򿪷���ˣ�",jFrame);
        } finally
        {
            try
            {
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e1)
            {
                tips = "���ر�ʧ�ܣ�";
                new mDialog(string + "���", tips,jFrame);
            }
            new mDialog(string + "���", tips,jFrame);
        }
    }





    public static void main(String[] args)
    {
        Client client = new Client();
    }

}
