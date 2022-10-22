package oit.is.z0604.kaizi.janken2.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matches")
  ArrayList<Match> selectAllMatch();

}
