package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    // @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    // 위의 문장을 아래 한줄로 요약할 수 있다.
    @GetMapping("new-form")
    public String newForm() {
        return "new-form";
        //return new ModelAndView("new-form");
    }

    // @RequestMapping(value = "/save", method = RequestMethod.POST)
    // 위의 문장을 아래 한줄로 요약할 수 있다.
    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username,
            @RequestParam("age") int age, // 자동 타입 변환까지 해준다.
            Model model
    ) {
        // String username = request.getParameter("username");
        // int age = Integer.parseInt(request.getParameter("age"));
        // @RequestParam을 사용하여 파라미터를 인수로 받을 수 있다.
        // 별도로 HttpServletRequest, HttpServletResponse 등을 사용하지 않아도 된다.

        Member member = new Member(username, age);
        memberRepository.save(member);

        /*
            ModelAndView mv = new ModelAndView("save-result");
            mv.addObject("member", member);
            별도의 ModelAndView를 반환하여 addObject를 사용할 필요 없이
            아래처럼 메서드에서 넘겨받은 model에다 addAttribute로 데이터를 넣어주기만 하면 된다.
        */
        model.addAttribute("member", member);

        /*
            modelView.getModel().put("member", member);
            기존에는 위의 코드로 모델을 get해서 데이터를 입력했지만,
            ModelAndView 타입으로 addObject를 사용하면 된다.
         */
        /*
            Map 객체 model 에 위처럼 memeber를 put 하는 이유는 jsp 페이지에서 조회할 때 쓰려고 만들어놓는거다.
            Ex) ${member.id}
        */
        return "save-result";
    }

    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        /*
            ModelAndView mv = new ModelAndView("members");
            mv.addObject("members", members);
        */
        return "members";
    }

}
