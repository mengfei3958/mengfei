package xmlTest;

import javax.xml.bind.annotation.XmlAttribute;

public class ElementXml {

    private int i;

    public ElementXml() {
    }

    public ElementXml(int i) {
        this.i = i;
    }

    @XmlAttribute(name="i")
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
