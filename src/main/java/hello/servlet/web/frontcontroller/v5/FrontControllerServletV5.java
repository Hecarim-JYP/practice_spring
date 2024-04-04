package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    // 어떤 Controller가 와도 처리가 가능해야 하기 때문에 Object 타입으로 Value를 받는다.
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
    // 여러개의 adapter 중 하나를 찾아야 하기 때문에 List 객체에 adapter들을 담아놓는다.

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();

    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        // 일단 Map 객체에 각 URI와 컨트롤러 인스턴스를 반환할 생성자 메서드를 넣어놓는다.
        // 최상위 객체인 Object로 데이터를 받기 때문에 어떤 객체가 들어와도 상관없다.
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        // List에 사용하고 싶은 컨트롤러 어댑터를 담아놓는다.
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object handler = getHandler(request);
        // Map객체에서 요청에 의한 경로를 Key로 넘겨주고 반환된 Value로 컨트롤러 인스턴스를 반환.

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 반환된 인스턴스가 없다면 잘못된 요청이므로 적당히 처리

        MyHandlerAdapter adapter = getHandleAdapter(handler);

        ModelView modelView = adapter.handle(request, response, handler);

        String viewName = modelView.getViewName();
        MyView myView = getMyView(viewName);

        myView.render(modelView.getModel(), request, response);
        // getModel() 로 모델도 같이 넣어줘야 한다.
        // ModelView클래스의 Map객체 model에 데이터가 들어가있다.
    }

    private MyHandlerAdapter getHandleAdapter(Object handler) {
        //MyHandlerAdapter a;
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        // adapter List에서 지원 가능한 adapter를 찾아서 리턴해준다
        throw new IllegalArgumentException("지원 가능한 handler adapter를 찾을 수 없습니다. \n 요청된 handler는 [ " + handler + " ]");
        // adapter를 찾지 못했을 경우에는 예외처리해준다. 함수로 넘겨받은 파라미터가 잘못되었기 때문에 IllegalArgumentException 를 터뜨림.
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        // 요청에 의한 경로가 requestURI 에 문자열로 반환된다.
        return handlerMappingMap.get(requestURI);
    }

    private MyView getMyView(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
        // 이후에 폴더가 변경되더라도 위의 절대경로만 바꿔주면 된다.
    }
}
