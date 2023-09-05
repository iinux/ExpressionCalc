package cn.iinux.java.alpha;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;

public class ThumbnailTest {
    public static void main(String[] args) throws IOException {
        String pathname = "/home/qzhang/Downloads/media/wallhaven";
        File file = new File(pathname);
        File[] files = file.listFiles();
        assert files != null;
        Thumbnails.of(files)
                .size(640, 480)
                .outputFormat("jpg")
                .toFiles(Rename.PREFIX_DOT_THUMBNAIL);
    }
}
