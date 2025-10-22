package vn.project.jobhunter.service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.domain.dto.Meta;
import vn.project.jobhunter.domain.dto.ResultPaginationDTO;
import vn.project.jobhunter.repository.UserRepository;
import vn.project.jobhunter.util.error.IdInvalidException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public ResultPaginationDTO handleGetAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageUser.getNumber() + 1);
        mt.setPageSize(pageUser.getSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageUser.getContent());

        return rs;
    }

    public User handleGetUser(long id) {
        // Optional<User> userOpt = userRepository.findById(id);
        // if (userOpt.isPresent()) {
        // return userOpt.get();
        // }
        // return null;
        return userRepository.findById(id).orElseThrow(() -> new IdInvalidException("User not found with id "));

    }

    public User handleUpdateUser(User newInfo) {
        Optional<User> userOpt = userRepository.findById(newInfo.getId());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User userInDb = userOpt.get();

        userInDb.setName(newInfo.getName());
        userInDb.setEmail(newInfo.getEmail());
        userInDb.setPassword(newInfo.getPassword());

        return userRepository.save(userInDb);
    }

    public User handleGetUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

}