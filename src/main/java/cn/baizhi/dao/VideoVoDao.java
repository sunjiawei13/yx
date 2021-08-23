package cn.baizhi.dao;

import cn.baizhi.vo.VideoVo;

import java.util.List;

public interface VideoVoDao {

    //查所有视频
    List<VideoVo> queryAllVideo();
}
