import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ClientMQTT {

    public static final String HOST = "ws://link-test.bi.sensetime.com/mqtt";
    private static final String clientid = "client12344";
    private static final String TOPIC = "senselink/device/SKP-e6f99b5b31792d024172498b45eaab5f/group";


    //这个属性做具体主题的订阅
    static MqttClient client;
    private MqttConnectOptions options;
    private String userName = "yangzhenglei";
    private String passWord = "11111111";
    private int i;

    private ScheduledExecutorService scheduler;

    public ClientMQTT() {}

    /**
     * 客户端连接broker的方法
     */
    private void connect() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 设置回调，相当于一个默认的方法
            client.setCallback(new PushCallback());
            //MqttTopic topic = client.getTopic(TOPIC);
            // setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            //options.setWill(topic, "close".getBytes(), 2, true);

            client.connect(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        ClientMQTT clientMQTT = new ClientMQTT();
        //建立连接
        clientMQTT.connect();
        MqttClient client = clientMQTT.client;
        MyThread thread1 = new MyThread(client);
        MyThread thread2 = new MyThread(client);
        MyThread thread3 = new MyThread(client);
        MyThread thread4 = new MyThread(client);
        MyThread thread5 = new MyThread(client);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();


//        //建立客户端
//        ClientMQTT clientMQTT = new ClientMQTT();
//        //建立连接
//        clientMQTT.connect();
//        //要订阅的主题
//        String topic = "senselink/device/SKP-e6f99b5b31792d024172498b45eaab5f/group";
//        //绑定主题并自定义回调函数，这样调用默认使用qos为1
//        clientMQTT.client.subscribe(topic, new IMqttMessageListener(){
//            public void messageArrived(String topic, MqttMessage message){
//                try {
//                    System.out.println("接收消息主题 : " + topic);
//                    System.out.println("接收消息Qos : " + message.getQos());
//                    System.out.println("接收消息内容 : " + new String(message.getPayload()));
//                    System.out.println("这个主题进行AAA操作");
//
//
//                    final String s = new String(message.getPayload());
//                    JSONObject jsonObject = JSONObject.parseObject(s);
//                    final JSONArray data = jsonObject.getJSONArray("data");
//                    System.out.println(data);
//                    System.out.println(data.size());
//                    for (int i = 0; i < data.size(); i++) {
//                        JSONObject object = (JSONObject) data.get(i);
//                        String group_id = String.valueOf(object.get("group_id"));
//                        System.out.println("==============:" + group_id);
//                        String TOPIC = String.format("%s/%s/user", "senselink/group", group_id);
//                        ClientMQTT.client.subscribe(TOPIC, 1, new IMqttMessageListener() {
//                            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                                System.out.println("接收消息主题 : " + topic);
//                                System.out.println("接收消息Qos : " + message.getQos());
//                                System.out.println("接收消息内容 : " + new String(message.getPayload()));
//                                System.out.println("这个主题进行BBB操作");
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
//                                Date date = new Date();
//                                System.out.println(sdf.format(date));
//
//                            }
//                        });
//
//                        System.out.println(TOPIC + "*************************************");
//                    }
////                    System.out.println("++++++++++++++++++++++++++++");
////                    System.out.println(ClientMQTT.client.getTimeToWait());
////                    System.out.println(ClientMQTT.client.reconnect());
////                    System.out.println("++++++++++++++++++++++++++++");
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

//        clientMQTT.client.subscribe("senselink/group/214/user", new IMqttMessageListener(){
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                System.out.println("接收消息主题 : " + topic);
//                System.out.println("接收消息Qos : " + message.getQos());
//                System.out.println("接收消息内容 : " + new String(message.getPayload()));
//                System.out.println("这个主题进行BBB操作");
//            }
//        });
        //clientMQTT.client.subscribe("senselink/group/620/user", 1);
    }
    /**
     * 发布消息的回调类
     *
     * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
     * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
     * 在回调中，将它用来标识已经启动了该回调的哪个实例。
     * 必须在回调类中实现三个方法：
     *
     *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
     *
     *  public void connectionLost(Throwable cause)在断开连接时调用。
     *
     *  public void deliveryComplete(MqttDeliveryToken token))
     *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
     *  由 MqttClient.connect 激活此回调。
     *
     */
    public class PushCallback implements MqttCallback {


        public void connectionLost(Throwable cause) {
            // 连接丢失后，一般在这里面进行重连
            System.out.println("连接断开，可以做重连");
            try {
                ClientMQTT.client.reconnect();
            }catch (Exception e) {
                System.out.println("aaaa");
                e.printStackTrace();
            }


        }

        public void deliveryComplete(IMqttDeliveryToken token) {
            System.out.println("deliveryComplete---------" + token.isComplete());
        }

        public void messageArrived(String topic, MqttMessage message) throws Exception {
            // subscribe后得到的消息会执行到这里面
            System.out.println(++i);
        }
    }

    static class MyThread extends Thread {
        private MqttClient client;

        public MyThread(MqttClient client) {
            this.client = client;
        }

        @Override
        public void run(){
            try {
                for (int i = 0; i < 100; i++) {
                    client.subscribe(TOPIC, 1);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}