package com.Devex.Controller.api;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.Comment;
import com.Devex.Entity.CommentReply;
import com.Devex.Entity.Customer;
import com.Devex.Entity.User;
import com.Devex.Sevice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/comments")
public class CommentAPIController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentReplyService commentReplyService;

    @PostMapping("/add")
    public Comment addComment(@RequestBody Comment comment) {
        // Xử lý và lưu bình luận vào cơ sở dữ liệu
        return commentService.save(comment);
    }

    @PostMapping("/reply")
    public CommentReply addReplyComment(@RequestBody CommentReply replyComment) {
        // Xử lý và lưu phản hồi bình luận vào cơ sở dữ liệu
        return commentReplyService.save(replyComment);
    }

    @GetMapping("/product/{productId}")
    public List<Comment> getCommentsByProduct(@PathVariable String productId) {
        // Lấy danh sách bình luận theo sản phẩm
        return commentService.findByProductID(productId);
    }

    // Thêm các API khác để cập nhật, xóa bình luận và phản hồi bình luận

}

