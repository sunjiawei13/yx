package cn.baizhi;

import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.vo.VideoVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class YxSjwApplicationTests {

    @Autowired
    private VideoVoDao vvd;
    //private VideoService vs;
    //private VideoDao vd;

    @Test
    void contextLoads() {
        List<VideoVo> videoVos = vvd.queryAllVideo();
        for (VideoVo videoVo : videoVos) {
            System.out.println(videoVo);
        }


//        vs.delete("6faf6936-34be-4ad9-b0b3-4c50118e71f4");

 //       AliYun.deleteFile("video/16292121628582.mp4");

//        System.out.println(video);
//        String coverpath = video.getCoverpath();
//        System.out.println("============");
//        System.out.println(coverpath);
//        System.out.println("============");
//        String[] split = coverpath.split("//");
//        System.out.println("============");
//        System.out.println(split);



//        List<Video> videos = vd.queryByPage(0, 1);
//        for (Video video : videos) {
//            System.out.println(video);
//        }

    }
//    @Autowired
//    private AdminDao adminDao;
//    @Test
//    void contextLoads() {
//        System.out.println("123");
//        Admin admin = adminDao.queryByUsername("sjw");
//        System.out.println(admin);
//
//    }

}
