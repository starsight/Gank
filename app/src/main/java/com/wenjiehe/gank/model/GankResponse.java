package com.wenjiehe.gank.model;

import java.util.List;
import java.util.Map;

public class GankResponse {
    public boolean error;
    public Map<String, List<GankItem>> results;
    public List<String> category;

    public boolean hasData() {
        return results != null && results.size() > 0;
    }
}
