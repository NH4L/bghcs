package cn.aysst.bghcs.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * @author lcy
 * @version 2019-12-10
 * 生成目录工具类
 */
public class FileGenertor {

    public static String generateFilename(MultipartFile file) {
        Date dt = new Date();
        Long time = dt.getTime();

        String filename = time.toString().substring(time.toString().length() - 8);
        filename = filename.concat(".");
        filename = filename.concat(file.getOriginalFilename()
                .substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1));
        return filename;
    }

    public static String genericPath(String filename, String storeDirectory) {
        int hashCode = filename.hashCode();
        int dir1 = hashCode&0xf;
        int dir2 = (hashCode&0xf0)>>4;

        String dir = "/"+dir1+"/"+dir2+"/";

        File file = new File(storeDirectory, dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir1+"/"+dir2+"/";
    }
}
