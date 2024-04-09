package hello.servlet.web.springmvc.v1;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberSaveControllerV1 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members/save")
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);

        memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member);
        /*
            modelView.getModel().put("member", member);
            기존에는 위의 코드로 모델을 get해서 데이터를 입력했지만,
            ModelAndView 타입으로 addObject를 사용하면 된다.
         */
        /*
            Map 객체 model 에 위처럼 memeber를 put 하는 이유는 jsp 페이지에서 조회할 때 쓰려고 만들어놓는거다.
            Ex) ${member.id}
        */
        return mv;
    }

}
