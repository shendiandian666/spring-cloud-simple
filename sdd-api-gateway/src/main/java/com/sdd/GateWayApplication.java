package com.sdd;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 *
 */
@SpringCloudApplication
@EnableZuulProxy
public class GateWayApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(GateWayApplication.class, args);
    }
}
