package org.gradle;

import com.google.common.base.Objects;

public class Person {
    private String name;
    private String shortName;
    private Integer age;
    private Boolean isTall;

    public Person(String name, Integer age, Boolean isTall, String shortName) {
        this.name = name;
        this.age = age;
        this.isTall = isTall;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getIsTall() {
        return isTall;
    }

    public void setIsTall(Boolean isTall) {
        this.isTall = isTall;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Person) {
            Person that = (Person) object;
            return Objects.equal(this.name, that.name) 
                    && Objects.equal(this.age, that.age) 
                    && Objects.equal(this.isTall, that.isTall);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(name, age, isTall);
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("age", age)
                .add("isTall", isTall)
                .toString();
    }
    
    public String preferredName() {
        return Objects.firstNonNull(shortName, name);
    }
}
