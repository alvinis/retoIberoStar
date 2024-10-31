package com.jencys.iberostar.app.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* com.jencys.iberostar.app.presentation.controller.SpaceshipController.getSpaceshipById(..)) && args(id)")
    public void logNegativeIdForGet(Long id){
        logNegativeId("GET",id);
    }

    @Before(value = "execution(* com.jencys.iberostar.app.presentation.controller.SpaceshipController.updateSpaceship(..)) && args(id, spaceship)", argNames = "id,spaceship")
    public void logNegativeIdForUpdate(Long id, Object spaceship) {
        logNegativeId("UPDATE",id);
    }

    @Before(value = "execution(* com.jencys.iberostar.app.presentation.controller.SpaceshipController.deleteSpaceship(..)) && args(id)")
    public void logNegativeIdForDelete(Long id) {
        logNegativeId("DELETE",id);
    }

    private void logNegativeId(String nameMethod, Long id) {
        if (id < 0) {
            log.warn("id negativo detectado para el metodo: {} id: {}", nameMethod, id);
        }
    }
}
