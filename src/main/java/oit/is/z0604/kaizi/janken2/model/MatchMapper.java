package oit.is.z0604.kaizi.janken2.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matches")
  ArrayList<Match> selectAllMatch();

  @Select("SELECT * from matches where isActive = true")
  ArrayList<Match> selectIsActiveMatch();

  @Select("SELECT count(*) from matches where isActive = true")
  int selectCountMatchInfo();

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Update("UPDATE matches SET isActive=false")
  void updateByIsActive(ArrayList<Match> match);

}
