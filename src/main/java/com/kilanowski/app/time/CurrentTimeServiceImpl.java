package com.kilanowski.app.time;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.Year;

@Service
public class CurrentTimeServiceImpl implements CurrentTimeService {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
