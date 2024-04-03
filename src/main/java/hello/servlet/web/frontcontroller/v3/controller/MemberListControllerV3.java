package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        List<Member> members = memberRepository.findAll();

        ModelView modelView = new ModelView("members");
        modelView.getModel().put("members", members);
        /*
            Map 객체 model 에 위처럼 members를 put 하는 이유는 jsp 페이지에서 조회할 때 쓰려고 만들어놓는거다.
            Ex)
                <c:forEach var="item" items="${members}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.username}</td>
                    <td>${item.age}</td>
                </tr>
                </c:forEach>
        */
        return modelView;
    }
}
