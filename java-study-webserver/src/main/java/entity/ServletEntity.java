package main.java.entity;
public class ServletEntity {
    private String servletName;
    private String servletClass;

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    @Override
    public String toString() {
        return "ServletEntity{" +
                "servletName='" + servletName + '\'' +
                ", servletClass='" + servletClass + '\'' +
                '}';
    }
}
