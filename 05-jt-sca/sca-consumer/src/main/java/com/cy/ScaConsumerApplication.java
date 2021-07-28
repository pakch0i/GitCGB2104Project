package com.cy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ScaConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScaConsumerApplication.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    @LoadBalanced
    public  RestTemplate loadBalancedRestTemplate(){
        return new RestTemplate();
    }

    @RestController
    public class ConsumerController{

        @Value("${spring.application.name}")
        private String appName;
        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private RestTemplate loadBalancedRestTemplate;

        /**基于此对象可以从注册中心获取服务实例*/
        @Autowired
        private LoadBalancerClient loadBalancerClient;

        /**基于RestTemplate进行远程服务调用,但是不具备负载均衡特性
         * 这种方式不需要nacos,是直接通过固定的ip和端口号进行*/
        @GetMapping("/consumer/doRestEcho1")
        public String doRestEcho01(){
            String url = "http://localhost:8081/provider/echo/"+appName;
            System.out.println("request url:"+url);
            //远程过程调用-RPC
            return restTemplate.getForObject(url, String.class);//String.class 为调用服务响应数据类型
        }

        /**基于RestTemplate进行远程服务调用,但是在调用之前基于
         * loadBalancerClient对象去从nacos注册中心基于服务器
         * 查找服务实例(可能有多个),此时会在本地按照一定算法去选择
         * 服务实例,然后进行服务调用*/
        @GetMapping("/consumer/doRestEcho2")
        public String doRestEcho02(){
            //基于服务名(nacos中服务列表中的名字)查找服务实例
            ServiceInstance choose = loadBalancerClient.choose("sca-provider");
            String ip = choose.getHost();
            int port = choose.getPort();
            //构建远程调用的URL
            //String url = "http://"+ip+port+"/provider/echo/"+appName;
            String url = String.format("http://%s:%s/provider/echo/%s",ip,port,appName);
            System.out.println("request url:"+url);
            //远程过程调用-RPC
            return restTemplate.getForObject(url, String.class);//String.class 为调用服务响应数据类型
        }

        /**
         * 整合了负载均衡功能的RestTemplate进行远程服务调用,
         * 实现的功能和方式2相同,知识代码编写上相对于第二种方式做了
         * 简化,从效率上会稍微逊色于方式2,因为底层会对请求进行拦截
         * @return
         */
        @GetMapping("/consumer/doRestEcho3")
        public String doRestEcho03(){
            String url =
                    String.format("http://%s/provider/echo/%s","sca-provider",appName);
            return loadBalancedRestTemplate.getForObject(url,String.class);
        }
    }

}
