package main.java.handle;

import main.java.entity.MappingEntity;
import main.java.entity.ServletEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {
    private List<ServletEntity> servletEntities;
    private List<MappingEntity> mappingEntities;
    private Map<String,String> servletMap = new HashMap<>();
    private Map<String,String> mappingMap = new HashMap<>();

    public WebContext(List<ServletEntity> servletEntities, List<MappingEntity> mappingEntities) {
        this.servletEntities = servletEntities;
        this.mappingEntities = mappingEntities;
        this.servletEntities.stream().forEach(item->servletMap.put(item.getServletName(),item.getServletClass()));
        this.mappingEntities.stream().forEach(item->item.getPatterns().forEach(item2->mappingMap.put(item2,item.getServletName())));
    }
    public String getClz(String pattern){
        String name = mappingMap.get(pattern);
        return servletMap.get(name);
    }
}
