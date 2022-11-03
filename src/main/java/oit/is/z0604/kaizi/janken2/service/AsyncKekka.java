package oit.is.z0604.kaizi.janken2.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0604.kaizi.janken2.model.MatchInfo;
import oit.is.z0604.kaizi.janken2.model.MatchInfoMapper;
import oit.is.z0604.kaizi.janken2.model.Match;
import oit.is.z0604.kaizi.janken2.model.MatchMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchInfoMapper matchInfoMapper;
  @Autowired
  MatchMapper matchMapper;

  public ArrayList<Match> syncActiveMatches() {
    return matchMapper.selectIsActiveMatch();
  }

  @Async
  public void asyncActiveMatches(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        ArrayList<Match> Amatch = this.syncActiveMatches();
        emitter.send(Amatch);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
      ArrayList<Match> Amatch = this.syncActiveMatches();
      matchMapper.updateByIsActive(Amatch);
    }
    System.out.println("asyncActiveMatches complete");
  }

}
