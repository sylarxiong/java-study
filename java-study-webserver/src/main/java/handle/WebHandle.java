package main.java.handle;

import main.java.entity.MappingEntity;
import main.java.entity.ServletEntity;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebHandle extends DefaultHandler {
    private ServletEntity servletEntity;
    private List<ServletEntity> servletEntities;
    private MappingEntity mappingEntity;
    private List<MappingEntity> mappingEntities;
    private Set<String> patterns;
    private String tag;
    private Boolean isMapping;

    @Override
    public void startDocument() throws SAXException {
        servletEntities = new ArrayList<>();
        mappingEntities = new ArrayList<>();
        //System.out.println("------解析文档开始------");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName != null) {
            tag = qName;
        }
       // System.out.println("解析"+qName+"开始");
        if ("servlet".equals(qName)) {
            servletEntity = new ServletEntity();
            isMapping = false;
        }
        if ("servlet-mapping".equals(qName)) {
            mappingEntity = new MappingEntity();
            patterns = new HashSet<>();
            isMapping = true;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();
        if(isMapping != null){
            if(isMapping){
                if("servlet-name".equals(tag)){
                    mappingEntity.setServletName(contents);
                }else if("url-pattern".equals(tag)){
                    patterns.add(contents);
                }
            }else{
                if("servlet-name".equals(tag)){
                    servletEntity.setServletName(contents);
                }else if("servlet-class".equals(tag)){
                    servletEntity.setServletClass(contents);
                }
            }
        }

        tag = null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //System.out.println("解析"+qName+"结束");
        if("url-pattern".equals(qName)){
            mappingEntity.setPatterns(patterns);
        }
        if ("servlet".equals(qName)) {
            servletEntities.add(servletEntity);
        }
        if ("servlet-mapping".equals(qName)) {
            mappingEntities.add(mappingEntity);
        }


    }

    @Override
    public void endDocument() throws SAXException {
        //System.out.println("------解析文档结束------");
    }

    public List<ServletEntity> getServletEntities() {
        return servletEntities;
    }

    public List<MappingEntity> getMappingEntities() {
        return mappingEntities;
    }
}
