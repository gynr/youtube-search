package com.gynr.youtubesearch.service;

import java.io.IOException;

public interface HttpService {

    public void fetch(String query) throws IOException;
}
