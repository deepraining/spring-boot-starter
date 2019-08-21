package senntyou.sbs.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.WorkerNode;
import senntyou.sbs.mbg.model.WorkerNodeExample;

public interface WorkerNodeMapper {
    long countByExample(WorkerNodeExample example);

    int deleteByExample(WorkerNodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WorkerNode record);

    int insertSelective(WorkerNode record);

    List<WorkerNode> selectByExample(WorkerNodeExample example);

    WorkerNode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WorkerNode record, @Param("example") WorkerNodeExample example);

    int updateByExample(@Param("record") WorkerNode record, @Param("example") WorkerNodeExample example);

    int updateByPrimaryKeySelective(WorkerNode record);

    int updateByPrimaryKey(WorkerNode record);
}