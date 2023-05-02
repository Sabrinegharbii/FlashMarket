package com.example.marketplace.contollers;

import com.example.marketplace.entities.GeoIP;
import com.example.marketplace.services.GeoIPLocationService;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class GeoIPController {

    private final GeoIPLocationService geoIPLocationService;

    public GeoIPController(GeoIPLocationService geoIPLocationService) {
        this.geoIPLocationService = geoIPLocationService;
    }
    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    @GetMapping("/geoIP/")
    public GeoIP getLocation(HttpServletRequest request) throws IOException, GeoIp2Exception {
      //  String ipAddress = request.getHeader("X-Forwarded-For");
        String ipAddress="196.224.158.62";
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return geoIPLocationService.getIpLocation(ipAddress,request);
    }

}





