package okhttp;

import okhttp3.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Test {

    static OkHttpClient client = new OkHttpClient();
    static byte[] bytes;

    private final static String BOUNDARY = "----WebKitFormBoundarygrBcuHVTeNQcBtqn";

    public static final MediaType JSON = MediaType.parse("multipart/form-data; boundary=" + BOUNDARY +"; charset=utf-8");

    static String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }
    public String saveImage(byte[] avatar) throws IOException{
        if (avatar == null) {
            return "";
        }
        String body = "--" + BOUNDARY + "\r\n"
                + "Content-Disposition: form-data; name=\"type\"\r\n\r\n" + 1 + "\r\n"
                + "--" + BOUNDARY + "\r\n" + "Content-Disposition: form-data; name=\"image"
                + "\"; filename=\"" + "123.jpg" + "\"\r\nContent-Type: image/jpeg\r\n\r\n"
                + Arrays.toString(avatar) + "\r\n"
                + "--" + BOUNDARY + "--" + "\r\n";
        // 上传图片
        String requestUrl = "https://link-test.bi.sensetime.com/v1/image";
        return post(requestUrl, body);
    }
    public static byte[] fileToByte(File img) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "jpg", baos);
            bytes = baos.toByteArray();
            //System.err.println(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }

    public static void main(String[] args) throws Exception {
        Test example = new Test();
        File img = new File("F:\\库博.jpg");
        byte[] avatar = fileToByte(img);
        System.out.println(Arrays.toString(avatar));
//        String json = example.bowlingJson("Jesse", "Jake");
//        String response = example.post("https://link-test.bi.sensetime.com/v1/image", json);
        String response = example.saveImage(avatar);
        System.out.println(response);
    }

}
