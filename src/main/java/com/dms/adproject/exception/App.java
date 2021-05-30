package com.dms.adproject.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class App extends Throwable {

    private final String message;

}
