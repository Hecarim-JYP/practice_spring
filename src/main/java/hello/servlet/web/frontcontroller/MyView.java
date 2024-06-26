package hello.servlet.web.frontcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class MyView {

    private final String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;

    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);
        /*
            JSP는 request.getAttribute()로 데이터를 조회하기 때문에
            모델의 데이터를 꺼내서 request.setAttribute()로 담아둔다.
        */
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach(request::setAttribute);
        /*
            model.forEach(request::setAttribute);
            model.forEach((key, value) -> request.setAttribute(key, value));
            위 2함수는 동일하게 작용한다.
        */

        /*
            JSP는 request.getAttribute()로 데이터를 조회하기 때문에
            모델의 데이터를 꺼내서 request.setAttribute()로 담아둔다.
        */
    }
}
