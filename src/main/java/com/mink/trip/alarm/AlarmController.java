package com.mink.trip.alarm;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alarm")
public class AlarmController {
    @GetMapping("/timeline")
    public String timeline(){
        return "alarm/timeline";
    }
}
