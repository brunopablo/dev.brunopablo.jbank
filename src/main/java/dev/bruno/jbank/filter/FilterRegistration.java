package dev.bruno.jbank.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistration {

    private final IpFilter ipFilter;

    public FilterRegistration(IpFilter ipFilter) {
        this.ipFilter = ipFilter;
    }

    @Bean
    public FilterRegistrationBean<IpFilter> filterRegistrationBean(){

        var registrationBean = new FilterRegistrationBean<IpFilter>();

        registrationBean.setFilter(ipFilter);

        registrationBean.setOrder(0);

        return registrationBean;
    }
}