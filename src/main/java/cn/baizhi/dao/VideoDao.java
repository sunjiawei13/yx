package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    //分页查
    List<Video> queryByPage(@Param("start")int start, @Param("end") int end);
    //查总条数
    int queryCount();
    //添加视频
    void add(Video video);
    //根据id  查询出这条视频的信息
    Video queryById(String id);
    //根据id 删除视频信息
    void deleteById(String id);
}
