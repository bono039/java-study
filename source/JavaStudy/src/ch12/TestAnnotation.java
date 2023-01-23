package ch12;

import ch12.LottoAnnotation.TestType;

/**
 * Annotation 학습
 *  
 * @author : pej 
 * @date : 2023.01.23
 */
@LottoAnnotation(testArr = { "class" })
public class TestAnnotation {
    @LottoAnnotation(author="test1", order = 2, testType = TestType.MORNING, testArr = { "filed" })
    int num = 0;
    
    @LottoAnnotation(testArr = { "method" })
    public String test() {
        return "test";
    }
}
