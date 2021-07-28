package sso.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;
import sso.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//令牌拦截器,拦截客户端向服务端请求时传递的令牌
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        //1.从请求中获取token对象(取决于传递token的方式)
        String token = request.getHeader("token");
        //2.验证token是否存在
        if (token==null || "".equals(token)){
            throw new RuntimeException("请先登录");
        }
        //3.验证token是否过期
        if (JwtUtils.isTokenExpired(token)){
            throw new RuntimeException("登录超时,请先登录");
        }

        //4.解析token中的认证和权限信息(一般存储在jwt格式的负载部分)
        Claims claims = JwtUtils.getClaimsFromToken(token);
        List<String> list =
                (List<String>) claims.get("anthorities");//这个名字应该与创建token时,指定的参数一样
        //5.封装和存储认证和权限信息
        //5.1构建UserDetails对象
        UserDetails userDetails = new User((String) claims.get("username"),"",
                AuthorityUtils.createAuthorityList(list.toArray(new String[]{})));
        //5.2构建security交互对象
        PreAuthenticatedAuthenticationToken autheToken =
                new PreAuthenticatedAuthenticationToken(
                        userDetails,//用户身份
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
        //5.3将权限交互对象与当前请求进行绑定
        autheToken.setDetails(new WebAuthenticationDetails(request));
        //5.4将认证后的token存储到Security上下文(会话对象)
        SecurityContextHolder.getContext().setAuthentication(autheToken);
        return true;
    }
}
