package app.component.collateral.feign;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient("mftcc-platform-dhcc")
public interface FunGuGuFeign {

    @RequestMapping(value = "/funGuGu/request/3101")
    public Map<String,Object> request3101(@RequestBody Map<String,String> requestMap) throws Exception;

    @RequestMapping(value ="/funGuGu/request/3102")
    public Map<String,Object> request3102(@RequestBody Map<String,String> requestMap) throws Exception;


    @RequestMapping(value ="/funGuGu/request/3103")
    public Map<String,Object> request3103(@RequestBody Map<String,String> requestMap) throws Exception;

    @RequestMapping(value ="/funGuGu/request/3104")
    public Map<String,Object> request3104(@RequestBody Map<String,String> requestMap) throws Exception;

    @RequestMapping(value ="/funGuGu/request/3105")
    public Map<String,Object> request3105(@RequestBody Map<String,String> requestMap) throws Exception;
}
