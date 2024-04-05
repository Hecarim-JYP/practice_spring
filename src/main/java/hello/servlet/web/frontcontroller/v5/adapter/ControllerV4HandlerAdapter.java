package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV4 controller = (ControllerV4) handler;

        Map<String, String> paramMap = createParamMap(request);
        HashMap<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);
        // process 함수가 실행되면 위에 생성된 model 객체게 데이터를 다 담아놓는다.

        ModelView modelView = new ModelView(viewName);
        modelView.setModel(model);
        // 중요 코드 !
        // 데이터가 다 담겨진 model 객체를 setModel()로 modelView 객체에 넣는다.

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
