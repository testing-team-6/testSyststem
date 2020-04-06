package cn.cstqb.exam.testmaker.util;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/8
 * Time: 22:27
 */
public class HashUtil {
    private static final Logger logger = LoggerFactory.getLogger(HashUtil.class);
    public static final String hash(String input) {
        return Hashing.sha256().hashString(input, Charsets.UTF_8).toString();
    }

    /**
     * Gets the md5 checksum value for the input file.
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getCheckSum(File file) throws IOException, NoSuchAlgorithmException {
        HashCode md5 = Files.hash(file, Hashing.md5());
        String md5Hex = md5.toString();
        logger.debug("MD5 checksum for {} is: {}", file, md5Hex);
        return md5Hex.toUpperCase();
    }

    /**
     * Creates a md5checksum file
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static File createCheckSum(File file) throws IOException, NoSuchAlgorithmException {
        String md5Checksum = getCheckSum(file);
        String fileName = file.getName();
        String content = String.format("%s *%s",md5Checksum, fileName);
        logger.debug("HashUtil.createCheckSum: {}", content );

        //creates the checksum file
        File sumFile=new File(file.getParent(),Files.getNameWithoutExtension(fileName)+".md5");
        PrintWriter pw = new PrintWriter(sumFile);
        pw.println(content);
        pw.close();
        return sumFile;
    }

    public static Path createCheckSum(Path file) throws IOException, NoSuchAlgorithmException {
        return createCheckSum(file.toFile()).toPath();
    }

    /**
     * Generates a random string
     * @return The generated random string
     */
    public static String generateRandom() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
