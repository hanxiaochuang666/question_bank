package cn.eblcu.questionbank.ui.config;

import cn.eblcu.questionbank.infrastructure.util.HttpReqUtil;
import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.StatusCodeEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Configuration
@Aspect
@Slf4j
public class AopConfig {

    @Value("${tokenConfig.ssoUrl}")
    private String ssoUrl;
    @Value("${tokenConfig.open}")
    private boolean open;

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointcut() {
    }

    @Around("pointcut() && (@within(cn.eblcu.questionbank.client.CheckToken) || @annotation(cn.eblcu.questionbank.client.CheckToken))")
    public Object simpleAop(final ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // 调用原有的方法
            Long befor = System.currentTimeMillis();
            Object o = joinPoint.proceed();
            Long after = System.currentTimeMillis();
            log.info("调用方法结束===================共耗时：" + (after - befor) + "毫秒");
            log.info("方法返回：return:====================" + o);
            return o;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Before("@within(cn.eblcu.questionbank.client.CheckToken) || @annotation(cn.eblcu.questionbank.client.CheckToken)")
    public void doBefore(JoinPoint joinPoint) throws Exception {

        log.info("进入方法>>>>>>>" + getFunctionName(joinPoint));
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (open){
            int userId = SupperTokenUtils.getUserByToken(request);
            if(userId==-1){
                log.info("用户信息无效，请注册或登录!");
                throw new BusinessException("-1", "用户信息无效，请注册或登录!");
            }
            checked(request, response);
        }
        String url = request.getRequestURL().toString();
        log.info("请求URL:【{}】,\n请求参数:【{}】", url, args);
        Long befor = System.currentTimeMillis();
        log.info("方法开始时间：【{}】", befor);
    }

    @After("@within(cn.eblcu.questionbank.client.CheckToken) || @annotation(cn.eblcu.questionbank.client.CheckToken)")
    public void doAfter() {
        // todo 可以做一些事情
        Long after = System.currentTimeMillis();
        log.info("方法结束时间：" + after);
    }


    private String getFunctionName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getName();
//        CheckToken annotation = signature.getMethod().getAnnotation(CheckToken.class);

    }

    /**
     * 获取目标主机的ip
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    private boolean checked(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        boolean isDoFilter = true;
        String token = request.getHeader("token");
        if (token == null) {
            //2表示没有传token
            validFail(response,-1);
        }
        try {
            HttpResponse httpResponse = HttpReqUtil.postObjectReq(ssoUrl, token);
            Map<String, Object> responsemap = HttpReqUtil.parseHttpResponse(httpResponse);
            String isPassedStr = responsemap.get("isPassed").toString();
            int i = Integer.valueOf(isPassedStr).intValue();
            /**
             * 返回0表示验证通过,-1表示不通过
             */
            if (i == -1)
                isDoFilter = false;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("请求验证token失败");
            validFail(response,3);
        }
        if (!isDoFilter) {
            validFail(response,-2);
            //抛出的异常无法被拦截处理
            /*throw new BusinessException("-2", "token验证失败！！");*/
        }
        return true;
    }

    private void validFail(HttpServletResponse httpServletResponse,int flag){
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter writer=null;
        try {
            writer = httpServletResponse.getWriter();
            //防止与一些业务数据冲突
            if(-1==flag)
                writer.append(JSON.toJSONString(BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),"请求参数token不能为空！")));
            else if(-2==flag)
                writer.append(JSON.toJSONString(BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),"token验证失败！")));
            else
                writer.append(JSON.toJSONString(BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),"sso平台故障！")));
        }catch (Exception e){
            log.info("回写数据失败");
        }finally {
            writer.close();
        }
    }
}
