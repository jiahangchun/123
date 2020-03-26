package com.jiahangchun.config.service.impl;

import com.jiahangchun.config.service.TestA;
import org.springframework.stereotype.Service;

@Service
public class TestAImpl implements TestA {
    /**
     * @param code 策略
     * @return name
     */
    @Override
    public String getName(String code) {
        return null;
    }
}
