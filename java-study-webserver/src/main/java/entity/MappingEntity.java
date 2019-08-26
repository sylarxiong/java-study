package main.java.entity;

import java.util.HashSet;
import java.util.Set;

public class MappingEntity {
    private String servletName;

    private Set<String> patterns;

    public MappingEntity() {
         patterns = new HashSet<>();
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(Set<String> patterns) {
        this.patterns = patterns;
    }

    @Override
    public String toString() {
        return "MappingEntity{" +
                "servletName='" + servletName + '\'' +
                ", patterns=" + patterns +
                '}';
    }
}
