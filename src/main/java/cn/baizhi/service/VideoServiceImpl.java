package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import cn.baizhi.util.AliYun;
import cn.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.security.action.PutAllAction;

import java.util.*;


@Service
@Transactional
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoDao vd;


    //分页查业务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String,Object> queryByPage(int page, int size) {
        // 1   0   2          2   2    2
        Map<String,Object> map=new HashMap<>();

        int start=(page-1)*size;

        //需要在页面上显示的数据
        List<Video> videos=vd.queryByPage(start,size);
        map.put("data",videos );
        //页面还需要现实当前页
        map.put("page",page );
        //总条数
        int i=vd.queryCount();
        //页面还需要现实总页
        int pageNum=i%size==0?i/size:i/size+1;
        map.put("pageNum",pageNum);
        return map;
    }
        //添加视频业务
    @DeleteCache
    @Override
    public void add(MultipartFile file, Video video) {
        /*
           1需要将视频上传到阿里云
           2将视频截图第一针   保存有时间限制
           3将视频第一针 保存到阿里云
           4数据入库
         */
        //1
        long time = new Date().getTime();
        String fileName=time+file.getOriginalFilename();
        AliYun.uploadByBytes(file,"video/"+fileName );
        //2
        String s = AliYun.jqvideo("video/" + fileName);
        System.out.println(s);
        //3
        int i = fileName.lastIndexOf('.');
        String substring = fileName.substring(0, i);
        AliYun.URLupload(s,substring+".jpg");
        //4
        video.setId(UUID.randomUUID().toString());
        video.setCoverpath("http://20210816class.oss-cn-beijing.aliyuncs.com/video/"+substring+".jpg");//封面路径
        video.setCreatedate(new Date());
        video.setVideopath("http://20210816class.oss-cn-beijing.aliyuncs.com/video/"+fileName);
        vd.add(video);

    }
    //删除视频业务
    @DeleteCache
    @Override
    public void delete(String id) {
        /*
        前提  根基id查出这条视频的信息(视频的路径   视频的封面)
        1删除视频
        2删除封面
        3删除表中的信息
         */

        Video video= vd.queryById(id);

        System.out.println(video);

        //删封面
        //https://20210816class.oss-cn-beijing.aliyuncs.com/video/2.jpg
        String coverpath = video.getCoverpath();//视频封面的路径
        //    https:   20210816class.oss-cn-beijing.aliyuncs.com     video   2.jpg
        String[] split = coverpath.split("/");
        System.out.println("video/" + split[split.length - 1]);
        System.out.println("============");
        AliYun.deleteFile("video/" + split[split.length - 1]);
        //删视频
        //https://20210816class.oss-cn-beijing.aliyuncs.com/video/2.jpg
        String videopath = video.getVideopath();//视频的路径
        String[] split1 = videopath.split("/");
        System.out.println("video/" + split1[split.length - 1]);
        System.out.println("===========");
        AliYun.deleteFile("video/" + split1[split.length - 1]);

        vd.deleteById(id);
//       System.out.println(split.length);
//        System.out.println(split1.length);
    }

    @Autowired
    private VideoVoDao vvd;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoVo> queryByCreateDate() {
        return  vvd.queryAllVideo();

    }

}
