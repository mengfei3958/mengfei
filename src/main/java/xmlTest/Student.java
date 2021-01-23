package xmlTest;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"className", "number", "elementXml", "hobby"})
public class Student {
    String name;  //姓名
    String sex;    //性别
    int number;     //学号
    String className;    //班级
    ElementXml elementXml;
    List<String> hobby;    //爱好

    public Student(){
    }

    public Student(String name,String sex,int number,
                   String className,List<String> hobby) {
        this.name = name;
        this.sex = sex;
        this.number = number;
        this.className = className;
        this.hobby = hobby;
    }

    public Student(String name,String sex,int number,
                   String className,List<String> hobby, ElementXml elementXml) {
        this.name = name;
        this.sex = sex;
        this.number = number;
        this.className = className;
        this.hobby = hobby;
        this.elementXml = elementXml;
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="sex")
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    @XmlElement(name="number")
//    @XmlAttribute(name="number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @XmlElement(name="className")
    public String getClassName() {
        return className;
    }

    @XmlElement(name="elementXml")
    public ElementXml getElementXml() {
        return elementXml;
    }

    public void setElementXml(ElementXml elementXml) {
        this.elementXml = elementXml;
    }

    public void setClFtpUtilassName(String className) {
        this.className = className;
    }

    @XmlElementWrapper(name="hobbys")
    @XmlElement(name = "hobby")
    public List<String> getHobby() {
        return hobby;
    }
    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

}
