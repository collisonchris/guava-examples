package org.gradle;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

public class Person implements Comparable<Person> {
    private String name;
    private String shortName;
    private Integer age;
    private Boolean isTall;

    public Person(String name, Integer age, Boolean isTall, String shortName) {
        this.name = name;
        this.age = age;
        this.isTall = isTall;
        this.shortName=shortName;
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
        if (object instanceof Person) {
            Person that = (Person) object;
            return Objects.equal(this.name, that.name)
                    && Objects.equal(this.age, that.age)
                    && Objects.equal(this.shortName, that.shortName)
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
                .add("shortName", shortName)
                .toString();
    }

    public String preferredName() {
        return Objects.firstNonNull(shortName, name);
    }

    //normal null checking
    public boolean oldCanDrink() {
        if(age == null) {
            throw new IllegalArgumentException("Can't have a null age and drink!");
        }
        return age >=21;
    }
    
    //slightly better null checking!
    public boolean canDrink() {
        Preconditions.checkState(age != null, "Can't have a null age and drink!");
        return age >= 21;
    }

    //Comparison requirements: use age as primary, use isTall as secondary criteria
    
    public int oldCompareTo(Person that) {
        if(this.age > that.age || (this.age == that.age && this.isTall && !that.isTall)) {
            return 1;
        } else if( this.age < that.age || (this.age == that.age && !this.isTall && that.isTall)) {
            return -1;
        }
        return 0;

    }
    
    public int compareTo(Person that) {
        return ComparisonChain.start()
                .compare(this.age, that.age)
                .compare(this.isTall, that.isTall)
                .result();
    }
}
