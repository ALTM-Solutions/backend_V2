package com.ressourcesrelationnelles.config;

import com.ressourcesrelationnelles.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {


    @Autowired
    private SchedulerService schedulerService;

    @Scheduled(cron = SecurityConstants.CRON_EXPRESSION)
    public void purgeExpiredValidationUtilisateur(){
        schedulerService.deleteExpiredValidationRepository();
    }

}
