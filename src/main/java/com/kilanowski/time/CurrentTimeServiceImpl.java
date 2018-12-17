package com.kilanowski.time;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CurrentTimeServiceImpl implements CurrentTimeService {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
