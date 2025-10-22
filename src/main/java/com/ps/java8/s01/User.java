package com.ps.java8.s01;

public final class User {
    private final String name;
    private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User other = (User) o;
        if (age != other.age) return false;
        return name != null ? name.equals(other.name) : other.name == null;
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }

    @Override
    public String toString() {
        return "User(name=" + name + ", age=" + age + ")";
    }
}