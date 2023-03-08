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
        font = new Font("微软雅黑", 1, 18);
    }

    public void init()
    {
        jFrame = new JFrame("用户登录");
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 270) / 2, (toolkit.getScreenSize().height - 200) / 2, 270, 200);

        componentInit( new JPanel(),  new JLabel(), accountText = new JTextField(), "   帐号");

        componentInit(new JPanel(), new JLabel(), passwdText = new JTextField(), "   密码");
        JPanel invitejPanel;
        JLabel inviteLabel;
        componentInit(invitejPanel = new JPanel(), inviteLabel = new JLabel(), inviteText = new JTextField(), "邀请码");

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
        JButton loginButton = new JButton("登录");

        loginButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    Socket socket = new Socket("localhost", 10001);
                    //每点击一次必须新建一个新的Socket，否则无法一直获取服务端的数据，具体原因不明，日后考证
                    sendInfo(0, socket);
                    if (tips.contains("成功"))
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
        JButton registerButton = new JButton("注册");
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

    public void sendInfo(int code, Socket socket)//封装了注册登录的共性方法
    {

        String account = accountText.getText();
        String passwd = passwdText.getText();
        String string;
        if (code == 0)
        {
            string = "登录";
        }
        else
            string = "注册";
        try
        {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //这里同样要使用每次的新的Socket获取写入流

            bufferedWriter.write(code + "\r\n");
            bufferedWriter.flush();//输出标示，告诉服务端是登录还是注册，登录为0，注册为1

            bufferedWriter.write(account + "\r\n");//必须要有结束标示，否则服务端不会停止读取
            bufferedWriter.flush();                    //刷新流
            bufferedWriter.write(passwd + "\r\n");
            bufferedWriter.flush();
            if (code == 1)          //注册的话有一个邀请码，需要多传输一次
            {
                bufferedWriter.write(inviteText.getText() + "\r\n");
                bufferedWriter.flush();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            tips = bufferedReader.readLine();
            if (tips.contains("管理员"))
            {
                isAdmin = true;
            }
        } catch (IOException e1)
        {
            new mDialog(string + "结果", "交换数据失败！",jFrame);

        } catch (NullPointerException e1)
        {
            new mDialog(string + "结果", "服务端关闭！请先打开服务端！",jFrame);
        } finally
        {
            try
            {
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e1)
            {
                tips = "流关闭失败！";
                new mDialog(string + "结果", tips,jFrame);
            }
            new mDialog(string + "结果", tips,jFrame);
        }
    }





    public static void main(String[] args)
    {
        Client client = new Client();
    }

}
