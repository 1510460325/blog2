package cn.wzy.controller;

import cn.wzy.biz.User_InfoService;
import cn.wzy.entity.User_Info;
import cn.wzy.model.ResultModel;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author wzy
 * @Date 2018/4/10 15:52
 */
@Log4j
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private User_InfoService user_infoService;


    /**
     * 注册用户，0 用户已经存在 2 验证码错误
     * @param record
     * @return
     */
    @ResponseBody
    @RequestMapping("reg.do")
    public ResultModel<Integer> reg(User_Info record, String str) {
        ResultModel<Integer> result = new ResultModel<>();
        String randomString = (String) getRequest().getSession(true).getAttribute("randomString");
        //验证一次就销毁，防止重复提交
        getRequest().getSession(true).removeAttribute("randomString");
        if (randomString == null || !randomString.equals(str.toLowerCase()))
            return result.setData(Arrays.asList(2))
                    .setCode(ResultModel.SUCCESS)
                    .setOnline_num(users.size());
        return result.setData(Arrays.asList(user_infoService.reg(record)))
                .setCode(ResultModel.SUCCESS)
                .setOnline_num(users.size());
    }

    /**
     * 登录，0不存在 2 密码错误 3.验证码错误
     * @param record
     * @return
     */
    @ResponseBody
    @RequestMapping("login.do")
    public ResultModel<Integer> login(User_Info record, String str) {
        ResultModel<Integer> result = new ResultModel<>();
        String randomString = (String) getRequest().getSession(true).getAttribute("randomString");
        //验证一次就销毁，防止重复提交
        getRequest().getSession(true).removeAttribute("randomString");
        if (randomString == null || !randomString.equals(str.toLowerCase()))
            return result.setData(Arrays.asList(3))
                    .setCode(ResultModel.SUCCESS)
                    .setOnline_num(users.size());
        return result.setData(Arrays.asList(user_infoService.login(record,getRequest())))
                .setCode(ResultModel.SUCCESS)
                .setOnline_num(users.size());
    }

    /**
     * 记录每个人的访问
     */
    @ResponseBody
    @RequestMapping("log.do")
    public String userLog() throws IOException {
        String command = "java -classpath /root/AdressQueryUtil AdressQuery" + getRequest().getRemoteAddr();
        BufferedReader br;
        Process p = Runtime.getRuntime().exec(command);
        br = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        log.info("ip为:" + getRequest().getRemoteAddr() + "访问了你的网站!" + "ta的地址：" + sb.toString());
        return "SUCCESS";
    }

    /**
     * 判断是否在线，如果在就返回信息
     * @return
     */
    @ResponseBody
    @RequestMapping("checkOnline.do")
    public ResultModel<User_Info> checkOnline() {
        HttpSession session = getRequest().getSession(false);
        if (session == null)
            return new ResultModel<User_Info>()
                    .setOnline_num(users.size())
                    .setData(null)
                    .setCode(ResultModel.SUCCESS)
                    .setTotal(0);
        Integer id =(Integer) session.getAttribute("id");
        if (id == null)
            return new ResultModel<User_Info>()
                .setOnline_num(users.size())
                .setData(null)
                .setCode(ResultModel.SUCCESS)
                .setTotal(0);
        return new ResultModel<User_Info>()
                .setOnline_num(users.size())
                .setTotal(1)
                .setData(Arrays.asList(user_infoService.getUserInfo(id)))
                .setCode(ResultModel.SUCCESS);
    }

    @ResponseBody
    @RequestMapping("exit.do")
    public ResultModel<User_Info> exit() {

        getRequest().getSession().removeAttribute("id");
        return new ResultModel<User_Info>()
                    .setOnline_num(users.size())
                    .setData(null)
                    .setCode(ResultModel.SUCCESS)
                    .setTotal(0);
    }
}
