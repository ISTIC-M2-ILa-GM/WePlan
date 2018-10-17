package fr.istic.gm.weplan.server.log;

import fr.istic.gm.weplan.server.model.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Logging Handler
 */
@AllArgsConstructor
@Aspect
@Slf4j
@Component
public class LoggingHandler {

    private HttpServletRequest request;

    String findUserId(RequestAttributes requestAttributes) {
        if (!(requestAttributes instanceof NativeWebRequest) || request.getUserPrincipal() == null) {
            return "";
        }
        if (request.getUserPrincipal() instanceof SecurityUser) {
            return String.valueOf(((SecurityUser) request.getUserPrincipal()).getId());
        }
        return "";
    }

    /**
     * Point cut for rest controller
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
        // Rest logging pointcut
    }

    /**
     * Point cut for service
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void service() {
        // Service logging pointcut
    }

    /**
     * Point cut for component
     */
    @Pointcut("within(@org.springframework.stereotype.Component *)")
    public void component() {
        // Component logging pointcut
    }

    /**
     * Point cut for all public method
     */
    @Pointcut("execution(* fr.istic.gm.weplan..*(..)) && execution(public * *(..))")
    protected void loggingPublicOperation() {
        // Logging public method pointcut
    }

    /**
     * Logging before controller call
     *
     * @param joinPoint the join point
     */
    @Before("restController() && loggingPublicOperation() && args(..)")
    public void logControllerBefore(JoinPoint joinPoint) {
        String id = findUserId(RequestContextHolder.getRequestAttributes());
        log.info("CONTROLLER REQUEST({}) {} {}: {}", id, request.getMethod(), request.getRequestURI(), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Logging before service call
     *
     * @param joinPoint the join point
     */
    @Before("service() && loggingPublicOperation() && args(..)")
    public void logServiceBefore(JoinPoint joinPoint) {
        String id = findUserId(RequestContextHolder.getRequestAttributes());
        log.info("SERVICE REQUEST({}) {} {}: {}", id, joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Logging before component call
     *
     * @param joinPoint the join point
     */
    @Before("component() && loggingPublicOperation() && args(..)")
    public void logComponentBefore(JoinPoint joinPoint) {
        String id = findUserId(RequestContextHolder.getRequestAttributes());
        log.info("COMPONENT REQUEST({}) {} {}: {}", id, joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Logging after component call
     *
     * @param joinPoint the join point
     * @param result    the return object
     */
    @AfterReturning(pointcut = "component() && loggingPublicOperation()", returning = "result")
    public void logComponentAfter(JoinPoint joinPoint, Object result) {
        String id = findUserId(RequestContextHolder.getRequestAttributes());
        log.info("COMPONENT RESPONSE({}) {} {}: {}", id, joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), result);
    }

    /**
     * Logging after service call
     *
     * @param joinPoint the join point
     * @param result    the return object
     */
    @AfterReturning(pointcut = "service() && loggingPublicOperation()", returning = "result")
    public void logServiceAfter(JoinPoint joinPoint, Object result) {
        String id = findUserId(RequestContextHolder.getRequestAttributes());
        log.info("SERVICE RESPONSE({}) {} {}: {}", id, joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), result);
    }

    /**
     * Logging after controller call
     *
     * @param joinPoint the join point
     * @param result    the return object
     */
    @AfterReturning(pointcut = "restController() && loggingPublicOperation()", returning = "result")
    public void logControllerAfter(JoinPoint joinPoint, Object result) {
        String id = findUserId(RequestContextHolder.getRequestAttributes());
        log.info("CONTROLLER RESPONSE({}) {} {}: {}", id, request.getMethod(), request.getRequestURI(), result);
    }

    /**
     * Logging after throwing an exception
     *
     * @param joinPoint the join point
     * @param exception the exception
     */
    @AfterThrowing(pointcut = "restController() && loggingPublicOperation()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String id = findUserId(RequestContextHolder.getRequestAttributes());
        log.error("ERROR({}) {} {}: {}", id, joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), exception.getMessage(), exception);
    }
}
