package com.technozor.javaz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by slim on 5/8/14.
 */
@RunWith(JUnit4.class)
public class LensTest {
    public class Person {

        private final String firstName;
        private final String lastName;
        private final Address address;

        public Person(String firstName, String lastName, Address address) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Address getAddress() {
            return address;
        }
    }

    public class Address {

        private final String street;
        private final String city;
        private final String state;
        private final Integer zipCode;

        public Address(String street, String city, String state, Integer zipCode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }

        public String getStreet() {
            return street;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public Integer getZipCode() {
            return zipCode;
        }

    }
    Lens<Person, Address>  personAddressLens = Lens.lens(Person::getAddress, (p,a) ->  new Person(p.getFirstName(), p.getLastName(), a));
    Lens<Address, Integer> addressZipCodeLens = Lens.lens(Address::getZipCode, (a,z) -> new Address(a.getStreet(), a.getCity(), a.getState(), z));



    @Test
    public void testLenses() {
        Person person = new Person("Jack", "Smith", new Address("Default", "Default", "Default", 0));

        Lens<Person, Integer> personZipCodeLens = personAddressLens.round(addressZipCodeLens);

        Person p1 = personZipCodeLens.setter.apply(person, personZipCodeLens.getter.apply(person) + 1);
        assertEquals(p1.address.zipCode - 1, 0);
    }


    @Test
    public void testMod() {
        Person person = new Person("Jack", "Smith", new Address("Default", "Default", "Default", 0));

        Lens<Person, Integer> personZipCodeLens = personAddressLens.round(addressZipCodeLens);

        Person p1 = personZipCodeLens.mod(person, a -> a + 1);
        assertEquals(p1.address.zipCode - 1, 0);
    }

}
