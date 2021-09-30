package com.lsm.big.data.controller;

import com.lsm.big.data.service.IBigDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/big/data")
public class BigDataController {

    @Autowired
    private IBigDataService bigDataService;

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read() throws Exception {
        bigDataService.read();
        return "成功";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete() {
        bigDataService.delete();
        return "成功";
    }
}
