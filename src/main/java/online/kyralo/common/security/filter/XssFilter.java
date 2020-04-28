package online.kyralo.common.security.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-01-22
 * \* Time: 下午1:27
 * \* Description:xss过滤器
 * \
 */
@WebFilter(urlPatterns = "/**", filterName = "xssFilter")
public class XssFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ModifyParametersWrapper wrapper = new ModifyParametersWrapper(httpServletRequest);
        filterChain.doFilter(wrapper, httpServletResponse);
    }

    /**
     * 继承HttpServletRequestWrapper，创建装饰类，以达到修改HttpServletRequest参数的目的
     */
    private class ModifyParametersWrapper extends HttpServletRequestWrapper {

        private Map<String, String[]> requestParams;

        ModifyParametersWrapper(HttpServletRequest request) {
            super(request);
        }

        /**
         * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
         *
         * @param name 指定参数名
         * @return 指定参数名的值
         */
        @Override
        public String getParameter(String name) {
            String parameter = null;
            String[] vals = getParameterMap().get(name);

            if (vals != null && vals.length > 0) {
                parameter = vals[0];
            }

            return parameter;
        }

        /**
         * 获取指定参数名的所有值的数组
         */
        @Override
        public String[] getParameterValues(String name) {
            return getParameterMap().get(name);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            if (requestParams == null) {
                requestParams = new HashMap<>();
                Map<String, String[]> originalQueryString = super.getParameterMap();
                if (originalQueryString != null) {
                    for (Map.Entry<String, String[]> entry : originalQueryString.entrySet()) {
                        //对参数名进行过滤
                        String key = cleanXSS(entry.getKey());
                        //对每个传参进行过滤
                        String[] rawValues = entry.getValue();
                        String[] filteredValues = new String[rawValues.length];
                        for (int i = 0; i < rawValues.length; i++) {
                            //具体的过滤规则
                            filteredValues[i] = cleanXSS((rawValues[i]));
                        }
                        requestParams.put(key, filteredValues);
                    }
                }
            }
            return requestParams;
        }
    }

    //具体的过滤规则

    /**
     * 标签部分转译
     *
     * @param value 传入的值
     * @return 屏蔽掉xss攻击和sql注入等危险字符的值
     */
    private static String cleanXSS(String value) {
        //屏蔽掉xss攻击和sql注入等危险字符
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("\"", "&#34;");


        value = value.replaceAll("\\\\", "");
        value = value.replaceAll("\\\\/", "");

        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("e-xpression\\\\((.*?)\\\\)\"", "");

        value = value.replaceAll("[\"'][\\s]*javascript:(.*)[\"']", "\"\"");
        value = value.replaceAll("[\"'][\\s]*vbscript:(.*)[\"']", "\"\"");
        value = value.replaceAll("[\"'][\\s]*onload:(.*)[\"']", "\"\"");
        return value;
    }
}
