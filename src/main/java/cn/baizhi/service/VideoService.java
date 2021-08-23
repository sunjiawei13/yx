package cn.baizhi.service;

import cn.baizhi.entity.Video;
import cn.baizhi.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    //分页查业务
    Map<String,Object> queryByPage(int page, int size);
    //添加视频业务         视频          video对象
    void add(MultipartFile file, Video video);
    //删除视频业务
    void delete(String id);
    //根据视频的上传事件降序业务
    List<VideoVo> queryByCreateDate();


}
