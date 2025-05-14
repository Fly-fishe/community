package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DateService;
import com.nowcoder.community.util.HostHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DateInterceptor implements HandlerInterceptor {
    @Autowired
    private DateService dateService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //统计UV
        String ip = request.getRemoteUser();
        dateService.recordUV(ip);

        //统计DAU
        User user= hostHolder.getUser();
        if (user!=null){
            dateService.recordDAU(user.getId());
        }

        return true;
    }
}
