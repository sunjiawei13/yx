package cn.baizhi.controller;



import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService vs;
    //日志
    private Logger log= LoggerFactory.getLogger(VideoController.class);

    @RequestMapping("queryByPage")
    public Map<String, Object> queryById(int page){
        int size=2;
        return vs.queryByPage(page, size);

    }

    @RequestMapping("delete")
    public void delete(String id){
        log.debug("id:"+id);
        vs.delete(id);
    }

    /*
    formData.append("video", this.$refs.video.files[0]);
      formData.append("title", this.title);
      formData.append("brief", this.brief);
      formData.append("id",this.value2);
     */
    @RequestMapping("/add")
    public void add(MultipartFile video,String title,String brief,String id){
        log.debug("执行了");
        log.debug("标题"+title);
        log.debug("描述"+brief);
        log.debug("二级类别id："+id);
        System.out.println(video.getOriginalFilename());


        Video video1=new Video(null,title,brief,null,null,null,new Category(id,null,null,null),null,null);
        vs.add(video,video1 );




    }

}
