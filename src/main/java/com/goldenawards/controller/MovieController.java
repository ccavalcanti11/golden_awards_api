package com.goldenawards.controller;

import com.goldenawards.data.dto.RangeDTO;
import com.goldenawards.services.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private RangeService rangeService;

    @GetMapping("/range")
    public RangeDTO getRange() {
        return rangeService.getMinMaxRange();
    }
}
