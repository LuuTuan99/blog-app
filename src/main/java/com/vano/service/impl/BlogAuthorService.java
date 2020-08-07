package com.vano.service.impl;

import com.vano.entity.BlogAuthor;
import org.springframework.data.domain.Page;

public interface BlogAuthorService {
    Page<BlogAuthor> getList(int page, int limit);

    BlogAuthor getById(long id);

    // Thực hiện xác thực người dùng.
    BlogAuthor login(String username, String password);

    // Đăng ký tài khoản, mã hoá mật khẩu...
    BlogAuthor register(BlogAuthor blogAuthor);

    // Update thông tin tài khoản theo email.
    BlogAuthor update(long id, BlogAuthor updateBlogAuthor);

    boolean delete(long id);
}
