package org.delivery.api.scheduling;

import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreStarCalc {
    @Autowired
    private StoreRepository storeRepository;

    @Value("${schedule.use}")
    private boolean useSchedule;

    @Scheduled(cron="${schedule.cron}")
    @Transactional
    public void calcStarSchedule(){
        if(useSchedule){
            List<StoreEntity> storeEntityList = storeRepository.findAll();

            for(StoreEntity store: storeEntityList){
                var star = store.calculateStar();
                store.setStar(star);
            }
        }
    }
}
