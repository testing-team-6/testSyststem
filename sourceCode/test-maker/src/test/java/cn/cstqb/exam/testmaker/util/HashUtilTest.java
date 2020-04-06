package cn.cstqb.exam.testmaker.util;

import com.google.common.base.Strings;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class HashUtilTest {

    @Test
    public void testGetCheckSum() throws Exception {
        Path file = Paths.get("D:\\ie-bookmark.htm");
        String checkSum = HashUtil.getCheckSum(file.toFile());
        assertTrue(checkSum.equals("51ac6939e364b364db2ff4d190ce143c"));
    }

    @Test
    public void testCreateCheckSum() throws Exception {
        File input = new File("C:\\Users\\gaojianm\\Downloads\\Windows-.com.zerog.registry.xml");

        File md5 = HashUtil.createCheckSum(input);
        assertTrue(Files.exists(md5.toPath()) && md5.length()>0);
    }

    @Test
    public void testGenerateRandom() throws Exception {
        String text=HashUtil.generateRandom();
        System.out.println("HashUtilTest.testGenerateRandom: text: "+text);
        assertNotNull(text);
    }

    @Test
    public void testGetHash() throws Exception {
        String text = HashUtil.hash("test123");
        System.out.println(text);
    }
}
