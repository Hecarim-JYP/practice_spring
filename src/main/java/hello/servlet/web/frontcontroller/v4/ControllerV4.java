package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {

    /**
     *
     * @param paramMap 파라미터 Map 객체
     * @param model 모델 Map 객체
     * @return viewName 뷰 네임
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);

}
