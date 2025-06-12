package main;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZlomekTest {

    Zlomek z1,z2;

    @BeforeEach
    void setUp() {
        z1 = new Zlomek(2,5);
        z2 = new Zlomek(1,3);
    }

    @Test
    void secti() {
        Zlomek z = new Zlomek(11,15);

        assertEquals(z,z1.secti(z2));
    }

    @Test
    void odecti() {
        Zlomek z = new Zlomek(1,15);

        assertEquals(z,z1.odecti(z2));
    }

    @Test
    void nasob() {
        Zlomek z = new Zlomek(2,15);

        assertEquals(z,z1.nasob(z2));
    }

    @Test
    void vydel() {
        Zlomek z = new Zlomek(6,5);

        assertEquals(z,z1.vydel(z2));
    }

    @Test
    void vydel2() {
        assertEquals(new Zlomek(1), z1.vydel(z1),"Zlomek vydeleny sam sebou nedava hodnotu 1.");
    }
    @Test
    void vydel3() {
        assertThrows(ArithmeticException.class, () -> {z1.vydel(new Zlomek(0));});
    }

        @Test
    void testEquals() {
        assertNotEquals(z1,z2);
        assertNotEquals(z2,z1);
        assertEquals(z1,z1);
        assertEquals(z2,z2);
        assertEquals(new Zlomek(4,10,false), z1);
        assertEquals(new Zlomek(3,9,false), z2);
    }

    @Test
    void compareTo() {
        assertEquals(0, z1.compareTo(z1));
        assertEquals(0, z2.compareTo(z2));
        assertEquals(+1, z1.compareTo(z2));
        assertEquals(-1, z2.compareTo(z1));

    }

    @Test
    void zkrat() {
        Zlomek z3 = new Zlomek(4,10,false);
        Zlomek z4 = new Zlomek(2,6,false);
        z3.zkrat();
        z4.zkrat();
        assert(z1.getCitatel() == z3.getCitatel() && z1.getJmenovatel() == z3.getJmenovatel());
        assert(z2.getCitatel() == z4.getCitatel() && z2.getJmenovatel() == z4.getJmenovatel());
    }

    @AfterEach
    void tearDown() {
    }
}