package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private final Map<String, ControllerV4> controllerMap = new HashMap<>();
    // 아래 각 URI와 인스턴스를 담기 위한 Map 객체 생성

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
        // 일단 Map 객체에 각 URI와 컨트롤러 인스턴스를 반환할 생성자 메서드를 넣어놓는다.
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        // 요청에 의한 경로가 반환된다.

        ControllerV4 controller = controllerMap.get(requestURI);
        // Map객체에서 요청에 의한 경로를 Key로 넘겨주고 반환된 Value로 컨트롤러 인스턴스를 반환.

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 반환된 인스턴스가 없다면 잘못된 요청이므로 적당히 처리

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        // 파라미터를 맵으로 만들어 변수에 저장한다.
        // process함수에 넘겨줄 파라미터 Map 객체와 model Map 객체를 생성한다.
        // 기존에 각 컨트롤러에서 만들던 모델을 FrontController에서 만들어서 넘겨준다.
        // 각 Controller에서는 넘겨준 model 객체에 그냥 put만 해서 쓰면 됨.

        String viewName = controller.process(paramMap, model);
        // 37번째 줄에서 생성된 컨트롤러 인스턴스에서 각각 정의된 함수가 실행되어 modelView를 반환한다.

        MyView myView = getMyView(viewName);

        myView.render(model, request, response);
        // 모델도 같이 넣어줘야 한다.
        // ModelView클래스의 Map객체 model에 데이터가 들어가있다.
    }

    private MyView getMyView(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
        // 이후에 폴더가 변경되더라도 위의 절대경로만 바꿔주면 된다.
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
