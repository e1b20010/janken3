package oit.is.z0604.kaizi.janken2.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT * from matchinfo where isActive = true")
  ArrayList<MatchInfo> selectActiveMatchInfo();

  @Select("SELECT count(*) from matchinfo where isActive = true and user2 = #{user}")
  int selectUser2MatchInfo(int user);

  @Select("SELECT count(*) from matchinfo where isActive = true and user1 = #{user}")
  int selectUser1MatchInfo(int user);

  @Select("SELECT * from matchinfo where isActive = true and user2 = #{user}")
  MatchInfo selectUserMatchInfo(int user);

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchinfo);

  @Update("UPDATE matchinfo SET isActive=false")
  void updateByIsActive(MatchInfo matchinfo);

}
