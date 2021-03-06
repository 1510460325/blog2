package cn.wzy.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wzy
 * @Date 2018/4/10 13:49
 */
public abstract class BaseController {
    protected static final ThreadLocal<HttpServletRequest> requests = new ThreadLocal();
    protected static final ThreadLocal<HttpServletResponse> responses = new ThreadLocal();

    public static final Map<String, Long> users = new HashMap<>();

    public static void refresh() {
        synchronized (users) {
            long now = System.currentTimeMillis();
            for (String key : users.keySet()) {
                if (now - users.get(key) >= 30000l)
                    users.remove(key);
            }
        }
    }
    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.requests.set(request);
        this.responses.set(response);
    }

    protected HttpServletRequest getRequest() {
        return requests.get();
    }

    protected HttpServletResponse getResponse() {
        return responses.get();
    }

    protected String queryAdress() throws IOException {
        return "";
//        String command = "java -classpath /root/AdressQueryUtil AdressQuery " + getRequest().getRemoteAddr();
//        BufferedReader br;
//        Process p = Runtime.getRuntime().exec(command);
//        br = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));
//        String line;
//        StringBuilder sb = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        return sb.toString();
    }
}
