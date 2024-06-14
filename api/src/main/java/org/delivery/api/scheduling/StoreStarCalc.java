package org.delivery.api.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service
@Slf4j(topic = "store star calc")
public class StoreStarCalc {
    @Autowired
    private StoreRepository storeRepository;

    @Value("${schedule.use}")
    private boolean useSchedule;

//    @Scheduled(cron="${schedule.cron}")
    @Scheduled(fixedDelay = 1000*60)
    @Transactional
    public void calcStarSchedule(){
        if(useSchedule){
            log.info("scheduling******************");
            List<StoreEntity> storeEntityList = storeRepository.findAll();

            for(StoreEntity store: storeEntityList){
                var star = store.calculateStar();
                store.setStar(star);
            }
        }
    }
}
