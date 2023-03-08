import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

public class Server
{
    private Socket socket;
    private String ipInfo;
    private BufferedReader bufferedReader;
    private String adminKey;
    private HashSet hashSet;
    private String account;
    private String passwd;
    private final File infofile;
    private boolean isAdmin = false;


    public Server(String adminKey)
    {
        this.adminKey = adminKey;
        try
        {
            ServerSocket serverSocket = new ServerSocket(10001);
            while (true)//循环接受Socket
            {
                System.out.println("服务端开启，等待客户端建立连接。");
                socket = serverSocket.accept();
                ipInfo = socket.getInetAddress().getHostAddress();
                System.out.println(ipInfo+"  Connected! ");
                new Thread(new Task(socket)).start();//并且每次接收到Socket之后，就要新建一个线程以达到多次返回数据接受数据的目的
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        infofile = new File("D://info.key");
    }

    public class Task implements Runnable
    {
        private final Socket socket;

        public Task(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            try
            {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(ipInfo);
                String code = bufferedReader.readLine();//客户端先发送一个标志，说明是登录还是返回
                if (code.equals("0"))
                {
                    login();
                }
                else
                    regist();
                code = bufferedReader.readLine();
                System.out.println(code);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void login()
    {
        String result;
        String status;
        PrintWriter printWriter = null;
        if (isAdmin)//确定找到的用户的身份
        {
            status = "管理员";
        }
        else

            status = "一般用户";
        try
        {
            readFile(infofile);//先读文件
            account = bufferedReader.readLine();//客户端传回来的帐号密码
            passwd = bufferedReader.readLine();
            User user = new User(account, passwd);//封装对象
            if (isExists(user, false))//找到了
            {
                result = "登录成功，身份：" + status;//传回相关信息
            }
            else
            {
                result = "登录失败，请查验帐号密码！";
            }
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(result);//返回客户端
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void regist()
    {
        PrintWriter printWriter = null;
        String status = null;
        try
        {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            account = bufferedReader.readLine();//客户端传回来的帐号密码
            passwd = bufferedReader.readLine();
            String inviteCode = bufferedReader.readLine();

            User user = new User(account, passwd);
            readFile(infofile);
            if (!isExists(user, true))
            {
                user.setAdmin(inviteCode);
                if (user.isAdmin())
                {
                    status = "管理员";
                }
                else
                    status = "一般用户";

                hashSet.add(user);//没找到就添加进Set
                writeFile(infofile);
                printWriter.println("注册成功！身份：" + status);
            }
            else
            {
                printWriter.println("注册失败，用户已存在！");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void readFile(File file)
    {
        ObjectInputStream objectInputStream = null;
        PrintWriter printWriter = null;
        try
        {
            printWriter = new PrintWriter(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(new FileInputStream(file));//读取密码文件
            hashSet = (HashSet) objectInputStream.readObject();//信息是以hashSet的形式存放在文件中
        } catch (IOException e)
        {
            if (hashSet == null)
            {
                hashSet = new HashSet<>();//程序第一次运行时添加进的hashMap是null，需要新实例化一个
                writeFile(infofile);//然后再写进去
            }
        } catch (ClassNotFoundException e)
        {
            printWriter.println("数据文件异常，请检查文件！");
        }
    }

    public void writeFile(File file)
    {
        PrintWriter printWriter = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));//对象写入流
            objectOutputStream.writeObject(hashSet);//将hashSet写入文件
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e)
        {
            printWriter.println("数据文件异常，请检查文件！");
        }
    }

    public boolean isExists(User user, boolean isRegister)
    {
        String account = user.getAccount();
        String passwd = user.getPasswd();
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext())
        {
            User stu = (User) iterator.next();
            isAdmin = stu.isAdmin();
            if (stu.getAccount().equals(account))//如果找到了相同用户名
            {
                if (isRegister)//注册的话
                {
                    return true;//已经找到了
                }
                return stu.getPasswd().equals(passwd);//登陆的话还要比较密码是否相同
            }
        }
        return false;//没找到就是假
    }


    public void setAdminKey(String string)
    {
        adminKey = string;
    }

    public String getAdminKey()
    {
        return adminKey;
    }

    public static void main(String[] args)
    {
        Server server = new Server("KangYh is very handsome!");

    }
}

class User implements Serializable
{
    private String account;
    private String passwd;
    private boolean isAdmin = false;

    public User(String account, String passwd)
    {
        this.account = account;
        this.passwd = passwd;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getPasswd()
    {
        return passwd;
    }

    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public void setAdmin(String string)
    {
        if (string.equals(new Server("KangYh is very handsome!").getAdminKey()))
        {
            isAdmin = true;
        }
    }

    @Override
    public int hashCode()
    {
        return account.hashCode() + passwd.hashCode() * 3;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof User))
        {
            return false;
        }
        User user = (User) obj;
        return account.equals(user.account);
    }
}
