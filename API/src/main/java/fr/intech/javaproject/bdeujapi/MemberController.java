package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private IMapper mapper;
    @Autowired
    MemberRepository memberRepository;

    @GetMapping
    public void saveMember(Member member) throws Exception {

        memberRepository.save(member);
    }

    @GetMapping
    public Iterable<Member> getAllMembers() throws Exception {

        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public Member getMemberByID(@PathVariable int id) throws Exception {

        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            return optionalMember.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    public void deleteAllMembers() throws Exception {

        memberRepository.deleteAll();
    }
    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) throws Exception {

        memberRepository.deleteById(id);
    }

    @GetMapping("/updatePlace/{id}/{place}")
    public void updatePlace(@PathVariable int id, @PathVariable String place) throws Exception {

        memberRepository.updatePlace(place, id);
    }

    @GetMapping("/updateStartDate/{id}/{startDate}")
    public void updateStartDate(@PathVariable int id, @PathVariable Date startDate) throws Exception {

        memberRepository.updateStartDate(startDate, id);
    }

    @GetMapping("/updateEndDate/{id}/{endDate}")
    public void updateEndDate(@PathVariable int id, @PathVariable Date endDate) throws Exception {

        memberRepository.updateEndDate(endDate, id);
    }
}
