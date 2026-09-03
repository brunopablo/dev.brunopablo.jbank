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

        var filterRegistrationBean = new FilterRegistrationBean<IpFilter>();

        filterRegistrationBean.setFilter(ipFilter);

        filterRegistrationBean.setOrder(0);

        return filterRegistrationBean;
    }
}