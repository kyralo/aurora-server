package online.kyralo.common.security.filter;

import io.jsonwebtoken.Claims;
import online.kyralo.common.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static online.kyralo.common.constants.SecurityConstants.HEADER_STRING;
import static online.kyralo.common.constants.SecurityConstants.TOKEN_PREFIX;


/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2018-12-11
 * \* Time: 下午6:56
 * \* Description:jwt验证过滤器 进行token验证及用户授权
 * \
 * @author 王宸
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        System.out.println(header);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            Claims claims = JwtUtil.parseToken(token);
            if (claims != null) {
                //用户的唯一标识符 管理员为用户名 普通用户为ID
                String userIdentifier = claims.getAudience();
                String role = claims.getSubject();
                return new UsernamePasswordAuthenticationToken(userIdentifier, null,
                        Collections.singletonList(new SimpleGrantedAuthority(role)));
            }
            return null;
        }
        return null;
    }
}
