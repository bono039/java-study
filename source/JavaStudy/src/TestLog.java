import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * logger 테스트
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestLog {
    private static Logger logger = LogManager.getLogger(TestLog.class);
    
    public static void main(String[] args) {
        // 로그레벨 : TRACE > DEBUG > INFO > WARN > ERROR > FATAL
        // 보통 debug, info, error 많이 사용함
        logger.trace("### trace : debug 보다 상세하게 표시됨");
        logger.debug("### debug : 디버깅시 사용");
        logger.info("### info : 시스템에 대한 정보를 제공해야하는 경우 사용");
        logger.warn("### warn : 에러가 발생하지 않았지만 해당 정보에 대해서 알려주어야 할 경우 사용");
        logger.error("### error : 에러가 발생한 경우 사용");
        logger.fatal("### fatal : 시스템 운영이 불가능한 경우 사용");
    }

}
