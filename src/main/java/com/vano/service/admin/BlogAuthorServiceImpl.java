package com.vano.service.admin;

import com.vano.entity.BlogAuthor;
import com.vano.repository.BlogAuthorRepository;
import com.vano.service.impl.BlogAuthorService;
import com.vano.specification.BlogAuthorSpecification;
import com.vano.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class BlogAuthorServiceImpl implements BlogAuthorService {

    @Autowired
    private BlogAuthorRepository blogAuthorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Page<BlogAuthor> getList(int page, int limit) {
        return blogAuthorRepository.findAll(PageRequest.of(page - 1, limit));
    }

    public Page<BlogAuthor> findAllActive(Specification specification, Pageable pageable) {
        specification = specification
                .and(new BlogAuthorSpecification(new SearchCriteria("status", "!=", BlogAuthor.Status.DELETED.getValue())));
        return blogAuthorRepository.findAll(specification, pageable);
    }

    @Override
    public BlogAuthor getById(long id) {
        return blogAuthorRepository.findById(id).orElse(null);
    }

    public BlogAuthor getByName(String username) {
        return blogAuthorRepository.findByUsername(username).orElse(null);
    }

    @Override
    public BlogAuthor login(String username, String password) {
        Optional<BlogAuthor> optionalBlogAuthor = blogAuthorRepository.findByUsername(username);
        if (optionalBlogAuthor.isPresent()) {
            BlogAuthor blogAuthor = optionalBlogAuthor.get();
            if (blogAuthor.getPassword().equals(password)) {
                return blogAuthor;
            }
        }
        return null;
    }

    @Override
    public BlogAuthor register(BlogAuthor blogAuthor) {
        blogAuthor.setPassword(passwordEncoder.encode(blogAuthor.getPassword()));
        blogAuthor.setGender(BlogAuthor.Gender.MALE.getValue());
        blogAuthor.setRole(BlogAuthor.Role.ADMIN.getValue());
        blogAuthor.setStatus(BlogAuthor.Status.ACTIVE.getValue());
        blogAuthor.setCreatedAtMLS(Calendar.getInstance().getTimeInMillis());
        blogAuthor.setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis());
        return blogAuthorRepository.save(blogAuthor);
    }

    @Override
    public BlogAuthor update(long id, BlogAuthor updateBlogAuthor) {
        Optional<BlogAuthor> optionalBlogAuthor = blogAuthorRepository.findById(id);

        if (optionalBlogAuthor.isPresent()) {
            BlogAuthor existBlogAuthor = optionalBlogAuthor.get();
            existBlogAuthor.setUsername(updateBlogAuthor.getUsername());
            existBlogAuthor.setEmail(updateBlogAuthor.getEmail());
            existBlogAuthor.setAvatar(updateBlogAuthor.getAvatar());
            existBlogAuthor.setRole(updateBlogAuthor.getRole());
            existBlogAuthor.setGender(updateBlogAuthor.getGender());
            existBlogAuthor.setStatus(updateBlogAuthor.getStatus());
            existBlogAuthor.setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis());
        }
        return blogAuthorRepository.save(updateBlogAuthor);
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
