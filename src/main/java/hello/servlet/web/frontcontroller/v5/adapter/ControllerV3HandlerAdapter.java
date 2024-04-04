package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
        // 파라미터로 넘겨받은 핸들러가 V3 타이빙 맞는지 확인하고 결과를 반환한다.
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;
        // 파라미터 타입이 Object 타입이기 때문에 ControllerV3 타입으로 형변환이 필요하다.
        // supports 함수로 검증이 된 상태기 때문에 캐스팅을 해도 된다.

        Map<String, String> paramMap = createParamMap(request);
        ModelView modelView = controller.process(paramMap);

        return modelView;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {

        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
        /*
            각 파라미터들을 Key와 Value로 맵에 담아서 맵으로 만들어서 데이터를 주고 받는다.
            Ex) Key : age / Value : 32
                Key : username  / value : Park
            request 객체에서 파라미터를 모두 조회하여 Map 변수 paramMap에 넣는다.
        */

    }

}
