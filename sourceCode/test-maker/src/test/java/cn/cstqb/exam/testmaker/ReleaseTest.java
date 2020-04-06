package cn.cstqb.exam.testmaker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReleaseTest {
    private Release release;
    @Before
    public void setUp() throws Exception {
        release = new Release();
    }

    @Test
    public void testGeProductName() throws Exception {
        System.out.println(release);
    }

    @Test
    public void testGetFullVersion() throws Exception {

    }
}
