package org.gradle;

public class Pet {
    private String name;
    private PetType petType;

    public Pet(String name,PetType petType) {
        this.petType = petType;
        this.name = name;
    }
    public PetType getPetType() {
        return petType;
    }
    public void setPetType(PetType petType) {
        this.petType = petType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
}
