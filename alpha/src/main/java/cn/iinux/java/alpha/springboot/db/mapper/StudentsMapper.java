package cn.iinux.java.alpha.springboot.db.mapper;

import cn.iinux.java.alpha.springboot.db.entity.Students;
import cn.iinux.java.alpha.springboot.db.entity.StudentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudentsMapper {
    long countByExample(StudentsExample example);

    int deleteByExample(StudentsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Students row);

    int insertSelective(Students row);

    List<Students> selectByExample(StudentsExample example);

    Students selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") Students row, @Param("example") StudentsExample example);

    int updateByExample(@Param("row") Students row, @Param("example") StudentsExample example);

    int updateByPrimaryKeySelective(Students row);

    int updateByPrimaryKey(Students row);
}